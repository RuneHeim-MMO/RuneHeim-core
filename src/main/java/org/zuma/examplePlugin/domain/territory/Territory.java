package org.zuma.examplePlugin.domain.territory;

import org.bukkit.Location;

import javax.annotation.Nullable;

public class Territory {
    private final Location centerPoint, spawnPoint;
    private final int radius, minLevel, maxLevel;
    private final String name, description, firstTimeDiscoveryLore, id;

    public Territory(String id, Location centerPoint, @Nullable Location spawnPoint, int radius, String name, String description, String firstTimeDiscoveryLore, int minLevel, int maxLevel) {
        this.centerPoint = centerPoint;
        this.radius = radius;
        this.minLevel = minLevel;
        this.maxLevel = maxLevel;
        this.name = name;
        this.id = id;
        this.description = description;
        this.spawnPoint = spawnPoint;
        this.firstTimeDiscoveryLore = firstTimeDiscoveryLore;
    }

    public Location getCenterPoint() {
        return centerPoint;
    }

    public int getRadius() {
        return radius;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public int getMinLevel() {
        return minLevel;
    }

    public int getMaxLevel() {
        return maxLevel;
    }

    @Nullable
    public Location getSpawnPoint() {
        return spawnPoint;
    }

    public String getFirstTimeDiscoveryLore() {
        return firstTimeDiscoveryLore;
    }
}
