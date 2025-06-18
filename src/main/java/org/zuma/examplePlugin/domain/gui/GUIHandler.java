package org.zuma.examplePlugin.domain.gui;

import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.InventoryHolder;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.constants.Icon;
import org.zuma.examplePlugin.core.logger.constants.LogLevel;
import org.zuma.examplePlugin.domain.block.BlockLoader;
import org.zuma.examplePlugin.domain.gui.base.AbstractGUI;
import org.zuma.examplePlugin.domain.gui.constants.InventoryId;
import org.zuma.examplePlugin.domain.gui.impl.individual.AnvilInventory;
import org.zuma.examplePlugin.domain.gui.impl.individual.CookingInventory;
import org.zuma.examplePlugin.domain.gui.impl.individual.FurnaceInventory;
import org.zuma.examplePlugin.domain.gui.impl.individual.admin.BlockInvCreatorInventory;
import org.zuma.examplePlugin.domain.gui.impl.individual.admin.NodeCreatorInventory;
import org.zuma.examplePlugin.domain.player.RunePlayer;
import org.zuma.examplePlugin.domain.player.RunePlayerManager;

public class GUIHandler {
    private final GUIRegistry registry;
    private final RunePlayerManager playerManager;

    public GUIHandler(GUIRegistry registry, RunePlayerManager playerManager) {
        this.registry = registry;
        this.playerManager = playerManager;
    }

    public GUIRegistry getRegistry() {
        return registry;
    }

    public void closeAllInventories() {
        Bukkit.getOnlinePlayers().forEach(HumanEntity::closeInventory);
    }

    public void initializeInventories(BlockLoader blockLoader) {
        this.registry.registerGUI(new FurnaceInventory("§f" + Icon.FIRE.get() + " Furnace", InventoryId.BASIC_FURNACE.asInt()));
        this.registry.registerGUI(new CookingInventory("§f" + Icon.FIRE.get() + "§r Stove", InventoryId.STOVE.asInt()));
        this.registry.registerGUI(new CookingInventory("§f" + Icon.FIRE.get() + " Campfire", InventoryId.FIRE.asInt()));
        this.registry.registerGUI(new AnvilInventory("§f" + Icon.ANVIL.get() + " Anvil", InventoryId.ANVIL.asInt()));
        this.registry.registerGUI(new AnvilInventory("§f" + Icon.CRAFTING.get() + " Crafting Table", InventoryId.CRAFTING_TABLE.asInt()));
        this.registry.registerGUI(new NodeCreatorInventory("[A] Create Resource Node", InventoryId.ADMIN_NODE_CREATE.asInt(), blockLoader));
        this.registry.registerGUI(new BlockInvCreatorInventory("[A] Create Inventory Opener", InventoryId.ADMIN_INTERACTABLE_BLOCK_CREATE.asInt(), blockLoader, this));

        RuneQuestPlugin.getRuneLogger().log(LogLevel.INFO, "Initialized " + this.registry.getAllInventories().size() + " inventories.");
    }

    public void openInventory(int id, RunePlayer player) {
        AbstractGUI matchingGUI = this.registry.getInventoryByID(id);

        if (matchingGUI == null) {
            RuneQuestPlugin.getRuneLogger().log(LogLevel.WARN, player.getPlayer().getName() + " attempted to open a non existent inventory with id " + id + "!");
            return;
        }

        matchingGUI.openInventory(player);
    }


    public void onInventoryClick(InventoryClickEvent event) {
        InventoryHolder holder = event.getInventory().getHolder();

        if (!(event.getWhoClicked() instanceof Player bukkitPlayer)) {
            return;
        }

        if (!(holder instanceof IDHolder idHolder)) {
            return;
        }


        RunePlayer player = playerManager.getRunePlayer(bukkitPlayer);
        int inventoryId = idHolder.id();

        if (player == null) {
            return;
        }

        AbstractGUI matchingGUI = this.registry.getInventoryByID(inventoryId);

        if (matchingGUI == null) {
            return;
        }

        matchingGUI.onInventoryClick(event, event.getRawSlot(), player);
    }


}
