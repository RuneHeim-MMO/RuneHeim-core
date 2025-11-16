package org.zuma.examplePlugin.domain.quest.base;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.constants.SoundType;
import org.zuma.examplePlugin.core.logger.constants.LogLevel;
import org.zuma.examplePlugin.domain.hud.HudService;
import org.zuma.examplePlugin.domain.player.RunePlayer;
import org.zuma.examplePlugin.domain.quest.PlayerQuestState;
import org.zuma.examplePlugin.domain.quest.Quest;
import org.zuma.examplePlugin.domain.quest.constants.StepType;
import org.zuma.examplePlugin.events.dialog.QuestStepToBeCompleted;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public abstract class AbstractQuestStep implements Listener {
    private transient RuneQuestPlugin plugin;
    private transient Quest parentQuest;
    private int id;
    private final StepType type;
    @Nullable
    private final String description;
    private String stableId;

    @Nullable
    private final UUID owner;

    public AbstractQuestStep(RuneQuestPlugin plugin, Quest parentQuest, StepType type, @Nullable String description, String stableId, @Nullable UUID owner) { // if owner is null, then this is a 'dummy' quest used for displaying to players.
        this.plugin = plugin;
        this.parentQuest = parentQuest;
        this.type = type;
        this.owner = owner;
        this.description = description;
        this.stableId = stableId;

        if (getOwner() != null) {
            plugin.getServer().getPluginManager().registerEvents(this, plugin);
        }
    }

    @EventHandler
    public void onPlayerDisconnect(PlayerQuitEvent event) {
        if (owner == null || !event.getPlayer().getUniqueId().equals(owner))
            return;

        HandlerList.unregisterAll(this);
    }

    @EventHandler
    public void onDialogStepCompleteTriggered(QuestStepToBeCompleted event) {
        if (!event.getPlayerUUID().equals(getOwner()) || !Objects.equals(event.getStableDialogStepId(), this.stableId))
            return;
        this.handleStepCompletion();
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

        AbstractQuestStep nextStepInstance = nextQuestStep.cloneForPlayer(runePlayer.getUUID());
        runePlayer.getActiveQuestSteps().put(parentQuest.getId(), nextStepInstance);

        if (runePlayer.getTrackedQuestId() != null && runePlayer.getTrackedQuestId().equals(parentQuest.getId())) {
            runePlayer.getBossBarManager().setText(nextStepInstance.getDescription());
        }

        if (!this.parentQuest.isSilent()) {
            HudService.sendQuestStepTaskMessage(runePlayer, nextQuestStep);
        }
    }

    public abstract AbstractQuestStep cloneForPlayer(UUID ownerUUID);


    public AbstractQuestStep initializeValuesFromState(PlayerQuestState state) {
        this.stableId = state.getStepProgressData().get("stableId").toString();
        return assignValuesFromState(state);
    }


    public PlayerQuestState saveValuesToState(PlayerQuestState state) {
        Map<String, Object> toBeMutated = state.getStepProgressData();
        toBeMutated.put("stableId", this.stableId);
        return assignValuesToState(state);
    }


    protected abstract AbstractQuestStep assignValuesFromState(PlayerQuestState state);

    protected abstract PlayerQuestState assignValuesToState(PlayerQuestState state);

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

    public String getStableId() {
        return stableId;
    }

    public void setPlugin(RuneQuestPlugin plugin) {
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

    @Nullable
    public String getDescription() {
        return description;
    }
}
