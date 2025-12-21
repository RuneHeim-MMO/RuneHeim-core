package org.zuma.examplePlugin.domain.block.impl.door;

import eu.decentsoftware.holograms.api.DHAPI;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Bisected;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.Door;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.analytics.AnalyticsKey;
import org.zuma.examplePlugin.core.constants.SoundType;
import org.zuma.examplePlugin.core.utils.AnalyticsUtils;
import org.zuma.examplePlugin.core.utils.PlayerUtils;
import org.zuma.examplePlugin.domain.block.base.AbstractInteractableBlock;
import org.zuma.examplePlugin.domain.block.base.MultiBlockHitbox;
import org.zuma.examplePlugin.domain.block.base.QuestLockedInteractable;
import org.zuma.examplePlugin.domain.block.meta.AbstractInteractableBlockMeta;
import org.zuma.examplePlugin.domain.block.meta.v1.QuestLockedMeta;
import org.zuma.examplePlugin.domain.item.ItemRecord;
import org.zuma.examplePlugin.domain.item.base.AbstractItemType;
import org.zuma.examplePlugin.domain.player.RunePlayer;
import org.zuma.examplePlugin.events.block.TeleportDoorEnteredEvent;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TeleportDoor extends QuestLockedInteractable implements MultiBlockHitbox {
    private final Location teleportLocation;
    private final String id, label;
    private final Door.Hinge hinge;
    private final List<UUID> inUseBy = new ArrayList<>();

    public TeleportDoor(AbstractInteractableBlockMeta meta, Door.Hinge hinge, String id, String label, String world, int x, int y, int z, int yaw, QuestLockedMeta questLockedMeta) {
        super(meta, questLockedMeta);
        this.teleportLocation = new Location(Bukkit.getWorld(world), x, y, z, yaw, 0);
        this.id = id;
        this.label = label;
        this.hinge = hinge;
    }

    private void initializeHologram() {
        List<String> hologramContent = new ArrayList<>(List.of(this.label));
        setHologram(DHAPI.createHologram(UUID.randomUUID().toString(), getHologramLocation().add(0, -1, 0), hologramContent));
    }

    @Override
    public void onInteractSuccess(RunePlayer player, Action action, PlayerInteractEvent event, AbstractItemType itemInHand) {
        if (inUseBy.contains(player.getUUID())) {
            return;
        }

        if(teleportLocation.getWorld() == null){
            teleportLocation.setWorld(Bukkit.getWorld("world"));
        }

        getLocation().getWorld().playSound(getLocation(), Sound.BLOCK_IRON_DOOR_OPEN, 1f, 1f);
        setDoorOpen(true);
        inUseBy.add(player.getUUID());

        Bukkit.getScheduler().runTaskLater(RuneQuestPlugin.getInstance(), () -> {
            Bukkit.getScheduler().runTaskLater(RuneQuestPlugin.getInstance(), () -> {
                player.asBukkitPlayer().teleport(teleportLocation);
                setDoorOpen(false);
                inUseBy.remove(player.getUUID());

                Bukkit.getPluginManager().callEvent(new TeleportDoorEnteredEvent(this, player));
                AnalyticsUtils.incrementServerAndPlayer(player, AnalyticsKey.DOORS_USED, 1);
            }, 1L);

            getLocation().getWorld().playSound(getLocation(), Sound.BLOCK_IRON_DOOR_CLOSE, 1f, 1f);
        }, 10L);
    }

    public void setDoorOpen(boolean open) {
        Block block = getLocation().getBlock();

        if (block.getBlockData() instanceof Door door) {
            door.setOpen(open);
            block.setBlockData(door);
        }
    }

    @Override
    public void spawn(Material material) {
        BlockFace face = getFace() == null ? BlockFace.NORTH : getFace();
        Block bottom = getLocation().getBlock();
        Block top = bottom.getRelative(0, 1, 0);

        // Create complete door BlockData from scratch
        Door bottomData = (Door) Bukkit.createBlockData(Material.IRON_DOOR);
        bottomData.setHalf(Bisected.Half.BOTTOM);
        bottomData.setFacing(face);
        bottomData.setHinge(hinge);
        bottomData.setOpen(false);

        Door topData = (Door) Bukkit.createBlockData(Material.IRON_DOOR);
        topData.setHalf(Bisected.Half.TOP);
        topData.setFacing(face);
        topData.setHinge(hinge);
        topData.setOpen(false);

        // Set both blocks with the complete data
        bottom.setBlockData(bottomData);
        top.setBlockData(topData);
    }

    public void initializeBlock() {
        initializeHologram();
        this.spawn(Material.IRON_DOOR);
    }

    @Override
    public List<Location> getHitboxLocations() {
        return List.of(getLocation(), getLocation().clone().add(0, 1, 0));
    }


    @Override
    public int getWidth() {
        return 1;
    }

    @Override
    public int getHeight() {
        return 1;
    }

    public String getDoorId() {
        return this.id;
    }

}
