package org.zuma.examplePlugin.domain.mob.mechanics;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.zuma.examplePlugin.core.constants.SoundType;
import org.zuma.examplePlugin.core.utils.MobUtils;
import org.zuma.examplePlugin.domain.combat.Hit;
import org.zuma.examplePlugin.domain.combat.constants.AttackType;
import org.zuma.examplePlugin.domain.mob.base.CombatantMob;
import org.zuma.examplePlugin.domain.player.RunePlayer;

import java.util.*;

public class AntiSafeSpotPullMechanic implements MobMechanic {
    // Number of hits within the time frame to trigger pull
    private static final int HIT_THRESHOLD = 3;

    private final CombatantMob mob;
    private final HashMap<UUID, List<Long>> recentAttackers;

    public AntiSafeSpotPullMechanic(CombatantMob mob) {
        this.mob = mob;
        this.recentAttackers = new HashMap<>();
    }

    @Override
    public void onDamagedByPlayer(RunePlayer attacker, Hit hit) {
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
            // Clean up old timestamps (older than 10 seconds)
            timestamps.removeIf(timestamp -> now - timestamp > 15000);

            // Add current hit
            timestamps.add(now);

            if (timestamps.size() >= HIT_THRESHOLD && mob.getEntity() != null) {
                MobUtils.pullPlayerTowardMob(attacker.asBukkitPlayer(), mob.getEntity(), 1, 0.3);
                SoundType.PLAYER_PULL.play(entity.getLocation());
                recentAttackers.remove(uuid);
            }
        }
    }

    @Override
    public void onMobDamagePlayer(RunePlayer target) {
        this.recentAttackers.remove(target.getUUID());
    }

}
