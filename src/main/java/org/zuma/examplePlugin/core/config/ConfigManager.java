package org.zuma.examplePlugin.core.config;

import io.github.cdimascio.dotenv.Dotenv;
import org.jetbrains.annotations.NotNull;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.logger.constants.LogLevel;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ConfigManager {
    public static final Path ENVIRONMENT_CONFIG = Paths.get(System.getProperty("user.dir")).resolve("plugins").getParent();
    public static final Path ROOT_CONFIG_PATH = Paths.get("plugins").resolve("RuneQuest");
    public static final Path SCHEMATICS_PATH = ROOT_CONFIG_PATH.resolve("schematics");
    public static boolean isPreviewServer = false;
    public static int serverId;
    public static EnvironmentType environment;

    private static final Dotenv dotenv = Dotenv.configure()
            .directory(ConfigManager.ENVIRONMENT_CONFIG != null ? Paths.get(System.getProperty("user.dir")).resolve("plugins").getParent().toString() : null)
            .ignoreIfMissing()
            .systemProperties()
            .load();

    public ConfigManager() {

    }

    public static void initializeConfig() {
        try {
            String serverId = dotenv.get("SERVER_ID");
            String isLiveServer = dotenv.get("IS_LIVE_SERVER");
            String envKey = dotenv.get("ENV");

            ConfigManager.isPreviewServer = "false".equalsIgnoreCase(isLiveServer != null ? isLiveServer.trim() : null);
            ConfigManager.serverId = Integer.parseInt(serverId);
            ConfigManager.environment = EnvironmentType.getByKey(envKey == null ? "production" : envKey.trim());

            RuneQuestPlugin.getRuneLogger().log(LogLevel.INFO, "Loaded environment: " + ConfigManager.environment.getKey() + ", Server ID: " + ConfigManager.serverId + ", Is Preview Server: " + ConfigManager.isPreviewServer);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid environment variables. SERVER_ID or IS_LIVE_SERVER is missing, or server ID is not a number.");
        }

        validateKeys();
        createFoldersIfMissing();
    }

    private static void validateKeys() {
        List<String> requiredKeysInEnv = getRequiredEnvKeys();

        List<String> missingKeys = new ArrayList<>();

        requiredKeysInEnv.forEach((key) -> {
            if (dotenv.get(key) == null) {
                missingKeys.add(key);
            }
        });

        if (!missingKeys.isEmpty()) {
            throw new IllegalArgumentException("Invalid environment variables. Keys " + String.join(", ", missingKeys) + " are missing.");
        }
    }

    private static @NotNull List<String> getRequiredEnvKeys() {
        List<String> requiredKeysInEnv = new ArrayList<>(Arrays.asList(
                "GITHUB_TOKEN",
                "IS_LIVE_SERVER",
                "JDBC_URL",
                "JVM_XMS",
                "JVM_XMX",
                "EXPOSE_MINECRAFT_PORT",
                "MYSQL_PASSWORD",
                "MYSQL_USERNAME",
                "REDIS_CON_STR",
                "SERVER_ID",
                "ENV"));

        if (isPreviewServer) {
            requiredKeysInEnv.add("WEB_HOOK_KEY");
            requiredKeysInEnv.add("EXPOSE_MAP_PORT");
            requiredKeysInEnv.add("EXPOSE_HOOK_PORT");
        }
        return requiredKeysInEnv;
    }

    private static void createFoldersIfMissing() {
        List<Path> paths = List.of(ROOT_CONFIG_PATH, SCHEMATICS_PATH);

        paths.forEach(path -> {
            try {
                if (!Files.exists(path)) {
                    Files.createDirectories(path);
                }
            } catch (IOException e) {
                RuneQuestPlugin.getRuneLogger().log(LogLevel.CRITICAL, "Failed creating config directory: " + path);
            }
        });
    }

    public static Dotenv getDotenv() {
        return dotenv;
    }

    public static boolean getIsPreviewServer() {
        return isPreviewServer;
    }

    public static int getServerId() {
        return serverId;
    }

}
