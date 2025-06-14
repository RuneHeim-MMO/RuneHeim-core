package org.zuma.examplePlugin.features.admin.commands;


import com.google.gson.Gson;
import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.text.Component;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.RayTraceResult;
import org.jetbrains.annotations.NotNull;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.constants.Colors;
import org.zuma.examplePlugin.core.meta.VersionedJson;
import org.zuma.examplePlugin.core.utils.TypeUtils;
import org.zuma.examplePlugin.core.utils.XpUtils;
import org.zuma.examplePlugin.domain.block.BlockManager;
import org.zuma.examplePlugin.domain.block.constants.BlockType;
import org.zuma.examplePlugin.domain.block.constants.ResourceNodeTypeId;
import org.zuma.examplePlugin.domain.block.meta.AbstractInteractableBlockMeta;
import org.zuma.examplePlugin.domain.block.meta.HologramSide;
import org.zuma.examplePlugin.domain.block.meta.v1.ResourceNodeMeta;
import org.zuma.examplePlugin.domain.block.persistence.BlockEntity;
import org.zuma.examplePlugin.domain.block.persistence.InteractableBlockRepository;
import org.zuma.examplePlugin.domain.block.persistence.ResourceNodeTypeRepository;
import org.zuma.examplePlugin.domain.item.ItemRegistry;
import org.zuma.examplePlugin.domain.mob.base.AbstractMob;
import org.zuma.examplePlugin.domain.player.RunePlayer;
import org.zuma.examplePlugin.domain.recipes.RecipeRegistry;
import org.zuma.examplePlugin.features.admin.inventories.ItemRegistryInventory;
import org.zuma.examplePlugin.features.admin.inventories.RecipesInventory;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;
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
            sender.sendMessage(Component.text("This command can be only used by players").color(Colors.ERROR.asColor()));
            return;
        }

        RunePlayer runePlayer = RuneQuestPlugin.getPlayerManager().getRunePlayer(player);

        if (runePlayer == null) {
            sender.sendMessage(Component.text("Could not find RunePlayer instance!").color(Colors.ERROR.asColor()));
            return;
        }

        if (Objects.equals(args[0], "skills")) {
            runePlayer.getPlayer().sendMessage(
                    runePlayer.getSkills().stream()
                            .map(skill -> skill.getType().getLabel() + ": " + XpUtils.getLevelFromXp(skill.getXp()))
                            .collect(Collectors.joining(", "))
            );
        }

        if (Objects.equals(args[0], "mob1")) {
            AbstractMob mob = RuneQuestPlugin.getMobRegistry().getById(1);

            mob.spawn(player.getLocation());
        }

        if (Objects.equals(args[0], "tree")) {
            RayTraceResult result = player.rayTraceBlocks(20);

            if (result == null || result.getHitBlock() == null) {
                return;
            }


            BlockEntity entity = new BlockEntity(new AbstractInteractableBlockMeta(0, BlockType.ResourceNode, result.getHitBlock().getLocation(), HologramSide.TOP),
                    new VersionedJson(new Gson().toJson(new ResourceNodeMeta(ResourceNodeTypeId.TREE.asInt(), "tree", "tree_depleted")), 1));


            blockRepo.insertBlocks(List.of(entity));
            player.sendMessage("OK!");
        }

        if (Objects.equals(args[0], "birch")) {
            RayTraceResult result = player.rayTraceBlocks(20);

            if (result == null || result.getHitBlock() == null) {
                return;
            }


            BlockEntity entity = new BlockEntity(new AbstractInteractableBlockMeta(0, BlockType.ResourceNode, result.getHitBlock().getLocation(), HologramSide.TOP),
                    new VersionedJson(new Gson().toJson(new ResourceNodeMeta(ResourceNodeTypeId.BIRCH_TREE.asInt(), "birch_tree", "birch_tree_depleted")), 1));


            blockRepo.insertBlocks(List.of(entity));
            player.sendMessage("OK!");
        }

        if (Objects.equals(args[0], "maple")) {
            RayTraceResult result = player.rayTraceBlocks(20);

            if (result == null || result.getHitBlock() == null) {
                return;
            }


            BlockEntity entity = new BlockEntity(new AbstractInteractableBlockMeta(0, BlockType.ResourceNode, result.getHitBlock().getLocation(), HologramSide.TOP),
                    new VersionedJson(new Gson().toJson(new ResourceNodeMeta(ResourceNodeTypeId.MAPLE_TREE.asInt(), "maple_tree", "maple_tree_depleted")), 1));


            blockRepo.insertBlocks(List.of(entity));
            player.sendMessage("OK!");
        }


        if (Objects.equals(args[0], "yew")) {
            RayTraceResult result = player.rayTraceBlocks(20);

            if (result == null || result.getHitBlock() == null) {
                return;
            }


            BlockEntity entity = new BlockEntity(new AbstractInteractableBlockMeta(0, BlockType.ResourceNode, result.getHitBlock().getLocation(), HologramSide.TOP),
                    new VersionedJson(new Gson().toJson(new ResourceNodeMeta(ResourceNodeTypeId.YEW_TREE.asInt(), "yew_tree", "yew_tree_depleted")), 1));


            blockRepo.insertBlocks(List.of(entity));
            player.sendMessage("OK!");
        }

        if (Objects.equals(args[0], "mahogany")) {
            RayTraceResult result = player.rayTraceBlocks(20);

            if (result == null || result.getHitBlock() == null) {
                return;
            }


            BlockEntity entity = new BlockEntity(new AbstractInteractableBlockMeta(0, BlockType.ResourceNode, result.getHitBlock().getLocation(), HologramSide.TOP),
                    new VersionedJson(new Gson().toJson(new ResourceNodeMeta(ResourceNodeTypeId.MAHOGANY_TREE.asInt(), "mahogany_tree", "mahogany_tree_depleted")), 1));


            blockRepo.insertBlocks(List.of(entity));
            player.sendMessage("OK!");
        }







       /* if (Objects.equals(args[0], "ore")) {
            RayTraceResult result = player.rayTraceBlocks(20);

            if (result == null) {
                return;
            }

            Block hitBlock = result.getHitBlock();
            BlockFace hitFace = result.getHitBlockFace();

            if (hitFace == null || hitBlock == null) {
                return;
            }

            ResourceNodeType type = oreTypeRepository.getAll().getFirst();
            blockManager.spawnResourceNode(type,
                    hitBlock.getLocation(), hitBlock.getLocation().add(hitFace.getDirection()).add(0.5, 1.25, 0.5));
        }*/


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


