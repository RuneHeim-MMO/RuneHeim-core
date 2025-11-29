package org.zuma.examplePlugin.domain.prayer;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.analytics.AnalyticsKey;
import org.zuma.examplePlugin.core.constants.SoundType;
import org.zuma.examplePlugin.core.logger.constants.LogLevel;
import org.zuma.examplePlugin.core.utils.AnalyticsUtils;
import org.zuma.examplePlugin.domain.player.RunePlayer;
import org.zuma.examplePlugin.domain.skill.constants.SkillType;

import java.util.*;

public class PrayerManager implements Listener {
    private final HashMap<UUID, List<Prayer>> activePrayers = new HashMap<>();

    public PrayerManager() {
    }


    public void startLoop() {
        Bukkit.getScheduler().runTaskTimer(RuneQuestPlugin.getInstance(), this::tick, 0L, 60L);
        Bukkit.getScheduler().runTaskTimer(RuneQuestPlugin.getInstance(), this::presentationTick, 0L, 10L);
        RuneQuestPlugin.getRuneLogger().log(LogLevel.INFO, "PrayerManager loop started.");
    }

    private void tick() {
        List<UUID> offlinePlayers = new ArrayList<>();

        RuneQuestPlugin.getPlayerManager().getOnlinePlayers().forEach(player -> {
            if (!activePrayers.containsKey(player.getUUID())) {
                activePrayers.put(player.getUUID(), new ArrayList<>());
            }

            List<Prayer> prayers = activePrayers.get(player.getUUID());

            if (prayers.isEmpty()) return;

            double drainAmount = prayers.stream()
                    .mapToDouble(Prayer::getDrainRatePerTick)
                    .sum();

            player.setPrayerEnergy(player.getPrayerEnergy() - drainAmount);
            AnalyticsUtils.incrementServerAndPlayer(player, AnalyticsKey.PRAYER_POINTS_WASTED, drainAmount);

            if (player.getPrayerEnergy() <= 0) {
                prayers.clear();
                handlePrayerDepletion(player);
            }
        });

        activePrayers.forEach(((uuid, prayers) -> {
            Player player = Bukkit.getPlayer(uuid);
            if (player == null || !player.isOnline()) {
                offlinePlayers.add(uuid);
            }
        }));

        offlinePlayers.forEach(activePrayers::remove);
    }

    private void presentationTick() {
        for (Map.Entry<UUID, List<Prayer>> entry : activePrayers.entrySet()) {
            UUID playerUUID = entry.getKey();
            List<Prayer> prayers = entry.getValue();

            if (prayers.isEmpty()) continue;


            Player player = Bukkit.getPlayer(playerUUID);
            if (player == null || !player.isOnline()) {
                continue;
            }

            int highestLevel = prayers.stream()
                    .mapToInt(Prayer::getLevelRequirement)
                    .max()
                    .orElse(1);

            Color color = Color.WHITE;

            if (highestLevel <= 9) {
                color = Color.WHITE;
            } else if (highestLevel <= 29) {
                color = Color.YELLOW;
            } else {
                color = Color.PURPLE;
            }

            Location loc = player.getLocation().clone();
            Particle.DustOptions dust = new Particle.DustOptions(color, 0.5f + (prayers.size() * 0.25f));
            player.getWorld().spawnParticle(Particle.DUST, loc, 10, 0.5, 0.5, 0.5, dust);

        }
    }

    private void handlePrayerDepletion(RunePlayer player) {
        player.setPrayerEnergy(0);
        RuneQuestPlugin.getGearModifierHandler().recalculatePlayerModifiers(player, player.asBukkitPlayer().getInventory().getItemInMainHand());

        SoundType.PRAYER_DEACTIVATED.play(player, false);
    }

    public void clearPrayers(RunePlayer player) {
        if (activePrayers.containsKey(player.getUUID()) && activePrayers.get(player.getUUID()).isEmpty()) {
            return;
        }

        activePrayers.remove(player.getUUID());
        SoundType.PRAYER_DEACTIVATED.play(player, false);
        RuneQuestPlugin.getGearModifierHandler().recalculatePlayerModifiers(player, player.asBukkitPlayer().getInventory().getItemInMainHand());
    }

    public void setPrayer(RunePlayer player, Prayer prayer, boolean active) {
        List<Prayer> prayers = activePrayers.getOrDefault(player.getUUID(), new ArrayList<>());

        if (prayer.getLevelRequirement() > player.getSkill(SkillType.PRAYER).getLevelWithBonuses()) {
            throw new IllegalArgumentException("You do not meet the level requirement for this prayer.");
        }

        // collect conflicts without modifying the list while iterating
        List<Prayer> conflicts = new ArrayList<>();
        for (Prayer current : new ArrayList<>(prayers)) {
            if (current.getConflictingPrayers().contains(prayer) || prayer.getConflictingPrayers().contains(current)) {
                conflicts.add(current);
            }
        }

        // remove all conflicts at once
        if (!conflicts.isEmpty()) {
            prayers.removeAll(conflicts);
        }


        if (active) {
            if (!prayers.contains(prayer)) {
                if (player.getPrayerEnergy() <= 0) {
                    throw new IllegalStateException("Not enough prayer energy to activate this prayer.");
                }

                if (player.getSkill(SkillType.PRAYER).getLevelWithBonuses() < prayer.getLevelRequirement()) {
                    throw new IllegalStateException("Your prayer level is too low to activate this prayer.");
                }
                prayers.add(prayer);
            }
        } else {
            prayers.remove(prayer);
        }

        if (active) {
            SoundType.PRAYER_ACTIVATED.play(player, false);
        } else {
            SoundType.PRAYER_DEACTIVATED.play(player, false);
        }

        activePrayers.put(player.getUUID(), prayers);
        RuneQuestPlugin.getGearModifierHandler().recalculatePlayerModifiers(player, player.asBukkitPlayer().getInventory().getItemInMainHand());
    }

    public boolean getIsPrayerActive(UUID playerUUID, Prayer prayer) {
        List<Prayer> prayers = activePrayers.getOrDefault(playerUUID, new ArrayList<>());
        return prayers.contains(prayer);
    }

    public boolean hasActivePrayers(UUID playerUUID) {
        List<Prayer> prayers = activePrayers.getOrDefault(playerUUID, new ArrayList<>());
        return !prayers.isEmpty();
    }

    public List<Prayer> getActivePrayers(UUID playerUUID) {
        return activePrayers.getOrDefault(playerUUID, new ArrayList<>());
    }

}
