package org.zuma.examplePlugin.domain.dialog.impl;

import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.logger.constants.LogLevel;
import org.zuma.examplePlugin.domain.dialog.base.DialogStep;
import org.zuma.examplePlugin.domain.heimscript.condition.ScriptedConditionHandler;
import org.zuma.examplePlugin.domain.player.RunePlayer;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TeleportBranchStep extends DialogStep {
    private final String teleportToUniqueIdentifier;

    public TeleportBranchStep(String teleportToUniqueIdentifier, String uniqueIdentifier) {
        super(List.of(), uniqueIdentifier);
        this.teleportToUniqueIdentifier = teleportToUniqueIdentifier;
    }

    private void collectFollowUps(
            List<DialogStep> steps,
            String teleportToUniqueIdentifier,
            List<DialogStep> followUpSteps
    ) {
        for (int i = 0; i < steps.size(); i++) {
            DialogStep step = steps.get(i);

            // If found the target, add everything from here onward
            if (Objects.equals(step.getUniqueIdentifier(), teleportToUniqueIdentifier)) {
                followUpSteps.addAll(steps.subList(i, steps.size()));
                return;
            }

            // Recurse into truthy/falsy branches
            if (step instanceof ConditionStep conditionStep) {
                if (conditionStep.getTruthyFollowUp() != null) {
                    collectFollowUps(conditionStep.getTruthyFollowUp(),
                            teleportToUniqueIdentifier, followUpSteps);
                }
                if (conditionStep.getFalsyFollowUp() != null) {
                    collectFollowUps(conditionStep.getFalsyFollowUp(),
                            teleportToUniqueIdentifier, followUpSteps);
                }
            }

            // Recurse into interactive options
            if (step instanceof InteractiveDialogStep interactiveStep) {
                interactiveStep.getInteractiveContent().forEach(option -> {
                    if (option.subContent() != null) {
                        collectFollowUps(option.subContent(),
                                teleportToUniqueIdentifier, followUpSteps);
                    }
                });
            }
        }
    }

    @Override
    public void onDialogFinishRendering(RunePlayer player) {
        List<DialogStep> followUpSteps = new ArrayList<>();
        collectFollowUps(super.parent.getScenario(), teleportToUniqueIdentifier, followUpSteps);

        if (!followUpSteps.isEmpty()) {
            followUpSteps.forEach(step -> {
                step.setParent(super.parent);
            });

            int insertIndex = super.parent.getNextStepIndex();

            // Remove everything from insertIndex onward
            if (insertIndex < super.parent.getScenario().size()) {
                super.parent.getScenario().subList(insertIndex, super.parent.getScenario().size()).clear();
            }

            // Add new follow-ups
            super.parent.getScenario().addAll(followUpSteps);
        } else {
            RuneQuestPlugin.getRuneLogger().log(LogLevel.CRITICAL, "TeleportBranchStep cannot teleport to " + this.teleportToUniqueIdentifier + " branch. It is not found in " + super.parent.getId() + " dialog. Falling through.");
        }
        this.parent.advanceDialogStep();
    }


}
