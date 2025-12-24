package org.zuma.examplePlugin.events.housing;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;
import org.zuma.examplePlugin.domain.housing.instance.HouseInstance;

public class HouseSettingsUpdateEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();
    private final HouseInstance instance;

    public HouseSettingsUpdateEvent(HouseInstance instance) {
        this.instance = instance;
    }

    public HouseInstance getInstance() {
        return instance;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLERS;
    }

    public
    static HandlerList getHandlerList() {
        return HANDLERS;
    }
}
