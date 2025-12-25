package org.zuma.examplePlugin.events.party;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;
import org.zuma.examplePlugin.domain.party.Party;
import org.zuma.examplePlugin.domain.player.RunePlayer;

import java.util.List;

public class PartyMessageEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();
    private final Party party;
    private final RunePlayer sender;
    private final List<RunePlayer> receivers;
    private final String content;

    public PartyMessageEvent(Party party, RunePlayer sender, List<RunePlayer> receivers, String content) {
        this.party = party;
        this.sender = sender;
        this.receivers = receivers;
        this.content = content;
    }

    public
    static HandlerList getHandlerList() {
        return HANDLERS;
    }

    public Party getParty() {
        return party;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLERS;
    }

    public List<RunePlayer> getReceivers() {
        return receivers;
    }

    public RunePlayer getSender() {
        return sender;
    }

    public String getContent() {
        return content;
    }
}
