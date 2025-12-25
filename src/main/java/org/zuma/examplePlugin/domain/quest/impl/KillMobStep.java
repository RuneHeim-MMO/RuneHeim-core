package org.zuma.examplePlugin.domain.quest.impl;

import org.bukkit.event.EventHandler;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.domain.mob.base.CombatantMob;
import org.zuma.examplePlugin.domain.player.RunePlayer;
import org.zuma.examplePlugin.domain.quest.PlayerQuestState;
import org.zuma.examplePlugin.domain.quest.Quest;
import org.zuma.examplePlugin.domain.quest.base.AbstractQuestStep;
import org.zuma.examplePlugin.domain.quest.constants.StepType;
import org.zuma.examplePlugin.events.combat.RuneEntityDeathEvent;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;


public class KillMobStep extends AbstractQuestStep {
    private final List<KillMobStepEntry> requiredKills;
    private transient HashMap<List<String>, Integer> killsDone; // Mob type ID -> count

    public KillMobStep(RuneQuestPlugin plugin, Quest parentQuest, String description, String stableId, List<KillMobStepEntry> requiredKills, @Nullable UUID owner) {
        super(plugin, parentQuest, StepType.KILL_MOB, description, stableId, owner);
        this.requiredKills = requiredKills;
        this.killsDone = new HashMap<>();
    }

    public float getProgressPercentage() {
        int totalRequired = requiredKills.stream()
                .mapToInt(KillMobStepEntry::requiredAmount)
                .sum();

        if (totalRequired == 0) return 1.0f;

        int totalKilled = requiredKills.stream()
                .mapToInt(req -> {
                    int got = killsDone.getOrDefault(req.mobTypeIds(), 0);
                    return Math.min(got, req.requiredAmount());
                })
                .sum();

        return (float) totalKilled / totalRequired;
    }

    public List<KillMobStepEntry> getRequiredKills() {
        return requiredKills;
    }

    public HashMap<List<String>, Integer> getKillsDone() {
        return this.killsDone;
    }

    public void setKills(HashMap<List<String>, Integer> killsDone) {
        this.killsDone = killsDone;
    }

    @EventHandler
    public void onPlayerKillEntity(RuneEntityDeathEvent event) {
        if (!(event.getAttacker() instanceof RunePlayer player)) return;
        if (!(event.getDefender() instanceof CombatantMob combatantMob)) return;
        if (!player.getUUID().equals(getOwner())) return;

        String mobType = combatantMob.getType();

        // Check if this mob type matches any requirement
        boolean matchesRequirement = requiredKills.stream()
                .anyMatch(entry -> entry.mobTypeIds().contains(mobType));

        if (matchesRequirement) {
            addKills(mobType);
        }
    }

    private void addKills(String mobTypeId) {
        // Find all requirement entries that include this mob type
        for (KillMobStepEntry requirement : requiredKills) {
            if (requirement.mobTypeIds().contains(mobTypeId)) {
                List<String> key = requirement.mobTypeIds();
                int currentCount = killsDone.getOrDefault(key, 0);
                killsDone.put(key, currentCount + 1);
            }
        }

        // Check if all requirements are met
        boolean allComplete = requiredKills.stream()
                .allMatch(req -> killsDone.getOrDefault(req.mobTypeIds(), 0) >= req.requiredAmount());

        super.handleProgressUpdate();

        if (allComplete) {
            this.handleStepCompletion();
        }

    }

    @Override
    public AbstractQuestStep cloneForPlayer(UUID ownerUUID) {
        KillMobStep step = new KillMobStep(super.getPlugin(), super.getParentQuest(), super.getDescription(), super.getStableId(), requiredKills, ownerUUID);

        HashMap<List<String>, Integer> killsDone = new HashMap<>();
        requiredKills.forEach(required -> killsDone.put(required.mobTypeIds, 0));
        step.setKills(killsDone);
        step.setId(this.getId());

        return step;
    }

    @Override
    public AbstractQuestStep assignValuesFromState(PlayerQuestState state) {
        Map<?, ?> rawMap = (Map<?, ?>) state.getStepProgressData().get("kills");
        this.killsDone = new HashMap<>();

        for (Map.Entry<?, ?> entry : rawMap.entrySet()) {
            String raw = entry.getKey().toString();
            List<String> key = List.of(raw.split("="));
            int value = ((Number) entry.getValue()).intValue();
            killsDone.put(key, value);
        }

        return this;
    }

    @Override
    public PlayerQuestState assignValuesToState(PlayerQuestState state) {
        Map<String, Object> toBeMutated = state.getStepProgressData();
        Map<String, Integer> encoded = new HashMap<>();
        for (var entry : killsDone.entrySet()) {
            String key = String.join("=", entry.getKey());
            encoded.put(key, entry.getValue());
        }
        toBeMutated.put("kills", encoded);
        return state;
    }

    public record KillMobStepEntry(List<String> mobTypeIds, int requiredAmount, String displayName) {
    }
}
