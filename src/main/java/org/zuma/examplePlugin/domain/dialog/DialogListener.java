package org.zuma.examplePlugin.domain.dialog;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.domain.dialog.base.DialogStep;
import org.zuma.examplePlugin.domain.dialog.impl.InteractiveDialogStep;
import org.zuma.examplePlugin.domain.player.RunePlayer;

public class DialogListener implements Listener {
    private final DialogHandler handler;

    public DialogListener(DialogHandler handler) {
        this.handler = handler;
    }

    @EventHandler
    public void onHeldSlotChanged(PlayerItemHeldEvent event) {
        RunePlayer player = RuneQuestPlugin.getPlayerManager().getRunePlayer(event.getPlayer());

        if (player == null) {
            return;
        }

        Dialog playerDialog = handler.getActivePlayerDialog(player);

        if (playerDialog == null) {
            return;
        }

        DialogStep step = playerDialog.getCurrentStep();

        if (!(step instanceof InteractiveDialogStep)) {
            return;
        }

        event.setCancelled(true);
        step.handleHotbarSlotChange(event, player);
    }

    @EventHandler
    public void onPlayerDisconnect(PlayerQuitEvent event) {
        RuneQuestPlugin.getDialogHandler().abortDialog(event.getPlayer().getUniqueId());
    }
}
