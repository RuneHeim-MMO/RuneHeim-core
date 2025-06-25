package org.zuma.examplePlugin.domain.player.states;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.constants.Color;
import org.zuma.examplePlugin.core.constants.Icon;
import org.zuma.examplePlugin.core.constants.SoundType;
import org.zuma.examplePlugin.core.utils.TextUtils;
import org.zuma.examplePlugin.domain.hud.HudService;
import org.zuma.examplePlugin.domain.hud.LoadingBarBuilder;
import org.zuma.examplePlugin.domain.player.RunePlayer;

import javax.annotation.Nullable;
import java.time.Duration;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class RunePlayerBusyStateHandler {
    private final RunePlayer player;
    private boolean isBusy;

    public RunePlayerBusyStateHandler(RunePlayer player) {
        this.player = player;
    }

    public void useBusyState(int durationMs, String action, String successAction, SoundType stepSound, SoundType successSound, Runnable onSuccess, @Nullable Runnable onFailure) {
        UUID uuid = player.getUUID();
        int steps = 10;
        long stepDuration = Math.max(1L, (durationMs / 50L) / steps);
        AtomicInteger currentSteps = new AtomicInteger();

        if (isBusy) {
            HudService.showFailTitle(player, "You are already busy with something else!");
            return;
        }

        isBusy = true;

        new BukkitRunnable() {
            @Override
            public void run() {
                Player onlinePlayer = Bukkit.getPlayer(uuid);


                if (onlinePlayer == null || !onlinePlayer.isOnline()) {
                    isBusy = false;
                    this.cancel();
                    return;
                }

                if (!isBusy) {
                    isBusy = false;
                    HudService.showFailTitle(player, "Task cancelled.");
                    this.cancel();

                    if (onFailure != null) {
                        onFailure.run();
                    }

                    return;
                }


                if (currentSteps.incrementAndGet() >= steps) {
                    isBusy = false;
                    this.cancel();

                    successSound.play(onlinePlayer, true);
                    onlinePlayer.showTitle(Title.title(
                            Component.text(""),
                            Component.text(successAction).color(Color.SUCCESS.asColor()),
                            TextUtils.getGenericDuration()
                    ));

                    onSuccess.run();
                    return;

                }

                // Progress bar
                int secondsLeft = (int) Math.ceil(((durationMs / (double) steps) * (steps - currentSteps.get())) / 1000.0);
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

                stepSound.play(onlinePlayer, true);
                onlinePlayer.showTitle(Title.title(progressBar,
                        Component.text("[" + secondsLeft + "s] ").color(Color.SUCCESS.asColor())
                                .append(Component.text(action).color(Color.SUBTITLE.asColor())),
                        Title.Times.times(
                                Duration.ZERO,
                                Duration.ofSeconds(stepDuration),
                                Duration.ofMillis(500)
                        )
                ));


            }
        }.runTaskTimer(RuneQuestPlugin.getInstance(), 0L, stepDuration);
    }

    public boolean isBusy() {
        return isBusy;
    }

    public void cancelTask() {
        isBusy = false;
    }
}
