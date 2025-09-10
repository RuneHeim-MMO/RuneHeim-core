package org.zuma.examplePlugin.domain.quest.impl;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.domain.item.ItemRecord;
import org.zuma.examplePlugin.domain.item.base.AbstractItemType;
import org.zuma.examplePlugin.domain.quest.PlayerQuestState;
import org.zuma.examplePlugin.domain.quest.Quest;
import org.zuma.examplePlugin.domain.quest.StepType;
import org.zuma.examplePlugin.domain.quest.base.AbstractQuestStep;

import javax.annotation.Nullable;
import java.util.*;

public class CollectionStep extends AbstractQuestStep {
    private final List<ItemRecord> requiredItems;
    private HashMap<Integer, Integer> acquiredItems; // id:amount

    public CollectionStep(RuneQuestPlugin plugin, Quest parentQuest, @Nullable UUID owner, List<ItemRecord> requiredItems) {
        super(plugin, parentQuest, StepType.COLLECTION, owner);
        this.requiredItems = requiredItems;
        this.acquiredItems = new HashMap<>();

        requiredItems.forEach(required -> this.acquiredItems.put(required.getItemId(), 0));

        if (getOwner() != null) {
            plugin.getServer().getPluginManager().registerEvents(this, plugin);
        }
    }

    public List<ItemRecord> getRequiredItems() {
        return requiredItems;
    }

    @EventHandler
    public void onPlayerPickUpEntity(EntityPickupItemEvent event) {
        if (!(event.getEntity() instanceof Player player)) return;
        if (!player.getUniqueId().equals(getOwner())) return;

        ItemStack itemStack = event.getItem().getItemStack();
        AbstractItemType type = RuneQuestPlugin.getItemRegistry().getType(itemStack);

        if (type == null || !acquiredItems.containsKey(type.id)) {
            return;
        }

        acquiredItems.put(type.id, acquiredItems.get(type.id) + itemStack.getAmount());

        // Check counts

        this.handleStepCompletion();
    }

    @Override
    public AbstractQuestStep cloneForPlayer() {
        return new CollectionStep(super.getPlugin(), super.getParentQuest(), super.getOwner(), requiredItems);
    }

    @Override
    public AbstractQuestStep assignValuesFromState(PlayerQuestState state) {
        Map<?, ?> rawMap = (Map<?, ?>) state.getStepProgressData().get("acquired_items");
        this.acquiredItems = new HashMap<>();

        for (Map.Entry<?, ?> entry : rawMap.entrySet()) {
            this.acquiredItems.put((Integer) entry.getKey(), (Integer) entry.getValue());
        }

        return this;
    }

    @Override
    public PlayerQuestState assignValuesToState(PlayerQuestState state) {
        Map<String, Object> toBeMutated = state.getStepProgressData();
        toBeMutated.put("acquired_items", this.acquiredItems);
        return state;
    }
}
