package org.zuma.examplePlugin.events.inventory;

import org.zuma.examplePlugin.core.redis.RedisEvent;

import java.util.UUID;

public class RuneStorageInventoryOpenEvent extends RedisEvent<RuneStorageInventoryOpenEvent.Payload> {
    private final Payload payload;

    public RuneStorageInventoryOpenEvent(Payload payload) {
        this.payload = payload;
    }

    @Override
    public Payload composePayload() {
        this.payload.replayAtOrigin = false;
        return payload;
    }

    public static class Payload extends RedisPayload {
        public String senderName;
        public UUID senderUUID;
        public UUID containerUUID;

        public Payload(String senderName, UUID senderUUID, UUID containerUUID) {
            this.senderName = senderName;
            this.senderUUID = senderUUID;
            this.containerUUID = containerUUID;
        }
    }
}
