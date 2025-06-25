package org.zuma.examplePlugin.domain.gui;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

import java.util.UUID;

public record IDHolder(int id, UUID instanceUUID) implements InventoryHolder {

    @Override
    public Inventory getInventory() {
        return null;
    }
}
