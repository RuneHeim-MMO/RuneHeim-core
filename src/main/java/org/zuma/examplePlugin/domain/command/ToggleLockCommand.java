package org.zuma.examplePlugin.domain.command;

import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.constants.Color;
import org.zuma.examplePlugin.core.constants.SoundType;
import org.zuma.examplePlugin.core.permissions.Permission;
import org.zuma.examplePlugin.core.utils.CurrencyUtils;
import org.zuma.examplePlugin.core.utils.PlayerUtils;
import org.zuma.examplePlugin.core.utils.TypeUtils;
import org.zuma.examplePlugin.domain.hud.HudService;
import org.zuma.examplePlugin.domain.item.base.AbstractItemType;
import org.zuma.examplePlugin.domain.item.constants.ItemId;
import org.zuma.examplePlugin.domain.item.handlers.LockedItemHandler;
import org.zuma.examplePlugin.domain.player.RunePlayer;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class ToggleLockCommand implements BasicCommand {
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

        ItemStack inHand = PlayerUtils.getItemStackInHand(bukkitPlayer);

        if (inHand.isEmpty()) {
            HudService.sendFailMessage(runePlayer, Component.text("You must be holding an item to lock or unlock it."));
            return;
        }


        if (LockedItemHandler.isItemStackLocked(inHand)) {
            LockedItemHandler.unlockItemStack(inHand);
            HudService.sendInfoMessage(runePlayer, inHand.displayName().append(Component.text(" is now unlocked. You can now drop or trade it.")));
            SoundType.ITEM_LOCK_TO_INVENTORY.play(runePlayer, false);
        } else {
            LockedItemHandler.lockItemStack(inHand);
            HudService.sendInfoMessage(runePlayer, inHand.displayName().append(Component.text(" is now locked. You won't be able to drop or trade it.")));
            SoundType.ITEM_UNLOCK_FROM_INVENTORY.play(runePlayer, false);
        }


    }

    @Override
    public Collection<String> suggest(CommandSourceStack commandSourceStack, String[] args) {
        return List.of();
    }


}
