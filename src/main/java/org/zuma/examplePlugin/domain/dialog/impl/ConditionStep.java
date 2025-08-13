package org.zuma.examplePlugin.domain.dialog.impl;

import org.zuma.examplePlugin.domain.dialog.DialogCondition;
import org.zuma.examplePlugin.domain.dialog.base.DialogStep;
import org.zuma.examplePlugin.domain.player.RunePlayer;
import org.zuma.examplePlugin.domain.runescript.condition.ScriptedConditionHandler;

import java.util.ArrayList;
import java.util.List;

public class ConditionStep extends DialogStep {
    private final DialogCondition condition;

    public ConditionStep(DialogCondition condition) {
        super(List.of());
        this.condition = condition;
    }

    @Override
    public void onDialogFinishRendering(RunePlayer player) {
        String rawCondition = condition.condition();

        boolean isConditionTruthy = ScriptedConditionHandler.executeCondition(rawCondition, player, null, this.parent);
        List<DialogStep> followUpSteps = new ArrayList<>();

        if (isConditionTruthy) {
            if (condition.truthyFollowUp() != null)
                followUpSteps.addAll(condition.truthyFollowUp());
        } else {
            if (condition.falsyFollowUp() != null)
                followUpSteps.addAll(condition.falsyFollowUp());
        }

        followUpSteps.forEach(step -> {
            step.setParent(super.parent);
        });

        super.parent.getScenario().addAll(super.parent.getNextStepIndex(), followUpSteps);
        this.parent.advanceDialogStep();
    }


}
