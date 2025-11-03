package org.zuma.examplePlugin.domain.mob.mechanics.base;

import org.bukkit.Particle;
import org.bukkit.entity.EntityType;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BossPhase {
    private final Integer newMaxHealth;
    private final Integer newHealth;
    private final List<WeightedMechanic> weightedMechanics;
    private final MobMechanic transitionMechanic;
    private final Particle particles;

    public BossPhase(Integer newMaxHealth, Integer newHealth, List<WeightedMechanic> weightedMechanics, @Nullable MobMechanic transitionMechanic, @Nullable Particle particles) {
        this.newMaxHealth = newMaxHealth;
        this.newHealth = newHealth;
        this.weightedMechanics = weightedMechanics;
        this.transitionMechanic = transitionMechanic;
        this.particles = particles;
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

    @Nullable
    public Particle getParticles() {
        return particles;
    }
}
