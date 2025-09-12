package org.zuma.examplePlugin.domain.quest.impl;

import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.domain.quest.PlayerQuestState;
import org.zuma.examplePlugin.domain.quest.Quest;
import org.zuma.examplePlugin.domain.quest.constants.StepType;
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
    private transient boolean completed;

    public DialogStep(RuneQuestPlugin plugin, Quest parentQuest, @Nullable UUID owner, String stableId) {
        super(plugin, parentQuest, StepType.DIALOG, owner);
        this.stableId = stableId;
        this.completed = false;
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
    public AbstractQuestStep cloneForPlayer(UUID ownerUUID) {
        DialogStep step = new DialogStep(super.getPlugin(), super.getParentQuest(), ownerUUID, stableId);

        step.setCompleted(false);
        step.setId(this.getId());

        return step;
    }

    @Override
    public AbstractQuestStep assignValuesFromState(PlayerQuestState state) {
        this.stableId = state.getStepProgressData().get("stableId").toString();
        // Not assigning completed here, because its 'in progress' state is non-existent. as soon as completed is set to true, the next quest step replaces it.
        return this;
    }

    @Override
    public PlayerQuestState assignValuesToState(PlayerQuestState state) {
        Map<String, Object> toBeMutated = state.getStepProgressData();
        toBeMutated.put("stableId", this.stableId);
        return state;
    }
}
