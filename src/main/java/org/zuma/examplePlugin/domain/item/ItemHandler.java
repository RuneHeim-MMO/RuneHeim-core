package org.zuma.examplePlugin.domain.item;

import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.*;
import org.zuma.examplePlugin.core.utils.ItemUtils;
import org.zuma.examplePlugin.core.utils.PlayerUtils;
import org.zuma.examplePlugin.domain.cooldown.CooldownHandler;
import org.zuma.examplePlugin.domain.item.base.AbstractItemType;
import org.zuma.examplePlugin.domain.item.constants.WeaponSpeed;
import org.zuma.examplePlugin.domain.item.state.StateManager;
import org.zuma.examplePlugin.domain.player.RunePlayer;
import org.zuma.examplePlugin.events.item.PlayerArmorEquipEvent;

import javax.annotation.Nullable;

public class ItemHandler {
    private static final long INTERACTION_THROTTLE_INTERVAL = 150L;
    private final CooldownHandler interactionSpeedHandler;


    private final ItemRegistry registry;

    public ItemHandler(ItemRegistry registry) {
        this.registry = registry;
        this.interactionSpeedHandler = new CooldownHandler();
    }

    public ItemRegistry getRegistry() {
        return registry;
    }

    public void onPlayerInteract(PlayerInteractEvent event, AbstractItemType itemInHand, RunePlayer player, StateManager itemStateManager) {
        if (interactionSpeedHandler.isCooledDown(event.getPlayer())) {
            event.setCancelled(true);
            return;
        }

        interactionSpeedHandler.startCooldown(event.getPlayer(), INTERACTION_THROTTLE_INTERVAL);
        itemInHand.onPlayerInteract(event, player, itemStateManager);
    }

    public void onPlayerInteractEntity(PlayerInteractEntityEvent event, AbstractItemType itemInHand, RunePlayer player, StateManager itemStateManager) {
        if (interactionSpeedHandler.isCooledDown(event.getPlayer())) {
            event.setCancelled(true);
            return;
        }

        interactionSpeedHandler.startCooldown(event.getPlayer(), INTERACTION_THROTTLE_INTERVAL);
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
        item.onPlayerPickupItem(event, player, itemStateManager);
    }


    public void onPlayerConsumeItem(PlayerItemConsumeEvent event, AbstractItemType consumedItem, RunePlayer player, StateManager itemStateManager) {
        consumedItem.onPlayerConsumeItem(event, player, itemStateManager);
        event.setCancelled(true); // Prevent vanilla items consumption.
    }
}
