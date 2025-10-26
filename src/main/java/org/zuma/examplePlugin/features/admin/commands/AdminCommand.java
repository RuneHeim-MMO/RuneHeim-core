package org.zuma.examplePlugin.features.admin.commands;


import com.google.gson.Gson;
import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.constants.Color;
import org.zuma.examplePlugin.core.handlers.game.IllegalItemWatchdog;
import org.zuma.examplePlugin.core.permissions.Permission;
import org.zuma.examplePlugin.core.utils.TypeUtils;
import org.zuma.examplePlugin.core.utils.XpUtils;
import org.zuma.examplePlugin.domain.block.BlockManager;
import org.zuma.examplePlugin.domain.block.persistence.InteractableBlockRepository;
import org.zuma.examplePlugin.domain.block.persistence.ResourceNodeTypeRepository;
import org.zuma.examplePlugin.domain.combat.HitHandler;
import org.zuma.examplePlugin.domain.gui.constants.InventoryId;
import org.zuma.examplePlugin.domain.item.ItemRegistry;
import org.zuma.examplePlugin.domain.item.base.AbstractItemType;
import org.zuma.examplePlugin.domain.item.impl.Mount;
import org.zuma.examplePlugin.domain.item.impl.fairy.Fairy;
import org.zuma.examplePlugin.domain.item.state.states.FairyState;
import org.zuma.examplePlugin.domain.item.state.states.MountState;
import org.zuma.examplePlugin.domain.mob.constants.MobType;
import org.zuma.examplePlugin.domain.player.RunePlayer;
import org.zuma.examplePlugin.domain.player.inventory.AdditionalGearSlotHandler;
import org.zuma.examplePlugin.domain.player.inventory.ReservedSlot;
import org.zuma.examplePlugin.domain.quest.Quest;
import org.zuma.examplePlugin.domain.quest.QuestSerializer;
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

        RunePlayer runePlayer = RuneQuestPlugin.getPlayerManager().getRunePlayer(player);

        if (runePlayer == null || !runePlayer.getRole().hasPermission(Permission.DEBUG_COMMAND)) {
            sender.sendMessage(Component.text("Invalid command.").color(Color.ERROR.asColor()));
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

        if (args[0].equalsIgnoreCase("perms")) {
            player.sendMessage(runePlayer.getRole().getFormattedAllPermissionComponent());
        }

        if (args[0].equalsIgnoreCase("level") && args[1].equalsIgnoreCase("mount")) {
            AdditionalGearSlotHandler slotHandler = RuneQuestPlugin.getModuleContext().get(AdditionalGearSlotHandler.class);
            ItemStack itemStack = slotHandler.getSlotItem(runePlayer, ReservedSlot.MOUNT_SLOT);
            AbstractItemType item = RuneQuestPlugin.getItemRegistry().getType(itemStack);

            if (!(item instanceof Mount mount) || itemStack == null) {
                return;
            }

            MountState state = mount.getState(itemStack);

            if (state == null) {
                return;
            }

            state.setLevel(mount, Integer.parseInt(args[2]));
            item.getMetaGenerator().updatePresentationLayer(itemStack);
        }

        if (args[0].equalsIgnoreCase("level") && args[1].equalsIgnoreCase("fairy")) {
            AdditionalGearSlotHandler slotHandler = RuneQuestPlugin.getModuleContext().get(AdditionalGearSlotHandler.class);
            ItemStack itemStack = slotHandler.getSlotItem(runePlayer, ReservedSlot.FAIRY_SLOT);
            AbstractItemType item = RuneQuestPlugin.getItemRegistry().getType(itemStack);

            if (!(item instanceof Fairy fairy) || itemStack == null) {
                return;
            }

            FairyState state = fairy.getState(itemStack);

            if (state == null) {
                return;
            }

            state.setMaxExp();
            item.getMetaGenerator().updatePresentationLayer(itemStack);
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

            message.append("--- Mob Records ---\n");
            zone.getMobRecords().forEach(r -> {
                message.append("§f").append(r.mobId().getId()).append("§e ").append(r.spawnChancePercentage()).append("%\n");

            });
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
            boolean found = false;

            for (MobType mobType : MobType.values()) {
                if (mobType.toString().equalsIgnoreCase(input)) {
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


        if (Objects.equals(args[0], "rat")) {
            RuneQuestPlugin.getMobRegistry().spawnMob(UUID.randomUUID(), player.getLocation(), MobType.RAT.getId());
        }


        if (Objects.equals(args[0], "sheep")) {
            RuneQuestPlugin.getMobRegistry().spawnMob(UUID.randomUUID(), player.getLocation(), MobType.SHEEP.getId());
        }

        if (Objects.equals(args[0], "fusion_master")) {
            RuneQuestPlugin.getMobRegistry().spawnMob(UUID.randomUUID(), player.getLocation(), MobType.FUSION_MASTER.getId());
        }


        if (Objects.equals(args[0], "mobs")) {

        }

        if (Objects.equals(args[0], "rain")) {
            int totalArrows = 1500;      // Total arrows in the rain
            int arrowsPerTick = 10;     // Number of arrows to spawn per tick
            long delayBetweenTicks = 2L; // 2 ticks (~0.1s) between spawns
            double spread = 50.0;        // Maximum horizontal spread in blocks

            new BukkitRunnable() {
                int spawned = 0;

                @Override
                public void run() {
                    if (spawned >= totalArrows) {
                        cancel(); // stop after all arrows are spawned
                        return;
                    }

                    Location baseLoc = player.getLocation().add(0, 15, 0); // 15 blocks above player
                    World world = player.getWorld();

                    // Spawn multiple arrows per tick
                    for (int i = 0; i < arrowsPerTick && spawned < totalArrows; i++) {
                        // Horizontal spread using the spread variable
                        double spreadX = (Math.random() - 0.5) * 2 * spread; // -spread to +spread
                        double spreadZ = (Math.random() - 0.5) * 2 * spread;

                        Location arrowLoc = baseLoc.clone().add(spreadX, 0, spreadZ);

                        // Slightly varied velocity
                        double velocityX = (Math.random() - 0.5) * 0.5;
                        double velocityY = -1.0;
                        double velocityZ = (Math.random() - 0.5) * 0.5;

                        org.bukkit.util.Vector velocity = new org.bukkit.util.Vector(velocityX, velocityY, velocityZ);

                        world.spawnArrow(arrowLoc, velocity, 1.0f, 0.0f); // speed 1, no inbuilt spread
                        spawned++;
                    }
                }
            }.runTaskTimer(RuneQuestPlugin.getInstance(), 0L, delayBetweenTicks);
        }


        if (Objects.equals(args[0], "node")) {
            RuneQuestPlugin.getGUIHandler().openInventory(InventoryId.ADMIN_NODE_CREATE.asInt(), runePlayer, null);
        }

        if (Objects.equals(args[0], "opener")) {
            RuneQuestPlugin.getGUIHandler().openInventory(InventoryId.ADMIN_INTERACTABLE_BLOCK_CREATE.asInt(), runePlayer, null);
        }

        if (Objects.equals(args[0], "model_opener")) {
            RuneQuestPlugin.getGUIHandler().openInventory(InventoryId.ADMIN_MODELLED_INTERACTABLE_BLOCK_CREATE.asInt(), runePlayer, null);
        }

        if (Objects.equals(args[0], "shop")) {
            RuneQuestPlugin.getMobRegistry().spawnMob(UUID.randomUUID(), player.getLocation(), MobType.DRAKKAR_GENERAL_STORE.getId());
        }

        if (Objects.equals(args[0], "quest")) {
            Quest q = RuneQuestPlugin.getQuestHandler().getRegistry().getQuestById("sheep-shearer");
            System.out.println(new Gson().toJson(q));
        }

        if (Objects.equals(args[0], "des")) {
            Quest q = QuestSerializer.deserializeQuest("{\"id\":\"sheep-shearer\",\"name\":\"Sheep Shearer\",\"steps\":[{\"requiredItems\":[{\"itemId\":101,\"amount\":5}],\"id\":0,\"type\":\"COLLECTION\"},{\"requiredItems\":[{\"itemId\":100,\"amount\":5}],\"id\":1,\"type\":\"COLLECTION\"},{\"requiredItems\":[{\"itemId\":1600,\"amount\":5}],\"id\":2,\"type\":\"COLLECTION\"}],\"difficulty\":\"HARD\",\"requirements\":[{\"type\":\"PRAYER\",\"minimumLevel\":5}],\"requiredCompletedQuests\":[\"invalid-quest\"]}");
            q.startForPlayer(runePlayer);
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


    @Override
    public Collection<String> suggest(CommandSourceStack commandSourceStack, String[] args) {
        List<String> suggestions = new ArrayList<>();

        if (args.length == 1) {
            suggestions.addAll(List.of(
                    "mob", "skills", "base", "spawn", "pouch",
                    "zone", "map", "combat-debug", "pdc", "rat",
                    "sheep", "fusion_master", "node", "opener",
                    "shop", "illegal", "items", "recipes", "restart"
            ));
        } else if (args.length == 2) {
            if ("mob".equalsIgnoreCase(args[0])) {
                for (MobType mobType : MobType.values()) {
                    suggestions.add(mobType.toString().toLowerCase());
                }
            } else if ("items".equalsIgnoreCase(args[0]) || "recipes".equalsIgnoreCase(args[0])) {
                suggestions.addAll(List.of("0", "1", "2", "3", "4", "5")); // page numbers
            } else if ("base".equalsIgnoreCase(args[0]) || "pouch".equalsIgnoreCase(args[0])) {
                suggestions.addAll(List.of("1", "10", "25", "50", "100")); // common values
            } else if ("restart".equalsIgnoreCase(args[0])) {
                suggestions.add("60"); // default seconds
            }
        }

        return suggestions.stream()
                .filter(s -> s.toLowerCase().startsWith(args[args.length - 1].toLowerCase()))
                .collect(Collectors.toList());
    }
}


