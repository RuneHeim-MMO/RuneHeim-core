package org.zuma.examplePlugin.events.spawnzone;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;
import org.zuma.examplePlugin.domain.spawnzone.base.AbstractSpawnZone;

public class SpawnZoneWaveEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();
    private final AbstractSpawnZone zone;

    public SpawnZoneWaveEvent(AbstractSpawnZone zone) {
        this.zone = zone;
    }

    public
    static HandlerList getHandlerList() {
        return HANDLERS;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLERS;
    }

    public AbstractSpawnZone getZone() {
        return zone;
    }
}
