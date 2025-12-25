package org.zuma.examplePlugin.core.redis;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.logger.constants.LogLevel;
import org.zuma.examplePlugin.events.core.NetworkRestartEvent;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public class EventDeserializer {
    public final static Map<String, Map.Entry<Class<? extends RedisEvent<?>>, Class<? extends RedisEvent.RedisPayload>>> eventMap = new HashMap<>();
    private final static Gson gson = new Gson();

    static {
        addEvent(NetworkRestartEvent.class, NetworkRestartEvent.Payload.class);
    }

    public static void addEvent(Class<? extends RedisEvent<?>> eventClass, Class<? extends RedisEvent.RedisPayload> payloadClass) {
        eventMap.put(eventClass.getSimpleName(), Map.entry(eventClass, payloadClass));
    }

    @Nullable
    public static Map.Entry<Class<? extends RedisEvent<?>>, RedisEvent.RedisPayload> deserializeEvent(String incomingJson) {
        JsonObject jsonObject = RedisEvent.gson.fromJson(incomingJson, JsonObject.class);
        String eventName = jsonObject.get("event").getAsString();

        Map.Entry<Class<? extends RedisEvent<?>>, Class<? extends RedisEvent.RedisPayload>> matchingEntry = eventMap.get(eventName);

        if (matchingEntry == null) {
            RuneQuestPlugin.getRuneLogger().log(LogLevel.WARN, "Unknown redis event name " + eventName + " received! Did you register it?");
            return null;
        }

        Class<? extends RedisEvent<?>> eventClass = matchingEntry.getKey();
        Class<? extends RedisEvent.RedisPayload> payloadClass = matchingEntry.getValue();

        if (eventClass == null || payloadClass == null) {
            RuneQuestPlugin.getRuneLogger().log(LogLevel.WARN, "Unknown redis event/payload " + eventName + " received!");
            return null;
        }

        try {
            // Extract the nested 'payload' object
            JsonObject nestedPayload = jsonObject.getAsJsonObject("payload");
            RedisEvent.RedisPayload payload = gson.fromJson(nestedPayload, payloadClass);

            // Optionally get serverId from payload if needed
            String serverId = nestedPayload.has("serverId") ? nestedPayload.get("serverId").getAsString() : "unknown";

            return Map.entry(eventClass, payload);
        } catch (Exception e) {
            RuneQuestPlugin.getRuneLogger().log(LogLevel.CRITICAL, "Failed to deserialize event " + eventName + "!", e);
            return null;
        }
    }

}