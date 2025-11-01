package org.zuma.examplePlugin.domain.mob.mechanics;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.zuma.examplePlugin.core.constants.SoundType;
import org.zuma.examplePlugin.core.utils.MobUtils;
import org.zuma.examplePlugin.domain.combat.Hit;
import org.zuma.examplePlugin.domain.combat.constants.AttackType;
import org.zuma.examplePlugin.domain.mob.base.AbstractMob;
import org.zuma.examplePlugin.domain.mob.base.CombatantMob;
import org.zuma.examplePlugin.domain.player.RunePlayer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class MobEnergyMechanic implements MobMechanic {
    private final int maxEnergy, restoreRatePerSecond;
    private int energy;

    public MobEnergyMechanic(int maxEnergy, int initialEnergy, int restoreRatePerSecond) {
        this.maxEnergy = maxEnergy;
        this.energy = initialEnergy;
        this.restoreRatePerSecond = restoreRatePerSecond;
    }

    public void setParent(AbstractMob mob){

    }

    @Override
    public void tick() {
        if (energy < maxEnergy) {
            energy += restoreRatePerSecond;
            if (energy > maxEnergy) {
                energy = maxEnergy;
            }
        }


    }


}
