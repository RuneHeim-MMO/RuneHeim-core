package org.zuma.examplePlugin.domain.quest.impl;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.domain.item.ItemRecord;
import org.zuma.examplePlugin.domain.item.base.AbstractItemType;
import org.zuma.examplePlugin.domain.quest.PlayerQuestState;
import org.zuma.examplePlugin.domain.quest.Quest;
import org.zuma.examplePlugin.domain.quest.base.AbstractQuestStep;
import org.zuma.examplePlugin.domain.quest.base.Tickable;
import org.zuma.examplePlugin.domain.quest.constants.StepType;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/*
    WARNING: Do not use this step to reliably track player item collections! Players can drop and pick up same item multiple times.
 */
public class CollectionStep extends AbstractQuestStep implements Tickable {
    private final List<ItemRecord> requiredItems;
    private transient HashMap<Integer, Integer> acquiredItems; // id:amount

    public CollectionStep(RuneQuestPlugin plugin, Quest parentQuest, String description, String stableId, List<ItemRecord> requiredItems, @Nullable UUID owner) {
        super(plugin, parentQuest, StepType.COLLECTION, description, stableId, owner);
        this.requiredItems = requiredItems;
        this.acquiredItems = new HashMap<>();

        System.out.println("New instance: " + this.getOwner() + " | " + this.getStableId());

        requiredItems.forEach(required -> acquiredItems.put(required.getItemId(), 0));
    }

    public float getProgressPercentage() {
        int totalRequired = requiredItems.stream()
                .mapToInt(ItemRecord::getAmount)
                .sum();

        if (totalRequired == 0) return 1.0f;

        int totalAcquired = requiredItems.stream()
                .mapToInt(req -> {
                    int id = req.getItemId();
                    int got = acquiredItems.getOrDefault(id, 0);
                    return Math.min(got, req.getAmount());
                })
                .sum();

        return (float) totalAcquired / totalRequired;
    }

    public List<ItemRecord> getRequiredItems() {
        return requiredItems;
    }

    public HashMap<Integer, Integer> getAcquiredItems() {
        return this.acquiredItems;
    }

    @Override
    public AbstractQuestStep cloneForPlayer(UUID ownerUUID) {
        CollectionStep step = new CollectionStep(super.getPlugin(), super.getParentQuest(), super.getDescription(), super.getStableId(), requiredItems, ownerUUID);
        step.setId(this.getId());
        return step;
    }

    @Override
    public AbstractQuestStep assignValuesFromState(PlayerQuestState state) {
        this.acquiredItems = new HashMap<>();

        this.requiredItems.forEach((required) -> {
            this.acquiredItems.put(required.getItemId(), 0);
        });

        return this;
    }

    @Override
    public PlayerQuestState assignValuesToState(PlayerQuestState state) {
        return state;
    }

    @Override
    public void tick() {
        this.requiredItems.forEach((required) -> {
            this.acquiredItems.put(required.getItemId(), 0);
        });

        UUID owner = getOwner();

        if (owner == null) return;

        Player player = Bukkit.getPlayer(owner);

        if (player == null) return;

        HashMap<Integer, Integer> newAcquired = new HashMap<>();

        requiredItems.forEach(req ->
                newAcquired.put(req.getItemId(), 0)
        );

        for (ItemStack itemStack : player.getInventory().getContents()) {
            if (itemStack == null) continue;

            AbstractItemType type = RuneQuestPlugin.getItemRegistry().getType(itemStack);
            if (type == null) continue;

            ItemRecord record = requiredItems.stream()
                    .filter(req -> req.getItemId() == type.id)
                    .findFirst()
                    .orElse(null);

            if (record == null) continue;

            int current = newAcquired.get(type.id);
            int updated = Math.min(current + itemStack.getAmount(), record.getAmount());
            newAcquired.put(type.id, updated);
        }

        this.acquiredItems = newAcquired;

        boolean requirementsFulfilled = acquiredItems.entrySet().stream().allMatch((entry) -> {
            int id = entry.getKey();
            int amount = entry.getValue();

            return requiredItems.stream()
                    .filter(req -> req.getItemId() == id)
                    .findFirst()
                    .map(req -> amount >= req.getAmount())
                    .orElse(false);
        });

        super.handleProgressUpdate();

        if (requirementsFulfilled) {
            this.handleStepCompletion();
        }
    }
}
