package org.zuma.examplePlugin.core.redis;

import com.google.gson.Gson;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;
import org.zuma.examplePlugin.core.config.ConfigManager;

import java.util.ArrayList;
import java.util.List;

public abstract class RedisEvent<T extends RedisEvent.RedisPayload> extends Event {
    protected final static Gson gson = new Gson();
    private static final HandlerList HANDLERS = new HandlerList();
    boolean isForwarded = false;

    protected RedisEvent() {
    }

    public
    static HandlerList getHandlerList() {
        return HANDLERS;
    }

    public T getComposedPayload() {
        T composedPayload = composePayload();

        composedPayload.event = this.getClass().getSimpleName();
        composedPayload.serverId = ConfigManager.getServerId();
        composedPayload.timestamp = System.currentTimeMillis();

        return composedPayload;
    }

    public abstract T composePayload();

    public boolean isCancelled() {
        if (!(this instanceof Cancellable c)) {
            return false;
        }

        return c.isCancelled();
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLERS;
    }

    public boolean isForwarded() {
        return isForwarded;
    }

    public void setForwarded(boolean forwarded) {
        isForwarded = forwarded;
    }

    public static class RedisPayload {
        public String event;
        public Integer serverId;
        public Long timestamp;
        public List<Integer> targetServers = new ArrayList<>(); // Empty - broadcast to all
        public boolean replayAtOrigin = true;
    }


}
