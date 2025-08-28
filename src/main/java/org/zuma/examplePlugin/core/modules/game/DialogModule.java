package org.zuma.examplePlugin.core.modules.game;

import org.bukkit.plugin.Plugin;
import org.zuma.examplePlugin.core.interfaces.PluginModule;
import org.zuma.examplePlugin.core.modules.ModuleContext;
import org.zuma.examplePlugin.domain.dialog.DialogHandler;
import org.zuma.examplePlugin.domain.dialog.DialogListener;

import java.util.List;

public class DialogModule implements PluginModule {

    public DialogModule() {}

    @Override
    public List<Class<?>> dependencies() {
        return List.of();
    }

    @Override
    public void start(Plugin plugin, ModuleContext context) {
       DialogHandler dialogHandler = new DialogHandler();
        DialogListener dialogListener = new DialogListener(dialogHandler);

        plugin.getServer().getPluginManager().registerEvents(dialogListener, plugin);

        context.bind(DialogHandler.class, dialogHandler);
    }

    @Override
    public void stop(Plugin plugin, ModuleContext context) {

    }

    @Override
    public String name() {
        return this.getClass().getSimpleName();
    }
}
