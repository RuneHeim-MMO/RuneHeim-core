package org.zuma.examplePlugin.domain.item.constants;

public enum PersistenceDataKey {
    ITEM_ID("item_id"),
    STATE_UUID("state_uuid"),
    COINS_AMOUNT("coins_amount"),
    ILLEGAL_ITEM("illegal_item");

    private final String key;

    PersistenceDataKey(String key) {
        this.key = key;
    }

    public String asString() {
        return key;
    }
}
