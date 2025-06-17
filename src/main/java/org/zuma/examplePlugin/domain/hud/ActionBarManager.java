package org.zuma.examplePlugin.domain.hud;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.constants.Colors;
import org.zuma.examplePlugin.core.constants.Icon;
import org.zuma.examplePlugin.domain.player.RunePlayer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ActionBarManager {
    private static final int FRAME_DURATION = 2;
    private final RunePlayer player;
    private final List<Component> elements = new ArrayList<>();
    private BukkitTask activeTask = null;
    private boolean tick = false; // Tick tock counter

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


        return Component.text(tick ? Icon.HEART_BIG.get() : Icon.HEART.get(), NamedTextColor.WHITE).append(Component.text(" 10/99" + " ".repeat(80), Colors.ERROR.asColor()));
    }

    public void showLevelUp() {
        queueFrame(Component.text("Level ups!", Colors.SUCCESS.asColor()), 20);
        queueTypewriter("Some new recipes are available!", 1, Colors.SUCCESS);

        startAnimationLoop();

    }

    private void queueTypewriter(String text, int frameCountPerChar, Colors color) {
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
