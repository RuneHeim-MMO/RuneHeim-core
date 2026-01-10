package org.zuma.examplePlugin.core.module.core;


import org.bukkit.plugin.Plugin;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.config.ConfigManager;
import org.zuma.examplePlugin.core.handlers.core.ScheduledRestartHandler;
import org.zuma.examplePlugin.core.logger.constants.LogLevel;
import org.zuma.examplePlugin.core.module.ModuleContext;
import org.zuma.examplePlugin.core.module.PluginModule;
import org.zuma.examplePlugin.core.redis.CatchAllListener;
import org.zuma.examplePlugin.core.redis.GlobalEventHandler;
import org.zuma.examplePlugin.core.redis.RedisConnection;
import org.zuma.examplePlugin.domain.daily.DailyRewardHandler;
import org.zuma.examplePlugin.domain.gui.GUIHandler;
import org.zuma.examplePlugin.domain.modifier.scopes.server.ServerModifierHandler;
import org.zuma.examplePlugin.domain.payments.handlers.DonationHandler;
import org.zuma.examplePlugin.events.drops.DailyMegaRareClaimEvent;
import org.zuma.examplePlugin.events.core.NetworkRestartEvent;
import org.zuma.examplePlugin.events.drops.RareDropEvent;
import org.zuma.examplePlugin.events.inventory.RuneStorageInventoryOpenEvent;
import org.zuma.examplePlugin.events.payment.*;

import java.util.List;

public class InterCommunicationModule implements PluginModule {

    @Override
    public List<Class<?>> dependencies() {
        return List.of(GUIHandler.class);
    }

    @Override
    public void start(Plugin plugin, ModuleContext context) {
        initializeRedis(context);

        plugin.getServer().getPluginManager().registerEvents(new CatchAllListener(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new ScheduledRestartHandler(), plugin);
    }

    private void initializeRedis(ModuleContext context) {
        try {
            RedisConnection.init(ConfigManager.getDotenv());
            GlobalEventHandler.registerEvent(RuneStorageInventoryOpenEvent.class, RuneStorageInventoryOpenEvent.Payload.class);
            GlobalEventHandler.registerEvent(SupportLevelQueryEvent.class, SupportLevelQueryEvent.Payload.class);
            GlobalEventHandler.registerEvent(RankReloadEvent.class, RankReloadEvent.Payload.class);
            GlobalEventHandler.registerEvent(BombContainerReloadEvent.class, BombContainerReloadEvent.Payload.class);
            GlobalEventHandler.registerEvent(BombActivatedEvent.class, BombActivatedEvent.Payload.class);
            GlobalEventHandler.registerEvent(SupportLevelUpgradeEvent.class, SupportLevelUpgradeEvent.Payload.class);
            GlobalEventHandler.registerEvent(DailyMegaRareClaimEvent.class, DailyMegaRareClaimEvent.Payload.class);
            GlobalEventHandler.registerEvent(RareDropEvent.class, RareDropEvent.Payload.class);

            GlobalEventHandler.addListener(NetworkRestartEvent.class, e -> ScheduledRestartHandler.scheduleRestart(e.restartAt, e.reason));
            GlobalEventHandler.addListener(RuneStorageInventoryOpenEvent.class, e -> context.get(GUIHandler.class).onStorageContainerOpenGlobalEvent(e.containerUUID));
            GlobalEventHandler.addListener(SupportLevelQueryEvent.class, p -> DonationHandler.handleSupporterLevelReloadEvent(p.reloadUUID));
            GlobalEventHandler.addListener(RankReloadEvent.class, p -> DonationHandler.handleRankReloadEvent(p.reloadUUID, p.newRole));
            GlobalEventHandler.addListener(SupportLevelUpgradeEvent.class, p -> DonationHandler.handleSupporterUpgradeAnnounce(p.username, p.newLevel));
            GlobalEventHandler.addListener(BombContainerReloadEvent.class, p -> DonationHandler.handleBombContainerReloadEvent(p.reloadUUID, p.newContainer));
            GlobalEventHandler.addListener(BombActivatedEvent.class, p -> {
                RuneQuestPlugin.getPlayerManager().getOnlinePlayers().forEach(player -> {
                    context.get(ServerModifierHandler.class).showActivationMessage(p.activatedBy, p.serverId, player, p.type);
                });
            });
            GlobalEventHandler.addListener(DailyMegaRareClaimEvent.class, p -> {
                context.get(DailyRewardHandler.class).handleMegaRareClaim(p);
            });


        } catch (Throwable t) {
            RuneQuestPlugin.getRuneLogger().log(LogLevel.WARN, "Redis init failed, continuing anyway.", t);
        }
    }

    @Override
    public void stop(Plugin plugin, ModuleContext context) {
        GlobalEventHandler.clearHandlers();
        RedisConnection.onDisable();
    }

    @Override
    public String name() {
        return this.getClass().getSimpleName();
    }
}
