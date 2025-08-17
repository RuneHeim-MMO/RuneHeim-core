package org.zuma.examplePlugin.core.redis;

import com.google.gson.Gson;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.zuma.examplePlugin.RuneQuestPlugin;

public class CatchAllListener implements Listener {
    private static final Gson gson = new Gson();

    @EventHandler
    public static void onRedisEventBroadcastedInternally(RedisEvent<?> event) {
        String payload = gson.toJson(event.getComposedPayload());

        event.setForwarded(true);
        RedisConnection.broadcastEvent(payload);
    }


}
