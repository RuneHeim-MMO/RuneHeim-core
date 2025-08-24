package org.zuma.examplePlugin.events.player;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;
import org.zuma.examplePlugin.domain.player.RunePlayer;

public class RunePlayerHealthChangedEvent extends Event implements Cancellable {
    public enum Cause {
        COMBAT, HEALING, INITIALIZATION, RESPAWN
    }

    private static final HandlerList HANDLERS = new HandlerList();
    private boolean cancelled;
    private final RunePlayer player;
    private final int newHealth;
    private final Cause cause;

    public RunePlayerHealthChangedEvent(RunePlayer player, int newHealth, Cause cause) {
        this.player = player;
        this.newHealth = newHealth;
        this.cause = cause;
    }

    public RunePlayer getPlayer() {
        return player;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLERS;
    }

    public
    static HandlerList getHandlerList() {
        return HANDLERS;
    }
    
    public Cause getCause() {
        return cause;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    public int getNewHealth() {
        return newHealth;
    }
}
