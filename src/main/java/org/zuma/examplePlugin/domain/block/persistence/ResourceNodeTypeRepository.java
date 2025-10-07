package org.zuma.examplePlugin.domain.block.persistence;

import org.bukkit.Material;
import org.zuma.examplePlugin.core.constants.SoundType;
import org.zuma.examplePlugin.domain.block.constants.ResourceNodeTypeId;
import org.zuma.examplePlugin.domain.block.meta.ResourceNodeType;
import org.zuma.examplePlugin.domain.droptable.DropTable;
import org.zuma.examplePlugin.domain.droptable.DropTableId;
import org.zuma.examplePlugin.domain.item.ItemRecord;
import org.zuma.examplePlugin.domain.item.constants.ItemId;
import org.zuma.examplePlugin.domain.map.MarkerType;
import org.zuma.examplePlugin.domain.modifier.constants.ModifierType;
import org.zuma.examplePlugin.domain.skill.SkillRequirement;
import org.zuma.examplePlugin.domain.skill.SkillXpRecord;
import org.zuma.examplePlugin.domain.skill.constants.SkillType;

import java.util.List;

public class ResourceNodeTypeRepository {

    public List<ResourceNodeType> getAll() {
        return List.of(

                new ResourceNodeType(ResourceNodeTypeId.FLAX.asInt(), MarkerType.FLAX,
                        "Flax", Material.BLUE_ORCHID, false, null, null, 500, 60,
                        List.of(DropTableId.FLAX_NODE.asInt()), ItemId.FLAX.asInt(),
                        "", "Picking",
                        SoundType.PICKING_UP_PLANT_START, null, SoundType.PICKING_UP_PLANT_END,
                        List.of()),

                // Trees
                new ResourceNodeType(ResourceNodeTypeId.TREE.asInt(), MarkerType.TREE,
                        "Tree", Material.OAK_LOG, true,
                        List.of(new SkillRequirement(SkillType.WOODCUTTING, 1)),
                        ModifierType.WOODCUTTING_BOOST, 10000, 30,
                        List.of(DropTableId.TREE_NODE.asInt(), DropTableId.WOODCUTTING_RARE_DROPTABLE.asInt()), ItemId.LOG.asInt(),
                        "You need a hatchet to cut that!", "Woodcutting",
                        SoundType.WOODCUTTING_START, SoundType.WOODCUTTING_STEP, SoundType.WOODCUTTING_SUCCESS,
                        List.of(new SkillXpRecord(SkillType.WOODCUTTING, 25.0))),

                new ResourceNodeType(ResourceNodeTypeId.BIRCH_TREE.asInt(), MarkerType.BIRCH,
                        "Birch Tree", Material.BIRCH_LOG, true,
                        List.of(new SkillRequirement(SkillType.WOODCUTTING, 15)),
                        ModifierType.WOODCUTTING_BOOST, 12000, 40,
                        List.of(DropTableId.BIRCH_TREE_NODE.asInt(), DropTableId.WOODCUTTING_RARE_DROPTABLE.asInt()), ItemId.BIRCH_LOG.asInt(),
                        "You need a hatchet to cut that!", "Woodcutting",
                        SoundType.WOODCUTTING_START, SoundType.WOODCUTTING_STEP, SoundType.WOODCUTTING_SUCCESS,
                        List.of(new SkillXpRecord(SkillType.WOODCUTTING, 37.5))),

                new ResourceNodeType(ResourceNodeTypeId.MAPLE_TREE.asInt(), MarkerType.MAPLE,
                        "Maple Tree", Material.ACACIA_LOG, true,
                        List.of(new SkillRequirement(SkillType.WOODCUTTING, 45)),
                        ModifierType.WOODCUTTING_BOOST, 15000, 50,
                        List.of(DropTableId.MAPLE_TREE_NODE.asInt(), DropTableId.WOODCUTTING_RARE_DROPTABLE.asInt()), ItemId.MAPLE_LOG.asInt(),
                        "You need a hatchet to cut that!", "Woodcutting",
                        SoundType.WOODCUTTING_START, SoundType.WOODCUTTING_STEP, SoundType.WOODCUTTING_SUCCESS,
                        List.of(new SkillXpRecord(SkillType.WOODCUTTING, 100.0))),

                new ResourceNodeType(ResourceNodeTypeId.MAHOGANY_TREE.asInt(), MarkerType.MAHOGANY,
                        "Mahogany Tree", Material.MANGROVE_LOG, true,
                        List.of(new SkillRequirement(SkillType.WOODCUTTING, 50)),
                        ModifierType.WOODCUTTING_BOOST, 16000, 55,
                        List.of(DropTableId.MAHOGANY_TREE_NODE.asInt(), DropTableId.WOODCUTTING_RARE_DROPTABLE.asInt()), ItemId.MAHOGANY_LOG.asInt(),
                        "You need a hatchet to cut that!", "Woodcutting",
                        SoundType.WOODCUTTING_START, SoundType.WOODCUTTING_STEP, SoundType.WOODCUTTING_SUCCESS,
                        List.of(new SkillXpRecord(SkillType.WOODCUTTING, 125.0))),

                new ResourceNodeType(ResourceNodeTypeId.YEW_TREE.asInt(), MarkerType.YEW,
                        "Yew Tree", Material.DARK_OAK_LOG, true,
                        List.of(new SkillRequirement(SkillType.WOODCUTTING, 60)),
                        ModifierType.WOODCUTTING_BOOST, 18000, 60,
                        List.of(DropTableId.YEW_TREE_NODE.asInt(), DropTableId.WOODCUTTING_RARE_DROPTABLE.asInt()), ItemId.YEW_LOG.asInt(),
                        "You need a hatchet to cut that!", "Woodcutting",
                        SoundType.WOODCUTTING_START, SoundType.WOODCUTTING_STEP, SoundType.WOODCUTTING_SUCCESS,
                        List.of(new SkillXpRecord(SkillType.WOODCUTTING, 175.0))),

                new ResourceNodeType(ResourceNodeTypeId.MAGIC_TREE.asInt(), MarkerType.MAGIC,
                        "Magic Tree", Material.CHERRY_LOG, true,
                        List.of(new SkillRequirement(SkillType.WOODCUTTING, 75)),
                        ModifierType.WOODCUTTING_BOOST, 20000, 70,
                        List.of(DropTableId.MAGIC_TREE_NODE.asInt(), DropTableId.WOODCUTTING_RARE_DROPTABLE.asInt()), ItemId.MAGIC_LOG.asInt(),
                        "You need a hatchet to cut that!", "Woodcutting",
                        SoundType.WOODCUTTING_START, SoundType.WOODCUTTING_STEP, SoundType.WOODCUTTING_SUCCESS,
                        List.of(new SkillXpRecord(SkillType.WOODCUTTING, 250.0))),


                // Ores
                new ResourceNodeType(ResourceNodeTypeId.TIN_ORE.asInt(), MarkerType.TIN,
                        "Tin Ore Vein", Material.DEEPSLATE_GOLD_ORE, true,
                        List.of(new SkillRequirement(SkillType.MINING, 1)),
                        ModifierType.MINING_BOOST, 7500, 8,  // 7.5 seconds (7500 ms), respawn 8s
                        List.of(DropTableId.TIN_NODE.asInt(), DropTableId.MINING_RARE_DROPTABLE.asInt()), ItemId.TIN_ORE.asInt(),
                        "You need a pickaxe to mine that!", "Mining",
                        SoundType.MINING_START, SoundType.MINING_STEP, SoundType.MINING_SUCCESS,
                        List.of(new SkillXpRecord(SkillType.MINING, 17.5))),

                new ResourceNodeType(ResourceNodeTypeId.COPPER_ORE.asInt(), MarkerType.COPPER,
                        "Copper Ore Vein", Material.DEEPSLATE_COPPER_ORE, true,
                        List.of(new SkillRequirement(SkillType.MINING, 1)),
                        ModifierType.MINING_BOOST, 7500, 8,
                        List.of(DropTableId.COPPER_NODE.asInt(), DropTableId.MINING_RARE_DROPTABLE.asInt()), ItemId.COPPER_ORE.asInt(),
                        "You need a pickaxe to mine that!", "Mining",
                        SoundType.MINING_START, SoundType.MINING_STEP, SoundType.MINING_SUCCESS,
                        List.of(new SkillXpRecord(SkillType.MINING, 17.5))),

                new ResourceNodeType(ResourceNodeTypeId.IRON_ORE.asInt(), MarkerType.IRON,
                        "Iron Ore Vein", Material.DEEPSLATE_IRON_ORE, true,
                        List.of(new SkillRequirement(SkillType.MINING, 15)),
                        ModifierType.MINING_BOOST, 11000, 12,
                        List.of(DropTableId.IRON_NODE.asInt(), DropTableId.MINING_RARE_DROPTABLE.asInt()), ItemId.IRON_ORE.asInt(),
                        "You need a pickaxe to mine that!", "Mining",
                        SoundType.MINING_START, SoundType.MINING_STEP, SoundType.MINING_SUCCESS,
                        List.of(new SkillXpRecord(SkillType.MINING, 35.0))),

                new ResourceNodeType(ResourceNodeTypeId.COAL_ORE.asInt(), MarkerType.COAL,
                        "Coal Ore Vein", Material.DEEPSLATE_COAL_ORE, true,
                        List.of(new SkillRequirement(SkillType.MINING, 30)),
                        ModifierType.MINING_BOOST, 15000, 15,
                        List.of(DropTableId.COAL_NODE.asInt(), DropTableId.MINING_RARE_DROPTABLE.asInt()), ItemId.COAL_ORE.asInt(),
                        "You need a pickaxe to mine that!", "Mining",
                        SoundType.MINING_START, SoundType.MINING_STEP, SoundType.MINING_SUCCESS,
                        List.of(new SkillXpRecord(SkillType.MINING, 50.0))),

                new ResourceNodeType(ResourceNodeTypeId.MITHRIL_ORE.asInt(), MarkerType.MITHRIL,
                        "Mithril Ore Vein", Material.DEEPSLATE_LAPIS_ORE, true,
                        List.of(new SkillRequirement(SkillType.MINING, 55)),
                        ModifierType.MINING_BOOST, 23000, 30,
                        List.of(DropTableId.MITHRIL_NODE.asInt(), DropTableId.MINING_RARE_DROPTABLE.asInt()), ItemId.MITHRIL_ORE.asInt(),
                        "You need a pickaxe to mine that!", "Mining",
                        SoundType.MINING_START, SoundType.MINING_STEP, SoundType.MINING_SUCCESS,
                        List.of(new SkillXpRecord(SkillType.MINING, 80.0))),

                new ResourceNodeType(ResourceNodeTypeId.ADAMANT_ORE.asInt(), MarkerType.ADAMANT,
                        "Adamantite Ore Vein", Material.DEEPSLATE_EMERALD_ORE, true,
                        List.of(new SkillRequirement(SkillType.MINING, 70)),
                        ModifierType.MINING_BOOST, 29000, 60,
                        List.of(DropTableId.ADAMANT_NODE.asInt(), DropTableId.MINING_RARE_DROPTABLE.asInt()), ItemId.ADAMANT_ORE.asInt(),
                        "You need a pickaxe to mine that!", "Mining",
                        SoundType.MINING_START, SoundType.MINING_STEP, SoundType.MINING_SUCCESS,
                        List.of(new SkillXpRecord(SkillType.MINING, 95.0))),

                new ResourceNodeType(ResourceNodeTypeId.RUNE_ORE.asInt(), MarkerType.RUNE,
                        "Runite Ore Vein", Material.DEEPSLATE_DIAMOND_ORE, true,
                        List.of(new SkillRequirement(SkillType.MINING, 85)),
                        ModifierType.MINING_BOOST, 42000, 120,
                        List.of(DropTableId.RUNE_NODE.asInt(), DropTableId.MINING_RARE_DROPTABLE.asInt()), ItemId.RUNE_ORE.asInt(),
                        "You need a pickaxe to mine that!", "Mining",
                        SoundType.MINING_START, SoundType.MINING_STEP, SoundType.MINING_SUCCESS,
                        List.of(new SkillXpRecord(SkillType.MINING, 125.0))),


                new ResourceNodeType(ResourceNodeTypeId.HERRING_POOL.asInt(), MarkerType.HERRING, "Herring Pool", Material.BARRIER, true,
                        List.of(new SkillRequirement(SkillType.FISHING, 1)),
                        ModifierType.BASIC_FISHING, 10000, 5,
                        List.of(DropTableId.HERRING_NODE.asInt(), DropTableId.FISHING_RARE_DROPTABLE.asInt()), ItemId.RAW_HERRING.asInt(),
                        "You need a fishing net to catch that!", "Casting net at",
                        SoundType.FISHING_START_NET, SoundType.FISHING_STEP, SoundType.FISHING_SUCCESS,
                        List.of(new SkillXpRecord(SkillType.FISHING, 4.0))),

                new ResourceNodeType(ResourceNodeTypeId.MINNOW_POOL.asInt(), MarkerType.MINNOW, "Minnow Pool", Material.BARRIER, true,
                        List.of(new SkillRequirement(SkillType.FISHING, 5)),
                        ModifierType.BASIC_FISHING, 11000, 6,
                        List.of(DropTableId.MINNOW_NODE.asInt(), DropTableId.FISHING_RARE_DROPTABLE.asInt()), ItemId.RAW_MINNOW.asInt(),
                        "You need a fishing net to catch that!", "Sweeping net at",
                        SoundType.FISHING_START_NET, SoundType.FISHING_STEP, SoundType.FISHING_SUCCESS,
                        List.of(new SkillXpRecord(SkillType.FISHING, 5.0))),

                new ResourceNodeType(ResourceNodeTypeId.TROUT_STREAM.asInt(), MarkerType.TROUT, "Trout Stream", Material.BARRIER, true,
                        List.of(new SkillRequirement(SkillType.FISHING, 15)),
                        ModifierType.REGULAR_FISHING, 12000, 7,
                        List.of(DropTableId.TROUT_NODE.asInt(), DropTableId.FISHING_RARE_DROPTABLE.asInt()), ItemId.RAW_TROUT.asInt(),
                        "You need a fishing rod to catch that!", "Casting line at",
                        SoundType.FISHING_START_ROD, SoundType.FISHING_STEP, SoundType.FISHING_SUCCESS,
                        List.of(new SkillXpRecord(SkillType.FISHING, 7.0))),

                new ResourceNodeType(ResourceNodeTypeId.SALMON_STREAM.asInt(), MarkerType.SALMON, "Salmon Stream", Material.BARRIER, true,
                        List.of(new SkillRequirement(SkillType.FISHING, 25)),
                        ModifierType.REGULAR_FISHING, 13000, 7,
                        List.of(DropTableId.SALMON_NODE.asInt(), DropTableId.FISHING_RARE_DROPTABLE.asInt()), ItemId.RAW_SALMON.asInt(),
                        "You need a fishing rod to catch that!", "Reeling in at",
                        SoundType.FISHING_START_ROD, SoundType.FISHING_STEP, SoundType.FISHING_SUCCESS,
                        List.of(new SkillXpRecord(SkillType.FISHING, 10.0))),

                new ResourceNodeType(ResourceNodeTypeId.TUNA_SPOT.asInt(), MarkerType.TUNA, "Tuna Spot", Material.BARRIER, true,
                        List.of(new SkillRequirement(SkillType.FISHING, 35)),
                        ModifierType.REGULAR_FISHING, 14000, 8,
                        List.of(DropTableId.TUNA_NODE.asInt(), DropTableId.FISHING_RARE_DROPTABLE.asInt()), ItemId.RAW_TUNA.asInt(),
                        "You need a fishing rod to catch that!", "Hooking fish at",
                        SoundType.FISHING_START_ROD, SoundType.FISHING_STEP, SoundType.FISHING_SUCCESS,
                        List.of(new SkillXpRecord(SkillType.FISHING, 13.0))),

                new ResourceNodeType(ResourceNodeTypeId.CRAWLER_REEF.asInt(), MarkerType.CRAWLER, "Crawler Reef", Material.BARRIER, true,
                        List.of(new SkillRequirement(SkillType.FISHING, 45)),
                        ModifierType.REGULAR_FISHING, 15000, 8,
                        List.of(DropTableId.CRAWLER_NODE.asInt(), DropTableId.FISHING_RARE_DROPTABLE.asInt()), ItemId.RAW_CRAWLER.asInt(),
                        "You need a fishing rod to catch that!", "Casting line at",
                        SoundType.FISHING_START_ROD, SoundType.FISHING_STEP, SoundType.FISHING_SUCCESS,
                        List.of(new SkillXpRecord(SkillType.FISHING, 15.0))),

                new ResourceNodeType(ResourceNodeTypeId.PIKE_LAKE.asInt(), MarkerType.PIKE, "Pike Lake", Material.BARRIER, true,
                        List.of(new SkillRequirement(SkillType.FISHING, 55)),
                        ModifierType.REGULAR_FISHING, 16000, 9,
                        List.of(DropTableId.PIKE_NODE.asInt(), DropTableId.FISHING_RARE_DROPTABLE.asInt()), ItemId.RAW_PIKE.asInt(),
                        "You need a fishing rod to catch that!", "Reeling in at",
                        SoundType.FISHING_START_ROD, SoundType.FISHING_STEP, SoundType.FISHING_SUCCESS,
                        List.of(new SkillXpRecord(SkillType.FISHING, 18.0))),

                new ResourceNodeType(ResourceNodeTypeId.EEL_SWAMP.asInt(), MarkerType.EEL, "Eel Swamp", Material.BARRIER, true,
                        List.of(new SkillRequirement(SkillType.FISHING, 65)),
                        ModifierType.REGULAR_FISHING, 17000, 10,
                        List.of(DropTableId.EEL_NODE.asInt(), DropTableId.FISHING_RARE_DROPTABLE.asInt()), ItemId.RAW_EEL.asInt(),
                        "You need a fishing rod to catch that!", "Casting line at",
                        SoundType.FISHING_START_ROD, SoundType.FISHING_STEP, SoundType.FISHING_SUCCESS,
                        List.of(new SkillXpRecord(SkillType.FISHING, 22.0))),

                new ResourceNodeType(ResourceNodeTypeId.SHARK_DEPTHS.asInt(), MarkerType.SHARK, "Shark Depths", Material.BARRIER, true,
                        List.of(new SkillRequirement(SkillType.FISHING, 75)),
                        ModifierType.ADVANCED_FISHING, 18000, 12,
                        List.of(DropTableId.SHARK_NODE.asInt(), DropTableId.FISHING_RARE_DROPTABLE.asInt()), ItemId.RAW_SHARK.asInt(),
                        "You need a reinforced fishing rod to catch that!", "Casting reinforced rod at",
                        SoundType.FISHING_START_REINFORCED_ROD, SoundType.FISHING_STEP, SoundType.FISHING_SUCCESS,
                        List.of(new SkillXpRecord(SkillType.FISHING, 26.0))),

                new ResourceNodeType(ResourceNodeTypeId.TURTLE_BAY.asInt(), MarkerType.TURTLE, "Turtle Bay", Material.BARRIER, true,
                        List.of(new SkillRequirement(SkillType.FISHING, 85)),
                        ModifierType.TRAP_FISHING, 20000, 15,
                        List.of(DropTableId.TURTLE_NODE.asInt(), DropTableId.FISHING_RARE_DROPTABLE.asInt()), ItemId.RAW_TURTLE.asInt(),
                        "You need a fishing trap to catch that!", "Setting trap at",
                        SoundType.FISHING_START_TRAP, SoundType.FISHING_STEP, SoundType.FISHING_SUCCESS,
                        List.of(new SkillXpRecord(SkillType.FISHING, 30.0))),

                new ResourceNodeType(ResourceNodeTypeId.RAY_SANDBANK.asInt(), MarkerType.RAY, "Ray Sandbank", Material.BARRIER, true,
                        List.of(new SkillRequirement(SkillType.FISHING, 95)),
                        ModifierType.TRAP_FISHING, 22000, 18,
                        List.of(DropTableId.RAY_NODE.asInt(), DropTableId.FISHING_RARE_DROPTABLE.asInt()), ItemId.RAW_RAY.asInt(),
                        "You need a fishing trap to catch that!", "Checking trap at",
                        SoundType.FISHING_START_TRAP, SoundType.FISHING_STEP, SoundType.FISHING_SUCCESS,
                        List.of(new SkillXpRecord(SkillType.FISHING, 35.0)))


        );


    }
}
