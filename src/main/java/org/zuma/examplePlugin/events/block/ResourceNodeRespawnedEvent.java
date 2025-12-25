package org.zuma.examplePlugin.events.block;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;
import org.zuma.examplePlugin.domain.block.impl.ResourceNode;

public class ResourceNodeRespawnedEvent extends Event implements Cancellable {
    private static final HandlerList HANDLERS = new HandlerList();
    private final ResourceNode node;
    private boolean cancelled;

    public ResourceNodeRespawnedEvent(ResourceNode node) {
        this.node = node;
    }

    public
    static HandlerList getHandlerList() {
        return HANDLERS;
    }

    public ResourceNode getResourceNode() {
        return node;
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
