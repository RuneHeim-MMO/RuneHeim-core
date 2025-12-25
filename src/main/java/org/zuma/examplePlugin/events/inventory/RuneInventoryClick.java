package org.zuma.examplePlugin.events.inventory;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.jetbrains.annotations.NotNull;
import org.zuma.examplePlugin.domain.gui.base.AbstractGUI;
import org.zuma.examplePlugin.domain.player.RunePlayer;

public class RuneInventoryClick extends Event implements Cancellable {
    private static final HandlerList HANDLERS = new HandlerList();
    private final RunePlayer whoClicked;
    private final AbstractGUI gui;
    private final InventoryClickEvent event;
    private boolean cancelled;

    public RuneInventoryClick(RunePlayer whoClicked, AbstractGUI gui, InventoryClickEvent event) {
        this.whoClicked = whoClicked;
        this.gui = gui;
        this.event = event;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLERS;
    }


    public AbstractGUI getGui() {
        return gui;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    public RunePlayer getWhoClicked() {
        return whoClicked;
    }

    public InventoryClickEvent getEvent() {
        return event;
    }
}
