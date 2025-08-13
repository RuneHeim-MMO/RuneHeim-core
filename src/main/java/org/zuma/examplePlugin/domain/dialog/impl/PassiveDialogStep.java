package org.zuma.examplePlugin.domain.dialog.impl;

import org.bukkit.Bukkit;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.domain.heimscript.action.ScriptedActionHandler;
import org.zuma.examplePlugin.domain.dialog.DialogText;
import org.zuma.examplePlugin.domain.dialog.base.DialogStep;
import org.zuma.examplePlugin.domain.player.RunePlayer;

import javax.annotation.Nullable;
import java.util.List;

public class PassiveDialogStep extends DialogStep {
    private boolean timerCancelled = false;
    private final int contentLength;
    private final List<String> actions;
    private Runnable runnable = null;

    public PassiveDialogStep(List<DialogText> content, List<String> actions, @Nullable Runnable runnable) {
        super(content);
        this.contentLength = content.stream()
                .mapToInt(item -> item.line().length())
                .sum();
        this.actions = actions;
        this.runnable = runnable;
    }

    @Override
    public void onDialogFinishRendering(RunePlayer player) {
        Bukkit.getScheduler().runTaskLater(RuneQuestPlugin.getInstance(), () -> {
            if (timerCancelled) {
                return;
            }

            if (actions != null) {
                actions.forEach(ac ->
                        ScriptedActionHandler.executeAction(ac, player, null, new ScriptedActionHandler.ActionCaller(ScriptedActionHandler.ActionCallerType.DIALOG, "{dialog_id:" + parent.getId() + "}"), this.parent)
                );
            }

            if (runnable != null) {
                runnable.run();
            }

            parent.advanceDialogStep();
        }, Math.min(20, contentLength));
    }

    @Override
    public void onHotbarSlotChange(PlayerItemHeldEvent event, RunePlayer player, int newSlot) {
        timerCancelled = true;
        parent.advanceDialogStep();
    }

}
