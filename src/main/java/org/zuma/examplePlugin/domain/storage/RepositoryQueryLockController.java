package org.zuma.examplePlugin.domain.storage;

import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.logger.constants.LogLevel;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class RepositoryQueryLockController {
    private final ItemStorageRepository contentRepository;
    private final List<UUID> loadingContainerUUIDs = new CopyOnWriteArrayList<>();
    private final List<UUID> openedContainerUUIDs = new CopyOnWriteArrayList<>();

    public RepositoryQueryLockController(ItemStorageRepository contentRepository) {
        this.contentRepository = contentRepository;
    }

    @Nullable
    public String getByUUID(UUID containerUUID) throws IllegalStateException {
        boolean locked = loadingContainerUUIDs.contains(containerUUID);
        boolean opened = openedContainerUUIDs.contains(containerUUID);

        if (locked) {
            throw new IllegalStateException(containerUUID + " Is currently locked! (Save/loading in progress?)");
        }

        if (opened) {
            throw new IllegalStateException(containerUUID + " Is currently opened by another player!");
        }

        loadingContainerUUIDs.add(containerUUID);
        try {
            String result = contentRepository.getByUUID(containerUUID);
            RuneQuestPlugin.getRuneLogger().log(LogLevel.INFO, "Loaded storage content for container UUID: " + containerUUID);
            return result;
        } finally {
            loadingContainerUUIDs.remove(containerUUID);
        }
    }

    public void save(UUID containerUUID, String content) throws IllegalStateException {
        if (!loadingContainerUUIDs.contains(containerUUID)) {
            loadingContainerUUIDs.add(containerUUID);
        } else {
            throw new IllegalStateException(containerUUID + " Is currently locked! (Save in progress?)");
        }

        try {
            contentRepository.save(containerUUID, content);
            RuneQuestPlugin.getRuneLogger().log(LogLevel.INFO, "Saved storage content for container UUID: " + containerUUID);
        } finally {
            loadingContainerUUIDs.remove(containerUUID);
        }
    }

    public void lockContainerUUID(UUID containerUUID) {
        if (openedContainerUUIDs.contains(containerUUID)) {
            throw new IllegalStateException(containerUUID + " Is currently opened by another player!");
        }

        openedContainerUUIDs.add(containerUUID);
    }

    public void unlockContainerUUID(UUID containerUUID) {
        openedContainerUUIDs.remove(containerUUID);
    }
}
