package org.zuma.examplePlugin.domain.gui;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public record IDHolder(int id) implements InventoryHolder {

    @Override
    public Inventory getInventory() {
        return null;
    }
}
