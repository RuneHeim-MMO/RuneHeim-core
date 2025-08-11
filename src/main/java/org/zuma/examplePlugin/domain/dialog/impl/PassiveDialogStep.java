package org.zuma.examplePlugin.domain.dialog.impl;

import org.bukkit.Bukkit;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.domain.dialog.Dialog;
import org.zuma.examplePlugin.domain.dialog.DialogText;
import org.zuma.examplePlugin.domain.dialog.InteractiveDialogOption;
import org.zuma.examplePlugin.domain.dialog.base.DialogStep;
import org.zuma.examplePlugin.domain.player.RunePlayer;

import java.util.ArrayList;
import java.util.List;

public class PassiveDialogStep extends DialogStep {
    private boolean timerCancelled = false;
    private final int contentLength;

    public PassiveDialogStep(List<DialogText> content) {
        super(content);
        this.contentLength = content.stream()
                .mapToInt(item -> item.line().length())
                .sum();
    }

    @Override
    public void onDialogFinishRendering(RunePlayer player) {
        Bukkit.getScheduler().runTaskLater(RuneQuestPlugin.getInstance(), () -> {
            if (timerCancelled) {
                return;
            }

            parent.advanceDialogStep();
        }, contentLength);
    }

    @Override
    public void onHotbarSlotChange(PlayerItemHeldEvent event, RunePlayer player, int newSlot) {
        timerCancelled = true;
        parent.advanceDialogStep();
    }

}
