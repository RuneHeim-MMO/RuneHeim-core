package org.zuma.examplePlugin.domain.gui;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.logger.constants.LogLevel;
import org.zuma.examplePlugin.domain.gui.base.AbstractGUI;
import org.zuma.examplePlugin.domain.gui.base.IndividualGUI;
import org.zuma.examplePlugin.domain.gui.base.SharedGUI;
import org.zuma.examplePlugin.domain.gui.base.StorageInventory;

import javax.annotation.Nullable;
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
            RuneQuestPlugin.getRuneLogger().log(LogLevel.WARN, "Cannot register id " + id + " " + supplier.get().getTitle() + "! Inventory with this ID already exists.");
            return;
        }

        AbstractGUI supplierResult = supplier.get();

        if (supplierResult.isShared()) {
            sharedInstances.put(supplierResult.getId(), supplierResult);
        }

        types.put(id, supplier);
    }

    @Nullable
    public AbstractGUI getInstance(UUID playerUUID, int id) {
        Supplier<AbstractGUI> supplier = types.get(id);

        if (supplier == null) {
            throw new IllegalArgumentException("Unknown inventory id: " + id);
        }

        // Check individual instances first
        AbstractGUI matchingInstance = individualInstances.get(playerUUID);

        if (matchingInstance == null) {
            // Check shared instances if individual map has no result
            matchingInstance = sharedInstances.get(id);
        }

        return matchingInstance;
    }

    public AbstractGUI createInstance(UUID playerUUID, int id) {
        Supplier<AbstractGUI> supplier = types.get(id);

        if (supplier == null) {
            throw new IllegalArgumentException("Unknown inventory id: " + id);
        }

        AbstractGUI newGui = supplier.get();

        if (newGui instanceof IndividualGUI individualGUI) {
            individualGUI.setOwner(playerUUID);
            individualInstances.put(playerUUID, individualGUI);
        } else if (newGui instanceof SharedGUI sharedGUI) {
            sharedInstances.put(id, sharedGUI);
        }

        return newGui;
    }

    public void removePlayersActiveInstance(UUID playerUUID) {
        individualInstances.remove(playerUUID);
    }

    public List<Integer> getAllInventoryIds() {
        return types.keySet().stream().toList();
    }

    public void gracefullyForceCloseIndividualInstances() {
        individualInstances.forEach((uuid, instance) -> {
            Player player = Bukkit.getPlayer(uuid);

            if (instance instanceof StorageInventory bankGUI) {
                RuneQuestPlugin.getRuneLogger().log(LogLevel.INFO, "Attempting to save " + uuid + "'s bank contents.");
                bankGUI.saveBankSync(true);
                RuneQuestPlugin.getRuneLogger().log(LogLevel.INFO, uuid + "'s bank contents saved successfully.");
            }

            if (player == null) {
                // Too bad.
                RuneQuestPlugin.getRuneLogger().log(LogLevel.CRITICAL, "Couldn't forward onInventoryClose event to GUI instance! Player with uuid " + uuid + " not found.");
                return;
            }


            instance.onInventoryClose(null, RuneQuestPlugin.getPlayerManager().getRunePlayer(player));
            player.closeInventory();

        });
    }

}
