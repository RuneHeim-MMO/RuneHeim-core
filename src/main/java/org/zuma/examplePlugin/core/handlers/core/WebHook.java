package org.zuma.examplePlugin.core.handlers.core;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import io.github.cdimascio.dotenv.Dotenv;
import org.bukkit.Bukkit;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.logger.constants.LogLevel;
import org.zuma.examplePlugin.events.core.NetworkRestartEvent;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class WebHook {
    private static final int HTTP_PORT = 8080;
    private static HttpServer server;
    private static final AtomicInteger threadCounter = new AtomicInteger(0);
    private static final int MAX_BODY_BYTES = 128 * 1024; // 32 KB

    private static final ThreadPoolExecutor executor = new ThreadPoolExecutor(
            2,                // core pool size
            4,                // maximum pool size
            60L, TimeUnit.SECONDS, // keep-alive for extra threads
            new LinkedBlockingQueue<>(50), // bounded queue
            r -> {            // simple thread factory to name threads
                Thread t = new Thread(r, "webhook-http-%d".formatted(threadCounter.incrementAndGet()));
                t.setDaemon(false);
                return t;
            },
            new ThreadPoolExecutor.CallerRunsPolicy() // backpressure: caller runs task when overloaded
    );

    public static void initializeServer(Dotenv dotenv) {
        String secretKey = dotenv.get("WEB_HOOK_KEY", "default-secret");

        try {
            // bind to all interfaces; change to "127.0.0.1" if local-only
            server = HttpServer.create(new InetSocketAddress(HTTP_PORT), 0);
            server.setExecutor(executor);

            server.createContext("/restart", (HttpExchange exchange) -> {
                try {
                    if (!"POST".equalsIgnoreCase(exchange.getRequestMethod())) {
                        exchange.sendResponseHeaders(405, -1);
                        return;
                    }

                    Map<String, String> params = new HashMap<>(parseQuery(exchange.getRequestURI().getQuery()));

                    // read up to MAX_BODY_BYTES
                    try (InputStream is = exchange.getRequestBody()) {
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        byte[] buf = new byte[4096];
                        int read;
                        int total = 0;
                        while ((read = is.read(buf)) != -1) {
                            total += read;
                            if (total > MAX_BODY_BYTES) {
                                // too large
                                exchange.sendResponseHeaders(413, -1);
                                return;
                            }
                            baos.write(buf, 0, read);
                        }
                        String body = baos.toString(StandardCharsets.UTF_8);
                        if (!body.isEmpty()) params.putAll(parseQuery(body));
                    }

                    String keyParam = params.get("key");
                    String secondsParam = params.get("seconds");
                    String reasonParam = params.get("reason");
                    String senderParam = params.get("sender");

                    if (!Objects.equals(secretKey, keyParam)) {
                        exchange.sendResponseHeaders(401, -1);
                        return;
                    }
                    if (secondsParam == null || reasonParam == null || senderParam == null) {
                        exchange.sendResponseHeaders(400, -1);
                        return;
                    }

                    long restartInMillis;
                    try {
                        restartInMillis = Long.parseLong(secondsParam) * 1000L;
                    } catch (NumberFormatException nfe) {
                        exchange.sendResponseHeaders(400, -1);
                        return;
                    }

                    Bukkit.getScheduler().runTask(RuneQuestPlugin.getInstance(), () -> {
                        NetworkRestartEvent.Payload payload =
                                new NetworkRestartEvent.Payload(System.currentTimeMillis() + restartInMillis, reasonParam, senderParam);
                        Bukkit.getServer().getPluginManager().callEvent(new NetworkRestartEvent(payload));
                    });

                    byte[] response = "OK".getBytes(StandardCharsets.UTF_8);
                    exchange.sendResponseHeaders(200, response.length);
                    try (OutputStream os = exchange.getResponseBody()) {
                        os.write(response);
                    }
                } catch (Exception e) {
                    try { exchange.sendResponseHeaders(500, -1); } catch (IOException ignored) {}
                    RuneQuestPlugin.getRuneLogger().log(LogLevel.CRITICAL, "Webhook handler error", e);
                } finally {
                    exchange.close();
                }
            });

            server.start();
            RuneQuestPlugin.getRuneLogger().log(LogLevel.INFO, "HTTP hook server started at port " + HTTP_PORT);
        } catch (Exception e) {
            RuneQuestPlugin.getRuneLogger().log(LogLevel.CRITICAL, "Failed to start HTTP hook server.", e);
        }
    }

    private static Map<String, String> parseQuery(String query) {
        if (query == null || query.isEmpty()) return Collections.emptyMap();
        return Arrays.stream(query.split("&"))
                .map(s -> s.split("=", 2))
                .filter(a -> a.length == 2)
                .collect(Collectors.toMap(a -> urlDecode(a[0]), a -> urlDecode(a[1])));
    }

    private static String urlDecode(String s) {
        return URLDecoder.decode(s, StandardCharsets.UTF_8);
    }

    public static void cleanUp() {
        if (server == null) return;
        server.stop(1);
        RuneQuestPlugin.getRuneLogger().log(LogLevel.INFO, "HTTP server stopped.");
        try {
            executor.shutdown();
            if (!executor.awaitTermination(5, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
            executor.shutdownNow();
        }
    }
}
