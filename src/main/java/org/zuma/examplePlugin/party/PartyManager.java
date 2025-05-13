package org.zuma.examplePlugin.party;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.zuma.examplePlugin.party.events.PartyCreatedEvent;
import org.zuma.examplePlugin.party.events.PartyDisbandedEvent;
import org.zuma.examplePlugin.player.RunePlayer;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class PartyManager {
    public static final int MAX_MEMBERS = 10;
    private final HashMap<UUID, Party> parties = new HashMap<>();


    public void createParty(RunePlayer creator) {
        Party existingParty = getPlayersParty(creator);

        if (existingParty != null) {
            throw new IllegalStateException(creator.getPlayer().getName() + " is already in " + existingParty.getOwnerUsername() + "'s party.");
        }

        UUID partyUuid = UUID.randomUUID();
        Party party = new Party(partyUuid, creator, PartyManager.MAX_MEMBERS);
        parties.put(partyUuid, party);

        Bukkit.getServer().getPluginManager().callEvent(new PartyCreatedEvent(party));
    }

    public void disbandParty(RunePlayer disbander) {
        Party existingParty = getPlayersParty(disbander);

        if (existingParty == null) {
            throw new IllegalStateException(disbander.getPlayer().getName() + " does not belong to any party.");
        }

        if (!existingParty.getOwnerUuid().equals(disbander.getUUID())) {
            throw new IllegalStateException(disbander.getPlayer().getName() + " is not party owner.");
        }

        parties.remove(existingParty.getUuid());
        Bukkit.getServer().getPluginManager().callEvent(new PartyDisbandedEvent(existingParty));

    }

    public List<Party> getPartyInvites(RunePlayer player) {
        return parties.values().stream().filter(party -> party.playerHasInvite(player)).toList();
    }

    @Nullable
    public Party getPlayersParty(Player player) {
        return getPlayersParty(player.getUniqueId());
    }

    @Nullable
    public Party getPlayersParty(RunePlayer player) {
        return getPlayersParty(player.getUUID());
    }

    @Nullable
    public Party getPlayersParty(UUID uuid) {
        return parties.values().stream()
                .filter(party -> {
                    List<UUID> memberUUIDs = party.getMembers().stream()
                            .map(RunePlayer::getUUID)
                            .toList();

                    return memberUUIDs.contains(uuid);
                })
                .findFirst()
                .orElse(null);
    }
}
