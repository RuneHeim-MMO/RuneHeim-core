package org.zuma.examplePlugin.events.drops;

import org.zuma.examplePlugin.core.redis.RedisEvent;
import org.zuma.examplePlugin.domain.item.constants.ItemRarity;

public class RareDropEvent extends RedisEvent<RareDropEvent.Payload> {
    private final RareDropEvent.Payload payload;

    public RareDropEvent(RareDropEvent.Payload payload) {
        this.payload = payload;
        this.payload.replayAtOrigin = true;
    }

    @Override
    public RareDropEvent.Payload composePayload() {
        return payload;
    }

    public static class Payload extends RedisPayload {
        public final String playerName, itemName, mobName;
        public final ItemRarity tier;
        public final int amount;

        public Payload(String playerName,String mobName, String itemName, ItemRarity tier, int amount) {
            this.playerName = playerName;
            this.mobName = mobName;
            this.itemName = itemName;
            this.tier = tier;
            this.amount = amount;
        }
    }

}
