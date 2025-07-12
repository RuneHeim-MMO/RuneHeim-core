package org.zuma.examplePlugin.domain.mob.constants;

public enum MobType {
    GENERIC_SHOPKEEPER("generic_shopkeeper"),
    AGGRESSIVE_BANDIT_1("aggressive_bandit_1"),
    AGGRESSIVE_BANDIT_2("aggressive_bandit_2"),

    GOBLIN_LOW_LVL_1("goblin_low_lvl_1"),
    GOBLIN_LOW_LVL_2("goblin_low_lvl_2"),
    GOBLIN_LOW_LVL_3("goblin_low_lvl_3"),

    FUSION_MASTER("fusion_master"),
    SHEEP("sheep"),
    BROWN_BOAR("Brown Boar");


    private final String id;

    MobType(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
