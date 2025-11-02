package org.zuma.examplePlugin.domain.mob.mechanics.attacks;

import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.domain.mob.base.AbstractMob;
import org.zuma.examplePlugin.domain.mob.base.CombatantMob;
import org.zuma.examplePlugin.domain.mob.constants.MobType;
import org.zuma.examplePlugin.domain.mob.mechanics.base.MobMechanic;
import org.zuma.examplePlugin.domain.spawnzone.SpawnZoneMobRecord;

import java.util.List;
import java.util.UUID;

public class SummonCreaturesMechanic implements MobMechanic {
    private CombatantMob mob;
    private final List<SpawnZoneMobRecord> creatures;
    private final int summonRadius;

    public SummonCreaturesMechanic(List<SpawnZoneMobRecord> creatures, int summonRadius) {
        this.creatures = creatures;
        this.summonRadius = summonRadius;
    }

    @Override
    public void setParent(AbstractMob mob) {
        if (!(mob instanceof CombatantMob combatantMob)) {
            throw new IllegalArgumentException("SummonCreaturesMechanic can only be applied to CombatantMob");
        }

        this.mob = combatantMob;
    }


    @Override
    public void execute(LivingEntity target) {
        if (mob == null || mob.getEntity() == null) return;
        Location center = mob.getEntity().getLocation();

        for (SpawnZoneMobRecord record : creatures) {
            Location spawnLocation = center.clone().add(
                    (Math.random() - 0.5) * 2 * summonRadius,
                    0,
                    (Math.random() - 0.5) * 2 * summonRadius
            );

            int amountToSpawn = record.amount();
            MobType creatureType = record.mobId();
            int chance = record.spawnChancePercentage();

            for (int i = 0; i < amountToSpawn; i++) {
                if (Math.random() * 100 > chance) {
                    continue;
                }
                RuneQuestPlugin.getMobRegistry().spawnMob(UUID.randomUUID(), spawnLocation, creatureType.getId());
            }
        }
    }

    @Override
    public void cleanup() {
        this.mob = null;
    }


}
