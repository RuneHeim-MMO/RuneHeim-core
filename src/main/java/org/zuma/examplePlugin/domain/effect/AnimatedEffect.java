package org.zuma.examplePlugin.domain.effect;

import org.bukkit.Location;

public interface AnimatedEffect {
    void playAt(Location location, double time);
    int getDurationTicks();
}
