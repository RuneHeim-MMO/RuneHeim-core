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
        EquipableMeta pickaxeReqs = new EquipableMeta(List.of(new Modifier(ModifierType.MINING_BOOST, 1)), List.of(new SkillRequirement(SkillType.MINING, 1)));
        JsonMeta empty = new JsonMeta("", 1);
        JsonMeta emptyArmorReqs = new JsonMeta(new Gson().toJson(new EquipableMeta(new ArrayList<>(), new ArrayList<>())), 1);

        return List.of(
                new AbstractMeta(ItemType.GEAR, ItemId.BRONZE_PICKAXE.asInt(), Material.WOODEN_PICKAXE, ItemRarity.COMMON, "Bronze Pickaxe", null, null, null, new JsonMeta(new Gson().toJson(pickaxeReqs), 1)),

                new AbstractMeta(ItemType.MATERIAL, ItemId.BRONZE_ORE.asInt(), Material.COAL, ItemRarity.COMMON, "Bronze Ore", null, null, null, empty),
                new AbstractMeta(ItemType.MATERIAL, ItemId.IRON_ORE.asInt(), Material.DIAMOND_SWORD, ItemRarity.COMMON, "Iron Ore", null, null, null, empty),
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

                new AbstractMeta(ItemType.GEAR, ItemId.BRONZE_HELMET.asInt(), Material.PLAYER_HEAD, ItemRarity.COMMON, "Bronze Helmet", null, HeadTexture.BRONZE_HELM.asBase64(), Color.fromRGB(210, 170, 120), emptyArmorReqs),
                new AbstractMeta(ItemType.GEAR, ItemId.IRON_HELMET.asInt(), Material.PLAYER_HEAD, ItemRarity.COMMON, "Iron Helmet", null, HeadTexture.IRON_HELM.asBase64(), null, emptyArmorReqs),
                new AbstractMeta(ItemType.GEAR, ItemId.STEEL_HELMET.asInt(), Material.PLAYER_HEAD, ItemRarity.COMMON, "Steel Helmet", null, HeadTexture.STEEL_HELM.asBase64(), null, emptyArmorReqs),
                new AbstractMeta(ItemType.GEAR, ItemId.MITHRIL_HELMET.asInt(), Material.PLAYER_HEAD, ItemRarity.UNCOMMON, "Mithril Helmet", null, HeadTexture.MITHRIL_HELM.asBase64(), null, emptyArmorReqs),
                new AbstractMeta(ItemType.GEAR, ItemId.ADAMANTITE_HELMET.asInt(), Material.PLAYER_HEAD, ItemRarity.UNCOMMON, "Adamantite Helmet", null, HeadTexture.ADAMANT_HELM.asBase64(), null, emptyArmorReqs),
                new AbstractMeta(ItemType.GEAR, ItemId.RUNITE_HELMET.asInt(), Material.PLAYER_HEAD, ItemRarity.RARE, "Runite Helmet", null, HeadTexture.RUNE_HELM.asBase64(), null, emptyArmorReqs),

                new AbstractMeta(ItemType.GEAR, ItemId.BRONZE_BODY.asInt(), Material.LEATHER_CHESTPLATE, ItemRarity.COMMON, "Bronze Body", null, null, Color.fromRGB(210, 170, 120), emptyArmorReqs),
                new AbstractMeta(ItemType.GEAR, ItemId.IRON_BODY.asInt(), Material.CHAINMAIL_CHESTPLATE, ItemRarity.COMMON, "Iron Body", null, null, null, emptyArmorReqs),
                new AbstractMeta(ItemType.GEAR, ItemId.STEEL_BODY.asInt(), Material.IRON_CHESTPLATE, ItemRarity.COMMON, "Steel Body", null, null, null, emptyArmorReqs),
                new AbstractMeta(ItemType.GEAR, ItemId.MITHRIL_BODY.asInt(), Material.GOLDEN_CHESTPLATE, ItemRarity.UNCOMMON, "Mithril Body", null, null, null, emptyArmorReqs),
                new AbstractMeta(ItemType.GEAR, ItemId.ADAMANTITE_BODY.asInt(), Material.DIAMOND_CHESTPLATE, ItemRarity.UNCOMMON, "Adamantite Body", null, null, null, emptyArmorReqs),
                new AbstractMeta(ItemType.GEAR, ItemId.RUNITE_BODY.asInt(), Material.NETHERITE_CHESTPLATE, ItemRarity.RARE, "Runite Body", null, null, null, emptyArmorReqs),

                new AbstractMeta(ItemType.GEAR, ItemId.BRONZE_PLATELEGS.asInt(), Material.LEATHER_LEGGINGS, ItemRarity.COMMON, "Bronze Platelegs", null, null, Color.fromRGB(210, 170, 120), emptyArmorReqs),
                new AbstractMeta(ItemType.GEAR, ItemId.IRON_PLATELEGS.asInt(), Material.CHAINMAIL_LEGGINGS, ItemRarity.COMMON, "Iron Platelegs", null, null, null, emptyArmorReqs),
                new AbstractMeta(ItemType.GEAR, ItemId.STEEL_PLATELEGS.asInt(), Material.IRON_LEGGINGS, ItemRarity.COMMON, "Steel Platelegs", null, null, null, emptyArmorReqs),
                new AbstractMeta(ItemType.GEAR, ItemId.MITHRIL_PLATELEGS.asInt(), Material.GOLDEN_LEGGINGS, ItemRarity.UNCOMMON, "Mithril Platelegs", null, null, null, emptyArmorReqs),
                new AbstractMeta(ItemType.GEAR, ItemId.ADAMANTITE_PLATELEGS.asInt(), Material.DIAMOND_LEGGINGS, ItemRarity.UNCOMMON, "Adamantite Platelegs", null, null, null, emptyArmorReqs),
                new AbstractMeta(ItemType.GEAR, ItemId.RUNITE_PLATELEGS.asInt(), Material.NETHERITE_LEGGINGS, ItemRarity.RARE, "Runite Platelegs", null, null, null, emptyArmorReqs),

                new AbstractMeta(ItemType.GEAR, ItemId.BRONZE_BOOTS.asInt(), Material.LEATHER_BOOTS, ItemRarity.COMMON, "Bronze Boots", null, null, Color.fromRGB(210, 170, 120), emptyArmorReqs),
                new AbstractMeta(ItemType.GEAR, ItemId.IRON_BOOTS.asInt(), Material.CHAINMAIL_BOOTS, ItemRarity.COMMON, "Iron Boots", null, null, null, emptyArmorReqs),
                new AbstractMeta(ItemType.GEAR, ItemId.STEEL_BOOTS.asInt(), Material.IRON_BOOTS, ItemRarity.COMMON, "Steel Boots", null, null, null, emptyArmorReqs),
                new AbstractMeta(ItemType.GEAR, ItemId.MITHRIL_BOOTS.asInt(), Material.GOLDEN_BOOTS, ItemRarity.UNCOMMON, "Mithril Boots", null, null, null, emptyArmorReqs),
                new AbstractMeta(ItemType.GEAR, ItemId.ADAMANTITE_BOOTS.asInt(), Material.DIAMOND_BOOTS, ItemRarity.UNCOMMON, "Adamantite Boots", null, null, null, emptyArmorReqs),
                new AbstractMeta(ItemType.GEAR, ItemId.RUNITE_BOOTS.asInt(), Material.NETHERITE_BOOTS, ItemRarity.RARE, "Runite Boots", null, null, null, emptyArmorReqs),

                new AbstractMeta(ItemType.GEAR, ItemId.BRONZE_SWORD.asInt(), Material.WOODEN_SWORD, ItemRarity.COMMON, "Bronze Sword", null, null, null, emptyArmorReqs),
                new AbstractMeta(ItemType.GEAR, ItemId.IRON_SWORD.asInt(), Material.WOODEN_SWORD, ItemRarity.COMMON, "Iron Sword", null, null, null, emptyArmorReqs),
                new AbstractMeta(ItemType.GEAR, ItemId.STEEL_SWORD.asInt(), Material.WOODEN_SWORD, ItemRarity.COMMON, "Steel Sword", null, null, null, emptyArmorReqs),
                new AbstractMeta(ItemType.GEAR, ItemId.MITHRIL_SWORD.asInt(), Material.WOODEN_SWORD, ItemRarity.UNCOMMON, "Mithril Sword", null, null, null, emptyArmorReqs),
                new AbstractMeta(ItemType.GEAR, ItemId.ADAMANTITE_SWORD.asInt(), Material.WOODEN_SWORD, ItemRarity.UNCOMMON, "Adamantite Sword", null, null, null, emptyArmorReqs),
                new AbstractMeta(ItemType.GEAR, ItemId.RUNITE_SWORD.asInt(), Material.WOODEN_SWORD, ItemRarity.RARE, "Runite Sword", null, null, null, emptyArmorReqs)


                /*new AbstractMeta(ItemType.CONSUMABLE, ItemId.RUNITE_BAR.asInt(), Material.PLAYER_HEAD, ItemRarity.RARE, "Consumable Runite Bar", null, HeadTexture.IRON_HELM.asBase64(), new JsonMeta(
                        new Gson().toJson(new ConsumableMeta(5, 3)),
                        1))*/
        );
    }
}
