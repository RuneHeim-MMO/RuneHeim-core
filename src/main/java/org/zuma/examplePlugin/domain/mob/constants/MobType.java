package org.zuma.examplePlugin.domain.mob.constants;

public enum MobType {
    GENERIC_SHOPKEEPER("generic_shopkeeper"),
    AGGRESSIVE_BANDIT("aggressive_bandit");

    private final String id;

    MobType(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
