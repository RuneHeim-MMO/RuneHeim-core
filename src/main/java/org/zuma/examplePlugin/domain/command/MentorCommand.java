package org.zuma.examplePlugin.domain.command;

import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.text.Component;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.constants.Color;
import org.zuma.examplePlugin.domain.player.RunePlayer;

import java.util.Collection;

public class MentorCommand implements BasicCommand {

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


        if (args.length == 1) {
            if (args[0].length() > 32) {
                sender.sendMessage(Component.text("Mentor name cannot be longer than 32 characters!").color(Color.ERROR.asColor()));
                return;
            }

            RuneQuestPlugin.getReferralManager().setMentorForPlayer(runePlayer, args[0]);
            return;
        }


        sender.sendMessage(Component.text("Usage: /mentor <mentor_name>").color(Color.ERROR.asColor()));
        return;

    }

    @Override
    public Collection<String> suggest(CommandSourceStack commandSourceStack, String[] args) {
        return RuneQuestPlugin.getPlayerManager().getOnlinePlayers().stream().map(p -> p.asBukkitPlayer().getName()).toList();
    }


}
