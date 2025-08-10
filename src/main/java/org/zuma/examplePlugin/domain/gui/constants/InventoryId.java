package org.zuma.examplePlugin.domain.gui.constants;

public enum InventoryId {
    ADMIN_NODE_CREATE(-100),
    ADMIN_INTERACTABLE_BLOCK_CREATE(-99),
    BASIC_FURNACE(0),
    STOVE(1),
    FIRE(2),
    ANVIL(3),
    CRAFTING_TABLE(4),
    FUSION_MASTER(5),
    GENERAL_STORE(6),
    SPINNING_WHEEL(7),
    DRAKKAR_BLACKSMITH(8),
    BANK(9),
    INVESTMENT_BANKER(10),
    JEWELER(11),
    XP_LAMP(12),
    TERRITORY_INVENTORY(13);

    private final int id;

    InventoryId(int id) {
        this.id = id;
    }

    public int asInt() {
        return this.id;
    }


}
