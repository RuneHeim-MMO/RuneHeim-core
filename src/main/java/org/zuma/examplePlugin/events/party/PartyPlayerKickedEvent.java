package org.zuma.examplePlugin.events.party;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;
import org.zuma.examplePlugin.domain.party.Party;
import org.zuma.examplePlugin.domain.player.RunePlayer;

public class PartyPlayerKickedEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();
    private final Party party;
    private final RunePlayer kickedPlayer;


    public PartyPlayerKickedEvent(Party party, RunePlayer kickedPlayer) {
        this.party = party;
        this.kickedPlayer = kickedPlayer;
    }

    public
    static HandlerList getHandlerList() {
        return HANDLERS;
    }

    public Party getParty() {
        return party;
    }

    public RunePlayer getKickedPlayer() {
        return kickedPlayer;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLERS;
    }
}
