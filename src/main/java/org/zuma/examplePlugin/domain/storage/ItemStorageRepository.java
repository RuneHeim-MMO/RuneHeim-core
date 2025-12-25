package org.zuma.examplePlugin.domain.storage;

import javax.annotation.Nullable;
import java.util.UUID;

public interface ItemStorageRepository {

    @Nullable
    String getByUUID(UUID playerUUID);

    void save(UUID playerUUID, String content);
}
