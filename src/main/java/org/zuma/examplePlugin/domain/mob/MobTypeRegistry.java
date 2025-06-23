package org.zuma.examplePlugin.domain.mob;

import org.zuma.examplePlugin.domain.mob.base.AbstractMob;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class MobTypeRegistry {
    private final Map<String, Supplier<AbstractMob>> registry = new HashMap<>();

    public void register(String id, Supplier<AbstractMob> supplier) {
        registry.put(id, supplier);
    }

    public AbstractMob create(String id) {
        Supplier<AbstractMob> supplier = registry.get(id);
        if (supplier == null) throw new IllegalArgumentException("Unknown mob type: " + id);
        return supplier.get();
    }
}