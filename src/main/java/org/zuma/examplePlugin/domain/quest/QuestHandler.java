package org.zuma.examplePlugin.domain.quest;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import org.bukkit.Bukkit;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.logger.constants.LogLevel;
import org.zuma.examplePlugin.domain.player.RunePlayer;
import org.zuma.examplePlugin.domain.quest.base.AbstractQuestStep;
import org.zuma.examplePlugin.domain.quest.base.Tickable;
import org.zuma.examplePlugin.domain.quest.persistence.QuestProgressRepository;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

public class QuestHandler {
    private final QuestProgressRepository repository;
    private final QuestRegistry registry;
    private final Gson gson;

    public QuestHandler(QuestProgressRepository repository, QuestRegistry registry) {
        this.repository = repository;
        this.registry = registry;
        this.gson = new Gson();

        startTicker();
    }

    private void startTicker() {
        Bukkit.getScheduler().runTaskTimerAsynchronously(RuneQuestPlugin.getInstance(), this::tickTickables, 0L, 20L * 5); // Runs every 5 seconds
    }

    private void tickTickables() {
        List<Tickable> tickableList =
                RuneQuestPlugin.getPlayerManager()
                        .getOnlinePlayers()
                        .stream()
                        .flatMap(p -> p.getActiveQuestSteps().values().stream())
                        .flatMap(step -> step instanceof Tickable t ? Stream.of(t) : Stream.empty())
                        .toList();


        for (Tickable tickable : tickableList) {
            tickable.tick();
        }
    }

    private HashMap<String, PlayerQuestState> convertPlayerStepsToState(RunePlayer player) {
        HashMap<String, PlayerQuestState> map = new HashMap<>();

        player.getActiveQuestSteps().forEach((key, step) -> {
            PlayerQuestState state = step.saveValuesToState(new PlayerQuestState(step.getParentQuest().getId(), step.getId(), step.getType()));
            map.put(key, state);
        });

        return map;
    }

    private AbstractQuestStep initializeStepFromState(UUID ownerUUID, Quest parentQuest, PlayerQuestState playerQuestState) {
        AbstractQuestStep matchingStep = parentQuest.getSteps().get(playerQuestState.getStepId());

        if (matchingStep == null) {
            throw new IllegalStateException("Cannot find matching step in " + playerQuestState.getQuestId() + " quest, step id: " + playerQuestState.getStepId());
        }

        return matchingStep.cloneForPlayer(ownerUUID).initializeValuesFromState(playerQuestState);
    }

    public void savePlayerQuestData(RunePlayer player, boolean async) {
        if (async) {
            Bukkit.getScheduler().runTaskAsynchronously(RuneQuestPlugin.getInstance(), () -> {
                try {
                    savePlayerQuestDataSync(player);
                } catch (Exception e) {
                    RuneQuestPlugin.getRuneLogger().log(LogLevel.CRITICAL, "Failed saving players " + player.getUUID() + " quest data.", e);
                }
            });
            return;
        }

        try {
            savePlayerQuestDataSync(player);
        } catch (Exception e) {
            RuneQuestPlugin.getRuneLogger().log(LogLevel.CRITICAL, "Failed saving players " + player.getUUID() + " quest data.", e);
            throw e;
        }
    }

    private void savePlayerQuestDataSync(RunePlayer player) {
        try {
            String json = this.gson.toJson(convertPlayerStepsToState(player));
            this.repository.save(player.getUUID(), player.getTrackedQuestId(), json);
        } catch (Exception e) {
            RuneQuestPlugin.getRuneLogger().log(LogLevel.CRITICAL, "Failed loading players " + player.getUUID() + " quest data.", e);
            throw e;
        }
    }

    public HashMap<String, PlayerQuestState> getPlayerQuestDataSync(UUID uuid) {
        String json = this.repository.getByUUID(uuid);

        if (json == null) return new HashMap<>();

        Type type = new TypeToken<HashMap<String, PlayerQuestState>>() {
        }.getType();

        return this.gson.fromJson(json, type);
    }

    public HashMap<String, AbstractQuestStep> initQuestStepInstances(UUID ownerUUID, HashMap<String, PlayerQuestState> state) {
        HashMap<String, AbstractQuestStep> result = new HashMap<>();

        state.forEach((key, value) -> {
            Quest matchingQuest = this.registry.getQuestById(key);

            if (matchingQuest == null) {
                RuneQuestPlugin.getRuneLogger().log(LogLevel.CRITICAL, "Failed to initialize quest steps for player. Quest with id " + key + " does not exist.");
                return;
            }

            try {
                AbstractQuestStep step = initializeStepFromState(ownerUUID, matchingQuest, value);
                result.put(key, step);
            } catch (Exception err) {
                RuneQuestPlugin.getRuneLogger().log(LogLevel.CRITICAL, "Failed to initialize quest step for player. Quest id: " + key + ", step id: " + value.getStepId() + ". Skipping.", err);
            }
        });

        return result;
    }

    public QuestRegistry getRegistry() {
        return registry;
    }


}
