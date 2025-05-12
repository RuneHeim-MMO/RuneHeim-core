package org.zuma.examplePlugin.commands;


import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;
import org.zuma.examplePlugin.constants.Colors;
import org.zuma.examplePlugin.item.ItemRegistry;
import org.zuma.examplePlugin.item.base.CustomItem;
import org.zuma.examplePlugin.utils.TypeUtils;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;

@SuppressWarnings("UnstableApiUsage")
public class AdminCommand implements BasicCommand {
    private final ItemRegistry registry;

    public AdminCommand(ItemRegistry registry) {
        this.registry = registry;
    }

    @Override
    public void execute(CommandSourceStack commandSourceStack, String @NotNull [] args) {
        CommandSender sender = commandSourceStack.getSender();

        if (!(sender instanceof Player player)) {
            sender.sendMessage("This command can be only run by players.");
            return;
        }

        // Items without page
        if (args.length == 1 && Objects.equals(args[0], "items")) {
            openItemRegistryInventory(player, 0);
        }

        // Items with page
        if (args.length == 2 && Objects.equals(args[0], "items")) {
            int page = 0;

            if (TypeUtils.isInteger(args[1])) {
                page = Integer.parseInt(args[1]);
            }

            openItemRegistryInventory(player, page);
        }

    }

    public void openItemRegistryInventory(Player player, int page) {
        int INVENTORY_ROWS = 6; // Max 6!
        int ITEMS_IN_ROW = 9; // Always 9

        if (!player.hasPermission("rune.admin.item_registry")) {
            player.sendMessage(Component.text("Missing rune.admin.item_registry permission.").color(Colors.ERROR.asColor()));
            return;
        }

        int slots = INVENTORY_ROWS * ITEMS_IN_ROW;
        int totalItems = registry.getAllItems().size();
        int indexStart = page * slots;
        int indexEnd = Math.min(indexStart + slots, totalItems);
        int maxPages = ((totalItems + slots - 1) / slots) - 1;

        if (indexStart >= totalItems) {
            player.sendMessage(Component.text("No items to display on page " + page + ". Total pages: 0-" + maxPages).color(Colors.ERROR.asColor()));
            return;
        }

        List<CustomItem> paginatedRegistryItems = registry.getAllItems().subList(indexStart, indexEnd);

        Inventory inventory = Bukkit.createInventory(null, slots, Component.text("Item Registry Page: " + page));

        for (int i = 0; i < paginatedRegistryItems.size(); i++) {
            inventory.setItem(i, paginatedRegistryItems.get(i).asItemStack());
        }

        player.openInventory(inventory);


    }

    @Override
    public @Nullable String permission() {
        return "rune.admin";
    }

}


