package org.zuma.examplePlugin.core.redis;

import com.google.gson.Gson;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.zuma.examplePlugin.RuneQuestPlugin;

public abstract class RedisEvent<T extends RedisEvent.RedisPayload> extends Event {
    protected final static Gson gson = new Gson();

    public static class RedisPayload {
        public String event;
        public Integer serverId;
        public Long timestamp;
    }

    protected RedisEvent() {
    }

    protected abstract Class<T> getPayloadClass();

    public T getPayload(String payload) {
        return gson.fromJson(payload, getPayloadClass());
    }

    public String getSerializedPayload() {
        T composedPayload = composePayload();

        composedPayload.event = this.getClass().getSimpleName();
        composedPayload.serverId = RuneQuestPlugin.SERVER_ID;
        composedPayload.timestamp = System.currentTimeMillis();

        return gson.toJson(composedPayload);
    }

    public abstract T composePayload();

    public boolean isCancelled() {
        if (!(this instanceof Cancellable c)) {
            return false;
        }

        return c.isCancelled();
    }
}
