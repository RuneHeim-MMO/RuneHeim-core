package org.zuma.examplePlugin;

import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import org.bukkit.Bukkit;
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
import org.zuma.examplePlugin.player.RunePlayerListener;
import org.zuma.examplePlugin.player.RunePlayerManager;

@SuppressWarnings("UnstableApiUsage")
public final class RuneQuestPlugin extends JavaPlugin implements Listener {
    public static final Logger logger = new Logger();
    private static RuneQuestPlugin pluginInstance;
    private RunePlayerManager runePlayerManager;
    private ItemHandler itemHandler;
    private ItemRegistry itemRegistry;

    public void initializePlugin() {
        pluginInstance = this;

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


        // TODO: Load inventory from DB & Verify their meta
        Bukkit.getOnlinePlayers().forEach((player) -> {
            player.getInventory().addItem(itemRegistry.getItem(ItemId.HOLY_LOG.asInt()).asItemStack(1));
        });
    }

    public void registerCommands() {
        this.getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS,
                event -> event.registrar().register("admin", new AdminCommand(itemRegistry))
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


}
