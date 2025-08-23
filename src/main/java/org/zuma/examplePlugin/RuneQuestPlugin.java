package org.zuma.examplePlugin;

import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.zuma.examplePlugin.core.config.ConfigManager;
import org.zuma.examplePlugin.core.database.HikariPool;
import org.zuma.examplePlugin.core.handlers.core.WebHook;
import org.zuma.examplePlugin.core.handlers.core.ScheduledRestartHandler;
import org.zuma.examplePlugin.core.handlers.game.*;
import org.zuma.examplePlugin.core.interfaces.POIMarkerable;
import org.zuma.examplePlugin.core.logger.Logger;
import org.zuma.examplePlugin.core.logger.constants.LogLevel;
import org.zuma.examplePlugin.core.redis.CatchAllListener;
import org.zuma.examplePlugin.core.redis.GlobalEventHandler;
import org.zuma.examplePlugin.core.redis.RedisConnection;
import org.zuma.examplePlugin.domain.bank.BankContentRepository;
import org.zuma.examplePlugin.domain.bank.BankQueryLockController;
import org.zuma.examplePlugin.domain.block.BlockFactory;
import org.zuma.examplePlugin.domain.block.BlockListener;
import org.zuma.examplePlugin.domain.block.BlockLoader;
import org.zuma.examplePlugin.domain.block.BlockManager;
import org.zuma.examplePlugin.domain.block.persistence.InteractableBlockRepository;
import org.zuma.examplePlugin.domain.block.persistence.ResourceNodeTypeRepository;
import org.zuma.examplePlugin.domain.combat.ranged.BowVelocityHandler;
import org.zuma.examplePlugin.domain.combat.HitHandler;
import org.zuma.examplePlugin.domain.combat.HitListener;
import org.zuma.examplePlugin.domain.combat.melee.VisualMeleeSpeedHandler;
import org.zuma.examplePlugin.domain.command.ConsoleActionCommand;
import org.zuma.examplePlugin.domain.command.PouchCommand;
import org.zuma.examplePlugin.domain.command.SkillsCommand;
import org.zuma.examplePlugin.domain.combat.ranged.BowSpeedHandler;
import org.zuma.examplePlugin.domain.cooldown.InteractionSpeedHandler;
import org.zuma.examplePlugin.domain.combat.melee.WeaponSpeedHandler;
import org.zuma.examplePlugin.domain.dialog.DialogHandler;
import org.zuma.examplePlugin.domain.dialog.DialogListener;
import org.zuma.examplePlugin.domain.droptable.DropTableHandler;
import org.zuma.examplePlugin.domain.droptable.persistence.DropTableRepository;
import org.zuma.examplePlugin.domain.fairy.FairyHandler;
import org.zuma.examplePlugin.domain.fairy.FairyListener;
import org.zuma.examplePlugin.domain.gui.GUIHandler;
import org.zuma.examplePlugin.domain.gui.GUIListener;
import org.zuma.examplePlugin.domain.gui.GUIRegistry;
import org.zuma.examplePlugin.domain.item.*;
import org.zuma.examplePlugin.domain.item.persistence.ItemLoader;
import org.zuma.examplePlugin.domain.item.persistence.ItemRepository;
import org.zuma.examplePlugin.domain.item.state.StateFactory;
import org.zuma.examplePlugin.domain.item.state.StateManager;
import org.zuma.examplePlugin.domain.item.state.StateRepository;
import org.zuma.examplePlugin.domain.map.MapMarkerHandler;
import org.zuma.examplePlugin.domain.mob.*;
import org.zuma.examplePlugin.domain.modifier.GearModifierHandler;
import org.zuma.examplePlugin.domain.modifier.GearModifierListener;
import org.zuma.examplePlugin.domain.player.RunePlayerListener;
import org.zuma.examplePlugin.domain.player.RunePlayerManager;
import org.zuma.examplePlugin.domain.player.inventory.AdditionalGearSlotHandler;
import org.zuma.examplePlugin.domain.player.inventory.AdditionalGearSlotListener;
import org.zuma.examplePlugin.domain.player.persistence.RunePlayerRepository;
import org.zuma.examplePlugin.domain.recipe.RecipeFactory;
import org.zuma.examplePlugin.domain.recipe.RecipeHandler;
import org.zuma.examplePlugin.domain.recipe.RecipeListener;
import org.zuma.examplePlugin.domain.recipe.RecipeRegistry;
import org.zuma.examplePlugin.domain.recipe.persistence.RecipeLoader;
import org.zuma.examplePlugin.domain.recipe.persistence.RecipeRepository;
import org.zuma.examplePlugin.domain.shop.ShopHandler;
import org.zuma.examplePlugin.domain.shop.ShopRegistry;
import org.zuma.examplePlugin.domain.shop.persistence.ShopRepository;
import org.zuma.examplePlugin.domain.spawnzone.SpawnZoneHandler;
import org.zuma.examplePlugin.domain.spawnzone.SpawnZoneListener;
import org.zuma.examplePlugin.domain.spawnzone.base.AbstractSpawnZone;
import org.zuma.examplePlugin.domain.spawnzone.persistence.SpawnZoneRepository;
import org.zuma.examplePlugin.domain.territory.Territory;
import org.zuma.examplePlugin.domain.territory.TerritoryHandler;
import org.zuma.examplePlugin.domain.territory.TerritoryRepository;
import org.zuma.examplePlugin.events.core.NetworkRestartEvent;
import org.zuma.examplePlugin.features.admin.commands.AdminCommand;

import java.util.ArrayList;
import java.util.Collection;

@SuppressWarnings("UnstableApiUsage")
public final class RuneQuestPlugin extends JavaPlugin implements Listener {
    private static final Plugin blueMap = Bukkit.getPluginManager().getPlugin("BlueMap");

    public static final Logger logger = new Logger();
    private static RuneQuestPlugin pluginInstance;
    private static RunePlayerManager runePlayerManager;
    private static ItemRegistry itemRegistry;
    private static MobRegistry mobRegistry;
    private static GUIHandler guiHandler;
    private static RecipeRegistry recipeRegistry;
    private static RecipeHandler recipeHandler;
    private static InteractableBlockRepository blockRepository;
    private static MapMarkerHandler markerHandler;
    private static BlockManager blockManager;
    private static StateManager itemStateManager;
    private static FairyHandler fairyHandler;
    private static ShopRegistry shopRegistry;
    private static DropTableHandler dropTableHandler;
    private static VisualMeleeSpeedHandler visualMeleeSpeedHandler;
    private static SpawnZoneHandler spawnZoneHandler;
    private static TerritoryHandler territoryHandler;
    private static DialogHandler dialogHandler;
    private static HitHandler hitHandler;

    private MobHandler mobHandler;
    private HikariPool pool;


    private void initializePlugin() {
        pluginInstance = this;

        getServer().getPluginManager().registerEvents(new CatchAllListener(), pluginInstance);
        IllegalItemWatchdog illegalItemWatchdog = new IllegalItemWatchdog();

        getServer().getPluginManager().registerEvents(illegalItemWatchdog, pluginInstance);
        getServer().getPluginManager().registerEvents(new InteractionDisabler(), pluginInstance);
        getServer().getPluginManager().registerEvents(new BlockProtector(), pluginInstance);
        getServer().getPluginManager().registerEvents(new DefaultMobDisabler(), pluginInstance);
        getServer().getPluginManager().registerEvents(new VanillaDamageDisabler(), pluginInstance);
        getServer().getPluginManager().registerEvents(new DurabilityDisabler(), pluginInstance);
        getServer().getPluginManager().registerEvents(new HungerLocker(), pluginInstance);

        InteractionSpeedHandler interactionSpeedHandler = new InteractionSpeedHandler();
        WeaponSpeedHandler weaponSpeedHandler = new WeaponSpeedHandler();
        BowSpeedHandler bowSpeedHandler = new BowSpeedHandler();
        BowVelocityHandler bowVelocityHandler = new BowVelocityHandler();
        // BowDrawHelper bowDrawHelper = new BowDrawHelper();

        visualMeleeSpeedHandler = new VisualMeleeSpeedHandler();
        visualMeleeSpeedHandler.startEffectsLoop();

        getServer().getPluginManager().registerEvents(interactionSpeedHandler, pluginInstance);
        getServer().getPluginManager().registerEvents(weaponSpeedHandler, pluginInstance);
        getServer().getPluginManager().registerEvents(bowSpeedHandler, pluginInstance);
        getServer().getPluginManager().registerEvents(bowVelocityHandler, pluginInstance);
        // getServer().getPluginManager().registerEvents(bowDrawHelper, pluginInstance);

        runePlayerManager = new RunePlayerManager(new RunePlayerRepository(pool));
        RunePlayerListener runePlayerListener = new RunePlayerListener(runePlayerManager);
        getServer().getPluginManager().registerEvents(runePlayerListener, pluginInstance);

        dropTableHandler = new DropTableHandler(new DropTableRepository());
        dropTableHandler.initialize();

        itemRegistry = new ItemRegistry();
        ItemRepository itemRepository = new ItemRepository();

        ItemLoader itemLoader = new ItemLoader(itemRegistry, new ItemFactory(), itemRepository);
        itemLoader.loadItemsFromDatabase();

        ItemHandler itemHandler = new ItemHandler(itemRegistry);
        itemStateManager = new StateManager(new StateFactory(), itemHandler, new StateRepository());
        ItemListener itemListener = new ItemListener(itemHandler, runePlayerManager, itemStateManager);
        getServer().getPluginManager().registerEvents(itemListener, pluginInstance);

        AdditionalGearSlotHandler additionalGearSlotHandler = new AdditionalGearSlotHandler();
        AdditionalGearSlotListener additionalGearSlotListener = new AdditionalGearSlotListener(additionalGearSlotHandler);
        getServer().getPluginManager().registerEvents(additionalGearSlotListener, pluginInstance);

        GearModifierHandler gearModifierHandler = new GearModifierHandler(itemRegistry, additionalGearSlotHandler);
        GearModifierListener gearModifierListener = new GearModifierListener(gearModifierHandler, runePlayerManager);
        getServer().getPluginManager().registerEvents(gearModifierListener, pluginInstance);

        blockManager = new BlockManager();
        BlockListener blockListener = new BlockListener(blockManager, itemRegistry);
        getServer().getPluginManager().registerEvents(blockListener, pluginInstance);

        blockRepository = new InteractableBlockRepository(pool);
        BlockLoader blockLoader = new BlockLoader(blockManager, blockRepository, new BlockFactory(), new ResourceNodeTypeRepository());
        blockLoader.fetchAllResourceNodeTypes();
        blockLoader.loadInteractableBlocks();

        MobTypeRegistry mobTypeRegistry = new MobTypeRegistry();
        MobTypesLoader.registerMobs(mobTypeRegistry);

        MobSessionManager mobSessionManager = new MobSessionManager();
        mobRegistry = new MobRegistry(mobTypeRegistry);
        mobHandler = new MobHandler(mobRegistry);
        MobListener mobListener = new MobListener(runePlayerManager, mobHandler, mobRegistry);
        getServer().getPluginManager().registerEvents(mobListener, pluginInstance);
        getServer().getPluginManager().registerEvents(mobSessionManager, pluginInstance);

        recipeRegistry = new RecipeRegistry();
        RecipeLoader recipeLoader = new RecipeLoader(itemRegistry, recipeRegistry, new RecipeRepository(), new RecipeFactory());
        recipeLoader.loadAllRecipes();
        recipeHandler = new RecipeHandler(recipeRegistry);
        RecipeListener recipeListener = new RecipeListener(recipeHandler);
        getServer().getPluginManager().registerEvents(recipeListener, pluginInstance);

        ShopRepository shopRepository = new ShopRepository();
        shopRegistry = new ShopRegistry();
        ShopHandler shopHandler = new ShopHandler(shopRegistry, shopRepository);
        shopHandler.initializeShops();

        territoryHandler = new TerritoryHandler(new TerritoryRepository());
        territoryHandler.initialize();

        guiHandler = new GUIHandler(new GUIRegistry(), runePlayerManager);
        GUIListener guiListener = new GUIListener(guiHandler);
        getServer().getPluginManager().registerEvents(guiListener, pluginInstance);
        guiHandler.initializeInventories(blockLoader, new BankQueryLockController(new BankContentRepository(pool)));

        hitHandler = new HitHandler();
        HitListener hitListener = new HitListener(hitHandler, runePlayerManager, mobRegistry);
        getServer().getPluginManager().registerEvents(hitListener, pluginInstance);

        fairyHandler = new FairyHandler(additionalGearSlotHandler);
        FairyListener fairyListener = new FairyListener(fairyHandler);
        getServer().getPluginManager().registerEvents(fairyListener, pluginInstance);
        fairyHandler.startLoop();

        spawnZoneHandler = new SpawnZoneHandler(new SpawnZoneRepository());
        SpawnZoneListener spawnZoneListener = new SpawnZoneListener(spawnZoneHandler);
        getServer().getPluginManager().registerEvents(spawnZoneListener, pluginInstance);
        spawnZoneHandler.initializeFromDb();
        spawnZoneHandler.startLoop();

        CityGuardHandler cityGuardHandler = new CityGuardHandler();
        getServer().getPluginManager().registerEvents(cityGuardHandler, pluginInstance);

        dialogHandler = new DialogHandler();
        DialogListener dialogListener = new DialogListener(dialogHandler);
        getServer().getPluginManager().registerEvents(dialogListener, pluginInstance);

        getServer().getPluginManager().registerEvents(new ScheduledRestartHandler(), pluginInstance);

        initializeMap();

        // TODO: despawn all dropped item on restart

    }


    private void initializeMySQL() {
        pool = new HikariPool();
        pool.initialize(ConfigManager.getDotenv());
    }

    private void initializeRedis() {
        try {
            RedisConnection.init(ConfigManager.getDotenv());
            GlobalEventHandler.addListener(NetworkRestartEvent.class, e -> ScheduledRestartHandler.scheduleRestart(e.restartAt, e.reason));
        } catch (Throwable t) {
            RuneQuestPlugin.getRuneLogger().log(LogLevel.WARN, "Redis init failed, continuing anyway.", t);
        }
    }

    private void initializeHookServer() {
        if (!ConfigManager.getIsPreviewServer()) {
            RuneQuestPlugin.getRuneLogger().log(LogLevel.INFO, "Skipping hook HTTP server initialization, this is not a preview server.");
            return;
        }

        WebHook.initializeServer(ConfigManager.getDotenv());
    }

    private void initializeMap() {
        if (!ConfigManager.getIsPreviewServer()) {
            RuneQuestPlugin.getRuneLogger().log(LogLevel.INFO, "Skipping BlueMap initialization, this is not a preview server.");
            return;
        }

        if (blueMap == null || !blueMap.isEnabled()) {
            return;
        }

        markerHandler = new MapMarkerHandler();
        markerHandler.initializeMarkerSets();

        reloadMarkers();
    }

    public static void reloadMarkers() {
        if (blueMap == null || !blueMap.isEnabled()) {
            return;
        }

        Collection<POIMarkerable> poiMarkers = new ArrayList<>(blockManager.getAllBlocks());
        Collection<Territory> territories = new ArrayList<>(territoryHandler.getTerritoryList());
        Collection<AbstractSpawnZone> zones = new ArrayList<>(spawnZoneHandler.getZones());

        markerHandler.clearMarkers();
        markerHandler.renderMarkers(poiMarkers);
        markerHandler.renderTerritories(territories);
        markerHandler.renderSpawnZones(zones);
    }

    private void registerCommands() {
        this.getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS,
                event -> event.registrar().register("admin", new AdminCommand(hitHandler, blockRepository, itemRegistry, recipeRegistry, blockManager, new ResourceNodeTypeRepository()))
        );
        this.getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS,
                event -> event.registrar().register("skills", new SkillsCommand()));
        this.getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS,
                event -> event.registrar().register("pouch", new PouchCommand()));

        this.getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS,
                event -> event.registrar().register("actions", new ConsoleActionCommand()));

        RuneQuestPlugin.getRuneLogger().log(LogLevel.INFO, "Registered RuneHeim commands.");
    }


    @Override
    public void onEnable() {
        try {
            ConfigManager.initializeConfig();
            initializeMySQL();
            initializeRedis();
            initializeHookServer();
            initializePlugin();
            registerCommands();
            initializeMap();

            if (mobHandler != null) {
                mobHandler.cleanup();
            }

            if (runePlayerManager != null) {
                runePlayerManager.handleOnEnable();
            }

            if (guiHandler != null) {
                guiHandler.closeAllInventories();
            }
        } catch (Exception e) {
            RuneQuestPlugin.getRuneLogger().log(LogLevel.CRITICAL, "Failed to initialize RuneHeim-core! Shutting down.", e);
            Bukkit.getServer().shutdown();
        }

    }

    @Override
    public void onDisable() {
        getRuneLogger().log(LogLevel.INFO, "Disabling plugin...");

        GlobalEventHandler.clearHandlers();
        RedisConnection.onDisable();
        WebHook.cleanUp();

        if (guiHandler != null) {
            guiHandler.closeAllInventories();
        }

        if (blockManager != null) {
            blockManager.onDisable();
        }

        if (mobHandler != null) {
            mobHandler.cleanup();
        }

        if (fairyHandler != null) {
            fairyHandler.stopLoop();
        }

        if (visualMeleeSpeedHandler != null) {
            visualMeleeSpeedHandler.onDisable();
        }

        if (spawnZoneHandler != null) {
            spawnZoneHandler.onDisable();
        }

        if (runePlayerManager != null) {
            runePlayerManager.handleOnDisable();
        }

        if (pool != null) {
            pool.closeConnection();
        }

        Bukkit.getScheduler().cancelTasks(this);

        getRuneLogger().log(LogLevel.INFO, "Plugin disabled.");
    }

    public static Logger getRuneLogger() {
        return logger;
    }

    public static RuneQuestPlugin getInstance() {
        return pluginInstance;
    }

    public static RunePlayerManager getPlayerManager() {
        return runePlayerManager;
    }

    public static ItemRegistry getItemRegistry() {
        return itemRegistry;
    }

    public static MobRegistry getMobRegistry() {
        return mobRegistry;
    }

    public static GUIHandler getGUIHandler() {
        return guiHandler;
    }

    public static RecipeRegistry getRecipeRegistry() {
        return recipeRegistry;
    }

    public static RecipeHandler getRecipeHandler() {
        return recipeHandler;
    }

    public static BlockManager getBlockManager() {
        return blockManager;
    }

    public static StateManager getItemStateManager() {
        return itemStateManager;
    }

    public static ShopRegistry getShopRegistry() {
        return shopRegistry;
    }

    public static DropTableHandler getDropTableHandler() {
        return dropTableHandler;
    }

    public static SpawnZoneHandler getSpawnZoneHandler() {
        return spawnZoneHandler;
    }

    public static TerritoryHandler getTerritoryHandler() {
        return territoryHandler;
    }

    public static DialogHandler getDialogHandler() {
        return dialogHandler;
    }

}
