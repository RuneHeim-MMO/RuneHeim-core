package org.zuma.examplePlugin.domain.housing;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.util.Vector;

public enum HousingRegion {
    DRAKKAR("Drakkar", 226, 77, -7, -180);

    private final String name;
    private final int exitX, exitY, exitZ, yaw;

    HousingRegion(String name, int exitX, int exitY, int exitZ, int yaw) {
        this.name = name;
        this.exitX = exitX;
        this.exitY = exitY;
        this.exitZ = exitZ;
        this.yaw = yaw;
    }

    public String getName() {
        return name;
    }

    public Location getExitLocation() {
        Location loc = new Location(Bukkit.getWorld("world"), exitX, exitY, exitZ);
        loc.setYaw(yaw);
        return loc;
    }
}
