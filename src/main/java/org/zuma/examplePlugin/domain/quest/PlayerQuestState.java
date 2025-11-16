package org.zuma.examplePlugin.domain.quest;

import org.zuma.examplePlugin.domain.quest.constants.StepType;

import java.util.HashMap;
import java.util.Map;

public class PlayerQuestState {
    private final String questId;
    private final int stepId;
    private final StepType type;
    private final Map<String, Object> stepProgressData;

    public PlayerQuestState(String questId, int stepId, StepType type) {
        this.questId = questId;
        this.stepId = stepId;
        this.type = type;
        this.stepProgressData = new HashMap<>();
    }

    public Map<String, Object> getStepProgressData() {
        return stepProgressData;
    }

    public String getQuestId() {
        return questId;
    }

    public int getStepId() {
        return stepId;
    }

    public StepType getType() {
        return type;
    }
}
