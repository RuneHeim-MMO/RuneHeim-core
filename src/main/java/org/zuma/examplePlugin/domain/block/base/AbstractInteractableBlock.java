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
import org.zuma.examplePlugin.domain.item.base.AbstractItemType;
import org.zuma.examplePlugin.domain.map.MarkerType;
import org.zuma.examplePlugin.domain.player.RunePlayer;

import javax.annotation.Nullable;
import java.util.List;


public abstract class AbstractInteractableBlock implements POIMarkerable {
    private final int id;
    private final BlockType type;
    private Location location, hologramLocation;
    private Hologram hologram;

    @Nullable
    private BlockFace face;
    @Nullable
    private MarkerType markerType;
    @Nullable
    private String markerLabel;

    private boolean disabled = false;

    public AbstractInteractableBlock(AbstractInteractableBlockMeta meta, @Nullable MarkerType markerType, @Nullable String markerLabel) {
        this.id = meta.id();
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

    public int getId() {
        return this.id;
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

    public void setHologramLocation(Location location) {
        this.hologramLocation = location;
        if (this.hologram != null)
            this.hologram.setLocation(location);
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void onDisable() {
        if (hologram != null) {
            DHAPI.removeHologram(hologram.getId());
        }

        location.getBlock().setType(Material.AIR);

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

    @Nullable
    public BlockFace getFace() {
        return this.face;
    }

    public void setFace(@Nullable BlockFace face) {
        this.face = face;
    }

    public void onPlayerInteractEvent(RunePlayer player, Action action, PlayerInteractEvent event, AbstractItemType itemInHand) {

    }

    public void onPlayerBlockBreakProgressUpdateEvent(RunePlayer player, BlockBreakProgressUpdateEvent event) {

    }

    public void onPlayerBlockBreakEvent(RunePlayer player, BlockBreakEvent event) {

    }

    public List<Location> getHitboxLocations() {
        return List.of(this.location);
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }
}
