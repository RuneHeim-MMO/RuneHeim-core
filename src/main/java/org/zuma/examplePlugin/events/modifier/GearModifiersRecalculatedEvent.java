package org.zuma.examplePlugin.events.modifier;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;
import org.zuma.examplePlugin.domain.player.RunePlayer;

public class GearModifiersRecalculatedEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();
    private final RunePlayer player;

    public GearModifiersRecalculatedEvent(RunePlayer player) {
        this.player = player;
    }

    public RunePlayer getPlayer() {
        return player;
    }

    public Player getBukkitPlayer() {
        return player.getPlayer();
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
