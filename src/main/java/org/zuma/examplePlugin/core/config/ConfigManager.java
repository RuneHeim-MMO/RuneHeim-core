package org.zuma.examplePlugin.core.config;

import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.logger.constants.LogLevel;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class ConfigManager {
    public static final Path ENVIRONMENT_CONFIG = Paths.get(System.getProperty("user.dir")).resolve("plugins").getParent();
    public static final Path ROOT_CONFIG_PATH = Paths.get("plugins").resolve("RuneQuest");
    public static final Path SCHEMATICS_PATH = ROOT_CONFIG_PATH.resolve("schematics");

    public ConfigManager() {

    }

    public void initializeFolders() {
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
}
