package org.zuma.examplePlugin.events.state;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;
import org.zuma.examplePlugin.domain.item.state.base.AbstractState;

import java.util.UUID;

public class StateCreatedDefaultEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();
    private final UUID stateUUID;
    private final AbstractState state;

    public StateCreatedDefaultEvent(UUID stateUUID, AbstractState state) {
        this.stateUUID = stateUUID;
        this.state = state;
    }

    public UUID getStateUUID() {
        return stateUUID;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLERS;
    }

    public
    static HandlerList getHandlerList() {
        return HANDLERS;
    }

    public AbstractState getState() {
        return state;
    }
}
