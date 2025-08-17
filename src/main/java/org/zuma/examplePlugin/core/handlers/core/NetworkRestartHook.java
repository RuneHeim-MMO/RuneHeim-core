package org.zuma.examplePlugin.core.handlers.core;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import io.github.cdimascio.dotenv.Dotenv;
import org.bukkit.Bukkit;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.logger.constants.LogLevel;
import org.zuma.examplePlugin.events.core.NetworkRestartEvent;

import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.Arrays;
import java.util.Map;

public class NetworkRestartHook {
    private static HttpServer server;

    public static void initializeServer(Dotenv dotenv) {
        String secretKey = dotenv.get("WEB_HOOK_KEY", "43fk02309g235kg092309k4fk903");

        try {
            server = HttpServer.create(new InetSocketAddress(8080), 0);
            server.createContext("/restart", (HttpExchange exchange) -> {
                if ("POST".equals(exchange.getRequestMethod())) {
/* Test */
                    try {

                        String query = exchange.getRequestURI().getQuery();
                        Map<String, String> params = parseQuery(query);

                        String keyParam = params.get("key");
                        String secondsParam = params.get("seconds");
                        String reasonParam = params.get("reason");
                        String senderParam = params.get("sender");


                        if (secretKey.equals(keyParam) && secondsParam != null && reasonParam != null && senderParam != null) {
                            long restartIn = Long.parseLong(secondsParam) * 1000L;

                            Bukkit.getScheduler().runTask(RuneQuestPlugin.getInstance(), () -> {
                                NetworkRestartEvent.Payload payload = new NetworkRestartEvent.Payload(System.currentTimeMillis() + restartIn, reasonParam, senderParam);
                                Bukkit.getServer().getPluginManager().callEvent(new NetworkRestartEvent(payload));
                            });
                        }

                        String response = "OK";
                        exchange.sendResponseHeaders(200, response.getBytes().length);
                        try (OutputStream os = exchange.getResponseBody()) {
                            os.write(response.getBytes());
                        }
                    } catch (Exception e) {
                        exchange.sendResponseHeaders(400, -1);
                    }
                } else {
                    exchange.sendResponseHeaders(405, -1); // Method Not Allowed
                }
            });
            server.start();
            RuneQuestPlugin.getRuneLogger().log(LogLevel.INFO, "Successfully started HTTP hook server.");
        } catch (Exception e) {
            RuneQuestPlugin.getRuneLogger().log(LogLevel.CRITICAL, "Failed to start a HTTP hook server.", e);
        }
    }

    public static void cleanUp() {
        if (server == null) return;
        server.stop(1);
        RuneQuestPlugin.getRuneLogger().log(LogLevel.INFO, "HTTP server stopped.");
    }

    private static Map<String, String> parseQuery(String query) {
        return java.util.Arrays.stream((query == null ? "" : query).split("&"))
                .map(s -> s.split("=", 2))
                .filter(a -> a.length == 2)
                .collect(java.util.stream.Collectors.toMap(a -> a[0], a -> a[1]));
    }
}
