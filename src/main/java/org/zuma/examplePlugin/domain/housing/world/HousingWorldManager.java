package org.zuma.examplePlugin.domain.housing.world;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.codehaus.plexus.util.FileUtils;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.config.ConfigManager;
import org.zuma.examplePlugin.core.logger.constants.LogLevel;

import java.io.File;
import java.io.IOException;

public class HousingWorldManager {
    public static final String WORLD_KEY = "housing_world";
    private World housingWorld;

    public void init() {
        if (ConfigManager.getIsPreviewServer()) {
            RuneQuestPlugin.getRuneLogger().log(LogLevel.INFO, "Preview server detected, skipping housing world creation.");
            return;
        }

        World existing = Bukkit.getWorld(WORLD_KEY);

        if (existing != null) {
            deleteWorld(existing);
            RuneQuestPlugin.getRuneLogger().log(LogLevel.INFO, "Existing housing world deleted: " + existing.getName());
        }

        housingWorld = createEmptyWorld(WORLD_KEY);
        RuneQuestPlugin.getRuneLogger().log(LogLevel.INFO, "Housing world created: " + housingWorld.getName());
    }

    public void cleanup() {
        if (deleteWorld(housingWorld)) {
            RuneQuestPlugin.getRuneLogger().log(LogLevel.INFO, "Housing world deleted: " + housingWorld.getName());
        } else {
            RuneQuestPlugin.getRuneLogger().log(LogLevel.WARN, "Failed to delete housing world: " + housingWorld.getName());
        }
    }

    public World getHousingWorld() {
        return housingWorld;
    }

    private World createEmptyWorld(String name) {
        WorldCreator wc = new WorldCreator(name);
        wc.environment(World.Environment.NORMAL);
        wc.generator(new VoidGenerator());
        return Bukkit.createWorld(wc);
    }

    private boolean deleteWorld(World world) {
        if (world == null) return false;

        Bukkit.getServer().unloadWorld(world, false);
        File worldFolder = world.getWorldFolder();
        try {
            FileUtils.deleteDirectory(worldFolder);
            return true;
        } catch (IOException e) {
            RuneQuestPlugin.getRuneLogger().log(LogLevel.WARN, "Failed to delete world folder: " + worldFolder.getAbsolutePath(), e);
            return false;
        }
    }
}
