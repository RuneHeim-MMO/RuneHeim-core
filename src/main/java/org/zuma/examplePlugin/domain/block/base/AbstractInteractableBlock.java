package org.zuma.examplePlugin.domain.block.base;

import eu.decentsoftware.holograms.api.DHAPI;
import eu.decentsoftware.holograms.api.holograms.Hologram;
import io.papermc.paper.event.block.BlockBreakProgressUpdateEvent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.zuma.examplePlugin.core.interfaces.POIMarkerable;
import org.zuma.examplePlugin.domain.block.constants.BlockType;
import org.zuma.examplePlugin.domain.block.meta.AbstractInteractableBlockMeta;
import org.zuma.examplePlugin.domain.item.base.AbstractItem;
import org.zuma.examplePlugin.domain.map.MarkerType;
import org.zuma.examplePlugin.domain.player.RunePlayer;

import javax.annotation.Nullable;


public abstract class AbstractInteractableBlock implements POIMarkerable {
    private final BlockType type;
    private Location location, hologramLocation;
    private Hologram hologram;

    @Nullable
    private BlockFace face;
    @Nullable
    private MarkerType markerType;
    @Nullable
    private String markerLabel;

    public AbstractInteractableBlock(AbstractInteractableBlockMeta meta, @Nullable MarkerType markerType, @Nullable String markerLabel) {
        this.type = meta.type();
        this.location = meta.location();
        this.hologramLocation = meta.hologramLocation();
        this.markerType = markerType;
        this.markerLabel = markerLabel;
        this.face = meta.face();
    }

    public void spawn(Material material) {
        Block block = location.getBlock();
        block.setType(material);

        BlockData data = Bukkit.createBlockData(material);
        if (data instanceof Directional directional && face != null) {
            directional.setFacing(face);
            block.setBlockData(directional);
        }
    }

    public void setLocation(Location location) {
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

    public void onDisable() {
        if (hologram != null) {
            DHAPI.removeHologram(hologram.getId());
        }

        location.getBlock().setType(Material.AIR);

    }

    public void onPlayerInteractEvent(RunePlayer player, Action action, PlayerInteractEvent event, AbstractItem
            itemInHand) {

    }

    public void onPlayerBlockBreakProgressUpdateEvent(RunePlayer player, BlockBreakProgressUpdateEvent event) {

    }

    public void onPlayerBlockBreakEvent(RunePlayer player, BlockBreakEvent event) {

    }

    @Nullable
    public MarkerType getMarkerType() {
        return markerType;
    }

    public void setMarkerType(@Nullable MarkerType type) {
        this.markerType = type;
    }

    @Nullable
    public String getMarkerLabel() {
        return markerLabel;
    }

    public void setMarkerLabel(@Nullable String markerLabel) {
        this.markerLabel = markerLabel;
    }

    public void setFace(@Nullable BlockFace face) {
        this.face = face;
    }

    @Nullable
    public BlockFace getFace() {
        return this.face;
    }
}
