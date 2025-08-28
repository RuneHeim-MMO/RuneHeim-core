package org.zuma.examplePlugin.core.interfaces;

import org.bukkit.plugin.Plugin;
import org.zuma.examplePlugin.core.modules.ModuleContext;

import java.util.List;

public interface PluginModule {
    public void start(Plugin plugin, ModuleContext context);
    public void stop(Plugin plugin, ModuleContext context);
    public List<Class<?>> dependencies();
    public String name();
}
