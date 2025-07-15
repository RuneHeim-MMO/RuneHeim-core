package org.zuma.examplePlugin.domain.combat;

import org.bukkit.Bukkit;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitTask;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.utils.ItemUtils;
import org.zuma.examplePlugin.core.utils.PlayerUtils;
import org.zuma.examplePlugin.domain.item.constants.WeaponSpeed;

public class VisualWeaponSpeedHandler {
    private final static int TICK_DURATION = 40; // Every 2 seconds
    private BukkitTask loop;

    private int getMiningFatigueAmplifier(double hps) {
        double minHps = WeaponSpeed.VERY_SLOW.getHitsPerSec();
        double maxHps = WeaponSpeed.VERY_FAST.getHitsPerSec();
        int maxAmp = 8;

        double normalized = (hps - minHps) / (maxHps - minHps);
        double inverted = 1.0 - normalized;
        int amplifier = (int) Math.round(inverted * maxAmp);
        return Math.max(0, Math.min(amplifier, maxAmp));
    }

    public void startEffectsLoop() {
        loop = Bukkit.getScheduler().runTaskTimer(RuneQuestPlugin.getInstance(), this::loopTick, 0, TICK_DURATION);
    }

    public void onDisable() {
        if (loop != null && !loop.isCancelled()) {
            loop.cancel();
        }
    }

    private void loopTick() {
        Bukkit.getOnlinePlayers().forEach(player -> {
            WeaponSpeed speed = ItemUtils.getWeaponSpeed(PlayerUtils.getItemStackInHand(player));


            int amp = getMiningFatigueAmplifier(speed == null ? WeaponSpeed.NORMAL.getHitsPerSec() : speed.getHitsPerSec());

            PotionEffect fatigue = new PotionEffect(
                    PotionEffectType.MINING_FATIGUE,
                    TICK_DURATION * 2, // tps delay compensation
                    amp,
                    false,  // ambient
                    false,  // particles
                    false   // icon
            );

            player.addPotionEffect(fatigue);

        });
    }
}
