package org.zuma.examplePlugin.domain.player;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.analytics.AnalyticsContainer;
import org.zuma.examplePlugin.core.analytics.AnalyticsHandler;
import org.zuma.examplePlugin.core.analytics.AnalyticsKey;
import org.zuma.examplePlugin.core.analytics.AnalyticsType;
import org.zuma.examplePlugin.core.permissions.Role;
import org.zuma.examplePlugin.core.permissions.roles.BaseRole;
import org.zuma.examplePlugin.core.permissions.roles.UpgradedRole;
import org.zuma.examplePlugin.core.logger.constants.LogLevel;
import org.zuma.examplePlugin.core.utils.TextUtils;
import org.zuma.examplePlugin.core.utils.XpUtils;
import org.zuma.examplePlugin.core.exception.PlayerDataLoadException;
import org.zuma.examplePlugin.domain.daily.DailyRewardHandler;
import org.zuma.examplePlugin.domain.daily.DailyRewards;
import org.zuma.examplePlugin.domain.payments.SupporterLevel;
import org.zuma.examplePlugin.domain.player.persistence.DeserializedPlayerEntity;
import org.zuma.examplePlugin.domain.player.persistence.RunePlayerRepository;
import org.zuma.examplePlugin.domain.player.persistence.RunePlayerSerializer;
import org.zuma.examplePlugin.domain.player.persistence.SerializedPlayerContainer;
import org.zuma.examplePlugin.domain.player.states.PlayerLoadingStateHandler;
import org.zuma.examplePlugin.domain.quest.PlayerQuestState;
import org.zuma.examplePlugin.domain.quest.QuestHandler;
import org.zuma.examplePlugin.domain.quest.base.AbstractQuestStep;
import org.zuma.examplePlugin.domain.skill.Skill;
import org.zuma.examplePlugin.domain.skill.constants.SkillType;
import org.zuma.examplePlugin.events.player.RunePlayerLoadedEvent;
import org.zuma.examplePlugin.events.player.RunePlayerSavedEvent;

import javax.annotation.Nullable;
import javax.security.auth.login.AccountNotFoundException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class RunePlayerManager {

    private static final int AUTOSAVE_DELAY_SECONDS = 60;
    private final ConcurrentHashMap<UUID, RunePlayer> players = new ConcurrentHashMap<>();
    private final Set<UUID> uuidsAccessingDB = ConcurrentHashMap.newKeySet();
    private final PlayerLoadingStateHandler loadingStateHandler;
    private final RunePlayerRepository repository;
    private final QuestHandler questHandler;
    private final DailyRewardHandler dailyRewardHandler;
    private final AnalyticsHandler analyticsHandler;

    public RunePlayerManager(RunePlayerRepository repository, QuestHandler questHandler, DailyRewardHandler dailyRewardHandler, AnalyticsHandler analyticsHandler) {
        this.loadingStateHandler = new PlayerLoadingStateHandler(RuneQuestPlugin.getInstance());
        this.repository = repository;
        this.questHandler = questHandler;
        this.dailyRewardHandler = dailyRewardHandler;
        this.analyticsHandler = analyticsHandler;
    }

    public static EnumMap<SkillType, Skill> createDefaultSkills() {
        EnumMap<SkillType, Skill> map = new EnumMap<>(SkillType.class);
        for (SkillType type : SkillType.values()) {
            int xp = type == SkillType.HITPOINTS ? XpUtils.getXpForLevel(10) : XpUtils.getXpForLevel(1);
            map.put(type, new Skill(type, xp));
        }
        return map;
    }

    // -------------------- Getters --------------------

    public @Nullable RunePlayer getRunePlayer(Player player) {
        if (player == null) return null;
        return getRunePlayer(player.getUniqueId());
    }

    public @Nullable RunePlayer getRunePlayer(UUID uuid) {
        return players.get(uuid);
    }

    public Collection<RunePlayer> getOnlinePlayers() {
        return new ArrayList<>(players.values());
    }

    // -------------------- Lifecycle --------------------

    public void handleOnEnable() {
        Bukkit.getOnlinePlayers().forEach(this::loadPlayerData);
        startAsyncSavingLoop();
    }

    public void handleOnDisable() {
        players.values().forEach(player -> {
            savePlayerSync(player, true);
            player.getBossBarManager().removeBar();
        });

        players.clear();

        RuneQuestPlugin.getRuneLogger().log(LogLevel.INFO, "All players saved and unmounted.");
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
        wipeInventory(player);

        if (players.containsKey(uuid)) {
            kickPlayer(player, "Player " + uuid + " is already online.", false);
            return;
        }

        loadingStateHandler.setPlayerLoading(player);
        uuidsAccessingDB.add(uuid);

        runAsync(() -> {

            try {
                AbstractMap.SimpleEntry<RunePlayer, DeserializedPlayerEntity> data = fetchOrCreatePlayerData(player);
                RunePlayer runePlayer = data.getKey();

                BigDecimal totalPayments = RuneQuestPlugin.getPaymentHandler().getTotalPayments(player.getUniqueId());
                runePlayer.setSupporterLevel(SupporterLevel.getLevelByAmount(totalPayments.doubleValue()));
                HashMap<String, PlayerQuestState> questState = questHandler.getPlayerQuestDataSync(uuid);
                HashMap<String, AbstractQuestStep> questSteps = questHandler.initQuestStepInstances(uuid, questState);
                DailyRewards dailyRewards = dailyRewardHandler.getPlayerDailyRewardDataSync(uuid);
                AnalyticsContainer analyticsContainer = analyticsHandler.loadAnalyticsSync(AnalyticsType.PLAYER, uuid.toString());

                runSync(() -> {
                    if (!player.isOnline()) {
                        cleanup(uuid);
                        return;
                    }

                    runePlayer.setDailyRewards(dailyRewards);
                    runePlayer.setActiveQuestSteps(questSteps);
                    runePlayer.setAnalyticsContainer(analyticsContainer);
                    setPlayerOnlineAndFinalize(player, data);
                });

            } catch (Exception e) {
                RuneQuestPlugin.getRuneLogger().log(LogLevel.CRITICAL, "Error loading player data for " + player.getName(), e);
                runSync(() -> kickPlayer(player, e.getMessage(), true));
                cleanup(uuid);
            }
        });
    }

    private AbstractMap.SimpleEntry<RunePlayer, DeserializedPlayerEntity> fetchOrCreatePlayerData(Player player) throws PlayerDataLoadException {
        UUID uuid = player.getUniqueId();

        try {
            return RunePlayerSerializer.deserializePlayerSync(repository.getPlayerByUUID(uuid));
        } catch (AccountNotFoundException e) {
            return createNewProfile(player);
        } catch (IllegalAccessException e) {
            RuneQuestPlugin.getRuneLogger().log(LogLevel.CRITICAL, "Player " + player.getName() + " kicked: DB online flag already set.");
            throw new PlayerDataLoadException(e.getMessage(), false);
        } catch (Exception e) {
            RuneQuestPlugin.getRuneLogger().log(LogLevel.CRITICAL, "Failed to load data for " + player.getName(), e);
            throw new PlayerDataLoadException("Failed to load data: " + e.getMessage(), true);
        }
    }

    private AbstractMap.SimpleEntry<RunePlayer, DeserializedPlayerEntity> createNewProfile(Player player) throws PlayerDataLoadException {
        UUID uuid = player.getUniqueId();
        try {
            RuneQuestPlugin.getRuneLogger().log(LogLevel.INFO, "Creating new profile for " + player.getName());
            RunePlayer newPlayer = new RunePlayer(uuid, new EnumMap<>(createDefaultSkills()));
            SerializedPlayerContainer serialized = RunePlayerSerializer.serializePlayerSync(newPlayer, player, false);
            repository.upsertPlayer(serialized, false);
            return new AbstractMap.SimpleEntry<>(newPlayer, new DeserializedPlayerEntity(new ArrayList<>(), player.getLocation(), true));
        } catch (Exception e) {
            kickPlayer(player, "Failed creating new profile.", true);
            RuneQuestPlugin.getRuneLogger().log(LogLevel.CRITICAL, "Failed creating new player for " + player.getName(), e);
            throw new PlayerDataLoadException("Failed creating new user profile.", true);
        }
    }

    private void setPlayerOnlineAndFinalize(Player player, AbstractMap.SimpleEntry<RunePlayer, DeserializedPlayerEntity> data) {
        UUID uuid = player.getUniqueId();
        runAsync(() -> {
            try {
                repository.setPlayerOnline(uuid, true);
                runSync(() -> finalizePlayerLoad(player, data));
            } catch (Exception e) {
                runSync(() -> {
                    kickPlayer(player, "Failed to mark online: " + e.getMessage(), true);
                    RuneQuestPlugin.getRuneLogger().log(LogLevel.CRITICAL, "Failed to mark player online in DB for " + player.getName(), e);
                    cleanup(uuid);
                });
            }
        });
    }

    private void finalizePlayerLoad(Player player, AbstractMap.SimpleEntry<RunePlayer, DeserializedPlayerEntity> data) {
        UUID uuid = player.getUniqueId();
        RunePlayer runePlayer = data.getKey();
        DeserializedPlayerEntity playerData = data.getValue();

        if (!player.isOnline()) {
            cleanup(uuid);
            return;
        }
        players.put(uuid, runePlayer);
        RunePlayerSerializer.setPlayerInventoryContent(player, playerData.inventory());
        player.teleport(playerData.location());
        loadingStateHandler.setPlayerLoaded(player);
        runePlayer.setSurvivalMode();
        runePlayer.getDailyRewards().updateIfNeeded(runePlayer);
        runePlayer.getAnalyticsContainer().increment(AnalyticsKey.PLAYER_SESSIONS, 1);
        RuneQuestPlugin.getAnalytics().increment(AnalyticsKey.PLAYER_SESSIONS, 1);

        Bukkit.getScheduler().runTaskLater(RuneQuestPlugin.getInstance(), () -> {
            runePlayer.getRole().reloadPermissions(runePlayer);
            runePlayer.reloadNametags();
        }, 1L);

        RuneQuestPlugin.getRuneLogger().log(LogLevel.INFO, "Player " + player.getName() + " (" + runePlayer.getCombatLvl() + "cmb) loaded successfully [" + runePlayer.getRole().getBaseRole().toString() + "].");
        RuneQuestPlugin.getGearModifierHandler().recalculatePlayerModifiers(runePlayer, player.getInventory().getItemInMainHand());

        Bukkit.getServer().getPluginManager().callEvent(new RunePlayerLoadedEvent(runePlayer, playerData.isFirstJoin()));
        cleanup(uuid);
    }

    private void cleanup(UUID uuid) {
        uuidsAccessingDB.remove(uuid);
    }


    // -------------------- Player Saving --------------------

    public void saveAllPlayersAsync() {
        AtomicInteger remaining = new AtomicInteger(players.size());
        RuneQuestPlugin.getRuneLogger().log(LogLevel.INFO, "Auto-saving " + players.size() + " players.");

        Bukkit.getOnlinePlayers().forEach(p -> {
            savePlayerDataAsync(p, false, false, () -> {
                if (remaining.decrementAndGet() == 0) {
                    RuneQuestPlugin.getRuneLogger().log(LogLevel.INFO, "Auto-saved " + players.size() + " players.");
                }
            }, false);
        });
    }

    private boolean waitForPlayerFree(UUID uuid, long timeoutMillis) {
        long start = System.currentTimeMillis();
        while (uuidsAccessingDB.contains(uuid)) {
            if (System.currentTimeMillis() - start >= timeoutMillis) {
                // Timeout reached
                RuneQuestPlugin.getRuneLogger().log(LogLevel.WARN, "Player " + uuid + " is still in uuidsAccessingDB after " + (timeoutMillis / 1000) + " seconds. Save operation aborted.");
                return false;
            }
            try {
                Thread.sleep(50); // wait 50ms before checking again
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return false; // abort if interrupted
            }
        }
        return true; // player is free
    }

    public void savePlayerDataAsync(Player player, boolean unmount, boolean log, @Nullable Runnable onComplete, boolean clearStateCache) {
        RunePlayer runePlayer = getRunePlayer(player.getUniqueId());
        UUID uuid = player.getUniqueId();

        if (loadingStateHandler.isPlayerLoading(player)) return;

        if (!player.isOnline()) {
            return;
        }

        if (runePlayer == null) {
            kickPlayer(player, "Cannot save player data: instance not found.", true);
            return;
        }

        SerializedPlayerContainer serializedContainer = RunePlayerSerializer.serializePlayerSync(runePlayer, player, clearStateCache);

        runAsync(() -> {
            if (uuidsAccessingDB.contains(uuid)) {
                if (!waitForPlayerFree(uuid, 60000)) {
                    return;
                }
            }

            uuidsAccessingDB.add(uuid);

            try {
                repository.upsertPlayer(serializedContainer, unmount);
                questHandler.savePlayerQuestData(runePlayer, false);
                dailyRewardHandler.savePlayerDailyRewardData(runePlayer, false);
                analyticsHandler.saveAnalyticsSync(runePlayer.getAnalyticsContainer());

                runSync(() -> {
                    if (unmount) players.remove(player.getUniqueId());
                    if (log)
                        RuneQuestPlugin.getRuneLogger().log(LogLevel.INFO, "Player " + player.getName() + " data has been saved. Unmounted: " + unmount);
                    if (onComplete != null) onComplete.run();
                    uuidsAccessingDB.remove(uuid);
                    Bukkit.getServer().getPluginManager().callEvent(new RunePlayerSavedEvent(player));
                });
            } catch (Exception e) {
                uuidsAccessingDB.remove(uuid);
                kickPlayer(player, "Failed to save data: " + e.getMessage(), true);
                RuneQuestPlugin.getRuneLogger().log(LogLevel.CRITICAL, "Failed to save player data " + player.getName(), e);
            }
        });
    }

    public void savePlayerSync(RunePlayer player, boolean unmount) {
        try {
            UUID uuid = player.getUUID();

            if (loadingStateHandler.isPlayerLoading(player.asBukkitPlayer())) return;
            if (uuidsAccessingDB.contains(uuid)) {
                if (!waitForPlayerFree(uuid, 5000)) {
                    return;
                }
            }

            uuidsAccessingDB.add(uuid);
            try {
                repository.upsertPlayer(RunePlayerSerializer.serializePlayerSync(player, player.asBukkitPlayer(), unmount), unmount);
                questHandler.savePlayerQuestData(player, false);
                dailyRewardHandler.savePlayerDailyRewardData(player, false);
                analyticsHandler.saveAnalyticsSync(player.getAnalyticsContainer());
                wipeInventory(player.asBukkitPlayer());

                RuneQuestPlugin.getRuneLogger().log(LogLevel.INFO, "Player " + player.asBukkitPlayer().getName() + " data has been saved. Unmounted: true");
            } finally {
                uuidsAccessingDB.remove(uuid);
            }
        } catch (Exception e) {
            RuneQuestPlugin.getRuneLogger().log(LogLevel.CRITICAL, "Failed to save player " + player.asBukkitPlayer().getName(), e);
        }
    }

    // -------------------- Utilities --------------------

    private void wipeInventory(Player player) {
        player.getInventory().clear();
        player.getInventory().setArmorContents(null);
        player.getInventory().setItemInOffHand(null);
    }

    private void kickPlayer(Player player, String message, boolean unexpected) {
        runSync(() -> {
            Component kickScreen = TextUtils.getErrorKickScreen(message, unexpected);
            player.kick(kickScreen);
        });
    }

    private void runAsync(Runnable task) {
        Bukkit.getScheduler().runTaskAsynchronously(RuneQuestPlugin.getInstance(), task);
    }

    private void runSync(Runnable task) {
        Bukkit.getScheduler().runTask(RuneQuestPlugin.getInstance(), task);
    }

    public void handlePlayerDisconnect(Player player) {
        try {
            loadingStateHandler.setPlayerLoaded(player); // restore effects if they disconnected while loading
            this.savePlayerDataAsync(player, true, true, null, true);
        } catch (Exception e) {
            RuneQuestPlugin.getRuneLogger().log(LogLevel.CRITICAL, "Failed to save player " + player.getName() + "!", e);
        }
    }

    public void writeNewUpgradedRoleToDatabaseSync(UUID uuid, UpgradedRole newRole) {
        repository.setPlayerUpgradedRole(uuid, newRole);
    }

    public Map.Entry<BaseRole, UpgradedRole> getPlayerRolesFromDatabaseSync(UUID uuid) {
        try {
            List<Role> roles = repository.getPlayerRoles(uuid);

            BaseRole baseRole = (BaseRole) roles.get(0);
            UpgradedRole upgradedRole = roles.size() > 1 ? (UpgradedRole) roles.get(1) : null;

            return new AbstractMap.SimpleEntry<>(baseRole, upgradedRole);
        } catch (Exception e) {
            RuneQuestPlugin.getRuneLogger().log(LogLevel.CRITICAL, "Failed extracting 2 roles from the database for " + uuid, e);
            throw e;
        }
    }

    public PlayerBombsContainer getPlayerBombsContainerFromDatabaseSync(UUID uuid) {
        return repository.getPlayerBombsContainer(uuid);
    }

    public void writeNewPlayerBombsContainerToDatabaseSync(UUID uuid, PlayerBombsContainer container) {
        repository.updatePlayerBombsContainer(uuid, container);
    }
}
