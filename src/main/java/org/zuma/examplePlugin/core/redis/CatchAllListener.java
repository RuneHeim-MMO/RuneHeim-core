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
        String stringifiedPayload = gson.toJson(payload);

        System.out.println("RedisEvent to be fired: " + event.getClass().getSimpleName() + " payload " + stringifiedPayload);

        // REDIS BROADCAST HERE
        event.setForwarded(true);

        GlobalEventHandler.onRedisEventReceived(stringifiedPayload);

    }


}
