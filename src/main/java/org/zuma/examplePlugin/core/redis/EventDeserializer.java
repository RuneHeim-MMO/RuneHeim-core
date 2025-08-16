package org.zuma.examplePlugin.core.redis;

import com.google.gson.JsonObject;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.logger.constants.LogLevel;
import org.zuma.examplePlugin.events.player.RunePlayerLevelUpEvent;

import javax.annotation.Nullable;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class EventDeserializer {
    private final static Map<String, Class<? extends RedisEvent<?>>> eventMap = new HashMap<>();

    static {
        eventMap.put("RunePlayerLevelUpEvent", RunePlayerLevelUpEvent.class);
    }

    @Nullable
    public static <T extends RedisEvent.RedisPayload> T deserialize(String json, Class<T> payloadClass) {
        RedisEvent.RedisPayload payload = EventDeserializer.deserializeEventPayload(json);

        if (payloadClass.isInstance(payload)) {
            return payloadClass.cast(payload);
        }

        return null;
    }

    @Nullable
    private static RedisEvent.RedisPayload deserializeEventPayload(String incomingJson) {
        JsonObject jsonObject = RedisEvent.gson.fromJson(incomingJson, JsonObject.class);
        String eventName = jsonObject.get("event").getAsString();
        String serverId = jsonObject.get("server_id").getAsString();

        Class<? extends RedisEvent<?>> eventClass = eventMap.get(eventName);

        if (eventClass == null) {
            RuneQuestPlugin.getRuneLogger().log(LogLevel.WARN, "Unknown redis event " + eventName + " received from #" + serverId + "!");
            return null;
        }

        try {
            RedisEvent<?> eventInstance = eventClass.getDeclaredConstructor().newInstance();
            return eventInstance.getPayload(incomingJson);
        } catch (Exception e) {
            RuneQuestPlugin.getRuneLogger().log(LogLevel.CRITICAL, "Failed to deserialize event " + eventName + " received from #" + serverId + "!", e);
            return null;
        }
    }
}