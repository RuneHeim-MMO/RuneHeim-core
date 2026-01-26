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
    STEEL_FULL_SWORD(new MobEquipmentMeta(List.of(
            new EquipmentSetRecord(EquipmentSlot.HEAD, getRuneItem(ItemId.STEEL_FULLHELM), 100),
            new EquipmentSetRecord(EquipmentSlot.CHEST, getRuneItem(ItemId.STEEL_BODY), 100),
            new EquipmentSetRecord(EquipmentSlot.LEGS, getRuneItem(ItemId.STEEL_PLATELEGS), 100),
            new EquipmentSetRecord(EquipmentSlot.FEET, getRuneItem(ItemId.STEEL_BOOTS), 100),
            new EquipmentSetRecord(EquipmentSlot.HAND, getRuneItem(ItemId.STEEL_SWORD), 100)
    ))),

    MITHRIL_FULL_SWORD(new MobEquipmentMeta(List.of(
            new EquipmentSetRecord(EquipmentSlot.HEAD, getRuneItem(ItemId.MITHRIL_FULLHELM), 100),
            new EquipmentSetRecord(EquipmentSlot.CHEST, getRuneItem(ItemId.MITHRIL_BODY), 100),
            new EquipmentSetRecord(EquipmentSlot.LEGS, getRuneItem(ItemId.MITHRIL_PLATELEGS), 100),
            new EquipmentSetRecord(EquipmentSlot.FEET, getRuneItem(ItemId.MITHRIL_BOOTS), 100),
            new EquipmentSetRecord(EquipmentSlot.HAND, getRuneItem(ItemId.MITHRIL_SWORD), 100)
    ))),


    ADAMANT_FULL_SWORD(new MobEquipmentMeta(List.of(
            new EquipmentSetRecord(EquipmentSlot.HEAD, getRuneItem(ItemId.ADAMANT_FULLHELM), 100),
            new EquipmentSetRecord(EquipmentSlot.CHEST, getRuneItem(ItemId.ADAMANT_BODY), 100),
            new EquipmentSetRecord(EquipmentSlot.LEGS, getRuneItem(ItemId.ADAMANT_PLATELEGS), 100),
            new EquipmentSetRecord(EquipmentSlot.FEET, getRuneItem(ItemId.ADAMANT_BOOTS), 100),
            new EquipmentSetRecord(EquipmentSlot.HAND, getRuneItem(ItemId.ADAMANT_SWORD), 100)
    ))),


    RUNE_FULL_SWORD(new MobEquipmentMeta(List.of(
            new EquipmentSetRecord(EquipmentSlot.HEAD, getRuneItem(ItemId.RUNE_FULLHELM), 100),
            new EquipmentSetRecord(EquipmentSlot.CHEST, getRuneItem(ItemId.RUNE_BODY), 100),
            new EquipmentSetRecord(EquipmentSlot.LEGS, getRuneItem(ItemId.RUNE_PLATELEGS), 100),
            new EquipmentSetRecord(EquipmentSlot.FEET, getRuneItem(ItemId.RUNE_BOOTS), 100),
            new EquipmentSetRecord(EquipmentSlot.HAND, getRuneItem(ItemId.RUNE_SWORD), 100)
    ))),


    FUSION_MASTER(new MobEquipmentMeta(List.of(
            new EquipmentSetRecord(EquipmentSlot.HAND, getRuneItem(ItemId.OBSIDIAN_BULWARK), 100)
    ))),

    RUNE_SET_BATTLEAXE(new MobEquipmentMeta(List.of(
            new EquipmentSetRecord(EquipmentSlot.HEAD, getRuneItem(ItemId.RUNE_FULLHELM), 100),
            new EquipmentSetRecord(EquipmentSlot.CHEST, getRuneItem(ItemId.RUNE_BODY), 100),
            new EquipmentSetRecord(EquipmentSlot.LEGS, getRuneItem(ItemId.RUNE_PLATELEGS), 100),
            new EquipmentSetRecord(EquipmentSlot.FEET, getRuneItem(ItemId.RUNE_BOOTS), 100),
            new EquipmentSetRecord(EquipmentSlot.HAND, getRuneItem(ItemId.RUNE_BATTLEAXE), 100)
    ))),

    PAPER_IN_HAND(new MobEquipmentMeta(List.of(
            new EquipmentSetRecord(EquipmentSlot.HAND, new ItemStack(Material.PAPER), 100)
    ))),

    RUNE_SWORD(new MobEquipmentMeta(List.of(
            new EquipmentSetRecord(EquipmentSlot.HAND, getRuneItem(ItemId.RUNE_SWORD), 100)
    ))),

    MORVANE_PRIEST(new MobEquipmentMeta(List.of(
            new EquipmentSetRecord(EquipmentSlot.HAND, getRuneItem(ItemId.ADAMANT_DAGGER), 100),
            new EquipmentSetRecord(EquipmentSlot.CHEST, getRuneItem(ItemId.MONK_ROBE_TOP), 100),
            new EquipmentSetRecord(EquipmentSlot.LEGS, getRuneItem(ItemId.MONK_ROBE_BOTTOM), 100)
    ))),

    MUSHROOM_STEW(new MobEquipmentMeta(List.of(
            new EquipmentSetRecord(EquipmentSlot.HAND, new ItemStack(Material.MUSHROOM_STEW), 100)
    ))),

    COMPASS(new MobEquipmentMeta(List.of(
            new EquipmentSetRecord(EquipmentSlot.HAND, new ItemStack(Material.COMPASS), 100)
    ))),

    BLACKSMITH(new MobEquipmentMeta(List.of(
            new EquipmentSetRecord(EquipmentSlot.HAND, getRuneItem(ItemId.SMITHING_HAMMER), 100)
    ))),

    TANNER(new MobEquipmentMeta(List.of(
            new EquipmentSetRecord(EquipmentSlot.HAND, getRuneItem(ItemId.DRAGON_LEATHER), 100)
    ))),

    PICKAXES(new MobEquipmentMeta(List.of(
            new EquipmentSetRecord(EquipmentSlot.HAND, getRuneItem(ItemId.IRON_PICKAXE), 80),
            new EquipmentSetRecord(EquipmentSlot.HAND, getRuneItem(ItemId.STEEL_PICKAXE), 30),
            new EquipmentSetRecord(EquipmentSlot.HAND, getRuneItem(ItemId.MITHRIL_PICKAXE), 20)
    ))),

    COAL(new MobEquipmentMeta(List.of(
            new EquipmentSetRecord(EquipmentSlot.HAND, getRuneItem(ItemId.COAL_ORE), 100)
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
            new EquipmentSetRecord(EquipmentSlot.CHEST, getRuneItem(ItemId.RUNE_BODY), 100),
            new EquipmentSetRecord(EquipmentSlot.LEGS, getRuneItem(ItemId.RUNE_PLATELEGS), 100),
            new EquipmentSetRecord(EquipmentSlot.FEET, getRuneItem(ItemId.RUNE_BOOTS), 100),
            new EquipmentSetRecord(EquipmentSlot.HAND, getRuneItem(ItemId.RUNE_SWORD), 100)
    ))),

    RUNE_CHAIN_AND_SWORD(new MobEquipmentMeta(List.of(
            new EquipmentSetRecord(EquipmentSlot.CHEST, getRuneItem(ItemId.RUNE_CHAINBODY), 100),
            new EquipmentSetRecord(EquipmentSlot.HAND, getRuneItem(ItemId.RUNE_SWORD), 100)
    ))),

    MITHRIL_CHAIN_HALF_SET_SWORD(new MobEquipmentMeta(List.of(
            new EquipmentSetRecord(EquipmentSlot.CHEST, getRuneItem(ItemId.MITHRIL_CHAINBODY), 100),
            new EquipmentSetRecord(EquipmentSlot.LEGS, getRuneItem(ItemId.MITHRIL_PLATELEGS), 100),
            new EquipmentSetRecord(EquipmentSlot.FEET, getRuneItem(ItemId.MITHRIL_BOOTS), 100),
            new EquipmentSetRecord(EquipmentSlot.HAND, getRuneItem(ItemId.MITHRIL_SWORD), 100)
    ))),

    MITHRIL_CHAIN_HALF_SET_HAMMER(new MobEquipmentMeta(List.of(
            new EquipmentSetRecord(EquipmentSlot.CHEST, getRuneItem(ItemId.MITHRIL_CHAINBODY), 100),
            new EquipmentSetRecord(EquipmentSlot.LEGS, getRuneItem(ItemId.MITHRIL_PLATELEGS), 100),
            new EquipmentSetRecord(EquipmentSlot.FEET, getRuneItem(ItemId.MITHRIL_BOOTS), 100),
            new EquipmentSetRecord(EquipmentSlot.HAND, getRuneItem(ItemId.SMITHING_HAMMER), 100)
    ))),

    ADAMANT_CHAIN_HALF_SET_SWORD(new MobEquipmentMeta(List.of(
            new EquipmentSetRecord(EquipmentSlot.CHEST, getRuneItem(ItemId.ADAMANT_CHAINBODY), 100),
            new EquipmentSetRecord(EquipmentSlot.LEGS, getRuneItem(ItemId.ADAMANT_PLATELEGS), 100),
            new EquipmentSetRecord(EquipmentSlot.FEET, getRuneItem(ItemId.ADAMANT_BOOTS), 100),
            new EquipmentSetRecord(EquipmentSlot.HAND, getRuneItem(ItemId.ADAMANT_SWORD), 100)
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

    COLLECTOR_OF_DAYS(new MobEquipmentMeta(List.of(
            new EquipmentSetRecord(EquipmentSlot.HAND, getRuneItem(ItemId.ARMADILLO_HOOKBLADE), 100),
            new EquipmentSetRecord(EquipmentSlot.OFFHAND, new ItemStack(Material.SHIELD), 100)
    ))),

    SUNDERING_HAND_WARRIOR(new MobEquipmentMeta(List.of(
            new EquipmentSetRecord(EquipmentSlot.OFFHAND, new ItemStack(Material.SHIELD), 100),
            new EquipmentSetRecord(EquipmentSlot.CHEST, getRuneItem(ItemId.RUNE_CHAINBODY), 100),
            new EquipmentSetRecord(EquipmentSlot.LEGS, getRuneItem(ItemId.RUNE_PLATELEGS), 100),
            new EquipmentSetRecord(EquipmentSlot.HAND, getRuneItem(ItemId.RUNE_SWORD), 100)
    ))),

    AUCTIONEER(new MobEquipmentMeta(List.of(
            new EquipmentSetRecord(EquipmentSlot.HAND, getRuneItem(ItemId.COINS, 10000), 1),
            new EquipmentSetRecord(EquipmentSlot.HAND, getRuneItem(ItemId.COINS, 1000), 5),
            new EquipmentSetRecord(EquipmentSlot.HAND, getRuneItem(ItemId.COINS, 100), 20),
            new EquipmentSetRecord(EquipmentSlot.HAND, getRuneItem(ItemId.COINS, 1), 30)
    ))),

    SHEPHERD(new MobEquipmentMeta(List.of(
            new EquipmentSetRecord(EquipmentSlot.HAND, getRuneItem(ItemId.SHEARS), 100)
    ))),

    DOCTOR_BRAM(new MobEquipmentMeta(List.of(
            new EquipmentSetRecord(EquipmentSlot.HAND, getRuneItem(ItemId.NEEDLE), 100)
    ))),

    RUNE_FULL_CROSSBOW(new MobEquipmentMeta(List.of(
            new EquipmentSetRecord(EquipmentSlot.HAND, getRuneItem(ItemId.RUNE_CROSSBOW), 100),
            new EquipmentSetRecord(EquipmentSlot.CHEST, getRuneItem(ItemId.RUNE_BODY), 100),
            new EquipmentSetRecord(EquipmentSlot.LEGS, getRuneItem(ItemId.RUNE_PLATELEGS), 100)
    ))),

    RUNE_FULL_IRON_CROSSBOW(new MobEquipmentMeta(List.of(
            new EquipmentSetRecord(EquipmentSlot.HAND, getRuneItem(ItemId.IRON_CROSSBOW), 100),
            new EquipmentSetRecord(EquipmentSlot.CHEST, getRuneItem(ItemId.RUNE_BODY), 100),
            new EquipmentSetRecord(EquipmentSlot.LEGS, getRuneItem(ItemId.RUNE_PLATELEGS), 100)
    ))),

    BRONZE_PICKAXE(new MobEquipmentMeta(List.of(
            new EquipmentSetRecord(EquipmentSlot.HAND, getRuneItem(ItemId.BRONZE_PICKAXE), 100)
    ))),

    BRONZE_AXE(new MobEquipmentMeta(List.of(
            new EquipmentSetRecord(EquipmentSlot.HAND, getRuneItem(ItemId.BRONZE_AXE), 100)
    ))),

    CARROT(new MobEquipmentMeta(List.of(
            new EquipmentSetRecord(EquipmentSlot.HAND, new ItemStack(Material.CARROT), 100)
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
    ))),


    LOW_LEVEL_RANDOM_GEAR(new MobEquipmentMeta(List.of(
            new EquipmentSetRecord(EquipmentSlot.HEAD, getRuneItem(ItemId.BRONZE_MEDHELM), 10),
            new EquipmentSetRecord(EquipmentSlot.HEAD, getRuneItem(ItemId.IRON_MEDHELM), 10),
            new EquipmentSetRecord(EquipmentSlot.HEAD, getRuneItem(ItemId.STEEL_MEDHELM), 10),
            new EquipmentSetRecord(EquipmentSlot.HEAD, getRuneItem(ItemId.BRONZE_FULLHELM), 10),
            new EquipmentSetRecord(EquipmentSlot.HEAD, getRuneItem(ItemId.IRON_FULLHELM), 10),
            new EquipmentSetRecord(EquipmentSlot.HEAD, getRuneItem(ItemId.STEEL_FULLHELM), 10),


            new EquipmentSetRecord(EquipmentSlot.CHEST, getRuneItem(ItemId.BRONZE_CHAINBODY), 10),
            new EquipmentSetRecord(EquipmentSlot.CHEST, getRuneItem(ItemId.IRON_CHAINBODY), 10),
            new EquipmentSetRecord(EquipmentSlot.CHEST, getRuneItem(ItemId.STEEL_CHAINBODY), 10),
            new EquipmentSetRecord(EquipmentSlot.CHEST, getRuneItem(ItemId.BRONZE_BODY), 10),
            new EquipmentSetRecord(EquipmentSlot.CHEST, getRuneItem(ItemId.IRON_BODY), 10),
            new EquipmentSetRecord(EquipmentSlot.CHEST, getRuneItem(ItemId.STEEL_BODY), 10),

            new EquipmentSetRecord(EquipmentSlot.LEGS, getRuneItem(ItemId.BRONZE_PLATELEGS), 30),
            new EquipmentSetRecord(EquipmentSlot.LEGS, getRuneItem(ItemId.IRON_PLATELEGS), 30),
            new EquipmentSetRecord(EquipmentSlot.LEGS, getRuneItem(ItemId.STEEL_PLATELEGS), 30),

            new EquipmentSetRecord(EquipmentSlot.FEET, getRuneItem(ItemId.BRONZE_BOOTS), 30),
            new EquipmentSetRecord(EquipmentSlot.FEET, getRuneItem(ItemId.IRON_BOOTS), 30),
            new EquipmentSetRecord(EquipmentSlot.FEET, getRuneItem(ItemId.STEEL_BOOTS), 30),


            new EquipmentSetRecord(EquipmentSlot.HAND, new ItemStack(Material.STICK), 10),
            new EquipmentSetRecord(EquipmentSlot.HAND, getRuneItem(ItemId.BRONZE_BATTLEAXE), 10),
            new EquipmentSetRecord(EquipmentSlot.HAND, getRuneItem(ItemId.BRONZE_DAGGER), 10),
            new EquipmentSetRecord(EquipmentSlot.HAND, getRuneItem(ItemId.BRONZE_SWORD), 10),
            new EquipmentSetRecord(EquipmentSlot.HAND, getRuneItem(ItemId.IRON_BATTLEAXE), 10),
            new EquipmentSetRecord(EquipmentSlot.HAND, getRuneItem(ItemId.IRON_DAGGER), 10),
            new EquipmentSetRecord(EquipmentSlot.HAND, getRuneItem(ItemId.IRON_SWORD), 10),
            new EquipmentSetRecord(EquipmentSlot.HAND, getRuneItem(ItemId.STEEL_BATTLEAXE), 10),
            new EquipmentSetRecord(EquipmentSlot.HAND, getRuneItem(ItemId.STEEL_DAGGER), 10),
            new EquipmentSetRecord(EquipmentSlot.HAND, getRuneItem(ItemId.STEEL_SWORD), 10)
    ))),

    MEDIUM_LEVEL_RANDOM_GEAR(new MobEquipmentMeta(List.of(
            // Head - medhelms (Steel -> Mithril -> Adamant)
            new EquipmentSetRecord(EquipmentSlot.HEAD, getRuneItem(ItemId.STEEL_MEDHELM), 10),
            new EquipmentSetRecord(EquipmentSlot.HEAD, getRuneItem(ItemId.MITHRIL_MEDHELM), 10),
            new EquipmentSetRecord(EquipmentSlot.HEAD, getRuneItem(ItemId.ADAMANT_MEDHELM), 10),

            // Head - fullhelms (Steel -> Mithril -> Adamant)
            new EquipmentSetRecord(EquipmentSlot.HEAD, getRuneItem(ItemId.STEEL_FULLHELM), 10),
            new EquipmentSetRecord(EquipmentSlot.HEAD, getRuneItem(ItemId.MITHRIL_FULLHELM), 10),
            new EquipmentSetRecord(EquipmentSlot.HEAD, getRuneItem(ItemId.ADAMANT_FULLHELM), 10),

            // Chest - chainbodies (Steel -> Mithril -> Adamant)
            new EquipmentSetRecord(EquipmentSlot.CHEST, getRuneItem(ItemId.STEEL_CHAINBODY), 10),
            new EquipmentSetRecord(EquipmentSlot.CHEST, getRuneItem(ItemId.MITHRIL_CHAINBODY), 10),
            new EquipmentSetRecord(EquipmentSlot.CHEST, getRuneItem(ItemId.ADAMANT_CHAINBODY), 10),

            // Chest - bodies (Steel -> Mithril -> Adamant)
            new EquipmentSetRecord(EquipmentSlot.CHEST, getRuneItem(ItemId.STEEL_BODY), 10),
            new EquipmentSetRecord(EquipmentSlot.CHEST, getRuneItem(ItemId.MITHRIL_BODY), 10),
            new EquipmentSetRecord(EquipmentSlot.CHEST, getRuneItem(ItemId.ADAMANT_BODY), 10),

            // Legs - platelegs (Steel -> Mithril -> Adamant)
            new EquipmentSetRecord(EquipmentSlot.LEGS, getRuneItem(ItemId.STEEL_PLATELEGS), 30),
            new EquipmentSetRecord(EquipmentSlot.LEGS, getRuneItem(ItemId.MITHRIL_PLATELEGS), 30),
            new EquipmentSetRecord(EquipmentSlot.LEGS, getRuneItem(ItemId.ADAMANT_PLATELEGS), 30),

            // Feet - boots (Steel -> Mithril -> Adamant)
            new EquipmentSetRecord(EquipmentSlot.FEET, getRuneItem(ItemId.STEEL_BOOTS), 30),
            new EquipmentSetRecord(EquipmentSlot.FEET, getRuneItem(ItemId.MITHRIL_BOOTS), 30),
            new EquipmentSetRecord(EquipmentSlot.FEET, getRuneItem(ItemId.ADAMANT_BOOTS), 30),

            // Hand - battleaxes (Steel -> Mithril -> Adamant)
            new EquipmentSetRecord(EquipmentSlot.HAND, getRuneItem(ItemId.STEEL_BATTLEAXE), 10),
            new EquipmentSetRecord(EquipmentSlot.HAND, getRuneItem(ItemId.MITHRIL_BATTLEAXE), 10),
            new EquipmentSetRecord(EquipmentSlot.HAND, getRuneItem(ItemId.ADAMANT_BATTLEAXE), 10),

            // Hand - daggers (Steel -> Mithril -> Adamant)
            new EquipmentSetRecord(EquipmentSlot.HAND, getRuneItem(ItemId.STEEL_DAGGER), 10),
            new EquipmentSetRecord(EquipmentSlot.HAND, getRuneItem(ItemId.MITHRIL_DAGGER), 10),
            new EquipmentSetRecord(EquipmentSlot.HAND, getRuneItem(ItemId.ADAMANT_DAGGER), 10),

            // Hand - swords (Steel -> Mithril -> Adamant)
            new EquipmentSetRecord(EquipmentSlot.HAND, getRuneItem(ItemId.STEEL_SWORD), 10),
            new EquipmentSetRecord(EquipmentSlot.HAND, getRuneItem(ItemId.MITHRIL_SWORD), 10),
            new EquipmentSetRecord(EquipmentSlot.HAND, getRuneItem(ItemId.ADAMANT_SWORD), 10)
    ))),


    HIGH_LEVEL_RANDOM_GEAR(new MobEquipmentMeta(List.of(
            // Head - medhelms (Adamant -> Rune)
            new EquipmentSetRecord(EquipmentSlot.HEAD, getRuneItem(ItemId.ADAMANT_MEDHELM), 10),
            new EquipmentSetRecord(EquipmentSlot.HEAD, getRuneItem(ItemId.RUNE_MEDHELM), 10),

            // Head - fullhelms (Adamant -> Rune)
            new EquipmentSetRecord(EquipmentSlot.HEAD, getRuneItem(ItemId.ADAMANT_FULLHELM), 10),
            new EquipmentSetRecord(EquipmentSlot.HEAD, getRuneItem(ItemId.RUNE_FULLHELM), 10),

            // Chest - chainbodies (Adamant -> Rune)
            new EquipmentSetRecord(EquipmentSlot.CHEST, getRuneItem(ItemId.ADAMANT_CHAINBODY), 10),
            new EquipmentSetRecord(EquipmentSlot.CHEST, getRuneItem(ItemId.RUNE_CHAINBODY), 10),

            // Chest - bodies (Adamant -> Rune)
            new EquipmentSetRecord(EquipmentSlot.CHEST, getRuneItem(ItemId.ADAMANT_BODY), 10),
            new EquipmentSetRecord(EquipmentSlot.CHEST, getRuneItem(ItemId.RUNE_BODY), 10),

            // Legs - platelegs (Adamant -> Rune)
            new EquipmentSetRecord(EquipmentSlot.LEGS, getRuneItem(ItemId.ADAMANT_PLATELEGS), 30),
            new EquipmentSetRecord(EquipmentSlot.LEGS, getRuneItem(ItemId.RUNE_PLATELEGS), 30),

            // Feet - boots (Adamant -> Rune)
            new EquipmentSetRecord(EquipmentSlot.FEET, getRuneItem(ItemId.ADAMANT_BOOTS), 30),
            new EquipmentSetRecord(EquipmentSlot.FEET, getRuneItem(ItemId.RUNE_BOOTS), 30),

            // Hand - battleaxes (Adamant -> Rune)
            new EquipmentSetRecord(EquipmentSlot.HAND, getRuneItem(ItemId.ADAMANT_BATTLEAXE), 10),
            new EquipmentSetRecord(EquipmentSlot.HAND, getRuneItem(ItemId.RUNE_BATTLEAXE), 10),

            // Hand - daggers (Adamant -> Rune)
            new EquipmentSetRecord(EquipmentSlot.HAND, getRuneItem(ItemId.ADAMANT_DAGGER), 10),
            new EquipmentSetRecord(EquipmentSlot.HAND, getRuneItem(ItemId.RUNE_DAGGER), 10),

            // Hand - swords (Adamant -> Rune)
            new EquipmentSetRecord(EquipmentSlot.HAND, getRuneItem(ItemId.ADAMANT_SWORD), 10),
            new EquipmentSetRecord(EquipmentSlot.HAND, getRuneItem(ItemId.RUNE_SWORD), 10)
    )));

    private final MobEquipmentMeta meta;

    EquipmentSet(MobEquipmentMeta meta) {
        this.meta = meta;
    }

    private static ItemStack getRuneItem(ItemId id) {
        return getRuneItem(id, 1);
    }

    private static ItemStack getRuneItem(ItemId id, int amount) {
        AbstractItemType type = RuneQuestPlugin.getItemRegistry().getType(id);

        if (type == null) return new ItemStack(Material.AIR);
        return type.createItemStackOfTypeIgnoreState(amount); // skip state management for mob equipment
    }

    public MobEquipmentMeta asMobEquipmentMeta() {
        return this.meta;
    }
}
