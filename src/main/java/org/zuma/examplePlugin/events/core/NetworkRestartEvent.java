package org.zuma.examplePlugin.events.core;

import org.zuma.examplePlugin.core.redis.RedisEvent;

public class NetworkRestartEvent extends RedisEvent<NetworkRestartEvent.Payload> {
    private final Payload payload;

    public NetworkRestartEvent(Payload payload) {
        this.payload = payload;
        this.payload.replayAtOrigin = true;
    }

    @Override
    public Payload composePayload() {
        return payload;
    }

    public static class Payload extends RedisPayload {
        public Long restartAt;
        public String reason, initiator;

        public Payload(long restartAt, String reason, String initiator) {
            this.restartAt = restartAt + 500L; // Ping compensation
            this.reason = reason;
            this.initiator = initiator;
        }
    }

}
