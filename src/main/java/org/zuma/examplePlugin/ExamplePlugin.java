package org.zuma.examplePlugin;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.zuma.examplePlugin.player.RuneQuestPlayerHandler;
import org.zuma.examplePlugin.player.RuneQuestPlayerListener;

public final class ExamplePlugin extends JavaPlugin implements Listener {
    private RuneQuestPlayerHandler playerHandler;

    @Override
    public void onEnable() {
        playerHandler = new RuneQuestPlayerHandler(this);

        RuneQuestPlayerListener playerListener = new RuneQuestPlayerListener(playerHandler);
        getServer().getPluginManager().registerEvents(playerListener, this);

        playerHandler.handleOnEnable();
    }

    @Override
    public void onDisable() {
        playerHandler.handleOnDisable();
    }

}
