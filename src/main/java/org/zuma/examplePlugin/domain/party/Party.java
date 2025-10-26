package org.zuma.examplePlugin.domain.party;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.constants.Color;
import org.zuma.examplePlugin.core.constants.XpSource;
import org.zuma.examplePlugin.domain.player.RunePlayer;
import org.zuma.examplePlugin.domain.skill.constants.SkillType;
import org.zuma.examplePlugin.events.party.PartyOwnerUpdateEvent;
import org.zuma.examplePlugin.events.party.PartyPlayerInvited;
import org.zuma.examplePlugin.events.party.PartyPlayerJoinedEvent;
import org.zuma.examplePlugin.events.party.PartyPlayerKickedEvent;

import java.util.*;

public class Party {
    private final UUID uuid;
    private final List<UUID> players;
    private UUID ownerUuid;
    private String ownerUsername;
    private final Set<UUID> invites;
    private final int maxMembers;

    public Party(UUID uuid, RunePlayer owner, int maxMembers) {
        this.uuid = uuid;
        this.ownerUuid = owner.getUUID();
        this.ownerUsername = owner.asBukkitPlayer().getName();
        this.players = new ArrayList<>();
        this.invites = new HashSet<>();
        this.players.add(owner.getUUID());
        this.maxMembers = maxMembers;
    }

    public UUID getUuid() {
        return uuid;
    }

    public List<RunePlayer> getMembers() {
        List<RunePlayer> memberList = new ArrayList<>();

        for (UUID playerUuid : players) {
            RunePlayer player = RuneQuestPlugin.getPlayerManager().getRunePlayer(playerUuid);

            if (player != null) {
                memberList.add(player);
            }

        }

        return memberList;
    }

    public List<UUID> getMemberUUIDs() {
        return players;
    }

    public UUID getOwnerUuid() {
        return ownerUuid;
    }

    public String getOwnerUsername() {
        return ownerUsername;
    }

    public void setNewOwner(RunePlayer player) {
        if (!players.contains(player.getUUID())) {
            throw new IllegalStateException("This player is not in this party!");
        }

        ownerUuid = player.getUUID();
        ownerUsername = player.asBukkitPlayer().getName();
        Bukkit.getServer().getPluginManager().callEvent(new PartyOwnerUpdateEvent(this, player));

        this.players.forEach(uuid -> {
            RunePlayer member = RuneQuestPlugin.getPlayerManager().getRunePlayer(uuid);
            if (member != null) {
                member.asBukkitPlayer().sendMessage(Component.text(player.asBukkitPlayer().getName() + " is the new owner of the party.", Color.INFO.asColor()));
            }
        });

        reloadMemberNametags();
    }

    public void sendInvite(RunePlayer player) {
        if (!invites.add(player.getUUID())) {
            throw new IllegalStateException("The player is already invited.");
        }

        if (players.contains(player.getUUID())) {
            throw new IllegalStateException("The player is already in this party.");
        }

        if (players.size() >= maxMembers) {
            throw new IllegalStateException("This party can have maximum of " + maxMembers + " members.");
        }

        invites.add(player.getUUID());
        Bukkit.getServer().getPluginManager().callEvent(new PartyPlayerInvited(this, player));

        this.players.forEach(uuid -> {
            RunePlayer member = RuneQuestPlugin.getPlayerManager().getRunePlayer(uuid);
            if (member != null) {
                member.asBukkitPlayer().sendMessage(Component.text(player.asBukkitPlayer().getName() + " has been invited to the party.", Color.INFO.asColor()));
            }
        });
    }

    public void acceptInvite(RunePlayer player) {
        if (players.contains(player.getUUID())) {
            throw new IllegalStateException("You are already in this party.");
        }

        if (!invites.contains(player.getUUID())) {
            throw new IllegalStateException("You are not invited to this party.");
        }

        if (players.size() >= maxMembers) {
            throw new IllegalStateException("This party is full!");
        }

        invites.remove(player.getUUID());
        players.add(player.getUUID());
        player.setParty(this);
        Bukkit.getServer().getPluginManager().callEvent(new PartyPlayerJoinedEvent(this, player));

        this.players.forEach(uuid -> {
            RunePlayer member = RuneQuestPlugin.getPlayerManager().getRunePlayer(uuid);
            if (member != null) {
                member.asBukkitPlayer().sendMessage(Component.text(player.asBukkitPlayer().getName() + " has joined the party.", Color.SUCCESS.asColor()));
            }
        });

        reloadMemberNametags();
    }

    public void leaveParty(RunePlayer player) {
        if (!players.contains(player.getUUID())) {
            throw new IllegalArgumentException("The player is not in this party.");
        }

        players.remove(player.getUUID());
        player.setParty(null);


        if (player.getUUID().equals(ownerUuid)) {
            if (players.isEmpty()) {
                RuneQuestPlugin.getPartyManager().removeParty(this.uuid);
                return;
            } else {
                RunePlayer newOwner = RuneQuestPlugin.getPlayerManager().getRunePlayer(players.getFirst());
                if (newOwner != null) setNewOwner(newOwner);
            }
        }

        this.players.forEach(uuid -> {
            RunePlayer member = RuneQuestPlugin.getPlayerManager().getRunePlayer(uuid);
            if (member != null) {
                member.asBukkitPlayer().sendMessage(Component.text(player.asBukkitPlayer().getName() + " has left the party.", Color.ERROR.asColor()));
            }
        });

        reloadMemberNametags();
    }

    public void kickPlayer(RunePlayer whoKicked, RunePlayer player) {
        if (!ownerUuid.equals(whoKicked.getUUID())) {
            throw new IllegalStateException("You are not a party owner.");
        }

        if (!players.contains(player.getUUID())) {
            throw new IllegalArgumentException("The player is not in this party.");
        }

        if (player.getUUID().equals(ownerUuid)) {
            throw new IllegalStateException("Cannot kick party owner.");
        }

        players.remove(player.getUUID());
        player.setParty(null);
        Bukkit.getServer().getPluginManager().callEvent(new PartyPlayerKickedEvent(this, player));


        this.players.forEach(uuid -> {
            RunePlayer member = RuneQuestPlugin.getPlayerManager().getRunePlayer(uuid);
            if (member != null) {
                member.asBukkitPlayer().sendMessage(Component.text(player.asBukkitPlayer().getName() + " has been kicked from the party.", Color.ERROR.asColor()));
            }
        });
        reloadMemberNametags();
    }

    public boolean playerHasInvite(RunePlayer player) {
        return invites.contains(player.getUUID());
    }

    private void reloadMemberNametags() {
        for (UUID memberUuid : players) {
            RunePlayer member = RuneQuestPlugin.getPlayerManager().getRunePlayer(memberUuid);

            if (member != null) {
                member.reloadNametags();
            }

        }
    }

    public void splitXPBetweenMembers(SkillType type, double xp) {
        if (players.isEmpty()) return;

        double equalXp = xp / players.size();

        players.forEach(uuid -> {
            RunePlayer member = RuneQuestPlugin.getPlayerManager().getRunePlayer(uuid);

            if (member != null) {
                member.addXp(type, equalXp, XpSource.PARTY);
            }

        });
    }
}
