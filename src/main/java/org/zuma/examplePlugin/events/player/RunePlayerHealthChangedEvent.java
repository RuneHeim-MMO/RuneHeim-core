package org.zuma.examplePlugin.events.player;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;
import org.zuma.examplePlugin.domain.player.RunePlayer;

public class RunePlayerHealthChangedEvent extends Event implements Cancellable {
    private static final HandlerList HANDLERS = new HandlerList();
    private final RunePlayer player;
    private final double newHealth;
    private final Cause cause;
    private boolean cancelled;
    public RunePlayerHealthChangedEvent(RunePlayer player, double newHealth, Cause cause) {
        this.player = player;
        this.newHealth = newHealth;
        this.cause = cause;
    }

    public
    static HandlerList getHandlerList() {
        return HANDLERS;
    }

    public RunePlayer getPlayer() {
        return player;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
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

    public double getNewHealth() {
        return newHealth;
    }

    public enum Cause {
        COMBAT, REGENERATION, HEALING, INITIALIZATION, RESPAWN
    }
}
