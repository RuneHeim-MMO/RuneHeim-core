package org.zuma.examplePlugin.features.admin.commands;


import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.text.Component;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.constants.Color;
import org.zuma.examplePlugin.core.handlers.IllegalItemWatchdog;
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
import org.zuma.examplePlugin.domain.skill.Skill;
import org.zuma.examplePlugin.domain.skill.constants.SkillType;
import org.zuma.examplePlugin.features.admin.inventories.ItemRegistryInventory;
import org.zuma.examplePlugin.features.admin.inventories.RecipesInventory;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Function;
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

        if (!player.isOp()) {
            sender.sendMessage(Component.text("Invalid command.").color(Color.ERROR.asColor()));
            return;
        }

        RunePlayer runePlayer = RuneQuestPlugin.getPlayerManager().getRunePlayer(player);

        if (runePlayer == null) {
            sender.sendMessage(Component.text("Could not find RunePlayer instance!").color(Color.ERROR.asColor()));
            return;
        }

        if (Objects.equals(args[0], "skills")) {
            runePlayer.asBukkitPlayer().sendMessage(
                    runePlayer.getSkills().values().stream()
                            .map(skill -> skill.getType().getLabel() + ": " + skill.getBaseLevel() + " BONUS: +" + skill.getBonus())
                            .collect(Collectors.joining(", "))
            );
        }

        if (args.length == 2 && Objects.equals(args[0], "base")) {
            int baseLevel;

            if (TypeUtils.isInteger(args[1])) {
                baseLevel = Integer.parseInt(args[1]);
            } else {
                baseLevel = 1;
            }

            EnumMap<SkillType, Skill> skills = Arrays.stream(SkillType.values())
                    .collect(Collectors.toMap(
                            Function.identity(),
                            type -> new Skill(type, XpUtils.getXpForLevel(baseLevel)),
                            (a, b) -> b,
                            () -> new EnumMap<>(SkillType.class)
                    ));

            runePlayer.setSkills(skills);
            runePlayer.asBukkitPlayer().sendMessage("Set all skill levels to " + baseLevel);

        }

        if (Objects.equals(args[0], "mob1")) {
            RuneQuestPlugin.getMobRegistry().spawnMob(UUID.randomUUID(), player.getLocation(), MobType.GOBLIN_LOW_LVL_1.getId());
        }
        if (Objects.equals(args[0], "mob2")) {
            RuneQuestPlugin.getMobRegistry().spawnMob(UUID.randomUUID(), player.getLocation(), MobType.GOBLIN_LOW_LVL_2.getId());
        }

        if (Objects.equals(args[0], "fusion_master")) {
            RuneQuestPlugin.getMobRegistry().spawnMob(UUID.randomUUID(), player.getLocation(), MobType.FUSION_MASTER.getId());
        }


        if (Objects.equals(args[0], "node")) {
            RuneQuestPlugin.getGUIHandler().openInventory(InventoryId.ADMIN_NODE_CREATE.asInt(), runePlayer, null);
        }

        if (Objects.equals(args[0], "opener")) {
            RuneQuestPlugin.getGUIHandler().openInventory(InventoryId.ADMIN_INTERACTABLE_BLOCK_CREATE.asInt(), runePlayer, null);
        }

        if (Objects.equals(args[0], "shop")) {
            RuneQuestPlugin.getMobRegistry().spawnMob(UUID.randomUUID(), player.getLocation(), MobType.GENERIC_SHOPKEEPER.getId());
        }

        if (args.length == 1 && Objects.equals(args[0], "illegal")) {
            IllegalItemWatchdog.tagIllegalItem(player.getInventory().getItemInMainHand());
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

        // without page
        if (args.length == 1 && Objects.equals(args[0], "recipes")) {
            RecipesInventory.open(recipeRegistry, itemRegistry, runePlayer, 0);
        }

        // without page
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


