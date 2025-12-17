package org.zuma.examplePlugin.core.handlers.core;

import com.ticxo.modelengine.api.events.ModelRegistrationEvent;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.config.ConfigManager;
import org.zuma.examplePlugin.core.config.WhitelistMode;
import org.zuma.examplePlugin.core.constants.Color;
import org.zuma.examplePlugin.core.logger.constants.LogLevel;
import org.zuma.examplePlugin.core.permissions.Permission;
import org.zuma.examplePlugin.core.utils.TextUtils;
import org.zuma.examplePlugin.domain.player.RunePlayer;
import org.zuma.examplePlugin.events.player.RunePlayerLoadedEvent;

public class WhitelistHandler implements Listener {

    public WhitelistHandler() {
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerJoin(PlayerLoginEvent event) {
        WhitelistMode mode = ConfigManager.whitelistMode;
        Player p = event.getPlayer();

        // No permissions check here, we kick even before that
        if (mode.equals(WhitelistMode.OP_ONLY) && !p.isOp()) {
            event.disallow(PlayerLoginEvent.Result.KICK_OTHER, TextUtils.getBrandKickScreen(Component.text("Server is currently in OP_ONLY whitelist mode.\n\nCheck our Discord for updates.", Color.ERROR_LIGHT.asColor())));
            RuneQuestPlugin.getRuneLogger().log(LogLevel.INFO, "Kicked player " + p.getName() + " due to OP_ONLY whitelist mode.");
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerLoad(RunePlayerLoadedEvent event) {
        WhitelistMode mode = ConfigManager.whitelistMode;
        RunePlayer p = event.getPlayer();


        // Run whitelist checks after RunePlayer has been loaded so role-based permissions are available
        if (mode.equals(WhitelistMode.STAGING) && !p.getRole().hasPermission(Permission.JOIN_STAGING_SERVER)) {
            event.setCancelled(true);
            RuneQuestPlugin.getRuneLogger().log(LogLevel.INFO, "Kicked player " + p.asBukkitPlayer().getName() + " due to STAGING whitelist mode.");
            p.asBukkitPlayer().kick(TextUtils.getBrandKickScreen(Component.text("Server is currently in STAGING whitelist mode.\nYou do not have permission to join.", Color.ERROR_LIGHT.asColor())));
        }

        if (mode.equals(WhitelistMode.LOCKDOWN) && !p.getRole().hasPermission(Permission.JOIN_LOCKDOWN_SERVER)) {
            event.setCancelled(true);
            RuneQuestPlugin.getRuneLogger().log(LogLevel.INFO, "Kicked player " + p.asBukkitPlayer().getName() + " due to LOCKDOWN whitelist mode.");
            p.asBukkitPlayer().kick(TextUtils.getBrandKickScreen(Component.text("Server is currently in lockdown mode.\nYou do not have permission to join.\n\nCheck our Discord for updates.", Color.ERROR_LIGHT.asColor())));
        }


    }
}
