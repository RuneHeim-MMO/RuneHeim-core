package org.zuma.examplePlugin;

import com.ticxo.modelengine.api.ModelEngineAPI;
import com.ticxo.modelengine.api.events.ModelRegistrationEvent;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.zuma.examplePlugin.core.config.ConfigManager;
import org.zuma.examplePlugin.core.database.HikariPool;
import org.zuma.examplePlugin.core.handlers.core.ModelEngineWaiter;
import org.zuma.examplePlugin.domain.entity.EntitySessionManager;
import org.zuma.examplePlugin.core.handlers.core.WebHook;
import org.zuma.examplePlugin.core.handlers.core.ScheduledRestartHandler;
import org.zuma.examplePlugin.core.interfaces.POIMarkerable;
import org.zuma.examplePlugin.core.logger.Logger;
import org.zuma.examplePlugin.core.logger.constants.LogLevel;
import org.zuma.examplePlugin.core.modules.ModuleContext;
import org.zuma.examplePlugin.core.modules.ModuleLoader;
import org.zuma.examplePlugin.core.modules.game.*;
import org.zuma.examplePlugin.core.redis.CatchAllListener;
import org.zuma.examplePlugin.core.redis.GlobalEventHandler;
import org.zuma.examplePlugin.core.redis.RedisConnection;
import org.zuma.examplePlugin.domain.block.BlockManager;
import org.zuma.examplePlugin.domain.block.persistence.InteractableBlockRepository;
import org.zuma.examplePlugin.domain.block.persistence.ResourceNodeTypeRepository;
import org.zuma.examplePlugin.domain.combat.HitHandler;
import org.zuma.examplePlugin.domain.command.ConsoleActionCommand;
import org.zuma.examplePlugin.domain.command.PouchCommand;
import org.zuma.examplePlugin.domain.command.SkillsCommand;
import org.zuma.examplePlugin.domain.dialog.DialogHandler;
import org.zuma.examplePlugin.domain.droptable.DropTableHandler;
import org.zuma.examplePlugin.domain.fairy.FairyHandler;
import org.zuma.examplePlugin.domain.gui.GUIHandler;
import org.zuma.examplePlugin.domain.item.*;
import org.zuma.examplePlugin.domain.item.state.StateManager;
import org.zuma.examplePlugin.domain.map.MapMarkerHandler;
import org.zuma.examplePlugin.domain.mob.*;
import org.zuma.examplePlugin.domain.player.RunePlayerManager;
import org.zuma.examplePlugin.domain.recipe.RecipeHandler;
import org.zuma.examplePlugin.domain.recipe.RecipeRegistry;
import org.zuma.examplePlugin.domain.shop.ShopRegistry;
import org.zuma.examplePlugin.domain.spawnzone.SpawnZoneHandler;
import org.zuma.examplePlugin.domain.spawnzone.base.AbstractSpawnZone;
import org.zuma.examplePlugin.domain.territory.Territory;
import org.zuma.examplePlugin.domain.territory.TerritoryHandler;
import org.zuma.examplePlugin.events.core.NetworkRestartEvent;
import org.zuma.examplePlugin.features.admin.commands.AdminCommand;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@SuppressWarnings("UnstableApiUsage")
public final class RuneQuestPlugin extends JavaPlugin implements Listener {
    private final ModuleContext moduleContext;
    private final ModuleLoader moduleLoader;

    public static final Logger logger = new Logger();
    private static RuneQuestPlugin pluginInstance;

    public RuneQuestPlugin() {
        moduleContext = new ModuleContext();
        moduleLoader = new ModuleLoader(
                List.of(
                        new ItemModule(),
                        new BlockModule(),
                        new GameRulesModule(),
                        new BowModule(),
                        new InteractionSpeedModule(),
                        new DropTableModule(),
                        new AdditionalGearSlotModule(),
                        new PlayerManagerModule()
                ),moduleContext);
    }


    private void registerCommands() {
        this.getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS,
                event -> event.registrar().register("admin", new AdminCommand(
                        moduleContext.get(HitHandler.class),
                        moduleContext.get(InteractableBlockRepository.class),
                        moduleContext.get(ItemRegistry.class), moduleContext.get(RecipeRegistry.class), moduleContext.get(BlockManager.class), new ResourceNodeTypeRepository()))
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
        pluginInstance = this;

        // TODO: despawn all dropped item on restart

        try {
            ConfigManager.initializeConfig();
            moduleLoader.startAll(this);
            registerCommands();

        } catch (Exception e) {
            RuneQuestPlugin.getRuneLogger().log(LogLevel.CRITICAL, "Failed to initialize RuneHeim-core! Shutting down.", e);
            Bukkit.getServer().shutdown();
        }

    }

    @Override
    public void onDisable() {
        getRuneLogger().log(LogLevel.INFO, "Disabling plugin...");

        Bukkit.getScheduler().cancelTasks(this);
        moduleLoader.stopAll(this);

        getRuneLogger().log(LogLevel.INFO, "Plugin disabled.");
    }

    public static Logger getRuneLogger() {
        return logger;
    }

    public static RuneQuestPlugin getInstance() {
        return pluginInstance;
    }

    public static RunePlayerManager getPlayerManager() {
        return pluginInstance.moduleContext.get(RunePlayerManager.class);
    }

    public static ItemRegistry getItemRegistry() {
        return  pluginInstance.moduleContext.get(ItemRegistry.class);
    }

    public static MobRegistry getMobRegistry() {
        return pluginInstance.moduleContext.get(MobRegistry.class);
    }

    public static GUIHandler getGUIHandler() {
        return pluginInstance.moduleContext.get(GUIHandler.class);
    }

    public static RecipeRegistry getRecipeRegistry() {
        return pluginInstance.moduleContext.get(RecipeRegistry.class);
    }

    public static RecipeHandler getRecipeHandler() {
        return pluginInstance.moduleContext.get(RecipeHandler.class);
    }

    public static BlockManager getBlockManager() {
        return pluginInstance.moduleContext.get(BlockManager.class);
    }

    public static StateManager getItemStateManager() {
        return pluginInstance.moduleContext.get(StateManager.class);
    }

    public static ShopRegistry getShopRegistry() {
        return pluginInstance.moduleContext.get(ShopRegistry.class);
    }

    public static DropTableHandler getDropTableHandler() {
        return pluginInstance.moduleContext.get(DropTableHandler.class);
    }

    public static SpawnZoneHandler getSpawnZoneHandler() {
        return pluginInstance.moduleContext.get(SpawnZoneHandler.class);
    }

    public static TerritoryHandler getTerritoryHandler() {
        return pluginInstance.moduleContext.get(TerritoryHandler.class);
    }

    public static DialogHandler getDialogHandler() {
        return pluginInstance.moduleContext.get(DialogHandler.class);
    }

}
