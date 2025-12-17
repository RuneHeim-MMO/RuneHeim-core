package org.zuma.examplePlugin.domain.command;

import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.constants.Color;
import org.zuma.examplePlugin.core.permissions.Permission;
import org.zuma.examplePlugin.core.utils.CurrencyUtils;
import org.zuma.examplePlugin.core.utils.TypeUtils;
import org.zuma.examplePlugin.domain.hud.HudService;
import org.zuma.examplePlugin.domain.item.base.AbstractItemType;
import org.zuma.examplePlugin.domain.item.constants.ItemId;
import org.zuma.examplePlugin.domain.player.RunePlayer;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class PouchCommand implements BasicCommand {
    private static final AbstractItemType coinsType = RuneQuestPlugin.getItemRegistry().getType(ItemId.COINS.asInt());
    private static final HashMap<UUID, Long> dropTimes = new HashMap<>(); // Prevent coins spamming.
    private static final int DROP_COOLDOWN_SECONDS = 5;


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

        if (runePlayer.isInCutscene()) return;
        if (runePlayer.isDead()) return;

        if (args.length == 2 && args[0].equalsIgnoreCase("drop") && TypeUtils.isInteger(args[1])) {

            Long dropTime = dropTimes.get(runePlayer.getUUID());
            if (dropTime != null && System.currentTimeMillis() - dropTime <= DROP_COOLDOWN_SECONDS * 1000L && !runePlayer.getRole().hasPermission(Permission.COOLDOWN_BYPASS)) {
                HudService.showFailTitle(runePlayer, "Whoa there, slow down, cowboy. Try that again in a second.");
                return;
            }


            int amount = Integer.parseInt(args[1]);

            if (amount > runePlayer.getPouchCoins()) {
                HudService.sendFailMessage(runePlayer, Component.text("You do not have ")
                        .append(CurrencyUtils.getCoinsCurrencyComponent(amount))
                        .append(Component.text(" coins in your pouch!")));
                return;
            }

            if (coinsType == null) {
                return;
            }

            HudService.showCoinsChangeBubble(runePlayer, -amount);
            runePlayer.setPouchCoins(runePlayer.getPouchCoins() - amount, false);
            dropTimes.put(runePlayer.getUUID(), System.currentTimeMillis());

            // Simulate as if player has dropped it;
            Location dropLocation = bukkitPlayer.getEyeLocation();
            Item droppedItem = bukkitPlayer.getWorld().dropItem(dropLocation, coinsType.createItemStackOfType(amount));
            Vector direction = bukkitPlayer.getLocation().getDirection().normalize().multiply(0.25);
            direction.setY(0.2 + Math.random() * 0.1);
            droppedItem.setVelocity(direction);

            return;
        }

        bukkitPlayer.sendMessage(Component.text("Usage: /pouch drop <amount>", Color.ERROR.asColor()));


    }

    @Override
    public Collection<String> suggest(CommandSourceStack commandSourceStack, String[] args) {
        return List.of("drop");
    }


}
