package org.zuma.examplePlugin.domain.gui.constants;

public enum GUIOverlay {
    COOKING_OVERLAY("⬛"),
    CAMPFIRE_OVERLAY("⦇"),
    FURNACE_OVERLAY("Ⰳ"),
    ANVIL_OVERLAY("⽰"),
    CRAFTING_TABLE_OVERLAY("⼟"),
    FUSION_MASTER_OVERLAY("⾭");

    private final String glyph;

    GUIOverlay(String glyph) {
        this.glyph = glyph;
    }

    public String asGlyph() {
        return "§f⛒⛒⛒⛒⛒⛒⛒⛒" + glyph;
    }
}
