package org.zuma.examplePlugin;

import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.zuma.examplePlugin.admin.commands.AdminCommand;
import org.zuma.examplePlugin.block.BlockListener;
import org.zuma.examplePlugin.block.BlockManager;
import org.zuma.examplePlugin.core.OffHandDisabler;
import org.zuma.examplePlugin.item.ItemHandler;
import org.zuma.examplePlugin.item.ItemListener;
import org.zuma.examplePlugin.item.ItemRegistry;
import org.zuma.examplePlugin.logger.Logger;
import org.zuma.examplePlugin.modifier.GearModifierHandler;
import org.zuma.examplePlugin.modifier.GearModifierListener;
import org.zuma.examplePlugin.player.RunePlayerListener;
import org.zuma.examplePlugin.player.RunePlayerManager;

@SuppressWarnings("UnstableApiUsage")
public final class RuneQuestPlugin extends JavaPlugin implements Listener {
    public static final Logger logger = new Logger();
    private static RuneQuestPlugin pluginInstance;
    private static RunePlayerManager runePlayerManager;
    private static ItemRegistry itemRegistry;
    private ItemHandler itemHandler;
    private BlockManager blockManager;

    public void initializePlugin() {
        pluginInstance = this;

        getServer().getPluginManager().registerEvents(new OffHandDisabler(), pluginInstance);

        runePlayerManager = new RunePlayerManager();
        RunePlayerListener runePlayerListener = new RunePlayerListener(runePlayerManager);
        getServer().getPluginManager().registerEvents(runePlayerListener, pluginInstance);

        itemRegistry = new ItemRegistry();
        itemHandler = new ItemHandler(itemRegistry);
        ItemListener itemListener = new ItemListener(itemHandler, runePlayerManager);
        getServer().getPluginManager().registerEvents(itemListener, pluginInstance);

        GearModifierHandler gearModifierHandler = new GearModifierHandler(itemRegistry);
        GearModifierListener gearModifierListener = new GearModifierListener(gearModifierHandler, runePlayerManager);
        getServer().getPluginManager().registerEvents(gearModifierListener, pluginInstance);

        blockManager = new BlockManager();
        BlockListener blockListener = new BlockListener(blockManager, itemRegistry);
        getServer().getPluginManager().registerEvents(blockListener, pluginInstance);


        // TODO: Load inventory from DB & Verify their meta

    }

    public void registerCommands() {
        this.getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS,
                event -> event.registrar().register("admin", new AdminCommand(itemRegistry, blockManager))
        );
    }


    @Override
    public void onEnable() {
        initializePlugin();
        registerCommands();
        runePlayerManager.handleOnEnable();
    }

    @Override
    public void onDisable() {
        runePlayerManager.handleOnDisable();
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

}
