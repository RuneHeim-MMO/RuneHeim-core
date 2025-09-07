package org.zuma.examplePlugin.domain.mount;

import org.bukkit.Location;

import javax.annotation.Nullable;
import java.util.UUID;

public class PlayerMount {
    private UUID owner;
    private UUID activeEntity;
    private final UUID stateUUID;
    private int mountID;
    @Nullable
    private Location lastPosition;

    public PlayerMount(UUID owner, @Nullable UUID activeEntity, int mountID, UUID stateUUID) {
        this.owner = owner;
        this.activeEntity = activeEntity;
        this.mountID = mountID;
        this.stateUUID = stateUUID;
        this.lastPosition = null;
    }

    @Nullable
    public UUID getActiveEntityUUID() {
        return activeEntity;
    }

    public void setActiveEntityUUID(UUID activeEntity) {
        this.activeEntity = activeEntity;
    }

    public UUID getOwner() {
        return owner;
    }

    public void setOwner(UUID owner) {
        this.owner = owner;
    }

    public int getMountID() {
        return mountID;
    }

    public void setMountID(int mountID) {
        this.mountID = mountID;
    }

    public UUID getStateUUID() {
        return stateUUID;
    }

    @Nullable
    public Location getLastPosition() {
        return lastPosition;
    }

    public void setLastPosition(@Nullable Location lastPosition) {
        this.lastPosition = lastPosition;
    }
}
