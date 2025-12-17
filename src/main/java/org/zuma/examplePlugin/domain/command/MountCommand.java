package org.zuma.examplePlugin.domain.command;

import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.text.Component;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.constants.Color;
import org.zuma.examplePlugin.domain.hud.HudService;
import org.zuma.examplePlugin.domain.player.RunePlayer;

import java.util.*;

public class MountCommand implements BasicCommand {
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

        if (runePlayer.hasPendingActions()) return;
        if (runePlayer.isInCutscene()) return;
        if (runePlayer.isDead()) return;

        if (args.length == 0) {
            RuneQuestPlugin.getMountHandler().spawnMount(runePlayer);
            return;
        } else if (args.length == 1) {
            RuneQuestPlugin.getMountHandler().setMountName(runePlayer, args[0]);
            return;
        }

        bukkitPlayer.sendMessage(Component.text("Usage: /mount or /mount <name>", Color.ERROR.asColor()));


    }

    @Override
    public Collection<String> suggest(CommandSourceStack commandSourceStack, String[] args) {
        return List.of();
    }


}
