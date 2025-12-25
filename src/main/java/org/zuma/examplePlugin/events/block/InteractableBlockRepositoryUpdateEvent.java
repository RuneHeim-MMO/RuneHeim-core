package org.zuma.examplePlugin.events.block;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class InteractableBlockRepositoryUpdateEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();
    private final List<Long> updatedIds;

    public InteractableBlockRepositoryUpdateEvent(List<Long> updatedIds) {
        this.updatedIds = updatedIds;
    }

    public
    static HandlerList getHandlerList() {
        return HANDLERS;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLERS;
    }

    public List<Long> getUpdatedIds() {
        return updatedIds;
    }
}
