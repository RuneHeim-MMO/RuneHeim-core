package org.zuma.examplePlugin.events.player;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;
import org.zuma.examplePlugin.domain.player.RunePlayer;

public class RunePlayerLoadedEvent extends Event implements Cancellable {
    private static final HandlerList HANDLERS = new HandlerList();
    private final RunePlayer player;
    private final boolean isFirstTimeJoin;
    private boolean cancelled;

    public RunePlayerLoadedEvent(RunePlayer player, boolean isFirstTimeJoin) {
        this.player = player;
        this.isFirstTimeJoin = isFirstTimeJoin;
    }

    public
    static HandlerList getHandlerList() {
        return HANDLERS;
    }

    public RunePlayer getPlayer() {
        return player;
    }

    public Player getBukkitPlayer() {
        return player.asBukkitPlayer();
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLERS;
    }

    public boolean isFirstTimeJoin() {
        return isFirstTimeJoin;
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
