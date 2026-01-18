package org.zuma.examplePlugin.domain.party;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.constants.Color;
import org.zuma.examplePlugin.core.constants.Icon;
import org.zuma.examplePlugin.core.constants.XpSource;
import org.zuma.examplePlugin.domain.party.constants.ContributionMode;
import org.zuma.examplePlugin.domain.party.constants.PartyRole;
import org.zuma.examplePlugin.domain.player.RunePlayer;
import org.zuma.examplePlugin.domain.skill.constants.SkillType;
import org.zuma.examplePlugin.events.party.*;

import javax.annotation.Nullable;
import java.util.*;

public class Party {
    private final UUID uuid;
    private final List<PartyMember> players;
    private final Set<UUID> invites;
    private UUID ownerUuid;
    private String ownerUsername;
    private int maxMembers;
    private boolean xpSharing;

    public Party(UUID uuid, RunePlayer owner) {
        this.uuid = uuid;
        this.ownerUuid = owner.getUUID();
        this.ownerUsername = owner.asBukkitPlayer().getName();
        this.players = new ArrayList<>();
        this.invites = new HashSet<>();
        this.players.add(new PartyMember(owner.getUUID(), owner.asBukkitPlayer().getName(), PartyRole.LEADER));
        this.maxMembers = owner.getRole().getMaxPartySize();
        this.xpSharing = true;
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
        maxMembers = player.getRole().getMaxPartySize();
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
        players.add(new PartyMember(player.getUUID(), player.asBukkitPlayer().getName(), PartyRole.MEMBER));
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

    public void setXPSharing(RunePlayer setter, boolean enabled) {
        if (!ownerUuid.equals(setter.getUUID())) {
            throw new IllegalStateException("You are not a party owner.");
        }

        this.xpSharing = enabled;
        Bukkit.getServer().getPluginManager().callEvent(new PartyUpdateEvent(this));

        this.getOnlinePlayers().forEach(member -> {
            member.asBukkitPlayer().sendMessage(
                    Icon.PARTY.asComponent().append(Component.text("XP Sharing has been ", Color.INFO.asColor())
                            .append(Component.text(enabled ? "enabled" : "disabled", enabled ? Color.SUCCESS.asColor() : Color.ERROR.asColor()))
                            .append(Component.text(" by ", Color.INFO.asColor()))
                            .append(Component.text(setter.asBukkitPlayer().getName(), Color.ACCENT.asColor())))
            );
        });
    }

    public void setContributionMode(RunePlayer setter, ContributionMode mode) {
        PartyMember member = this.getPartyMember(setter.getUUID());

        if (member == null) {
            throw new IllegalArgumentException("The player is not in this party.");
        }

        Bukkit.getServer().getPluginManager().callEvent(new PartyUpdateEvent(this));
        member.setContributionMode(mode);
    }

    public void kickPlayer(RunePlayer whoKicked, String username) {
        if (!ownerUuid.equals(whoKicked.getUUID())) {
            throw new IllegalStateException("You are not a party owner.");
        }

        PartyMember matchingMember = players.stream()
                .filter(m -> m.getUsername().equalsIgnoreCase(username))
                .findFirst()
                .orElse(null);

        if (matchingMember == null) {
            throw new IllegalArgumentException("This player is not found in this party.");
        }

        if (matchingMember.getUuid().equals(ownerUuid)) {
            throw new IllegalStateException("Cannot kick party owner.");
        }

        UUID playerUUID = matchingMember.getUuid();
        players.remove(players.stream().filter(pm -> pm.getUuid().equals(playerUUID)).findFirst().orElse(null));
        Player bukkitPlayer = Bukkit.getPlayer(playerUUID);


        if (bukkitPlayer != null && bukkitPlayer.isOnline()) {
            RunePlayer player = RuneQuestPlugin.getPlayerManager().getRunePlayer(playerUUID);

            if (player != null) {
                player.setParty(null);
                Bukkit.getServer().getPluginManager().callEvent(new PartyPlayerKickedEvent(this, player));
                Bukkit.getServer().getPluginManager().callEvent(new PartyUpdateEvent(this));
            }
        }

        this.getOnlinePlayers().forEach(member -> {
            member.asBukkitPlayer().sendMessage(
                    Icon.PARTY.asComponent().append(Component.text(matchingMember.getUsername(), Color.ACCENT.asColor())
                            .append(Component.text(" has been kicked from the party by ", Color.ERROR.asColor()))
                            .append(Component.text(whoKicked.asBukkitPlayer().getName(), Color.ACCENT.asColor())))
            );
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

    public boolean isXpSharingEnabled() {
        return xpSharing;
    }

    public void splitXPBetweenMembers(RunePlayer contributor, SkillType type, double xp) {
        if (players.isEmpty()) return;

        if (!isXpSharingEnabled()) {
            contributor.addXp(type, xp, XpSource.PERSONAL);
            return;
        }

        PartyMember contributorMember = getPartyMember(contributor.getUUID());
        if (contributorMember == null) return;

        int contributionPercentage = contributorMember.getContributionMode().getPercent();
        double contributedXp = xp * (contributionPercentage / 100.0); // XP to share
        double personalXp = xp - contributedXp; // XP contributor keeps

        // Give contributor their personal XP
        contributor.addXp(type, personalXp, XpSource.PERSONAL);

        // Track contribution
        contributorMember.addXpContribution(type, Math.round(contributedXp));
        Bukkit.getPluginManager().callEvent(new PartyUpdateEvent(this));

        // Get other online members
        List<RunePlayer> others = this.getOnlinePlayers().stream()
                .filter(m -> !m.getUUID().equals(contributor.getUUID()))
                .filter(p -> p.getSkill(type).getBaseLevel() != 99) // Exclude max level players
                .toList();

        // Split contributed XP among others
        if (!others.isEmpty()) {
            double equalXp = contributedXp / others.size();
            others.forEach(member -> member.addXp(type, equalXp, XpSource.PARTY));
        }

        Bukkit.getPluginManager().callEvent(new PartyUpdateEvent(this));
    }

}
