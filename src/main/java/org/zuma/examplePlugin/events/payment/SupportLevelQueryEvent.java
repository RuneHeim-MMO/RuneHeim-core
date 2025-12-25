package org.zuma.examplePlugin.events.payment;

import org.zuma.examplePlugin.core.redis.RedisEvent;

import java.util.UUID;

public class SupportLevelQueryEvent extends RedisEvent<SupportLevelQueryEvent.Payload> {
    private final Payload payload;

    public SupportLevelQueryEvent(Payload payload) {
        this.payload = payload;
    }

    @Override
    public Payload composePayload() {
        return payload;
    }

    public static class Payload extends RedisPayload {
        public UUID reloadUUID;

        public Payload(UUID reloadUUID) {
            this.reloadUUID = reloadUUID;
        }
    }
}
