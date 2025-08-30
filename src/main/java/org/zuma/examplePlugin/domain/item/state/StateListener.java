package org.zuma.examplePlugin.domain.item.state;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ItemDespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.events.state.StateDeleteEvent;

public class StateListener implements Listener {
    private final StateManager stateManager;

    public StateListener(StateManager stateManager) {
        this.stateManager = stateManager;
    }

    @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
    public void onItemStackDespawn(ItemDespawnEvent event) {
        ItemStack stack = event.getEntity().getItemStack();
        RuneQuestPlugin.getItemStateManager().destroyStateIfExists(stack);
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onStateDelete(StateDeleteEvent event) {
        stateManager.destroyStateIfExists(event.getStateUUID());
    }
}
