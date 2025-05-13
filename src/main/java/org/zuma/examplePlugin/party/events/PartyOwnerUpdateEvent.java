package org.zuma.examplePlugin.party.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;
import org.zuma.examplePlugin.party.Party;
import org.zuma.examplePlugin.player.RunePlayer;

public class PartyOwnerUpdateEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();
    private final Party party;
    private final RunePlayer newOwner;

    public PartyOwnerUpdateEvent(Party party, RunePlayer newOwner) {
        this.party = party;
        this.newOwner = newOwner;
    }

    public Party getParty() {
        return party;
    }

    public RunePlayer getNewOwner() {
        return newOwner;
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
