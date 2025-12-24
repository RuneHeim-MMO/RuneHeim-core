package org.zuma.examplePlugin.domain.gui;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.constants.Icon;
import org.zuma.examplePlugin.core.interfaces.InventoryOpenerBlock;
import org.zuma.examplePlugin.core.logger.constants.LogLevel;
import org.zuma.examplePlugin.domain.gui.base.StorageInventory;
import org.zuma.examplePlugin.domain.gui.impl.individual.house.HouseBuyingMenu;
import org.zuma.examplePlugin.domain.gui.impl.individual.house.HouseDiscoverer;
import org.zuma.examplePlugin.domain.gui.impl.individual.house.HouseSettings;
import org.zuma.examplePlugin.domain.gui.impl.individual.house.HouseSlotEditor;
import org.zuma.examplePlugin.domain.gui.impl.individual.storage.*;
import org.zuma.examplePlugin.domain.housing.HousingRegion;
import org.zuma.examplePlugin.domain.hud.HudService;
import org.zuma.examplePlugin.domain.storage.RepositoryQueryLockController;
import org.zuma.examplePlugin.domain.block.BlockLoader;
import org.zuma.examplePlugin.domain.cooldown.CooldownHandler;
import org.zuma.examplePlugin.domain.gui.base.AbstractGUI;
import org.zuma.examplePlugin.domain.gui.constants.GUIOverlay;
import org.zuma.examplePlugin.domain.gui.constants.InventoryId;
import org.zuma.examplePlugin.domain.gui.impl.individual.*;
import org.zuma.examplePlugin.domain.gui.impl.individual.admin.*;
import org.zuma.examplePlugin.domain.gui.impl.individual.recipe.*;
import org.zuma.examplePlugin.domain.mob.base.AbstractInventoryOpener;
import org.zuma.examplePlugin.domain.player.RunePlayer;
import org.zuma.examplePlugin.domain.player.RunePlayerManager;
import org.zuma.examplePlugin.domain.shop.constants.ShopId;
import org.zuma.examplePlugin.events.inventory.RuneInventoryClick;
import org.zuma.examplePlugin.events.inventory.RuneInventoryCloseEvent;
import org.zuma.examplePlugin.events.inventory.RuneInventoryOpenEvent;

import javax.annotation.Nullable;
import java.util.*;

public class GUIHandler {
    private final static long INVENTORY_OPEN_COOLDOWN_MS = 500L;
    private final static long INVENTORY_CLICK_COOLDOWN_MS = 150L;

    private final GUIRegistry registry;
    private final RunePlayerManager playerManager;
    private final HashMap<UUID, List<UUID>> inventoryViewers = new HashMap<>();    // Inventory UUID : List<PlayerUUID>
    private final HashMap<UUID, Runnable> closeRunnables = new HashMap<>();
    private final CooldownHandler inventoryOpenCooldownHandler = new CooldownHandler();
    private final CooldownHandler itemClickCooldownHandler = new CooldownHandler();

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

    public void initializeInventories(BlockLoader blockLoader, RepositoryQueryLockController bankContentController, RepositoryQueryLockController storageItemContentController) {

        // Admin
        this.registry.register(InventoryId.ADMIN_NODE_CREATE.asInt(), () -> new NodeCreatorInventory("[A] Create Resource Node", InventoryId.ADMIN_NODE_CREATE.asInt(), blockLoader));
        this.registry.register(InventoryId.ADMIN_INTERACTABLE_BLOCK_CREATE.asInt(), () -> new BlockInvCreatorInventory("[A] Create Inventory Opener", InventoryId.ADMIN_INTERACTABLE_BLOCK_CREATE.asInt(), blockLoader, this));
        this.registry.register(InventoryId.ADMIN_MODELLED_INTERACTABLE_BLOCK_CREATE.asInt(), () -> new ModelledBlockInvCreatorInventory("[A] Create Inventory Opener", InventoryId.ADMIN_MODELLED_INTERACTABLE_BLOCK_CREATE.asInt(), blockLoader, this));
        this.registry.register(InventoryId.ADMIN_ITEM_REGISTRY.asInt(), () -> new ItemRegistry(InventoryId.ADMIN_ITEM_REGISTRY.asInt()));
        this.registry.register(InventoryId.ADMIN_RECIPE_REGISTRY.asInt(), () -> new RecipeRegistry(InventoryId.ADMIN_RECIPE_REGISTRY.asInt()));

        this.registry.register(InventoryId.BASIC_FURNACE.asInt(), () -> new FurnaceInventory(GUIOverlay.FURNACE_OVERLAY.asGlyph() + "§f" + Icon.FURNACE.get() + "§f Furnace", InventoryId.BASIC_FURNACE.asInt()));
        this.registry.register(InventoryId.STOVE.asInt(), () -> new CookingInventory(GUIOverlay.COOKING_OVERLAY.asGlyph() + "§f" + Icon.FIRE.get() + "§f Stove", InventoryId.STOVE.asInt()));
        this.registry.register(InventoryId.FIRE.asInt(), () -> new CookingInventory(GUIOverlay.CAMPFIRE_OVERLAY.asGlyph() + "§f" + Icon.FIRE.get() + "§f Campfire", InventoryId.FIRE.asInt()));
        this.registry.register(InventoryId.ANVIL.asInt(), () -> new AnvilInventory(GUIOverlay.ANVIL_OVERLAY.asGlyph() + "§f" + Icon.ANVIL.get() + "§f Anvil", InventoryId.ANVIL.asInt()));
        this.registry.register(InventoryId.CRAFTING_TABLE.asInt(), () -> new CraftingTableInventory(GUIOverlay.CRAFTING_TABLE_OVERLAY.asGlyph() + "§f" + Icon.CRAFTING.get() + "§f Crafting Table", InventoryId.CRAFTING_TABLE.asInt()));
        this.registry.register(InventoryId.FUSION_MASTER.asInt(), () -> new FusionMasterInventory(GUIOverlay.FUSION_MASTER_OVERLAY.asGlyph() + "§f" + Icon.SCROLL_RED.get() + "§f Fusion Master", InventoryId.FUSION_MASTER.asInt()));
        this.registry.register(InventoryId.DRAKKAR_GENERAL_STORE.asInt(), () -> new ShopInventory(GUIOverlay.GENERAL_STORE_OVERLAY.asGlyph() + "§f" + Icon.CHEST.get() + "§f General Store", InventoryId.DRAKKAR_GENERAL_STORE.asInt(), RuneQuestPlugin.getShopRegistry().getShop(ShopId.DRAKKAR_GENERAL_STORE.asInt())));
        this.registry.register(InventoryId.SPINNING_WHEEL.asInt(), () -> new SpinningWheelInventory(GUIOverlay.SPINNING_WHEEL_OVERLAY.asGlyph() + "§f" + Icon.MATERIAL.get() + "§f Spinning Wheel", InventoryId.SPINNING_WHEEL.asInt()));
        this.registry.register(InventoryId.DRAKKAR_BLACKSMITH.asInt(), () -> new ShopInventory(GUIOverlay.BLACKSMITH_SHOP_OVERLAY.asGlyph() + "§f" + Icon.HAMMER.get() + "§f Blacksmith", InventoryId.DRAKKAR_BLACKSMITH.asInt(), RuneQuestPlugin.getShopRegistry().getShop(ShopId.DRAKKAR_BLACKSMITH.asInt())));
        this.registry.register(InventoryId.BANK.asInt(), () -> new BankInventory(GUIOverlay.BANK_OVERLAY.asGlyph() + "§f" + Icon.DOLLAR.get() + "§f Bank", InventoryId.BANK.asInt(), bankContentController));
        this.registry.register(InventoryId.INVESTMENT_BANKER.asInt(), () -> new ShopInventory(GUIOverlay.INVESTMENT_BANKER_OVERLAY.asGlyph() + "§f" + Icon.COIN_PILE.get() + "§f Investment Banker", InventoryId.INVESTMENT_BANKER.asInt(), RuneQuestPlugin.getShopRegistry().getShop(ShopId.INVESTMENT_BANKER.asInt())));
        this.registry.register(InventoryId.JEWELER.asInt(), () -> new ShopInventory(GUIOverlay.JEWELER_SHOP_OVERLAY.asGlyph() + "§f" + Icon.GREEN_GEM.get() + "§f Jewellery Shop", InventoryId.JEWELER.asInt(), RuneQuestPlugin.getShopRegistry().getShop(ShopId.DRAKKAR_JEWLER.asInt())));
        this.registry.register(InventoryId.XP_LAMP.asInt(), () -> new XPLampInventory(GUIOverlay.XP_INVENTORY_OVERLAY.asGlyph() + "§f" + Icon.EXPERIENCE.get() + "§f XP Lamp", InventoryId.XP_LAMP.asInt()));
        this.registry.register(InventoryId.TERRITORY_INVENTORY.asInt(), () -> new TerritoriesInventory(GUIOverlay.LOCATION_DISCOVERY_OVERLAY.asGlyph() + "§f" + Icon.MAP.get() + "§f Discovered Locations", InventoryId.TERRITORY_INVENTORY.asInt()));
        this.registry.register(InventoryId.CONTRACT_BOARD.asInt(), () -> new ContractBoard(GUIOverlay.CONTRACTS_BOARD_OVERLAY.asGlyph() + "§f" + Icon.CONTRACT_SKULL.get() + "§f Contract Board", InventoryId.CONTRACT_BOARD.asInt()));
        this.registry.register(InventoryId.DRAKKAR_ARMOR_SHOP.asInt(), () -> new ShopInventory(GUIOverlay.ARMOR_SHOP_OVERLAY.asGlyph() + "§f" + Icon.ARMOR.get() + "§f Armor Shop", InventoryId.DRAKKAR_BLACKSMITH.asInt(), RuneQuestPlugin.getShopRegistry().getShop(ShopId.DRAKKAR_ARMOR_SHOP.asInt())));
        this.registry.register(InventoryId.DRAKKAR_FOOD_STALL.asInt(), () -> new ShopInventory(GUIOverlay.FOOD_SHOP_OVERLAY.asGlyph() + "§f" + Icon.FRUIT.get() + "§f Food Stall", InventoryId.DRAKKAR_FOOD_STALL.asInt(), RuneQuestPlugin.getShopRegistry().getShop(ShopId.DRAKKAR_BASIC_FOOD_STALL.asInt())));
        this.registry.register(InventoryId.DRAKKAR_GUARD_ARCHERY_SHOP.asInt(), () -> new ShopInventory(GUIOverlay.ARCHERY_SHOP_OVERLAY.asGlyph() + "§f" + Icon.FRUIT.get() + "§f Town Archer", InventoryId.DRAKKAR_GUARD_ARCHERY_SHOP.asInt(), RuneQuestPlugin.getShopRegistry().getShop(ShopId.DRAKKAR_GUARD_ARCHER_MERCHANT.asInt())));
        this.registry.register(InventoryId.QUESTS_LIST.asInt(), () -> new QuestsInventory(GUIOverlay.QUESTS_INVENTORY_OVERLAY.asGlyph() + "§f" + Icon.BOOK.get() + "§f Quests", InventoryId.QUESTS_LIST.asInt()));


        this.registry.register(InventoryId.MURKWATCH_ARCHERY_SHOP.asInt(), () -> new ShopInventory(GUIOverlay.ARCHERY_SHOP_OVERLAY.asGlyph() + "§f" + Icon.ARROW.get() + "§f Archery Shop", InventoryId.MURKWATCH_ARCHERY_SHOP.asInt(), RuneQuestPlugin.getShopRegistry().getShop(ShopId.MURKWATCH_ARCHERY_MERCHANT.asInt())));
        this.registry.register(InventoryId.MURKWATCH_BLACKSMITH.asInt(), () -> new ShopInventory(GUIOverlay.BLACKSMITH_SHOP_OVERLAY.asGlyph() + "§f" + Icon.HAMMER.get() + "§f Blacksmith", InventoryId.MURKWATCH_BLACKSMITH.asInt(), RuneQuestPlugin.getShopRegistry().getShop(ShopId.MURKWATCH_BLACKSMITH.asInt())));
        this.registry.register(InventoryId.BRINEPORT_FISHING_SHOP.asInt(), () -> new ShopInventory(GUIOverlay.FISHING_SHOP_OVERLAY.asGlyph() + "§f" + Icon.FISHING_HOOK.get() + "§f Fishing Shop", InventoryId.BRINEPORT_FISHING_SHOP.asInt(), RuneQuestPlugin.getShopRegistry().getShop(ShopId.BRINEPORT_FISHING_SHOP.asInt())));
        this.registry.register(InventoryId.FENSHADE_MINING_SHOP.asInt(), () -> new ShopInventory(GUIOverlay.TOOL_SHOP_OVERLAY.asGlyph() + "§f" + Icon.PICKAXE.get() + "§f Mining Shop", InventoryId.FENSHADE_MINING_SHOP.asInt(), RuneQuestPlugin.getShopRegistry().getShop(ShopId.FENSHADE_MINING_SHOP.asInt())));
        this.registry.register(InventoryId.ASHWALE_MINING_SHOP.asInt(), () -> new ShopInventory(GUIOverlay.TOOL_SHOP_OVERLAY.asGlyph() + "§f" + Icon.PICKAXE.get() + "§f Mining Shop", InventoryId.ASHWALE_MINING_SHOP.asInt(), RuneQuestPlugin.getShopRegistry().getShop(ShopId.ASHWALE_MINING_SHOP.asInt())));
        this.registry.register(InventoryId.DRAKKAR_SMELTERY_SHOP.asInt(), () -> new ShopInventory(GUIOverlay.BLACKSMITH_SHOP_OVERLAY.asGlyph() + "§f" + Icon.PICKAXE.get() + "§f Smeltery Shop", InventoryId.DRAKKAR_SMELTERY_SHOP.asInt(), RuneQuestPlugin.getShopRegistry().getShop(ShopId.DRAKKAR_SMELTERY_SHOP.asInt())));
        this.registry.register(InventoryId.ASHWALE_AXE_SHOP.asInt(), () -> new ShopInventory(GUIOverlay.TOOL_SHOP_OVERLAY.asGlyph() + "§f" + Icon.AXE.get() + "§f Axe Shop", InventoryId.ASHWALE_AXE_SHOP.asInt(), RuneQuestPlugin.getShopRegistry().getShop(ShopId.ASHWALE_AXE_SHOP.asInt())));
        this.registry.register(InventoryId.SPOREFELL_FISHING_SHOP.asInt(), () -> new ShopInventory(GUIOverlay.FISHING_SHOP_OVERLAY.asGlyph() + "§f" + Icon.FISHING_HOOK.get() + "§f Fishing Shop", InventoryId.SPOREFELL_FISHING_SHOP.asInt(), RuneQuestPlugin.getShopRegistry().getShop(ShopId.SPOREFELL_FISHING_SHOP.asInt())));
        this.registry.register(InventoryId.SAHARIJIN_FISHING_SHOP.asInt(), () -> new ShopInventory(GUIOverlay.FISHING_SHOP_OVERLAY.asGlyph() + "§f" + Icon.FISHING_HOOK.get() + "§f Fishing Shop", InventoryId.SAHARIJIN_FISHING_SHOP.asInt(), RuneQuestPlugin.getShopRegistry().getShop(ShopId.SAHARIJIN_FISHING_SHOP.asInt())));
        this.registry.register(InventoryId.ELDARWYN_ARMOR_SHOP.asInt(), () -> new ShopInventory(GUIOverlay.ARMOR_SHOP_OVERLAY.asGlyph() + "§f" + Icon.ARMOR.get() + "§f Armor Shop", InventoryId.ELDARWYN_ARMOR_SHOP.asInt(), RuneQuestPlugin.getShopRegistry().getShop(ShopId.ELDARWYN_ARMOR_SHOP.asInt())));
        this.registry.register(InventoryId.INFERNAK_BLACKSMITH.asInt(), () -> new ShopInventory(GUIOverlay.BLACKSMITH_SHOP_OVERLAY.asGlyph() + "§f" + Icon.HAMMER.get() + "§f Blacksmith", InventoryId.INFERNAK_BLACKSMITH.asInt(), RuneQuestPlugin.getShopRegistry().getShop(ShopId.INFERNAK_BLACKSMITH.asInt())));
        this.registry.register(InventoryId.ELDARWYN_GENERAL_STORE.asInt(), () -> new ShopInventory(GUIOverlay.GENERAL_STORE_OVERLAY.asGlyph() + "§f" + Icon.CHEST.get() + "§f General Store", InventoryId.ELDARWYN_GENERAL_STORE.asInt(), RuneQuestPlugin.getShopRegistry().getShop(ShopId.ELDARWYN_GENERAL_STORE.asInt())));
        this.registry.register(InventoryId.BRINEPORT_GENERAL_STORE.asInt(), () -> new ShopInventory(GUIOverlay.GENERAL_STORE_OVERLAY.asGlyph() + "§f" + Icon.CHEST.get() + "§f General Store", InventoryId.BRINEPORT_GENERAL_STORE.asInt(), RuneQuestPlugin.getShopRegistry().getShop(ShopId.BRINEPORT_GENERAL_STORE.asInt())));
        this.registry.register(InventoryId.MURKWATCH_GENERAL_STORE.asInt(), () -> new ShopInventory(GUIOverlay.GENERAL_STORE_OVERLAY.asGlyph() + "§f" + Icon.CHEST.get() + "§f General Store", InventoryId.MURKWATCH_GENERAL_STORE.asInt(), RuneQuestPlugin.getShopRegistry().getShop(ShopId.MURKWATCH_GENERAL_STORE.asInt())));
        this.registry.register(InventoryId.SAKURMEER_GENERAL_STORE.asInt(), () -> new ShopInventory(GUIOverlay.GENERAL_STORE_OVERLAY.asGlyph() + "§f" + Icon.CHEST.get() + "§f General Store", InventoryId.SAKURMEER_GENERAL_STORE.asInt(), RuneQuestPlugin.getShopRegistry().getShop(ShopId.SAKURMEER_GENERAL_STORE.asInt())));
        this.registry.register(InventoryId.AETHERIS_GENERAL_STORE.asInt(), () -> new ShopInventory(GUIOverlay.GENERAL_STORE_OVERLAY.asGlyph() + "§f" + Icon.CHEST.get() + "§f General Store", InventoryId.AETHERIS_GENERAL_STORE.asInt(), RuneQuestPlugin.getShopRegistry().getShop(ShopId.AETHERIS_GENERAL_STORE.asInt())));
        this.registry.register(InventoryId.ISLENOR_GENERAL_STORE.asInt(), () -> new ShopInventory(GUIOverlay.GENERAL_STORE_OVERLAY.asGlyph() + "§f" + Icon.CHEST.get() + "§f General Store", InventoryId.ISLENOR_GENERAL_STORE.asInt(), RuneQuestPlugin.getShopRegistry().getShop(ShopId.ISLENOR_GENERAL_STORE.asInt())));
        this.registry.register(InventoryId.THORNBARK_GENERAL_STORE.asInt(), () -> new ShopInventory(GUIOverlay.GENERAL_STORE_OVERLAY.asGlyph() + "§f" + Icon.CHEST.get() + "§f General Store", InventoryId.THORNBARK_GENERAL_STORE.asInt(), RuneQuestPlugin.getShopRegistry().getShop(ShopId.THORNBARK_GENERAL_STORE.asInt())));
        this.registry.register(InventoryId.SAKURMEER_FOOD_SHOP.asInt(), () -> new ShopInventory(GUIOverlay.FOOD_SHOP_OVERLAY.asGlyph() + "§f" + Icon.FRUIT.get() + "§f Food Shop", InventoryId.SAKURMEER_FOOD_SHOP.asInt(), RuneQuestPlugin.getShopRegistry().getShop(ShopId.SAKURMEER_FOOD_SHOP.asInt())));
        this.registry.register(InventoryId.VALDRIN_WEAPON_SHOP.asInt(), () -> new ShopInventory(GUIOverlay.WEAPON_SHOP_OVERLAY.asGlyph() + "§f" + Icon.SWORD.get() + "§f Weapon Shop", InventoryId.VALDRIN_WEAPON_SHOP.asInt(), RuneQuestPlugin.getShopRegistry().getShop(ShopId.VALDRIN_WEAPON_SHOP.asInt())));
        this.registry.register(InventoryId.TANNER.asInt(), () -> new TannerInventory(GUIOverlay.TANNER_OVERLAY.asGlyph() + "§f" + Icon.LEATHER.get() + "§f Tanner", InventoryId.TANNER.asInt()));
        this.registry.register(InventoryId.FLETCHING_TABLE.asInt(), () -> new FletchingTableInventory(GUIOverlay.FLETCHING_TABLE_OVERLAY.asGlyph() + "§f" + Icon.ARROW.get() + "§f Fletching Table", InventoryId.FLETCHING_TABLE.asInt()));
        this.registry.register(InventoryId.DRAKKAR_TANNER_SHOP.asInt(), () -> new ShopInventory(GUIOverlay.TANNER_SHOP_OVERLAY.asGlyph() + "§f" + Icon.LEATHER.get() + "§f Tanner Shop", InventoryId.DRAKKAR_TANNER_SHOP.asInt(), RuneQuestPlugin.getShopRegistry().getShop(ShopId.DRAKKAR_TANNER_SHOP.asInt())));
        this.registry.register(InventoryId.ZARALI_HERBLORE_SHOP.asInt(), () -> new ShopInventory(GUIOverlay.HERBLORE_SHOP_OVERLAY.asGlyph() + "§f" + Icon.HERBLORE.get() + "§f Herblore Shop", InventoryId.ZARALI_HERBLORE_SHOP.asInt(), RuneQuestPlugin.getShopRegistry().getShop(ShopId.ZARALI_HERBLORE_SHOP.asInt())));
        this.registry.register(InventoryId.SWAMP_ISLAND_HERBLORE_SHOP.asInt(), () -> new ShopInventory(GUIOverlay.HERBLORE_SHOP_OVERLAY.asGlyph() + "§f" + Icon.HERBLORE.get() + "§f Herblore Shop", InventoryId.SWAMP_ISLAND_HERBLORE_SHOP.asInt(), RuneQuestPlugin.getShopRegistry().getShop(ShopId.SWAMP_ISLAND_HERBLORE_SHOP.asInt())));

        this.registry.register(InventoryId.PRAYER_MENU.asInt(), () -> new PrayerMenu(GUIOverlay.PRAYER_MENU_OVERLAY.asGlyph() + "§f" + Icon.PRAYER.get() + "§b Prayer", InventoryId.PRAYER_MENU.asInt()));
        this.registry.register(InventoryId.PARTY_MENU.asInt(), () -> new PartyMenu(GUIOverlay.PARTY_MENU_OVERLAY.asGlyph() + "§f" + Icon.PARTY.get() + "§f Party", InventoryId.PARTY_MENU.asInt()));

        this.registry.register(InventoryId.TOOL_BAG.asInt(), () -> new ToolBagInventory(GUIOverlay.TOOL_BAG_OVERLAY.asGlyph() + "§f" + Icon.STORAGE.get() + "§f Tool Bag", InventoryId.TOOL_BAG.asInt(), storageItemContentController));
        this.registry.register(InventoryId.SMALL_STORAGE_BAG.asInt(), () -> new SmallBagInventory(GUIOverlay.SMALL_BAG_OVERLAY.asGlyph() + "§f" + Icon.STORAGE.get() + "§f Small Bag", InventoryId.SMALL_STORAGE_BAG.asInt(), storageItemContentController));
        this.registry.register(InventoryId.MEDIUM_STORAGE_BAG.asInt(), () -> new MediumBagInventory(GUIOverlay.MEDIUM_BAG_OVERLAY.asGlyph() + "§f" + Icon.STORAGE.get() + "§f Medium Bag", InventoryId.MEDIUM_STORAGE_BAG.asInt(), storageItemContentController));
        this.registry.register(InventoryId.LARGE_STORAGE_BAG.asInt(), () -> new LargeBagInventory(GUIOverlay.LARGE_BAG_OVERLAY.asGlyph() + "§f" + Icon.STORAGE.get() + "§f Large Bag", InventoryId.LARGE_STORAGE_BAG.asInt(), storageItemContentController));

        this.registry.register(InventoryId.BOMBS_MENU.asInt(), () -> new DonatorBombsInventory(GUIOverlay.BOMBS_MENU_OVERLAY.asGlyph() + "§f" + Icon.EXPLOSION.get() + "§f Bombs Menu", InventoryId.BOMBS_MENU.asInt()));
        this.registry.register(InventoryId.PLAYER_SETTINGS_MENU.asInt(), () -> new PlayerSettingsInventory(GUIOverlay.PLAYER_SETTINGS_MENU_OVERLAY.asGlyph() + "§f" + Icon.TOOL.get() + "§f Settings", InventoryId.PLAYER_SETTINGS_MENU.asInt()));
        this.registry.register(InventoryId.DAILY_REWARDS.asInt(), () -> new DailyRewardsInventory(GUIOverlay.DAILY_REWARDS_OVERLAY.asGlyph() + "§f" + Icon.TREASURE_CHEST.get() + "§f Daily Rewards", InventoryId.DAILY_REWARDS.asInt()));
        this.registry.register(InventoryId.PLAYER_MODIFIERS.asInt(), () -> new ModifiersInventory(GUIOverlay.PLAYER_MODIFIERS_OVERLAY.asGlyph() + "§f" + Icon.BOOK.get() + "§f Active Modifiers", InventoryId.PLAYER_MODIFIERS.asInt()));


        this.registry.register(InventoryId.HOUSE_SETTINGS.asInt(), () -> new HouseSettings(GUIOverlay.HOUSE_SETTINGS_OVERLAY.asGlyph() + "§f" + Icon.HOUSE.get() + "§f House Settings", InventoryId.HOUSE_SETTINGS.asInt()));
        this.registry.register(InventoryId.DRAKKAR_HOUSE_DISCOVERER.asInt(), () -> new HouseDiscoverer(GUIOverlay.HOUSES_DISCOVERER_OVERLAY.asGlyph() + "§f" + Icon.HOUSE.get() + "§f " + HousingRegion.DRAKKAR.getName() + " houses", InventoryId.DRAKKAR_HOUSE_DISCOVERER.asInt(), HousingRegion.DRAKKAR));
        this.registry.register(InventoryId.DRAKKAR_BUY_HOUSE.asInt(), () -> new HouseBuyingMenu(GUIOverlay.BUY_HOUSE_OVERLAY.asGlyph() + "§f" + Icon.HOUSE.get() + "§f Buy a house in " + HousingRegion.DRAKKAR.getName(), InventoryId.DRAKKAR_BUY_HOUSE.asInt(), HousingRegion.DRAKKAR));
        this.registry.register(InventoryId.HOUSE_SLOT_EDITOR.asInt(), () -> new HouseSlotEditor(GUIOverlay.HOUSE_SLOT_EDITOR_OVERLAY.asGlyph() + "§fEdit Slot", InventoryId.HOUSE_SLOT_EDITOR.asInt()));

        RuneQuestPlugin.getRuneLogger().log(LogLevel.INFO, "Initialized " + this.registry.getAllInventoryIds().size() + " inventories.");
    }

    @Nullable
    public UUID openInventory(int id, RunePlayer player, @Nullable Runnable onClose) {
        return openInventory(id, player, onClose, null, null);
    }

    @Nullable
    public UUID openInventory(int id, RunePlayer player, @Nullable Runnable onClose, @Nullable AbstractInventoryOpener mobHolder, @Nullable InventoryOpenerBlock blockHolder) {
        if (inventoryOpenCooldownHandler.isCooldownInProgress(player)) {
            return null;
        }

        // If player has other inventory open, close it first
        List<UUID> playerAlreadyViewingInstances = inventoryViewers.entrySet().stream()
                .filter(e -> e.getValue().contains(player.getUUID()))
                .map(Map.Entry::getKey)
                .toList();

        if (!playerAlreadyViewingInstances.isEmpty()) {
            player.asBukkitPlayer().closeInventory();
        }

        inventoryOpenCooldownHandler.startCooldown(player, INVENTORY_OPEN_COOLDOWN_MS);

        AbstractGUI matchingGUI = this.registry.getInstance(player.getUUID(), id);

        if (matchingGUI == null) {
            matchingGUI = this.registry.createInstance(player.getUUID(), id);
            Bukkit.getServer().getPluginManager().registerEvents(matchingGUI, RuneQuestPlugin.getInstance());
        }

        if (matchingGUI == null) {
            RuneQuestPlugin.getRuneLogger().log(LogLevel.WARN, player.asBukkitPlayer().getName() + " attempted to open a non existent inventory instance with id:uuid " + id + ":" + player.getUUID() + "!");
            return null;
        }

        UUID instanceUUID = matchingGUI.getUUID();
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
                HandlerList.unregisterAll(matchingGUI);
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

        return matchingGUI.getUUID();
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

    private void handleCloseInventoryCleanup(RunePlayer player, int typeId, UUID instanceUUID, boolean isForcedGlobally, @Nullable InventoryCloseEvent event) {
        if (closeRunnables.get(instanceUUID) != null) {
            closeRunnables.get(instanceUUID).run();
            closeRunnables.remove(instanceUUID);
        }

        List<UUID> viewerUUIDs = inventoryViewers.get(instanceUUID);
        AbstractGUI matchingGUI = this.registry.getInstance(player.getUUID(), typeId);

        if (matchingGUI != null) {
            matchingGUI.onInventoryClose(event, player);
            HandlerList.unregisterAll(matchingGUI);
        }

        registry.removePlayersActiveInstance(player.getUUID());
        Bukkit.getServer().getPluginManager().callEvent(new RuneInventoryCloseEvent(player, typeId, instanceUUID, viewerUUIDs, isForcedGlobally));

        if (isForcedGlobally) {
            inventoryViewers.remove(instanceUUID); // If unloaded, remove instance:viewers k:v
            return;
        }

        viewerUUIDs.remove(player.getUUID());

        if (viewerUUIDs.isEmpty()) {
            inventoryViewers.remove(instanceUUID);
        }
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

        handleCloseInventoryCleanup(player, id, instanceUUID, isForced, event);
    }

    public void onItemDrop(PlayerDropItemEvent event) {
        Player bukkitPlayer = event.getPlayer();
        Inventory topInventory = bukkitPlayer.getOpenInventory().getTopInventory();

        if (topInventory.getType() == InventoryType.PLAYER) {
            return;
        }

        InventoryHolder holder = topInventory.getHolder();

        if (!(holder instanceof IDHolder idHolder)) {
            return;
        }

        RunePlayer player = playerManager.getRunePlayer(bukkitPlayer);
        int inventoryId = idHolder.id();

        if (player == null) {
            return;
        }

        AbstractGUI matchingGUI = this.registry.getInstance(bukkitPlayer.getUniqueId(), inventoryId);

        if (matchingGUI == null) {
            return;
        }

        matchingGUI.onPlayerDropItem(event, player);
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

        if (itemClickCooldownHandler.isCooldownInProgress(player)) {
            event.setCancelled(true);
            return;
        }

        itemClickCooldownHandler.startCooldown(player, INVENTORY_CLICK_COOLDOWN_MS);

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

    public void onStorageContainerOpenGlobalEvent(UUID containerUUID) {
        List<UUID> playersToClose = new ArrayList<>();

        this.registry.getIndividualInstances().forEach((playerUUID, guiInstance) -> {
            if (!(guiInstance instanceof StorageInventory storageGUI) || storageGUI.getStorageContainerUUID() == null)
                return;

            // Some storage inventory accessing same container uuid has been opened on this/different server.
            if (storageGUI.getStorageContainerUUID().equals(containerUUID)) {
                playersToClose.add(playerUUID);
            }
        });

        playersToClose.forEach(playerUUID -> {
            RunePlayer runePlayer = playerManager.getRunePlayer(playerUUID);

            if (runePlayer == null) return;

            runePlayer.asBukkitPlayer().closeInventory(InventoryCloseEvent.Reason.UNLOADED);
            HudService.sendFailMessage(runePlayer, Component.text("Your storage container was opened from another location. This inventory has been closed to prevent data loss."));
        });
    }


}
