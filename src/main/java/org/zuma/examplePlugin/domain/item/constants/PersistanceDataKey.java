package org.zuma.examplePlugin.domain.item.constants;

public enum PersistanceDataKey {
    ITEM_ID("item_id"),
    STATE_UUID("state_uuid");

    private final String key;

    PersistanceDataKey(String key) {
        this.key = key;
    }

    public String asString() {
        return key;
    }
}
