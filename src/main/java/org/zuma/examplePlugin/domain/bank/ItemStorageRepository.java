package org.zuma.examplePlugin.domain.bank;

import javax.annotation.Nullable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public interface ItemStorageRepository {

    @Nullable
    String getByUUID(UUID playerUUID);
    void save(UUID playerUUID, String content);
}
