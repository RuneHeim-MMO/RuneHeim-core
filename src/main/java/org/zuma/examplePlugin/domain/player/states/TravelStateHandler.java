package org.zuma.examplePlugin.domain.player.states;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.scheduler.BukkitRunnable;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.constants.Color;
import org.zuma.examplePlugin.core.constants.SoundType;
import org.zuma.examplePlugin.core.logger.constants.LogLevel;
import org.zuma.examplePlugin.core.utils.PlayerUtils;
import org.zuma.examplePlugin.domain.effect.EffectHandler;
import org.zuma.examplePlugin.domain.effect.effects.TravelStartEffect;
import org.zuma.examplePlugin.domain.effect.effects.TravelStopEffect;
import org.zuma.examplePlugin.domain.hud.HudService;
import org.zuma.examplePlugin.domain.player.RunePlayer;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicBoolean;

public class TravelStateHandler {
    private final static int TRANSITION_DURATION_TICKS = 20;

    public static void startTravelingSequence(RunePlayer player, Location waypoint, Location buffer, int durationTicks, TravelMode mode) {
        if (durationTicks < TRANSITION_DURATION_TICKS) {
            RuneQuestPlugin.getRuneLogger().log(LogLevel.WARN, "Cannot start traveling sequence. Duration is too short.");
            return;
        }

        EffectHandler.playAt(player.asBukkitPlayer().getLocation(), new TravelStartEffect(player, mode));
        AtomicBoolean arrived = new AtomicBoolean(false);
        AtomicBoolean cancelled = new AtomicBoolean(false);

        Bukkit.getScheduler().runTaskLater(RuneQuestPlugin.getInstance(), () -> {
            if (player.asBukkitPlayer() == null || !player.asBukkitPlayer().isOnline()) {
                cancelled.set(true);
                return;
            }

            player.setInCutscene(player.asBukkitPlayer().getLocation());
            player.asBukkitPlayer().teleport(buffer);
            PlayerUtils.hideAllPlayersForPlayer(player.asBukkitPlayer());
            final int[] ticks = {0};

            new BukkitRunnable() {

                public void run() {
                    if (arrived.get() || player.asBukkitPlayer() == null || !player.asBukkitPlayer().isOnline()) {
                        if (!arrived.get()) cancelled.set(true);
                        cancel();
                        return;
                    }

                    ticks[0] += 12;
                    mode.getLoopSound().play(player, false);
                    player.asBukkitPlayer().setExp(Math.min(1f, (float) ticks[0] / durationTicks));
                }
            }.runTaskTimer(RuneQuestPlugin.getInstance(), TRANSITION_DURATION_TICKS, 12);

            Bukkit.getScheduler().runTaskLater(RuneQuestPlugin.getInstance(), () -> {
                if (player.asBukkitPlayer() == null || !player.asBukkitPlayer().isOnline()) return;

                player.asBukkitPlayer().showTitle(Title.title(
                        Component.empty(),
                        Component.text("Arriving soon!", Color.SUCCESS.asColor()),
                        Title.Times.times(Duration.ofSeconds(0), Duration.ofSeconds(3, 5), Duration.ofSeconds(3)
                        )
                ));

            }, durationTicks - (durationTicks / 3));

            Bukkit.getScheduler().runTaskLater(RuneQuestPlugin.getInstance(), () -> {
                if (player.asBukkitPlayer() == null || !player.asBukkitPlayer().isOnline() || cancelled.get()) return;

                arrived.set(true);
                EffectHandler.playAt(player.asBukkitPlayer().getLocation(), new TravelStopEffect(player, mode));
                mode.getEndSound().play(player, false);

                Bukkit.getScheduler().runTaskLater(RuneQuestPlugin.getInstance(), () -> {
                    if (player.asBukkitPlayer() == null || !player.asBukkitPlayer().isOnline() || cancelled.get())
                        return;

                    PlayerUtils.restorePlayersVisibilityForPlayer(player.asBukkitPlayer());
                    HudService.updateVanillaGUI(player);
                    player.asBukkitPlayer().teleport(waypoint);
                    player.setOutOfCutscene();
                }, TRANSITION_DURATION_TICKS);


            }, durationTicks + (TRANSITION_DURATION_TICKS * 2));

        }, TRANSITION_DURATION_TICKS);


    }
}
