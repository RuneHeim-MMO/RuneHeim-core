package org.zuma.examplePlugin.domain.mob.mechanics.traits;

import com.ibm.icu.impl.CollectionSet;
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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class InitialEffectMechanic implements MobMechanic {
    private CombatantMob mob;
    private final int nearbyRange;
    private final List<PotionEffect> effectList;
    private final String message;
    private final SoundType soundType;
    private boolean executed = false;


    public InitialEffectMechanic(int nearbyRange, List<PotionEffect> effectList, @Nullable String message, @Nullable SoundType soundType) {
        this.nearbyRange = nearbyRange;
        this.effectList = effectList;
        this.message = message;
        this.soundType = soundType;
    }

    public void setParent(AbstractMob mob) {
        if (!(mob instanceof CombatantMob combatantMob)) {
            throw new IllegalArgumentException("InitialEffectMechanic can only be applied to CombatantMob");
        }

        this.mob = combatantMob;
    }

    @Override
    public void onEntityTargetPlayer(EntityTargetEvent event, RunePlayer player) {
        if (executed) return;

        Collection<Player> targets = nearbyRange > 1
                ? player.asBukkitPlayer().getLocation().getNearbyPlayers(nearbyRange)
                : Collections.singleton(player.asBukkitPlayer());

        targets.forEach(t -> {
            effectList.forEach(t::addPotionEffect);
            if (message != null) mob.sendMessageToPlayer(t, message);
            if (soundType != null) soundType.play(player, true);
        });

        executed = true;
    }

    public void execute(LivingEntity target) {

    }
}
