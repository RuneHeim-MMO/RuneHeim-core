package org.zuma.examplePlugin.domain.quest.base;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.logger.constants.LogLevel;
import org.zuma.examplePlugin.domain.player.RunePlayer;
import org.zuma.examplePlugin.domain.quest.PlayerQuestState;
import org.zuma.examplePlugin.domain.quest.Quest;
import org.zuma.examplePlugin.domain.quest.StepType;

import javax.annotation.Nullable;
import java.util.UUID;

public abstract class AbstractQuestStep implements Listener {
    private RuneQuestPlugin plugin;
    private final Quest parentQuest;
    private int id;
    private final StepType type;

    @Nullable
    private final UUID owner;

    public AbstractQuestStep(RuneQuestPlugin plugin, Quest parentQuest, StepType type, @Nullable UUID owner) { // if owner is null, then this is a 'dummy' quest used for displaying to players.
        this.plugin = plugin;
        this.parentQuest = parentQuest;
        this.type = type;
        this.owner = owner;
    }

    protected void handleStepCompletion() {
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

        // Do runeplayer stuff
    }

    public abstract AbstractQuestStep cloneForPlayer();

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

    public Quest getParentQuest() {
        return parentQuest;
    }

    @Nullable
    public UUID getOwner() {
        return owner;
    }
}
