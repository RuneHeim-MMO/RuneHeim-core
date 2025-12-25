package org.zuma.examplePlugin.domain.housing.records;

import org.bukkit.block.BlockFace;

import java.util.Objects;
import java.util.UUID;

public class HouseFurnitureEntry {
    private final UUID uuid;
    private final int itemId;
    private final int slotIndex;
    private final HouseSlot.HouseSlotType type;
    private final String json;
    private BlockFace face; // mutable

    public HouseFurnitureEntry(UUID uuid, int itemId, int slotIndex, BlockFace face, HouseSlot.HouseSlotType type, String json) {
        this.uuid = uuid;
        this.itemId = itemId;
        this.slotIndex = slotIndex;
        this.face = face;
        this.type = type;
        this.json = json;
    }

    // Getters matching record methods
    public UUID uuid() {
        return uuid;
    }

    public int itemId() {
        return itemId;
    }

    public int slotIndex() {
        return slotIndex;
    }

    public BlockFace face() {
        return face;
    }

    public HouseSlot.HouseSlotType type() {
        return type;
    }

    public String json() {
        return json;
    }

    // Setter for face
    public void setFace(BlockFace face) {
        this.face = face;
    }

    // equals and hashCode (based on all fields, like a record)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HouseFurnitureEntry that)) return false;
        return itemId == that.itemId &&
                slotIndex == that.slotIndex &&
                Objects.equals(uuid, that.uuid) &&
                face == that.face &&
                type == that.type &&
                Objects.equals(json, that.json);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, itemId, slotIndex, face, type, json);
    }

    @Override
    public String toString() {
        return "HouseFurnitureEntry[" +
                "uuid=" + uuid +
                ", itemId=" + itemId +
                ", slotIndex=" + slotIndex +
                ", face=" + face +
                ", type=" + type +
                ", json=" + json +
                ']';
    }
}
