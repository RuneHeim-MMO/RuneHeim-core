package org.zuma.examplePlugin.events.player;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;
import org.zuma.examplePlugin.domain.player.RunePlayer;

public class RunePlayerLoadedEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();
    private final RunePlayer player;
    private final boolean isFirstTimeJoin;

    public RunePlayerLoadedEvent(RunePlayer player, boolean isFirstTimeJoin) {
        this.player = player;
        this.isFirstTimeJoin = isFirstTimeJoin;
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

    public
    static HandlerList getHandlerList() {
        return HANDLERS;
    }

    public boolean isFirstTimeJoin() {
        return isFirstTimeJoin;
    }
}
