package org.zuma.examplePlugin.domain.party;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.constants.Color;
import org.zuma.examplePlugin.core.constants.Icon;
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

    public List<RunePlayer> getOnlineMembers() {
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

        this.getOnlineMembers().forEach(member -> {
            member.asBukkitPlayer().sendMessage(
                    Icon.PARTY.asComponent().append(Component.text(player.asBukkitPlayer().getName(), Color.ACCENT.asColor())
                            .append(Component.text(" is the new owner of the party.", Color.INFO.asColor())))
            );
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

        this.getOnlineMembers().forEach(member -> {
            member.asBukkitPlayer().sendMessage(
                    Icon.PARTY.asComponent().append(Component.text(player.asBukkitPlayer().getName(), Color.ACCENT.asColor())
                            .append(Component.text(" has been invited to the party.", Color.INFO.asColor())))
            );
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

        this.getOnlineMembers().forEach(member -> {
            member.asBukkitPlayer().sendMessage(
                    Icon.PARTY.asComponent().append(Component.text(player.asBukkitPlayer().getName(), Color.ACCENT.asColor())
                            .append(Component.text(" has joined the party.", Color.SUCCESS.asColor())))
            );
        });


        reloadMemberNametags();
    }

    public void leaveParty(RunePlayer player) {
        if (!players.contains(player.getUUID())) {
            throw new IllegalArgumentException("The player is not in this party.");
        }

        players.remove(player.getUUID());
        player.setParty(null);

        this.getOnlineMembers().forEach(member -> {
            member.asBukkitPlayer().sendMessage(
                    Icon.PARTY.asComponent().append(Component.text(player.asBukkitPlayer().getName(), Color.ACCENT.asColor())
                            .append(Component.text(" has left the party.", Color.ERROR.asColor())))
            );
        });


        // Transfer ownership if the owner leaves
        if (player.getUUID().equals(ownerUuid)) {
            if (this.getOnlineMembers().isEmpty()) {
                RuneQuestPlugin.getPartyManager().removeParty(this.uuid);
                return;
            } else {
                RunePlayer newOwner = this.getOnlineMembers().getFirst();
                setNewOwner(newOwner);

            }
        }

        reloadMemberNametags();
    }

    public void kickPlayer(RunePlayer whoKicked, UUID playerUUID) {
        if (!ownerUuid.equals(whoKicked.getUUID())) {
            throw new IllegalStateException("You are not a party owner.");
        }

        if (!players.contains(playerUUID)) {
            throw new IllegalArgumentException("The player is not in this party.");
        }

        if (playerUUID.equals(ownerUuid)) {
            throw new IllegalStateException("Cannot kick party owner.");
        }

        players.remove(playerUUID);
        OfflinePlayer bukkitPlayer = Bukkit.getOfflinePlayer(playerUUID);
        RunePlayer player = RuneQuestPlugin.getPlayerManager().getRunePlayer(playerUUID);

        if (player != null) {
            player.setParty(null);
            Bukkit.getServer().getPluginManager().callEvent(new PartyPlayerKickedEvent(this, player));
        }

        this.getOnlineMembers().forEach(member -> {
            String name = bukkitPlayer.getName();

            if (name != null) {
                member.asBukkitPlayer().sendMessage(
                        Icon.PARTY.asComponent().append(Component.text(bukkitPlayer.getName(), Color.ACCENT.asColor())
                                .append(Component.text(" has been kicked from the party by ", Color.ERROR.asColor()))
                                .append(Component.text(whoKicked.asBukkitPlayer().getName(), Color.ACCENT.asColor())))
                );
            }
        });
        reloadMemberNametags();
    }

    public boolean playerHasInvite(RunePlayer player) {
        return invites.contains(player.getUUID());
    }

    private void reloadMemberNametags() {
        this.getOnlineMembers().forEach(RunePlayer::reloadNametags);

    }

    public void splitXPBetweenMembers(SkillType type, double xp) {
        if (players.isEmpty()) return;
        double equalXp = xp / this.getOnlineMembers().size();
        this.getOnlineMembers().forEach(member -> member.addXp(type, equalXp, XpSource.PARTY));
    }
}
