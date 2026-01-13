package org.zuma.examplePlugin.domain.player.handlers;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.analytics.AnalyticsKey;
import org.zuma.examplePlugin.core.logger.constants.LogLevel;
import org.zuma.examplePlugin.core.utils.AnalyticsUtils;
import org.zuma.examplePlugin.domain.modifier.constants.ModifierType;
import org.zuma.examplePlugin.domain.player.RunePlayer;
import org.zuma.examplePlugin.domain.player.RunePlayerManager;
import org.zuma.examplePlugin.domain.skill.constants.SkillType;
import org.zuma.examplePlugin.events.player.RunePlayerHealthChangedEvent;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

public class RegenerationHandler {
    private final static int HP_LOOP_TICK_DELAY_SECONDS = 3;
    private final static int HEAL_DELAY_SECONDS = 5;

    private final HashMap<UUID, Long> lastHeals;
    private final RunePlayerManager manager;
    private BukkitTask loop;

    public RegenerationHandler(RunePlayerManager manager) {
        this.lastHeals = new HashMap<>();
        this.manager = manager;
    }

    public void startLoop() {
        RuneQuestPlugin.getRuneLogger().log(LogLevel.INFO, "HP Regeneration loop started.");
        loop = Bukkit.getScheduler().runTaskTimer(RuneQuestPlugin.getInstance(), this::loopTick, 0L, HP_LOOP_TICK_DELAY_SECONDS * 20L);
        Bukkit.getScheduler().runTaskTimer(RuneQuestPlugin.getInstance(), this::modifierTick, 0L, 20L);
    }

    public void cleanUp() {
        if (loop != null) {
            loop.cancel();
            loop = null;
            RuneQuestPlugin.getRuneLogger().log(LogLevel.INFO, "HP Regeneration loop stopped.");
        }
    }

    private void modifierTick() {
        for (RunePlayer player : manager.getOnlinePlayers()) {
            if (player.getModifiers().containsKey(ModifierType.HEALTH_REGENERATION)) {
                double amount = player.getModifiers().get(ModifierType.HEALTH_REGENERATION).amount();

                if (player.getHealth() < player.getMaxHealth()) {
                    int newHp = (int) Math.min(player.getHealth() + amount, player.getMaxHealth());
                    player.setHealth(newHp, RunePlayerHealthChangedEvent.Cause.REGENERATION);
                    AnalyticsUtils.incrementServerAndPlayer(player, AnalyticsKey.HP_RESTORED, amount);
                }

            }

            if (player.getModifiers().containsKey(ModifierType.PRAYER_REGENERATION)) {
                double amount = player.getModifiers().get(ModifierType.PRAYER_REGENERATION).amount();

                if (player.getPrayerEnergy() < player.getSkill(SkillType.PRAYER).getLevelWithBonuses()) {
                    double newPrayer = Math.min(player.getPrayerEnergy() + amount, player.getSkill(SkillType.PRAYER).getLevelWithBonuses());
                    player.setPrayerEnergy(newPrayer);
                }
            }
        }
    }

    private void loopTick() {
        long now = System.currentTimeMillis();

        for (RunePlayer player : manager.getOnlinePlayers()) {
            lastHeals.putIfAbsent(player.getUUID(), 0L);
        }

        Iterator<Map.Entry<UUID, Long>> iterator = lastHeals.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<UUID, Long> entry = iterator.next();
            UUID uuid = entry.getKey();
            long lastHeal = entry.getValue();

            Player bukkitPlayer = Bukkit.getPlayer(uuid);
            RunePlayer player = RuneQuestPlugin.getPlayerManager().getRunePlayer(uuid);

            if (bukkitPlayer == null || !bukkitPlayer.isOnline() || player == null) {
                iterator.remove();
                continue;
            }

            if (player.isInCombat()) {
                continue;
            }

            if (lastHeal + (HEAL_DELAY_SECONDS * 1000L) <= now) {
                int maxHp = player.getMaxHealth();
                int currentHp = player.getHealth();

                double regen = Math.max(1, maxHp / 40);

                if (player.getModifiers().containsKey(ModifierType.HP_RECOVERY_BOOST_PERCENT)) {
                    double regenModifier = player.getModifiers().get(ModifierType.HP_RECOVERY_BOOST_PERCENT).amount();

                    regen *= 1 + (regenModifier / 100.0);
                }

                int newHp = (int) Math.min(currentHp + regen, maxHp);

                player.setHealth(newHp, RunePlayerHealthChangedEvent.Cause.REGENERATION);
                entry.setValue(now);
                AnalyticsUtils.incrementServerAndPlayer(player, AnalyticsKey.HP_RESTORED, regen);
            }
        }
    }

}
