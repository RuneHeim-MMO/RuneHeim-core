package org.zuma.examplePlugin.domain.item.constants;

import org.bukkit.NamespacedKey;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

public enum PersistenceDataKey {
    ITEM_ID("item_id", PersistentDataType.INTEGER),
    STATE_UUID("state_uuid", PersistentDataType.STRING),
    COINS_AMOUNT("coins_amount", PersistentDataType.INTEGER),
    ILLEGAL_ITEM("illegal_item", PersistentDataType.INTEGER),
    UNTRADEABLE_OWNER_UUID("untradeable_owner_uuid", PersistentDataType.STRING),

    LOCKED_TO_OWNER_UUID("locked_to_owner", PersistentDataType.STRING),
    LOCKED_TO_OWNER_UNTIL("locked_to_owner_until", PersistentDataType.LONG);

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
