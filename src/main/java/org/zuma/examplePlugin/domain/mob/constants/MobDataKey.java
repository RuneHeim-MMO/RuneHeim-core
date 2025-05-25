package org.zuma.examplePlugin.domain.mob.constants;

public enum MobDataKey {
    MOB_TYPE_ID("mob_type_id"),
    MOB_ID("mob_id");

    private final String key;

    MobDataKey(String key) {
        this.key = key;
    }

    public String asString() {
        return key;
    }
}
