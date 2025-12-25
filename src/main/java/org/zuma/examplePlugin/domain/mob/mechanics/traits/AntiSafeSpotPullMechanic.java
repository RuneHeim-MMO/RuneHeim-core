package org.zuma.examplePlugin.domain.mob.mechanics.traits;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.zuma.examplePlugin.core.constants.SoundType;
import org.zuma.examplePlugin.core.utils.MobUtils;
import org.zuma.examplePlugin.domain.combat.Hit;
import org.zuma.examplePlugin.domain.combat.constants.AttackType;
import org.zuma.examplePlugin.domain.mob.base.AbstractMob;
import org.zuma.examplePlugin.domain.mob.base.CombatantMob;
import org.zuma.examplePlugin.domain.mob.mechanics.base.MobMechanic;
import org.zuma.examplePlugin.domain.player.RunePlayer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class AntiSafeSpotPullMechanic implements MobMechanic {
    // Number of hits within the time frame to trigger pull
    private static final int HIT_THRESHOLD = 4;

    private CombatantMob mob;
    private final HashMap<UUID, List<Long>> recentAttackers;

    public AntiSafeSpotPullMechanic() {
        this.recentAttackers = new HashMap<>();
    }

    public void setParent(AbstractMob mob) {
        if (!(mob instanceof CombatantMob combatantMob)) {
            throw new IllegalArgumentException("AntiSafeSpotPullMechanic can only be applied to CombatantMob");
        }

        this.mob = combatantMob;
    }


    @Override
    public void onDamagedByPlayer(RunePlayer attacker, Hit hit) {
        if (mob == null) return;

        Entity entity = mob.getEntity();

        if ((hit.getAttackType() != AttackType.MELEE) || !(entity instanceof LivingEntity)) {
            return;
        }

        long now = System.currentTimeMillis();
        UUID uuid = attacker.getUUID();

        if (!recentAttackers.containsKey(uuid)) {
            List<Long> timestamps = new ArrayList<>();
            timestamps.add(now);
            recentAttackers.put(uuid, timestamps);
        } else {
            List<Long> timestamps = recentAttackers.get(uuid);
            // Clean up old timestamps (older than 15 seconds)
            timestamps.removeIf(timestamp -> now - timestamp > 15000);

            // Add current hit
            timestamps.add(now);

            if (timestamps.size() >= HIT_THRESHOLD && mob.getEntity() != null) {
                execute(attacker.asBukkitPlayer());
                recentAttackers.remove(uuid);
            }
        }
    }

    @Override
    public void onMobDamagePlayer(RunePlayer target, Hit hit) {
        this.recentAttackers.remove(target.getUUID());
    }

    public void execute(LivingEntity target) {
        if (mob == null) return;

        Entity entity = mob.getEntity();

        if (entity == null) return;
        MobUtils.pullEntityTowardMob(target, entity, 1, 0.3);
        SoundType.PLAYER_PULL.play(entity.getLocation());
    }

    @Override
    public void cleanup() {
        this.mob = null;
        this.recentAttackers.clear();
    }


}
