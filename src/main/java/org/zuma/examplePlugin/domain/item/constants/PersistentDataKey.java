package org.zuma.examplePlugin.domain.item.constants;

public enum PersistentDataKey {
    ITEM_ID("item_id");

    private final String key;

    PersistentDataKey(String key) {
        this.key = key;
    }

    public String asString() {
        return key;
    }
}
