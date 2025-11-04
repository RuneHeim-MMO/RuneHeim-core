package org.zuma.examplePlugin.domain.block.impl.door;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.constants.SoundType;
import org.zuma.examplePlugin.domain.block.base.AbstractInteractableBlock;
import org.zuma.examplePlugin.domain.block.base.MultiBlockHitbox;
import org.zuma.examplePlugin.domain.block.meta.AbstractInteractableBlockMeta;
import org.zuma.examplePlugin.domain.map.MarkerType;
import org.zuma.examplePlugin.domain.skill.SkillRequirement;

import javax.annotation.Nullable;
import java.util.List;

public class DoorBlock extends AbstractInteractableBlock implements MultiBlockHitbox {
    private final int height, width;
    private final String id;
    private final List<Location> hitboxLocations;
    private boolean isOpen;

    public DoorBlock(AbstractInteractableBlockMeta meta, String id, int height, int width) {
        super(meta, null, null);
        this.height = height;
        this.width = width;
        this.id = id;
        this.hitboxLocations = calculateHitboxSize();
        this.isOpen = false;
    }

    public void setOpen(boolean open) {
        if (this.isOpen == open) {
            return;
        }
        this.isOpen = open;
        int highestY = hitboxLocations.stream().mapToInt(Location::getBlockY).max().orElse(this.getLocation().getBlockY()) - 1;
        int lowestY = hitboxLocations.stream().mapToInt(Location::getBlockY).min().orElse(this.getLocation().getBlockY());

        new BukkitRunnable() {
            int currentY = isOpen ? lowestY : highestY;

            @Override
            public void run() {
                if (isOpen) {
                    if (currentY > highestY) {
                        this.cancel();
                        SoundType.DOOR_FINISH_OPENING.play(DoorBlock.super.getLocation());
                        return;
                    }

                    for (Location loc : hitboxLocations) {
                        if (loc.getBlockY() == currentY) {
                            loc.getBlock().setType(Material.AIR);
                        }
                    }
                    currentY++;
                    SoundType.DOOR_MOVING_STEP.play(DoorBlock.super.getLocation());
                } else {
                    if (currentY < lowestY) {
                        this.cancel();
                        SoundType.DOOR_FINISH_CLOSING.play(DoorBlock.super.getLocation());
                        return;
                    }

                    for (Location loc : hitboxLocations) {
                        if (loc.getBlockY() == currentY) {
                            loc.getBlock().setType(Material.IRON_BARS);
                        }
                    }
                    currentY--;
                    SoundType.DOOR_MOVING_STEP.play(DoorBlock.super.getLocation());
                }
            }
        }.runTaskTimer(RuneQuestPlugin.getInstance(), 0, 20L);


    }

    public boolean isOpen() {
        return isOpen;
    }

    public String getDoorId() {
        return id;
    }

    @Override
    public void spawn(Material material) {
        this.hitboxLocations.forEach(location -> {
            location.getBlock().setType(Material.IRON_BARS);
        });
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    public void initializeBlock() {
        super.spawn(Material.IRON_BARS);
        this.hitboxLocations.forEach(location -> {
            location.getBlock().setType(Material.IRON_BARS);
        });
    }
}
