package org.zuma.examplePlugin.domain.player.actions;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.constants.Color;
import org.zuma.examplePlugin.core.constants.Icon;
import org.zuma.examplePlugin.core.interfaces.InteractiveActionStrategy;
import org.zuma.examplePlugin.core.utils.TextUtils;
import org.zuma.examplePlugin.domain.hud.HudService;
import org.zuma.examplePlugin.domain.hud.LoadingBarBuilder;
import org.zuma.examplePlugin.domain.player.RunePlayer;

import java.time.Duration;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class ProgressBarAction implements InteractiveActionStrategy {
    private final RunePlayer player;

    public ProgressBarAction(RunePlayer player) {
        this.player = player;
    }

    public void startAction(ActionParams params) {
        UUID uuid = player.getUUID();
        int steps = 10;
        long stepDuration = Math.max(1L, (params.getDurationMs() / 50L) / steps);
        AtomicInteger currentSteps = new AtomicInteger();

        new BukkitRunnable() {
            @Override
            public void run() {
                Player onlinePlayer = Bukkit.getPlayer(uuid);


                if (onlinePlayer == null || !onlinePlayer.isOnline()) {
                    player.setBusy(false);
                    this.cancel();
                    return;
                }


                if (!player.isBusy()) {
                    HudService.showFailTitle(player, "Task cancelled.");
                    this.cancel();

                    if (params.getOnFailure() != null) {
                        params.getOnFailure().run();
                    }

                    return;
                }


                if (currentSteps.incrementAndGet() >= steps) {
                    player.setBusy(false);
                    this.cancel();

                    if (params.getSuccessSound() != null) params.getSuccessSound().play(onlinePlayer, true);
                    onlinePlayer.showTitle(Title.title(
                            Component.empty(),
                            Component.text(params.getSuccessActionLabel()).color(Color.SUCCESS.asColor()),
                            TextUtils.getGenericDuration()
                    ));

                    params.getOnSuccess().run();
                    return;

                }

                // Progress bar
                int secondsLeft = (int) Math.ceil(((params.getDurationMs() / (double) steps) * (steps - currentSteps.get())) / 1000.0);
                Component progressBar = new LoadingBarBuilder()
                        .totalSteps(steps)
                        .currentStep(currentSteps.get())
                        .barLength(16)
                        .segment(Icon.PROGRESS_SHORT_SEGMENT.get())
                        .start(Icon.PROGRESS_SHORT_START.get())
                        .stop(Icon.PROGRESS_SHORT_END.get())
                        .emptyColor(Color.BUSY_BAR_EMPTY.asColor())
                        .fillColor(Color.BUSY_BAR_FILL.asColor())
                        .build();

                if (params.getStepSound() != null) params.getStepSound().play(onlinePlayer, true);

                onlinePlayer.swingMainHand();
                onlinePlayer.showTitle(Title.title(progressBar,
                        Component.text("[" + secondsLeft + "s] ").color(Color.SUCCESS.asColor())
                                .append(Component.text(params.getActionLabel()).color(Color.SUBTITLE.asColor())),
                        Title.Times.times(
                                Duration.ZERO,
                                Duration.ofSeconds(stepDuration),
                                Duration.ofMillis(500)
                        )
                ));


            }
        }.runTaskTimer(RuneQuestPlugin.getInstance(), 0L, stepDuration);
    }

}
