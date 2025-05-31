package org.zuma.examplePlugin;

import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.zuma.examplePlugin.core.handlers.BlockProtector;
import org.zuma.examplePlugin.core.handlers.OffHandDisabler;
import org.zuma.examplePlugin.core.logger.Logger;
import org.zuma.examplePlugin.domain.block.BlockFactory;
import org.zuma.examplePlugin.domain.block.BlockListener;
import org.zuma.examplePlugin.domain.block.BlockLoader;
import org.zuma.examplePlugin.domain.block.BlockManager;
import org.zuma.examplePlugin.domain.block.persistence.InteractableBlockRepository;
import org.zuma.examplePlugin.domain.block.persistence.ResourceNodeTypeRepository;
import org.zuma.examplePlugin.domain.item.ItemFactory;
import org.zuma.examplePlugin.domain.item.ItemHandler;
import org.zuma.examplePlugin.domain.item.ItemListener;
import org.zuma.examplePlugin.domain.item.ItemRegistry;
import org.zuma.examplePlugin.domain.item.persistence.ItemLoader;
import org.zuma.examplePlugin.domain.item.persistence.ItemRepository;
import org.zuma.examplePlugin.domain.item.state.StateFactory;
import org.zuma.examplePlugin.domain.item.state.StateManager;
import org.zuma.examplePlugin.domain.item.state.StateRepository;
import org.zuma.examplePlugin.domain.mob.MobHandler;
import org.zuma.examplePlugin.domain.mob.MobListener;
import org.zuma.examplePlugin.domain.mob.MobRegistry;
import org.zuma.examplePlugin.domain.modifier.GearModifierHandler;
import org.zuma.examplePlugin.domain.modifier.GearModifierListener;
import org.zuma.examplePlugin.domain.player.RunePlayerListener;
import org.zuma.examplePlugin.domain.player.RunePlayerManager;
import org.zuma.examplePlugin.domain.recipes.RecipeFactory;
import org.zuma.examplePlugin.domain.recipes.RecipeRegistry;
import org.zuma.examplePlugin.domain.recipes.persistence.RecipeLoader;
import org.zuma.examplePlugin.domain.recipes.persistence.RecipeRepository;
import org.zuma.examplePlugin.features.admin.commands.AdminCommand;

@SuppressWarnings("UnstableApiUsage")
public final class RuneQuestPlugin extends JavaPlugin implements Listener {
    public static final Logger logger = new Logger();
    private static RuneQuestPlugin pluginInstance;
    private static RunePlayerManager runePlayerManager;
    private static ItemRegistry itemRegistry;
    private static MobRegistry mobRegistry;
    private ItemHandler itemHandler;
    private StateManager itemStateManager;
    private BlockManager blockManager;
    private MobHandler mobHandler;
    private RecipeRegistry recipeRegistry;

    public void initializePlugin() {
        pluginInstance = this;

        getServer().getPluginManager().registerEvents(new OffHandDisabler(), pluginInstance);
        getServer().getPluginManager().registerEvents(new BlockProtector(), pluginInstance);

        runePlayerManager = new RunePlayerManager();
        RunePlayerListener runePlayerListener = new RunePlayerListener(runePlayerManager);
        getServer().getPluginManager().registerEvents(runePlayerListener, pluginInstance);

        itemRegistry = new ItemRegistry();
        ItemLoader itemLoader = new ItemLoader(itemRegistry, new ItemFactory(), new ItemRepository());
        itemLoader.loadItemsFromDatabase();

        itemHandler = new ItemHandler(itemRegistry);
        itemStateManager = new StateManager(new StateFactory(), itemHandler, new StateRepository());
        ItemListener itemListener = new ItemListener(itemHandler, runePlayerManager, itemStateManager);
        getServer().getPluginManager().registerEvents(itemListener, pluginInstance);

        GearModifierHandler gearModifierHandler = new GearModifierHandler(itemRegistry);
        GearModifierListener gearModifierListener = new GearModifierListener(gearModifierHandler, runePlayerManager);
        getServer().getPluginManager().registerEvents(gearModifierListener, pluginInstance);

        blockManager = new BlockManager();
        BlockListener blockListener = new BlockListener(blockManager, itemRegistry);
        getServer().getPluginManager().registerEvents(blockListener, pluginInstance);

        BlockLoader blockLoader = new BlockLoader(blockManager, new InteractableBlockRepository(), new BlockFactory(), new ResourceNodeTypeRepository());
        blockLoader.fetchAllResourceNodeTypes();
        blockLoader.loadInteractableBlocks();

        mobRegistry = new MobRegistry();
        mobHandler = new MobHandler(mobRegistry);
        MobListener mobListener = new MobListener(runePlayerManager, mobHandler, mobRegistry);
        getServer().getPluginManager().registerEvents(mobListener, pluginInstance);


        recipeRegistry = new RecipeRegistry();
        RecipeLoader recipeLoader = new RecipeLoader(itemRegistry, recipeRegistry, new RecipeRepository(), new RecipeFactory());
        recipeLoader.loadAllRecipes();

        // TODO: Load inventory from DB & Verify their meta

    }

    public void registerCommands() {
        this.getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS,
                event -> event.registrar().register("admin", new AdminCommand(itemRegistry, recipeRegistry, blockManager, new ResourceNodeTypeRepository()))
        );
    }


    @Override
    public void onEnable() {
        initializePlugin();
        registerCommands();
        mobHandler.cleanup();
        runePlayerManager.handleOnEnable();
    }

    @Override
    public void onDisable() {
        runePlayerManager.handleOnDisable();
        mobHandler.cleanup();
        Bukkit.getScheduler().cancelTasks(this);
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

}
