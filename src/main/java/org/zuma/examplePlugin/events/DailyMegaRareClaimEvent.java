package org.zuma.examplePlugin.events;

import org.zuma.examplePlugin.core.redis.RedisEvent;
import org.zuma.examplePlugin.domain.item.constants.ItemRarity;

public class DailyMegaRareClaimEvent extends RedisEvent<DailyMegaRareClaimEvent.Payload> {
    private final DailyMegaRareClaimEvent.Payload payload;

    public DailyMegaRareClaimEvent(DailyMegaRareClaimEvent.Payload payload) {
        this.payload = payload;
        this.payload.replayAtOrigin = true;
    }

    @Override
    public Payload composePayload() {
        return payload;
    }

    public static class Payload extends RedisPayload {
        public final String playerName, itemName;
        public final ItemRarity tier;
        public final int amount;

        public Payload(String playerName, String itemName, ItemRarity tier, int amount) {
            this.playerName = playerName;
            this.itemName = itemName;
            this.tier = tier;
            this.amount = amount;
        }
    }

}
