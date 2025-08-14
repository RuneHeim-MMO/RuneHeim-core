package org.zuma.examplePlugin.domain.player;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.constants.Color;
import org.zuma.examplePlugin.core.logger.constants.LogLevel;
import org.zuma.examplePlugin.core.utils.TextUtils;
import org.zuma.examplePlugin.core.utils.XpUtils;
import org.zuma.examplePlugin.domain.gui.ItemStackSlotPair;
import org.zuma.examplePlugin.domain.gui.serializer.ItemContainerContentSerializer;
import org.zuma.examplePlugin.domain.player.persistence.RunePlayerRepository;
import org.zuma.examplePlugin.domain.player.states.RunePlayerLoadingStateHandler;
import org.zuma.examplePlugin.domain.skill.Skill;
import org.zuma.examplePlugin.domain.skill.constants.SkillType;
import org.zuma.examplePlugin.events.player.RunePlayerLoadedEvent;
import org.zuma.examplePlugin.events.player.RunePlayerSavedEvent;

import javax.annotation.Nullable;
import javax.security.auth.login.AccountNotFoundException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class RunePlayerManager {

    private static final int AUTOSAVE_DELAY_SECONDS = 60;
    private final Map<UUID, RunePlayer> players = new HashMap<>();
    private final RunePlayerLoadingStateHandler loadingStateHandler;
    private final RunePlayerRepository repository;

    public static final EnumMap<SkillType, Skill> defaultSkills = Arrays.stream(SkillType.values())
            .collect(Collectors.toMap(
                    Function.identity(),
                    type -> type == SkillType.HITPOINTS ? new Skill(type, XpUtils.getXpForLevel(10)) : new Skill(type, XpUtils.getXpForLevel(1)),
                    (a, b) -> b,
                    () -> new EnumMap<>(SkillType.class)
            ));

    public RunePlayerManager(RunePlayerRepository repository) {
        this.loadingStateHandler = new RunePlayerLoadingStateHandler(RuneQuestPlugin.getInstance());
        this.repository = repository;
    }

    // -------------------- Getters --------------------

    public @Nullable RunePlayer getRunePlayer(Player player) {
        return getRunePlayer(player.getUniqueId());
    }

    public @Nullable RunePlayer getRunePlayer(UUID uuid) {
        return players.get(uuid);
    }

    public Collection<RunePlayer> getOnlinePlayers() {
        return players.values();
    }

    // -------------------- Lifecycle --------------------

    public void handleOnEnable() {
        Bukkit.getOnlinePlayers().forEach(this::loadPlayerData);
        startAsyncSavingLoop();
    }

    public void handleOnDisable() {
        players.values().forEach(player -> savePlayerSync(player, player.asBukkitPlayer().getName(), true));
        players.clear();
    }

    private void startAsyncSavingLoop() {
        Bukkit.getScheduler().runTaskTimer(
                RuneQuestPlugin.getInstance(),
                this::saveAllPlayersAsync,
                AUTOSAVE_DELAY_SECONDS * 20,
                AUTOSAVE_DELAY_SECONDS * 20
        );
    }

    // -------------------- Player Loading --------------------

    public void loadPlayerData(Player player) {
        UUID uuid = player.getUniqueId();
        player.getInventory().clear();

        if (players.containsKey(uuid)) {
            kickPlayer(player, "Player " + uuid + " is already online.");
            return;
        }

        loadingStateHandler.setPlayerLoading(player);

        runAsync(() -> {
            RunePlayer runePlayer = null;
            List<ItemStackSlotPair> inventory = Collections.emptyList();

            try {
                runePlayer = repository.getPlayerByUUID(uuid);
                inventory = ItemContainerContentSerializer.deserializeItemContainerContent(repository.getInventoryByUUID(uuid));
            } catch (AccountNotFoundException e) {
                RuneQuestPlugin.getRuneLogger().log(LogLevel.INFO, "Creating new profile for " + player.getName());
                runePlayer = new RunePlayer(uuid, new EnumMap<>(defaultSkills));
                repository.upsertPlayer(runePlayer, player.getName(), "{}");
            } catch (IllegalAccessException e) {
                RuneQuestPlugin.getRuneLogger().log(LogLevel.CRITICAL, "Player " + player.getName() + " has been kicked because he is already online.");
                kickPlayer(player, e.getMessage());
                return;
            } catch (Exception e) {
                kickPlayer(player, "Failed to load data: " + e.getMessage());
                logCritical("Failed to load data for player " + player.getName(), e);
                return;
            }

            repository.setPlayerOnline(uuid, true);
            RunePlayer finalRunePlayer = runePlayer;
            List<ItemStackSlotPair> finalInventory = inventory;

            runSync(() -> {
                if (Bukkit.getPlayer(uuid) == null || finalRunePlayer == null) return;

                players.put(uuid, finalRunePlayer);
                ItemContainerContentSerializer.setPlayerInventoryContent(player, finalInventory);
                loadingStateHandler.setPlayerLoaded(player);
                Bukkit.getServer().getPluginManager().callEvent(new RunePlayerLoadedEvent(finalRunePlayer));
            });
        });
    }

    // -------------------- Player Saving --------------------

    public void saveAllPlayersAsync() {
        RuneQuestPlugin.getRuneLogger().log(LogLevel.INFO, "Auto-saving " + Bukkit.getOnlinePlayers().size() + " players.");
        Bukkit.getOnlinePlayers().forEach(p -> savePlayerDataAsync(p, p.getName(), false));
    }

    public void savePlayerDataAsync(Player player, String lastUsername, boolean unmount) {
        RunePlayer runePlayer = getRunePlayer(player.getUniqueId());

        if (loadingStateHandler.isPlayerLoading(player)) return;

        if (runePlayer == null) {
            kickPlayer(player, "Cannot save player data: instance not found.");
            return;
        }

        runAsync(() -> {
            try {
                repository.upsertPlayer(runePlayer, lastUsername, serializeInventory(player));

                if (unmount) repository.setPlayerOnline(player.getUniqueId(), false);

                runSync(() -> {
                    if (unmount) players.remove(player.getUniqueId());
                    Bukkit.getServer().getPluginManager().callEvent(new RunePlayerSavedEvent(player));
                });
            } catch (Exception e) {
                kickPlayer(player, "Failed to save data: " + e.getMessage());
                logCritical("Failed to save player data " + player.getName(), e);
            }
        });
    }

    public void savePlayerSync(RunePlayer player, String lastUsername, boolean unmount) {
        if (loadingStateHandler.isPlayerLoading(player.asBukkitPlayer())) return;

        repository.upsertPlayer(player, lastUsername, serializeInventory(player.asBukkitPlayer()));
        player.asBukkitPlayer().getInventory().clear();

        if (unmount) repository.setPlayerOnline(player.getUUID(), false);
    }

    // -------------------- Utilities --------------------

    private String serializeInventory(Player player) {
        return ItemContainerContentSerializer.serializeItemSlotPairs(
                ItemContainerContentSerializer.getPlayerInventoryContent(player)
        );
    }

    private void kickPlayer(Player player, String message) {
        runSync(() -> {
            Component kickScreen = TextUtils.getErrorKickScreen(message);
            player.kick(kickScreen);
            RuneQuestPlugin.getRuneLogger().log(LogLevel.WARN, message);
        });
    }

    private void logCritical(String message, Exception e) {
        RuneQuestPlugin.getRuneLogger().log(LogLevel.CRITICAL, message, e);
    }

    private void runAsync(Runnable task) {
        Bukkit.getScheduler().runTaskAsynchronously(RuneQuestPlugin.getInstance(), task);
    }

    private void runSync(Runnable task) {
        Bukkit.getScheduler().runTask(RuneQuestPlugin.getInstance(), task);
    }

    public void handlePlayerDisconnect(Player player) {
        loadingStateHandler.setPlayerLoaded(player); // restore effects if they disconnected while loading
    }
}
