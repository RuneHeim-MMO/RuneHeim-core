package org.zuma.examplePlugin.domain.item;

import net.kyori.adventure.text.Component;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.Inventory;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.domain.hud.HudService;
import org.zuma.examplePlugin.domain.item.base.AbstractItemType;
import org.zuma.examplePlugin.domain.item.state.StateManager;
import org.zuma.examplePlugin.domain.player.RunePlayer;
import org.zuma.examplePlugin.events.item.PlayerArmorEquipEvent;

import java.util.Arrays;
import java.util.UUID;

public class ItemHandler {
    private final ItemRegistry registry;

    public ItemHandler(ItemRegistry registry) {
        this.registry = registry;
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
        if (event.isCancelled()) {
            return;
        }

        if (item.isTradeable) {
            return;
        }

        UUID untradeableOwnerUUID = item.getUntradeableOwnerUUID(event.getItem().getItemStack());

        if (untradeableOwnerUUID == null) {
            item.setUntradeableOwnerUUID(event.getItem().getItemStack(), player.getUUID());
            return;
        }

        if (!player.getUUID().equals(untradeableOwnerUUID)) {
            HudService.sendFailMessage(player, Component.text("Cannot pick up item. It is untradeable and not owned by you."));
            event.setCancelled(true);
        }

        item.onPlayerPickupItem(event, player, itemStateManager);
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
