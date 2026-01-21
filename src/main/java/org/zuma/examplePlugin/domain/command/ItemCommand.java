package org.zuma.examplePlugin.domain.command;

import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.text.Component;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.constants.Color;
import org.zuma.examplePlugin.domain.gui.constants.InventoryId;
import org.zuma.examplePlugin.domain.hud.HudService;
import org.zuma.examplePlugin.domain.item.base.AbstractItemType;
import org.zuma.examplePlugin.domain.player.RunePlayer;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

public class ItemCommand implements BasicCommand {
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

        if (args.length >= 1) {
            String joined = String.join(" ", args);

            if (joined.length() > 64) {
                HudService.sendFailMessage(runePlayer, Component.text("Search term too long."));
                return;
            }

            String search = joined.toLowerCase();
            Optional<AbstractItemType> matchingItem = RuneQuestPlugin.getItemRegistry().getAllItems().stream()
                    .filter(i -> i.displayName.toLowerCase().contains(search)).findFirst();

            if (matchingItem.isPresent()) {
                AbstractItemType itemType = matchingItem.get();
                RuneQuestPlugin.getGUIHandler().openInventory(InventoryId.ITEM_EXPLORER.asInt(), runePlayer, null, Map.of("itemId", String.valueOf(itemType.id)));
            } else {
                HudService.sendFailMessage(runePlayer, Component.text("No item found matching: " + joined));
            }
            return;
        }

        bukkitPlayer.sendMessage(Component.text("Usage: /item <name>", Color.ERROR.asColor()));


    }

    @Override
    public Collection<String> suggest(CommandSourceStack commandSourceStack, String[] args) {
        String search = String.join(" ", args).toLowerCase();

        if (search.length() > 64) {
            return java.util.Collections.emptyList();
        }

        return RuneQuestPlugin.getItemRegistry().getAllItems().stream()
                .filter(i -> i.id > 0 && i.displayName != null)
                .map(i -> i.displayName)
                .filter(name -> name.toLowerCase().contains(search))
                .sorted((a, b) -> {
                    boolean aStarts = a.toLowerCase().startsWith(search);
                    boolean bStarts = b.toLowerCase().startsWith(search);
                    if (aStarts && !bStarts) return -1;
                    if (!aStarts && bStarts) return 1;
                    return a.compareToIgnoreCase(b);
                })
                .toList();
    }

}
