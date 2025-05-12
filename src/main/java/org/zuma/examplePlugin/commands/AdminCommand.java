package org.zuma.examplePlugin.commands;

import cloud.commandframework.CommandManager;
import cloud.commandframework.paper.PaperCommandManager;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.zuma.examplePlugin.RuneQuestPlugin;

public class AdminCommand {

    public AdminCommand(){
        PaperCommandManager<CommandSender> commandManager = RuneQuestPlugin.getCommandManager();
        commandManager.command(commandManager.commandBuilder("admin")
                .senderType(Player.class)
                .handler(context -> {
                    Player player = context.sender();
                    player.sendMessage("Hello, player!");
                })
        );
    }
}


