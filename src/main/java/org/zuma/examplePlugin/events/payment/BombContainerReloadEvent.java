package org.zuma.examplePlugin.events.payment;

import org.zuma.examplePlugin.core.redis.RedisEvent;
import org.zuma.examplePlugin.domain.player.PlayerBombsContainer;

import java.util.UUID;

public class BombContainerReloadEvent extends RedisEvent<BombContainerReloadEvent.Payload> {
    private final Payload payload;

    public BombContainerReloadEvent(Payload payload) {
        this.payload = payload;
    }

    @Override
    public Payload composePayload() {
        return payload;
    }

    public static class Payload extends RedisPayload {
        public UUID reloadUUID;
        public PlayerBombsContainer newContainer;

        public Payload(UUID reloadUUID, PlayerBombsContainer newContainer) {
            this.reloadUUID = reloadUUID;
            this.newContainer = newContainer;
        }
    }
}
