package org.zuma.examplePlugin.core.redis;

import com.google.gson.Gson;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.zuma.examplePlugin.RuneQuestPlugin;

public class CatchAllListener implements Listener {
    private static final Gson gson = new Gson();

    @EventHandler
    public static void onRedisEventBroadcastedInternally(RedisEvent<?> event) {
        RedisEvent.RedisPayload payload = event.getComposedPayload();
        String serializedPayload = gson.toJson(payload);

        event.setForwarded(true);
        RedisConnection.broadcastEvent(serializedPayload, payload.targetServers);
    }


}
