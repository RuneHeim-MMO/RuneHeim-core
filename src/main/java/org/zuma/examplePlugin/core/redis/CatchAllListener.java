package org.zuma.examplePlugin.core.redis;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class CatchAllListener implements Listener {
    private static final Gson gson = new Gson();

    @EventHandler
    public static void onRedisEventBroadcastedInternally(RedisEvent<?> event) {
        RedisEvent.RedisPayload payload = event.getComposedPayload();
        JsonObject json = gson.toJsonTree(payload).getAsJsonObject();

        JsonObject result = new JsonObject();
        result.add("event", json.get("event"));
        json.remove("event");
        result.add("payload", json);

        String serializedPayload = gson.toJson(result);

        event.setForwarded(true);
        RedisConnection.broadcastEvent(serializedPayload, payload.targetServers);
    }


}
