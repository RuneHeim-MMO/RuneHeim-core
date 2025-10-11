package org.zuma.examplePlugin.domain.droptable;

public enum DropTableId {
    BONES(0),
    COINS_SMALL(1),
    WOOL(2),
    CHICKEN_MEAT(3),
    FEATHERS(4),
    GEM_DROP_TABLE(5),
    RAW_ANIMAL_MEAT(6),
    COINS_MEDIUM(7),
    WOLF_LOW_LVL_ARMOR(8),
    WOLF_COINS(9),
    GOBLIN_LOW_LVL_ARMOR(10),
    COWHIDE(11),

    WOODCUTTING_RARE_DROPTABLE(100),
    TREE_NODE(101),
    BIRCH_TREE_NODE(102),
    MAPLE_TREE_NODE(103),
    MAHOGANY_TREE_NODE(104),
    YEW_TREE_NODE(105),
    MAGIC_TREE_NODE(106),

    MINING_RARE_DROPTABLE(200),
    TIN_NODE(201),
    COPPER_NODE(202),
    IRON_NODE(203),
    COAL_NODE(204),
    MITHRIL_NODE(205),
    ADAMANT_NODE(206),
    RUNE_NODE(207),

    FISHING_RARE_DROPTABLE(300),
    HERRING_NODE(301),
    MINNOW_NODE(302),
    TROUT_NODE(303),
    SALMON_NODE(304),
    TUNA_NODE(305),
    CRAWLER_NODE(306),
    PIKE_NODE(307),
    EEL_NODE(308),
    SHARK_NODE(309),
    TURTLE_NODE(310),
    RAY_NODE(311),

    FLAX_NODE(400),

    SCRATCH_CARD_COINS_UNDER_2K_COINS(500),

    TUNDRA_WOLF_THE_SICK_CAMELS(600),
    SWAMP_FROG_THE_SICK_CAMELS(601);

    private final int id;

    DropTableId(int id) {
        this.id = id;
    }

    public int asInt() {
        return this.id;
    }
}
