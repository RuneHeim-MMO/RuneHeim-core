package org.zuma.examplePlugin.domain.mob.mechanics.attacks;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.constants.SoundType;
import org.zuma.examplePlugin.domain.combat.Hit;
import org.zuma.examplePlugin.domain.mob.base.AbstractMob;
import org.zuma.examplePlugin.domain.mob.base.CombatantMob;
import org.zuma.examplePlugin.domain.mob.mechanics.base.MobMechanic;
import org.zuma.examplePlugin.domain.player.RunePlayer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class CobwebMechanic implements MobMechanic {
    private CombatantMob mob;
    private final int chancePerAttack, durationTicks;
    private final List<Location> placedCobwebs = new ArrayList<>();

    public CobwebMechanic(int chancePerAttack, int durationTicks) {
        this.chancePerAttack = chancePerAttack;
        this.durationTicks = durationTicks;
    }

    @Override
    public void setParent(AbstractMob mob) {
        if (!(mob instanceof CombatantMob combatantMob)) {
            throw new IllegalArgumentException("SpecialAttackMechanic can only be applied to CombatantMob");
        }

        this.mob = combatantMob;
    }

    @Override
    public void execute(LivingEntity target) {
        Location loc = target.getLocation();

        if (!loc.getBlock().getType().equals(Material.AIR)) {
            return;
        }

        SoundType.COBWEB_PLACE.play(loc);
        loc.getBlock().setType(Material.COBWEB);
        placedCobwebs.add(loc);

        Bukkit.getScheduler().runTaskLater(RuneQuestPlugin.getInstance(), () -> {
            if (loc.getBlock().getType().equals(Material.COBWEB)) {
                loc.getBlock().setType(Material.AIR);
                placedCobwebs.remove(loc);
            }
        }, durationTicks);

    }

    @Override
    public void onMobDamagePlayer(RunePlayer target, Hit hit) {
        if (mob == null || mob.getEntity() == null || target == null || target.isDead()) return;

        int roll = ThreadLocalRandom.current().nextInt(100);
        if (roll >= chancePerAttack) {
            return; // Did not meet chance to apply cobweb
        }

        execute(target.asBukkitPlayer());
    }

    @Override
    public void cleanup() {
        this.mob = null;

        placedCobwebs.forEach(l -> {
            l.getBlock().setType(Material.AIR);
        });
    }

}
