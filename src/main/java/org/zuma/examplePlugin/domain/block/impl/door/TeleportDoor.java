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
import org.zuma.examplePlugin.core.constants.SoundType;
import org.zuma.examplePlugin.core.utils.PlayerUtils;
import org.zuma.examplePlugin.domain.block.base.AbstractInteractableBlock;
import org.zuma.examplePlugin.domain.block.base.MultiBlockHitbox;
import org.zuma.examplePlugin.domain.block.meta.AbstractInteractableBlockMeta;
import org.zuma.examplePlugin.domain.item.ItemRecord;
import org.zuma.examplePlugin.domain.item.base.AbstractItemType;
import org.zuma.examplePlugin.domain.player.RunePlayer;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TeleportDoor extends AbstractInteractableBlock implements MultiBlockHitbox {
    private final Location teleportLocation;
    private final String label;

    public TeleportDoor(AbstractInteractableBlockMeta meta, String label, String world, int x, int y, int z, int yaw) {
        super(meta, null, null);
        this.teleportLocation = new Location(Bukkit.getWorld(world), x, y, z, yaw, 0);
        this.label = label;
    }

    private void initializeHologram() {
        List<String> hologramContent = new ArrayList<>(List.of(this.label));
        setHologram(DHAPI.createHologram(UUID.randomUUID().toString(), getHologramLocation().add(0, -1, 0), hologramContent));
    }

    @Override
    public void onPlayerInteractEvent(RunePlayer player, Action action, PlayerInteractEvent event, AbstractItemType itemInHand) {
        event.setCancelled(true);
        getLocation().getWorld().playSound(getLocation(), Sound.BLOCK_IRON_DOOR_OPEN, 1f, 1f);
        setDoorOpen(true);

        Bukkit.getScheduler().runTaskLater(RuneQuestPlugin.getInstance(), () -> {
            Bukkit.getScheduler().runTaskLater(RuneQuestPlugin.getInstance(), () -> {
                player.asBukkitPlayer().teleport(teleportLocation);
                setDoorOpen(false);
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
        bottomData.setHinge(Door.Hinge.RIGHT);
        bottomData.setOpen(false);

        Door topData = (Door) Bukkit.createBlockData(Material.IRON_DOOR);
        topData.setHalf(Bisected.Half.TOP);
        topData.setFacing(face);
        topData.setHinge(Door.Hinge.RIGHT);
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
}
