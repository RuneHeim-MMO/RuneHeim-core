package org.zuma.examplePlugin.domain.item;

import org.bukkit.entity.HumanEntity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jspecify.annotations.Nullable;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.domain.item.base.AbstractItemType;
import org.zuma.examplePlugin.domain.item.handlers.ownership.ItemPickupHandler;
import org.zuma.examplePlugin.domain.item.state.StateManager;
import org.zuma.examplePlugin.domain.player.RunePlayer;
import org.zuma.examplePlugin.events.item.PlayerArmorEquipEvent;

import java.util.Arrays;

public class ItemHandler {
    private final ItemRegistry registry;
    private final ItemPickupHandler pickupHandler;

    public ItemHandler(ItemRegistry registry) {
        this.registry = registry;
        this.pickupHandler = new ItemPickupHandler();
    }

    public ItemRegistry getRegistry() {
        return registry;
    }

    public void onPlayerInteract(PlayerInteractEvent event, AbstractItemType itemInHand, RunePlayer player, StateManager itemStateManager) {
        itemInHand.onPlayerInteract(event, player, itemStateManager);
    }

    public void onPlayerInteractEntity(PlayerInteractEntityEvent event, AbstractItemType itemInHand, RunePlayer player, StateManager itemStateManager) {
        itemInHand.onPlayerInteractEntity(event, player, itemStateManager);
    }

    public void onPlayerItemDrop(PlayerDropItemEvent event, AbstractItemType droppedItem, RunePlayer player, StateManager itemStateManager) {
        droppedItem.onPlayerItemDrop(event, player, itemStateManager);
    }

    public void onPlayerTakeItemInHand(PlayerItemHeldEvent event, AbstractItemType itemInHand, RunePlayer player, StateManager itemStateManager) {
        itemInHand.onPlayerTakeItemInHand(event, player, itemStateManager);
    }

    public void onPlayerDamageEntity(EntityDamageByEntityEvent event, RunePlayer damager, AbstractItemType damagedWith, StateManager itemStateManager) {
        damagedWith.onPlayerDamageEntity(event, damager, itemStateManager);
    }

    public void onPlayerWearItem(PlayerArmorEquipEvent event, RunePlayer player, StateManager itemStateManager) {
        event.getItem().onPlayerWearItem(event, player, itemStateManager);
    }

    public void onPlayerPickupItem(PlayerAttemptPickupItemEvent event, AbstractItemType item, RunePlayer player, StateManager itemStateManager) {
        pickupHandler.onPlayerPickupItem(event, item, player);

        if (event.isCancelled()) {
            return;
        }

        item.onPlayerPickupItem(event, player, itemStateManager);
    }

    @Nullable // MUST RETURN MODIFIED/ORIGINAL ITEM STACK TO MARK AS INTERCEPTED, NULL TO MARK AS IGNORED
    public ItemStack onOtherItemPickupWhileHaving(EntityPickupItemEvent event, AbstractItemType item, ItemStack thisItemStack, RunePlayer player, StateManager itemStateManager) {
        if (event.isCancelled()) {
            return null;
        }

        return item.onOtherItemPickupWhileHaving(event, thisItemStack, player, itemStateManager);
    }


    public void onPlayerConsumeItem(PlayerItemConsumeEvent event, AbstractItemType consumedItem, RunePlayer player, StateManager itemStateManager) {
        consumedItem.onPlayerConsumeItem(event, player, itemStateManager);
        event.setCancelled(true); // Prevent vanilla items consumption.
    }

    public void checkForUnclaimedUntradeablesInInventory(Inventory inventory, HumanEntity player) {
        Arrays.stream(inventory.getContents()).toList().forEach(stack -> {

            if (stack == null) {
                return;
            }

            AbstractItemType type = RuneQuestPlugin.getItemRegistry().getType(stack);

            if (type == null || type.isTradeable) {
                return;
            }

            type.setUntradeableOwnerUUID(stack, player.getUniqueId());

        });


    }


}
