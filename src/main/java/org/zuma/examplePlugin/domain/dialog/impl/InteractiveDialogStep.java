package org.zuma.examplePlugin.domain.dialog.impl;

import org.bukkit.Bukkit;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.constants.Icon;
import org.zuma.examplePlugin.core.constants.SoundType;
import org.zuma.examplePlugin.core.logger.constants.LogLevel;
import org.zuma.examplePlugin.domain.heimscript.action.ScriptedActionHandler;
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
    private final List<DialogText> textContent;
    private final boolean loop;


    public InteractiveDialogStep(List<DialogText> textContent, List<InteractiveDialogOption> interactiveOptions, boolean loop, String uniqueIdentifier) {
        super(Stream.concat(
                textContent.stream(),
                IntStream.range(0, interactiveOptions.size())
                        .mapToObj(i -> new DialogText((i + 1) + ") " + interactiveOptions.get(i).content(), false))
        ).collect(Collectors.toList()), uniqueIdentifier);

        this.textContent = textContent;
        this.interactiveContent = interactiveOptions;
        this.loop = loop;

    }

    @Override
    public void onDialogFinishRendering(RunePlayer player) {
        player.setDialogOptionChoosing(true);
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

        SoundType.DIALOG_CHOICE_SELECT.play(player, false);
        super.replaceDialogLine(newSlot + textContent.size(), (prevContent) -> "§a" + Icon.ARROW_RIGHT.get() + " §f§n" + prevContent + " §r§a" + Icon.ARROW_LEFT.get());

        Bukkit.getScheduler().runTaskLater(RuneQuestPlugin.getInstance(), () -> {
            if (player.asBukkitPlayer() == null || !player.asBukkitPlayer().isOnline()) {
                return;
            }

            player.setDialogOptionChoosing(false);

            if (clickedOption.actions() != null) {
                try {
                    for (String action : clickedOption.actions()) {
                        ScriptedActionHandler.executeAction(action, player, null, new ScriptedActionHandler.ActionCaller(ScriptedActionHandler.ActionCallerType.DIALOG, "{dialog_id:" + parent.getId() + "}"), this.parent);
                    }
                } catch (Exception e) {
                    RuneQuestPlugin.getRuneLogger().log(LogLevel.CRITICAL, "Couldn't execute action in InteractiveDialogStep " + this.getUniqueIdentifier() + ". Aborting.");
                }


            }

            if (clickedOption.runnable() != null) {
                clickedOption.runnable().run();
            }

            if (clickedOption.subContent() != null) {
                clickedOption.subContent().forEach(step -> {
                    step.setParent(super.parent);
                });
                super.parent.getScenario().addAll(super.parent.getNextStepIndex(), clickedOption.subContent());

                if (loop) {
                    if (clickedOption.subContent().isEmpty()) {
                        super.parent.advanceDialogStep();
                        return;
                    }

                    super.parent.getScenario().add(super.parent.getNextStepIndex() + clickedOption.subContent().size(), this);
                }
            }

            super.parent.advanceDialogStep();

        }, 10L);
    }

    public List<InteractiveDialogOption> getInteractiveContent() {
        return interactiveContent;
    }

}
