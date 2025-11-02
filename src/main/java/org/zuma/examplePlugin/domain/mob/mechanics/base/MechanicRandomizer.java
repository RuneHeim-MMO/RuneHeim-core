package org.zuma.examplePlugin.domain.mob.mechanics.base;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.logger.constants.LogLevel;
import org.zuma.examplePlugin.domain.mob.base.AbstractMob;
import org.zuma.examplePlugin.domain.mob.mechanics.traits.MobEnergyMechanic;

import java.util.*;

public class MechanicRandomizer implements MobMechanic {
    private AbstractMob mob;
    private final List<WeightedMechanic> weightedMechanics;
    private boolean stopped;
    private final List<Candidate> candidates = new ArrayList<>();

    // Internal state & config
    private final Random rnd = new Random();
    private final Map<MobMechanic, Integer> internalCooldownTicks = new HashMap<>(); // mechanic -> ticks remaining
    private final double temperature = 0.7;         // lower => greedier, higher => more random
    private final double utilityExponent = 1.0;     // >1 favors higher utility-per-energy
    private final int lookaheadTicks = 5;           // how many ticks ahead we'll consider "can afford soon"
    private final int targetSearchRadius = 12;      // blocks to search for a LivingEntity target
    private final double saveThreshold = 2.0;       // if top score >> second * this and we lack energy, we'll try to save

    public MechanicRandomizer(List<WeightedMechanic> weightedMechanics) {
        this.weightedMechanics = weightedMechanics;
        this.stopped = false;
    }

    @Override
    public void setParent(AbstractMob mob) {
        this.mob = mob;
        this.weightedMechanics.forEach(we -> we.mechanic().setParent(mob));
    }

    @Override
    public void tick() {
        if (mob == null || stopped) return;
        Entity entity = mob.getEntity();
        if (entity == null) return;

        this.weightedMechanics.forEach(we -> we.mechanic().tick());

        // Find energy mechanic
        MobEnergyMechanic energyMechanics = (MobEnergyMechanic) mob.getMobMechanics().get(MobEnergyMechanic.class);
        if (energyMechanics == null) {
            RuneQuestPlugin.getRuneLogger().log(LogLevel.WARN,
                    "MechanicRandomizer: MobEnergyMechanic not found on mob " + mob.getDisplayName());
            stopped = true;
            return;
        }

        // Decrease internal cooldowns
        if (!internalCooldownTicks.isEmpty()) {
            internalCooldownTicks.replaceAll((k, v) -> v <= 1 ? 0 : v - 1);
            internalCooldownTicks.entrySet().removeIf(e -> e.getValue() == 0);
        }

        int mobEnergy = energyMechanics.getEnergy();
        int assumedRegen = energyMechanics.getRegenPerSecond(); // fallback assume 1 energy per tick

        // Choose a target (nearest living entity, prefer player)
        LivingEntity target = findTarget(entity);

        if (target == null) return;

        // Build candidate list: affordable now OR affordable within lookahead AND not on internal cooldown
        candidates.clear();
        for (WeightedMechanic wm : weightedMechanics) {
            MobMechanic mech = wm.mechanic();
            int cost = wm.cost();

            if (isOnCooldown(mech)) continue; // skip if internal cooldown active

            boolean affordableNow = cost <= mobEnergy;
            boolean affordableSoon = (mobEnergy + assumedRegen * lookaheadTicks) >= cost;

            if (!affordableNow && !affordableSoon) continue;

            // compute a simple heuristic utility: combine weight and cost ratio
            double utilityPerEnergy;
            if (cost == 0) {
                utilityPerEnergy = wm.weight() * 10.0; // free moves are attractive
            } else {
                utilityPerEnergy = (double) Math.max(1, wm.weight()) / Math.max(1, cost);
            }
            double score = wm.weight() * Math.pow(utilityPerEnergy, utilityExponent);

            // skip obviously useless (shouldn't happen since weight>=0)
            if (score <= 0.0) continue;

            candidates.add(new Candidate(wm, mech, cost, score, affordableNow));
        }

        if (candidates.isEmpty()) {
            // No candidate: fallback. Try cheapest available mechanic that is off-cooldown and execute if cost == 0
            Optional<WeightedMechanic> cheap = weightedMechanics.stream()
                    .filter(wm -> !isOnCooldown(wm.mechanic())).min(Comparator.comparingInt(WeightedMechanic::cost));

            return;
        }

        // Sort by score descending for save heuristic
        candidates.sort(Comparator.comparingDouble((Candidate c) -> c.score).reversed());

        // Save heuristic: if top candidate far better than second and it's not affordable now -> skip to regen
        if (candidates.size() >= 2) {
            Candidate top = candidates.get(0);
            Candidate second = candidates.get(1);
            if (!top.affordableNow && top.score > second.score * saveThreshold) {
                // skip action to "save" energy (do nothing, let energy regen)
                return;
            }
        }

        // Softmax sampling of candidates (only use their scores; we already filtered affordability/cooldowns)
        double maxScore = candidates.stream().mapToDouble(c -> c.score).max().orElse(0.0);
        double sumExp = 0.0;
        for (Candidate c : candidates) {
            c.exp = Math.exp((c.score - maxScore) / Math.max(1e-6, temperature));
            sumExp += c.exp;
        }

        double pick = rnd.nextDouble() * sumExp;
        double acc = 0.0;
        Candidate chosen = null;
        for (Candidate c : candidates) {
            acc += c.exp;
            if (pick <= acc) {
                chosen = c;
                break;
            }
        }
        if (chosen == null) chosen = candidates.getFirst(); // safety fallback

        // If chosen is not affordable now (but was affordableSoon), we skip (save)
        if (!chosen.affordableNow) {
            return;
        }

        // Execute chosen mechanic
        try {
            if (target == null) return;
            chosen.wm.mechanic().execute(target);
            System.out.println("Mob " + mob.getDisplayName() + " used mechanic " + chosen.mech.getClass().getSimpleName() + " (cost " + chosen.cost + ", score " + String.format("%.2f", chosen.score) + ", energy left " + (mobEnergy - chosen.cost) + ")");

            if (chosen.wm.executionSound() != null) chosen.wm.executionSound().play(mob.getEntity().getLocation());
            if (chosen.wm.executionMessage() != null) mob.sendMessageToNearbyPlayers(chosen.wm.executionMessage(), 50);

            energyMechanics.setEnergy(energyMechanics.getEnergy() - chosen.cost);
            // start an internal cooldown based on cost (heavier skills cooldown longer)
            int cdTicks = Math.max(10, chosen.cost / 2); // configurable heuristic
            internalCooldownTicks.put(chosen.wm.mechanic(), cdTicks);
        } catch (Exception ex) {
            RuneQuestPlugin.getRuneLogger().log(LogLevel.WARN,
                    "MechanicRandomizer: exception when executing mechanic for mob " + mob.getDisplayName() + ": " + ex.getMessage());
        }
    }

    @Override
    public void execute(LivingEntity target) {
        // This mechanic doesn't implement a single-execute behaviour.
    }

    // --------------------
    // Helpers & utilities
    // --------------------

    private boolean isOnCooldown(MobMechanic mech) {
        Integer cd = internalCooldownTicks.get(mech);
        return cd != null && cd > 0;
    }

    /**
     * Find a reasonable living target for mechanics:
     * - prefer a player in range
     * - otherwise any nearest LivingEntity in radius
     */
    private LivingEntity findTarget(Entity self) {
        if (self instanceof LivingEntity livingEntity) {
            Entity target = livingEntity.getTargetEntity(120, true);

            if (target instanceof LivingEntity) {
                return (LivingEntity) target;
            }
        }
        return null;
    }

    private static class Candidate {
        final WeightedMechanic wm;
        final MobMechanic mech;
        final int cost;
        final double score;
        final boolean affordableNow;
        double exp;

        Candidate(WeightedMechanic wm, MobMechanic mech, int cost, double score, boolean affordableNow) {
            this.wm = wm;
            this.mech = mech;
            this.cost = cost;
            this.score = score;
            this.affordableNow = affordableNow;
        }
    }
}
