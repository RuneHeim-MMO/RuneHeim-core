package org.zuma.examplePlugin.domain.dialog;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.utils.MathUtils;
import org.zuma.examplePlugin.domain.dialog.base.DialogStep;
import org.zuma.examplePlugin.domain.dialog.impl.InteractiveDialogStep;
import org.zuma.examplePlugin.domain.mob.base.AbstractMob;
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

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        Dialog playerDialog = handler.getActivePlayerDialog(player.getUniqueId());

        if (playerDialog == null) {
            return;
        }

        AbstractMob mob = playerDialog.getDialogHolder();

        if (mob == null) {
            return;
        }

        Entity entity = mob.getEntity();

        if (entity == null) {
            return;
        }

        if (MathUtils.getLocationsDistanceXZ(entity.getLocation(), player.getLocation()) > 10) {
            RuneQuestPlugin.getDialogHandler().abortDialog(player.getUniqueId());
        }
    }
}
