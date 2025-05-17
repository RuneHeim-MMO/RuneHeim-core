package org.zuma.examplePlugin.admin.commands;


import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.text.Component;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.RayTraceResult;
import org.jetbrains.annotations.NotNull;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.admin.inventories.ItemRegistryInventory;
import org.zuma.examplePlugin.block.BlockManager;
import org.zuma.examplePlugin.block.constants.OreType;
import org.zuma.examplePlugin.constants.Colors;
import org.zuma.examplePlugin.item.ItemRegistry;
import org.zuma.examplePlugin.player.RunePlayer;
import org.zuma.examplePlugin.utils.TypeUtils;
import org.zuma.examplePlugin.utils.XpUtils;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.stream.Collectors;

@SuppressWarnings("UnstableApiUsage")
public class AdminCommand implements BasicCommand {
    private final ItemRegistry registry;
    private final BlockManager blockManager;

    public AdminCommand(ItemRegistry registry, BlockManager blockManager) {
        this.registry = registry;
        this.blockManager = blockManager;
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
                            .map(skill -> skill.getType() + ": " + XpUtils.getLevelFromXp(skill.getXp()))
                            .collect(Collectors.joining(", "))
            );
        }

        if (Objects.equals(args[0], "ore")) {
            RayTraceResult result = player.rayTraceBlocks(20);

            if (result == null) {
                return;
            }

            Block hitBlock = result.getHitBlock();
            BlockFace hitFace = result.getHitBlockFace();

            if (hitFace == null || hitBlock == null) {
                return;
            }


            blockManager.spawnOreBlock(OreType.IRON_ORE, hitBlock.getLocation(), hitBlock.getLocation().add(hitFace.getDirection()).add(0.5, 1.25, 0.5));
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


