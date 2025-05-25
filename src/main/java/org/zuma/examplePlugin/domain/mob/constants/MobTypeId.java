package org.zuma.examplePlugin.domain.mob.constants;

public enum MobTypeId {

    IMMORTAL_STATIC_SHOPKEEPER(1);

    private final int id;

    MobTypeId(int id) {
        this.id = id;
    }

    public int asInt() {
        return this.id;
    }

}
