package org.zuma.examplePlugin.domain.gui.constants;

public enum GUIOverlay {
    COOKING_OVERLAY("⬛"),
    CAMPFIRE_OVERLAY("⦇"),
    FURNACE_OVERLAY("Ⰳ"),
    ANVIL_OVERLAY("⽰"),
    CRAFTING_TABLE_OVERLAY("⼟"),
    FUSION_MASTER_OVERLAY("⾭"),
    SHOP_OVERLAY("⿸"),
    SPINNING_WHEEL_OVERLAY("⼥"),
    BANK_OVERLAY("╄"),
    INVESTMENT_BANKER_OVERLAY("┠"),
    JEWELER_SHOP_OVERLAY("⽂"),
    XP_INVENTORY_OVERLAY("┒"),
    LOCATION_DISCOVERY_OVERLAY("╣"),
    CONTRACTS_BOARD_OVERLAY("ⁿ"),
    QUESTS_INVENTORY_OVERLAY("⼭"),

    ARCHERY_SHOP_OVERLAY("⨷"),
    BLACKSMITH_SHOP_OVERLAY("⯶"),
    FISHING_SHOP_OVERLAY("☛"),
    ARMOR_SHOP_OVERLAY("◫"),
    GENERAL_STORE_OVERLAY("⍏"),
    FOOD_SHOP_OVERLAY("⾦"),
    TOOL_SHOP_OVERLAY("⯃");

    private final String glyph;

    GUIOverlay(String glyph) {
        this.glyph = glyph;
    }

    public String asGlyph() {
        return "§f⛒⛒⛒⛒⛒⛒⛒⛒" + glyph;
    }
}
