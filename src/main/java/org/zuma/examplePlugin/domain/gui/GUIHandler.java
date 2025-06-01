package org.zuma.examplePlugin.domain.gui;

import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.logger.constants.LogLevel;
import org.zuma.examplePlugin.core.utils.TextUtils;
import org.zuma.examplePlugin.domain.gui.base.AbstractGUI;
import org.zuma.examplePlugin.domain.gui.constants.InventoryId;
import org.zuma.examplePlugin.domain.gui.impl.individual.FurnaceInventory;
import org.zuma.examplePlugin.domain.player.RunePlayer;
import org.zuma.examplePlugin.domain.player.RunePlayerManager;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GUIHandler {
    private final GUIRegistry registry;
    private final RunePlayerManager playerManager;

    public GUIHandler(GUIRegistry registry, RunePlayerManager playerManager) {
        this.registry = registry;
        this.playerManager = playerManager;
    }

    public void closeAllInventories() {
        Bukkit.getOnlinePlayers().forEach(HumanEntity::closeInventory);
    }

    public void initializeInventories() {
        this.registry.registerGUI(new FurnaceInventory("Smelting Furnace", InventoryId.BASIC_FURNACE.asInt(), 1));

        RuneQuestPlugin.getRuneLogger().log(LogLevel.INFO, "Initialized " + this.registry.getAllInventories().size() + " inventories.");
    }

    public void openInventory(InventoryId id, RunePlayer player) {
        AbstractGUI matchingGUI = this.registry.getInventoryByID(id.asInt());

        if (matchingGUI == null) {
            RuneQuestPlugin.getRuneLogger().log(LogLevel.WARN, player.getPlayer().getName() + " attempted to open a non existent inventory with id " + id + "!");
            return;
        }

        matchingGUI.openInventory(player);
    }


    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player bukkitPlayer)) {
            return;
        }

        RunePlayer player = playerManager.getRunePlayer(bukkitPlayer);
        String title = TextUtils.extractPlainText(event.getView().title());
        Integer inventoryId = extractInventoryId(title);

        if (player == null || inventoryId == null) {
            return;
        }

        AbstractGUI matchingGUI = this.registry.getInventoryByID(inventoryId);

        if (matchingGUI == null) {
            return;
        }

        matchingGUI.onInventoryClick(event, event.getRawSlot(), player);
    }

    public Integer extractInventoryId(String input) {
        Pattern pattern = Pattern.compile("\\[(\\d+)]");
        Matcher matcher = pattern.matcher(input);

        if (matcher.find()) {
            return Integer.parseInt(matcher.group(1));
        }

        return null;
    }

}
