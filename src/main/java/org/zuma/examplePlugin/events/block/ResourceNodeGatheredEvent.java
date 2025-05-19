package org.zuma.examplePlugin.events.block;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;
import org.zuma.examplePlugin.domain.block.base.ResourceNode;
import org.zuma.examplePlugin.domain.player.RunePlayer;

public class ResourceNodeGatheredEvent extends Event implements Cancellable {
    private static final HandlerList HANDLERS = new HandlerList();
    private final ResourceNode node;
    private final RunePlayer player;
    private boolean cancelled;

    public ResourceNodeGatheredEvent(RunePlayer player, ResourceNode node) {
        this.player = player;
        this.node = node;
    }

    public ResourceNode getResourceNode() {
        return node;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLERS;
    }

    public
    static HandlerList getHandlerList() {
        return HANDLERS;
    }

    public RunePlayer getPlayer() {
        return player;
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
