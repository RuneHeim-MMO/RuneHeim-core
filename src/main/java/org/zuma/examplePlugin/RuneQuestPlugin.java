package org.zuma.examplePlugin;

import cloud.commandframework.execution.ExecutionCoordinator;
import cloud.commandframework.paper.PaperCommandManager;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.zuma.examplePlugin.commands.AdminCommand;
import org.zuma.examplePlugin.constants.ItemId;
import org.zuma.examplePlugin.item.ItemHandler;
import org.zuma.examplePlugin.item.ItemListener;
import org.zuma.examplePlugin.item.ItemRegistry;
import org.zuma.examplePlugin.logger.Logger;
import org.zuma.examplePlugin.modifier.GearModifierHandler;
import org.zuma.examplePlugin.modifier.GearModifierListener;
import org.zuma.examplePlugin.player.RunePlayerHandler;
import org.zuma.examplePlugin.player.RunePlayerListener;

public final class RuneQuestPlugin extends JavaPlugin implements Listener {
    public static final Logger logger = new Logger();
    private static PaperCommandManager<CommandSender> commandManager;
    private static RuneQuestPlugin pluginInstance;
    private RunePlayerHandler runePlayerHandler;
    private ItemHandler itemHandler;
    private ItemRegistry itemRegistry;

    public void initializePlugin() {
        commandManager = PaperCommandManager.createNative(this, ExecutionCoordinator.simpleCoordinator());
        pluginInstance = this;

        runePlayerHandler = new RunePlayerHandler();
        RunePlayerListener runePlayerListener = new RunePlayerListener(runePlayerHandler);
        getServer().getPluginManager().registerEvents(runePlayerListener, pluginInstance);

        itemRegistry = new ItemRegistry();
        itemHandler = new ItemHandler(itemRegistry);
        ItemListener itemListener = new ItemListener(itemHandler, runePlayerHandler);
        getServer().getPluginManager().registerEvents(itemListener, pluginInstance);

        GearModifierHandler gearModifierHandler = new GearModifierHandler(itemRegistry);
        GearModifierListener gearModifierListener = new GearModifierListener(gearModifierHandler, runePlayerHandler);
        getServer().getPluginManager().registerEvents(gearModifierListener, pluginInstance);


        // TODO: Load inventory from DB & Verify their meta
        Bukkit.getOnlinePlayers().forEach((player) -> {
            player.getInventory().addItem(itemRegistry.getItem(ItemId.HOLY_LOG.asInt()).asItemStack(1));
        });
    }

    public void registerCommands() {
        new AdminCommand();
    }

    @Override
    public void onEnable() {
        initializePlugin();
        runePlayerHandler.handleOnEnable();
    }

    @Override
    public void onDisable() {
        runePlayerHandler.handleOnDisable();
        Bukkit.getScheduler().cancelTasks(this);
    }

    public static Logger getRuneLogger() {
        return logger;
    }

    public static RuneQuestPlugin getInstance() {
        return pluginInstance;
    }

    public static PaperCommandManager<CommandSender> getCommandManager() {
        return commandManager;
    }


}
