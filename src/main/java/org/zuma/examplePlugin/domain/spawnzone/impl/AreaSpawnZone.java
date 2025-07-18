package org.zuma.examplePlugin.domain.spawnzone.impl;

import org.bukkit.Location;
import org.zuma.examplePlugin.domain.spawnzone.SpawnZoneMobRecord;
import org.zuma.examplePlugin.domain.spawnzone.SpawnZoneType;
import org.zuma.examplePlugin.domain.spawnzone.base.AbstractSpawnZone;
import org.zuma.examplePlugin.domain.spawnzone.meta.AbstractSpawnZoneMeta;

import java.util.List;

public class AreaSpawnZone extends AbstractSpawnZone {
    private final int radius;
    private final boolean spawnAtHighestBlock;

    public AreaSpawnZone(AbstractSpawnZoneMeta meta, int radius, boolean spawnAtHighestBlock) {
        super(SpawnZoneType.AREA, meta);
        this.radius = radius;
        this.spawnAtHighestBlock = spawnAtHighestBlock;
    }

    @Override
    public Location getNextMobLocation() {
        Location center = super.getLocation();

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
}
