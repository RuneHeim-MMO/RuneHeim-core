package org.zuma.examplePlugin.domain.item.impl;

import org.bukkit.event.player.PlayerInteractEvent;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.domain.gui.constants.InventoryId;
import org.zuma.examplePlugin.domain.item.impl.equipable.Tool;
import org.zuma.examplePlugin.domain.item.meta.AbstractItemMeta;
import org.zuma.examplePlugin.domain.item.state.StateManager;
import org.zuma.examplePlugin.domain.player.RunePlayer;

public class CompassItem extends Tool {

    public CompassItem(AbstractItemMeta meta) {
        super(meta);
    }

    @Override
    public void onPlayerInteract(PlayerInteractEvent event, RunePlayer player, StateManager itemStateManager) {
        RuneQuestPlugin.getGUIHandler().openInventory(InventoryId.TERRITORY_INVENTORY.asInt(), player, null,null);
    }

}
