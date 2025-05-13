package org.zuma.examplePlugin.item.constants;

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
