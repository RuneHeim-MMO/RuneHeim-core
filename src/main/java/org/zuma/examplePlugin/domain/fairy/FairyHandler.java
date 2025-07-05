package org.zuma.examplePlugin.domain.fairy;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitTask;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.constants.Color;
import org.zuma.examplePlugin.core.constants.SoundType;
import org.zuma.examplePlugin.domain.item.base.AbstractItemType;
import org.zuma.examplePlugin.domain.item.impl.fairy.Fairy;
import org.zuma.examplePlugin.domain.item.impl.fairy.FairyFood;
import org.zuma.examplePlugin.domain.item.state.states.FairyState;
import org.zuma.examplePlugin.domain.player.RunePlayer;
import org.zuma.examplePlugin.domain.player.inventory.AdditionalGearSlotHandler;
import org.zuma.examplePlugin.domain.player.inventory.ReservedSlot;
import org.zuma.examplePlugin.events.fairy.FairyAutoFedEvent;

import java.util.HashMap;
import java.util.UUID;

public class FairyHandler {
    private static final int SPRITE_DRAW_INTERVAL = 2;
    private static final int SLOT_CHECKER_INTERVAL = 20;
    private static final int FAIRY_TICK_INTERVAL = 20 * 10; //20 * 10;

    private final AdditionalGearSlotHandler slotHandler;
    private final HashMap<UUID, Fairy> wornFairies;
    private final HashMap<UUID, Fairy> fedFairies;
    private BukkitTask spriteDrawingTask;
    private BukkitTask playerSlotTracker;
    private BukkitTask fairyTickTask;

    double radius = 1.0;
    double height = 2;
    private double t = 0;
    private double velocity = Math.PI / 40;

    public FairyHandler(AdditionalGearSlotHandler slotHandler) {
        this.slotHandler = slotHandler;
        this.wornFairies = new HashMap<>();
        this.fedFairies = new HashMap<>();
    }


    private void spriteDrawingTaskTick() {
        updateT();

        fedFairies.forEach((uuid, fairy) -> {
            Player player = Bukkit.getPlayer(uuid);
            Particle particle = fairy.getParticle();

            if (player == null || !player.isOnline()) {
                return;
            }

            drawParticleInHelix(player, particle);
        });
    }

    private void playerSlotTrackerTick() {
        RuneQuestPlugin.getPlayerManager().getOnlinePlayers().forEach((player) -> {
            ItemStack itemStack = slotHandler.getSlotItem(player, ReservedSlot.FAIRY_SLOT);
            UUID playerUUID = player.getUUID();

            if (itemStack == null) {
                player.getActionBar().setFairyStatus(FairyStatus.NONE);
                wornFairies.remove(playerUUID);
                fedFairies.remove(playerUUID);
                return;
            }

            AbstractItemType itemType = RuneQuestPlugin.getItemRegistry().getItem(itemStack);

            if (!(itemType instanceof Fairy fairy)) {
                player.getActionBar().setFairyStatus(FairyStatus.NONE);
                wornFairies.remove(playerUUID);
                fedFairies.remove(playerUUID);
                return;
            }

            FairyState state = fairy.getState(itemStack);

            if (state == null) {
                player.getActionBar().setFairyStatus(FairyStatus.NONE);
                fedFairies.remove(playerUUID);
                wornFairies.remove(playerUUID);
                return;
            }

            if (state.getStamina() <= 0) {
                player.getActionBar().setFairyStatus(FairyStatus.EXHAUSTED);

                if (fedFairies.containsKey(playerUUID)) {
                    player.asBukkitPlayer().sendMessage(Component.text("Your fairy's stamina has run out. Feed it to keep its bonuses active.", Color.WARNING.asColor()));
                    SoundType.FAIRY_OUT_OF_STAMINA.play(player, true);
                    fedFairies.remove(playerUUID);
                }
            } else {
                fedFairies.put(playerUUID, fairy);

                if (!player.getActionBar().getFairyStatus().equals(FairyStatus.READY_FOR_UPGRADE)) {
                    player.getActionBar().setFairyStatus(FairyStatus.ACTIVE);
                }
            }

            wornFairies.put(playerUUID, fairy);

        });
    }

    private void fairyTaskTick() {
        wornFairies.forEach((uuid, fairy) -> {
            Player player = Bukkit.getPlayer(uuid);

            if (player == null || !player.isOnline()) {
                return;
            }

            RunePlayer runePlayer = RuneQuestPlugin.getPlayerManager().getRunePlayer(player);
            ItemStack item = player.getInventory().getItem(ReservedSlot.FAIRY_SLOT.getSlot());
            AbstractItemType runeItem = RuneQuestPlugin.getItemRegistry().getItem(item);
            FairyState state = fairy.getState(item);

            if (runePlayer == null || item == null || runeItem == null || state == null) {
                return;
            }

            state.tick();
            runeItem.getMetaGenerator().updatePresentationLayer(item);

            if (state.canConsumeFruit() && state.getStamina() > 0) {
                runePlayer.getActionBar().setFairyStatus(FairyStatus.READY_FOR_UPGRADE);
            }

            // Feeding
            ItemStack food = player.getInventory().getItem(ReservedSlot.FAIRY_FOOD_SLOT.getSlot());
            AbstractItemType abstractFood = RuneQuestPlugin.getItemRegistry().getItem(food);

            if (food == null || !(abstractFood instanceof FairyFood fairyFood)) {
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
                runeItem.getMetaGenerator().updatePresentationLayer(item);

                player.sendMessage(Component.text("Your fairy has eaten 1x ", Color.INFO.asColor()).append(food.displayName()).append(Component.text(".", Color.INFO.asColor())));
                SoundType.FAIRY_FEED_SOUND.play(player, true);
            }

        });
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

    private void drawParticleInHelix(Player player, Particle particle) {
        World world = player.getWorld();
        Location base = player.getLocation().add(0, 1.5, 0);
        Location offset = getHelixPoint(world, t, radius, height);
        Location particleLoc = base.clone().add(offset);

        world.spawnParticle(particle, particleLoc, 3);
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
