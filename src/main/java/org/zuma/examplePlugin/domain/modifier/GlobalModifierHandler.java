package org.zuma.examplePlugin.domain.modifier;

import org.bukkit.Bukkit;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.constants.SoundType;
import org.zuma.examplePlugin.core.logger.constants.LogLevel;
import org.zuma.examplePlugin.domain.hud.HudService;
import org.zuma.examplePlugin.domain.modifier.persistence.GlobalModifierRepository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class GlobalModifierHandler {
    private static final long TICK_MILLIS = 60000L; // Check every minute

    private final GlobalModifierRepository repository;
    private final ConcurrentHashMap<Integer, GlobalModifier> modifiers;

    public GlobalModifierHandler(GlobalModifierRepository repository) {
        this.repository = repository;
        this.modifiers = new ConcurrentHashMap<>();
    }

    public void startLoop() {
        RuneQuestPlugin.getRuneLogger().log(LogLevel.INFO, "[GlobalModifiers] Starting global modifier handler loop.");
        Bukkit.getScheduler().runTaskTimerAsynchronously(RuneQuestPlugin.getInstance(), this::reloadModifiers, 0, TICK_MILLIS / 50);
    }

    private void reloadModifiers() {
        List<Map.Entry<Integer, GlobalModifier>> fetchResult = this.repository.getAll();
        long now = System.currentTimeMillis();
        List<GlobalModifier> newModifiers = new ArrayList<>();
        List<GlobalModifier> expiredModifiers = new ArrayList<>();

        // Collect keys to remove
        List<Integer> keysToRemove = new ArrayList<>();
        for (Map.Entry<Integer, GlobalModifier> entry : this.modifiers.entrySet()) {
            if (fetchResult.stream().noneMatch(newModifier ->
                    Objects.equals(entry.getKey(), newModifier.getKey()) &&
                            Objects.equals(entry.getValue().label(), newModifier.getValue().label()) &&
                            Objects.equals(entry.getValue().amount(), newModifier.getValue().amount()) &&
                            Objects.equals(entry.getValue().activeUntilMillis(), newModifier.getValue().activeUntilMillis()))) {

                RuneQuestPlugin.getRuneLogger().log(LogLevel.INFO, "[GlobalModifiers] Active global modifier was deactivated ['" + entry.getValue().label() + "' (" + entry.getValue().type() + " : " + entry.getValue().amount() + ")].");
                keysToRemove.add(entry.getKey());
                expiredModifiers.add(entry.getValue());
            }

            if (entry.getValue().activeUntilMillis() < now) {
                RuneQuestPlugin.getRuneLogger().log(LogLevel.INFO, "[GlobalModifiers] Global modifier expired ['" + entry.getValue().label() + "' (" + entry.getValue().type() + " : " + entry.getValue().amount() + ")]");
                keysToRemove.add(entry.getKey());
                expiredModifiers.add(entry.getValue());
            }
        }

        // Remove after iteration
        for (Integer key : keysToRemove) {
            modifiers.remove(key);
        }

        if (!keysToRemove.isEmpty()) {
            updateAllPlayers();
        }

        // Add new modifiers
        for (Map.Entry<Integer, GlobalModifier> entry : fetchResult) {
            if (!modifiers.containsKey(entry.getKey()) && entry.getValue().activeUntilMillis() > now) {
                RuneQuestPlugin.getRuneLogger().log(LogLevel.INFO, "[GlobalModifiers] New global modifier was activated ['" + entry.getValue().label() + "' (" + entry.getValue().type() + " : " + entry.getValue().amount() + ")]");
                modifiers.put(entry.getKey(), entry.getValue());
                newModifiers.add(entry.getValue());
            }
        }

        if (!expiredModifiers.isEmpty()) {
            Bukkit.getScheduler().runTask(RuneQuestPlugin.getInstance(), () -> {
                RuneQuestPlugin.getPlayerManager().getOnlinePlayers().forEach(p -> {
                    HudService.globalModifiersExpired(p, expiredModifiers);
                    SoundType.GLOBAL_EFFECT_EXPIRED.play(p, true);
                });
            });
        }

        if (!newModifiers.isEmpty()) {
            Bukkit.getScheduler().runTask(RuneQuestPlugin.getInstance(), () -> {
                RuneQuestPlugin.getPlayerManager().getOnlinePlayers().forEach(p -> {
                    HudService.globalModifiersActivated(p, newModifiers);
                    SoundType.GLOBAL_EFFECT_ACTIVATED.play(p, true);
                });
            });
        }

    }

    private void updateAllPlayers() {
        Bukkit.getScheduler().runTask(RuneQuestPlugin.getInstance(), () -> {
            RuneQuestPlugin.getPlayerManager().getOnlinePlayers().forEach(player -> {
                RuneQuestPlugin.getGearModifierHandler().recalculatePlayerModifiers(player, player.asBukkitPlayer().getInventory().getItemInMainHand());
            });
        });
    }

    public Collection<GlobalModifier> getActiveModifiers() {
        return modifiers.values();
    }
}
