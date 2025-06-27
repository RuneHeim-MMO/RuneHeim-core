package org.zuma.examplePlugin.events.inventory;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;
import org.zuma.examplePlugin.domain.gui.base.AbstractGUI;
import org.zuma.examplePlugin.domain.player.RunePlayer;

import java.util.List;
import java.util.UUID;

public class RuneInventoryOpenEvent extends Event implements Cancellable {
    private static final HandlerList HANDLERS = new HandlerList();
    private boolean cancelled;
    private final RunePlayer viewer;
    private final AbstractGUI gui;
    private final List<UUID> otherViewerUUIDs;

    public RuneInventoryOpenEvent(RunePlayer viewer, AbstractGUI gui, List<UUID> otherViewerUUIDs) {
        this.viewer = viewer;
        this.gui = gui;
        this.otherViewerUUIDs = otherViewerUUIDs;
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

    public RunePlayer getViewer() {
        return viewer;
    }

    public AbstractGUI getGui() {
        return gui;
    }

    public List<UUID> getOtherViewerUUIDs() {
        return otherViewerUUIDs;
    }
}
