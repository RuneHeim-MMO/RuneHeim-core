package org.zuma.examplePlugin.domain.quest.impl;

import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.domain.block.impl.CampfireBlock;
import org.zuma.examplePlugin.domain.quest.PlayerQuestState;
import org.zuma.examplePlugin.domain.quest.Quest;
import org.zuma.examplePlugin.domain.quest.base.AbstractQuestStep;
import org.zuma.examplePlugin.domain.quest.constants.StepType;
import org.zuma.examplePlugin.events.block.CampfireStateChanged;

import javax.annotation.Nullable;
import java.util.*;


public class CampfireStep extends AbstractQuestStep {
    private final List<CampfireStepEntry> requiredFires;
    private transient HashMap<String, Integer> litFires; // id:amount

    public record CampfireStepEntry(String zoneId, Location location, int radius, int amount) {
    }

    public CampfireStep(RuneQuestPlugin plugin, Quest parentQuest, String description, String stableId, List<CampfireStepEntry> requiredFires, @Nullable UUID owner) {
        super(plugin, parentQuest, StepType.LIGHT_CAMPFIRE, description, stableId, owner);
        this.requiredFires = requiredFires;
        this.litFires = new HashMap<>();
    }

    public float getProgressPercentage() {
        int totalRequired = requiredFires.stream()
                .mapToInt(CampfireStepEntry::amount)
                .sum();

        if (totalRequired == 0) {
            return 1.0f;
        }

        int totalAcquired = requiredFires.stream()
                .mapToInt(entry -> {
                    int lit = litFires.getOrDefault(entry.zoneId(), 0);
                    return Math.min(lit, entry.amount());
                })
                .sum();

        return (float) totalAcquired / totalRequired;
    }


    public void setLitFires(HashMap<String, Integer> litFires) {
        this.litFires = litFires;
    }

    public List<CampfireStepEntry> getRequiredFires() {
        return requiredFires;
    }

    public HashMap<String, Integer> getLitFires() {
        return this.litFires;
    }

    @EventHandler
    public void onCampfireStateChanged(CampfireStateChanged event) {
        CampfireBlock campfire = event.getCampfire();

        if (!event.getToBeLit() || !campfire.getOwner().equals(this.getOwner())) {
            return;
        }

        Location campfireLocation = campfire.getLocation();
        for (CampfireStepEntry entry : requiredFires) {
            if (campfireLocation.getWorld() != entry.location().getWorld()) {
                continue;
            }

            double distance = campfireLocation.distance(entry.location());
            if (distance <= entry.radius()) {
                this.addCampfiresToLit(entry.zoneId());
            }
        }
    }


    private void addCampfiresToLit(String id) {
        if (!litFires.containsKey(id)) {
            return;
        }

        litFires.put(id, litFires.get(id) + 1);

        // Check counts
        boolean requirementsFulfilled = litFires.entrySet().stream().allMatch((entry) -> {
            String zoneId = entry.getKey();
            int amount = entry.getValue();

            return requiredFires.stream().anyMatch(req -> req.zoneId().equals(zoneId) && amount >= req.amount());
        });

        super.handleProgressUpdate();

        if (requirementsFulfilled) {
            this.handleStepCompletion();
        }

    }

    @Override
    public AbstractQuestStep cloneForPlayer(UUID ownerUUID) {
        CampfireStep step = new CampfireStep(super.getPlugin(), super.getParentQuest(), super.getDescription(), super.getStableId(), requiredFires, ownerUUID);

        HashMap<String, Integer> litFires = new HashMap<>();
        requiredFires.forEach(required -> litFires.put(required.zoneId, 0));
        step.setLitFires(litFires);
        step.setId(this.getId());

        return step;
    }

    @Override
    public AbstractQuestStep assignValuesFromState(PlayerQuestState state) {
        Map<?, ?> rawMap = (Map<?, ?>) state.getStepProgressData().get("lit_fires");
        this.litFires = new HashMap<>();

        for (Map.Entry<?, ?> entry : rawMap.entrySet()) {
            String key = entry.getKey().toString();
            int value = ((Number) entry.getValue()).intValue();
            this.litFires.put(key, value);
        }

        return this;
    }

    @Override
    public PlayerQuestState assignValuesToState(PlayerQuestState state) {
        Map<String, Object> toBeMutated = state.getStepProgressData();
        toBeMutated.put("lit_fires", this.litFires);
        return state;
    }
}
