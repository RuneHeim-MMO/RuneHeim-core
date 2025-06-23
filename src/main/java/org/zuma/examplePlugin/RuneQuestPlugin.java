package org.zuma.examplePlugin;

import io.github.cdimascio.dotenv.Dotenv;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.zuma.examplePlugin.core.config.ConfigManager;
import org.zuma.examplePlugin.core.database.HikariPool;
import org.zuma.examplePlugin.core.handlers.*;
import org.zuma.examplePlugin.core.interfaces.POIMarkerable;
import org.zuma.examplePlugin.core.logger.Logger;
import org.zuma.examplePlugin.core.logger.constants.LogLevel;
import org.zuma.examplePlugin.domain.block.BlockFactory;
import org.zuma.examplePlugin.domain.block.BlockListener;
import org.zuma.examplePlugin.domain.block.BlockLoader;
import org.zuma.examplePlugin.domain.block.BlockManager;
import org.zuma.examplePlugin.domain.block.persistence.InteractableBlockRepository;
import org.zuma.examplePlugin.domain.block.persistence.ResourceNodeTypeRepository;
import org.zuma.examplePlugin.domain.combat.HitHandler;
import org.zuma.examplePlugin.domain.combat.HitListener;
import org.zuma.examplePlugin.domain.commands.SkillsCommand;
import org.zuma.examplePlugin.domain.gui.GUIHandler;
import org.zuma.examplePlugin.domain.gui.GUIListener;
import org.zuma.examplePlugin.domain.gui.GUIRegistry;
import org.zuma.examplePlugin.domain.item.ItemFactory;
import org.zuma.examplePlugin.domain.item.ItemHandler;
import org.zuma.examplePlugin.domain.item.ItemListener;
import org.zuma.examplePlugin.domain.item.ItemRegistry;
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
import org.zuma.examplePlugin.domain.recipes.RecipeFactory;
import org.zuma.examplePlugin.domain.recipes.RecipeHandler;
import org.zuma.examplePlugin.domain.recipes.RecipeListener;
import org.zuma.examplePlugin.domain.recipes.RecipeRegistry;
import org.zuma.examplePlugin.domain.recipes.persistence.RecipeLoader;
import org.zuma.examplePlugin.domain.recipes.persistence.RecipeRepository;
import org.zuma.examplePlugin.features.admin.commands.AdminCommand;

import java.util.ArrayList;
import java.util.Collection;

@SuppressWarnings("UnstableApiUsage")
public final class RuneQuestPlugin extends JavaPlugin implements Listener {
    private static final Dotenv dotenv = Dotenv.configure().directory(ConfigManager.ENVIRONMENT_CONFIG.toString()).load();
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

    private MobHandler mobHandler;
    private HikariPool pool;


    private void initializePlugin() {
        pluginInstance = this;

        getServer().getPluginManager().registerEvents(new OffHandDisabler(), pluginInstance);
        getServer().getPluginManager().registerEvents(new BlockProtector(), pluginInstance);
        getServer().getPluginManager().registerEvents(new DefaultMobDisabler(), pluginInstance);
        getServer().getPluginManager().registerEvents(new FireworkDamageDisabler(), pluginInstance);
        getServer().getPluginManager().registerEvents(new DurabilityDisabler(), pluginInstance);

        ConfigManager configManager = new ConfigManager();
        configManager.initializeFolders();

        runePlayerManager = new RunePlayerManager();
        RunePlayerListener runePlayerListener = new RunePlayerListener(runePlayerManager);
        getServer().getPluginManager().registerEvents(runePlayerListener, pluginInstance);

        itemRegistry = new ItemRegistry();
        ItemRepository itemRepository = new ItemRepository();

        ItemLoader itemLoader = new ItemLoader(itemRegistry, new ItemFactory(), itemRepository);
        itemLoader.loadItemsFromDatabase();


        ItemHandler itemHandler = new ItemHandler(itemRegistry);
        StateManager itemStateManager = new StateManager(new StateFactory(), itemHandler, new StateRepository());
        ItemListener itemListener = new ItemListener(itemHandler, runePlayerManager, itemStateManager);
        getServer().getPluginManager().registerEvents(itemListener, pluginInstance);

        GearModifierHandler gearModifierHandler = new GearModifierHandler(itemRegistry);
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

        mobRegistry = new MobRegistry(mobTypeRegistry);
        mobHandler = new MobHandler(mobRegistry);
        MobListener mobListener = new MobListener(runePlayerManager, mobHandler, mobRegistry);
        getServer().getPluginManager().registerEvents(mobListener, pluginInstance);

        recipeRegistry = new RecipeRegistry();
        RecipeLoader recipeLoader = new RecipeLoader(itemRegistry, recipeRegistry, new RecipeRepository(), new RecipeFactory());
        recipeLoader.loadAllRecipes();
        recipeHandler = new RecipeHandler(recipeRegistry, itemRegistry);
        RecipeListener recipeListener = new RecipeListener(recipeHandler);
        getServer().getPluginManager().registerEvents(recipeListener, pluginInstance);

        guiHandler = new GUIHandler(new GUIRegistry(), runePlayerManager);
        GUIListener guiListener = new GUIListener(guiHandler);
        getServer().getPluginManager().registerEvents(guiListener, pluginInstance);
        guiHandler.initializeInventories(blockLoader);

        HitHandler hitHandler = new HitHandler();
        HitListener hitListener = new HitListener(hitHandler, runePlayerManager, mobRegistry);
        getServer().getPluginManager().registerEvents(hitListener, pluginInstance);

        initializeMap();


        // TODO: Load inventory from DB & Verify their meta

    }

    private void initializeMySQL() {
        pool = new HikariPool();
        pool.initialize(dotenv);
    }

    private void initializeMap() {
        markerHandler = new MapMarkerHandler();
        markerHandler.initializeMarkerSets();

        reloadMarkers();
    }

    public static void reloadMarkers() {
        Collection<POIMarkerable> poiMarkers = new ArrayList<>(blockManager.getAllBlocks());

        markerHandler.clearMarkers();
        markerHandler.renderMarkers(poiMarkers);
    }

    private void registerCommands() {
        this.getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS,
                event -> event.registrar().register("admin", new AdminCommand(blockRepository, itemRegistry, recipeRegistry, blockManager, new ResourceNodeTypeRepository()))
        );
        this.getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS,
                event -> event.registrar().register("skills", new SkillsCommand()));

    }


    @Override
    public void onEnable() {
        initializeMySQL();
        initializePlugin();
        registerCommands();
        mobHandler.cleanup();
        runePlayerManager.handleOnEnable();
    }

    @Override
    public void onDisable() {
        getRuneLogger().log(LogLevel.INFO, "Disabling plugin...");
        guiHandler.closeAllInventories();
        blockManager.onDisable();
        pool.closeConnection();
        mobHandler.cleanup();
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


}
