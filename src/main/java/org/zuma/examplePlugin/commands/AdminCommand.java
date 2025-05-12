package org.zuma.examplePlugin.commands;


import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;

@SuppressWarnings("UnstableApiUsage")
public class AdminCommand implements BasicCommand {

    @Override
    public void execute(CommandSourceStack commandSourceStack, String[] args) {
        CommandSender sender = commandSourceStack.getSender();


        if (!(sender instanceof Player player)) {
            sender.sendMessage("This command can be only run by players.");
            return;
        }

        player.sendMessage("You are a player, and you just ran an admin command.");


    }

    @Override
    public @Nullable String permission() {
        return "runequest.admin";
    }

}


