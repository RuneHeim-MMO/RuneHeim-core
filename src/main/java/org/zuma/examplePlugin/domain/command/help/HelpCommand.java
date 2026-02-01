package org.zuma.examplePlugin.domain.command.help;

import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.zuma.examplePlugin.core.constants.Color;

import java.util.Collection;
import java.util.List;

public class HelpCommand implements BasicCommand {

    public static void sendCommand(int index, CommandSender sender, String command, String description) {
        sender.sendMessage(Component.text(index + ") ").decoration(TextDecoration.BOLD, false).color(Color.SERVER_BRAND.asColor()).append(
                Component.text(command).color(Color.SUCCESS.asColor())).append(Component.text(" - " + description, Color.ITEM_LORE_DESCRIPTION.asColor())));
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
                Component.text("Commands")
                        .color(Color.SERVER_BRAND.asColor())
                        .decorate(TextDecoration.BOLD)
        );
        sender.sendMessage(line);

        sendCommand(1, sender, "/skills", "View your skill levels and progress.");
        sendCommand(2, sender, "/pouch drop <amount>", "Drop coins from your pouch.");
        sendCommand(3, sender, "/stuck", "Teleport to the nearest safe location.");
        sendCommand(4, sender, "/modifiers", "Show active modifiers.");
        sendCommand(5, sender, "/bombs", "View your available bombs and their effects.");
        sendCommand(6, sender, "/megaphone", "Send a message to all players on the server.");
        sendCommand(7, sender, "/social", "View links to our social media pages.");
        sendCommand(8, sender, "/map", "View the server map online.");
        sendCommand(9, sender, "/rules", "View the server rules.");
        sendCommand(10, sender, "/item", "View and explore items in the game.");
        sendCommand(11, sender, "/staffhelp <message>", "Send message to online staff members.");
        sendCommand(12, sender, "/togglelock", "Lock/unlock item in hand to prevent accidental drops.");

        sender.sendMessage(line);
        sender.sendMessage(Component.empty());
    }

    @Override
    public Collection<String> suggest(CommandSourceStack commandSourceStack, String[] args) {
        return List.of();
    }


}
