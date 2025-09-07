package org.zuma.examplePlugin.domain.player.inventory;

import org.zuma.examplePlugin.core.constants.SoundType;
import org.zuma.examplePlugin.domain.item.base.AbstractItemType;
import org.zuma.examplePlugin.domain.item.constants.ItemType;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public enum ReservedSlot {
    MOUNT_SLOT(9, "Mount Slot", ReservedSlotItemType.MOUNT, SoundType.MOUNT_EQUIP, SoundType.MOUNT_UNEQUIP),
    AMULET_SLOT(10, "Amulet Slot", ReservedSlotItemType.AMULET, SoundType.JEWELERY_EQUIP, SoundType.JEWELERY_UNEQUIP),
    RING_1_SLOT(11, "Ring Slot", ReservedSlotItemType.RING, SoundType.JEWELERY_EQUIP, SoundType.JEWELERY_UNEQUIP),
    RING_2_SLOT(12, "Ring Slot", ReservedSlotItemType.RING, SoundType.JEWELERY_EQUIP, SoundType.JEWELERY_UNEQUIP),
    FAIRY_SLOT(13, "Fairy Slot", ReservedSlotItemType.FAIRY, SoundType.FAIRY_EQUIP, SoundType.FAIRY_UNEQUIP),
    FAIRY_FOOD_SLOT(14, "Fairy Food Slot", ReservedSlotItemType.FAIRY_FOOD, SoundType.FAIRY_FOOD_EQUIP, SoundType.FAIRY_FOOD_UNEQUIP),
    ARROW_SLOT(15, "Arrow Slot", ReservedSlotItemType.ARROW, SoundType.ARROW_EQUIP, SoundType.ARROW_UNEQUIP);

    private static final Map<Integer, ReservedSlot> SLOT_LOOKUP;

    static {
        SLOT_LOOKUP = Arrays.stream(values())
                .collect(Collectors.toMap(ReservedSlot::getSlot, slot -> slot));
    }

    private final int slot;
    private final String placeHolderLabel;
    private final ReservedSlotItemType type;
    private final SoundType equipSound, unequipSound;

    ReservedSlot(int slot, String placeholderLabel, ReservedSlotItemType type, SoundType equipSound, SoundType unequipSound) {
        this.slot = slot;
        this.placeHolderLabel = placeholderLabel;
        this.type = type;
        this.equipSound = equipSound;
        this.unequipSound = unequipSound;
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
            case AMULET -> itemType.type == ItemType.ACCESSORY_AMULET;
            case RING -> itemType.type == ItemType.ACCESSORY_RING;
            case FAIRY -> itemType.type == ItemType.FAIRY;
            case FAIRY_FOOD -> itemType.type == ItemType.FAIRY_FOOD;
            case ARROW -> itemType.type == ItemType.ARROW;
        };
    }

    public static boolean isItemInCorrectSlot(int slot, AbstractItemType itemType) {
        ReservedSlot slotDef = SLOT_LOOKUP.get(slot);

        if (slotDef == null) {
            return false;
        }

        return slotDef.isValidItem(itemType);

    }


    public SoundType getUnequipSound() {
        return unequipSound;
    }

    public SoundType getEquipSound() {
        return equipSound;
    }
}
