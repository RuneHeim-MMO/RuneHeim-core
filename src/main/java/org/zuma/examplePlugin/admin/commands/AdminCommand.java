package org.zuma.examplePlugin.admin.commands;


import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.zuma.examplePlugin.admin.inventories.ItemRegistryInventory;
import org.zuma.examplePlugin.item.ItemRegistry;
import org.zuma.examplePlugin.utils.TypeUtils;

import javax.annotation.Nullable;
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
            ItemRegistryInventory.open(registry, player, 0);
        }

        // Items with page
        if (args.length == 2 && Objects.equals(args[0], "items")) {
            int page = 0;

            if (TypeUtils.isInteger(args[1])) {
                page = Integer.parseInt(args[1]);
            }

            ItemRegistryInventory.open(registry, player, page);
        }

    }


    @Override
    public @Nullable String permission() {
        return "rune.admin";
    }

}


