package org.zuma.examplePlugin.core.redis;

import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class CatchAllListener implements Listener {

    @EventHandler
    public void onRedisEvent(RedisEvent<?> event) {
        String payload = event.getSerializedPayload();
        System.out.println("RedisEvent to be fired: " + event.getClass().getSimpleName() + " payload " + payload);
    }

}
