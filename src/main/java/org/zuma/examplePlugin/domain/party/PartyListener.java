package org.zuma.examplePlugin.domain.party;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.domain.player.RunePlayer;
import org.zuma.examplePlugin.events.party.PartyUpdateEvent;
import org.zuma.examplePlugin.events.player.RunePlayerLoadedEvent;

public class PartyListener implements Listener {

    @EventHandler
    public void onPlayerDisconnect(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        Party party = RuneQuestPlugin.getPartyManager().getPlayersParty(event.getPlayer());

        if (party == null) {
            return;
        }

        // If the player is the party owner, make them leave the party so ownership can be transferred
        if (party.getOwnerUuid().equals(player.getUniqueId())) {
            RunePlayer runePlayer = RuneQuestPlugin.getPlayerManager().getRunePlayer(player);
            if (runePlayer == null) return;

            party.leaveParty(runePlayer);
        }

        Bukkit.getServer().getPluginManager().callEvent(new PartyUpdateEvent(party));

    }

    @EventHandler
    public void onPlayerJoin(RunePlayerLoadedEvent event) {
        RunePlayer player = event.getPlayer();
        Party party = RuneQuestPlugin.getPartyManager().getPlayersParty(player);

        if (party != null) {
            player.setParty(party);
            Bukkit.getServer().getPluginManager().callEvent(new PartyUpdateEvent(party));
        }
    }
}
