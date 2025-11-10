package org.zuma.examplePlugin;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.zuma.examplePlugin.core.config.ConfigManager;
import org.zuma.examplePlugin.core.handlers.core.ModelEngineWaiter;
import org.zuma.examplePlugin.core.logger.Logger;
import org.zuma.examplePlugin.core.logger.constants.LogLevel;
import org.zuma.examplePlugin.core.module.ModuleContext;
import org.zuma.examplePlugin.core.module.ModuleLoader;
import org.zuma.examplePlugin.core.module.core.*;
import org.zuma.examplePlugin.core.module.game.*;
import org.zuma.examplePlugin.domain.block.BlockLoader;
import org.zuma.examplePlugin.domain.block.BlockManager;
import org.zuma.examplePlugin.domain.dialog.DialogHandler;
import org.zuma.examplePlugin.domain.droptable.DropTableHandler;
import org.zuma.examplePlugin.domain.gui.GUIHandler;
import org.zuma.examplePlugin.domain.item.*;
import org.zuma.examplePlugin.domain.item.state.StateManager;
import org.zuma.examplePlugin.domain.mob.*;
import org.zuma.examplePlugin.domain.modifier.GearModifierHandler;
import org.zuma.examplePlugin.domain.modifier.GlobalModifierHandler;
import org.zuma.examplePlugin.domain.mount.MountHandler;
import org.zuma.examplePlugin.domain.party.PartyManager;
import org.zuma.examplePlugin.domain.payments.PaymentHandler;
import org.zuma.examplePlugin.domain.player.RunePlayerManager;
import org.zuma.examplePlugin.domain.prayer.PrayerManager;
import org.zuma.examplePlugin.domain.quest.QuestHandler;
import org.zuma.examplePlugin.domain.recipe.RecipeHandler;
import org.zuma.examplePlugin.domain.recipe.RecipeRegistry;
import org.zuma.examplePlugin.domain.shop.ShopRegistry;
import org.zuma.examplePlugin.domain.spawnzone.SpawnZoneHandler;
import org.zuma.examplePlugin.domain.territory.TerritoryHandler;
import org.zuma.examplePlugin.domain.tour.TourHandler;

import java.util.List;

public final class RuneQuestPlugin extends JavaPlugin implements Listener {
    private final ModuleContext moduleContext;
    private final ModuleLoader moduleLoader;

    public static final Logger logger = new Logger();
    private static RuneQuestPlugin pluginInstance;

    public RuneQuestPlugin() {
        moduleContext = new ModuleContext();
        moduleLoader = new ModuleLoader(
                List.of(
                        new DatabaseModule(),
                        new HTTPHookModule(),
                        new InterCommunicationModule(),
                        new MapModule(),
                        new PaymentModule(),

                        new PacketsModule(),
                        new QuestModule(),
                        new AdditionalGearSlotModule(),
                        new BlockModule(),
                        new BowModule(),
                        new CombatModule(),
                        new DialogModule(),
                        new DropTableModule(),
                        new FairyModule(),
                        new GameRulesModule(),
                        new GUIModule(),
                        new InteractionSpeedModule(),
                        new ItemModule(),
                        new MobModule(),
                        new PlayerManagerModule(),
                        new PlayerModifierModule(),
                        new RecipeModule(),
                        new ShopModule(),
                        new SpawnZoneModule(),
                        new TerritoryModule(),
                        new CommandModule(),
                        new MountModule(),
                        new TourModule(),
                        new AmbienceModule(),
                        new GlobalModifierModule(),
                        new PrayerModule(),
                        new PartyModule()
                ), moduleContext);
    }


    @Override
    public void onEnable() {
        pluginInstance = this;

        // TODO: despawn all dropped item on restart

        try {
            ConfigManager.initializeConfig();
            moduleLoader.startAll(this);

            ModelEngineWaiter waiter = new ModelEngineWaiter(() -> {
                moduleContext.get(BlockLoader.class).loadInteractableBlocks();
                RuneQuestPlugin.reloadMarkers();
            }, 30, () -> {
                // Timeout callback, shut down server
                getServer().shutdown();
            });

            this.getServer().getPluginManager().registerEvents(waiter, this);

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

    public static void reloadMarkers() {
        MapModule mapModule = pluginInstance.moduleContext.get(MapModule.class);

        if (mapModule == null) return;

        mapModule.reloadMarkers(pluginInstance.moduleContext);
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
        return pluginInstance.moduleContext.get(ItemRegistry.class);
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

    public static MountHandler getMountHandler() {
        return pluginInstance.moduleContext.get(MountHandler.class);
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

    public static QuestHandler getQuestHandler() {
        return pluginInstance.moduleContext.get(QuestHandler.class);
    }

    public static TourHandler getTourHandler() {
        return pluginInstance.moduleContext.get(TourHandler.class);
    }

    public static GearModifierHandler getGearModifierHandler() {
        return pluginInstance.moduleContext.get(GearModifierHandler.class);
    }

    public static GlobalModifierHandler getGlobalModifierHandler() {
        return pluginInstance.moduleContext.get(GlobalModifierHandler.class);
    }

    public static PrayerManager getPrayerManager() {
        return pluginInstance.moduleContext.get(PrayerManager.class);
    }

    public static PartyManager getPartyManager() {
        return pluginInstance.moduleContext.get(PartyManager.class);
    }

    public static ModuleContext getModuleContext() {
        return pluginInstance.moduleContext;
    }

    public static PaymentHandler getPaymentHandler() {
        return pluginInstance.moduleContext.get(PaymentHandler.class);
    }
}
