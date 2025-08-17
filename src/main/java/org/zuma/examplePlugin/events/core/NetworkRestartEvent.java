package org.zuma.examplePlugin.events.core;

import org.bukkit.event.Cancellable;
import org.zuma.examplePlugin.core.constants.XpSource;
import org.zuma.examplePlugin.core.redis.RedisEvent;
import org.zuma.examplePlugin.domain.player.RunePlayer;
import org.zuma.examplePlugin.domain.skill.constants.SkillType;

import java.util.UUID;

public class NetworkRestartEvent extends RedisEvent<NetworkRestartEvent.Payload> {
    private final Payload payload;

    public static class Payload extends RedisPayload {
        public Long restartAt;
        public String reason, initiator;

        public Payload(long restartAt, String reason, String initiator) {
            this.restartAt = restartAt + 500L; // Ping compensation
            this.reason = reason;
            this.initiator = initiator;
        }
    }

    public NetworkRestartEvent(Payload payload) {
        this.payload = payload;
        this.payload.replayAtOrigin = true;
    }

    @Override
    public Payload composePayload() {
        return payload;
    }

}
