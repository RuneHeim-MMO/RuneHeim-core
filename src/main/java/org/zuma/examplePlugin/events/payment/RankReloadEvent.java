package org.zuma.examplePlugin.events.payment;

import org.zuma.examplePlugin.core.permissions.roles.UpgradedRole;
import org.zuma.examplePlugin.core.redis.RedisEvent;

import java.util.UUID;

public class RankReloadEvent extends RedisEvent<RankReloadEvent.Payload> {
    private final Payload payload;

    public RankReloadEvent(Payload payload) {
        this.payload = payload;
    }

    @Override
    public Payload composePayload() {
        return payload;
    }

    public static class Payload extends RedisPayload {
        public UUID reloadUUID;
        public UpgradedRole newRole;

        public Payload(UUID reloadUUID, UpgradedRole newRole) {
            this.reloadUUID = reloadUUID;
            this.newRole = newRole;
        }
    }
}
