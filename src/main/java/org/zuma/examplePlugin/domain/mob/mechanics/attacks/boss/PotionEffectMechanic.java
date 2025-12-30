package org.zuma.examplePlugin.domain.mob.mechanics.attacks.boss;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.zuma.examplePlugin.domain.mob.base.AbstractMob;
import org.zuma.examplePlugin.domain.mob.mechanics.base.MobMechanic;

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

    @Override
    public void setParent(AbstractMob mob) {
    }

    @Override
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

    @Override
    public void cleanup() {
    }
}
