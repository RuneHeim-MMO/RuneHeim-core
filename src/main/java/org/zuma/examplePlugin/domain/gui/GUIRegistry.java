package org.zuma.examplePlugin.domain.gui;

import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.logger.constants.LogLevel;
import org.zuma.examplePlugin.domain.gui.base.AbstractGUI;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.HashMap;

public class GUIRegistry {
    private final HashMap<Integer, AbstractGUI> inventories = new HashMap<>();

    public void registerGUI(AbstractGUI inventory) {
        AbstractGUI existingGUI = getInventoryByID(inventory.getId());

        if (existingGUI != null) {
            RuneQuestPlugin.getRuneLogger().log(LogLevel.WARN, "Cannot register " + inventory.getTitle() + "! Inventory with this ID already exists.");
            return;
        }

        inventories.put(inventory.getId(), inventory);
    }


    @Nullable
    public AbstractGUI getInventoryByID(int id) {
        return inventories.get(id);
    }

    public Collection<AbstractGUI> getAllInventories() {
        return this.inventories.values();
    }
}
