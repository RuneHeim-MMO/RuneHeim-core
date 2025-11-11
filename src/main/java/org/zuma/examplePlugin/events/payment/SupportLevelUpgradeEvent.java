package org.zuma.examplePlugin.events.payment;

import org.zuma.examplePlugin.core.redis.RedisEvent;
import org.zuma.examplePlugin.domain.payments.SupporterLevel;

import java.util.UUID;

public class SupportLevelUpgradeEvent extends RedisEvent<SupportLevelUpgradeEvent.Payload> {
    private final Payload payload;

    public SupportLevelUpgradeEvent(Payload payload) {
        this.payload = payload;
    }

    public static class Payload extends RedisPayload {
        public String username;
        public SupporterLevel newLevel;

        public Payload(String username, SupporterLevel newLevel) {
            this.username = username;
            this.newLevel = newLevel;
        }
    }

    @Override
    public Payload composePayload() {
        return payload;
    }
}
