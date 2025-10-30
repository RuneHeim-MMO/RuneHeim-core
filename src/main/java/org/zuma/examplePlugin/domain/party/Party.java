package org.zuma.examplePlugin.domain.party;

import io.papermc.paper.chat.ChatRenderer;
import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.chat.SignedMessage;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.constants.Color;
import org.zuma.examplePlugin.core.constants.Icon;
import org.zuma.examplePlugin.core.constants.XpSource;
import org.zuma.examplePlugin.domain.player.RunePlayer;
import org.zuma.examplePlugin.domain.skill.constants.SkillType;
import org.zuma.examplePlugin.events.party.*;

import javax.annotation.Nullable;
import java.util.*;
import java.util.stream.Collectors;

public class Party {
    private final UUID uuid;
    private final List<PartyMember> players;
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
        this.players.add(new PartyMember(owner.getUUID(), PartyRole.OWNER));
        this.maxMembers = maxMembers;
    }

    public UUID getUuid() {
        return uuid;
    }

    public List<RunePlayer> getOnlinePlayers() {
        return players.stream().map(PartyMember::getRunePlayer).filter(Objects::nonNull).toList();
    }

    @Nullable
    public PartyMember getPartyMember(UUID playerUUID) {
        return players.stream().filter(pm -> pm.getUuid().equals(playerUUID)).findFirst().orElse(null);
    }

    public List<PartyMember> getAllMembers() {
        return Collections.unmodifiableList(players);
    }

    public List<UUID> getMemberUUIDs() {
        return players.stream().map(PartyMember::getUuid).toList();
    }

    public UUID getOwnerUuid() {
        return ownerUuid;
    }

    public String getOwnerUsername() {
        return ownerUsername;
    }

    public void setNewOwner(RunePlayer player) {
        if (!getMemberUUIDs().contains(player.getUUID())) {
            throw new IllegalStateException("This player is not in this party!");
        }

        ownerUuid = player.getUUID();
        ownerUsername = player.asBukkitPlayer().getName();
        Bukkit.getServer().getPluginManager().callEvent(new PartyOwnerUpdateEvent(this, player));
        Bukkit.getServer().getPluginManager().callEvent(new PartyUpdateEvent(this));

        this.getOnlinePlayers().forEach(member -> {
            member.asBukkitPlayer().sendMessage(
                    Icon.PARTY.asComponent().append(Component.text(player.asBukkitPlayer().getName(), Color.ACCENT.asColor())
                            .append(Component.text(" is the new owner of the party.", Color.INFO.asColor())))
            );
        });

        reloadMemberNametags();
    }

    public void sendInvite(RunePlayer player) {
        if (getMemberUUIDs().contains(player.getUUID())) {
            throw new IllegalStateException("The player is already in this party.");
        }

        if (players.size() >= maxMembers) {
            throw new IllegalStateException("This party can have maximum of " + maxMembers + " members.");
        }

        if (!invites.add(player.getUUID())) {
            throw new IllegalStateException("The player is already invited.");
        }

        Bukkit.getServer().getPluginManager().callEvent(new PartyPlayerInvited(this, player));
        Bukkit.getServer().getPluginManager().callEvent(new PartyUpdateEvent(this));

        this.getOnlinePlayers().forEach(member -> {
            member.asBukkitPlayer().sendMessage(
                    Icon.PARTY.asComponent().append(Component.text(player.asBukkitPlayer().getName(), Color.ACCENT.asColor())
                            .append(Component.text(" has been invited to the party.", Color.INFO.asColor())))
            );
        });
    }

    public void acceptInvite(RunePlayer player) {
        if (getMemberUUIDs().contains(player.getUUID())) {
            throw new IllegalStateException("You are already in this party.");
        }

        if (!invites.contains(player.getUUID())) {
            throw new IllegalStateException("You are not invited to this party.");
        }

        if (players.size() >= maxMembers) {
            throw new IllegalStateException("This party is full!");
        }

        invites.remove(player.getUUID());
        players.add(new PartyMember(player.getUUID(), PartyRole.MEMBER));
        player.setParty(this);
        Bukkit.getServer().getPluginManager().callEvent(new PartyPlayerJoinedEvent(this, player));
        Bukkit.getServer().getPluginManager().callEvent(new PartyUpdateEvent(this));

        this.getOnlinePlayers().forEach(member -> {
            member.asBukkitPlayer().sendMessage(
                    Icon.PARTY.asComponent().append(Component.text(player.asBukkitPlayer().getName(), Color.ACCENT.asColor())
                            .append(Component.text(" has joined the party.", Color.SUCCESS.asColor())))
            );
        });


        reloadMemberNametags();
    }

    public void leaveParty(RunePlayer player) {
        if (!getMemberUUIDs().contains(player.getUUID())) {
            throw new IllegalArgumentException("The player is not in this party.");
        }

        players.remove(players.stream().filter(pm -> pm.getUuid().equals(player.getUUID())).findFirst().orElse(null));
        player.setParty(null);

        this.getOnlinePlayers().forEach(member -> {
            member.asBukkitPlayer().sendMessage(
                    Icon.PARTY.asComponent().append(Component.text(player.asBukkitPlayer().getName(), Color.ACCENT.asColor())
                            .append(Component.text(" has left the party.", Color.ERROR.asColor())))
            );
        });


        // Transfer ownership if the owner leaves
        if (player.getUUID().equals(ownerUuid)) {
            if (this.getOnlinePlayers().isEmpty()) {
                RuneQuestPlugin.getPartyManager().removeParty(this.uuid);
                return;
            } else {
                RunePlayer newOwner = this.getOnlinePlayers().getFirst();
                setNewOwner(newOwner);

            }
        }

        Bukkit.getServer().getPluginManager().callEvent(new PartyUpdateEvent(this));
        reloadMemberNametags();
    }

    public void kickPlayer(RunePlayer whoKicked, UUID playerUUID) {
        if (!ownerUuid.equals(whoKicked.getUUID())) {
            throw new IllegalStateException("You are not a party owner.");
        }

        if (!getMemberUUIDs().contains(playerUUID)) {
            throw new IllegalArgumentException("The player is not in this party.");
        }

        if (playerUUID.equals(ownerUuid)) {
            throw new IllegalStateException("Cannot kick party owner.");
        }

        players.remove(players.stream().filter(pm -> pm.getUuid().equals(playerUUID)).findFirst().orElse(null));
        OfflinePlayer bukkitPlayer = Bukkit.getOfflinePlayer(playerUUID);
        RunePlayer player = RuneQuestPlugin.getPlayerManager().getRunePlayer(playerUUID);

        if (player != null) {
            player.setParty(null);
            Bukkit.getServer().getPluginManager().callEvent(new PartyPlayerKickedEvent(this, player));
            Bukkit.getServer().getPluginManager().callEvent(new PartyUpdateEvent(this));
        }

        this.getOnlinePlayers().forEach(member -> {
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

    public void sendMessageToMembers(RunePlayer sender, String message) {
        PartyMessageEvent event = new PartyMessageEvent(this, sender, getOnlinePlayers(), message);
        Bukkit.getPluginManager().callEvent(event);
    }

    private void reloadMemberNametags() {
        this.getOnlinePlayers().forEach(RunePlayer::reloadNametags);
    }

    public void splitXPBetweenMembers(RunePlayer contributor, SkillType type, double xp) {
        if (players.isEmpty()) return;
        double equalXp = xp / this.getOnlinePlayers().size();
        this.getOnlinePlayers().forEach(member -> member.addXp(type, equalXp, XpSource.PARTY));

        PartyMember matchingMember = players.stream().filter(pm -> pm.getUuid().equals(contributor.getUUID())).findFirst().orElse(null);
        if (matchingMember != null) {
            matchingMember.addXpContribution(type, (int) xp);
            Bukkit.getPluginManager().callEvent(new PartyUpdateEvent(this));
        }
    }
}
