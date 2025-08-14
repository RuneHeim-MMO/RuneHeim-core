package org.zuma.examplePlugin.domain.gui.serializer;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.interfaces.SerializableItemContainer;
import org.zuma.examplePlugin.domain.gui.ItemStackSlotPair;
import org.zuma.examplePlugin.domain.gui.constants.GUIControl;
import org.zuma.examplePlugin.domain.item.serializer.ItemSerializer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemContainerContentSerializer {
    private static final Gson gson = new Gson();

    public static String serializeItemSlotPairs(List<ItemStackSlotPair> pairs) throws IllegalArgumentException {
        Map<String, String> serialized = new HashMap<>();

        pairs.forEach(pair -> {
            ItemStack stack = pair.itemStack();

            if (GUIControl.isGuiItem(stack)) {
                return;
            }

            serialized.put(String.valueOf(pair.slot()), ItemSerializer.serializeItemStackWithPDC(stack, RuneQuestPlugin.getInstance()));
        });

        return gson.toJson(serialized);
    }

    public static String serializeItemContainerContent(SerializableItemContainer container) throws IllegalArgumentException {
        return serializeItemSlotPairs(container.getContent());
    }

    public static List<ItemStackSlotPair> deserializeItemContainerContent(String json) throws IllegalArgumentException {
        Map<String, String> map = gson.fromJson(json, new TypeToken<Map<String, String>>() {
        }.getType());

        List<ItemStackSlotPair> deserialized = new ArrayList<>();

        map.forEach((slotStr, serializedStack) -> {
            int slot = Integer.parseInt(slotStr);
            ItemStack stack = ItemSerializer.deserializeItemStackWithPDC(serializedStack, RuneQuestPlugin.getInstance());

            deserialized.add(new ItemStackSlotPair(slot, stack));
        });

        return deserialized;
    }

}
