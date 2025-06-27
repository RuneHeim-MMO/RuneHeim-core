package org.zuma.examplePlugin.events.inventory;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;
import org.zuma.examplePlugin.domain.player.RunePlayer;

import java.util.List;
import java.util.UUID;

public class RuneInventoryCloseEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();
    private final RunePlayer viewer;
    private final int id;
    private final UUID instanceUUID;
    private final List<UUID> otherViewerUUIDs;
    private final boolean forced;

    public RuneInventoryCloseEvent(RunePlayer viewer, int id, UUID instanceUUID, List<UUID> otherViewerUUIDs, boolean forced) {
        this.viewer = viewer;
        this.id = id;
        this.instanceUUID = instanceUUID;
        this.otherViewerUUIDs = otherViewerUUIDs;
        this.forced = forced;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLERS;
    }

    public RunePlayer getViewer() {
        return viewer;
    }

    public List<UUID> getOtherViewerUUIDs() {
        return otherViewerUUIDs;
    }

    public boolean isForced() {
        return forced;
    }

    public UUID getInstanceUUID() {
        return instanceUUID;
    }

    public int getId() {
        return id;
    }
}
