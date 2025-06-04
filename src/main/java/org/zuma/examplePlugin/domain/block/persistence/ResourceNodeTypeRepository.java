package org.zuma.examplePlugin.domain.block.persistence;

import org.bukkit.Material;
import org.zuma.examplePlugin.core.constants.SoundType;
import org.zuma.examplePlugin.domain.block.constants.ResourceNodeTypeId;
import org.zuma.examplePlugin.domain.block.meta.ResourceNodeType;
import org.zuma.examplePlugin.domain.item.ItemRecord;
import org.zuma.examplePlugin.domain.item.constants.ItemId;
import org.zuma.examplePlugin.domain.modifier.constants.ModifierType;
import org.zuma.examplePlugin.domain.skill.SkillRequirement;
import org.zuma.examplePlugin.domain.skill.SkillXpRecord;
import org.zuma.examplePlugin.domain.skill.constants.SkillType;

import java.util.List;

public class ResourceNodeTypeRepository {

    public List<ResourceNodeType> getAll() {
        return List.of(
                new ResourceNodeType(ResourceNodeTypeId.IRON_ORE.asInt(), "Iron Ore Vein", Material.IRON_ORE,
                        List.of(new SkillRequirement(SkillType.MINING, 1)),
                        ModifierType.MINING_BOOST, 3000, 5,
                        new ItemRecord(ItemId.IRON_ORE, 1),
                        "You need a pickaxe to mine that!", "Mining",
                        SoundType.MINING_START, SoundType.MINING_STEP, SoundType.MINING_SUCCESS,
                        List.of(new SkillXpRecord(SkillType.MINING, 5.0))),

                new ResourceNodeType(ResourceNodeTypeId.HERRING_POOL.asInt(), "Herring Pool", Material.BARRIER,
                        List.of(new SkillRequirement(SkillType.FISHING, 1)),
                        ModifierType.BASIC_FISHING, 10000, 5,
                        new ItemRecord(ItemId.RAW_HERRING, 1),
                        "You need a fishing net to catch that!", "Casting net at",
                        SoundType.FISHING_START_NET, SoundType.FISHING_STEP, SoundType.FISHING_SUCCESS,
                        List.of(new SkillXpRecord(SkillType.FISHING, 4.0))),

                new ResourceNodeType(ResourceNodeTypeId.MINNOW_POOL.asInt(), "Minnow Pool", Material.BARRIER,
                        List.of(new SkillRequirement(SkillType.FISHING, 5)),
                        ModifierType.BASIC_FISHING, 11000, 6,
                        new ItemRecord(ItemId.RAW_MINNOW, 1),
                        "You need a fishing net to catch that!", "Sweeping net at",
                        SoundType.FISHING_START_NET, SoundType.FISHING_STEP, SoundType.FISHING_SUCCESS,
                        List.of(new SkillXpRecord(SkillType.FISHING, 5.0))),

                new ResourceNodeType(ResourceNodeTypeId.TROUT_STREAM.asInt(), "Trout Stream", Material.BARRIER,
                        List.of(new SkillRequirement(SkillType.FISHING, 15)),
                        ModifierType.REGULAR_FISHING, 12000, 7,
                        new ItemRecord(ItemId.RAW_TROUT, 1),
                        "You need a fishing rod to catch that!", "Casting line at",
                        SoundType.FISHING_START_ROD, SoundType.FISHING_STEP, SoundType.FISHING_SUCCESS,
                        List.of(new SkillXpRecord(SkillType.FISHING, 7.0))),

                new ResourceNodeType(ResourceNodeTypeId.SALMON_STREAM.asInt(), "Salmon Stream", Material.BARRIER,
                        List.of(new SkillRequirement(SkillType.FISHING, 25)),
                        ModifierType.REGULAR_FISHING, 13000, 7,
                        new ItemRecord(ItemId.RAW_SALMON, 1),
                        "You need a fishing rod to catch that!", "Reeling in at",
                        SoundType.FISHING_START_ROD, SoundType.FISHING_STEP, SoundType.FISHING_SUCCESS,
                        List.of(new SkillXpRecord(SkillType.FISHING, 10.0))),

                new ResourceNodeType(ResourceNodeTypeId.TUNA_SPOT.asInt(), "Tuna Spot", Material.BARRIER,
                        List.of(new SkillRequirement(SkillType.FISHING, 35)),
                        ModifierType.REGULAR_FISHING, 14000, 8,
                        new ItemRecord(ItemId.RAW_TUNA, 1),
                        "You need a fishing rod to catch that!", "Hooking fish at",
                        SoundType.FISHING_START_ROD, SoundType.FISHING_STEP, SoundType.FISHING_SUCCESS,
                        List.of(new SkillXpRecord(SkillType.FISHING, 13.0))),

                new ResourceNodeType(ResourceNodeTypeId.CRAWLER_REEF.asInt(), "Crawler Reef", Material.BARRIER,
                        List.of(new SkillRequirement(SkillType.FISHING, 45)),
                        ModifierType.REGULAR_FISHING, 15000, 8,
                        new ItemRecord(ItemId.RAW_CRAWLER, 1),
                        "You need a fishing rod to catch that!", "Casting line at",
                        SoundType.FISHING_START_ROD, SoundType.FISHING_STEP, SoundType.FISHING_SUCCESS,
                        List.of(new SkillXpRecord(SkillType.FISHING, 15.0))),

                new ResourceNodeType(ResourceNodeTypeId.PIKE_LAKE.asInt(), "Pike Lake", Material.BARRIER,
                        List.of(new SkillRequirement(SkillType.FISHING, 55)),
                        ModifierType.REGULAR_FISHING, 16000, 9,
                        new ItemRecord(ItemId.RAW_PIKE, 1),
                        "You need a fishing rod to catch that!", "Reeling in at",
                        SoundType.FISHING_START_ROD, SoundType.FISHING_STEP, SoundType.FISHING_SUCCESS,
                        List.of(new SkillXpRecord(SkillType.FISHING, 18.0))),

                new ResourceNodeType(ResourceNodeTypeId.EEL_SWAMP.asInt(), "Eel Swamp", Material.BARRIER,
                        List.of(new SkillRequirement(SkillType.FISHING, 65)),
                        ModifierType.REGULAR_FISHING, 17000, 10,
                        new ItemRecord(ItemId.RAW_EEL, 1),
                        "You need a fishing rod to catch that!", "Casting line at",
                        SoundType.FISHING_START_ROD, SoundType.FISHING_STEP, SoundType.FISHING_SUCCESS,
                        List.of(new SkillXpRecord(SkillType.FISHING, 22.0))),

                new ResourceNodeType(ResourceNodeTypeId.SHARK_DEPTHS.asInt(), "Shark Depths", Material.BARRIER,
                        List.of(new SkillRequirement(SkillType.FISHING, 75)),
                        ModifierType.ADVANCED_FISHING, 18000, 12,
                        new ItemRecord(ItemId.RAW_SHARK, 1),
                        "You need a reinforced fishing rod to catch that!", "Casting reinforced rod at",
                        SoundType.FISHING_START_REINFORCED_ROD, SoundType.FISHING_STEP, SoundType.FISHING_SUCCESS,
                        List.of(new SkillXpRecord(SkillType.FISHING, 26.0))),

                new ResourceNodeType(ResourceNodeTypeId.TURTLE_BAY.asInt(), "Turtle Bay", Material.BARRIER,
                        List.of(new SkillRequirement(SkillType.FISHING, 85)),
                        ModifierType.TRAP_FISHING, 20000, 15,
                        new ItemRecord(ItemId.RAW_TURTLE, 1),
                        "You need a fishing trap to catch that!", "Setting trap at",
                        SoundType.FISHING_START_TRAP, SoundType.FISHING_STEP, SoundType.FISHING_SUCCESS,
                        List.of(new SkillXpRecord(SkillType.FISHING, 30.0))),

                new ResourceNodeType(ResourceNodeTypeId.RAY_SANDBANK.asInt(), "Ray Sandbank", Material.BARRIER,
                        List.of(new SkillRequirement(SkillType.FISHING, 95)),
                        ModifierType.TRAP_FISHING, 22000, 18,
                        new ItemRecord(ItemId.RAW_RAY, 1),
                        "You need a fishing trap to catch that!", "Checking trap at",
                        SoundType.FISHING_START_TRAP, SoundType.FISHING_STEP, SoundType.FISHING_SUCCESS,
                        List.of(new SkillXpRecord(SkillType.FISHING, 35.0)))


        );


    }
}
