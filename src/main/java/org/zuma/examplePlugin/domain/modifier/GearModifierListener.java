package org.zuma.examplePlugin.domain.modifier;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;
import org.zuma.examplePlugin.domain.player.RunePlayer;
import org.zuma.examplePlugin.domain.player.RunePlayerManager;

public class GearModifierListener implements Listener {
    private final GearModifierHandler gearModifierHandler;
    private final RunePlayerManager playerHandler;

    public GearModifierListener(GearModifierHandler gearModifierHandler, RunePlayerManager playerHandler) {
        this.gearModifierHandler = gearModifierHandler;
        this.playerHandler = playerHandler;
    }

    @EventHandler
    public void onPlayerTakeItemInHand(PlayerItemHeldEvent event) {
        RunePlayer player = playerHandler.getRunePlayer(event.getPlayer());

        if (player == null) {
            return;
        }

        ItemStack itemInHand = event.getPlayer().getInventory().getItem(event.getNewSlot());
        gearModifierHandler.recalculatePlayerModifiers(player, itemInHand);

    }

    @EventHandler
    public void onPlayerInventoryClose(InventoryCloseEvent event) {
        if (!(event.getPlayer() instanceof Player player)) return;

        RunePlayer runePlayer = playerHandler.getRunePlayer(player.getUniqueId());
        if (runePlayer == null) return;

        ItemStack itemInHand = player.getInventory().getItemInMainHand();
        gearModifierHandler.recalculatePlayerModifiers(runePlayer, itemInHand);
    }


}
