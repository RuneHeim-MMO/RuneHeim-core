package org.zuma.examplePlugin.domain.block.constants;

public enum ResourceNodeTypeId {

    TREE(200),
    BIRCH_TREE(201),
    MAPLE_TREE(203),
    YEW_TREE(204),
    MAHOGANY_TREE(205),
    MAGIC_TREE(206),

    COPPER_ORE(100),
    IRON_ORE(101),
    MITHRIL_ORE(103),
    ADAMANTITE_ORE(104),
    RUNITE_ORE(105),
    TIN_ORE(106),
    COAL_ORE(107),

    HERRING_POOL(1),
    MINNOW_POOL(2),
    TROUT_STREAM(3),
    SALMON_STREAM(4),
    TUNA_SPOT(5),
    CRAWLER_REEF(6),
    PIKE_LAKE(7),
    EEL_SWAMP(8),
    SHARK_DEPTHS(9),
    TURTLE_BAY(10),
    RAY_SANDBANK(11);


    private final int id;

    ResourceNodeTypeId(int id) {
        this.id = id;
    }

    public int asInt() {
        return this.id;
    }

}
