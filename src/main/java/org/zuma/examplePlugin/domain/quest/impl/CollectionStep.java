package org.zuma.examplePlugin.domain.quest.impl;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.domain.item.ItemRecord;
import org.zuma.examplePlugin.domain.item.base.AbstractItemType;
import org.zuma.examplePlugin.domain.player.RunePlayer;
import org.zuma.examplePlugin.domain.quest.PlayerQuestState;
import org.zuma.examplePlugin.domain.quest.Quest;
import org.zuma.examplePlugin.domain.quest.constants.StepType;
import org.zuma.examplePlugin.domain.quest.base.AbstractQuestStep;
import org.zuma.examplePlugin.events.item.PlayerItemToBeGiven;

import javax.annotation.Nullable;
import java.util.*;

/*
    WARNING: Do not use this step to reliably track player item collections! Players can drop and pick up same item multiple times.
 */
public class CollectionStep extends AbstractQuestStep {
    private final List<ItemRecord> requiredItems;

    public void setAcquiredItems(HashMap<Integer, Integer> acquiredItems) {
        this.acquiredItems = acquiredItems;
    }

    private transient HashMap<Integer, Integer> acquiredItems; // id:amount

    public CollectionStep(RuneQuestPlugin plugin, Quest parentQuest, String description, String stableId, List<ItemRecord> requiredItems, @Nullable UUID owner) {
        super(plugin, parentQuest, StepType.COLLECTION, description, stableId, owner);
        this.requiredItems = requiredItems;
        this.acquiredItems = new HashMap<>();
    }

    public List<ItemRecord> getRequiredItems() {
        return requiredItems;
    }

    public HashMap<Integer, Integer> getAcquiredItems() {
        return this.acquiredItems;
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

        addItemsToAcquired(itemStack, type);
    }

    @EventHandler
    public void onPlayerRetrieveItem(PlayerItemToBeGiven event) {
        if (!event.getPlayer().getUUID().equals(getOwner())) return;

        ItemStack itemStack = event.getItemStack();
        AbstractItemType type = RuneQuestPlugin.getItemRegistry().getType(itemStack);

        if (type == null || !acquiredItems.containsKey(type.id)) {
            return;
        }

        addItemsToAcquired(itemStack, type);
    }

    @EventHandler
    public void onPlayerCloseInventory(InventoryCloseEvent event) {
        if (!(event.getPlayer() instanceof Player player)) return;
        checkPlayerInventoryForExistingItems(player.getUniqueId());
    }

    private void checkPlayerInventoryForExistingItems(UUID playerUUID) {
        Player player = Bukkit.getPlayer(playerUUID);
        if (player == null || !player.isOnline() || !playerUUID.equals(getOwner())) return;

        Arrays.stream(player.getInventory().getContents()).forEach(itemStack -> {
            if (itemStack == null) return;
            AbstractItemType type = RuneQuestPlugin.getItemRegistry().getType(itemStack);

            if (type == null || !acquiredItems.containsKey(type.id)) {
                return;
            }
            
            addItemsToAcquired(itemStack, type);
        });
    }

    private void addItemsToAcquired(ItemStack itemStack, AbstractItemType type) {
        acquiredItems.put(type.id, acquiredItems.get(type.id) + itemStack.getAmount());

        // Check counts
        boolean requirementsFulfilled = acquiredItems.entrySet().stream().allMatch((entry) -> {
            int id = entry.getKey();
            int amount = entry.getValue();

            return requiredItems.stream()
                    .filter(req -> req.getItemId() == id)
                    .findFirst()
                    .map(req -> amount >= req.getAmount())
                    .orElse(false);
        });

        if (requirementsFulfilled) {
            this.handleStepCompletion();
        }
    }

    @Override
    public AbstractQuestStep cloneForPlayer(UUID ownerUUID) {
        CollectionStep step = new CollectionStep(super.getPlugin(), super.getParentQuest(), super.getDescription(), super.getStableId(), requiredItems, ownerUUID);

        HashMap<Integer, Integer> acquiredItems = new HashMap<>();
        requiredItems.forEach(required -> acquiredItems.put(required.getItemId(), 0));
        step.setAcquiredItems(acquiredItems);
        step.setId(this.getId());

        Bukkit.getScheduler().runTaskLater(RuneQuestPlugin.getInstance(), () -> {
            // Initial check in case player already has some required items
            step.checkPlayerInventoryForExistingItems(ownerUUID);
        }, 10L); // Delay to ensure everything is loaded.

        return step;
    }

    @Override
    public AbstractQuestStep assignValuesFromState(PlayerQuestState state) {
        Map<?, ?> rawMap = (Map<?, ?>) state.getStepProgressData().get("acquired_items");
        this.acquiredItems = new HashMap<>();

        for (Map.Entry<?, ?> entry : rawMap.entrySet()) {
            int key = Integer.parseInt(entry.getKey().toString());
            int value = ((Number) entry.getValue()).intValue();
            this.acquiredItems.put(key, value);
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
