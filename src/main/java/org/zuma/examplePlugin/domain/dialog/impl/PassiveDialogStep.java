package org.zuma.examplePlugin.domain.dialog.impl;

import org.bukkit.Bukkit;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.logger.constants.LogLevel;
import org.zuma.examplePlugin.domain.dialog.DialogText;
import org.zuma.examplePlugin.domain.dialog.base.DialogStep;
import org.zuma.examplePlugin.domain.heimscript.action.ScriptedActionHandler;
import org.zuma.examplePlugin.domain.player.RunePlayer;
import org.zuma.examplePlugin.domain.player.settings.PlayerSetting;

import javax.annotation.Nullable;
import java.util.List;

public class PassiveDialogStep extends DialogStep {
    private final boolean timerCancelled = false;
    private final int contentLength;
    private final List<String> actions;
    private Runnable runnable = null;

    public PassiveDialogStep(List<DialogText> content, List<String> actions, @Nullable Runnable runnable, String uniqueIdentifier) {
        super(content, uniqueIdentifier);
        this.contentLength = content.stream()
                .mapToInt(item -> item.line().length())
                .sum();
        this.actions = actions;
        this.runnable = runnable;
    }

    @Override
    public void onDialogFinishRendering(RunePlayer player) {
        boolean isSlowMode = !player.getSettings().getSetting(PlayerSetting.FAST_DIALOG);

        Bukkit.getScheduler().runTaskLater(RuneQuestPlugin.getInstance(), () -> {
            if (timerCancelled) {
                return;
            }

            if (actions != null) {
                try {
                    for (String action : actions) {
                        ScriptedActionHandler.executeAction(action, player, null, new ScriptedActionHandler.ActionCaller(ScriptedActionHandler.ActionCallerType.DIALOG, "{dialog_id:" + parent.getId() + "}"), this.parent);
                    }
                } catch (Exception e) {
                    RuneQuestPlugin.getRuneLogger().log(LogLevel.CRITICAL, "Couldn't execute action in PassiveDialogStep " + this.getUniqueIdentifier() + ". Aborting.");
                }
            }

            if (runnable != null) {
                runnable.run();
            }

            parent.advanceDialogStep();
        }, Math.min(isSlowMode ? 50 : 20, isSlowMode ? contentLength * 3 : contentLength));
    }

}
