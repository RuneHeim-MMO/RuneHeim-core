package org.zuma.examplePlugin.domain.party;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.domain.player.RunePlayer;

public class PartyListener implements Listener {

    @EventHandler
    public void onPlayerDisconnect(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        Party party = RuneQuestPlugin.getPartyManager().getPlayersParty(event.getPlayer());

        if (party != null) {
            RunePlayer runePlayer = RuneQuestPlugin.getPlayerManager().getRunePlayer(player);
            if (runePlayer == null) return;

            party.leaveParty(runePlayer);
        }

    }
}
