package org.zuma.examplePlugin.events.territory;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;
import org.zuma.examplePlugin.domain.player.RunePlayer;
import org.zuma.examplePlugin.domain.territory.Territory;

public class TerritoryEnterEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();
    private final RunePlayer player;
    private final Territory territory;
    private final boolean discovered;

    public TerritoryEnterEvent(RunePlayer player, Territory territory, boolean discovered) {
        this.player = player;
        this.territory = territory;
        this.discovered = discovered;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLERS;
    }

    public
    static HandlerList getHandlerList() {
        return HANDLERS;
    }

    public Territory getTerritory() {
        return territory;
    }

    public RunePlayer getPlayer() {
        return player;
    }

    public boolean isDiscovered() {
        return discovered;
    }
}
