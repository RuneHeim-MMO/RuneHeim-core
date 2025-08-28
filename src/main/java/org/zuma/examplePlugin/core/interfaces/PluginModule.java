package org.zuma.examplePlugin.core.interfaces;

import org.bukkit.plugin.Plugin;
import org.zuma.examplePlugin.core.module.ModuleContext;

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

    /**
     * The service types this module will bind into ModuleContext when started.
     * Example: a module that creates BlockManager should return List.of(BlockManager.class).
     * Default empty (most modules might not provide services).
     */
    default List<Class<?>> provides() {
        return List.of();
    }
}