package org.zuma.examplePlugin.events.referral;

import org.zuma.examplePlugin.core.redis.RedisEvent;

import java.util.UUID;

public class ReferralReachedThresholdEvent extends RedisEvent<ReferralReachedThresholdEvent.Payload> {
    private final Payload payload;

    public ReferralReachedThresholdEvent(Payload payload) {
        this.payload = payload;
    }

    @Override
    public Payload composePayload() {
        return payload;
    }

    public static class Payload extends RedisPayload {
        public UUID menteeUUID, mentorUUID;
        public String menteeUsername;

        public Payload(UUID menteeUUID, UUID mentorUUID, String menteeUsername) {
            this.menteeUUID = menteeUUID;
            this.mentorUUID = mentorUUID;
            this.menteeUsername = menteeUsername;
        }
    }
}
