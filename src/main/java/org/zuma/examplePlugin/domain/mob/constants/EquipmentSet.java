package org.zuma.examplePlugin.domain.mob.constants;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.domain.item.base.AbstractItemType;
import org.zuma.examplePlugin.domain.item.constants.ItemId;
import org.zuma.examplePlugin.domain.mob.EquipmentSetRecord;
import org.zuma.examplePlugin.domain.mob.meta.MobEquipmentMeta;

import java.util.List;

public enum EquipmentSet {
    FUSION_MASTER(new MobEquipmentMeta(List.of(
            new EquipmentSetRecord(EquipmentSlot.HAND, getRuneItem(ItemId.OBSIDIAN_BULWARK), 100)
    ))),

    RUNE_SET_BATTLEAXE(new MobEquipmentMeta(List.of(
            new EquipmentSetRecord(EquipmentSlot.HEAD, getRuneItem(ItemId.RUNE_FULLHELM), 100),
            new EquipmentSetRecord(EquipmentSlot.CHEST, getRuneItem(ItemId.RUNITE_BODY), 100),
            new EquipmentSetRecord(EquipmentSlot.LEGS, getRuneItem(ItemId.RUNITE_PLATELEGS), 100),
            new EquipmentSetRecord(EquipmentSlot.FEET, getRuneItem(ItemId.RUNITE_BOOTS), 100),
            new EquipmentSetRecord(EquipmentSlot.HAND, getRuneItem(ItemId.RUNITE_BATTLEAXE), 100)
    ))),

    SHOPKEEPER_1(new MobEquipmentMeta(List.of(
            new EquipmentSetRecord(EquipmentSlot.HAND, new ItemStack(Material.PAPER), 100)
    ))),

    BLACKSMITH(new MobEquipmentMeta(List.of(
            new EquipmentSetRecord(EquipmentSlot.HAND, getRuneItem(ItemId.SMITHING_HAMMER), 100)
    ))),

    PICKAXES(new MobEquipmentMeta(List.of(
            new EquipmentSetRecord(EquipmentSlot.HAND, getRuneItem(ItemId.IRON_PICKAXE), 80),
            new EquipmentSetRecord(EquipmentSlot.HAND, getRuneItem(ItemId.STEEL_PICKAXE), 30),
            new EquipmentSetRecord(EquipmentSlot.HAND, getRuneItem(ItemId.MITHRIL_PICKAXE), 20)

    ))),

    AXES(new MobEquipmentMeta(List.of(
            new EquipmentSetRecord(EquipmentSlot.HAND, getRuneItem(ItemId.IRON_AXE), 80),
            new EquipmentSetRecord(EquipmentSlot.HAND, getRuneItem(ItemId.STEEL_AXE), 30),
            new EquipmentSetRecord(EquipmentSlot.HAND, getRuneItem(ItemId.MITHRIL_AXE), 20)

    ))),

    FISHERMAN(new MobEquipmentMeta(List.of(
            new EquipmentSetRecord(EquipmentSlot.HAND, new ItemStack(Material.FISHING_ROD), 50),
            new EquipmentSetRecord(EquipmentSlot.HAND, getRuneItem(ItemId.RAW_SALMON), 50)

    ))),

    RUNE_HALF_SET_SWORD(new MobEquipmentMeta(List.of(
            new EquipmentSetRecord(EquipmentSlot.CHEST, getRuneItem(ItemId.RUNITE_BODY), 100),
            new EquipmentSetRecord(EquipmentSlot.LEGS, getRuneItem(ItemId.RUNITE_PLATELEGS), 100),
            new EquipmentSetRecord(EquipmentSlot.FEET, getRuneItem(ItemId.RUNITE_BOOTS), 100),
            new EquipmentSetRecord(EquipmentSlot.HAND, getRuneItem(ItemId.RUNITE_SWORD), 100)
    ))),

    MITHRIL_HALF_SET_SWORD(new MobEquipmentMeta(List.of(
            new EquipmentSetRecord(EquipmentSlot.CHEST, getRuneItem(ItemId.MITHRIL_BODY), 100),
            new EquipmentSetRecord(EquipmentSlot.LEGS, getRuneItem(ItemId.MITHRIL_PLATELEGS), 100),
            new EquipmentSetRecord(EquipmentSlot.FEET, getRuneItem(ItemId.MITHRIL_BOOTS), 100),
            new EquipmentSetRecord(EquipmentSlot.HAND, getRuneItem(ItemId.MITHRIL_SWORD), 100)
    ))),

    ADAMANT_HALF_SET_SWORD(new MobEquipmentMeta(List.of(
            new EquipmentSetRecord(EquipmentSlot.CHEST, getRuneItem(ItemId.ADAMANTITE_BODY), 100),
            new EquipmentSetRecord(EquipmentSlot.LEGS, getRuneItem(ItemId.ADAMANTITE_PLATELEGS), 100),
            new EquipmentSetRecord(EquipmentSlot.FEET, getRuneItem(ItemId.ADAMANTITE_BOOTS), 100),
            new EquipmentSetRecord(EquipmentSlot.HAND, getRuneItem(ItemId.ADAMANTITE_SWORD), 100)
    ))),


    JEWELER(new MobEquipmentMeta(List.of(
            new EquipmentSetRecord(EquipmentSlot.HAND, getRuneItem(ItemId.SAPPHIRE), 20),
            new EquipmentSetRecord(EquipmentSlot.HAND, getRuneItem(ItemId.EMERALD), 20),
            new EquipmentSetRecord(EquipmentSlot.HAND, getRuneItem(ItemId.RUBY), 20),
            new EquipmentSetRecord(EquipmentSlot.HAND, getRuneItem(ItemId.DIAMOND), 20),
            new EquipmentSetRecord(EquipmentSlot.OFFHAND, getRuneItem(ItemId.COINS, 100), 80)
    ))),

    INVESTMENT_BANKER(new MobEquipmentMeta(List.of(
            new EquipmentSetRecord(EquipmentSlot.HAND, new ItemStack(Material.GOLD_INGOT), 10),
            new EquipmentSetRecord(EquipmentSlot.HAND, new ItemStack(Material.GOLD_BLOCK), 10)
    ))),

    BANK_TELLER(new MobEquipmentMeta(List.of(
            new EquipmentSetRecord(EquipmentSlot.HAND, getRuneItem(ItemId.COINS, 10000), 1),
            new EquipmentSetRecord(EquipmentSlot.HAND, getRuneItem(ItemId.COINS, 1000), 5),
            new EquipmentSetRecord(EquipmentSlot.HAND, getRuneItem(ItemId.COINS, 100), 20),
            new EquipmentSetRecord(EquipmentSlot.HAND, getRuneItem(ItemId.COINS, 1), 30)
    ))),

    AUCTIONEER(new MobEquipmentMeta(List.of(
            new EquipmentSetRecord(EquipmentSlot.HAND, getRuneItem(ItemId.COINS, 10000), 1),
            new EquipmentSetRecord(EquipmentSlot.HAND, getRuneItem(ItemId.COINS, 1000), 5),
            new EquipmentSetRecord(EquipmentSlot.HAND, getRuneItem(ItemId.COINS, 100), 20),
            new EquipmentSetRecord(EquipmentSlot.HAND, getRuneItem(ItemId.COINS, 1), 30)
    ))),

    SHEPHERD(new MobEquipmentMeta(List.of(
            new EquipmentSetRecord(EquipmentSlot.HAND, getRuneItem(ItemId.SHEARS, 1), 100)
    ))),

    RUNE_FULL_CROSSBOW(new MobEquipmentMeta(List.of(
            new EquipmentSetRecord(EquipmentSlot.HAND, getRuneItem(ItemId.RUNITE_CROSSBOW, 1), 100),
            new EquipmentSetRecord(EquipmentSlot.CHEST, getRuneItem(ItemId.RUNITE_BODY, 1), 100),
            new EquipmentSetRecord(EquipmentSlot.LEGS, getRuneItem(ItemId.RUNITE_PLATELEGS, 1), 100)
    ))),

    RUNE_FULL_IRON_CROSSBOW(new MobEquipmentMeta(List.of(
            new EquipmentSetRecord(EquipmentSlot.HAND, getRuneItem(ItemId.IRON_CROSSBOW, 1), 100),
            new EquipmentSetRecord(EquipmentSlot.CHEST, getRuneItem(ItemId.RUNITE_BODY, 1), 100),
            new EquipmentSetRecord(EquipmentSlot.LEGS, getRuneItem(ItemId.RUNITE_PLATELEGS, 1), 100)
    ))),

    BRONZE_PICKAXE(new MobEquipmentMeta(List.of(
            new EquipmentSetRecord(EquipmentSlot.HAND, getRuneItem(ItemId.BRONZE_PICKAXE, 1), 100)
    ))),

    BRONZE_AXE(new MobEquipmentMeta(List.of(
            new EquipmentSetRecord(EquipmentSlot.HAND, getRuneItem(ItemId.BRONZE_AXE, 1), 100)
    ))),

    HORSE_DEALER(new MobEquipmentMeta(List.of(
            new EquipmentSetRecord(EquipmentSlot.HAND, new ItemStack(Material.SUGAR), 25),
            new EquipmentSetRecord(EquipmentSlot.HAND, new ItemStack(Material.APPLE), 25),
            new EquipmentSetRecord(EquipmentSlot.HAND, new ItemStack(Material.WHEAT), 25),
            new EquipmentSetRecord(EquipmentSlot.HAND, new ItemStack(Material.HAY_BLOCK), 25)
    ))),

    GOBLIN(new MobEquipmentMeta(List.of(
            new EquipmentSetRecord(EquipmentSlot.LEGS, getRuneItem(ItemId.IRON_BODY), 5),
            new EquipmentSetRecord(EquipmentSlot.FEET, getRuneItem(ItemId.BRONZE_BOOTS), 30),
            new EquipmentSetRecord(EquipmentSlot.HAND, new ItemStack(Material.STICK), 20)
    )));


    private static ItemStack getRuneItem(ItemId id) {
        return getRuneItem(id, 1);
    }

    private static ItemStack getRuneItem(ItemId id, int amount) {
        AbstractItemType type = RuneQuestPlugin.getItemRegistry().getType(id);

        if (type == null) return new ItemStack(Material.AIR);
        return type.createItemStackOfType(amount);
    }

    private final MobEquipmentMeta meta;

    EquipmentSet(MobEquipmentMeta meta) {
        this.meta = meta;
    }

    public MobEquipmentMeta asMobEquipmentMeta() {
        return this.meta;
    }
}
