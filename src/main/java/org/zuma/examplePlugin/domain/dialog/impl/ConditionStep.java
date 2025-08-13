package org.zuma.examplePlugin.domain.dialog.impl;

import org.zuma.examplePlugin.domain.dialog.base.DialogStep;
import org.zuma.examplePlugin.domain.player.RunePlayer;
import org.zuma.examplePlugin.domain.heimscript.condition.ScriptedConditionHandler;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class ConditionStep extends DialogStep {
    private final List<String> conditions;
    private final List<DialogStep> truthyFollowUp;
    private final List<DialogStep> falsyFollowUp;

    public ConditionStep(List<String> conditions, @Nullable List<DialogStep> truthyFollowUp,
                         @Nullable List<DialogStep> falsyFollowUp) {
        super(List.of());
        this.conditions = conditions;
        this.truthyFollowUp = truthyFollowUp;
        this.falsyFollowUp = falsyFollowUp;
    }

    @Override
    public void onDialogFinishRendering(RunePlayer player) {
        boolean isResultTruthy = true;
        List<DialogStep> followUpSteps = new ArrayList<>();

        for (String rawCondition : this.conditions) {
            boolean isConditionTruthy = ScriptedConditionHandler.executeCondition(rawCondition, player, null, this.parent);

            if (!isConditionTruthy) {
                isResultTruthy = false;
            }

        }

        if (isResultTruthy) {
            if (this.truthyFollowUp != null)
                followUpSteps.addAll(this.truthyFollowUp);
        } else {
            if (this.falsyFollowUp != null)
                followUpSteps.addAll(this.falsyFollowUp);
        }

        followUpSteps.forEach(step -> {
            step.setParent(super.parent);
        });

        super.parent.getScenario().addAll(super.parent.getNextStepIndex(), followUpSteps);
        this.parent.advanceDialogStep();
    }


}
