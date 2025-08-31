package org.zuma.examplePlugin.domain.particle;

import org.bukkit.Location;

public interface AnimatedEffect {
    public void playAt(Location location, double time);
    public int getDurationTicks();
}
