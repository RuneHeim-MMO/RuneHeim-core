package org.zuma.examplePlugin.domain.mob.mechanics.traits;

import org.bukkit.entity.LivingEntity;
import org.zuma.examplePlugin.domain.mob.base.AbstractMob;
import org.zuma.examplePlugin.domain.mob.mechanics.base.MobMechanic;

public class MobEnergyMechanic implements MobMechanic {
    private final int maxEnergy, restoreRatePerSecond;
    private int energy;

    public MobEnergyMechanic(int maxEnergy, int initialEnergy, int restoreRatePerSecond) {
        this.maxEnergy = maxEnergy;
        this.energy = initialEnergy;
        this.restoreRatePerSecond = restoreRatePerSecond;
    }

    public void setParent(AbstractMob mob) {

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

    public void execute(LivingEntity target) {
        // No implementation needed for this mechanic
    }

    public int getEnergy() {
        return energy;
    }

    public void setEnergy(int energy) {
        this.energy = Math.min(energy, maxEnergy);
    }

    public int getMaxEnergy() {
        return maxEnergy;
    }

    public int getRegenPerSecond() {
        return restoreRatePerSecond;
    }


}
