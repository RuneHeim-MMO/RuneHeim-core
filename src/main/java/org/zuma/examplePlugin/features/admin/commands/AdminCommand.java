package org.zuma.examplePlugin.features.admin.commands;


import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.util.Transformation;
import org.jetbrains.annotations.NotNull;
import org.joml.AxisAngle4f;
import org.joml.Vector3f;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.constants.Color;
import org.zuma.examplePlugin.core.handlers.game.IllegalItemWatchdog;
import org.zuma.examplePlugin.core.redis.GlobalEventHandler;
import org.zuma.examplePlugin.core.utils.MathUtils;
import org.zuma.examplePlugin.core.utils.TypeUtils;
import org.zuma.examplePlugin.core.utils.XpUtils;
import org.zuma.examplePlugin.domain.block.BlockManager;
import org.zuma.examplePlugin.domain.block.persistence.InteractableBlockRepository;
import org.zuma.examplePlugin.domain.block.persistence.ResourceNodeTypeRepository;
import org.zuma.examplePlugin.domain.combat.HitHandler;
import org.zuma.examplePlugin.domain.gui.constants.InventoryId;
import org.zuma.examplePlugin.domain.item.ItemRegistry;
import org.zuma.examplePlugin.domain.item.constants.ItemId;
import org.zuma.examplePlugin.domain.mob.constants.MobType;
import org.zuma.examplePlugin.domain.player.RunePlayer;
import org.zuma.examplePlugin.domain.recipe.RecipeRegistry;
import org.zuma.examplePlugin.domain.skill.Skill;
import org.zuma.examplePlugin.domain.skill.constants.SkillType;
import org.zuma.examplePlugin.domain.spawnzone.base.AbstractSpawnZone;
import org.zuma.examplePlugin.events.core.NetworkRestartEvent;
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
    private final HitHandler hitHandler;

    public AdminCommand(HitHandler hitHandler, InteractableBlockRepository blockRepo, ItemRegistry itemRegistry, RecipeRegistry recipeRegistry, BlockManager blockManager, ResourceNodeTypeRepository oreTypeRepository) {
        this.hitHandler = hitHandler;
        this.itemRegistry = itemRegistry;
        this.recipeRegistry = recipeRegistry;
        this.blockManager = blockManager;
        this.oreTypeRepository = oreTypeRepository;
        this.blockRepo = blockRepo;
    }

    @Override
    public void execute(CommandSourceStack commandSourceStack, String @NotNull [] args) {
        CommandSender sender = commandSourceStack.getSender();

        if (args.length >= 3 && Objects.equals(args[0], "restart")) {
            String reason = String.join(" ", Arrays.copyOfRange(args, 2, args.length));
            long restartIn = Long.parseLong(args[1]) * 1000L;

            String senderName = "Console";

            if (sender instanceof Player player) {
                senderName = player.getName();
            }

            NetworkRestartEvent.Payload payload = new NetworkRestartEvent.Payload(System.currentTimeMillis() + restartIn, reason, senderName);
            Bukkit.getServer().getPluginManager().callEvent(new NetworkRestartEvent(payload));
            return;
        }

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

        if (args[0].equalsIgnoreCase("spawn")) {
            Location displayLocation = MathUtils.roundLocation(player.getLocation());
            ItemDisplay display = player.getWorld().spawn(displayLocation, ItemDisplay.class);
            display.setItemDisplayTransform(ItemDisplay.ItemDisplayTransform.GROUND);
            display.setItemStack(RuneQuestPlugin.getItemRegistry().getType(ItemId.BRONZE_SWORD).createItemStackOfType());
            display.setPersistent(false);

            display.setTransformation(
                    new Transformation(
                            new Vector3f(), // no translation
                            new AxisAngle4f(), // no left rotation
                            new Vector3f(5, 5, 5), // scale up by a factor of 2 on all axes
                            new AxisAngle4f() // no right rotation
                    )
            );
        }


        if (args.length == 2 && Objects.equals(args[0], "pouch") & TypeUtils.isInteger(args[1])) {
            runePlayer.setPouchCoins(Long.parseLong(args[1]), false);
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

            if (zone.getCenterPoint() != null) {
                message.append("§eLocation: §fWorld: ").append(zone.getCenterPoint().getWorld().getName())
                        .append(", X: ").append(zone.getCenterPoint().getBlockX())
                        .append(", Y: ").append(zone.getCenterPoint().getBlockY())
                        .append(", Z: ").append(zone.getCenterPoint().getBlockZ()).append("\n");
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

        if (Objects.equals(args[0], "map")) {
            RuneQuestPlugin.getGUIHandler().openInventory(InventoryId.TERRITORY_INVENTORY.asInt(), runePlayer, null);
        }

        if (Objects.equals(args[0], "combat-debug")) {
            hitHandler.toggleCombatDebug(runePlayer);
        }

        if (args.length >= 2 && Objects.equals(args[0], "restart")) {
            String reason = String.join(" ", Arrays.copyOfRange(args, 1, args.length));
            long restartIn = 60L * 1000L;

            NetworkRestartEvent.Payload payload = new NetworkRestartEvent.Payload(System.currentTimeMillis() + restartIn, reason, player.getName());
            Bukkit.getServer().getPluginManager().callEvent(new NetworkRestartEvent(payload));
        }

        if (Objects.equals(args[0], "pdc")) {
            ItemStack item = player.getInventory().getItemInMainHand();
            PersistentDataContainer pdc = item.getItemMeta().getPersistentDataContainer();

            for (NamespacedKey key : pdc.getKeys()) {
                Object value = null;

                // Try each common type
                if (pdc.has(key, PersistentDataType.STRING)) {
                    value = pdc.get(key, PersistentDataType.STRING);
                } else if (pdc.has(key, PersistentDataType.INTEGER)) {
                    value = pdc.get(key, PersistentDataType.INTEGER);
                } else if (pdc.has(key, PersistentDataType.LONG)) {
                    value = pdc.get(key, PersistentDataType.LONG);
                } else if (pdc.has(key, PersistentDataType.DOUBLE)) {
                    value = pdc.get(key, PersistentDataType.DOUBLE);
                } else if (pdc.has(key, PersistentDataType.FLOAT)) {
                    value = pdc.get(key, PersistentDataType.FLOAT);
                } else if (pdc.has(key, PersistentDataType.BYTE)) {
                    value = pdc.get(key, PersistentDataType.BYTE);
                } else if (pdc.has(key, PersistentDataType.BYTE_ARRAY)) {
                    value = Arrays.toString(pdc.get(key, PersistentDataType.BYTE_ARRAY));
                } else if (pdc.has(key, PersistentDataType.INTEGER_ARRAY)) {
                    value = Arrays.toString(pdc.get(key, PersistentDataType.INTEGER_ARRAY));
                } else if (pdc.has(key, PersistentDataType.TAG_CONTAINER)) {
                    value = pdc.get(key, PersistentDataType.TAG_CONTAINER);
                } else if (pdc.has(key, PersistentDataType.TAG_CONTAINER_ARRAY)) {
                    value = Arrays.toString(pdc.get(key, PersistentDataType.TAG_CONTAINER_ARRAY));
                }

                sender.sendMessage(key.toString() + " = " + (value != null ? value : "<unknown type>"));
            }

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
                        MobType.HOGLIN_BASE_5,
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
            RuneQuestPlugin.getMobRegistry().spawnMob(UUID.randomUUID(), player.getLocation(), MobType.CHICKEN_BASE_2.getId());
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


