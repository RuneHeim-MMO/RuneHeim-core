package org.zuma.examplePlugin.domain.command;

import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.constants.Color;
import org.zuma.examplePlugin.core.constants.SoundType;
import org.zuma.examplePlugin.domain.hud.HudService;
import org.zuma.examplePlugin.domain.item.base.AbstractItemType;
import org.zuma.examplePlugin.domain.item.constants.ItemId;
import org.zuma.examplePlugin.domain.player.RunePlayer;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class MegaphoneCommand implements BasicCommand {
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

        if (runePlayer.getBombsContainer().getMegaphoneUses() <= 0) {
            sender.sendMessage(
                    Component.text("You don't have any megaphone uses left! Purchase more at our website: ")
                            .color(Color.ERROR.asColor())
                            .append(
                                    Component.text("https://store.runeheim.org")
                                            .color(Color.SERVER_BRAND.asColor())
                                            .clickEvent(ClickEvent.openUrl("https://store.runeheim.org"))
                                            .hoverEvent(HoverEvent.showText(Component.text("Click to open the store")))
                            )
            );

            SoundType.FAIL_ACTION.play(runePlayer, false);
            return;
        }

        if (args.length == 0 && !runePlayer.isNextMessageMegaphone()) {
            runePlayer.setNextMessageMegaphone(true);
            HudService.sendInfoMessage(runePlayer, Component.text("Your next message will be sent globally and megaphone will be consumed."));
            HudService.sendInfoMessage(runePlayer, Component.text("Type /megaphone cancel to cancel."));
            return;
        }

        if (args.length == 1 && args[0].equalsIgnoreCase("cancel")) {
            if (runePlayer.isNextMessageMegaphone()) {
                runePlayer.setNextMessageMegaphone(false);
                HudService.sendInfoMessage(runePlayer, Component.text("Megaphone message cancelled."));
            } else {
                sender.sendMessage(Component.text("You don't have a megaphone message pending to cancel!").color(Color.ERROR.asColor()));
            }
            return;
        }

        if (runePlayer.isNextMessageMegaphone()) {
            sender.sendMessage(Component.text("Usage: /megaphone cancel - to cancel your global message.", Color.ERROR.asColor()));
            return;
        } else {
            sender.sendMessage(Component.text("Usage: /megaphone - set your next message to be sent globally using a megaphone use.", Color.ERROR.asColor()));
            return;
        }


    }

    @Override
    public Collection<String> suggest(CommandSourceStack commandSourceStack, String[] args) {
        return List.of("cancel");
    }


}
