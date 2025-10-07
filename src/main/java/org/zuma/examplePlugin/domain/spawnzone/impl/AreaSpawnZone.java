package org.zuma.examplePlugin.domain.spawnzone.impl;

import org.bukkit.Location;
import org.zuma.examplePlugin.domain.spawnzone.SpawnZoneType;
import org.zuma.examplePlugin.domain.spawnzone.base.AbstractSpawnZone;
import org.zuma.examplePlugin.domain.spawnzone.meta.AbstractSpawnZoneMeta;

public class AreaSpawnZone extends AbstractSpawnZone {
    private final int radius;
    private final boolean spawnAtHighestBlock, isBiomeSpawn;

    public AreaSpawnZone(AbstractSpawnZoneMeta meta, int radius, boolean spawnAtHighestBlock, boolean isBiomeSpawn) {
        super(SpawnZoneType.AREA, meta);
        this.radius = radius;
        this.spawnAtHighestBlock = spawnAtHighestBlock;
        this.isBiomeSpawn = isBiomeSpawn;
    }

    @Override
    public Location getNextMobLocation() {
        Location center = super.getCenterPoint();

        // Generate random angle (0 to 2π)
        double angle = Math.random() * 2 * Math.PI;

        // Generate random distance from center (0 to radius), using sqrt to ensure uniform distribution
        double distance = Math.sqrt(Math.random()) * radius;

        // Calculate x and z offset
        double xOffset = distance * Math.cos(angle);
        double zOffset = distance * Math.sin(angle);

        // Clone the center to avoid modifying the original location
        Location mobLocation = center.clone().add(xOffset, 0, zOffset);

        if (spawnAtHighestBlock) {
            mobLocation.setY(center.getWorld().getHighestBlockYAt(mobLocation));
        }

        return mobLocation;
    }

    public int getRadius() {
        return this.radius;
    }

    public boolean isBiomeSpawn() {
        return isBiomeSpawn;
    }
}
