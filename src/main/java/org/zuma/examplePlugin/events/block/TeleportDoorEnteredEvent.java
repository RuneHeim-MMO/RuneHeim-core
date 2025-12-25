package org.zuma.examplePlugin.events.block;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;
import org.zuma.examplePlugin.domain.block.impl.door.TeleportDoor;
import org.zuma.examplePlugin.domain.player.RunePlayer;

public class TeleportDoorEnteredEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();
    private final TeleportDoor door;
    private final RunePlayer player;

    public TeleportDoorEnteredEvent(TeleportDoor door, RunePlayer player) {
        this.door = door;
        this.player = player;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLERS;
    }

    public
    static HandlerList getHandlerList() {
        return HANDLERS;
    }

    public TeleportDoor getDoor() {
        return door;
    }

    public RunePlayer getPlayer() {
        return player;
    }
}
