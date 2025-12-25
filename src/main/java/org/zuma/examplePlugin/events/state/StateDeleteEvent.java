package org.zuma.examplePlugin.events.state;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class StateDeleteEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();
    private final UUID stateUUID;

    public StateDeleteEvent(UUID stateUUID) {
        this.stateUUID = stateUUID;
    }

    public
    static HandlerList getHandlerList() {
        return HANDLERS;
    }

    public UUID getStateUUID() {
        return stateUUID;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLERS;
    }
}
