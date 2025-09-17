package org.zuma.examplePlugin.domain.dialog.impl;

import org.zuma.examplePlugin.domain.dialog.base.DialogStep;
import org.zuma.examplePlugin.domain.player.RunePlayer;
import org.zuma.examplePlugin.domain.heimscript.condition.ScriptedConditionHandler;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class ConditionStep extends DialogStep {
    public enum LogicalOperator {
        AND, OR
    }

    private final List<String> conditions;
    private final List<DialogStep> truthyFollowUp;
    private final List<DialogStep> falsyFollowUp;
    private final LogicalOperator logicalOperator;


    public ConditionStep(List<String> conditions, @Nullable List<DialogStep> truthyFollowUp,
                         @Nullable List<DialogStep> falsyFollowUp, String uniqueIdentifier, LogicalOperator logicalOperator) {
        super(List.of(), uniqueIdentifier);
        this.conditions = conditions;
        this.truthyFollowUp = truthyFollowUp;
        this.falsyFollowUp = falsyFollowUp;
        this.logicalOperator = logicalOperator;
    }

    @Override
    public void onDialogFinishRendering(RunePlayer player) {
        boolean isResultTruthy = true;
        List<DialogStep> followUpSteps = new ArrayList<>();

        if (logicalOperator == LogicalOperator.AND) {
            for (String rawCondition : this.conditions) {
                boolean isConditionTruthy = ScriptedConditionHandler.executeCondition(rawCondition, player, null, this.parent);

                if (!isConditionTruthy) {
                    isResultTruthy = false;
                }

            }
        }

        if (logicalOperator == LogicalOperator.OR) {
            isResultTruthy = false;

            for (String rawCondition : this.conditions) {
                boolean isConditionTruthy = ScriptedConditionHandler.executeCondition(rawCondition, player, null, this.parent);

                if (isConditionTruthy) {
                    isResultTruthy = true;
                    break;
                }

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

    public List<DialogStep> getTruthyFollowUp() {
        return truthyFollowUp;
    }

    public List<DialogStep> getFalsyFollowUp() {
        return falsyFollowUp;
    }


}
