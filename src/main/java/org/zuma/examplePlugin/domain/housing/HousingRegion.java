package org.zuma.examplePlugin.domain.housing;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.util.Vector;

public enum HousingRegion {
    DRAKKAR(192, 73, -70);

    private final int exitX, exitY, exitZ;

    HousingRegion(int exitX, int exitY, int exitZ) {
        this.exitX = exitX;
        this.exitY = exitY;
        this.exitZ = exitZ;
    }

    public Location getExitLocation() {
        return new Location(Bukkit.getWorld("world"), exitX, exitY, exitZ);
    }
}
