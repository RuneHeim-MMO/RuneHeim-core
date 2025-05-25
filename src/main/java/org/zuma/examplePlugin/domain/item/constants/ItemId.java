package org.zuma.examplePlugin.domain.item.constants;

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
    RUNITE_BAR(205),
    
    BRONZE_HELMET(300),
    IRON_HELMET(301),
    STEEL_HELMET(302),
    MITHRIL_HELMET(303),
    ADAMANTITE_HELMET(304),
    RUNITE_HELMET(305),

    BRONZE_BODY(400),
    IRON_BODY(401),
    STEEL_BODY(402),
    MITHRIL_BODY(403),
    ADAMANTITE_BODY(404),
    RUNITE_BODY(405),

    BRONZE_PLATELEGS(500),
    IRON_PLATELEGS(501),
    STEEL_PLATELEGS(502),
    MITHRIL_PLATELEGS(503),
    ADAMANTITE_PLATELEGS(504),
    RUNITE_PLATELEGS(505),

    BRONZE_BOOTS(600),
    IRON_BOOTS(601),
    STEEL_BOOTS(602),
    MITHRIL_BOOTS(603),
    ADAMANTITE_BOOTS(604),
    RUNITE_BOOTS(605);


    private final int id;

    ItemId(int id) {
        this.id = id;
    }

    public int asInt() {
        return this.id;
    }


}
