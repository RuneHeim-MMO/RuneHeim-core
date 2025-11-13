package org.zuma.examplePlugin.domain.modifier.scopes.server;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.Nullable;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.constants.Color;
import org.zuma.examplePlugin.core.constants.SoundType;
import org.zuma.examplePlugin.core.logger.constants.LogLevel;
import org.zuma.examplePlugin.core.permissions.Permission;
import org.zuma.examplePlugin.core.utils.TextUtils;
import org.zuma.examplePlugin.domain.effect.EffectHandler;
import org.zuma.examplePlugin.domain.effect.effects.BonusBombEffect;
import org.zuma.examplePlugin.domain.hud.HudService;
import org.zuma.examplePlugin.domain.player.PlayerBombsContainer;
import org.zuma.examplePlugin.domain.player.RunePlayer;
import org.zuma.examplePlugin.events.player.RunePlayerLoadedEvent;

import java.time.Duration;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;

public class ServerModifierHandler implements Listener {
    private final EnumMap<ServerModifierType, ServerModifier> activeModifiers;
    private final List<ServerModifierType> waitingForAnimationFinish;

    public ServerModifierHandler() {
        this.activeModifiers = new EnumMap<>(ServerModifierType.class);
        this.waitingForAnimationFinish = new ArrayList<>();
    }

    public boolean startLoop() {
        Bukkit.getScheduler().runTaskTimerAsynchronously(RuneQuestPlugin.getInstance(), () -> {
            List<ServerModifierType> expiredModifiers = new ArrayList<>();

            for (ServerModifier modifier : activeModifiers.values()) {
                if (modifier.activeUntilMillis() < System.currentTimeMillis()) {
                    expiredModifiers.add(modifier.type());
                }
            }

            for (ServerModifierType type : expiredModifiers) {
                RuneQuestPlugin.getRuneLogger().log(LogLevel.INFO, type.getLabel() + " has expired on the server.");
                activeModifiers.remove(type);
                RuneQuestPlugin.getPlayerManager().getOnlinePlayers().forEach(p -> {
                    showExpirationMessage(p, type);
                });
            }

        }, 200L, 200L); // Run every 10 seconds

        return true;
    }

    public boolean isModifierActive(ServerModifierType type) {
        ServerModifier modifier = activeModifiers.get(type);

        if (modifier == null) {
            return false;
        }

        // Check if modifier is expired
        if (modifier.activeUntilMillis() < System.currentTimeMillis()) {
            return false;
        }

        return true;
    }

    public int getMinutesRemaining(ServerModifierType type) {
        ServerModifier modifier = activeModifiers.get(type);

        if (modifier == null) {
            return 0;
        }

        long millisRemaining = modifier.activeUntilMillis() - System.currentTimeMillis();
        return (int) (millisRemaining / 60000);
    }

    public void activateXPBomb(RunePlayer activatedBy, ServerModifierType type, long durationMillis) {
        if (isModifierActive(type)) {
            HudService.sendFailMessage(activatedBy, Component.text("A " + type.getLabel() + " is already active on this server. Try again in " + getMinutesRemaining(type) + " minutes."));
            return;
        }

        if (waitingForAnimationFinish.contains(type)) {
            HudService.sendFailMessage(activatedBy, Component.text("Someone is already throwing a " + type.getLabel() + "."));
            return;
        }

        if (activatedBy.getBombsContainer().getBombsAmounts().getOrDefault(type, 0) <= 0) {
            HudService.sendFailMessage(activatedBy, Component.text("You do not have any " + type.getLabel() + "s to activate."));
            return;
        }

        waitingForAnimationFinish.add(type);
        EffectHandler.playAt(activatedBy.asBukkitPlayer().getLocation(), new BonusBombEffect(activatedBy, type));
        Bukkit.getScheduler().runTaskLater(RuneQuestPlugin.getInstance(), () -> {
            if (activatedBy.asBukkitPlayer() != null && !activatedBy.asBukkitPlayer().isOnline()) {
                waitingForAnimationFinish.remove(type);
                return;
            }

            if (!activatedBy.getBombsContainer().attemptDeductPlayerBomb(type)) {
                HudService.sendFailMessage(activatedBy, Component.text("You do not have any " + type.getLabel() + "s to activate."));
                waitingForAnimationFinish.remove(type);
                return;
            }

            long activeUntil = System.currentTimeMillis() + durationMillis;
            ServerModifier modifier = new ServerModifier(type, activeUntil);
            activeModifiers.put(type, modifier);
            waitingForAnimationFinish.remove(type);
            RuneQuestPlugin.getRuneLogger().log(LogLevel.INFO, activatedBy.asBukkitPlayer().getName() + " has activated a " + type.getLabel() + " on the server for " + (durationMillis / 60000) + " minutes.");

            RuneQuestPlugin.getPlayerManager().getOnlinePlayers().forEach(p -> {
                showActivationMessage(activatedBy.asBukkitPlayer().getName(), null, p, type);
            });
        }, 60L);

    }

    private void showExpirationMessage(RunePlayer player, ServerModifierType type) {
        HudService.sendInfoMessage(player, Component.text(type.getLabel() + " has expired."));
        SoundType.GLOBAL_EFFECT_EXPIRED.play(player, false);
    }

    public void showActivationMessage(String activatedBy, @Nullable Integer activatedOnServerID, RunePlayer player, ServerModifierType type) {
        if (activatedOnServerID != null) {
            if (!player.getRole().hasPermission(Permission.BONUS_BOMB_TRACKER)) {
                return; // Don't show message if player doesn't have a tracker permission
            }
            player.asBukkitPlayer().sendMessage(Component.text(type.getLabel() + " has been activated by " + activatedBy + " on server #" + activatedOnServerID + " for " + getMinutesRemaining(type) + "minutes."));
            HudService.sendInfoMessage(player, Component.text(type.getLabel() + " is active " + activatedBy + " on server #" + activatedOnServerID + " for " + getMinutesRemaining(type) + "minutes."));
            SoundType.GLOBAL_EFFECT_TRACKER_BEEP.play(player, false);
            return;
        }

        player.asBukkitPlayer().showTitle(Title.title(
                Component.text(type.getLabel(), Color.SUCCESS.asColor()),
                Component.text("Activated by " + activatedBy + " for " + getMinutesRemaining(type) + " minutes").color(Color.SERVER_BRAND.asColor()),
                Title.Times.times(
                        Duration.ZERO,
                        Duration.ofSeconds(6, 5),
                        Duration.ofMillis(200)
                )
        ));

        SoundType.GLOBAL_EFFECT_ACTIVATED.play(player, false);
    }

    private void showAllActiveMessage(RunePlayer player) {
        String combinedNames = String.join(", ",
                activeModifiers.values().stream().map(s -> s.type().getLabel()).toList()
        );

        HudService.sendInfoMessage(player, Component.text("This server has " + combinedNames + " currently active."));
        SoundType.GLOBAL_EFFECT_ACTIVATED.play(player, false);
    }


    @EventHandler
    public void onPlayerLoad(RunePlayerLoadedEvent event) {
        RunePlayer player = event.getPlayer();

        if (!activeModifiers.isEmpty()) {
            showAllActiveMessage(player);
        }
    }


}
