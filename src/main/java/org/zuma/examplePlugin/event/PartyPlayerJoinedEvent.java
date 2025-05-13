package org.zuma.examplePlugin.event;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;
import org.zuma.examplePlugin.party.Party;
import org.zuma.examplePlugin.player.RunePlayer;

public class PartyPlayerJoinedEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();
    private final Party party;
    private final RunePlayer player;

    public PartyPlayerJoinedEvent(Party party, RunePlayer player) {
        this.party = party;
        this.player = player;
    }

    public Party getParty() {
        return party;
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
}
