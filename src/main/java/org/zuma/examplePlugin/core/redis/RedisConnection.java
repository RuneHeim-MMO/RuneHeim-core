package org.zuma.examplePlugin.core.redis;

import io.github.cdimascio.dotenv.Dotenv;
import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.async.RedisAsyncCommands;
import io.lettuce.core.api.sync.RedisCommands;
import io.lettuce.core.pubsub.RedisPubSubListener;
import io.lettuce.core.pubsub.StatefulRedisPubSubConnection;
import io.lettuce.core.pubsub.api.async.RedisPubSubAsyncCommands;
import org.bukkit.Bukkit;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.logger.constants.LogLevel;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

public class RedisConnection {
    private static RedisClient client = null;
    private static StatefulRedisPubSubConnection<String, String> pubSubConnection = null;
    private static StatefulRedisConnection<String, String> pubConnection = null;

    public static void init(Dotenv dotenv) {
        try {
            if (client != null) {
                RuneQuestPlugin.getRuneLogger().log(LogLevel.INFO, "Existing redis client exists, attempting to reuse.");
            } else {
                String url = dotenv.get("REDIS_CON_STR");
                client = RedisClient.create("redis://" + url);
            }

            pubSubConnection = client.connectPubSub();
            pubConnection = client.connect();

            RedisPubSubAsyncCommands<String, String> pubSub = pubSubConnection.async();
            pubSubConnection.addListener(new RedisPubSubListener<String, String>() {
                @Override
                public void message(String channel, String message) {
                    System.out.println("Received message: " + message + " on channel: " + channel);
                    Bukkit.getScheduler().runTask(RuneQuestPlugin.getInstance(), () -> GlobalEventHandler.onRedisEventReceived(message));
                }

                @Override
                public void message(String pattern, String channel, String message) {
                }

                @Override
                public void subscribed(String channel, long count) {
                }

                @Override
                public void psubscribed(String pattern, long count) {
                }

                @Override
                public void unsubscribed(String channel, long count) {
                }

                @Override
                public void punsubscribed(String pattern, long count) {
                }
            });

            for (int id = -1; id < 100; id++) {
                pubSub.subscribe("server-" + id).get();
            }

            pubSub.subscribe("global-events").get();
            RuneQuestPlugin.getRuneLogger().log(LogLevel.INFO, "Redis connected: true. Global events available.");
        } catch (Exception e) {
            RuneQuestPlugin.getRuneLogger().log(LogLevel.CRITICAL, "Failed redis connection init. Global events halted.", e);
        }

    }

    public static void broadcastEvent(String data, List<Integer> targetServerIds) {
        if (pubConnection == null) {
            RuneQuestPlugin.getRuneLogger().log(LogLevel.WARN, "Failed publishing event. Payload: " + data + ". pubConnection is null!");
            return;
        }

        Bukkit.getScheduler().runTaskAsynchronously(RuneQuestPlugin.getInstance(), () -> {
            try {
                RedisAsyncCommands<String, String> asyncCommands = pubConnection.async();

                if (targetServerIds.isEmpty()) {
                    asyncCommands.publish("global-events", data);
                    return;
                } else {
                    for (Integer serverId : targetServerIds) {
                        asyncCommands.publish("server-" + serverId, data);
                    }
                }
            } catch (Exception e) {
                RuneQuestPlugin.getRuneLogger().log(LogLevel.WARN, "Failed publishing event. Payload: " + data, e);
            }
        });
    }


    public static void onDisable() {
        if (pubConnection != null) {
            pubConnection.close();
            pubConnection = null;
        }
        if (pubSubConnection != null) {
            pubSubConnection.close();
            pubSubConnection = null;
        }
        if (client != null) {
            client.shutdown();
        }
        RuneQuestPlugin.getRuneLogger().log(LogLevel.INFO, "Redis connection cleared.");
    }
}
