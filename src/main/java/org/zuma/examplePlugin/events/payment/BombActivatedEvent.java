package org.zuma.examplePlugin.events.payment;

import org.zuma.examplePlugin.core.redis.RedisEvent;
import org.zuma.examplePlugin.domain.modifier.scopes.server.ServerModifierType;
import org.zuma.examplePlugin.domain.player.PlayerBombsContainer;

import java.util.UUID;

public class BombActivatedEvent extends RedisEvent<BombActivatedEvent.Payload> {
    private final Payload payload;

    public BombActivatedEvent(Payload payload) {
        this.payload = payload;
    }

    public static class Payload extends RedisPayload {
        public ServerModifierType type;
        public String activatedBy;

        public Payload(String activatedBy, ServerModifierType type) {
            this.type = type;
            this.activatedBy = activatedBy;
        }
    }

    @Override
    public Payload composePayload() {
        return payload;
    }
}
