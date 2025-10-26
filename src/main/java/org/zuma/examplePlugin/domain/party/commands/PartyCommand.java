package org.zuma.examplePlugin.domain.party.commands;

import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.constants.Color;
import org.zuma.examplePlugin.domain.hud.HudService;
import org.zuma.examplePlugin.domain.party.Party;
import org.zuma.examplePlugin.domain.player.RunePlayer;

import java.util.Collection;
import java.util.List;

public class PartyCommand implements BasicCommand {

    @Override
    public void execute(CommandSourceStack commandSourceStack, String[] args) {
        CommandSender sender = commandSourceStack.getSender();

        if (!(sender instanceof Player bukkitPlayer)) {
            sender.sendMessage(Component.text("This command can be only used by players.").color(Color.ERROR.asColor()));
            return;
        }

        RunePlayer runePlayer = RuneQuestPlugin.getPlayerManager().getRunePlayer(bukkitPlayer);

        if (runePlayer == null) {
            sender.sendMessage(Component.text("Could not find RunePlayer instance!").color(Color.ERROR.asColor()));
            return;
        }

        try {
            if (args.length == 0) {
                runePlayer.asBukkitPlayer().sendMessage(Component.text("Usage: /party <create|disband|invite|join|kick|leave>"));
                return;
            }

            if (args.length == 1 && args[0].equalsIgnoreCase("create")) {
                RuneQuestPlugin.getPartyManager().createParty(runePlayer);
                return;
            }

            if (args.length == 1 && args[0].equalsIgnoreCase("disband")) {
                RuneQuestPlugin.getPartyManager().disbandParty(runePlayer);
                return;
            }

            if (args[0].equalsIgnoreCase("leave")) {
                Party playersParty = RuneQuestPlugin.getPartyManager().getPlayersParty(runePlayer);

                if (playersParty == null) {
                    throw new IllegalStateException("You are not in a party!");
                }

                playersParty.leaveParty(runePlayer);
                runePlayer.asBukkitPlayer().sendMessage(Component.text("You have left " + playersParty.getOwnerUsername() + "'s party.", Color.INFO.asColor()));
                return;
            }

            if (args[0].equalsIgnoreCase("invite")) {
                if (args.length != 2) {
                    throw new IllegalStateException("Usage: /party invite <player>");
                }

                Party playersParty = RuneQuestPlugin.getPartyManager().getPlayersParty(runePlayer);

                if (playersParty == null) {
                    RuneQuestPlugin.getPartyManager().createParty(runePlayer);
                    playersParty = RuneQuestPlugin.getPartyManager().getPlayersParty(runePlayer);
                }

                Player candidate = Bukkit.getPlayer(args[1]);
                RunePlayer runeCandidate = RuneQuestPlugin.getPlayerManager().getRunePlayer(candidate);

                if (runeCandidate == null) {
                    throw new IllegalStateException("Could not find player with name: " + args[1]);
                }

                playersParty.sendInvite(runeCandidate);
                runeCandidate.asBukkitPlayer().sendMessage(Component.text("You have been invited to " + playersParty.getOwnerUsername() + "'s party. Use /party join " + playersParty.getOwnerUsername() + " to join.", Color.INFO.asColor()));
                return;
            }

            if (args[0].equalsIgnoreCase("join")) {
                if (args.length != 2) {
                    throw new IllegalStateException("Usage: /party join <party member>");
                }

                Party playersParty = RuneQuestPlugin.getPartyManager().getPlayersParty(runePlayer);

                if (playersParty != null) {
                    throw new IllegalStateException("You are already in a party!");
                }

                Player partyMember = Bukkit.getPlayer(args[1]);

                if (partyMember == null) {
                    throw new IllegalStateException("Could not find player with name: " + args[1]);
                }

                Party joiningParty = RuneQuestPlugin.getPartyManager().getPlayersParty(partyMember);

                if (joiningParty == null) {
                    throw new IllegalStateException("The player " + args[1] + " is not in a party.");
                }

                joiningParty.acceptInvite(runePlayer);
                return;
            }

            if (args[0].equalsIgnoreCase("kick")) {
                if (args.length != 2) {
                    throw new IllegalStateException("Usage: /party kick <party member>");
                }

                Party playersParty = RuneQuestPlugin.getPartyManager().getPlayersParty(runePlayer);

                if (playersParty == null) {
                    throw new IllegalStateException("You are not in a party!");
                }

                Player candidate = Bukkit.getPlayer(args[1]);
                RunePlayer runeCandidate = RuneQuestPlugin.getPlayerManager().getRunePlayer(candidate);

                if (runeCandidate == null) {
                    throw new IllegalStateException("Could not find player with name: " + args[1]);
                }


                playersParty.kickPlayer(runePlayer, runeCandidate);
                runeCandidate.asBukkitPlayer().sendMessage(Component.text("You have been kicked from " + playersParty.getOwnerUsername() + "'s party.", Color.ERROR.asColor()));
                return;
            }

        } catch (Exception e) {
            HudService.sendFailMessage(runePlayer, Component.text(e.getMessage()));
        }

    }

    @Override
    public Collection<String> suggest(CommandSourceStack commandSourceStack, String[] args) {
        String prefix = args.length == 3 ? args[2].toLowerCase() : null;
        String senderName = commandSourceStack.getSender().getName();

        if (args.length == 2 && (args[0].equalsIgnoreCase("invite") || args[0].equalsIgnoreCase("kick") || args[0].equalsIgnoreCase("join"))) {
            return Bukkit.getOnlinePlayers().stream()
                    .map(Player::getName)
                    .filter(name -> !senderName.equalsIgnoreCase(name))   // exclude sender
                    .filter(name -> prefix != null ? name.toLowerCase().startsWith(prefix) : true) // match typed prefix
                    .toList();
        }


        return List.of("create", "disband", "invite", "join", "kick", "leave");
    }

}
