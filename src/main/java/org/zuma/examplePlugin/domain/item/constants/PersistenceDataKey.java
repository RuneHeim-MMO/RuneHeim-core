package org.zuma.examplePlugin.domain.item.constants;

import org.bukkit.NamespacedKey;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

public enum PersistenceDataKey {
    ITEM_ID("item_id", PersistentDataType.INTEGER),
    STATE_UUID("state_uuid", PersistentDataType.STRING),
    COINS_AMOUNT("coins_amount", PersistentDataType.INTEGER),

    // Custom marked illegal item
    ILLEGAL_ITEM("illegal_item", PersistentDataType.BYTE),

    // First owner of the item, for untradeable items
    UNTRADEABLE_OWNER_UUID("untradeable_owner_uuid", PersistentDataType.STRING),

    // For protecting drops
    LOCKED_TO_OWNER_UUID("locked_to_owner", PersistentDataType.STRING),
    LOCKED_TO_OWNER_UNTIL("locked_to_owner_until", PersistentDataType.LONG),

    // For storage items
    STORAGE_ITEM_STORED_IDS("stored_ids", PersistentDataType.INTEGER_ARRAY),
    STORAGE_ITEM_STORED_AMOUNTS("stored_amounts", PersistentDataType.INTEGER_ARRAY),

    // Item locking, to prevent accidental dropping/selling
    LOCKED_TO_INVENTORY("locked_to_inventory", PersistentDataType.BYTE);

    private final String keyName;
    private final PersistentDataType<?, ?> type;

    PersistenceDataKey(String keyName, PersistentDataType<?, ?> type) {
        this.keyName = keyName;
        this.type = type;
    }

    public NamespacedKey key(JavaPlugin plugin) {
        return new NamespacedKey(plugin, keyName);
    }

    public String keyAsString() {
        return keyName;
    }

    public PersistentDataType<?, ?> getType() {
        return type;
    }
}
