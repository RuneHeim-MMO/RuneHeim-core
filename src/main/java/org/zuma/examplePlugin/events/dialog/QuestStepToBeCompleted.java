package org.zuma.examplePlugin.events.dialog;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class QuestStepToBeCompleted extends Event {
    private static final HandlerList HANDLERS = new HandlerList();
    private final String stableDialogStepId;
    private final UUID playerUUID;

    public QuestStepToBeCompleted(UUID playerUUID, String stableDialogStepId) {
        this.stableDialogStepId = stableDialogStepId;
        this.playerUUID = playerUUID;
    }

    public String getStableDialogStepId() {
        return stableDialogStepId;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLERS;
    }

    public
    static HandlerList getHandlerList() {
        return HANDLERS;
    }


    public UUID getPlayerUUID() {
        return playerUUID;
    }
}
