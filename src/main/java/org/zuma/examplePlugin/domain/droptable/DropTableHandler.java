package org.zuma.examplePlugin.domain.droptable;

import com.google.gson.Gson;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.exception.UnsupportedJsonVersionException;
import org.zuma.examplePlugin.core.logger.constants.LogLevel;
import org.zuma.examplePlugin.core.meta.VersionedJson;
import org.zuma.examplePlugin.domain.droptable.persistence.DropTableRepository;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public class DropTableHandler {
    private static final Gson gson = new Gson();
    private final Map<Integer, DropTable> dropTables;
    private final DropTableRepository repository;

    public DropTableHandler(DropTableRepository repository) {
        this.dropTables = new HashMap<>();
        this.repository = repository;
    }

    @Nullable
    public DropTable getByID(int id) {
        return dropTables.get(id);
    }

    public void initialize() {
        this.repository.getAll().forEach(entry -> register(entry.id(), entry.json()));
        RuneQuestPlugin.getRuneLogger().log(LogLevel.INFO, "Loaded " + dropTables.size() + " drop tables.");
    }

    public void register(int id, VersionedJson json) {
        try {
            if (json == null) {
                throw new UnsupportedOperationException("Cannot create drop table instance id " + id + " required json is null!");
            }

            switch (json.version()) {
                case 1 -> {
                    DropTableRecord deserialized = gson.fromJson(json.meta(), DropTableRecord.class);
                    DropTable table = new DropTable(id, deserialized.drops());
                    dropTables.put(id, table);
                }
                default -> throw new UnsupportedJsonVersionException("Drop table id " + id, json.version());
            }

        } catch (Exception e) {
            RuneQuestPlugin.getRuneLogger().log(LogLevel.WARN, "Failed to load drop table with id " + id + "! " + e.getMessage());
        }
    }
}
