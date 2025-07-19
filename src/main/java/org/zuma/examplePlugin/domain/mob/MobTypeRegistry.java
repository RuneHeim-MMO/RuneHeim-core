package org.zuma.examplePlugin.domain.mob;

import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.logger.constants.LogLevel;
import org.zuma.examplePlugin.domain.mob.base.AbstractMob;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

public class MobTypeRegistry {
    private final Map<String, Supplier<AbstractMob>> registry = new HashMap<>();

    public void register(String id, Supplier<AbstractMob> supplier) {
        if (registry.get(id) != null) {
            RuneQuestPlugin.getRuneLogger().log(LogLevel.WARN, "Cannot register id" + id + "! Mob type with this id already exists.");
            return;
        }

        registry.put(id, supplier);
    }

    public AbstractMob create(String id) {
        Supplier<AbstractMob> supplier = registry.get(id);
        if (supplier == null) throw new IllegalArgumentException("Unknown mob type: " + id);
        return supplier.get();
    }

    public Set<String> getAvailable() {
        return registry.keySet();
    }
}