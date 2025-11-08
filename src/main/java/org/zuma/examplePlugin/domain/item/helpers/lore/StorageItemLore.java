package org.zuma.examplePlugin.domain.item.helpers.lore;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.inventory.ItemStack;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.constants.Color;
import org.zuma.examplePlugin.core.utils.StorageUtils;
import org.zuma.examplePlugin.domain.item.ItemRecord;
import org.zuma.examplePlugin.domain.item.base.AbstractItemType;
import org.zuma.examplePlugin.domain.item.base.EquipableItemType;
import org.zuma.examplePlugin.domain.item.impl.StorageItem;
import org.zuma.examplePlugin.domain.quest.Quest;

import java.util.ArrayList;
import java.util.List;

public class StorageItemLore implements LoreCreator {
    private final List<Component> lore = new ArrayList<>();

    public StorageItemLore(StorageItem storageItem, ItemStack itemStack) {
        List<ItemRecord> storedItems = StorageUtils.getDisplayItemIdsFromPDC(itemStack);

        lore.add(Component.empty());
        lore.add(Component.text("Contents: ", Color.ITEM_CATEGORY_LABEL.asColor()));
        if (storedItems.isEmpty()) {
            lore.add(Component.text("• Empty", Color.ITEM_REQUIREMENT.asColor()));
        } else {
            for (ItemRecord record : storedItems) {
                AbstractItemType type = RuneQuestPlugin.getItemRegistry().getType(record.getItemId());
                if (type == null) continue;

                Component itemDisplayName = type.getMetaGenerator().getDisplayName(itemStack);
                lore.add(Component.text("• " + record.getAmount() + " x ", itemDisplayName.color()).append(itemDisplayName));
            }
        }

    }

    @Override
    public List<Component> getLore() {
        return lore;
    }
}
