package org.zuma.examplePlugin.domain.item.persistence;

import com.google.gson.Gson;
import org.bukkit.Material;
import org.zuma.examplePlugin.core.meta.JsonMeta;
import org.zuma.examplePlugin.domain.item.SkillRequirement;
import org.zuma.examplePlugin.domain.item.constants.ItemId;
import org.zuma.examplePlugin.domain.item.constants.ItemRarity;
import org.zuma.examplePlugin.domain.item.constants.ItemType;
import org.zuma.examplePlugin.domain.item.meta.AbstractMeta;
import org.zuma.examplePlugin.domain.item.meta.ConsumableMeta;
import org.zuma.examplePlugin.domain.item.meta.EquipableMeta;
import org.zuma.examplePlugin.domain.modifier.Modifier;
import org.zuma.examplePlugin.domain.modifier.constants.ModifierType;
import org.zuma.examplePlugin.domain.skill.constants.SkillType;

import java.util.List;

public class ItemMetaRepository {

    public List<AbstractMeta> getAll() {
        EquipableMeta pickaxeReqs = new EquipableMeta(List.of(new Modifier(ModifierType.MINING_BOOST, 1)), List.of(new SkillRequirement(SkillType.MINING, 1)));
        JsonMeta empty = new JsonMeta("", 1);

        return List.of(
                new AbstractMeta(ItemType.GEAR, ItemId.BRONZE_PICKAXE.asInt(), Material.WOODEN_PICKAXE, ItemRarity.COMMON, "Bronze Pickaxe", null, new JsonMeta(new Gson().toJson(pickaxeReqs), 1)),

                new AbstractMeta(ItemType.MATERIAL, ItemId.BRONZE_ORE.asInt(), Material.COAL, ItemRarity.COMMON, "Bronze Ore", null, empty),
                new AbstractMeta(ItemType.MATERIAL, ItemId.IRON_ORE.asInt(), Material.COAL, ItemRarity.COMMON, "Iron Ore", null, empty),
                new AbstractMeta(ItemType.MATERIAL, ItemId.STEEL_ORE.asInt(), Material.COAL, ItemRarity.COMMON, "Steel Ore", null, empty),
                new AbstractMeta(ItemType.MATERIAL, ItemId.MITHRIL_ORE.asInt(), Material.COAL, ItemRarity.UNCOMMON, "Mithril Ore", null, empty),
                new AbstractMeta(ItemType.MATERIAL, ItemId.ADAMANTITE_ORE.asInt(), Material.COAL, ItemRarity.UNCOMMON, "Adamantite Ore", null, empty),
                new AbstractMeta(ItemType.MATERIAL, ItemId.RUNITE_ORE.asInt(), Material.COAL, ItemRarity.RARE, "Runite Ore", null, empty),

                new AbstractMeta(ItemType.MATERIAL, ItemId.BRONZE_BAR.asInt(), Material.COAL, ItemRarity.COMMON, "Bronze Bar", null, empty),
                new AbstractMeta(ItemType.MATERIAL, ItemId.IRON_BAR.asInt(), Material.COAL, ItemRarity.COMMON, "Iron Bar", null, empty),
                new AbstractMeta(ItemType.MATERIAL, ItemId.STEEL_BAR.asInt(), Material.COAL, ItemRarity.COMMON, "Steel Bar", null, empty),
                new AbstractMeta(ItemType.MATERIAL, ItemId.MITHRIL_BAR.asInt(), Material.COAL, ItemRarity.UNCOMMON, "Mithril Bar", null, empty),
                new AbstractMeta(ItemType.MATERIAL, ItemId.ADAMANTITE_BAR.asInt(), Material.COAL, ItemRarity.UNCOMMON, "Adamantite Bar", null, empty),
                // new CustomItemMeta(ItemType.MATERIAL, ItemId.RUNITE_BAR.asInt(), Material.COAL, ItemRarity.RARE, "Runite Bar", null, "")
                new AbstractMeta(ItemType.CONSUMABLE, ItemId.RUNITE_BAR.asInt(), Material.COAL, ItemRarity.RARE, "Consumable Runite Bar", null, new JsonMeta(
                        new Gson().toJson(new ConsumableMeta(5, 3)),
                        1))
        );
    }
}
