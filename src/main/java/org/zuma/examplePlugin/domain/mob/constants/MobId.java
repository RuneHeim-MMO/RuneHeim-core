package org.zuma.examplePlugin.domain.mob.constants;

public enum MobId {

    CHESTPLATE_SHOP(1),
    CHESTPLATE_SHOP_DUPE(2);

    private final int id;

    MobId(int id) {
        this.id = id;
    }

    public int asInt() {
        return this.id;
    }

}
