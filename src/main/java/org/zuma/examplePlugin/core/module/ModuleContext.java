package org.zuma.examplePlugin.core.module;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ModuleContext {
    private final Map<Class<?>, Object> services = new ConcurrentHashMap<>();

    public <T> void bind(Class<T> type, T instance) {
        services.put(type, instance);
    }

    public boolean has(Class<?> type) {
        return services.containsKey(type);
    }

    /**
     * Returns true if a bound service exists whose key type is assignable to provided type.
     * Useful if you bind concrete implementations under their concrete class but
     * dependencies ask for an interface/superclass.
     */
    public boolean hasAssignable(Class<?> queryType) {
        if (has(queryType)) return true;
        for (Class<?> boundType : services.keySet()) {
            if (queryType.isAssignableFrom(boundType)) return true;
        }
        return false;
    }

    @SuppressWarnings("unchecked")
    public <T> T get(Class<T> type) {
        Object val = services.get(type);
        if (val != null) return (T) val;
        // fallback by assignable match
        for (Map.Entry<Class<?>, Object> e : services.entrySet()) {
            if (type.isAssignableFrom(e.getKey())) {
                return (T) e.getValue();
            }
        }
        return null;
    }
}