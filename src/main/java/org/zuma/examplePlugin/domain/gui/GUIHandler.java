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
import org.zuma.examplePlugin.domain.bank.BankQueryLockController;
import org.zuma.examplePlugin.domain.block.BlockLoader;
import org.zuma.examplePlugin.domain.block.impl.InventoryOpenerBlock;
import org.zuma.examplePlugin.domain.cooldown.CooldownHandler;
import org.zuma.examplePlugin.domain.gui.base.AbstractGUI;
import org.zuma.examplePlugin.domain.gui.constants.GUIOverlay;
import org.zuma.examplePlugin.domain.gui.constants.InventoryId;
import org.zuma.examplePlugin.domain.gui.impl.individual.*;
import org.zuma.examplePlugin.domain.gui.impl.individual.admin.BlockInvCreatorInventory;
import org.zuma.examplePlugin.domain.gui.impl.individual.admin.NodeCreatorInventory;
import org.zuma.examplePlugin.domain.gui.impl.individual.recipe.*;
import org.zuma.examplePlugin.domain.mob.base.AbstractInventoryOpener;
import org.zuma.examplePlugin.domain.player.RunePlayer;
import org.zuma.examplePlugin.domain.player.RunePlayerManager;
import org.zuma.examplePlugin.domain.shop.constants.ShopId;
import org.zuma.examplePlugin.events.inventory.RuneInventoryClick;
import org.zuma.examplePlugin.events.inventory.RuneInventoryCloseEvent;
import org.zuma.examplePlugin.events.inventory.RuneInventoryOpenEvent;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class GUIHandler {
    private final GUIRegistry registry;
    private final RunePlayerManager playerManager;
    // Inventory UUID : List<PlayerUUID>
    private final HashMap<UUID, List<UUID>> inventoryViewers = new HashMap<>();
    private final HashMap<UUID, Runnable> closeRunnables = new HashMap<>();
    private final CooldownHandler cooldownHandler = new CooldownHandler();

    public GUIHandler(GUIRegistry registry, RunePlayerManager playerManager) {
        this.registry = registry;
        this.playerManager = playerManager;
    }

    public GUIRegistry getRegistry() {
        return registry;
    }

    public void closeAllInventories() {
        this.registry.gracefullyForceCloseIndividualInstances();
        Bukkit.getOnlinePlayers().forEach(HumanEntity::closeInventory);
        RuneQuestPlugin.getRuneLogger().log(LogLevel.INFO, "Closing GUI's gracefully.");
    }

    public void initializeInventories(BlockLoader blockLoader, BankQueryLockController bankQueryLockController) {
        this.registry.register(InventoryId.BASIC_FURNACE.asInt(), () -> new FurnaceInventory(GUIOverlay.FURNACE_OVERLAY.asGlyph() + "§f" + Icon.FURNACE.get() + "§f Furnace", InventoryId.BASIC_FURNACE.asInt()));
        this.registry.register(InventoryId.STOVE.asInt(), () -> new CookingInventory(GUIOverlay.COOKING_OVERLAY.asGlyph() + "§f" + Icon.FIRE.get() + "§f Stove", InventoryId.STOVE.asInt()));
        this.registry.register(InventoryId.FIRE.asInt(), () -> new CookingInventory(GUIOverlay.CAMPFIRE_OVERLAY.asGlyph() + "§f" + Icon.FIRE.get() + "§f Campfire", InventoryId.FIRE.asInt()));
        this.registry.register(InventoryId.ANVIL.asInt(), () -> new AnvilInventory(GUIOverlay.ANVIL_OVERLAY.asGlyph() + "§f" + Icon.ANVIL.get() + "§f Anvil", InventoryId.ANVIL.asInt()));
        this.registry.register(InventoryId.CRAFTING_TABLE.asInt(), () -> new CraftingTableInventory(GUIOverlay.CRAFTING_TABLE_OVERLAY.asGlyph() + "§f" + Icon.CRAFTING.get() + "§f Crafting Table", InventoryId.CRAFTING_TABLE.asInt()));
        this.registry.register(InventoryId.FUSION_MASTER.asInt(), () -> new FusionMasterInventory(GUIOverlay.FUSION_MASTER_OVERLAY.asGlyph() + "§f" + Icon.SCROLL_RED.get() + "§f Fusion Master", InventoryId.FUSION_MASTER.asInt()));
        this.registry.register(InventoryId.ADMIN_NODE_CREATE.asInt(), () -> new NodeCreatorInventory("[A] Create Resource Node", InventoryId.ADMIN_NODE_CREATE.asInt(), blockLoader));
        this.registry.register(InventoryId.ADMIN_INTERACTABLE_BLOCK_CREATE.asInt(), () -> new BlockInvCreatorInventory("[A] Create Inventory Opener", InventoryId.ADMIN_INTERACTABLE_BLOCK_CREATE.asInt(), blockLoader, this));
        this.registry.register(InventoryId.GENERAL_STORE.asInt(), () -> new ShopInventory(GUIOverlay.SHOP_OVERLAY.asGlyph() + "§f" + Icon.CHEST.get() + "§f General Store", InventoryId.GENERAL_STORE.asInt(), RuneQuestPlugin.getShopRegistry().getShop(ShopId.GENERAL_STORE.asInt())));
        this.registry.register(InventoryId.SPINNING_WHEEL.asInt(), () -> new SpinningWheelInventory(GUIOverlay.SPINNING_WHEEL_OVERLAY.asGlyph() + "§f" + Icon.MATERIAL.get() + "§f Spinning Wheel", InventoryId.SPINNING_WHEEL.asInt()));
        this.registry.register(InventoryId.DRAKKAR_BLACKSMITH.asInt(), () -> new ShopInventory(GUIOverlay.SHOP_OVERLAY.asGlyph() + "§f" + Icon.TOOL.get() + "§f Blacksmith", InventoryId.DRAKKAR_BLACKSMITH.asInt(), RuneQuestPlugin.getShopRegistry().getShop(ShopId.DRAKKAR_BLACKSMITH.asInt())));
        this.registry.register(InventoryId.BANK.asInt(), () -> new BankInventory(GUIOverlay.BANK_OVERLAY.asGlyph() + "§f" + Icon.MONEY_BAG.get() + "§f Bank", InventoryId.BANK.asInt(), bankQueryLockController));
        this.registry.register(InventoryId.INVESTMENT_BANKER.asInt(), () -> new ShopInventory(GUIOverlay.INVESTMENT_BANKER_OVERLAY.asGlyph() + "§f" + Icon.COIN_PILE.get() + "§f Investment Banker", InventoryId.INVESTMENT_BANKER.asInt(), RuneQuestPlugin.getShopRegistry().getShop(ShopId.INVESTMENT_BANKER.asInt())));
        this.registry.register(InventoryId.JEWELER.asInt(), () -> new ShopInventory(GUIOverlay.JEWELER_SHOP_OVERLAY.asGlyph() + "§f" + Icon.GREEN_GEM.get() + "§f Jewellery Shop", InventoryId.JEWELER.asInt(), RuneQuestPlugin.getShopRegistry().getShop(ShopId.JEWLER.asInt())));
        this.registry.register(InventoryId.XP_LAMP.asInt(), () -> new XPLampInventory(GUIOverlay.XP_INVENTORY_OVERLAY.asGlyph() + "§f" + Icon.EXPERIENCE.get() + "§f XP Lamp", InventoryId.XP_LAMP.asInt()));
        this.registry.register(InventoryId.TERRITORY_INVENTORY.asInt(), () -> new TerritoriesInventory(GUIOverlay.LOCATION_DISCOVERY_OVERLAY.asGlyph() + "§f" + Icon.MAP.get() + "§f Discovered Locations", InventoryId.TERRITORY_INVENTORY.asInt()));

        RuneQuestPlugin.getRuneLogger().log(LogLevel.INFO, "Initialized " + this.registry.getAllInventoryIds().size() + " inventories.");
    }

    @Nullable
    public UUID openInventory(int id, RunePlayer player, @Nullable Runnable onClose) {
        return openInventory(id, player, onClose, null, null);
    }

    @Nullable
    public UUID openInventory(int id, RunePlayer player, @Nullable Runnable onClose, @Nullable AbstractInventoryOpener mobHolder, @Nullable InventoryOpenerBlock blockHolder) {
        AbstractGUI matchingGUI = this.registry.getInstance(player.getUUID(), id);

        if (matchingGUI == null) {
            matchingGUI = this.registry.createInstance(player.getUUID(), id);
        }

        if (matchingGUI == null) {
            RuneQuestPlugin.getRuneLogger().log(LogLevel.WARN, player.asBukkitPlayer().getName() + " attempted to open a non existent inventory instance with id:uuid " + id + ":" + player.getUUID() + "!");
            return null;
        }

        UUID instanceUUID = matchingGUI.getUuid();
        matchingGUI.setMobHolder(mobHolder);
        matchingGUI.setBlockHolder(blockHolder);

        RuneInventoryOpenEvent event = new RuneInventoryOpenEvent(player, matchingGUI, inventoryViewers.get(instanceUUID));
        Bukkit.getServer().getPluginManager().callEvent(event);

        // Revert new instance if inventory failed to open
        if (event.isCancelled() || !matchingGUI.attemptOpenInventory(player, () -> {
            if (onClose != null) {
                onClose.run();
            }
            inventoryViewers.remove(instanceUUID);
            registry.removePlayersActiveInstance(player.getUUID());
        })) {
            if (onClose != null) {
                onClose.run();
            }
            registry.removePlayersActiveInstance(player.getUUID());
            return null;
        }

        if (!inventoryViewers.containsKey(instanceUUID)) {
            inventoryViewers.put(instanceUUID, new ArrayList<>());
        }

        inventoryViewers.get(instanceUUID).add(player.getUUID());

        if (onClose != null) {
            closeRunnables.put(instanceUUID, onClose);
        }

        return matchingGUI.getUuid();
    }

    public void forceCloseOpenedInventories(UUID inventoryUUID) {
        List<UUID> matchingList = inventoryViewers.get(inventoryUUID);

        if (matchingList == null) {
            RuneQuestPlugin.getRuneLogger().log(LogLevel.WARN, "Cannot force close inventories with id " + inventoryUUID + "! This id does not exist in inventoryViewers hashmap.");
            return;
        }

        matchingList.forEach(uuid -> {
            Player player = Bukkit.getPlayer(uuid);

            if (player != null && player.isOnline()) {
                player.closeInventory(InventoryCloseEvent.Reason.UNLOADED);
            }

        });

        Bukkit.getScheduler().runTask(RuneQuestPlugin.getInstance(), () -> inventoryViewers.put(inventoryUUID, new ArrayList<>()));
    }


    public void onInventoryClose(InventoryCloseEvent event) {
        InventoryHolder holder = event.getInventory().getHolder();
        boolean isForced = event.getReason() == InventoryCloseEvent.Reason.UNLOADED;

        if (!(event.getPlayer() instanceof Player bukkitPlayer)) {
            return;
        }

        if (!(holder instanceof IDHolder(int id, UUID instanceUUID))) {
            return;
        }

        RunePlayer player = playerManager.getRunePlayer(bukkitPlayer);

        if (player == null) {
            return;
        }

        if (closeRunnables.get(instanceUUID) != null) {
            closeRunnables.get(instanceUUID).run();
            closeRunnables.remove(instanceUUID);
        }

        List<UUID> viewerUUIDs = inventoryViewers.get(instanceUUID);
        AbstractGUI matchingGUI = this.registry.getInstance(bukkitPlayer.getUniqueId(), id);

        if (matchingGUI != null) {
            matchingGUI.onInventoryClose(event, player);
        }

        registry.removePlayersActiveInstance(bukkitPlayer.getUniqueId());
        Bukkit.getServer().getPluginManager().callEvent(new RuneInventoryCloseEvent(player, id, instanceUUID, viewerUUIDs, isForced));

        if (isForced) {
            inventoryViewers.remove(instanceUUID); // If unloaded, remove instance:viewers k:v
            return;
        }

        viewerUUIDs.remove(player.getUUID());

        if (viewerUUIDs.isEmpty()) {
            inventoryViewers.remove(instanceUUID);
        }
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

        if (cooldownHandler.isCooledDown(player)) {
            event.setCancelled(true);
            return;
        }

        cooldownHandler.startCooldown(player, 250L);

        AbstractGUI matchingGUI = this.registry.getInstance(bukkitPlayer.getUniqueId(), inventoryId);

        if (matchingGUI == null) {
            return;
        }

        RuneInventoryClick runeEvent = new RuneInventoryClick(player, matchingGUI, event);
        Bukkit.getServer().getPluginManager().callEvent(runeEvent);

        if (runeEvent.isCancelled()) {
            event.setCancelled(true);
            return;
        }

        matchingGUI.onInventoryClick(event, event.getRawSlot(), player);
    }


}
