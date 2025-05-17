package org.zuma.examplePlugin.item.constants;

public enum ItemId {
    BRONZE_PICKAXE(0),
    IRON_PICKAXE(1),

    BRONZE_ORE(100),
    IRON_ORE(101),
    STEEL_ORE(102),
    MITHRIL_ORE(103),
    ADAMANTITE_ORE(104),
    RUNITE_ORE(105),

    BRONZE_BAR(200),
    IRON_BAR(201),
    STEEL_BAR(202),
    MITHRIL_BAR(203),
    ADAMANTITE_BAR(204),
    RUNITE_BAR(205);

    private final int id;

    ItemId(int id) {
        this.id = id;
    }

    public int asInt() {
        return this.id;
    }


}
