package org.zuma.examplePlugin.domain.effect;

import org.bukkit.Location;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.Nullable;
import org.zuma.examplePlugin.RuneQuestPlugin;

import java.util.function.Supplier;

public class EffectHandler {

    public static void playAt(Location location, AnimatedEffect effect, Runnable onFinish) {
        EffectHandler.playAt(() -> location, effect, 1, onFinish);
    }

    public static void playAt(Location location, AnimatedEffect effect, double speedMultiplier) {
        EffectHandler.playAt(() -> location, effect, speedMultiplier, null);
    }

    public static void playAt(Location location, AnimatedEffect effect) {
        EffectHandler.playAt(() -> location, effect, 1, null);
    }

    public static void playAt(Supplier<Location> getLocation, AnimatedEffect effect) {
        EffectHandler.playAt(getLocation, effect, 1, null);
    }

    public static void playAt(Supplier<Location> getLocation, AnimatedEffect effect, double speedMultiplier, @Nullable Runnable onFinish) {
        if (effect.getDurationTicks() == 0) {
            effect.playAt(getLocation.get(), 0);
            return;
        }


        new BukkitRunnable() {
            double time = 0;

            @Override
            public void run() {
                try {
                    effect.playAt(getLocation.get(), time);
                } catch (Exception e) {
                    // Swallow any errors to prevent infinite loops
                }

                time += speedMultiplier;

                if (time >= effect.getDurationTicks()) {
                    this.cancel();
                    if (onFinish != null) onFinish.run();
                }
            }
        }.runTaskTimer(RuneQuestPlugin.getInstance(), 0L, 1L);
    }

    public static void playAt(Location loc, StaticEffect effect) {
        effect.playAt(loc);
    }
}
