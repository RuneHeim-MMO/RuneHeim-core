package org.zuma.examplePlugin.domain.block.constants;

import org.bukkit.Material;

public enum ResourceNodeTypeId {

    FLAX(300),

    TREE(200),
    BIRCH_TREE(201),
    MAPLE_TREE(203),
    YEW_TREE(204),
    MAHOGANY_TREE(205),
    MAGIC_TREE(206),

    COPPER_ORE(100),
    IRON_ORE(101),
    MITHRIL_ORE(103),
    ADAMANT_ORE(104),
    RUNE_ORE(105),
    TIN_ORE(106),
    COAL_ORE(107),
    GOLD_ORE(108),

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
    RAY_SANDBANK(11),

    POTATO_CROP(400),
    CARROT_CROP(401),
    DEWLEAF(402),
    SUNROOT(403),
    MOONSHADE(404),
    CRIMSON_THISTLE(405),
    FROSTCAP(406),
    GLOWFERN(407),
    STORMVINE(408),
    BLOODORCHID(409),
    DRAGONS_BREATH(410),
    ETHEREAL_BLOOM(411);


    private final int id;

    ResourceNodeTypeId(int id) {
        this.id = id;
    }

    public int asInt() {
        return this.id;
    }

}
