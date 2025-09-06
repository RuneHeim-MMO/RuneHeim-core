package org.zuma.examplePlugin.domain.mount;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitTask;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.utils.MathUtils;
import org.zuma.examplePlugin.domain.hud.HudService;
import org.zuma.examplePlugin.domain.item.base.AbstractItemType;
import org.zuma.examplePlugin.domain.item.impl.Mount;
import org.zuma.examplePlugin.domain.item.state.base.AbstractState;
import org.zuma.examplePlugin.domain.item.state.states.MountState;
import org.zuma.examplePlugin.domain.player.RunePlayer;
import org.zuma.examplePlugin.domain.player.inventory.AdditionalGearSlotHandler;
import org.zuma.examplePlugin.domain.player.inventory.ReservedSlot;

import javax.annotation.Nullable;
import java.util.*;

public class MountHandler {
    private static final int LEVELING_TICK_INTERVAL = 20;
    private static final int MOUNT_VALIDITY_CHECK_TICK_INTERVAL = 40;
    private static final int DESPAWN_DISTANCE_BLOCKS = 50;

    private final AdditionalGearSlotHandler slotHandler;

    private BukkitTask tickTask;
    private BukkitTask playerSlotTracker;

    private final HashMap<UUID, PlayerMount> mounts = new HashMap<>(); // Player UUID : Mount

    // TODO: maybe assign datakey to prevent mounts from being alive infinitely if left in an unloaded chunk
    public MountHandler(AdditionalGearSlotHandler slotHandler) {
        this.slotHandler = slotHandler;
    }

    public void startLoop() {
        tickTask = Bukkit.getScheduler().runTaskTimer(RuneQuestPlugin.getInstance(), this::levelingTick, LEVELING_TICK_INTERVAL, LEVELING_TICK_INTERVAL);
        playerSlotTracker = Bukkit.getScheduler().runTaskTimer(RuneQuestPlugin.getInstance(), this::mountLifeCycleTick, MOUNT_VALIDITY_CHECK_TICK_INTERVAL, MOUNT_VALIDITY_CHECK_TICK_INTERVAL);
    }

    private void stopLoop() {
        if (tickTask != null && !tickTask.isCancelled()) {
            tickTask.cancel();
            tickTask = null;
        }

        if (playerSlotTracker != null && !playerSlotTracker.isCancelled()) {
            playerSlotTracker.cancel();
            playerSlotTracker = null;
        }
    }

    public void onDisable() {
        stopLoop();
        mounts.forEach((uuid, data) -> removePlayersActiveMount(uuid));
    }

    public void setMountName(RunePlayer player, String name) {
        ItemStack itemStack = slotHandler.getSlotItem(player, ReservedSlot.MOUNT_SLOT);
        if (itemStack == null) return;

        AbstractItemType item = RuneQuestPlugin.getItemRegistry().getType(itemStack);
        if (!(item instanceof Mount mountItem)) {
            HudService.sendFailMessage(player, Component.text("You do not have any active mounts."));
            return;
        }

        AbstractState state = RuneQuestPlugin.getItemStateManager().getItemState(itemStack);
        if (!(state instanceof MountState mountState)) return;

        try {
            mountState.setName(name, true);
            RuneQuestPlugin.getItemStateManager().saveUpdatedState(mountState, false, true);
            removePlayersActiveMount(player.getUUID());
            item.getMetaGenerator().updatePresentationLayer(itemStack);

            HudService.sendInfoMessage(player, Component.text("You have set your mounts name to '" + name + "'"));
        } catch (Exception e) {
            HudService.sendFailMessage(player, Component.text(e.getMessage()));
        }
    }

    public ActiveMountData getMountData(@Nullable UUID mountUUID) {
        if (mountUUID == null) return null;

        for (Map.Entry<UUID, PlayerMount> entry : mounts.entrySet()) {
            UUID uuid = entry.getKey();
            PlayerMount data = entry.getValue();

            if (data.getActiveEntityUUID() != mountUUID) {
                continue;
            }

            Player player = Bukkit.getPlayer(uuid);
            if (player == null) return null;

            RunePlayer runePlayer = RuneQuestPlugin.getPlayerManager().getRunePlayer(player);
            if (runePlayer == null) return null;

            ItemStack itemStack = slotHandler.getSlotItem(runePlayer, ReservedSlot.MOUNT_SLOT);
            if (itemStack == null) return null;

            AbstractItemType item = RuneQuestPlugin.getItemRegistry().getType(itemStack);
            if (!(item instanceof Mount mountItem)) return null;

            AbstractState state = RuneQuestPlugin.getItemStateManager().getItemState(itemStack);
            if (!(state instanceof MountState mountState)) return null;

            return new ActiveMountData(mountState, mountState.getLevel(mountItem));
        }

        return null;
    }

    private void levelingTick() {
        mounts.forEach((playerUUID, data) -> {
            Player bukkitPlayer = Bukkit.getPlayer(playerUUID);

            if (data.getActiveEntityUUID() == null || bukkitPlayer == null) {
                return;
            }

            RunePlayer player = RuneQuestPlugin.getPlayerManager().getRunePlayer(bukkitPlayer);
            Entity activeEntity = Bukkit.getEntity(data.getActiveEntityUUID());

            if (player == null || activeEntity == null || !activeEntity.isValid() || !(activeEntity instanceof Horse horse)) {
                return;
            }

            if (data.getLastPosition() == null) {
                data.setLastPosition(horse.getLocation());
            }

            if (!horse.getPassengers().contains(bukkitPlayer)) {
                return;
            }

            double distanceSinceLastTick = MathUtils.getLocationsDistanceXZ(data.getLastPosition(), horse.getLocation());
            if (distanceSinceLastTick < 3) {
                return;
            }

            // todo redirect damage to horse

            ItemStack itemStack = slotHandler.getSlotItem(player, ReservedSlot.MOUNT_SLOT);
            AbstractItemType item = RuneQuestPlugin.getItemRegistry().getType(itemStack);

            if (!(item instanceof Mount mount) || itemStack == null) {
                return;
            }

            MountState state = mount.getState(itemStack);

            if (state == null) {
                return;
            }

            data.setLastPosition(horse.getLocation());
            state.tick(mount, distanceSinceLastTick);
            item.getMetaGenerator().updatePresentationLayer(itemStack);
        });
    }

    public void spawnMount(RunePlayer player) {
        UUID playerUUID = player.getUUID();

        if (!mounts.containsKey(playerUUID)) {
            HudService.sendFailMessage(player, Component.text("You do not have any mounts equipped."));
            return;
        }

        PlayerMount playerMount = mounts.get(playerUUID);
        AbstractItemType item = RuneQuestPlugin.getItemRegistry().getType(playerMount.getMountID());

        if (!(item instanceof Mount mount)) {
            return;
        }

        ItemStack itemStack = slotHandler.getSlotItem(player, ReservedSlot.MOUNT_SLOT);

        removePlayersActiveMount(playerUUID);
        MountState state = mount.getState(itemStack);

        if (state == null) {
            return;
        }

        UUID newMountUUID = mount.spawn(player, state);

        if (newMountUUID == null) {
            return;
        }

        state.setOnLevelUp((newLevel) -> {
            mount.onLevelChange(player, newLevel, newMountUUID, true);
            return null;
        });

        playerMount.setActiveEntityUUID(newMountUUID);

    }

    private void removePlayersActiveMount(UUID playerUUID) {
        if (!mounts.containsKey(playerUUID)) {
            return;
        }

        PlayerMount playerMount = mounts.get(playerUUID);
        UUID mountUUID = playerMount.getActiveEntityUUID();

        if (mountUUID == null) {
            return;
        }

        Entity mount = Bukkit.getEntity(mountUUID);

        if (mount != null) {
            mount.remove();
            playerMount.setActiveEntityUUID(null);
            playerMount.setLastPosition(null);
        }

    }

    public void clearMountInstances(UUID playerUUID) {
        removePlayersActiveMount(playerUUID);
        mounts.remove(playerUUID);
    }

    private double getActiveMountDistance(RunePlayer player) {
        PlayerMount playerMount = mounts.get(player.getUUID());

        if (playerMount != null && playerMount.getActiveEntityUUID() != null) {
            UUID mountUUID = playerMount.getActiveEntityUUID();

            if (mountUUID == null) {
                return -1;
            }

            Entity entity = Bukkit.getEntity(mountUUID);

            if (entity == null || !entity.isValid()) {
                removePlayersActiveMount(mountUUID);
                return -1;
            }

            return (MathUtils.getLocationsDistanceXZ(player.asBukkitPlayer().getLocation(), entity.getLocation()));
        }

        return -1;
    }

    private void mountLifeCycleTick() {
        RuneQuestPlugin.getPlayerManager().getOnlinePlayers().forEach((player) -> {
            ItemStack itemStack = slotHandler.getSlotItem(player, ReservedSlot.MOUNT_SLOT);
            UUID playerUUID = player.getUUID();

            if (itemStack == null) {
                clearMountInstances(playerUUID);
                return;
            }

            AbstractItemType itemType = RuneQuestPlugin.getItemRegistry().getType(itemStack);

            if (!(itemType instanceof Mount mount)) {
                clearMountInstances(playerUUID);
                player.getActionBar().setMountStatus(MountStatus.NONE);
                return;
            }

            MountState state = mount.getState(itemStack);
            UUID stateUUID = RuneQuestPlugin.getItemStateManager().getItemStackStateUuid(itemStack);

            if (state == null || stateUUID == null) {
                clearMountInstances(playerUUID);
                player.getActionBar().setMountStatus(MountStatus.NONE);
                return;
            }

            PlayerMount playerMount = mounts.get(playerUUID);

            if (playerMount != null && (!playerMount.getStateUUID().equals(stateUUID) || getActiveMountDistance(player) > DESPAWN_DISTANCE_BLOCKS)) {
                player.getActionBar().setMountStatus(MountStatus.NONE);
                clearMountInstances(playerUUID);
            }

            if (!mounts.containsKey(playerUUID)) {
                mounts.put(playerUUID, new PlayerMount(playerUUID, null, itemType.id, stateUUID));
            }
        });

        Set<UUID> playerUUIDs = new HashSet<>(mounts.keySet());

        playerUUIDs.forEach(uuid -> {
            Player player = Bukkit.getPlayer(uuid);
            if (player == null || !player.isOnline()) {
                clearMountInstances(uuid);
                return;
            }

            RunePlayer runePlayer = RuneQuestPlugin.getPlayerManager().getRunePlayer(player);

            if (runePlayer != null) {
                PlayerMount mount = this.mounts.get(runePlayer.getUUID());
                MountStatus status;

                if (mount == null) {
                    status = MountStatus.NONE;
                } else if (mount.getActiveEntityUUID() == null || Bukkit.getEntity(mount.getActiveEntityUUID()) == null) {
                    status = MountStatus.WEARING;
                } else {
                    status = MountStatus.ACTIVE;
                }

                runePlayer.getActionBar().setMountStatus(status);
            }

            if (runePlayer == null || runePlayer.isDead()) {
                clearMountInstances(uuid);
                return;
            }
        });


    }
}
