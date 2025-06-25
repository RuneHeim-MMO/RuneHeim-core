package org.zuma.examplePlugin.domain.gui;

import org.bukkit.Bukkit;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.logger.constants.LogLevel;
import org.zuma.examplePlugin.domain.gui.base.AbstractGUI;
import org.zuma.examplePlugin.domain.gui.base.IndividualGUI;
import org.zuma.examplePlugin.domain.gui.base.SharedGUI;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;

public class GUIRegistry {
    // Inventory ID : Supplier
    private final HashMap<Integer, Supplier<AbstractGUI>> types = new HashMap<>();

    // Player UUID : Instance
    private final HashMap<UUID, AbstractGUI> individualInstances = new HashMap<>();

    // Inventory ID : Instance
    private final HashMap<Integer, AbstractGUI> sharedInstances = new HashMap<>();

    public void register(int id, Supplier<AbstractGUI> supplier) {
        if (types.get(id) != null) {
            RuneQuestPlugin.getRuneLogger().log(LogLevel.WARN, "Cannot register id" + id + " " + supplier.get().getTitle() + "! Inventory with this ID already exists.");
            return;
        }

        AbstractGUI supplierResult = supplier.get();

        if (supplierResult.isShared()) {
            sharedInstances.put(supplierResult.getId(), supplierResult);
        }

        types.put(id, supplier);
    }

    public AbstractGUI getInstance(UUID playerUUID, int id) {
        Supplier<AbstractGUI> supplier = types.get(id);

        Bukkit.getOnlinePlayers().forEach(p -> p.sendMessage(individualInstances.keySet().toString()));


        if (supplier == null) {
            throw new IllegalArgumentException("Unknown inventory id: " + id);
        }

        // Check individual instances first
        AbstractGUI matchingInstance = individualInstances.get(playerUUID);

        if (matchingInstance == null) {
            // Check shared instances if individual map has no result
            matchingInstance = sharedInstances.get(id);
        }

        if (matchingInstance == null || matchingInstance.getId() != id) {
            AbstractGUI newGui = supplier.get();

            if (newGui instanceof IndividualGUI individualGUI) {
                individualGUI.setOwner(playerUUID);
                individualInstances.put(playerUUID, individualGUI);
            } else if (newGui instanceof SharedGUI sharedGUI) {
                sharedInstances.put(id, sharedGUI);
            }

            return newGui;
        }

        return matchingInstance;
    }

    public void removePlayersActiveInstance(UUID playerUUID) {
        individualInstances.remove(playerUUID);
    }

    public List<Integer> getAllInventoryIds() {
        return types.keySet().stream().toList();
    }

}
