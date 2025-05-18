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
import org.zuma.examplePlugin.domain.item.base.CustomItem;
import org.zuma.examplePlugin.domain.player.RunePlayer;


public abstract class InteractableBlock {
    private final BlockType type;
    private final Location location;
    private Hologram hologram;

    public InteractableBlock(BlockType type, Location location) {
        this.type = type;
        this.location = location;
    }

    public void spawn(Material material) {
        Block block = location.getBlock();
        block.setType(material);
    }

    public Location getLocation() {
        return location;
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

    public void onPlayerInteractEvent(RunePlayer player, Action action, PlayerInteractEvent event, CustomItem itemInHand) {

    }

    public void onPlayerBlockBreakProgressUpdateEvent(RunePlayer player, BlockBreakProgressUpdateEvent event) {

    }

    public void onPlayerBlockBreakEvent(RunePlayer player, BlockBreakEvent event) {

    }

}
