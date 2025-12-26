package org.zuma.examplePlugin.domain.xp;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.logger.constants.LogLevel;
import org.zuma.examplePlugin.domain.combat.Hit;
import org.zuma.examplePlugin.domain.combat.constants.HitType;
import org.zuma.examplePlugin.domain.player.RunePlayer;
import org.zuma.examplePlugin.domain.skill.constants.SkillType;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class PendingXPHandler {
    private static final double HP_SKILL_XP_PER_DMG_POINT = 1.3;
    private static final double COMBAT_SKILL_XP_PER_DMG_POINT = 3;

    private final HashMap<UUID, PendingXpContainer> pendingPlayerXP;

    public PendingXPHandler() {
        pendingPlayerXP = new HashMap<>();
    }

    public void distributePendingXP() {
        pendingPlayerXP.forEach((uuid, container) -> {
            try {
                long minuteAgo = System.currentTimeMillis() - 60_000;
                if (container.getLastInteraction() < minuteAgo) {
                    return;
                }

                RunePlayer player = RuneQuestPlugin.getPlayerManager().getRunePlayer(uuid);

                if (!player.isOnline()) {
                    return;
                }

                AtomicInteger index = new AtomicInteger();
                container.getPendingXP().forEach((type, amount) -> {
                    Bukkit.getScheduler().runTaskLater(RuneQuestPlugin.getInstance(), () -> {
                        if (!player.isOnline()) {
                            return;
                        }

                        player.distributeXp(type, amount);

                    }, index.get() * 5L);
                    index.getAndIncrement();
                });

            } catch (Exception e) {
                RuneQuestPlugin.getRuneLogger().log(LogLevel.WARN, "Failed to distribute XP: ", e);
            }
        });
    }

    public void registerHit(Hit hit) {
        if (hit.getHitType() == HitType.MISS) {
            return;
        }

        Entity attacker = hit.getAttacker().getEntity();

        if (!(attacker instanceof Player livingAttacker)) {
            return;
        }

        RunePlayer player = RuneQuestPlugin.getPlayerManager().getRunePlayer(livingAttacker);

        if (player == null) {
            return;
        }


        UUID uuid = player.getUUID();
        long now = System.currentTimeMillis();

        if (!pendingPlayerXP.containsKey(uuid)) {
            pendingPlayerXP.put(uuid, new PendingXpContainer(now));
        }

        PendingXpContainer container = pendingPlayerXP.get(uuid);
        int damage = hit.getDamage();

        container.updateLastInteraction(now);
        //container.addXP(SkillType.HITPOINTS, damage * HP_SKILL_XP_PER_DMG_POINT);

        switch (hit.getAttackType()) {
            case RANGED -> container.addXP(SkillType.RANGED, damage * COMBAT_SKILL_XP_PER_DMG_POINT);
            case MELEE -> {
                container.addXP(SkillType.ATTACK, damage * COMBAT_SKILL_XP_PER_DMG_POINT);
                container.addXP(SkillType.STRENGTH, damage * COMBAT_SKILL_XP_PER_DMG_POINT);
                container.addXP(SkillType.DEFENCE, damage * COMBAT_SKILL_XP_PER_DMG_POINT);
            }
            //  case MAGIC -> container.addXP(SkillType.MAGIC, damage * COMBAT_SKILL_XP_PER_DMG_POINT);

        }

    }

    public UUID getPlayerWithMostPendingXP() {
        return pendingPlayerXP.entrySet().stream()
                .max(Comparator.comparingDouble(entry -> entry.getValue().getPendingXP().values().stream().mapToDouble(Double::doubleValue).sum()))
                .map(Map.Entry::getKey)
                .orElse(null);
    }

    public List<RunePlayer> getRecentAttackers(Long durationMillis) {
        List<RunePlayer> recentAttackers = new ArrayList<>();
        long minuteAgo = System.currentTimeMillis() - durationMillis;

        pendingPlayerXP.forEach((uuid, container) -> {
            if (container.getLastInteraction() >= minuteAgo) {
                RunePlayer player = RuneQuestPlugin.getPlayerManager().getRunePlayer(uuid);

                if (player != null) {
                    recentAttackers.add(player);
                }
            }
        });

        return recentAttackers;

    }

    private static class PendingXpContainer {
        private final HashMap<SkillType, Double> pendingXP;
        private long lastInteraction;

        public PendingXpContainer(long lastInteraction) {
            this.pendingXP = new HashMap<>();
            this.lastInteraction = lastInteraction;
        }

        public HashMap<SkillType, Double> getPendingXP() {
            return pendingXP;
        }

        public void addXP(SkillType type, double amount) {
            if (!pendingXP.containsKey(type)) {
                pendingXP.put(type, 0.0);
            }

            pendingXP.compute(type, (k, currentXP) -> (currentXP == null ? 0 : currentXP) + amount);
        }

        public void updateLastInteraction(long timestamp) {
            this.lastInteraction = timestamp;
        }

        public long getLastInteraction() {
            return lastInteraction;
        }
    }
}
