package org.zuma.examplePlugin.features.admin.commands;


import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.*;
import org.jetbrains.annotations.NotNull;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.constants.Color;
import org.zuma.examplePlugin.core.handlers.IllegalItemWatchdog;
import org.zuma.examplePlugin.core.utils.PlayerUtils;
import org.zuma.examplePlugin.core.utils.TypeUtils;
import org.zuma.examplePlugin.core.utils.XpUtils;
import org.zuma.examplePlugin.domain.block.BlockManager;
import org.zuma.examplePlugin.domain.block.persistence.InteractableBlockRepository;
import org.zuma.examplePlugin.domain.block.persistence.ResourceNodeTypeRepository;
import org.zuma.examplePlugin.domain.gui.constants.InventoryId;
import org.zuma.examplePlugin.domain.item.ItemRegistry;
import org.zuma.examplePlugin.domain.item.serializer.ItemSerializer;
import org.zuma.examplePlugin.domain.mob.constants.MobType;
import org.zuma.examplePlugin.domain.player.RunePlayer;
import org.zuma.examplePlugin.domain.recipes.RecipeRegistry;
import org.zuma.examplePlugin.domain.skill.Skill;
import org.zuma.examplePlugin.domain.skill.constants.SkillType;
import org.zuma.examplePlugin.domain.spawnzone.base.AbstractSpawnZone;
import org.zuma.examplePlugin.features.admin.inventories.ItemRegistryInventory;
import org.zuma.examplePlugin.features.admin.inventories.RecipesInventory;

import javax.annotation.Nullable;
import java.util.*;
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

        if (args[0].equalsIgnoreCase("serialize")) {
            System.out.println(ItemSerializer.serializeItemStackWithPDC(PlayerUtils.getItemStackInHand(player), RuneQuestPlugin.getInstance()));
        }

        if (args[0].equalsIgnoreCase("deserialize")) {
            runePlayer.giveItemOrDrop(ItemSerializer.deserializeItemStackWithPDC("{\"amount\":1,\"name\":\"Fairy of Light\",\"pdc\":{\"runequest-core:state_uuid\":\"19e421fe-b636-4857-a383-ff6531c6c4cd\",\"runequest-core:item_id\":2101}}", RuneQuestPlugin.getInstance()));
        }

        if (args.length == 2 && Objects.equals(args[0], "pouch") & TypeUtils.isInteger(args[1])) {
            runePlayer.setPouchCoins(Long.parseLong(args[1]));
        }

        if (Objects.equals(args[0], "zone")) {
            AbstractSpawnZone zone = RuneQuestPlugin.getSpawnZoneHandler().getClosest(player.getLocation());
            if (zone == null) {
                player.sendMessage("Closest zone not found.");
                return;
            }

            StringBuilder message = new StringBuilder();


            message.append("§6---- Spawn Zone Info ----\n");
            message.append("§eZone ID: §f").append(zone.getId()).append("\n");
            message.append("§eType: §f").append(zone.getType().name()).append("\n");

            if (zone.getLocation() != null) {
                message.append("§eLocation: §fWorld: ").append(zone.getLocation().getWorld().getName())
                        .append(", X: ").append(zone.getLocation().getBlockX())
                        .append(", Y: ").append(zone.getLocation().getBlockY())
                        .append(", Z: ").append(zone.getLocation().getBlockZ()).append("\n");
            } else {
                message.append("§eLocation: §fN/A\n");
            }

            message.append("§eWave Rate: §f").append(zone.getWaveRateSeconds()).append(" seconds\n");
            message.append("§eDead Threshold for Wave: §f").append(zone.getDeadThresholdForWave()).append("%\n");
            message.append("§eMax Mobs: §f").append(zone.getMaxMobs()).append("\n");
            message.append("§eCurrently Alive: §f").append(zone.getAliveMobCount()).append("\n");
            message.append("§eLast Spawned Wave: §f").append(zone.getLastWaveSpawnedAmount()).append(" mobs\n");
            message.append("§eDead % of Last Wave: §f").append(String.format("%.2f", zone.getDeadEntitiesPercentage())).append("%\n");

            player.sendMessage(message.toString());

        }

        if (Objects.equals(args[0], "mob1")) {
            RuneQuestPlugin.getMobRegistry().spawnMob(UUID.randomUUID(), player.getLocation(), MobType.GOBLIN_LOW_LVL_1.getId());
            RuneQuestPlugin.getMobRegistry().spawnMob(UUID.randomUUID(), player.getLocation(), MobType.GOBLIN_LOW_LVL_2.getId());
            RuneQuestPlugin.getMobRegistry().spawnMob(UUID.randomUUID(), player.getLocation(), MobType.GOBLIN_LOW_LVL_3.getId());
            RuneQuestPlugin.getMobRegistry().spawnMob(UUID.randomUUID(), player.getLocation(), MobType.GOBLIN_LOW_LVL_4.getId());
            RuneQuestPlugin.getMobRegistry().spawnMob(UUID.randomUUID(), player.getLocation(), MobType.GOBLIN_LOW_LVL_5.getId());

        }

        if (Objects.equals(args[0], "mob") && args.length == 2) {
            String input = args[1].toLowerCase();
            Location baseLocation = player.getLocation();

            if (Objects.equals(input, "all")) {
                UUID uuid;
                int offset = 0;

                for (MobType mobType : List.of(
                        MobType.RAT,
                        MobType.AXOLOTL,
                        MobType.ARMADILLO,
                        MobType.BEE,
                        MobType.CAT,
                        MobType.DONKEY,
                        MobType.FOX,
                        MobType.FROG,
                        MobType.GOAT,
                        MobType.HOGLIN,
                        MobType.HUSK,
                        MobType.LLAMA,
                        MobType.PANDA,
                        MobType.PIG,
                        MobType.POLAR_BEAR,
                        MobType.RABBIT,
                        MobType.RAVAGER,
                        MobType.SNIFFER,
                        MobType.SPIDER,
                        MobType.STRIDER,
                        MobType.TURTLE,
                        MobType.WITCH
                )) {
                    uuid = UUID.randomUUID();
                    Location spawnLocation = baseLocation.clone().add(offset % 5, 0, offset / 5);
                    RuneQuestPlugin.getMobRegistry().spawnMob(uuid, spawnLocation, mobType.getId());
                    offset++;
                }

                player.sendMessage("Spawned all custom mobs.");
            } else {
                boolean found = false;

                for (MobType mobType : MobType.values()) {
                    if (mobType.getId().equalsIgnoreCase(input)) {
                        UUID uuid = UUID.randomUUID();
                        RuneQuestPlugin.getMobRegistry().spawnMob(uuid, baseLocation, mobType.getId());
                        player.sendMessage("Spawned mob: " + mobType.getId());
                        found = true;
                        break;
                    }
                }

                if (!found) {
                    player.sendMessage("Unknown mob name: " + input);
                }
            }
        }


        if (Objects.equals(args[0], "chicken")) {
            RuneQuestPlugin.getMobRegistry().spawnMob(UUID.randomUUID(), player.getLocation(), MobType.CHICKEN.getId());
        }

        if (Objects.equals(args[0], "rat")) {
            RuneQuestPlugin.getMobRegistry().spawnMob(UUID.randomUUID(), player.getLocation(), MobType.RAT.getId());
        }


        if (Objects.equals(args[0], "sheep")) {
            RuneQuestPlugin.getMobRegistry().spawnMob(UUID.randomUUID(), player.getLocation(), MobType.SHEEP.getId());
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
            RuneQuestPlugin.getMobRegistry().spawnMob(UUID.randomUUID(), player.getLocation(), MobType.GENERAL_STORE.getId());
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


