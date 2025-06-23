package org.zuma.examplePlugin.features.admin.commands;


import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.text.Component;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.constants.Color;
import org.zuma.examplePlugin.core.utils.TypeUtils;
import org.zuma.examplePlugin.core.utils.XpUtils;
import org.zuma.examplePlugin.domain.block.BlockManager;
import org.zuma.examplePlugin.domain.block.persistence.InteractableBlockRepository;
import org.zuma.examplePlugin.domain.block.persistence.ResourceNodeTypeRepository;
import org.zuma.examplePlugin.domain.gui.constants.InventoryId;
import org.zuma.examplePlugin.domain.item.ItemRegistry;
import org.zuma.examplePlugin.domain.mob.constants.MobType;
import org.zuma.examplePlugin.domain.player.RunePlayer;
import org.zuma.examplePlugin.domain.recipes.RecipeRegistry;
import org.zuma.examplePlugin.features.admin.inventories.ItemRegistryInventory;
import org.zuma.examplePlugin.features.admin.inventories.RecipesInventory;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@SuppressWarnings("UnstableApiUsage")
public class AdminCommand implements BasicCommand {
    private final ItemRegistry itemRegistry;
    private final RecipeRegistry recipeRegistry;
    private final BlockManager blockManager;
    private final ResourceNodeTypeRepository oreTypeRepository;
    private final InteractableBlockRepository blockRepo;

    public AdminCommand(InteractableBlockRepository blockRepo, ItemRegistry itemRegistry, RecipeRegistry recipeRegistry, BlockManager blockManager, ResourceNodeTypeRepository oreTypeRepository) {
        this.itemRegistry = itemRegistry;
        this.recipeRegistry = recipeRegistry;
        this.blockManager = blockManager;
        this.oreTypeRepository = oreTypeRepository;
        this.blockRepo = blockRepo;
    }

    @Override
    public void execute(CommandSourceStack commandSourceStack, String @NotNull [] args) {
        CommandSender sender = commandSourceStack.getSender();

        if (!(sender instanceof Player player)) {
            sender.sendMessage(Component.text("This command can be only used by players").color(Color.ERROR.asColor()));
            return;
        }

        RunePlayer runePlayer = RuneQuestPlugin.getPlayerManager().getRunePlayer(player);

        if (runePlayer == null) {
            sender.sendMessage(Component.text("Could not find RunePlayer instance!").color(Color.ERROR.asColor()));
            return;
        }

        if (Objects.equals(args[0], "skills")) {
            runePlayer.getPlayer().sendMessage(
                    runePlayer.getSkills().values().stream()
                            .map(skill -> skill.getType().getLabel() + ": " + XpUtils.getLevelFromXp(skill.getXp()))
                            .collect(Collectors.joining(", "))
            );
        }

        if (Objects.equals(args[0], "mob1")) {
            for (int i = 0; i < 1; i++) {
                RuneQuestPlugin.getMobRegistry().spawnMob(UUID.randomUUID(), player.getLocation(), MobType.AGGRESSIVE_BANDIT);
            }

        }

        if (Objects.equals(args[0], "node")) {
            RuneQuestPlugin.getGUIHandler().openInventory(InventoryId.ADMIN_NODE_CREATE.asInt(), runePlayer);
        }

        if (Objects.equals(args[0], "opener")) {
            RuneQuestPlugin.getGUIHandler().openInventory(InventoryId.ADMIN_INTERACTABLE_BLOCK_CREATE.asInt(), runePlayer);
        }

        // Items without page
        if (args.length == 1 && Objects.equals(args[0], "items")) {
            ItemRegistryInventory.open(itemRegistry, player, 0);
        }

        // Items with page
        if (args.length == 2 && Objects.equals(args[0], "items")) {
            int page = 0;

            if (TypeUtils.isInteger(args[1])) {
                page = Integer.parseInt(args[1]);
            }

            ItemRegistryInventory.open(itemRegistry, player, page);
        }

        // Items without page
        if (args.length == 1 && Objects.equals(args[0], "recipes")) {
            RecipesInventory.open(recipeRegistry, itemRegistry, runePlayer, 0);
        }

        // Items with page
        if (args.length == 2 && Objects.equals(args[0], "recipes")) {
            int page = 0;

            if (TypeUtils.isInteger(args[1])) {
                page = Integer.parseInt(args[1]);
            }

            RecipesInventory.open(recipeRegistry, itemRegistry, runePlayer, page);
        }

    }


    @Override
    public @Nullable String permission() {
        return "rune.admin";
    }

}


