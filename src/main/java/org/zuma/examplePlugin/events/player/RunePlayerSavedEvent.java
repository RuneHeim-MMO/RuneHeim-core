package org.zuma.examplePlugin.events.player;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class RunePlayerSavedEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();
    private final Player player;

    public RunePlayerSavedEvent(Player player) {
        this.player = player;
    }

    public
    static HandlerList getHandlerList() {
        return HANDLERS;
    }

    public Player getPlayer() {
        return player;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLERS;
    }
}
