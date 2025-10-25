package org.zuma.examplePlugin.domain.player.handlers;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.logger.constants.LogLevel;
import org.zuma.examplePlugin.domain.modifier.constants.ModifierType;
import org.zuma.examplePlugin.domain.player.RunePlayer;
import org.zuma.examplePlugin.domain.player.RunePlayerManager;
import org.zuma.examplePlugin.domain.skill.constants.SkillType;
import org.zuma.examplePlugin.events.player.RunePlayerHealthChangedEvent;

import java.util.*;

public class HPRegenerationHandler {
    private final static int LOOP_TICK_DELAY_SECONDS = 3;
    private final static int HEAL_DELAY_SECONDS = 5;

    private final HashMap<UUID, Long> lastHeals;
    private final RunePlayerManager manager;
    private BukkitTask loop;

    public HPRegenerationHandler(RunePlayerManager manager) {
        this.lastHeals = new HashMap<>();
        this.manager = manager;
    }

    public void startLoop() {
        RuneQuestPlugin.getRuneLogger().log(LogLevel.INFO, "HP Regeneration loop started.");
        loop = Bukkit.getScheduler().runTaskTimer(RuneQuestPlugin.getInstance(), this::loopTick, 0L, LOOP_TICK_DELAY_SECONDS * 20L);
    }

    public void cleanUp() {
        if (loop != null) {
            loop.cancel();
            loop = null;
            RuneQuestPlugin.getRuneLogger().log(LogLevel.INFO, "HP Regeneration loop stopped.");
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
                    double regedModifier = player.getModifiers().get(ModifierType.HP_RECOVERY_BOOST_PERCENT).amount();

                    regen *= 1 + (regedModifier / 100.0);
                }

                int newHp = (int) Math.min(currentHp + regen, maxHp);

                player.setHealth(newHp, RunePlayerHealthChangedEvent.Cause.REGENERATION);
                entry.setValue(now);
            }
        }
    }

}
