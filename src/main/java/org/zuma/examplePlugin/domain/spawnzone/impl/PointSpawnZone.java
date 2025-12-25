package org.zuma.examplePlugin.domain.spawnzone.impl;

import org.bukkit.Location;
import org.jetbrains.annotations.Nullable;
import org.zuma.examplePlugin.core.interfaces.POIMarkerable;
import org.zuma.examplePlugin.domain.map.MarkerType;
import org.zuma.examplePlugin.domain.spawnzone.SpawnZoneType;
import org.zuma.examplePlugin.domain.spawnzone.base.AbstractSpawnZone;
import org.zuma.examplePlugin.domain.spawnzone.meta.AbstractSpawnZoneMeta;

public class PointSpawnZone extends AbstractSpawnZone implements POIMarkerable {
    private final boolean spawnAtHighestBlock;
    @Nullable
    private final MarkerType marker;
    @Nullable
    private final String markerLabel;

    public PointSpawnZone(AbstractSpawnZoneMeta meta, boolean spawnAtHighestBlock, @Nullable MarkerType markerType, @Nullable String markerLabel) {
        super(SpawnZoneType.POINT, meta);
        this.spawnAtHighestBlock = spawnAtHighestBlock;
        this.marker = markerType;
        this.markerLabel = markerLabel;
    }

    @Override
    public Location getNextMobLocation() {
        Location base = super.getCenterPoint();

        if (spawnAtHighestBlock) {
            int highestY = base.getWorld().getHighestBlockYAt(base.getBlockX(), base.getBlockZ());
            return new Location(base.getWorld(), base.getX(), highestY, base.getZ());
        } else {
            return base.clone();
        }
    }

    @Override
    public Location getLocation() {
        return super.getCenterPoint();
    }

    @Override
    public @Nullable MarkerType getMarkerType() {
        return marker;
    }

    @Override
    public @Nullable String getMarkerLabel() {
        return markerLabel;
    }
}
