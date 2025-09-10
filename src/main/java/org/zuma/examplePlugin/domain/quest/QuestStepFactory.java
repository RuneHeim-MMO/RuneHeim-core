package org.zuma.examplePlugin.domain.quest;

import org.zuma.examplePlugin.domain.quest.base.AbstractQuestStep;

public class QuestStepFactory {

    public AbstractQuestStep createStep(Quest parentQuest, PlayerQuestState playerQuestState) {
        AbstractQuestStep matchingStep = parentQuest.getSteps().get(playerQuestState.getStepId());

        if (matchingStep == null) {
            throw new IllegalStateException("Cannot find matching step in " + playerQuestState.getQuestId() + " quest, step id: " + playerQuestState.getStepId());
        }

        return matchingStep.cloneForPlayer().assignValuesFromState(playerQuestState);
    }
}
