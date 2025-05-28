package org.zuma.examplePlugin.domain.item.persistence;

import com.google.gson.Gson;
import org.bukkit.Color;
import org.bukkit.Material;
import org.zuma.examplePlugin.core.meta.JsonMeta;
import org.zuma.examplePlugin.domain.heads.HeadTexture;
import org.zuma.examplePlugin.domain.item.SkillRequirement;
import org.zuma.examplePlugin.domain.item.constants.ItemId;
import org.zuma.examplePlugin.domain.item.constants.ItemRarity;
import org.zuma.examplePlugin.domain.item.constants.ItemType;
import org.zuma.examplePlugin.domain.item.meta.AbstractMeta;
import org.zuma.examplePlugin.domain.item.meta.EquipableMeta;
import org.zuma.examplePlugin.domain.modifier.Modifier;
import org.zuma.examplePlugin.domain.modifier.constants.ModifierType;
import org.zuma.examplePlugin.domain.skill.constants.SkillType;

import java.util.ArrayList;
import java.util.List;

public class ItemMetaRepository {

    public List<AbstractMeta> getAll() {
        JsonMeta pickaxeReqs = new JsonMeta(new Gson().toJson(new EquipableMeta(List.of(new Modifier(ModifierType.MINING_BOOST, 1)), List.of(new SkillRequirement(SkillType.MINING, 1)))), 1);
        JsonMeta axeReqs = new JsonMeta(new Gson().toJson(new EquipableMeta(new ArrayList<>(), List.of(new SkillRequirement(SkillType.WOODCUTTING, 1)))), 1);

        JsonMeta empty = new JsonMeta("", 1);
        JsonMeta emptyArmorReqs = new JsonMeta(new Gson().toJson(new EquipableMeta(new ArrayList<>(), new ArrayList<>())), 1);

        return List.of(
                // Materials

                new AbstractMeta(ItemType.MATERIAL, ItemId.BRONZE_ORE.asInt(), Material.COAL, ItemRarity.COMMON, "Bronze Ore", null, null, null, empty),
                new AbstractMeta(ItemType.MATERIAL, ItemId.IRON_ORE.asInt(), Material.COAL, ItemRarity.COMMON, "Iron Ore", null, null, null, empty),
                new AbstractMeta(ItemType.MATERIAL, ItemId.STEEL_ORE.asInt(), Material.COAL, ItemRarity.COMMON, "Steel Ore", null, null, null, empty),
                new AbstractMeta(ItemType.MATERIAL, ItemId.MITHRIL_ORE.asInt(), Material.COAL, ItemRarity.UNCOMMON, "Mithril Ore", null, null, null, empty),
                new AbstractMeta(ItemType.MATERIAL, ItemId.ADAMANTITE_ORE.asInt(), Material.COAL, ItemRarity.UNCOMMON, "Adamantite Ore", null, null, null, empty),
                new AbstractMeta(ItemType.MATERIAL, ItemId.RUNITE_ORE.asInt(), Material.COAL, ItemRarity.RARE, "Runite Ore", null, null, null, empty),

                new AbstractMeta(ItemType.MATERIAL, ItemId.BRONZE_BAR.asInt(), Material.COAL, ItemRarity.COMMON, "Bronze Bar", null, null, null, empty),
                new AbstractMeta(ItemType.MATERIAL, ItemId.IRON_BAR.asInt(), Material.COAL, ItemRarity.COMMON, "Iron Bar", null, null, null, empty),
                new AbstractMeta(ItemType.MATERIAL, ItemId.STEEL_BAR.asInt(), Material.COAL, ItemRarity.COMMON, "Steel Bar", null, null, null, empty),
                new AbstractMeta(ItemType.MATERIAL, ItemId.MITHRIL_BAR.asInt(), Material.COAL, ItemRarity.UNCOMMON, "Mithril Bar", null, null, null, empty),
                new AbstractMeta(ItemType.MATERIAL, ItemId.ADAMANTITE_BAR.asInt(), Material.COAL, ItemRarity.UNCOMMON, "Adamantite Bar", null, null, null, empty),
                new AbstractMeta(ItemType.MATERIAL, ItemId.RUNITE_BAR.asInt(), Material.COAL, ItemRarity.RARE, "Runite Bar", null, null, null, empty),


                // Tools
                new AbstractMeta(ItemType.GEAR, ItemId.BRONZE_PICKAXE.asInt(), Material.WOODEN_PICKAXE, ItemRarity.COMMON, "Bronze Pickaxe", null, null, null,
                        new JsonMeta(new Gson().toJson(
                                new EquipableMeta(
                                        List.of(new Modifier(ModifierType.MINING_BOOST, 1)),
                                        List.of(new SkillRequirement(SkillType.MINING, 1))
                                )
                        ), 1)
                ),
                new AbstractMeta(ItemType.GEAR, ItemId.IRON_PICKAXE.asInt(), Material.WOODEN_PICKAXE, ItemRarity.COMMON, "Iron Pickaxe", null, null, null,
                        new JsonMeta(new Gson().toJson(
                                new EquipableMeta(
                                        List.of(new Modifier(ModifierType.MINING_BOOST, 2)),
                                        List.of(new SkillRequirement(SkillType.MINING, 1))
                                )
                        ), 1)
                ),
                new AbstractMeta(ItemType.GEAR, ItemId.STEEL_PICKAXE.asInt(), Material.WOODEN_PICKAXE, ItemRarity.COMMON, "Steel Pickaxe", null, null, null,
                        new JsonMeta(new Gson().toJson(
                                new EquipableMeta(
                                        List.of(new Modifier(ModifierType.MINING_BOOST, 3)),
                                        List.of(new SkillRequirement(SkillType.MINING, 6))
                                )
                        ), 1)
                ),
                new AbstractMeta(ItemType.GEAR, ItemId.MITHRIL_PICKAXE.asInt(), Material.WOODEN_PICKAXE, ItemRarity.UNCOMMON, "Mithril Pickaxe", null, null, null,
                        new JsonMeta(new Gson().toJson(
                                new EquipableMeta(
                                        List.of(new Modifier(ModifierType.MINING_BOOST, 4)),
                                        List.of(new SkillRequirement(SkillType.MINING, 21))
                                )
                        ), 1)
                ),
                new AbstractMeta(ItemType.GEAR, ItemId.ADAMANTITE_PICKAXE.asInt(), Material.WOODEN_PICKAXE, ItemRarity.UNCOMMON, "Adamantite Pickaxe", null, null, null,
                        new JsonMeta(new Gson().toJson(
                                new EquipableMeta(
                                        List.of(new Modifier(ModifierType.MINING_BOOST, 5)),
                                        List.of(new SkillRequirement(SkillType.MINING, 31))
                                )
                        ), 1)
                ),
                new AbstractMeta(ItemType.GEAR, ItemId.RUNITE_PICKAXE.asInt(), Material.WOODEN_PICKAXE, ItemRarity.RARE, "Runite Pickaxe", null, null, null,
                        new JsonMeta(new Gson().toJson(
                                new EquipableMeta(
                                        List.of(new Modifier(ModifierType.MINING_BOOST, 6)),
                                        List.of(new SkillRequirement(SkillType.MINING, 41))
                                )
                        ), 1)
                ),

                new AbstractMeta(ItemType.GEAR, ItemId.BRONZE_AXE.asInt(), Material.WOODEN_AXE, ItemRarity.COMMON, "Bronze axe", null, null, null,
                        new JsonMeta(new Gson().toJson(
                                new EquipableMeta(
                                        List.of(new Modifier(ModifierType.WOODCUTTING_BOOST, 1)),
                                        List.of(new SkillRequirement(SkillType.WOODCUTTING, 1))
                                )
                        ), 1)
                ),
                new AbstractMeta(ItemType.GEAR, ItemId.IRON_AXE.asInt(), Material.WOODEN_AXE, ItemRarity.COMMON, "Iron axe", null, null, null,
                        new JsonMeta(new Gson().toJson(
                                new EquipableMeta(
                                        List.of(new Modifier(ModifierType.WOODCUTTING_BOOST, 2)),
                                        List.of(new SkillRequirement(SkillType.WOODCUTTING, 1))
                                )
                        ), 1)
                ),
                new AbstractMeta(ItemType.GEAR, ItemId.STEEL_AXE.asInt(), Material.WOODEN_AXE, ItemRarity.COMMON, "Steel axe", null, null, null,
                        new JsonMeta(new Gson().toJson(
                                new EquipableMeta(
                                        List.of(new Modifier(ModifierType.WOODCUTTING_BOOST, 3)),
                                        List.of(new SkillRequirement(SkillType.WOODCUTTING, 6))
                                )
                        ), 1)
                ),
                new AbstractMeta(ItemType.GEAR, ItemId.MITHRIL_AXE.asInt(), Material.WOODEN_AXE, ItemRarity.UNCOMMON, "Mithril axe", null, null, null,
                        new JsonMeta(new Gson().toJson(
                                new EquipableMeta(
                                        List.of(new Modifier(ModifierType.WOODCUTTING_BOOST, 4)),
                                        List.of(new SkillRequirement(SkillType.WOODCUTTING, 21))
                                )
                        ), 1)
                ),
                new AbstractMeta(ItemType.GEAR, ItemId.ADAMANTITE_AXE.asInt(), Material.WOODEN_AXE, ItemRarity.UNCOMMON, "Adamantite axe", null, null, null,
                        new JsonMeta(new Gson().toJson(
                                new EquipableMeta(
                                        List.of(new Modifier(ModifierType.WOODCUTTING_BOOST, 5)),
                                        List.of(new SkillRequirement(SkillType.WOODCUTTING, 31))
                                )
                        ), 1)
                ),
                new AbstractMeta(ItemType.GEAR, ItemId.RUNITE_AXE.asInt(), Material.WOODEN_AXE, ItemRarity.RARE, "Runite axe", null, null, null,
                        new JsonMeta(new Gson().toJson(
                                new EquipableMeta(
                                        List.of(new Modifier(ModifierType.WOODCUTTING_BOOST, 6)),
                                        List.of(new SkillRequirement(SkillType.WOODCUTTING, 41))
                                )
                        ), 1)
                ),


                // Armor - Helmets
                new AbstractMeta(ItemType.GEAR, ItemId.BRONZE_HELMET.asInt(), Material.PLAYER_HEAD, ItemRarity.COMMON, "Bronze Helmet", null, HeadTexture.BRONZE_HELM.asBase64(), Color.fromRGB(210, 170, 120),
                        new JsonMeta(new Gson().toJson(
                                new EquipableMeta(
                                        List.of(new Modifier(ModifierType.DEFENCE_STAB, 1), new Modifier(ModifierType.DEFENCE_SLASH, 1), new Modifier(ModifierType.DEFENCE_CRUSH, 1)),
                                        List.of(new SkillRequirement(SkillType.DEFENCE, 1))
                                )
                        ), 1)
                ),
                new AbstractMeta(ItemType.GEAR, ItemId.IRON_HELMET.asInt(), Material.PLAYER_HEAD, ItemRarity.COMMON, "Iron Helmet", null, HeadTexture.IRON_HELM.asBase64(), null,
                        new JsonMeta(new Gson().toJson(
                                new EquipableMeta(
                                        List.of(new Modifier(ModifierType.DEFENCE_STAB, 3), new Modifier(ModifierType.DEFENCE_SLASH, 4), new Modifier(ModifierType.DEFENCE_CRUSH, 2)),
                                        List.of(new SkillRequirement(SkillType.DEFENCE, 1))
                                )
                        ), 1)
                ),
                new AbstractMeta(ItemType.GEAR, ItemId.STEEL_HELMET.asInt(), Material.PLAYER_HEAD, ItemRarity.COMMON, "Steel Helmet", null, HeadTexture.STEEL_HELM.asBase64(), null,
                        new JsonMeta(new Gson().toJson(
                                new EquipableMeta(
                                        List.of(new Modifier(ModifierType.DEFENCE_STAB, 5), new Modifier(ModifierType.DEFENCE_SLASH, 6), new Modifier(ModifierType.DEFENCE_CRUSH, 5)),
                                        List.of(new SkillRequirement(SkillType.DEFENCE, 5))
                                )
                        ), 1)
                ),
                new AbstractMeta(ItemType.GEAR, ItemId.MITHRIL_HELMET.asInt(), Material.PLAYER_HEAD, ItemRarity.UNCOMMON, "Mithril Helmet", null, HeadTexture.MITHRIL_HELM.asBase64(), null,
                        new JsonMeta(new Gson().toJson(
                                new EquipableMeta(
                                        List.of(new Modifier(ModifierType.DEFENCE_STAB, 8), new Modifier(ModifierType.DEFENCE_SLASH, 9), new Modifier(ModifierType.DEFENCE_CRUSH, 7)),
                                        List.of(new SkillRequirement(SkillType.DEFENCE, 20))
                                )
                        ), 1)
                ),
                new AbstractMeta(ItemType.GEAR, ItemId.ADAMANTITE_HELMET.asInt(), Material.PLAYER_HEAD, ItemRarity.UNCOMMON, "Adamantite Helmet", null, HeadTexture.ADAMANT_HELM.asBase64(), null,
                        new JsonMeta(new Gson().toJson(
                                new EquipableMeta(
                                        List.of(new Modifier(ModifierType.DEFENCE_STAB, 12), new Modifier(ModifierType.DEFENCE_SLASH, 13), new Modifier(ModifierType.DEFENCE_CRUSH, 11)),
                                        List.of(new SkillRequirement(SkillType.DEFENCE, 30))
                                )
                        ), 1)
                ),
                new AbstractMeta(ItemType.GEAR, ItemId.RUNITE_HELMET.asInt(), Material.PLAYER_HEAD, ItemRarity.RARE, "Runite Helmet", null, HeadTexture.RUNE_HELM.asBase64(), null,
                        new JsonMeta(new Gson().toJson(
                                new EquipableMeta(
                                        List.of(new Modifier(ModifierType.DEFENCE_STAB, 18), new Modifier(ModifierType.DEFENCE_SLASH, 19), new Modifier(ModifierType.DEFENCE_CRUSH, 17)),
                                        List.of(new SkillRequirement(SkillType.DEFENCE, 40))
                                )
                        ), 1)
                ),

                // Armor - Chestplates
                new AbstractMeta(ItemType.GEAR, ItemId.BRONZE_BODY.asInt(), Material.LEATHER_CHESTPLATE, ItemRarity.COMMON, "Bronze Body", null, null, Color.fromRGB(210, 170, 120),
                        new JsonMeta(new Gson().toJson(
                                new EquipableMeta(
                                        List.of(
                                                new Modifier(ModifierType.DEFENCE_STAB, 6),
                                                new Modifier(ModifierType.DEFENCE_SLASH, 8),
                                                new Modifier(ModifierType.DEFENCE_CRUSH, 7)
                                        ),
                                        List.of(new SkillRequirement(SkillType.DEFENCE, 1))
                                )
                        ), 1)
                ),
                new AbstractMeta(ItemType.GEAR, ItemId.IRON_BODY.asInt(), Material.CHAINMAIL_CHESTPLATE, ItemRarity.COMMON, "Iron Body", null, null, null,
                        new JsonMeta(new Gson().toJson(
                                new EquipableMeta(
                                        List.of(
                                                new Modifier(ModifierType.DEFENCE_STAB, 10),
                                                new Modifier(ModifierType.DEFENCE_SLASH, 13),
                                                new Modifier(ModifierType.DEFENCE_CRUSH, 11)
                                        ),
                                        List.of(new SkillRequirement(SkillType.DEFENCE, 1))
                                )
                        ), 1)
                ),
                new AbstractMeta(ItemType.GEAR, ItemId.STEEL_BODY.asInt(), Material.IRON_CHESTPLATE, ItemRarity.COMMON, "Steel Body", null, null, null,
                        new JsonMeta(new Gson().toJson(
                                new EquipableMeta(
                                        List.of(
                                                new Modifier(ModifierType.DEFENCE_STAB, 18),
                                                new Modifier(ModifierType.DEFENCE_SLASH, 19),
                                                new Modifier(ModifierType.DEFENCE_CRUSH, 17)
                                        ),
                                        List.of(new SkillRequirement(SkillType.DEFENCE, 5))
                                )
                        ), 1)
                ),
                new AbstractMeta(ItemType.GEAR, ItemId.MITHRIL_BODY.asInt(), Material.GOLDEN_CHESTPLATE, ItemRarity.UNCOMMON, "Mithril Body", null, null, null,
                        new JsonMeta(new Gson().toJson(
                                new EquipableMeta(
                                        List.of(
                                                new Modifier(ModifierType.DEFENCE_STAB, 24),
                                                new Modifier(ModifierType.DEFENCE_SLASH, 25),
                                                new Modifier(ModifierType.DEFENCE_CRUSH, 23)
                                        ),
                                        List.of(new SkillRequirement(SkillType.DEFENCE, 20))
                                )
                        ), 1)
                ),
                new AbstractMeta(ItemType.GEAR, ItemId.ADAMANTITE_BODY.asInt(), Material.DIAMOND_CHESTPLATE, ItemRarity.UNCOMMON, "Adamantite Body", null, null, null,
                        new JsonMeta(new Gson().toJson(
                                new EquipableMeta(
                                        List.of(
                                                new Modifier(ModifierType.DEFENCE_STAB, 30),
                                                new Modifier(ModifierType.DEFENCE_SLASH, 31),
                                                new Modifier(ModifierType.DEFENCE_CRUSH, 29)
                                        ),
                                        List.of(new SkillRequirement(SkillType.DEFENCE, 30))
                                )
                        ), 1)
                ),
                new AbstractMeta(ItemType.GEAR, ItemId.RUNITE_BODY.asInt(), Material.NETHERITE_CHESTPLATE, ItemRarity.RARE, "Runite Body", null, null, null,
                        new JsonMeta(new Gson().toJson(
                                new EquipableMeta(
                                        List.of(
                                                new Modifier(ModifierType.DEFENCE_STAB, 40),
                                                new Modifier(ModifierType.DEFENCE_SLASH, 42),
                                                new Modifier(ModifierType.DEFENCE_CRUSH, 38)
                                        ),
                                        List.of(new SkillRequirement(SkillType.DEFENCE, 40))
                                )
                        ), 1)
                ),

                // Armor - platelegs
                new AbstractMeta(ItemType.GEAR, ItemId.BRONZE_PLATELEGS.asInt(), Material.LEATHER_LEGGINGS, ItemRarity.COMMON, "Bronze Platelegs", null, null, Color.fromRGB(210, 170, 120),
                        new JsonMeta(new Gson().toJson(
                                new EquipableMeta(
                                        List.of(
                                                new Modifier(ModifierType.DEFENCE_STAB, 4),
                                                new Modifier(ModifierType.DEFENCE_SLASH, 5),
                                                new Modifier(ModifierType.DEFENCE_CRUSH, 3)
                                        ),
                                        List.of(new SkillRequirement(SkillType.DEFENCE, 1))
                                )
                        ), 1)
                ),
                new AbstractMeta(ItemType.GEAR, ItemId.IRON_PLATELEGS.asInt(), Material.CHAINMAIL_LEGGINGS, ItemRarity.COMMON, "Iron Platelegs", null, null, null,
                        new JsonMeta(new Gson().toJson(
                                new EquipableMeta(
                                        List.of(
                                                new Modifier(ModifierType.DEFENCE_STAB, 7),
                                                new Modifier(ModifierType.DEFENCE_SLASH, 8),
                                                new Modifier(ModifierType.DEFENCE_CRUSH, 6)
                                        ),
                                        List.of(new SkillRequirement(SkillType.DEFENCE, 1))
                                )
                        ), 1)
                ),
                new AbstractMeta(ItemType.GEAR, ItemId.STEEL_PLATELEGS.asInt(), Material.IRON_LEGGINGS, ItemRarity.COMMON, "Steel Platelegs", null, null, null,
                        new JsonMeta(new Gson().toJson(
                                new EquipableMeta(
                                        List.of(
                                                new Modifier(ModifierType.DEFENCE_STAB, 12),
                                                new Modifier(ModifierType.DEFENCE_SLASH, 13),
                                                new Modifier(ModifierType.DEFENCE_CRUSH, 11)
                                        ),
                                        List.of(new SkillRequirement(SkillType.DEFENCE, 5))
                                )
                        ), 1)
                ),
                new AbstractMeta(ItemType.GEAR, ItemId.MITHRIL_PLATELEGS.asInt(), Material.GOLDEN_LEGGINGS, ItemRarity.UNCOMMON, "Mithril Platelegs", null, null, null,
                        new JsonMeta(new Gson().toJson(
                                new EquipableMeta(
                                        List.of(
                                                new Modifier(ModifierType.DEFENCE_STAB, 16),
                                                new Modifier(ModifierType.DEFENCE_SLASH, 17),
                                                new Modifier(ModifierType.DEFENCE_CRUSH, 15)
                                        ),
                                        List.of(new SkillRequirement(SkillType.DEFENCE, 20))
                                )
                        ), 1)
                ),
                new AbstractMeta(ItemType.GEAR, ItemId.ADAMANTITE_PLATELEGS.asInt(), Material.DIAMOND_LEGGINGS, ItemRarity.UNCOMMON, "Adamantite Platelegs", null, null, null,
                        new JsonMeta(new Gson().toJson(
                                new EquipableMeta(
                                        List.of(
                                                new Modifier(ModifierType.DEFENCE_STAB, 21),
                                                new Modifier(ModifierType.DEFENCE_SLASH, 22),
                                                new Modifier(ModifierType.DEFENCE_CRUSH, 20)
                                        ),
                                        List.of(new SkillRequirement(SkillType.DEFENCE, 30))
                                )
                        ), 1)
                ),
                new AbstractMeta(ItemType.GEAR, ItemId.RUNITE_PLATELEGS.asInt(), Material.NETHERITE_LEGGINGS, ItemRarity.RARE, "Runite Platelegs", null, null, null,
                        new JsonMeta(new Gson().toJson(
                                new EquipableMeta(
                                        List.of(
                                                new Modifier(ModifierType.DEFENCE_STAB, 28),
                                                new Modifier(ModifierType.DEFENCE_SLASH, 30),
                                                new Modifier(ModifierType.DEFENCE_CRUSH, 26)
                                        ),
                                        List.of(new SkillRequirement(SkillType.DEFENCE, 40))
                                )
                        ), 1)
                ),

                // Armor - Boots
                new AbstractMeta(ItemType.GEAR, ItemId.BRONZE_BOOTS.asInt(), Material.LEATHER_BOOTS, ItemRarity.COMMON, "Bronze Boots", null, null, Color.fromRGB(210, 170, 120),
                        new JsonMeta(new Gson().toJson(
                                new EquipableMeta(
                                        List.of(
                                                new Modifier(ModifierType.DEFENCE_STAB, 1),
                                                new Modifier(ModifierType.DEFENCE_SLASH, 2),
                                                new Modifier(ModifierType.DEFENCE_CRUSH, 1)
                                        ),
                                        List.of(new SkillRequirement(SkillType.DEFENCE, 1))
                                )
                        ), 1)
                ),
                new AbstractMeta(ItemType.GEAR, ItemId.IRON_BOOTS.asInt(), Material.CHAINMAIL_BOOTS, ItemRarity.COMMON, "Iron Boots", null, null, null,
                        new JsonMeta(new Gson().toJson(
                                new EquipableMeta(
                                        List.of(
                                                new Modifier(ModifierType.DEFENCE_STAB, 2),
                                                new Modifier(ModifierType.DEFENCE_SLASH, 3),
                                                new Modifier(ModifierType.DEFENCE_CRUSH, 2)
                                        ),
                                        List.of(new SkillRequirement(SkillType.DEFENCE, 1))
                                )
                        ), 1)
                ),
                new AbstractMeta(ItemType.GEAR, ItemId.STEEL_BOOTS.asInt(), Material.IRON_BOOTS, ItemRarity.COMMON, "Steel Boots", null, null, null,
                        new JsonMeta(new Gson().toJson(
                                new EquipableMeta(
                                        List.of(
                                                new Modifier(ModifierType.DEFENCE_STAB, 3),
                                                new Modifier(ModifierType.DEFENCE_SLASH, 4),
                                                new Modifier(ModifierType.DEFENCE_CRUSH, 3)
                                        ),
                                        List.of(new SkillRequirement(SkillType.DEFENCE, 5))
                                )
                        ), 1)
                ),
                new AbstractMeta(ItemType.GEAR, ItemId.MITHRIL_BOOTS.asInt(), Material.GOLDEN_BOOTS, ItemRarity.UNCOMMON, "Mithril Boots", null, null, null,
                        new JsonMeta(new Gson().toJson(
                                new EquipableMeta(
                                        List.of(
                                                new Modifier(ModifierType.DEFENCE_STAB, 5),
                                                new Modifier(ModifierType.DEFENCE_SLASH, 6),
                                                new Modifier(ModifierType.DEFENCE_CRUSH, 5)
                                        ),
                                        List.of(new SkillRequirement(SkillType.DEFENCE, 20))
                                )
                        ), 1)
                ),
                new AbstractMeta(ItemType.GEAR, ItemId.ADAMANTITE_BOOTS.asInt(), Material.DIAMOND_BOOTS, ItemRarity.UNCOMMON, "Adamantite Boots", null, null, null,
                        new JsonMeta(new Gson().toJson(
                                new EquipableMeta(
                                        List.of(
                                                new Modifier(ModifierType.DEFENCE_STAB, 7),
                                                new Modifier(ModifierType.DEFENCE_SLASH, 8),
                                                new Modifier(ModifierType.DEFENCE_CRUSH, 7)
                                        ),
                                        List.of(new SkillRequirement(SkillType.DEFENCE, 30))
                                )
                        ), 1)
                ),
                new AbstractMeta(ItemType.GEAR, ItemId.RUNITE_BOOTS.asInt(), Material.NETHERITE_BOOTS, ItemRarity.RARE, "Runite Boots", null, null, null,
                        new JsonMeta(new Gson().toJson(
                                new EquipableMeta(
                                        List.of(
                                                new Modifier(ModifierType.DEFENCE_STAB, 10),
                                                new Modifier(ModifierType.DEFENCE_SLASH, 11),
                                                new Modifier(ModifierType.DEFENCE_CRUSH, 9)
                                        ),
                                        List.of(new SkillRequirement(SkillType.DEFENCE, 40))
                                )
                        ), 1)
                ),


                // Weapons - Swords
                new AbstractMeta(ItemType.GEAR, ItemId.BRONZE_SWORD.asInt(), Material.WOODEN_SWORD, ItemRarity.COMMON, "Bronze Sword", null, null, null,
                        new JsonMeta(new Gson().toJson(
                                new EquipableMeta(
                                        List.of(
                                                new Modifier(ModifierType.ATTACK_SLASH, 4),
                                                new Modifier(ModifierType.STRENGTH_BONUS, 3)
                                        ),
                                        List.of(new SkillRequirement(SkillType.ATTACK, 1))
                                )
                        ), 1)
                ),
                new AbstractMeta(ItemType.GEAR, ItemId.IRON_SWORD.asInt(), Material.WOODEN_SWORD, ItemRarity.COMMON, "Iron Sword", null, null, null,
                        new JsonMeta(new Gson().toJson(
                                new EquipableMeta(
                                        List.of(
                                                new Modifier(ModifierType.ATTACK_SLASH, 6),
                                                new Modifier(ModifierType.STRENGTH_BONUS, 5)
                                        ),
                                        List.of(new SkillRequirement(SkillType.ATTACK, 1))
                                )
                        ), 1)
                ),
                new AbstractMeta(ItemType.GEAR, ItemId.STEEL_SWORD.asInt(), Material.WOODEN_SWORD, ItemRarity.COMMON, "Steel Sword", null, null, null,
                        new JsonMeta(new Gson().toJson(
                                new EquipableMeta(
                                        List.of(
                                                new Modifier(ModifierType.ATTACK_SLASH, 10),
                                                new Modifier(ModifierType.STRENGTH_BONUS, 9)
                                        ),
                                        List.of(new SkillRequirement(SkillType.ATTACK, 5))
                                )
                        ), 1)
                ),
                new AbstractMeta(ItemType.GEAR, ItemId.MITHRIL_SWORD.asInt(), Material.WOODEN_SWORD, ItemRarity.UNCOMMON, "Mithril Sword", null, null, null,
                        new JsonMeta(new Gson().toJson(
                                new EquipableMeta(
                                        List.of(
                                                new Modifier(ModifierType.ATTACK_SLASH, 16),
                                                new Modifier(ModifierType.STRENGTH_BONUS, 14)
                                        ),
                                        List.of(new SkillRequirement(SkillType.ATTACK, 20))
                                )
                        ), 1)
                ),
                new AbstractMeta(ItemType.GEAR, ItemId.ADAMANTITE_SWORD.asInt(), Material.WOODEN_SWORD, ItemRarity.UNCOMMON, "Adamantite Sword", null, null, null,
                        new JsonMeta(new Gson().toJson(
                                new EquipableMeta(
                                        List.of(
                                                new Modifier(ModifierType.ATTACK_SLASH, 21),
                                                new Modifier(ModifierType.STRENGTH_BONUS, 19)
                                        ),
                                        List.of(new SkillRequirement(SkillType.ATTACK, 30))
                                )
                        ), 1)
                ),
                new AbstractMeta(ItemType.GEAR, ItemId.RUNITE_SWORD.asInt(), Material.WOODEN_SWORD, ItemRarity.RARE, "Runite Sword", null, null, null,
                        new JsonMeta(new Gson().toJson(
                                new EquipableMeta(
                                        List.of(
                                                new Modifier(ModifierType.ATTACK_SLASH, 30),
                                                new Modifier(ModifierType.STRENGTH_BONUS, 29)
                                        ),
                                        List.of(new SkillRequirement(SkillType.ATTACK, 40))
                                )
                        ), 1)
                ),

                // Weapons - Daggers
                new AbstractMeta(ItemType.GEAR, ItemId.BRONZE_DAGGER.asInt(), Material.WOODEN_SWORD, ItemRarity.COMMON, "Bronze Dagger", null, null, null,
                        new JsonMeta(new Gson().toJson(
                                new EquipableMeta(
                                        List.of(
                                                new Modifier(ModifierType.ATTACK_STAB, 3),
                                                new Modifier(ModifierType.STRENGTH_BONUS, 2)
                                        ),
                                        List.of(new SkillRequirement(SkillType.ATTACK, 1))
                                )
                        ), 1)
                ),
                new AbstractMeta(ItemType.GEAR, ItemId.IRON_DAGGER.asInt(), Material.WOODEN_SWORD, ItemRarity.COMMON, "Iron Dagger", null, null, null,
                        new JsonMeta(new Gson().toJson(
                                new EquipableMeta(
                                        List.of(
                                                new Modifier(ModifierType.ATTACK_STAB, 5),
                                                new Modifier(ModifierType.STRENGTH_BONUS, 3)
                                        ),
                                        List.of(new SkillRequirement(SkillType.ATTACK, 1))
                                )
                        ), 1)
                ),
                new AbstractMeta(ItemType.GEAR, ItemId.STEEL_DAGGER.asInt(), Material.WOODEN_SWORD, ItemRarity.COMMON, "Steel Dagger", null, null, null,
                        new JsonMeta(new Gson().toJson(
                                new EquipableMeta(
                                        List.of(
                                                new Modifier(ModifierType.ATTACK_STAB, 8),
                                                new Modifier(ModifierType.STRENGTH_BONUS, 6)
                                        ),
                                        List.of(new SkillRequirement(SkillType.ATTACK, 5))
                                )
                        ), 1)
                ),
                new AbstractMeta(ItemType.GEAR, ItemId.MITHRIL_DAGGER.asInt(), Material.WOODEN_SWORD, ItemRarity.UNCOMMON, "Mithril Dagger", null, null, null,
                        new JsonMeta(new Gson().toJson(
                                new EquipableMeta(
                                        List.of(
                                                new Modifier(ModifierType.ATTACK_STAB, 14),
                                                new Modifier(ModifierType.STRENGTH_BONUS, 10)
                                        ),
                                        List.of(new SkillRequirement(SkillType.ATTACK, 20))
                                )
                        ), 1)
                ),
                new AbstractMeta(ItemType.GEAR, ItemId.ADAMANTITE_DAGGER.asInt(), Material.WOODEN_SWORD, ItemRarity.UNCOMMON, "Adamantite Dagger", null, null, null,
                        new JsonMeta(new Gson().toJson(
                                new EquipableMeta(
                                        List.of(
                                                new Modifier(ModifierType.ATTACK_STAB, 18),
                                                new Modifier(ModifierType.STRENGTH_BONUS, 14)
                                        ),
                                        List.of(new SkillRequirement(SkillType.ATTACK, 30))
                                )
                        ), 1)
                ),
                new AbstractMeta(ItemType.GEAR, ItemId.RUNITE_DAGGER.asInt(), Material.WOODEN_SWORD, ItemRarity.RARE, "Runite Dagger", null, null, null,
                        new JsonMeta(new Gson().toJson(
                                new EquipableMeta(
                                        List.of(
                                                new Modifier(ModifierType.ATTACK_STAB, 24),
                                                new Modifier(ModifierType.STRENGTH_BONUS, 19)
                                        ),
                                        List.of(new SkillRequirement(SkillType.ATTACK, 40))
                                )
                        ), 1)
                ),

                // Weapons - Battleaxes
                new AbstractMeta(ItemType.GEAR, ItemId.BRONZE_BATTLEAXE.asInt(), Material.WOODEN_SWORD, ItemRarity.COMMON, "Bronze Battleaxe", null, null, null,
                        new JsonMeta(new Gson().toJson(
                                new EquipableMeta(
                                        List.of(
                                                new Modifier(ModifierType.ATTACK_SLASH, 5),
                                                new Modifier(ModifierType.ATTACK_CRUSH, 6),
                                                new Modifier(ModifierType.STRENGTH_BONUS, 6)
                                        ),
                                        List.of(new SkillRequirement(SkillType.ATTACK, 1))
                                )
                        ), 1)
                ),
                new AbstractMeta(ItemType.GEAR, ItemId.IRON_BATTLEAXE.asInt(), Material.WOODEN_SWORD, ItemRarity.COMMON, "Iron Battleaxe", null, null, null,
                        new JsonMeta(new Gson().toJson(
                                new EquipableMeta(
                                        List.of(
                                                new Modifier(ModifierType.ATTACK_SLASH, 8),
                                                new Modifier(ModifierType.ATTACK_CRUSH, 9),
                                                new Modifier(ModifierType.STRENGTH_BONUS, 9)
                                        ),
                                        List.of(new SkillRequirement(SkillType.ATTACK, 1))
                                )
                        ), 1)
                ),
                new AbstractMeta(ItemType.GEAR, ItemId.STEEL_BATTLEAXE.asInt(), Material.WOODEN_SWORD, ItemRarity.COMMON, "Steel Battleaxe", null, null, null,
                        new JsonMeta(new Gson().toJson(
                                new EquipableMeta(
                                        List.of(
                                                new Modifier(ModifierType.ATTACK_SLASH, 12),
                                                new Modifier(ModifierType.ATTACK_CRUSH, 13),
                                                new Modifier(ModifierType.STRENGTH_BONUS, 13)
                                        ),
                                        List.of(new SkillRequirement(SkillType.ATTACK, 5))
                                )
                        ), 1)
                ),
                new AbstractMeta(ItemType.GEAR, ItemId.MITHRIL_BATTLEAXE.asInt(), Material.WOODEN_SWORD, ItemRarity.UNCOMMON, "Mithril Battleaxe", null, null, null,
                        new JsonMeta(new Gson().toJson(
                                new EquipableMeta(
                                        List.of(
                                                new Modifier(ModifierType.ATTACK_SLASH, 18),
                                                new Modifier(ModifierType.ATTACK_CRUSH, 19),
                                                new Modifier(ModifierType.STRENGTH_BONUS, 20)
                                        ),
                                        List.of(new SkillRequirement(SkillType.ATTACK, 20))
                                )
                        ), 1)
                ),
                new AbstractMeta(ItemType.GEAR, ItemId.ADAMANTITE_BATTLEAXE.asInt(), Material.WOODEN_SWORD, ItemRarity.UNCOMMON, "Adamantite Battleaxe", null, null, null,
                        new JsonMeta(new Gson().toJson(
                                new EquipableMeta(
                                        List.of(
                                                new Modifier(ModifierType.ATTACK_SLASH, 23),
                                                new Modifier(ModifierType.ATTACK_CRUSH, 24),
                                                new Modifier(ModifierType.STRENGTH_BONUS, 25)
                                        ),
                                        List.of(new SkillRequirement(SkillType.ATTACK, 30))
                                )
                        ), 1)
                ),
                new AbstractMeta(ItemType.GEAR, ItemId.RUNITE_BATTLEAXE.asInt(), Material.WOODEN_SWORD, ItemRarity.RARE, "Runite Battleaxe", null, null, null,
                        new JsonMeta(new Gson().toJson(
                                new EquipableMeta(
                                        List.of(
                                                new Modifier(ModifierType.ATTACK_SLASH, 32),
                                                new Modifier(ModifierType.ATTACK_CRUSH, 33),
                                                new Modifier(ModifierType.STRENGTH_BONUS, 34)
                                        ),
                                        List.of(new SkillRequirement(SkillType.ATTACK, 40))
                                )
                        ), 1)
                )



                /*new AbstractMeta(ItemType.CONSUMABLE, ItemId.RUNITE_BAR.asInt(), Material.PLAYER_HEAD, ItemRarity.RARE, "Consumable Runite Bar", null, HeadTexture.IRON_HELM.asBase64(), new JsonMeta(
                        new Gson().toJson(new ConsumableMeta(5, 3)),
                        1))*/
        );
    }
}
