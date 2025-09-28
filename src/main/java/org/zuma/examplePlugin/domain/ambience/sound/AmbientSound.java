package org.zuma.examplePlugin.domain.ambience.sound;

import org.bukkit.Location;
import org.bukkit.World;
import org.zuma.examplePlugin.core.constants.SoundType;

import java.util.concurrent.ThreadLocalRandom;

public class AmbientSound {
    private final SoundType sound;
    private final Location centerLocation;
    private final int delaySeconds, delayVariationSeconds;
    private Long lastPlayedAt;
    private int variationX, variationY, variationZ;

    protected AmbientSound(SoundType sound, Location centerLocation, int delaySeconds, int delayVariationSeconds) {
        this.sound = sound;
        this.centerLocation = centerLocation;
        this.delaySeconds = delaySeconds;
        this.delayVariationSeconds = delayVariationSeconds;
        this.lastPlayedAt = null;
    }

    public void tick() {
        if (shouldPlay()) {
            play();
        }
    }

    public AmbientSound withVariation(int variationX, int variationY, int variationZ) {
        this.variationX = variationX;
        this.variationY = variationY;
        this.variationZ = variationZ;
        return this;
    }

    private boolean shouldPlay() {
        World world = centerLocation.getWorld();
        int chunkX = centerLocation.getBlockX() >> 4;
        int chunkZ = centerLocation.getBlockZ() >> 4;

        if (!world.isChunkLoaded(chunkX, chunkZ)) return false;

        int variation = delayVariationSeconds > 0
                ? ThreadLocalRandom.current().nextInt(0, delayVariationSeconds + 1)
                : 0;

        int effectiveDelay = Math.max(1, delaySeconds - variation);

        if (lastPlayedAt != null) {
            long secondsSinceLastPlayed = (System.currentTimeMillis() - lastPlayedAt) / 1000;
            return secondsSinceLastPlayed >= effectiveDelay;
        }

        return true;


    }

    private void play() {
        Location playLocation = centerLocation;

        if (variationX != 0 || variationY != 0 || variationZ != 0) {
            playLocation = centerLocation.clone().add(
                    variationX == 0 ? 0 : ThreadLocalRandom.current().nextInt(-variationX, variationX + 1),
                    variationY == 0 ? 0 : ThreadLocalRandom.current().nextInt(-variationY, variationY + 1),
                    variationZ == 0 ? 0 : ThreadLocalRandom.current().nextInt(-variationZ, variationZ + 1)
            );
        }

        sound.play(playLocation);
        lastPlayedAt = System.currentTimeMillis();
    }
}
