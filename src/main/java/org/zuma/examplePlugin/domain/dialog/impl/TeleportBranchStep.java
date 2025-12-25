package org.zuma.examplePlugin.domain.dialog.impl;

import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.logger.constants.LogLevel;
import org.zuma.examplePlugin.domain.dialog.base.DialogStep;
import org.zuma.examplePlugin.domain.player.RunePlayer;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TeleportBranchStep extends DialogStep {
    private final String teleportToUniqueIdentifier;

    public TeleportBranchStep(String teleportToUniqueIdentifier, String uniqueIdentifier) {
        super(List.of(), uniqueIdentifier);
        this.teleportToUniqueIdentifier = teleportToUniqueIdentifier;
    }

    private boolean collectFollowUps(
            List<DialogStep> steps,
            String teleportToUniqueIdentifier,
            List<DialogStep> followUpSteps
    ) {
        for (int i = 0; i < steps.size(); i++) {
            DialogStep step = steps.get(i);

            // If found the target, add everything from here onward (only from this list)
            if (Objects.equals(step.getUniqueIdentifier(), teleportToUniqueIdentifier)) {
                followUpSteps.addAll(steps.subList(i, steps.size()));
                return true; // signal: found — stop all further traversal
            }

            // Recurse into truthy/falsy branches — if any recursion finds it, propagate true up
            if (step instanceof ConditionStep conditionStep) {
                if (conditionStep.getTruthyFollowUp() != null) {
                    if (collectFollowUps(conditionStep.getTruthyFollowUp(),
                            teleportToUniqueIdentifier, followUpSteps)) {
                        return true;
                    }
                }
                if (conditionStep.getFalsyFollowUp() != null) {
                    if (collectFollowUps(conditionStep.getFalsyFollowUp(),
                            teleportToUniqueIdentifier, followUpSteps)) {
                        return true;
                    }
                }
            }

            // Recurse into interactive options
            if (step instanceof InteractiveDialogStep interactiveStep) {
                for (var option : interactiveStep.getInteractiveContent()) {
                    if (option.subContent() != null) {
                        if (collectFollowUps(option.subContent(),
                                teleportToUniqueIdentifier, followUpSteps)) {
                            return true;
                        }
                    }
                }
            }
        }

        return false; // not found in this branch
    }


    @Override
    public void onDialogFinishRendering(RunePlayer player) {
        List<DialogStep> followUpSteps = new ArrayList<>();

        // collectFollowUps now returns true if it found & added the tail (and stopped traversal)
        boolean found = collectFollowUps(super.parent.getScenario(), teleportToUniqueIdentifier, followUpSteps);

        if (found && !followUpSteps.isEmpty()) {
            followUpSteps.forEach(step -> step.setParent(super.parent));

            int insertIndex = super.parent.getNextStepIndex();

            // Remove everything from insertIndex onward
            if (insertIndex < super.parent.getScenario().size()) {
                super.parent.getScenario().subList(insertIndex, super.parent.getScenario().size()).clear();
            }

            // Add new follow-ups
            super.parent.getScenario().addAll(followUpSteps);
        } else {
            RuneQuestPlugin.getRuneLogger().log(LogLevel.CRITICAL,
                    "TeleportBranchStep cannot teleport to " + this.teleportToUniqueIdentifier +
                            " branch. It is not found in " + super.parent.getId() + " dialog. Falling through.");
        }

        this.parent.advanceDialogStep();
    }

}
