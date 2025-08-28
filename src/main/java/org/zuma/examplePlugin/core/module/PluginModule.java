package org.zuma.examplePlugin.core.module;

import java.util.List;


public interface PluginModule {
    String name();

    void start(org.bukkit.plugin.Plugin plugin, org.zuma.examplePlugin.core.module.ModuleContext context) throws Exception;

    void stop(org.bukkit.plugin.Plugin plugin, org.zuma.examplePlugin.core.module.ModuleContext context) throws Exception;

    /**
     * The dependencies this module needs before starting (services / types).
     * Example: List.of(HikariPool.class, ItemRegistry.class)
     */
    default List<Class<?>> dependencies() {
        return List.of();
    }
}