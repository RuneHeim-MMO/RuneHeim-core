package org.zuma.examplePlugin.domain.mob.constants;

public enum MobDataKey {
    MOB_TYPE_ID("mob_type_id"),
    MOB_UUID("mob_uuid"),
    SESSION_UUID("session_uuid");

    private final String key;

    MobDataKey(String key) {
        this.key = key;
    }

    public String asString() {
        return key;
    }
}
