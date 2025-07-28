package org.zuma.examplePlugin.domain.player.handlers;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.constants.Color;
import org.zuma.examplePlugin.core.constants.Icon;
import org.zuma.examplePlugin.core.constants.SoundType;
import org.zuma.examplePlugin.core.logger.constants.LogLevel;
import org.zuma.examplePlugin.core.utils.TextUtils;
import org.zuma.examplePlugin.domain.item.ItemRecord;
import org.zuma.examplePlugin.domain.item.base.AbstractItemType;
import org.zuma.examplePlugin.domain.player.RunePlayer;

import java.util.HashMap;

public class PlayerItemDistributor {
    private final RunePlayer runePlayer;

    public PlayerItemDistributor(RunePlayer player) {
        this.runePlayer = player;
    }

    public void addItemOrDrop(ItemRecord item) {
        AbstractItemType customItem = RuneQuestPlugin.getItemRegistry().getType(item.getItemId());

        if (customItem == null) {
            RuneQuestPlugin.getRuneLogger().logWithStack(LogLevel.WARN, "Cannot add item with id " + item.getItemId() + " to players inventory. Item does not exist in registry.");
            return;
        }


        ItemStack itemStack = customItem.createItemStackOfType(item.getAmount());
        Component name = itemStack.getItemMeta().displayName();
        String plainName = name != null ? PlainTextComponentSerializer.plainText().serialize(name) : null;

        addItemStackOrDrop(itemStack, "Your inventory is full! " + itemStack.getAmount() + "x " + plainName + " dropped on the ground.");
    }

    public void addItemOrDrop(ItemStack item) {
        addItemStackOrDrop(item, "Your inventory is full! " + item.getAmount() + "x " + TextUtils.extractPlainText(item.displayName()) + " dropped on the ground.");
    }

    private void addItemStackOrDrop(ItemStack item, String dropMessage) {
        Player player = runePlayer.asBukkitPlayer();
        HashMap<Integer, ItemStack> leftovers = player.getInventory().addItem(item);


        if (!leftovers.isEmpty()) {
            Location dropLocation = player.getLocation();
            leftovers.values().forEach(leftover ->
                    player.getWorld().dropItemNaturally(dropLocation, leftover)
            );

            if (dropMessage != null && !dropMessage.isEmpty()) {
                player.sendMessage(Icon.INFO.asComponent().append(Component.text(dropMessage).color(Color.INFO.asColor())));
                SoundType.INVENTORY_FULL.play(player, true);
            }

            return;
        }

        player.playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP, SoundCategory.PLAYERS, 1f, 1f);
    }

}
