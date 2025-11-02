package org.zuma.examplePlugin.domain.mob.mechanics.attacks;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.potion.PotionEffect;
import org.jetbrains.annotations.Nullable;
import org.zuma.examplePlugin.core.constants.SoundType;
import org.zuma.examplePlugin.domain.mob.base.AbstractMob;
import org.zuma.examplePlugin.domain.mob.base.CombatantMob;
import org.zuma.examplePlugin.domain.mob.mechanics.base.MobMechanic;
import org.zuma.examplePlugin.domain.player.RunePlayer;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class PotionEffectMechanic implements MobMechanic {
    private final int nearbyRange;
    private final List<PotionEffect> effectList;

    public PotionEffectMechanic(int nearbyRange, List<PotionEffect> effectList) {
        this.nearbyRange = nearbyRange;
        this.effectList = effectList;
    }

    public void setParent(AbstractMob mob) {
    }

    public void execute(LivingEntity target) {
        if (!(target instanceof Player player)) {
            return;
        }

        Collection<Player> targets = nearbyRange > 1
                ? target.getLocation().getNearbyPlayers(nearbyRange)
                : Collections.singleton(player);

        targets.forEach(t -> {
            effectList.forEach(t::addPotionEffect);
        });
    }
}
