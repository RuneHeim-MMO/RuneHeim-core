package org.zuma.examplePlugin.domain.fairy;

import com.ticxo.modelengine.api.ModelEngineAPI;
import com.ticxo.modelengine.api.model.ActiveModel;
import com.ticxo.modelengine.api.model.ModeledEntity;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitTask;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.analytics.AnalyticsKey;
import org.zuma.examplePlugin.core.constants.Color;
import org.zuma.examplePlugin.core.constants.SoundType;
import org.zuma.examplePlugin.core.logger.constants.LogLevel;
import org.zuma.examplePlugin.core.utils.AnalyticsUtils;
import org.zuma.examplePlugin.domain.hud.HudService;
import org.zuma.examplePlugin.domain.item.base.AbstractItemType;
import org.zuma.examplePlugin.domain.item.impl.fairy.Fairy;
import org.zuma.examplePlugin.domain.item.impl.fairy.FairyFood;
import org.zuma.examplePlugin.domain.item.impl.fairy.FairyFruit;
import org.zuma.examplePlugin.domain.item.state.states.FairyState;
import org.zuma.examplePlugin.domain.player.RunePlayer;
import org.zuma.examplePlugin.domain.player.inventory.AdditionalGearSlotHandler;
import org.zuma.examplePlugin.domain.player.inventory.ReservedSlot;
import org.zuma.examplePlugin.events.fairy.FairyAutoFedEvent;
import org.zuma.examplePlugin.events.fairy.FairyUpgradeEvent;

import javax.annotation.Nullable;
import java.util.*;

public class FairyHandler {
    private static final int SPRITE_DRAW_INTERVAL = 4;
    private static final int SLOT_CHECKER_INTERVAL = 20;
    private static final int FAIRY_TICK_INTERVAL = 20 * 10;
    private static final Random RANDOM = new Random();

    private final AdditionalGearSlotHandler slotHandler;
    private final HashMap<UUID, Fairy> wornFairies;
    private final HashMap<UUID, Fairy> fedFairies;
    private final HashMap<UUID, Entity> fairyModels;
    double radius = 1.0;
    double height = 2;
    private BukkitTask spriteDrawingTask;
    private BukkitTask playerSlotTracker;
    private BukkitTask fairyTickTask;
    private double t = 0;
    private double velocity = Math.PI / 40;

    public FairyHandler(AdditionalGearSlotHandler slotHandler) {
        this.slotHandler = slotHandler;
        this.wornFairies = new HashMap<>();
        this.fedFairies = new HashMap<>();
        this.fairyModels = new HashMap<>();
    }

    public void handleFruitClickOnFairy(InventoryClickEvent event, RunePlayer player, Fairy fairy, FairyFruit fruit, ItemStack clickedOnFairyItemStack, ItemStack cursorFruitItemStack) {
        FairyState state = fairy.getState(clickedOnFairyItemStack);

        if (state == null) {
            return;
        }

        if (!state.canConsumeFruit()) {
            HudService.sendFailMessage(player, Component.text("This fairy is not ready to consume upgrades."));
            return;
        }

        FairyUpgradeEvent newEvent = new FairyUpgradeEvent(player, fruit, fairy, state);
        Bukkit.getServer().getPluginManager().callEvent(newEvent);

        if (newEvent.isCancelled()) {
            return;
        }

        try {
            state.consumeFruit(fruit);
            fairy.getMetaGenerator().updatePresentationLayer(clickedOnFairyItemStack);

            if (cursorFruitItemStack.getAmount() > 1) {
                cursorFruitItemStack.setAmount(cursorFruitItemStack.getAmount() - 1);
                event.getView().setCursor(cursorFruitItemStack);
            } else {
                event.getView().setCursor(null);
            }


            SoundType.FAIRY_UPGRADE.play(player, true);
            RuneQuestPlugin.getItemStateManager().saveUpdatedState(state, false, true);
            HudService.sendInfoMessage(player,
                    Component.text("Your fairy devoured ", Color.SUCCESS.asColor())
                            .append(cursorFruitItemStack.displayName())
                            .append(Component.text(" and gained new powers!", Color.SUCCESS.asColor()))
            );

            Entity fairyEntity = fairyModels.get(player.getUUID());
            ModeledEntity me = fairyEntity != null ? ModelEngineAPI.getModeledEntity(fairyEntity) : null;

            if (me != null) {
                me.getModels().values().forEach(am -> {
                    am.getAnimationHandler().playAnimation("pet", 0f, 0.3f, 1, true);
                });
            }

            AnalyticsUtils.incrementServerAndPlayer(player, AnalyticsKey.FAIRIES_UPGRADED, 1);
        } catch (Exception e) {
            HudService.sendFailMessage(player, Component.text(e.getMessage()));
        }

    }

    public void startLoop() {
        if (spriteDrawingTask != null && !spriteDrawingTask.isCancelled()) return;
        if (playerSlotTracker != null && !playerSlotTracker.isCancelled()) return;

        spriteDrawingTask = Bukkit.getScheduler().runTaskTimer(RuneQuestPlugin.getInstance(), this::spriteDrawingTaskTick, SPRITE_DRAW_INTERVAL, SPRITE_DRAW_INTERVAL);
        playerSlotTracker = Bukkit.getScheduler().runTaskTimer(RuneQuestPlugin.getInstance(), this::playerSlotTrackerTick, SLOT_CHECKER_INTERVAL, SLOT_CHECKER_INTERVAL);
        fairyTickTask = Bukkit.getScheduler().runTaskTimer(RuneQuestPlugin.getInstance(), this::fairyTaskTick, FAIRY_TICK_INTERVAL, FAIRY_TICK_INTERVAL);
    }

    public void stopLoop() {
        if (spriteDrawingTask != null && !spriteDrawingTask.isCancelled()) {
            spriteDrawingTask.cancel();
            spriteDrawingTask = null;
        }

        if (playerSlotTracker != null && !playerSlotTracker.isCancelled()) {
            playerSlotTracker.cancel();
            playerSlotTracker = null;
        }


        if (fairyTickTask != null && !fairyTickTask.isCancelled()) {
            fairyTickTask.cancel();
            fairyTickTask = null;
        }

        RuneQuestPlugin.getRuneLogger().log(LogLevel.INFO, "Fairy loops stopped.");
    }

    public void clearPlayerFairyInstances(UUID uuid) {
        wornFairies.remove(uuid);
        fedFairies.remove(uuid);

        removeFairyModel(uuid);
    }

    private void removeFairyModel(UUID uuid) {
        Entity fairyModel = fairyModels.get(uuid);

        if (fairyModel == null) {
            return;
        }

        fairyModel.remove();
        fairyModels.remove(uuid);
    }

    private void spriteDrawingTaskTick() {
        updateT();


        fedFairies.forEach((uuid, fairy) -> {

            Player player = Bukkit.getPlayer(uuid);
            Entity matchingEntity = fairyModels.get(uuid);

            if (player == null || !player.isOnline()) {
                if (matchingEntity != null) matchingEntity.remove();
                fairyModels.remove(uuid);
                return;
            }

            if (matchingEntity == null) {
                return;
            }

            moveEntityInHelix(player, matchingEntity);

        });
    }

    @Nullable
    private Entity spawnFairyModel(Player owner, Fairy fairy) {
        ArmorStand entity = (ArmorStand) owner.getWorld().spawnEntity(owner.getLocation().clone().add(0, 128, 0), EntityType.ARMOR_STAND);
        entity.setSilent(true);
        entity.setInvulnerable(true);
        entity.setMarker(true);
        entity.setInvisible(true);

        try {
            ModeledEntity modeledEntity = ModelEngineAPI.createModeledEntity(entity);
            ActiveModel activeModel = ModelEngineAPI.createActiveModel(fairy.getModel());
            modeledEntity.addModel(activeModel, true);
        } catch (Exception e) {
            entity.remove();
            RuneQuestPlugin.getRuneLogger().log(LogLevel.WARN, "Failed to spawn fairy model for " + owner.getName() + " ", e);
            return null;
        }

        return entity;

    }

    private void playerSlotTrackerTick() {
        RuneQuestPlugin.getPlayerManager().getOnlinePlayers().forEach((player) -> {
            ItemStack itemStack = slotHandler.getSlotItem(player, ReservedSlot.FAIRY_SLOT);
            UUID playerUUID = player.getUUID();

            if (itemStack == null) {
                player.getActionBar().setFairyStatus(FairyStatus.NONE);
                clearPlayerFairyInstances(playerUUID);
                return;
            }

            AbstractItemType itemType = RuneQuestPlugin.getItemRegistry().getType(itemStack);

            if (!(itemType instanceof Fairy fairy)) {
                player.getActionBar().setFairyStatus(FairyStatus.NONE);
                clearPlayerFairyInstances(playerUUID);
                return;
            }

            FairyState state = fairy.getState(itemStack);

            if (state == null) {
                player.getActionBar().setFairyStatus(FairyStatus.NONE);
                clearPlayerFairyInstances(playerUUID);
                return;
            }

            if (state.getStamina() <= 0) {
                player.getActionBar().setFairyStatus(FairyStatus.EXHAUSTED);

                fairyMaybeConsumeFoodTick(state, player, fairy, itemType);

                if (fedFairies.containsKey(playerUUID)) {
                    HudService.sendInfoMessage(player, Component.text("Your fairy's stamina has run out. Feed it to keep its bonuses active.", Color.WARNING.asColor()));
                    SoundType.FAIRY_OUT_OF_STAMINA.play(player, true);
                    fedFairies.remove(playerUUID);
                    removeFairyModel(playerUUID);
                }

                return;
            }


            if (!fairyModels.containsKey(playerUUID)) {
                Entity newFairyEntity = spawnFairyModel(player.asBukkitPlayer(), fairy);

                if (newFairyEntity != null) {
                    fairyModels.put(playerUUID, newFairyEntity);
                }
            } else {


                // Check if active entities model matches player worn fairy model
                var modeledEntity = ModelEngineAPI.getModeledEntity(fairyModels.get(playerUUID));
                if (modeledEntity == null || !modeledEntity.getModels().containsKey(fairy.getModel())) {
                    clearPlayerFairyInstances(playerUUID);

                    Entity newFairyEntity = spawnFairyModel(player.asBukkitPlayer(), fairy);
                    fairyModels.put(playerUUID, newFairyEntity);
                    return;
                }


            }


            if (!player.getActionBar().getFairyStatus().equals(FairyStatus.READY_FOR_UPGRADE)) {
                player.getActionBar().setFairyStatus(FairyStatus.ACTIVE);
            }

            fedFairies.put(playerUUID, fairy);
            wornFairies.put(playerUUID, fairy);

        });
    }

    private void fairyTaskTick() {
        List<UUID> invalidPlayers = new ArrayList<>();

        wornFairies.forEach((uuid, fairy) -> {
            Player player = Bukkit.getPlayer(uuid);

            if (player == null || !player.isOnline()) {
                invalidPlayers.add(uuid);
                return;
            }

            RunePlayer runePlayer = RuneQuestPlugin.getPlayerManager().getRunePlayer(player);
            ItemStack item = player.getInventory().getItem(ReservedSlot.FAIRY_SLOT.getSlot());

            if (item == null) {
                invalidPlayers.add(uuid);
                return;
            }

            AbstractItemType runeItem = RuneQuestPlugin.getItemRegistry().getType(item);
            FairyState state = fairy.getState(item);

            if (runePlayer == null || runeItem == null || state == null) {
                invalidPlayers.add(uuid);
                return;
            }

            state.tick();
            runeItem.getMetaGenerator().updatePresentationLayer(item);

            if (state.canConsumeFruit() && state.getStamina() > 0) {
                runePlayer.getActionBar().setFairyStatus(FairyStatus.READY_FOR_UPGRADE);
            }

            fairyMaybeConsumeFoodTick(state, runePlayer, fairy, runeItem);


        });

        invalidPlayers.forEach(this::clearPlayerFairyInstances);
    }

    private void fairyMaybeConsumeFoodTick(FairyState state, RunePlayer runePlayer, Fairy fairy, AbstractItemType fairyItem) {
        Player player = runePlayer.asBukkitPlayer();
        ItemStack item = player.getInventory().getItem(ReservedSlot.FAIRY_SLOT.getSlot());
        ItemStack food = player.getInventory().getItem(ReservedSlot.FAIRY_FOOD_SLOT.getSlot());
        AbstractItemType abstractFood = RuneQuestPlugin.getItemRegistry().getType(food);

        if (item == null || food == null || !(abstractFood instanceof FairyFood fairyFood)) {
            return;
        }

        if (100 - state.getStamina() >= fairyFood.getEnergyRestoreAmount()) {
            FairyAutoFedEvent event = new FairyAutoFedEvent(runePlayer, fairyFood, fairy, state);
            Bukkit.getServer().getPluginManager().callEvent(event);

            if (event.isCancelled()) {
                return;
            }

            if (food.getAmount() > 1) {
                food.setAmount(food.getAmount() - 1);
            } else {
                player.getInventory().setItem(ReservedSlot.FAIRY_FOOD_SLOT.getSlot(), null);
            }

            slotHandler.fillReservedSlots(runePlayer);
            state.feedFairy(fairyFood.getEnergyRestoreAmount());
            fairyItem.getMetaGenerator().updatePresentationLayer(item);
            AnalyticsUtils.incrementServerAndPlayer(runePlayer, AnalyticsKey.FAIRY_RATIONS_CONSUMED, 1);

            HudService.sendInfoMessage(runePlayer, Component.text("Your fairy has consumed 1x ", Color.INFO.asColor()).append(food.displayName()).append(Component.text(".", Color.INFO.asColor())));
            SoundType.FAIRY_FEED_SOUND.play(player, true);
        }
    }

    private void updateT() {
        t += velocity;
        if (t >= 2 * Math.PI) {
            t = 2 * Math.PI;
            velocity = -velocity; // reverse direction to go back down
        } else if (t <= 0) {
            t = 0;
            velocity = -velocity; // reverse direction to go back up
        }
    }

    private void moveEntityInHelix(Player player, Entity entity) {
        World world = entity.getWorld();
        Location base = player.getLocation().add(0, 1, 0);
        Location offset = getHelixPoint(world, t, radius, height);
        Location particleLoc = base.clone().add(offset);

        entity.teleport(particleLoc);
    }

    // Helix point calculation
    private Location getHelixPoint(World world, double t, double radius, double height) {
        double angle;
        if (velocity > 0) {
            angle = t;  // going up, normal angle
        } else {
            angle = 2 * Math.PI - t;  // going down, mirrored angle
        }
        double x = radius * Math.cos(angle);
        double z = radius * Math.sin(angle);
        double y = (height / (2 * Math.PI)) * t;  // linear y from 0 to height as t goes 0 to 2π, mirrors naturally when t goes down
        return new Location(world, x, y, z);
    }
}
