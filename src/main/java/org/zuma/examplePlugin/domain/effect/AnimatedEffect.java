package org.zuma.examplePlugin.domain.effect;

import org.bukkit.Location;

public interface AnimatedEffect {
    public void playAt(Location location, double time);
    public int getDurationTicks();
}
