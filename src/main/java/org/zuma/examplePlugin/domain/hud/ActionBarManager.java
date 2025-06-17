package org.zuma.examplePlugin.domain.hud;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.constants.Color;
import org.zuma.examplePlugin.core.constants.Icon;
import org.zuma.examplePlugin.core.utils.CurrencyUtils;
import org.zuma.examplePlugin.domain.player.RunePlayer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ActionBarManager {
    private static final int FRAME_DURATION = 2;
    private final RunePlayer player;
    private final List<Component> elements = new ArrayList<>();
    private BukkitTask activeTask = null;
    private boolean tick = false; // Ticktock counter
    int coins = 0;

    public ActionBarManager(RunePlayer player) {
        this.player = player;
        startDefaultLoop();
    }

    private void queueFrame(Component component, int frames) {
        this.elements.addAll(Collections.nCopies(frames, component));

        if (activeTask != null && activeTask.isCancelled()) {
            activeTask.cancel();
        }

    }

    private Component getDefaultElement() {

        coins += 5000;

        Component specialAttackBar = new LoadingBarBuilder()
                .totalSteps(12)
                .currentStep(6)
                .barLength(12)
                .segment(Icon.PROGRESS_TALL_SEGMENT.get())
                .start(Icon.PROGRESS_GEM_TALL_START.get())
                .stop(Icon.PROGRESS_TALL_END.get())
                .emptyColor(Color.SPECIAL_BAR_EMPTY.asColor())
                .fillColor(Color.SPECIAL_BAR_FILL.asColor())
                .build();

        Component manaBar = new LoadingBarBuilder()
                .totalSteps(12)
                .currentStep(3)
                .barLength(12)
                .segment(Icon.PROGRESS_TALL_SEGMENT.get())
                .start(Icon.PROGRESS_TALL_START.get())
                .stop(Icon.PROGRESS_SKULL_TALL_END.get())
                .emptyColor(Color.MANA_BAR_EMPTY.asColor())
                .fillColor(Color.MANA_BAR_FILL.asColor())
                .inverted(true)
                .build();

        return Component.text(Icon.HEART_BIG.get(), NamedTextColor.WHITE)
                .append(Component.text(" 10/99 HP" + " ".repeat(5), Color.ERROR.asColor()))
                .append(specialAttackBar)
                .append(manaBar)
                .append(Component.text(" ".repeat(5)))
                .append(CurrencyUtils.getCurrencyComponent(coins))
                .append(Component.text(" GP", CurrencyUtils.getCurrencyColor(coins).asColor()));

    }

    public void showLevelUp() {
        queueFrame(Component.text("Level ups!", Color.SUCCESS.asColor()), 20);
        queueTypewriter("Some new recipes are available!", 1, Color.SUCCESS);

        startAnimationLoop();

    }

    private void queueTypewriter(String text, int frameCountPerChar, Color color) {
        for (int i = 1; i <= text.length(); i++) {
            Component frame = Component.text(text.substring(0, i), color.asColor());
            queueFrame(frame, frameCountPerChar);
        }
    }

    private void startAnimationLoop() {
        if (activeTask != null) {
            activeTask.cancel();
        }

        activeTask = new BukkitRunnable() {

            @Override
            public void run() {
                Player p = player.getPlayer();

                if (p == null || !p.isOnline()) {
                    this.cancel();
                    return;
                }

                if (elements.isEmpty()) {
                    this.cancel();
                    startDefaultLoop();
                } else {
                    p.sendActionBar(elements.getFirst());
                    elements.removeFirst();
                }

                tick = !tick;

            }
        }.runTaskTimer(RuneQuestPlugin.getInstance(), 0L, FRAME_DURATION);

    }

    private void startDefaultLoop() {
        if (activeTask != null) {
            activeTask.cancel();
        }

        activeTask = new BukkitRunnable() {

            @Override
            public void run() {
                Player p = player.getPlayer();

                if (p == null || !p.isOnline()) {
                    this.cancel();
                    return;
                }

                p.sendActionBar(getDefaultElement());
                tick = !tick;

            }
        }.runTaskTimer(RuneQuestPlugin.getInstance(), 0L, 10L);

    }

}
