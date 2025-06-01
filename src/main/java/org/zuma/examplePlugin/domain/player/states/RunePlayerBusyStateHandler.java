package org.zuma.examplePlugin.domain.player.states;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.constants.Colors;
import org.zuma.examplePlugin.core.constants.SoundType;
import org.zuma.examplePlugin.core.utils.TextUtils;
import org.zuma.examplePlugin.domain.player.RunePlayer;

import java.time.Duration;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

public class RunePlayerBusyStateHandler {
    private final RunePlayer player;
    private boolean isBusy;

    public RunePlayerBusyStateHandler(RunePlayer player) {
        this.player = player;
    }

    public void useBusyState(int durationMs, String action, String successAction, SoundType stepSound, SoundType successSound, Consumer<Void> consumer) {
        UUID uuid = player.getUUID();
        int steps = 10;
        long stepDuration = Math.max(1L, (durationMs / 50L) / steps);
        AtomicInteger currentSteps = new AtomicInteger();

        if (isBusy) {
            player.showFailTitle("You are already busy with something else!");
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
                    player.showFailTitle("Task cancelled.");
                    this.cancel();
                    return;
                }


                if (currentSteps.incrementAndGet() >= steps) {
                    isBusy = false;
                    this.cancel();

                    successSound.play(onlinePlayer);
                    onlinePlayer.showTitle(Title.title(
                            Component.text(""),
                            Component.text(successAction).color(Colors.SUCCESS.asColor()),
                            TextUtils.getGenericDuration()
                    ));

                    consumer.accept(null);
                    return;

                }

                // Progress bar
                int secondsLeft = (int) Math.ceil(((durationMs / (double) steps) * (steps - currentSteps.get())) / 1000.0);

                stepSound.play(onlinePlayer);
                onlinePlayer.showTitle(Title.title(
                        TextUtils.buildLoadingBar(steps, currentSteps.get()),
                        Component.text("[" + secondsLeft + "s] ").color(Colors.SUCCESS.asColor())
                                .append(Component.text(action).color(Colors.SUBTITLE.asColor())),
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
