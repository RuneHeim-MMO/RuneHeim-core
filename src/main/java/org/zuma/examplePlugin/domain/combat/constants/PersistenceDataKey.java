package org.zuma.examplePlugin.domain.combat.constants;

public enum PersistenceDataKey {
    PROJECTILE_TYPE("projectile_type");

    private final String key;

    PersistenceDataKey(String key) {
        this.key = key;
    }

    public String asString() {
        return key;
    }
}
