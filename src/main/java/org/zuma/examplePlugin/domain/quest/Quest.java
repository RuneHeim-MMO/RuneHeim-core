package org.zuma.examplePlugin.domain.quest;

import org.zuma.examplePlugin.domain.quest.base.AbstractQuestStep;

import java.util.ArrayList;
import java.util.List;

public class Quest {
    private final String id, name;
    private final List<AbstractQuestStep> steps;

    public Quest(String id, String name, List<AbstractQuestStep> steps) {
        this.id = id;
        this.name = name;
        this.steps = new ArrayList<>();

        int stepIndex = 0;
        for (AbstractQuestStep step : steps) {
            step.setId(stepIndex);
            steps.add(step);
            stepIndex++;
        }

    }


    public String getId() {
        return id;
    }

    public List<AbstractQuestStep> getSteps() {
        return steps;
    }

    public String getName() {
        return name;
    }
}
