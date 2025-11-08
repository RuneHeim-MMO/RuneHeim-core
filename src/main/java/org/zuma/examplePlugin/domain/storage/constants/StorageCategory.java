package org.zuma.examplePlugin.domain.storage.constants;

import org.zuma.examplePlugin.domain.item.constants.ItemType;

import java.util.List;

public enum StorageCategory {
    MISC("Misc items", List.of(ItemType.MISC, ItemType.REMAINS, ItemType.CURRENCY, ItemType.CHANCE_ITEM, ItemType.XP_LAMP)),
    EQUIPMENT("Equipment", List.of(ItemType.FAIRY, ItemType.EQUIPMENT, ItemType.EQUIPMENT_WITH_GEM_SOCKETS, ItemType.ACCESSORY_RING, ItemType.ACCESSORY_AMULET, ItemType.MOUNT, ItemType.MELEE_WEAPON, ItemType.MELEE_WEAPON_WITH_GEM_SOCKETS, ItemType.RANGED_WEAPON, ItemType.RANGED_WEAPON_WITH_SOCKETS, ItemType.ARROW)),
    MATERIAL("Materials", List.of(ItemType.EMPTY_CONTAINER, ItemType.MATERIAL, ItemType.MATERIAL_SCROLL, ItemType.GEM)),
    CONSUMABLES("Consumables", List.of(ItemType.FOOD, ItemType.POTION, ItemType.FAIRY_FOOD, ItemType.FAIRY_FRUIT)),
    TOOLS("Tools", List.of(ItemType.TOOL, ItemType.TOOL_WITH_GEM_SOCKETS, ItemType.COMPASS));

    private final String displayName;
    private final List<ItemType> associatedItemTypes;

    StorageCategory(String displayName, List<ItemType> associatedItemTypes) {
        this.displayName = displayName;
        this.associatedItemTypes = associatedItemTypes;
    }

    public String getDisplayName() {
        return displayName;
    }

    public List<ItemType> getAssociatedItemTypes() {
        return associatedItemTypes;
    }

    public boolean isItemTypeInCategory(ItemType itemType) {
        return associatedItemTypes.contains(itemType);
    }
}
