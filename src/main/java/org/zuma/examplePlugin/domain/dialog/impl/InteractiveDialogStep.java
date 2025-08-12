package org.zuma.examplePlugin.domain.dialog.impl;

import org.bukkit.event.player.PlayerItemHeldEvent;
import org.zuma.examplePlugin.core.constants.SoundType;
import org.zuma.examplePlugin.domain.action.ScriptedActionHandler;
import org.zuma.examplePlugin.domain.dialog.DialogText;
import org.zuma.examplePlugin.domain.dialog.InteractiveDialogOption;
import org.zuma.examplePlugin.domain.dialog.base.DialogStep;
import org.zuma.examplePlugin.domain.player.RunePlayer;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class InteractiveDialogStep extends DialogStep {
    private final List<InteractiveDialogOption> interactiveContent;

    public InteractiveDialogStep(List<DialogText> textContent, List<InteractiveDialogOption> interactiveOptions) {
        super(Stream.concat(
                textContent.stream(),
                IntStream.range(0, interactiveOptions.size())
                        .mapToObj(i -> new DialogText((i + 1) + ") " + interactiveOptions.get(i).content(), false))
        ).collect(Collectors.toList()));

        this.interactiveContent = interactiveOptions;

    }

    @Override
    public void onDialogFinishRendering(RunePlayer player) {
        SoundType.DIALOG_CHOICE_SOUND.play(player, false);
        parent.setSlotLocked(true);
    }

    @Override
    public void onHotbarSlotChange(PlayerItemHeldEvent event, RunePlayer player, int newSlot) {
        if (newSlot >= interactiveContent.size()) {
            return;
        }

        InteractiveDialogOption clickedOption = interactiveContent.get(newSlot);

        if (clickedOption == null) {
            return;
        }

        if (clickedOption.actions() != null) {
            clickedOption.actions().forEach(ac -> {
                ScriptedActionHandler.executeAction(ac, player, null, new ScriptedActionHandler.ActionCaller(ScriptedActionHandler.ActionCallerType.DIALOG, "{dialog_id:" + parent.getId() + "}"), this.parent);
            });
        }

        if (clickedOption.runnable() != null) {
            clickedOption.runnable().run();
        }

        if (clickedOption.subContent() != null) {
            clickedOption.subContent().forEach(step -> {
                step.setParent(super.parent);
            });
            super.parent.getScenario().addAll(super.parent.getNextStepIndex(), clickedOption.subContent());
        }

        super.parent.advanceDialogStep();
    }

}
