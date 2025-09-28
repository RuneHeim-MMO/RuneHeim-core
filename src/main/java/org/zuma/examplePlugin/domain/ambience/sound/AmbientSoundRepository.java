package org.zuma.examplePlugin.domain.ambience.sound;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.zuma.examplePlugin.core.constants.SoundType;

import java.util.List;

public class AmbientSoundRepository {

    public List<AmbientSound> getAll() {
        return List.of(
                // Rogues haven ambient sounds

                // Church
                new AmbientSound(SoundType.AMBIENT_CREAK, new Location(Bukkit.getWorld("world"), -214, 90, -1084), 6, 2).withVariation(10, 10, 0),
                new AmbientSound(SoundType.AMBIENT_SNIFFER_DIGGING_STOP, new Location(Bukkit.getWorld("world"), -220, 77, -1085), 30, 5),

                // Lighthouse
                new AmbientSound(SoundType.AMBIENT_SCREAM, new Location(Bukkit.getWorld("world"), -172, 80, -992), 60, 20)

                );
    }
}
