package org.zuma.examplePlugin.domain.dialog;

import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.logger.constants.LogLevel;
import org.zuma.examplePlugin.domain.player.RunePlayer;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.UUID;

public class DialogHandler {
    private final HashMap<UUID, Dialog> activeDialogs = new HashMap<>();


    public void abortDialog(RunePlayer player) {
        this.abortDialog(player.getUUID());
    }

    public void abortDialog(UUID playerUUID) {
        Dialog matchingDialog = activeDialogs.get(playerUUID);

        if (matchingDialog == null) {
            return;
        }

        matchingDialog.cleanUp();
        activeDialogs.remove(playerUUID);
    }

    public void startDialog(RunePlayer player, Dialog dialog, @Nullable Runnable onFinishCancel) {
        if (activeDialogs.get(player.getUUID()) != null) {
            RuneQuestPlugin.getRuneLogger().log(LogLevel.WARN, player.asBukkitPlayer().getName() + " attempted to start a new dialog while having an active dialog.");
            return;
        }

        activeDialogs.put(player.getUUID(), dialog);
        dialog.setOnDialogFinish(() -> {
            this.abortDialog(player);

            if (onFinishCancel != null) {
                onFinishCancel.run();
            }
        });
        dialog.startDialog(player);
    }

    @Nullable
    public Dialog getActivePlayerDialog(RunePlayer player) {
        return activeDialogs.get(player.getUUID());
    }
}
