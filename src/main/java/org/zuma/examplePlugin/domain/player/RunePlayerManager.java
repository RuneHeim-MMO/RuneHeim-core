package org.zuma.examplePlugin.domain.player;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.constants.Color;
import org.zuma.examplePlugin.core.logger.constants.LogLevel;
import org.zuma.examplePlugin.core.utils.TextUtils;
import org.zuma.examplePlugin.core.utils.XpUtils;
import org.zuma.examplePlugin.domain.player.states.RunePlayerLoadingStateHandler;
import org.zuma.examplePlugin.domain.skill.Skill;
import org.zuma.examplePlugin.domain.skill.constants.SkillType;
import org.zuma.examplePlugin.events.player.RunePlayerLoadedEvent;
import org.zuma.examplePlugin.events.player.RunePlayerSavedEvent;

import javax.annotation.Nullable;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class RunePlayerManager {
    private static final int AUTOSAVE_DELAY_SECONDS = 60;
    private final Map<UUID, RunePlayer> players = new HashMap<>();
    private final RunePlayerLoadingStateHandler loadingStateHandler;

    private static final EnumMap<SkillType, Skill> defaultSkills = Arrays.stream(SkillType.values())
            .collect(Collectors.toMap(
                    Function.identity(),
                    type -> type == SkillType.HITPOINTS ? new Skill(type, XpUtils.getXpForLevel(10)) : new Skill(type, XpUtils.getXpForLevel(1)),
                    (a, b) -> b,
                    () -> new EnumMap<>(SkillType.class)
            ));

    public RunePlayerManager() {
        loadingStateHandler = new RunePlayerLoadingStateHandler(RuneQuestPlugin.getInstance());
    }

    public @Nullable RunePlayer getRunePlayer(Player player) {
        return this.getRunePlayer(player.getUniqueId());
    }

    public @Nullable RunePlayer getRunePlayer(UUID uuid) {
        return players.get(uuid);
    }

    public Collection<RunePlayer> getOnlinePlayers() {
        return players.values();
    }

    public void handleOnEnable() {
        Bukkit.getOnlinePlayers().forEach(this::loadPlayerData);
        startAsyncSavingLoop();
    }

    public void saveAllPlayers() {
        RuneQuestPlugin.getRuneLogger().log(LogLevel.INFO, "Auto-saving " + Bukkit.getOnlinePlayers().size() + " players data.");
        Bukkit.getOnlinePlayers().forEach(player -> savePlayerData(player, false));
    }

    private void startAsyncSavingLoop() {
        Bukkit.getScheduler().runTaskTimer(RuneQuestPlugin.getInstance(), this::saveAllPlayers, AUTOSAVE_DELAY_SECONDS * 20, AUTOSAVE_DELAY_SECONDS * 20);
    }

    public void loadPlayerData(Player player) {
        UUID uuid = player.getUniqueId();

        if (players.containsKey(uuid)) {
            Component kickScreen = TextUtils.getErrorKickScreen("Player " + uuid + " is already online.");
            players.get(uuid).asBukkitPlayer().kick(kickScreen);
            player.kick(kickScreen);
            return;
        }

        loadingStateHandler.setPlayerLoading(player);

        Bukkit.getScheduler().runTaskAsynchronously(RuneQuestPlugin.getInstance(), () -> {
            try {

                // TODO: Deserialize & initialize player
                RunePlayer runePlayer = new RunePlayer(uuid, new EnumMap<>(defaultSkills));

                Bukkit.getScheduler().runTask(RuneQuestPlugin.getInstance(), () -> {
                    if (Bukkit.getPlayer(uuid) == null) {
                        return;
                    }

                    players.put(uuid, runePlayer);
                    loadingStateHandler.setPlayerLoaded(player);
                    Bukkit.getServer().getPluginManager().callEvent(new RunePlayerLoadedEvent(runePlayer));
                });

            } catch (Exception e) {
                Bukkit.getScheduler().runTask(RuneQuestPlugin.getInstance(), () -> {
                    player.kick(TextUtils.getErrorKickScreen("Failed to load data for player " + player.getName() + "\n" + e.getMessage()));
                    RuneQuestPlugin.getRuneLogger().log(LogLevel.CRITICAL, "Failed to load data for player " + player.getName(),e);
                });
            }
        });

    }

    public void savePlayerData(Player player, boolean unmount) {
        UUID uuid = player.getUniqueId();

        if (!players.containsKey(uuid)) {
            player.kick(TextUtils.getErrorKickScreen("Cannot save player " + uuid + ". RuneQuestPlayer instance not found."));
            return;
        }

        Bukkit.getScheduler().runTaskAsynchronously(RuneQuestPlugin.getInstance(), () -> {
            try {

                // TODO: Serialize and save player

                Bukkit.getScheduler().runTask(RuneQuestPlugin.getInstance(), () -> {
                    if (unmount) {
                        players.remove(uuid);
                    }
                    Bukkit.getServer().getPluginManager().callEvent(new RunePlayerSavedEvent(player));
                });


            } catch (Exception e) {
                Bukkit.getScheduler().runTask(RuneQuestPlugin.getInstance(), () -> {
                    player.kick(TextUtils.getBrandKickScreen(Component.text("Failed to save player data " + player.getName() + "\n" + e.getMessage()).color(Color.ERROR.asColor())));
                    RuneQuestPlugin.getRuneLogger().log(LogLevel.CRITICAL, "Failed to save player data " + player.getName(),e);
                });
            }
        });


    }

    public void handlePlayerDisconnect(Player player) {
        loadingStateHandler.setPlayerLoaded(player); // Restore effects before disconnection if player disconnected while he was loading.
    }

    public void handleActionInterruption(Player player) {
        RunePlayer runePlayer = players.get(player.getUniqueId());

        if (runePlayer == null) {
            return;
        }

        runePlayer.cancelActionTask();
    }
}
