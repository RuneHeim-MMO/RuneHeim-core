package org.zuma.examplePlugin.events.payment;

import org.zuma.examplePlugin.core.redis.RedisEvent;

import java.util.UUID;

public class RankReloadEvent extends RedisEvent<RankReloadEvent.Payload> {
    private final Payload payload;

    public RankReloadEvent(Payload payload) {
        this.payload = payload;
    }

    public static class Payload extends RedisPayload {
        public UUID reloadUUID;

        public Payload(UUID reloadUUID) {
            this.reloadUUID = reloadUUID;
        }
    }

    @Override
    public Payload composePayload() {
        this.payload.replayAtOrigin = false;
        return payload;
    }
}
