package org.zuma.examplePlugin.domain.mob.behavior;

import org.bukkit.entity.Entity;
import org.zuma.examplePlugin.core.constants.SoundType;

import java.util.List;
import java.util.Random;

public class MobBehavior {
    private final List<SoundType> idleSounds, interactionSuccessSounds, interactionFailSounds, damageSounds, deathSounds;
    private final float pitchModifier;
    private final Random random = new Random();

    public enum MobSoundType {
        POSITIVE, NEGATIVE, IDLE, HURT, DEATH
    }

    public MobBehavior(float pitchModifier, List<SoundType> idleSounds,
                       List<SoundType> interactionSuccessSounds,
                       List<SoundType> interactionFailSounds,
                       List<SoundType> damageSounds,
                       List<SoundType> deathSounds) {
        this.pitchModifier = pitchModifier;
        this.idleSounds = idleSounds;
        this.interactionSuccessSounds = interactionSuccessSounds;
        this.interactionFailSounds = interactionFailSounds;
        this.damageSounds = damageSounds;
        this.deathSounds = deathSounds;
    }

    public void playSound(Entity entity, MobSoundType type) {
        switch (type) {
            case POSITIVE -> playRandomSound(entity, interactionSuccessSounds);
            case NEGATIVE -> playRandomSound(entity, interactionFailSounds);
            case IDLE -> playRandomSound(entity, idleSounds);
            case HURT -> playRandomSound(entity, damageSounds);
            case DEATH -> playRandomSound(entity, deathSounds);
        }
    }

    private void playRandomSound(Entity entity, List<SoundType> sounds) {
        if (sounds.isEmpty()) return;
        SoundType sound = sounds.get(random.nextInt(sounds.size()));
        sound.play(entity.getLocation(), pitchModifier);
    }

}
