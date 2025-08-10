package org.zuma.examplePlugin.domain.territory;

import org.bukkit.Location;

public class Territory {
    private final Location centerPoint;
    private final int radius, minLevel, maxLevel;
    private final String name, description, id;

    public Territory(String id, Location centerPoint, int radius, String name, String description, int minLevel, int maxLevel) {
        this.centerPoint = centerPoint;
        this.radius = radius;
        this.minLevel = minLevel;
        this.maxLevel = maxLevel;
        this.name = name;
        this.id = id;
        this.description = description;
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
}
