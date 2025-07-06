package org.zuma.examplePlugin.core.utils;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class Throttler {
    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
    private final long intervalMillis;
    private long lastRun = 0;
    private ScheduledFuture<?> trailingFuture = null;

    public Throttler(long intervalMillis) {
        this.intervalMillis = intervalMillis;
    }

    public synchronized void throttle(Runnable task) {
        long now = System.currentTimeMillis();
        long elapsed = now - lastRun;

        if (elapsed >= intervalMillis) {
            lastRun = now;
            task.run();
        } else {
            // Cancel previously scheduled trailing call
            if (trailingFuture != null && !trailingFuture.isDone()) {
                trailingFuture.cancel(false);
            }

            // Schedule trailing call to run after remaining time
            long delay = intervalMillis - elapsed;
            trailingFuture = scheduler.schedule(() -> {
                synchronized (Throttler.this) {
                    lastRun = System.currentTimeMillis();
                    task.run();
                }
            }, delay, TimeUnit.MILLISECONDS);
        }
    }

    public void shutdown() {
        scheduler.shutdown();
    }
}
