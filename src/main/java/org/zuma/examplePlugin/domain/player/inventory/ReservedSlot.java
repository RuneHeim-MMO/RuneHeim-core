package org.zuma.examplePlugin.domain.player.inventory;

import org.zuma.examplePlugin.domain.item.base.AbstractItemType;
import org.zuma.examplePlugin.domain.item.constants.ItemType;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public enum ReservedSlot {
    MOUNT_SLOT(9, "Mount Slot", ReservedSlotItemType.MOUNT),
    AMULET_SLOT(10, "Amulet Slot", ReservedSlotItemType.AMULET),
    RING_1_SLOT(11, "Ring Slot", ReservedSlotItemType.RING),
    RING_2_SLOT(12, "Ring Slot", ReservedSlotItemType.RING),
    FAIRY_SLOT(13, "Fairy Slot", ReservedSlotItemType.FAIRY),
    FAIRY_FOOD_SLOT(14, "Fairy Food Slot", ReservedSlotItemType.FAIRY_FOOD);

    private static final Map<Integer, ReservedSlot> SLOT_LOOKUP;

    static {
        SLOT_LOOKUP = Arrays.stream(values())
                .collect(Collectors.toMap(ReservedSlot::getSlot, slot -> slot));
    }

    private final int slot;
    private final String placeHolderLabel;
    private final ReservedSlotItemType type;

    ReservedSlot(int slot, String placeholderLabel, ReservedSlotItemType type) {
        this.slot = slot;
        this.placeHolderLabel = placeholderLabel;
        this.type = type;
    }


    public int getSlot() {
        return slot;
    }

    public String getPlaceHolderLabel() {
        return placeHolderLabel;
    }

    public ReservedSlotItemType getType() {
        return type;
    }

    public static Map<Integer, ReservedSlot> getSlotMap() {
        return SLOT_LOOKUP;
    }

    public boolean isValidItem(@Nullable AbstractItemType itemType) {
        if (itemType == null) {
            return false;
        }
        return switch (type) {
            case MOUNT -> false;
            case AMULET -> false;
            case RING -> false;
            case FAIRY -> itemType.type == ItemType.FAIRY;
            case FAIRY_FOOD -> itemType.type == ItemType.FAIRY_FOOD;
        };
    }

    public static boolean isItemInCorrectSlot(int slot, AbstractItemType itemType) {
        ReservedSlot slotDef = SLOT_LOOKUP.get(slot);

        if (slotDef == null) {
            return false;
        }

        return slotDef.isValidItem(itemType);

    }


}
