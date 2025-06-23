package org.zuma.examplePlugin.domain.mob.constants;

public enum MobType {
    GENERIC_SHOPKEEPER("generic_shopkeeper"),
    AGGRESSIVE_BANDIT_1("aggressive_bandit_1"),
    AGGRESSIVE_BANDIT_2("aggressive_bandit_2");

    private final String id;

    MobType(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
