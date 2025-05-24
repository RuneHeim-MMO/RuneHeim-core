package org.zuma.examplePlugin.domain.item.constants;

public enum PersistentDataKey {
    ITEM_ID("item_id"),
    STATE_UUID("state_uuid"),
    STATE_TYPE("state_type");

    private final String key;

    PersistentDataKey(String key) {
        this.key = key;
    }

    public String asString() {
        return key;
    }
}
