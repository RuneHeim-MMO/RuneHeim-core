package org.zuma.examplePlugin.domain.party;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.domain.player.RunePlayer;
import org.zuma.examplePlugin.events.player.RunePlayerLoadedEvent;

public class PartyListener implements Listener {

    @EventHandler
    public void onPlayerDisconnect(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        Party party = RuneQuestPlugin.getPartyManager().getPlayersParty(event.getPlayer());

        // If the player is the party owner, make them leave the party so ownership can be transferred
        if (party != null && party.getOwnerUuid().equals(player.getUniqueId())) {
            RunePlayer runePlayer = RuneQuestPlugin.getPlayerManager().getRunePlayer(player);
            if (runePlayer == null) return;

            party.leaveParty(runePlayer);
        }

    }

    @EventHandler
    public void onPlayerJoin(RunePlayerLoadedEvent event) {
        RunePlayer player = event.getPlayer();
        Party party = RuneQuestPlugin.getPartyManager().getPlayersParty(player);

        if (party != null) {
            player.setParty(party);
        }
    }
}
