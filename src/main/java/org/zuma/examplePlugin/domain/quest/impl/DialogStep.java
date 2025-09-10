package org.zuma.examplePlugin.domain.quest.impl;

import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.domain.quest.PlayerQuestState;
import org.zuma.examplePlugin.domain.quest.Quest;
import org.zuma.examplePlugin.domain.quest.StepType;
import org.zuma.examplePlugin.domain.quest.base.AbstractQuestStep;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.UUID;

public class DialogStep extends AbstractQuestStep {
    /*
     * Dialog steps are calculated by id, this stableId allows existing dialogs
     * to keep setting completion of this step even if order of steps changes. Also makes it easier to track.
     */
    private String stableId;
    private boolean completed;

    public DialogStep(RuneQuestPlugin plugin, Quest parentQuest, @Nullable UUID owner, String stableId) {
        super(plugin, parentQuest, StepType.DIALOG, owner);
        this.stableId = stableId;

        if (getOwner() != null) {
            plugin.getServer().getPluginManager().registerEvents(this, plugin);
        }
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;

        if (completed) {
            this.handleStepCompletion();
        }
    }

    public String getStableId() {
        return stableId;
    }

    @Override
    public AbstractQuestStep cloneForPlayer() {
        return new DialogStep(super.getPlugin(), super.getParentQuest(), super.getOwner(), stableId);
    }

    @Override
    public AbstractQuestStep assignValuesFromState(PlayerQuestState state) {
        this.stableId = state.getStepProgressData().get("stableId").toString();
        return this;
    }

    @Override
    public PlayerQuestState assignValuesToState(PlayerQuestState state) {
        Map<String, Object> toBeMutated = state.getStepProgressData();
        toBeMutated.put("stableId", this.stableId);
        return state;
    }
}
