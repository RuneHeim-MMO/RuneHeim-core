package org.zuma.examplePlugin.domain.quest;

import java.util.HashMap;
import java.util.Map;

public class PlayerQuestState {
    private String questId;
    private int stepId;
    private StepType type;
    private Map<String, Object> stepProgressData = new HashMap<>();

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
