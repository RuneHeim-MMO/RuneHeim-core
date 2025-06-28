package org.zuma.examplePlugin.domain.mob.behavior;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.zuma.examplePlugin.core.constants.SoundType;

import java.util.List;
import java.util.Random;

public class MobBehavior {
    private final List<SoundType> idleSounds;
    private final List<SoundType> interactionSuccessSounds;
    private final List<SoundType> interactionFailSounds;
    private final List<SoundType> damageSounds;
    private final List<SoundType> deathSounds;
    private final Random random = new Random();

    public MobBehavior(List<SoundType> idleSounds,
                       List<SoundType> interactionSuccessSounds,
                       List<SoundType> interactionFailSounds,
                       List<SoundType> damageSounds,
                       List<SoundType> deathSounds) {
        this.idleSounds = idleSounds;
        this.interactionSuccessSounds = interactionSuccessSounds;
        this.interactionFailSounds = interactionFailSounds;
        this.damageSounds = damageSounds;
        this.deathSounds = deathSounds;
    }

    public void handleIdleBehavior(LivingEntity entity) {
        playRandomSound(entity, idleSounds);
    }


    public void handleInteractionSuccess(PlayerInteractAtEntityEvent event) {
        playRandomSound(event.getRightClicked(), interactionSuccessSounds);
    }

    public void handleInteractionFail(PlayerInteractAtEntityEvent event) {
        playRandomSound(event.getRightClicked(), interactionFailSounds);
    }

    public void handleDeath(EntityDeathEvent event) { //TODO: Add this guy!
        playRandomSound(event.getEntity(), deathSounds);
    }

    public void handleDamage(EntityDamageEvent event) {
        playRandomSound(event.getEntity(), damageSounds);
    }

    private void playRandomSound(Entity entity, List<SoundType> sounds) {
        if (sounds.isEmpty()) return;
        SoundType sound = sounds.get(random.nextInt(sounds.size()));
        sound.play(entity.getLocation());
    }

}
