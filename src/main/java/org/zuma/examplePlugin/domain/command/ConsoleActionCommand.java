package org.zuma.examplePlugin.domain.command;

import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.constants.Color;
import org.zuma.examplePlugin.domain.action.ScriptedAction;
import org.zuma.examplePlugin.domain.action.ScriptedActionHandler;
import org.zuma.examplePlugin.domain.player.RunePlayer;

import java.util.*;

public class ConsoleActionCommand implements BasicCommand {


    @Override
    public void execute(CommandSourceStack commandSourceStack, String[] args) {
        CommandSender sender = commandSourceStack.getSender();

        if (sender instanceof Player bukkitPlayer) {
            if (args.length != 1) {
                sender.sendMessage(Component.text("Invalid /action format! Use /action {action}.").color(Color.ERROR.asColor()));
                return;
            }

            if (!bukkitPlayer.isOp()) {
                sender.sendMessage(Component.text("This command can be only used by console.").color(Color.ERROR.asColor()));
                return;
            }

            RunePlayer runePlayer = RuneQuestPlugin.getPlayerManager().getRunePlayer(bukkitPlayer);
            ScriptedActionHandler.executeAction(args[0], runePlayer, null, new ScriptedActionHandler.ActionCaller(ScriptedActionHandler.ActionCallerType.PLAYER_COMMAND, "{username: " + bukkitPlayer.getName() + "}"));
        } else {

            if (args.length != 3) {
                sender.sendMessage(Component.text("Invalid /action format! Use /action {target_player} {action} {executor_identifier}.").color(Color.ERROR.asColor()));
                return;
            }

            Player matchingPlayer = Bukkit.getPlayer(args[0]);

            if (matchingPlayer == null || !matchingPlayer.isOnline()) {
                sender.sendMessage(Component.text("Player " + args[0] + " is not found or is not online.").color(Color.ERROR.asColor()));
                return;
            }

            RunePlayer runePlayer = RuneQuestPlugin.getPlayerManager().getRunePlayer(matchingPlayer);

            if (runePlayer == null) {
                sender.sendMessage(Component.text("Players " + args[0] + " RunePlayer instance not found.").color(Color.ERROR.asColor()));
                return;
            }

            ScriptedActionHandler.executeAction(args[1], runePlayer, null, new ScriptedActionHandler.ActionCaller(ScriptedActionHandler.ActionCallerType.CONSOLE_COMMAND, args[2]));

        }

    }

    @Override
    public Collection<String> suggest(CommandSourceStack commandSourceStack, String[] args) {
        return Arrays.stream(ScriptedAction.values()).map(ScriptedAction::getPreview).toList();
    }


}
