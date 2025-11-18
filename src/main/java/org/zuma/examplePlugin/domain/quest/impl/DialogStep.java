package org.zuma.examplePlugin.domain.quest.impl;

import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.domain.quest.PlayerQuestState;
import org.zuma.examplePlugin.domain.quest.Quest;
import org.zuma.examplePlugin.domain.quest.constants.StepType;
import org.zuma.examplePlugin.domain.quest.base.AbstractQuestStep;

import javax.annotation.Nullable;
import java.util.UUID;

public class DialogStep extends AbstractQuestStep {

    public DialogStep(RuneQuestPlugin plugin, Quest parentQuest, String description, String stableId, @Nullable UUID owner) {
        super(plugin, parentQuest, StepType.DIALOG, description, stableId, owner);
    }


    public float getProgressPercentage() {
        return 0.0f;
    }

    @Override
    public AbstractQuestStep cloneForPlayer(UUID ownerUUID) {
        DialogStep step = new DialogStep(super.getPlugin(), super.getParentQuest(), super.getDescription(), super.getStableId(), ownerUUID);
        step.setId(this.getId());

        return step;
    }

    @Override
    public AbstractQuestStep assignValuesFromState(PlayerQuestState state) {
        // Not assigning completed here, because its 'in progress' state is non-existent. as soon as completed is set to true, the next quest step replaces it.
        return this;
    }

    @Override
    public PlayerQuestState assignValuesToState(PlayerQuestState state) {
        return state;
    }
}
