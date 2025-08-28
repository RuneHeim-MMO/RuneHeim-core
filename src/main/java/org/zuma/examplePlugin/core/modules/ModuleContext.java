package org.zuma.examplePlugin.core.modules;

import java.util.HashMap;
import java.util.Map;

public class ModuleContext {
    private final Map<Class<?>, Object> services = new HashMap<>();

    public <T> void bind(Class<T> clazz, T instance) {
        services.put(clazz, instance);
    }

    public <T> T get(Class<T> clazz) {
        return clazz.cast(services.get(clazz));
    }

    public boolean has(Class<?> clazz) {
        return services.containsKey(clazz);
    }
}