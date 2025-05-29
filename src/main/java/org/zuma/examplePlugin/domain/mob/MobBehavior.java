package org.zuma.examplePlugin.domain.mob;

import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.zuma.examplePlugin.domain.player.RunePlayer;

import java.util.List;
import java.util.Random;

public class MobBehavior {
    private final List<Sound> idleSounds;
    private final List<Sound> interactionSuccessSounds;
    private final List<Sound> interactionFailSounds;
    private final List<Sound> damageSounds;
    private final Random random = new Random();

    public MobBehavior(List<Sound> idleSounds,
                       List<Sound> interactionSuccessSounds,
                       List<Sound> interactionFailSounds,
                       List<Sound> damageSounds) {
        this.idleSounds = idleSounds;
        this.interactionSuccessSounds = interactionSuccessSounds;
        this.interactionFailSounds = interactionFailSounds;
        this.damageSounds = damageSounds;
    }

    public void handleIdleBehavior(LivingEntity entity) {
        playRandomSound(entity, idleSounds);
    }


    public void handleInteractionSuccess(PlayerInteractAtEntityEvent event, RunePlayer player) {
        playRandomSound(event.getRightClicked(), interactionSuccessSounds);
    }

    public void handleInteractionFail(PlayerInteractAtEntityEvent event, RunePlayer player) {
        playRandomSound(event.getRightClicked(), interactionFailSounds);
    }


    public void handleDamage(EntityDamageEvent event) {
        playRandomSound(event.getEntity(), damageSounds);
    }

    private void playRandomSound(Entity entity, List<Sound> sounds) {
        if (sounds.isEmpty()) return;
        Sound sound = sounds.get(random.nextInt(sounds.size()));
        float pitch = 0.8f + random.nextFloat() * 0.4f;
        entity.getWorld().playSound(entity.getLocation(), sound, 1.0f, pitch);
    }

}
