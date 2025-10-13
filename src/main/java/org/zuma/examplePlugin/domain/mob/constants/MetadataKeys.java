package org.zuma.examplePlugin.domain.mob.constants;

public enum MetadataKeys {
    KILLER_UUID("killer_uuid"),
    MOST_DAMAGING_PLAYER_UUID("most_damaging_player_uuid");

    private final String key;

    MetadataKeys(String key) {
        this.key = key;
    }

    public String asString() {
        return key;
    }
}
