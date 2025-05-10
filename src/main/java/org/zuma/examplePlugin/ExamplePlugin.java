package org.zuma.examplePlugin;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.zuma.examplePlugin.player.RuneQuestPlayerHandler;
import org.zuma.examplePlugin.player.RuneQuestPlayerListener;

public final class ExamplePlugin extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        RuneQuestPlayerHandler playerHandler = new RuneQuestPlayerHandler();
        RuneQuestPlayerListener playerListener = new RuneQuestPlayerListener(playerHandler);

        getServer().getPluginManager().registerEvents(playerListener, this);

        playerHandler.handleReload();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

}
