package org.zuma.examplePlugin;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.zuma.examplePlugin.item.ItemHandler;
import org.zuma.examplePlugin.item.ItemListener;
import org.zuma.examplePlugin.logger.Logger;
import org.zuma.examplePlugin.player.RuneQuestPlayerHandler;
import org.zuma.examplePlugin.player.RuneQuestPlayerListener;

public final class RuneQuestPlugin extends JavaPlugin implements Listener {
    public final Logger logger = new Logger();
    private RuneQuestPlayerHandler runePlayerHandler;
    private ItemHandler itemHandler;

    public void initializePlugin() {
        runePlayerHandler = new RuneQuestPlayerHandler(this);
        RuneQuestPlayerListener runePlayerListener = new RuneQuestPlayerListener(runePlayerHandler);
        getServer().getPluginManager().registerEvents(runePlayerListener, this);

        itemHandler = new ItemHandler(this, runePlayerHandler);
        ItemListener itemListener = new ItemListener(itemHandler);
        getServer().getPluginManager().registerEvents(itemListener, this);
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

    public Logger getRuneLogger() {
        return logger;
    }

}
