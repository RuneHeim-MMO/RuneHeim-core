package org.zuma.examplePlugin.domain.block.base;

import eu.decentsoftware.holograms.api.holograms.Hologram;
import io.papermc.paper.event.block.BlockBreakProgressUpdateEvent;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.zuma.examplePlugin.domain.block.constants.BlockType;
import org.zuma.examplePlugin.domain.block.meta.AbstractInteractableBlockMeta;
import org.zuma.examplePlugin.domain.item.base.AbstractItem;
import org.zuma.examplePlugin.domain.player.RunePlayer;


public abstract class AbstractInteractableBlock {
    private final BlockType type;
    private Location location, hologramLocation;
    private Hologram hologram;

    public AbstractInteractableBlock(AbstractInteractableBlockMeta meta) {
        this.type = meta.type();
        this.location = meta.location();
        this.hologramLocation = meta.hologramLocation();
    }

    public void spawn(Material material) {
        Block block = location.getBlock();
        block.setType(material);
    }

    public void setLocation(Location location) {
        this.location.getBlock().setType(Material.AIR);
        this.location = location;
    }

    public void setHologramLocation(Location location) {
        this.hologramLocation = location;
        if (this.hologram != null)
            this.hologram.setLocation(location);
    }

    public Hologram getHologram() {
        return hologram;
    }

    public void setHologram(Hologram hologram) {
        this.hologram = hologram;
    }

    public BlockType getType() {
        return type;
    }

    public Location getHologramLocation() {
        return hologramLocation;
    }

    public Location getLocation() {
        return location;
    }

    public void onPlayerInteractEvent(RunePlayer player, Action action, PlayerInteractEvent event, AbstractItem itemInHand) {

    }

    public void onPlayerBlockBreakProgressUpdateEvent(RunePlayer player, BlockBreakProgressUpdateEvent event) {

    }

    public void onPlayerBlockBreakEvent(RunePlayer player, BlockBreakEvent event) {

    }

}
