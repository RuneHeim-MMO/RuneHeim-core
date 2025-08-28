package org.zuma.examplePlugin.core.modules;

import org.bukkit.plugin.Plugin;
import org.zuma.examplePlugin.core.interfaces.PluginModule;

import java.util.*;

public class ModuleLoader {
    private final List<PluginModule> modules;
    private final ModuleContext context;
    private  List<PluginModule> sorted = new ArrayList<>();

    public ModuleLoader(List<PluginModule> modules, ModuleContext context) {
        this.modules = modules;
        this.context = context;
    }

    public void startAll(Plugin plugin) {
        sorted = sortModules(modules);
        for (PluginModule module : sorted) {
            module.start(plugin, context);
        }
    }

    public void stopAll(Plugin plugin) {
        List<PluginModule> reversed = sorted.reversed();
        for (PluginModule module : reversed) {
            module.stop(plugin, context);
        }
    }

    private List<PluginModule> sortModules(List<PluginModule> modules) {
        Map<String, PluginModule> nameToModule = new HashMap<>();
        for (PluginModule module : modules) {
            nameToModule.put(module.name(), module);
        }

        List<PluginModule> sorted = new ArrayList<>();
        Set<String> visited = new HashSet<>();

        for (PluginModule module : modules) {
            visit(module, nameToModule, visited, sorted, new HashSet<>());
        }

        return sorted;
    }

    private void visit(PluginModule module,
                       Map<String, PluginModule> nameToModule,
                       Set<String> visited,
                       List<PluginModule> sorted,
                       Set<String> path) {
        if (visited.contains(module.name())) return;

        if (path.contains(module.name())) {
            throw new RuntimeException("Circular dependency detected at module: " + module.name());
        }

        path.add(module.name());

        for (Class<?> dep : module.dependencies()) {
            for (PluginModule potential : nameToModule.values()) {
                if (dep.isAssignableFrom(potential.getClass())) {
                    visit(potential, nameToModule, visited, sorted, path);
                    break;
                }
            }
        }

        path.remove(module.name());
        visited.add(module.name());
        sorted.add(module);
    }
}
