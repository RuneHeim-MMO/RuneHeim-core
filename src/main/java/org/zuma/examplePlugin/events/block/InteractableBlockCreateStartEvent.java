package org.zuma.examplePlugin.events.block;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;
import org.zuma.examplePlugin.domain.block.persistence.BlockEntity;

public class InteractableBlockCreateStartEvent extends Event implements Cancellable {
    private static final HandlerList HANDLERS = new HandlerList();
    private final BlockEntity entity;
    private boolean cancelled;

    public InteractableBlockCreateStartEvent(BlockEntity entity) {
        this.entity = entity;
    }

    public
    static HandlerList getHandlerList() {
        return HANDLERS;
    }

    public BlockEntity getBlockEntity() {
        return entity;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLERS;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }
}
