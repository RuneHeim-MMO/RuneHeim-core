package org.zuma.examplePlugin.events.fairy;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;
import org.zuma.examplePlugin.domain.item.impl.fairy.Fairy;
import org.zuma.examplePlugin.domain.item.impl.fairy.FairyFood;
import org.zuma.examplePlugin.domain.item.impl.fairy.FairyFruit;
import org.zuma.examplePlugin.domain.item.state.states.FairyState;
import org.zuma.examplePlugin.domain.player.RunePlayer;

public class FairyUpgradeEvent extends Event implements Cancellable {
    private static final HandlerList HANDLERS = new HandlerList();
    private final RunePlayer player;
    private final FairyFruit upgradeType;
    private final Fairy fairy;
    private final FairyState state;
    private boolean cancelled;

    public FairyUpgradeEvent(RunePlayer player, FairyFruit upgradeType, Fairy fairy, FairyState state) {
        this.player = player;
        this.upgradeType = upgradeType;
        this.fairy = fairy;
        this.state = state;
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

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    public Fairy getFairy() {
        return fairy;
    }

    public FairyState getState() {
        return state;
    }

    public FairyFruit getUpgradeType() {
        return upgradeType;
    }
}
