package org.zuma.examplePlugin.domain.item.serializer;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.adapters.ClassTypeAdapter;
import org.zuma.examplePlugin.core.adapters.OptionalTypeAdapter;
import org.zuma.examplePlugin.core.utils.TextUtils;
import org.zuma.examplePlugin.domain.item.base.AbstractItemType;
import org.zuma.examplePlugin.domain.item.constants.PersistenceDataKey;
import org.zuma.examplePlugin.domain.item.state.base.AbstractState;

import javax.annotation.Nullable;
import java.util.*;

public class ItemSerializer {
    private static final Gson gson = new GsonBuilder().registerTypeAdapter(Optional.class, new OptionalTypeAdapter<>())
            .registerTypeAdapter(Class.class, new ClassTypeAdapter())
            .create();

    public static String serializeItemStackWithPDCSync(ItemStack item, JavaPlugin plugin, boolean clearStateCache) throws IllegalArgumentException {
        AbstractItemType type = RuneQuestPlugin.getItemRegistry().getType(item);
        if (type == null) {
            throw new IllegalArgumentException("Cannot serialize an item " + TextUtils.extractPlainText(item.displayName()) + "! It is not in registry.");
        }

        Map<String, Object> serialized = new HashMap<>();
        ItemMeta meta = item.getItemMeta();

        if (meta == null) {
            throw new IllegalArgumentException("Cannot serialize an item " + TextUtils.extractPlainText(item.displayName()) + "! Meta is null.");
        }

        String displayName = TextUtils.extractPlainText(meta.displayName()).replaceAll("\\[", "").replaceAll("\\]", "");

        serialized.put("name", displayName); // Not used in deserialization
        serialized.put("amount", item.getAmount());

        PersistentDataContainer pdc = meta.getPersistentDataContainer();
        UUID pdcStateUUIDValue = RuneQuestPlugin.getItemStateManager().getItemStackStateUuid(item);
        Map<String, Object> customData = new HashMap<>();

        for (PersistenceDataKey dataKey : PersistenceDataKey.values()) {
            NamespacedKey key = dataKey.key(plugin);

            if (pdc.has(key, dataKey.getType())) {
                customData.put(key.toString(), pdc.get(key, dataKey.getType()));
            }
        }
        serialized.put("pdc", customData);

        if (pdcStateUUIDValue != null) {
            AbstractState state = RuneQuestPlugin.getItemStateManager().getItemState(item);

            if (state != null) {
                RuneQuestPlugin.getItemStateManager().saveUpdatedState(state, clearStateCache, true);
            }
        }

        return gson.toJson(serialized);
    }

    public static ItemStack deserializeItemStackWithPDCSync(String json, JavaPlugin plugin) throws IllegalArgumentException {
        Map<String, Object> map = gson.fromJson(json, new TypeToken<Map<String, Object>>() {
        }.getType());
        String storedName = (String) map.get("name");

        if (!map.containsKey("pdc")) {
            throw new IllegalArgumentException("Cannot deserialize an item (" + storedName + "). Property pdc is null.");
        }

        @SuppressWarnings("unchecked")
        Map<String, Object> customData = (Map<String, Object>) map.get("pdc");
        List<PDCValue> pdcValues = new ArrayList<>();
        @Nullable AbstractItemType matchedType = null;
        NamespacedKey itemIdKey = PersistenceDataKey.ITEM_ID.key(plugin);
        NamespacedKey stateUUIDKey = PersistenceDataKey.STATE_UUID.key(plugin);
        Object stateUUIDRawValue = customData.get(stateUUIDKey.toString());

        if (stateUUIDRawValue != null) {
            RuneQuestPlugin.getItemStateManager().preloadItemStateAsync(UUID.fromString((String) stateUUIDRawValue));
        }

        for (Map.Entry<String, Object> entry : customData.entrySet()) {
            String rawKey = entry.getKey();
            Object rawValue = entry.getValue();

            NamespacedKey key = NamespacedKey.fromString(rawKey);
            if (key == null) continue;

            // Find matching dataKey from enum
            for (PersistenceDataKey dataKey : PersistenceDataKey.values()) {
                NamespacedKey expectedKey = dataKey.key(plugin);
                if (!expectedKey.equals(key)) continue;

                @SuppressWarnings("unchecked")
                PersistentDataType<Object, Object> type = (PersistentDataType<Object, Object>) dataKey.getType();
                Object parsedValue = parseValue(rawValue, type);

                if (parsedValue != null) {
                    pdcValues.add(new PDCValue(key, type, parsedValue));
                }
            }

            if (rawKey.equals(itemIdKey.toString())) {
                if (rawValue instanceof Number) {
                    matchedType = RuneQuestPlugin.getItemRegistry().getType(((Number) rawValue).intValue());
                } else {
                    throw new IllegalArgumentException("Cannot deserialize an item (" + storedName + "). ID is invalid. " + rawValue + " is not a number.");
                }
            }
        }

        if (matchedType == null) {
            throw new IllegalArgumentException("Cannot deserialize an item (" + storedName + "). " + PersistenceDataKey.ITEM_ID.keyAsString() + " key is missing in PDC or ID is invalid item type.");
        }

        Object amountObj = map.get("amount");
        int amount = 1;

        if (amountObj instanceof Number) {
            amount = ((Number) amountObj).intValue();
        } else {
            throw new IllegalArgumentException("Cannot deserialize an item (" + storedName + "). Illegal 'amount' property value! (" + amountObj + ")");
        }

        ItemStack item = matchedType.createItemStackOfType(true);
        ItemMeta meta = item.getItemMeta();
        PersistentDataContainer pdc = meta.getPersistentDataContainer();

        for (PDCValue value : pdcValues) {
            applyPDCValue(pdc, value);
        }

        item.setItemMeta(meta);
        matchedType.applyItemStackMeta(item, amount);

        return item;
    }

    private static Object parseValue(Object raw, PersistentDataType<?, ?> type) {
        if (type == PersistentDataType.STRING) {
            return raw.toString();
        } else if (type == PersistentDataType.INTEGER) {
            if (raw instanceof Number) return ((Number) raw).intValue();
        } else if (type == PersistentDataType.LONG) {
            if (raw instanceof Number) return ((Number) raw).longValue();
        } else if (type == PersistentDataType.DOUBLE) {
            if (raw instanceof Number) return ((Number) raw).doubleValue();
        } else if (type == PersistentDataType.FLOAT) {
            if (raw instanceof Number) return ((Number) raw).floatValue();
        } else if (type == PersistentDataType.BYTE) {
            if (raw instanceof Number) return ((Number) raw).byteValue();
        } else if (type == PersistentDataType.BOOLEAN) {
            return Boolean.parseBoolean(raw.toString());
        } else if (type == PersistentDataType.INTEGER_ARRAY) {
            return gson.fromJson(gson.toJson(raw), int[].class);
        }
        // Add support or warnings for unsupported types:
        System.err.println("Unsupported PersistentDataType for parseValue: " + type);
        return null;
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private static void applyPDCValue(PersistentDataContainer pdc, PDCValue value) {
        pdc.set(value.key, (PersistentDataType) value.type, value.value);
    }

    private record PDCValue(NamespacedKey key, PersistentDataType<?, ?> type, Object value) {
    }

}
