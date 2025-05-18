package org.zuma.examplePlugin.events.party;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;
import org.zuma.examplePlugin.domain.party.Party;

public class PartyCreatedEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();
    private final Party party;

    public PartyCreatedEvent(Party party) {
        this.party = party;
    }

    public Party getParty() {
        return party;
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
