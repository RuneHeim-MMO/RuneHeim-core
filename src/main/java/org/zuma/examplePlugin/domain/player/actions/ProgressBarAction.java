package org.zuma.examplePlugin.domain.player.actions;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.constants.Color;
import org.zuma.examplePlugin.core.constants.Icon;
import org.zuma.examplePlugin.core.interfaces.InteractiveActionStrategy;
import org.zuma.examplePlugin.core.utils.TextUtils;
import org.zuma.examplePlugin.domain.hud.HudService;
import org.zuma.examplePlugin.domain.hud.LoadingBarBuilder;
import org.zuma.examplePlugin.domain.player.RunePlayer;

import javax.annotation.Nullable;
import java.time.Duration;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class ProgressBarAction implements InteractiveActionStrategy {
    private final RunePlayer player;

    @Nullable
    private BukkitTask task;
    private boolean isCancelled;

    public ProgressBarAction(RunePlayer player) {
        this.player = player;
        this.task = null;
        this.isCancelled = false;
    }

    public InteractiveActionStrategy startAction(ActionParams params) {
        UUID uuid = player.getUUID();

        // Calculate total ticks for duration (1 tick = 50 ms)
        long totalTicks = params.getDurationMs() / 50L;

        // Initial step count
        int baseSteps = 10;

        final long maxStepDuration = 20L; // 20 ticks = 1 second max

        long stepDuration;  // Declare here

        int steps;

        // Calculate initial step duration (ticks per step)
        long calculatedStepDuration = totalTicks / baseSteps;

        if (calculatedStepDuration <= maxStepDuration) {
            // Step duration is less than or equal to max, keep base steps
            stepDuration = calculatedStepDuration;
            steps = baseSteps;
        } else {
            // Step duration too long, cap to maxStepDuration and increase steps accordingly
            stepDuration = maxStepDuration;
            steps = (int) Math.ceil((double) totalTicks / stepDuration);
        }

        AtomicInteger currentSteps = new AtomicInteger();

        this.task = new BukkitRunnable() {
            @Override
            public void run() {
                Player onlinePlayer = Bukkit.getPlayer(uuid);

                if (onlinePlayer == null || !onlinePlayer.isOnline()) {
                    player.cancelActionTask(true);
                    this.cancel();
                    return;
                }

                if (isCancelled) {
                    this.cancel();

                    if (params.getOnFailure() != null) {
                        params.getOnFailure().run();
                    }
                    return;
                }

                if (currentSteps.incrementAndGet() >= steps) {
                    player.cancelActionTask(false);
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
                                Duration.ofSeconds(20L),  // convert ticks to seconds
                                Duration.ofMillis(500)
                        )
                ));
            }
        }.runTaskTimer(RuneQuestPlugin.getInstance(), 0L, stepDuration);

        return this;
    }



    @Override
    public void cancelTask() {
        isCancelled = true;
    }

}
