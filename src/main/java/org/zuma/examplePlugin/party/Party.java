package org.zuma.examplePlugin.party;

import org.bukkit.Bukkit;
import org.zuma.examplePlugin.constants.XpSource;
import org.zuma.examplePlugin.party.events.PartyOwnerUpdateEvent;
import org.zuma.examplePlugin.party.events.PartyPlayerInvited;
import org.zuma.examplePlugin.party.events.PartyPlayerJoinedEvent;
import org.zuma.examplePlugin.party.events.PartyPlayerKickedEvent;
import org.zuma.examplePlugin.player.RunePlayer;
import org.zuma.examplePlugin.skill.constants.SkillType;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Party {
    private final UUID uuid;
    private final List<RunePlayer> players;
    private UUID ownerUuid;
    private String ownerUsername;
    private final List<UUID> invites;
    private final int maxMembers;

    public Party(UUID uuid, RunePlayer owner, int maxMembers) {
        this.uuid = uuid;
        this.ownerUuid = owner.getUUID();
        this.ownerUsername = owner.getPlayer().getName();
        this.players = new ArrayList<>();
        this.invites = new ArrayList<>();
        this.players.add(owner);
        this.maxMembers = maxMembers;
    }

    public UUID getUuid() {
        return uuid;
    }

    public List<RunePlayer> getMembers() {
        return players;
    }

    public UUID getOwnerUuid() {
        return ownerUuid;
    }

    public String getOwnerUsername() {
        return ownerUsername;
    }

    public void setNewOwner(RunePlayer player) {
        if (!players.contains(player)) {
            throw new IllegalStateException("This player is not in this party!");
        }

        ownerUuid = player.getUUID();
        ownerUsername = player.getPlayer().getName();
        Bukkit.getServer().getPluginManager().callEvent(new PartyOwnerUpdateEvent(this, player));
    }

    public void sendInvite(RunePlayer player) {
        if (players.contains(player)) {
            throw new IllegalStateException("The player is already in this party.");
        }

        if (players.size() >= maxMembers) {
            throw new IllegalStateException("This party can have maximum of " + maxMembers + " members.");
        }

        invites.add(player.getUUID());
        Bukkit.getServer().getPluginManager().callEvent(new PartyPlayerInvited(this, player));
    }

    public void acceptInvite(RunePlayer player) {
        if (players.contains(player)) {
            throw new IllegalStateException("The player is already in this party.");
        }

        if (!invites.contains(player.getUUID())) {
            throw new IllegalStateException("Player is not invited to this party.");
        }

        if (players.size() >= maxMembers) {
            throw new IllegalStateException("This party is full!");
        }

        invites.remove(player.getUUID());
        players.add(player);
        Bukkit.getServer().getPluginManager().callEvent(new PartyPlayerJoinedEvent(this, player));
    }

    public void kickPlayer(RunePlayer player) {
        if (!players.contains(player)) {
            throw new IllegalArgumentException("The player is not in this party.");
        }

        if (player.getUUID().equals(ownerUuid)) {
            throw new IllegalStateException("Cannot kick party owner.");
        }

        players.remove(player);
        Bukkit.getServer().getPluginManager().callEvent(new PartyPlayerKickedEvent(this, player));

    }

    public boolean playerHasInvite(RunePlayer player) {
        return invites.contains(player.getUUID());
    }

    public void splitXPBetweenMembers(SkillType type, int xp) {
        int equalXp = xp / players.size();

        players.forEach(player -> {
            player.addSkillXp(type, equalXp, XpSource.PARTY);
        });
    }
}
