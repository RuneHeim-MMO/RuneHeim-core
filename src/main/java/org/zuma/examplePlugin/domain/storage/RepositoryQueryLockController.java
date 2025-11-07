package org.zuma.examplePlugin.domain.storage;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class RepositoryQueryLockController {
    private final ItemStorageRepository contentRepository;
    private final List<UUID> lockedPlayerUUIDs = new ArrayList<>();

    public RepositoryQueryLockController(ItemStorageRepository contentRepository) {
        this.contentRepository = contentRepository;
    }

    @Nullable
    public String getByUUID(UUID playerUUID) throws IllegalStateException {
        boolean locked = lockedPlayerUUIDs.contains(playerUUID);

        if (locked) {
            throw new IllegalStateException(playerUUID + " Is currently locked! (Save in progress?)");
        }

        lockedPlayerUUIDs.add(playerUUID);
        try {
            return contentRepository.getByUUID(playerUUID);
        } finally {
            lockedPlayerUUIDs.remove(playerUUID);
        }
    }

    public void save(UUID playerUUID, String content) throws IllegalStateException {
        boolean locked = lockedPlayerUUIDs.contains(playerUUID);

        if (locked) {
            throw new IllegalStateException(playerUUID + " is already locked! (Concurrent save?)");
        }

        lockedPlayerUUIDs.add(playerUUID);
        try {
            contentRepository.save(playerUUID, content);
        } finally {
            lockedPlayerUUIDs.remove(playerUUID);
        }
    }
}
