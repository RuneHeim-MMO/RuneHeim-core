package org.zuma.examplePlugin.events.block;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;
import org.zuma.examplePlugin.domain.block.impl.CampfireBlock;

public class CampfireStateChanged extends Event implements Cancellable {
    private static final HandlerList HANDLERS = new HandlerList();
    private final CampfireBlock campfire;
    private boolean cancelled;
    private final boolean toBeLit;

    public CampfireStateChanged(CampfireBlock campfire, boolean toBeLit) {
        this.campfire = campfire;
        this.toBeLit = toBeLit;
    }

    public CampfireBlock getCampfire() {
        return campfire;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLERS;
    }

    public
    static HandlerList getHandlerList() {
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

    public boolean getToBeLit() {
        return toBeLit;
    }
}
