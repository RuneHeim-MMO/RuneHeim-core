package org.zuma.examplePlugin.domain.skill.constants;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.zuma.examplePlugin.core.constants.Icon;

import javax.annotation.Nullable;

public enum SkillType {
    ATTACK(0, "Attack", "Train to improve your melee accuracy and damage.", 0, 60, Icon.SWORD, Material.IRON_SWORD),
    DEFENCE(1, "Defence", "Boost your ability to block and reduce damage.", 0, 60, Icon.SHIELD, Material.SHIELD),
    STRENGTH(2, "Strength", "Increase the power of your melee hits.", 0, 60, Icon.STRENGTH, Material.DIAMOND_SWORD),
    HITPOINTS(3, "Hitpoints", "Your total health - stay alive longer by training this.", 1154, 60, Icon.HEART, Material.GOLDEN_APPLE),
    RANGED(4, "Ranged", "Attack from afar with bows and crossbows.", 0, 60, Icon.ARROW, Material.BOW),
    COOKING(7, "Cooking", "Prepare food to heal yourself in combat.", 0, null, Icon.EGGS, Material.COOKED_BEEF),
    WOODCUTTING(8, "Woodcutting", "Chop trees to collect logs for crafting and firemaking.", 0, null, Icon.AXE, Material.IRON_AXE),
    FISHING(9, "Fishing", "Catch fish to cook and eat or sell for profit.", 0, null, Icon.FISH, Material.FISHING_ROD),
    FIREMAKING(10, "Firemaking", "Light fires to cook food or for warmth.", 0, null, Icon.CAMPFIRE, Material.CAMPFIRE),
    CRAFTING(11, "Crafting", "Create jewelry, armor, and other useful items.", 0, null, Icon.CRAFTING, Material.CRAFTING_TABLE),
    SMITHING(12, "Smithing", "Forge ores into weapons and armor.", 0, null, Icon.ANVIL, Material.ANVIL),
    MINING(13, "Mining", "Mine rocks to gather ores and gems.", 0, null, Icon.PICKAXE, Material.IRON_PICKAXE),
    FLETCHING(14, "Fletching", "Make bows and arrows to improve your ranged combat.", 0, null, Icon.BOW, Material.ARROW),
    PRAYER(15, "Prayer", "Enhance your abilities and protect yourself using divine blessings.", 0, null, Icon.PRAYER, Material.BONE),
    HERBLORE(16, "Herblore", "Discover and create variety of potions.", 0, null, Icon.HERBLORE, Material.TALL_GRASS);

    /*
        AGILITY(16, "Agility", 0),
        THIEVING(17, "Thieving", 0),
        SLAYER(18, "Slayer", 0),
        FARMING(19, "Farming", 0),
        RUNECRAFT(20, "Runecraft", 0),
        HUNTER(21, "Hunter", 0),
        CONSTRUCTION(22, "Construction", 0);
    */

    private final int key;
    private final String label, description;
    private final int defaultXp;
    private final @Nullable Integer maxLevelCap;
    private final Icon symbol;
    private final Material material;

    SkillType(int key, String label, String description, int defaultXp, @Nullable Integer maxLevelCap, Icon symbol, Material material) {
        this.key = key;
        this.label = label;
        this.description = description;
        this.defaultXp = defaultXp;
        this.maxLevelCap = maxLevelCap;
        this.symbol = symbol;
        this.material = material;
    }

    public int asNumber() {
        return key;
    }

    public String getLabel() {
        return label;
    }

    public int getDefaultXp() {
        return defaultXp;
    }

    public String getSymbol() {
        return symbol.get();
    }

    public Component getSymbolComponent() {
        return symbol.asComponent();
    }

    public Material getMaterial() {
        return material;
    }

    public String getDescription() {
        return description;
    }

    @Nullable
    public Integer getMaxLevelCap() {
        return maxLevelCap;
    }
}



