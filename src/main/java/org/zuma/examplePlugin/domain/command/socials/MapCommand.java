package org.zuma.examplePlugin.domain.command.socials;

import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.constants.Color;
import org.zuma.examplePlugin.domain.player.RunePlayer;

import java.util.Collection;
import java.util.List;

public class MapCommand implements BasicCommand {


    @Override
    public void execute(CommandSourceStack commandSourceStack, String[] args) {
        CommandSender sender = commandSourceStack.getSender();

        if (!(sender instanceof Player bukkitPlayer)) {
            sender.sendMessage(Component.text("This command can be only used by players.").color(Color.ERROR.asColor()));
            return;
        }

        Component line = Component.text("───────────────────────────────────")
                .color(NamedTextColor.DARK_GRAY);

        sender.sendMessage(Component.empty()); // top spacing
        sender.sendMessage(line);
        sender.sendMessage(
                Component.text("RuneHeim Links")
                        .color(Color.SERVER_BRAND.asColor())
                        .decorate(TextDecoration.BOLD)
        );
        sender.sendMessage(line);


        SocialsCommand.sendLink(1, sender, "Map", "https://database.runeheim.org/map");

        sender.sendMessage(line);
        sender.sendMessage(Component.empty());
    }

    @Override
    public Collection<String> suggest(CommandSourceStack commandSourceStack, String[] args) {
        return List.of();
    }


}
