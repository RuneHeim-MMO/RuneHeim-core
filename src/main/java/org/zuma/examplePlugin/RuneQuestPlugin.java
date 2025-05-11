package org.zuma.examplePlugin;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.zuma.examplePlugin.item.ItemHandler;
import org.zuma.examplePlugin.item.ItemListener;
import org.zuma.examplePlugin.item.ItemRegistry;
import org.zuma.examplePlugin.logger.Logger;
import org.zuma.examplePlugin.player.RunePlayerHandler;
import org.zuma.examplePlugin.player.RunePlayerListener;

public final class RuneQuestPlugin extends JavaPlugin implements Listener {
    public static final Logger logger = new Logger();
    private static RuneQuestPlugin pluginInstance;
    private RunePlayerHandler runePlayerHandler;
    private ItemHandler itemHandler;
    private ItemRegistry itemRegistry;

    public void initializePlugin() {
        pluginInstance = this;

        runePlayerHandler = new RunePlayerHandler();
        RunePlayerListener runePlayerListener = new RunePlayerListener(runePlayerHandler);
        getServer().getPluginManager().registerEvents(runePlayerListener, pluginInstance);

        itemRegistry = new ItemRegistry();
        itemHandler = new ItemHandler(itemRegistry);
        ItemListener itemListener = new ItemListener(itemHandler, runePlayerHandler);
        getServer().getPluginManager().registerEvents(itemListener, pluginInstance);

        Bukkit.getOnlinePlayers().forEach((player) -> {
            player.getInventory().addItem(itemRegistry.getItem(0).asItemStack(1));
        });
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

}
