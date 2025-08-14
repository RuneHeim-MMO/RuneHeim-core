package org.zuma.examplePlugin.domain.spawnzone.impl;

import org.bukkit.Location;
import org.zuma.examplePlugin.domain.spawnzone.SpawnZoneType;
import org.zuma.examplePlugin.domain.spawnzone.base.AbstractSpawnZone;
import org.zuma.examplePlugin.domain.spawnzone.meta.AbstractSpawnZoneMeta;

public class PointSpawnZone extends AbstractSpawnZone {
    private final boolean spawnAtHighestBlock;

    public PointSpawnZone(AbstractSpawnZoneMeta meta, boolean spawnAtHighestBlock) {
        super(SpawnZoneType.POINT, meta);
        this.spawnAtHighestBlock = spawnAtHighestBlock;
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
}
