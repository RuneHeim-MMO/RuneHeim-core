package org.zuma.examplePlugin.core.redis;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.logger.constants.LogLevel;
import org.zuma.examplePlugin.events.player.RunePlayerLevelUpEvent;

import javax.annotation.Nullable;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class EventDeserializer {
    public final static Map<String, Map.Entry<Class<? extends RedisEvent<?>>, Class<? extends RedisEvent.RedisPayload>>> eventMap = new HashMap<>();
    private final static Gson gson = new Gson();

    static {
        addEvent(RunePlayerLevelUpEvent.class, RunePlayerLevelUpEvent.Payload.class);
    }

    private static void addEvent(Class<? extends RedisEvent<?>> eventClass, Class<? extends RedisEvent.RedisPayload> payloadClass) {
        eventMap.put(eventClass.getSimpleName(), Map.entry(eventClass, payloadClass));
    }

    @Nullable
    public static RedisEvent.RedisPayload deserializeEventPayload(String incomingJson) {
        JsonObject jsonObject = RedisEvent.gson.fromJson(incomingJson, JsonObject.class);
        String eventName = jsonObject.get("event").getAsString();
        String serverId = jsonObject.get("serverId").getAsString();

        Map.Entry<Class<? extends RedisEvent<?>>, Class<? extends RedisEvent.RedisPayload>> matchingEntry = eventMap.get(eventName);

        Class<? extends RedisEvent<?>> eventClass = matchingEntry.getKey();
        Class<? extends RedisEvent.RedisPayload> payloadClass = matchingEntry.getValue();

        if (eventClass == null || payloadClass == null) {
            RuneQuestPlugin.getRuneLogger().log(LogLevel.WARN, "Unknown redis event/payload " + eventName + " received from server #" + serverId + "!");
            return null;
        }

        try {
            return gson.fromJson(incomingJson, payloadClass);
        } catch (Exception e) {
            RuneQuestPlugin.getRuneLogger().log(LogLevel.CRITICAL, "Failed to deserialize event " + eventName + " received from server #" + serverId + "!", e);
            return null;
        }
    }
}