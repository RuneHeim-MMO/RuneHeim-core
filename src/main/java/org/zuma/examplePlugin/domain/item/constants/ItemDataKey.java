package org.zuma.examplePlugin.domain.item.constants;

public enum ItemDataKey {
    ITEM_ID("item_id"),
    STATE_UUID("state_uuid");

    private final String key;

    ItemDataKey(String key) {
        this.key = key;
    }

    public String asString() {
        return key;
    }
}
