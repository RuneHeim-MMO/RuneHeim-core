package org.zuma.examplePlugin.domain.skill.constants;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.zuma.examplePlugin.core.constants.Icon;

public enum SkillType {
    ATTACK(0, "Attack", "Train to improve your melee accuracy and damage.", 0, Icon.SWORD, Material.IRON_SWORD),
    DEFENCE(1, "Defence", "Boost your ability to block and reduce damage.", 0, Icon.SHIELD, Material.SHIELD),
    STRENGTH(2, "Strength", "Increase the power of your melee hits.", 0, Icon.STRENGTH, Material.DIAMOND_SWORD),
    HITPOINTS(3, "Hitpoints", "Your total health - stay alive longer by training this.", 1154, Icon.HEART, Material.GOLDEN_APPLE),
    RANGED(4, "Ranged", "Attack from afar with bows and crossbows.", 0, Icon.ARROW, Material.BOW),
    COOKING(7, "Cooking", "Prepare food to heal yourself in combat.", 0, Icon.EGGS, Material.COOKED_BEEF),
    WOODCUTTING(8, "Woodcutting", "Chop trees to collect logs for crafting and firemaking.", 0, Icon.AXE, Material.IRON_AXE),
    FISHING(9, "Fishing", "Catch fish to cook and eat or sell for profit.", 0, Icon.FISH, Material.FISHING_ROD),
    FIREMAKING(10, "Firemaking", "Light fires to cook food or for warmth.", 0, Icon.FIRE, Material.CAMPFIRE),

    CRAFTING(11, "Crafting", "Create jewelry, armor, and other useful items.", 0, Icon.CRAFTING, Material.CRAFTING_TABLE),
    SMITHING(12, "Smithing", "Forge ores into weapons and armor.", 0, Icon.ANVIL, Material.ANVIL),
    MINING(13, "Mining", "Mine rocks to gather ores and gems.", 0, Icon.PICKAXE, Material.IRON_PICKAXE),
    FLETCHING(14, "Fletching", "Make bows and arrows to improve your ranged combat.", 0, Icon.BOW, Material.ARROW);

    /*
        HERBLORE(15, "Herblore", 0),
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
    private final Icon symbol;
    private final Material material;

    SkillType(int key, String label, String description, int defaultXp, Icon symbol, Material material) {
        this.key = key;
        this.label = label;
        this.description = description;
        this.defaultXp = defaultXp;
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
}



