package org.zuma.examplePlugin.domain.command.help;

import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.zuma.examplePlugin.core.constants.Color;

import java.util.Collection;
import java.util.List;

public class SocialsCommand implements BasicCommand {

    public static void sendLink(int index, CommandSender sender, String label, String url) {
        sender.sendMessage(Component.text(index + ") ").decoration(TextDecoration.BOLD, false).color(Color.SERVER_BRAND.asColor()).append(
                Component.text("[CLICK] ")
                        .color(Color.SUCCESS.asColor())
                        .clickEvent(ClickEvent.openUrl(url))
                        .hoverEvent(HoverEvent.showText(
                                Component.text("Click to open: ").append(Component.text(url)))).append(Component.text("- " + label).decoration(TextDecoration.BOLD, false)
                                .color(Color.ITEM_LORE_DESCRIPTION.asColor())))

        );
    }

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
                Component.text("RuneHeim Social Links")
                        .color(Color.SERVER_BRAND.asColor())
                        .decorate(TextDecoration.BOLD)
        );
        sender.sendMessage(line);

        sendLink(1, sender, "Website", "https://runeheim.org");
        sendLink(2, sender, "Our store", "https://store.runeheim.org");
        sendLink(3, sender, "Hi-scores / Wiki", "https://database.runeheim.org/");
        sendLink(4, sender, "Rules", "https://runeheim.org/rules");
        sendLink(5, sender, "Map", "https://database.runeheim.org/map");
        sendLink(6, sender, "Discord", "https://discord.com/invite/dZpS2mkb2k");
        sendLink(7, sender, "YouTube", "https://www.youtube.com/@RuneHeimOfficial");
        sendLink(8, sender, "X (Twitter)", "https://x.com/RuneHeimMMORPG");

        sender.sendMessage(line);
        sender.sendMessage(Component.empty());
    }

    @Override
    public Collection<String> suggest(CommandSourceStack commandSourceStack, String[] args) {
        return List.of();
    }


}
