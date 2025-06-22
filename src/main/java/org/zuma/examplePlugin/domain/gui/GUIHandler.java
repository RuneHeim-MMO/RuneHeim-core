package org.zuma.examplePlugin.domain.gui;

import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class GUIHandler {
    private final GUIRegistry registry;
    private final RunePlayerManager playerManager;
    private final HashMap<Integer, List<UUID>> inventoryViewers = new HashMap<Integer, List<UUID>>();

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

        this.registry.getAllInventories().forEach(inventory -> {
            inventoryViewers.put(inventory.getId(), new ArrayList<>());
        });

        RuneQuestPlugin.getRuneLogger().log(LogLevel.INFO, "Initialized " + this.registry.getAllInventories().size() + " inventories.");
    }

    public void openInventory(int id, RunePlayer player) {
        AbstractGUI matchingGUI = this.registry.getInventoryByID(id);

        if (matchingGUI == null) {
            RuneQuestPlugin.getRuneLogger().log(LogLevel.WARN, player.getPlayer().getName() + " attempted to open a non existent inventory with id " + id + "!");
            return;
        }

        matchingGUI.openInventory(player);
        inventoryViewers.get(id).add(player.getUUID());
    }

    public void forceCloseOpenedInventories(int id) {
        List<UUID> matchingList = inventoryViewers.get(id);

        if (matchingList == null) {
            RuneQuestPlugin.getRuneLogger().log(LogLevel.WARN, "Cannot force close inventories with id " + id + "! This id does not exist in inventoryViewers hashmap.");
            return;
        }

        matchingList.forEach(uuid -> {
            Player player = Bukkit.getPlayer(uuid);

            if (player != null && player.isOnline()) {
                player.closeInventory(InventoryCloseEvent.Reason.UNLOADED);
            }

        });

        Bukkit.getScheduler().runTask(RuneQuestPlugin.getInstance(), () -> inventoryViewers.put(id, new ArrayList<>()));
    }

    public void onInventoryClose(InventoryCloseEvent event) {
        InventoryHolder holder = event.getInventory().getHolder();

        if (event.getReason() == InventoryCloseEvent.Reason.UNLOADED) {
            return;
        }

        if (!(event.getPlayer() instanceof Player bukkitPlayer)) {
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

        inventoryViewers.get(matchingGUI.getId()).remove(player.getUUID());

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
