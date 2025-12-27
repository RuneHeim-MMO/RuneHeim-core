package org.zuma.examplePlugin.domain.gui.serializer;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.interfaces.SerializableItemContainer;
import org.zuma.examplePlugin.core.logger.constants.LogLevel;
import org.zuma.examplePlugin.domain.gui.ItemStackSlotPair;
import org.zuma.examplePlugin.domain.gui.constants.GUIControl;
import org.zuma.examplePlugin.domain.item.constants.PersistenceDataKey;
import org.zuma.examplePlugin.domain.item.serializer.ItemSerializer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemContainerContentSerializer {
    private static final Gson gson = new Gson();

    public static String serializeItemSlotPairsSync(List<ItemStackSlotPair> pairs, boolean clearStateCache) throws IllegalArgumentException {
        Map<String, String> serialized = new HashMap<>();

        pairs.forEach(pair -> {
            try {
                ItemStack stack = pair.itemStack();
                ItemMeta meta = stack.getItemMeta();
                PersistentDataContainer pdc = meta.getPersistentDataContainer();

                if (GUIControl.isGuiItem(stack)) {
                    return;
                }

                boolean shouldSave = pdc.get(PersistenceDataKey.OVERRIDE_SAVING_AND_ILLEGALITY.key(RuneQuestPlugin.getInstance()), PersistentDataType.BYTE) == null;

                if (!shouldSave) {
                    return;
                }

                serialized.put(String.valueOf(pair.slot()), ItemSerializer.serializeItemStackWithPDCSync(stack, RuneQuestPlugin.getInstance(), clearStateCache));
            } catch (Exception e) {
                RuneQuestPlugin.getRuneLogger().log(LogLevel.WARN, "Failed to serialize item stack in slot " + pair.slot() + " from item container content!", e);
            }
        });

        return gson.toJson(serialized);
    }

    public static String serializeItemContainerContentSync(SerializableItemContainer container, boolean clearStateCache) throws IllegalArgumentException {
        return serializeItemSlotPairsSync(container.getContent(), clearStateCache);
    }

    public static List<ItemStackSlotPair> deserializeItemContainerContentSync(String json) throws IllegalArgumentException {
        Map<String, String> map = gson.fromJson(json, new TypeToken<Map<String, String>>() {
        }.getType());

        List<ItemStackSlotPair> deserialized = new ArrayList<>();

        map.forEach((slotStr, serializedStack) -> {
            try {
                int slot = Integer.parseInt(slotStr);
                ItemStack stack = ItemSerializer.deserializeItemStackWithPDCSync(serializedStack, RuneQuestPlugin.getInstance());

                deserialized.add(new ItemStackSlotPair(slot, stack));
            } catch (Exception e) {
                RuneQuestPlugin.getRuneLogger().log(LogLevel.WARN, "Failed to deserialize item stack in slot " + slotStr + " from item container content!", e);
            }
        });

        return deserialized;
    }

}
