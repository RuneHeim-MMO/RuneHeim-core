package org.zuma.examplePlugin.core.redis;

import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.config.ConfigManager;
import org.zuma.examplePlugin.core.logger.constants.LogLevel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class GlobalEventHandler {
    private static final Map<Class<? extends RedisEvent<?>>, List<Consumer<RedisEvent.RedisPayload>>> handlers = new HashMap<>();

    public static void registerEvent(Class<? extends RedisEvent<?>> eventClass, Class<? extends RedisEvent.RedisPayload> payloadClass) {
        EventDeserializer.addEvent(eventClass, payloadClass);
    }

    public static void clearHandlers() {
        handlers.clear();
    }

    @SuppressWarnings("unchecked")
    private static <T extends RedisEvent<?>> void acceptConsumer(Consumer<T> consumer, RedisEvent.RedisPayload payload, Class<?> payloadClass) {
        consumer.accept((T) payloadClass.cast(payload));
    }

    public static void onRedisEventReceived(String payloadStr) {
        try {
            Map.Entry<Class<? extends RedisEvent<?>>, RedisEvent.RedisPayload> deserializeEvent = EventDeserializer.deserializeEvent(payloadStr);

            if (deserializeEvent == null) {
                return;
            }

            handlers.forEach((eventClass, handlerList) -> {

                if (!eventClass.equals(deserializeEvent.getKey())) {
                    return;
                }

                for (Consumer<RedisEvent.RedisPayload> handler : handlerList) {
                    var payload = deserializeEvent.getValue();

                    if (shouldHandleEvent(payload)) {
                        handler.accept(payload);
                    }
                }

            });
        } catch (Exception e) {
            RuneQuestPlugin.getRuneLogger().log(LogLevel.CRITICAL, "Failed to handle redis payload! Input string: " + payloadStr, e);
        }
    }

    public static <T extends RedisEvent.RedisPayload, E extends RedisEvent<T>> void addListener(
            Class<E> eventClass,
            Consumer<T> payloadConsumer
    ) {
        // wrapper takes RedisPayload at dispatch-time and forwards to user consumer after casting
        Consumer<RedisEvent.RedisPayload> wrapper = (RedisEvent.RedisPayload payload) -> {
            // safe cast is guaranteed by the runtime check in onRedisEventReceived()
            @SuppressWarnings("unchecked")
            T t = (T) payload;
            payloadConsumer.accept(t);
        };

        handlers.computeIfAbsent(eventClass, k -> new ArrayList<>()).add(wrapper);
    }


    public static boolean shouldHandleEvent(RedisEvent.RedisPayload payload) {
        if (payload.targetServers.contains(ConfigManager.getServerId())) {
            return true;
        }

        if (payload.targetServers.isEmpty() || payload.serverId == ConfigManager.getServerId() && payload.replayAtOrigin) {
            return true;
        }

        return false;

    }

}
