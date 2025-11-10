package org.zuma.examplePlugin.events.payment;

import org.zuma.examplePlugin.core.redis.RedisEvent;

import java.util.UUID;

public class SupportLevelReloadEvent extends RedisEvent<SupportLevelReloadEvent.Payload> {
    private final Payload payload;

    public SupportLevelReloadEvent(Payload payload) {
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
