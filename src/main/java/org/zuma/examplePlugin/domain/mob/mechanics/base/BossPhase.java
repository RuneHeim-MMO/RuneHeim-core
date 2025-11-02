package org.zuma.examplePlugin.domain.mob.mechanics.base;

import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BossPhase {
    private final Integer newMaxHealth;
    private final Integer newHealth;
    private final List<WeightedMechanic> weightedMechanics;
    private final MobMechanic transitionMechanic;

    public BossPhase(Integer newMaxHealth, Integer newHealth, List<WeightedMechanic> weightedMechanics, @Nullable MobMechanic transitionMechanic) {
        this.newMaxHealth = newMaxHealth;
        this.newHealth = newHealth;
        this.weightedMechanics = weightedMechanics;
        this.transitionMechanic = transitionMechanic;
    }


    public Integer getNewMaxHealth() {
        return newMaxHealth;
    }

    public Integer getNewHealth() {
        return newHealth;
    }

    public List<WeightedMechanic> getWeightedMechanics() {
        return weightedMechanics;
    }

    @Nullable
    public MobMechanic getTransitionMechanic() {
        return transitionMechanic;
    }
}
