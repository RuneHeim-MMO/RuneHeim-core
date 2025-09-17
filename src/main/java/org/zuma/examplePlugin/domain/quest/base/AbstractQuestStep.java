package org.zuma.examplePlugin.domain.quest.base;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.logger.constants.LogLevel;
import org.zuma.examplePlugin.domain.player.RunePlayer;
import org.zuma.examplePlugin.domain.quest.PlayerQuestState;
import org.zuma.examplePlugin.domain.quest.Quest;
import org.zuma.examplePlugin.domain.quest.constants.StepType;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

public abstract class AbstractQuestStep implements Listener {
    private transient RuneQuestPlugin plugin;
    private transient Quest parentQuest;
    private int id;
    private final StepType type;
    private final String description;

    @Nullable
    private final UUID owner;

    public AbstractQuestStep(RuneQuestPlugin plugin, Quest parentQuest, StepType type, String description, @Nullable UUID owner) { // if owner is null, then this is a 'dummy' quest used for displaying to players.
        this.plugin = plugin;
        this.parentQuest = parentQuest;
        this.type = type;
        this.owner = owner;
        this.description = description;

        if (getOwner() != null) {
            plugin.getServer().getPluginManager().registerEvents(this, plugin);
        }
    }

    protected void handleStepCompletion() {
        HandlerList.unregisterAll(this);

        if (owner == null) {
            RuneQuestPlugin.getRuneLogger().log(LogLevel.WARN, "Cannot advance " + parentQuest.getId() + " quest. Owner is not present.");
            return;
        }

        Player bukkitPlayer = plugin.getServer().getPlayer(owner);

        if (bukkitPlayer == null || !bukkitPlayer.isOnline()) {
            RuneQuestPlugin.getRuneLogger().log(LogLevel.WARN, "Cannot advance " + parentQuest.getId() + " quest. Owner is offline.");
            return;
        }

        RunePlayer runePlayer = RuneQuestPlugin.getPlayerManager().getRunePlayer(bukkitPlayer);

        if (runePlayer == null) {
            RuneQuestPlugin.getRuneLogger().log(LogLevel.WARN, "Cannot advance " + parentQuest.getId() + " quest. Owners RunePlayer instance is null (Still loading?).");
            return;
        }

        List<AbstractQuestStep> steps = parentQuest.getSteps();
        int nextIndex = this.id + 1;

        AbstractQuestStep nextQuestStep = (nextIndex < steps.size())
                ? steps.get(nextIndex)
                : null;

        if (nextQuestStep == null) {
            parentQuest.handleCompletion(runePlayer);
            return;
        }

        runePlayer.getActiveQuestSteps().put(parentQuest.getId(), nextQuestStep.cloneForPlayer(runePlayer.getUUID()));

    }

    public abstract AbstractQuestStep cloneForPlayer(UUID ownerUUID);

    public abstract AbstractQuestStep assignValuesFromState(PlayerQuestState state);

    public abstract PlayerQuestState assignValuesToState(PlayerQuestState state);

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public StepType getType() {
        return type;
    }

    public RuneQuestPlugin getPlugin() {
        return plugin;
    }

    public void setPlugin(RuneQuestPlugin plugin) {
        System.out.println("plugin set");
        this.plugin = plugin;
    }

    public Quest getParentQuest() {
        return parentQuest;
    }

    public void setParentQuest(Quest parentQuest) {
        this.parentQuest = parentQuest;
    }

    @Nullable
    public UUID getOwner() {
        return owner;
    }

    public String getDescription() {
        return description;
    }
}
