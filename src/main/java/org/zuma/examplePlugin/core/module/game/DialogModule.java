package org.zuma.examplePlugin.core.module.game;

import org.bukkit.plugin.Plugin;
import org.zuma.examplePlugin.core.database.HikariPool;
import org.zuma.examplePlugin.core.interfaces.PluginModule;
import org.zuma.examplePlugin.core.module.ModuleContext;
import org.zuma.examplePlugin.domain.dialog.DialogHandler;
import org.zuma.examplePlugin.domain.dialog.DialogListener;

import java.util.List;

public class DialogModule implements PluginModule {

    @Override
    public List<Class<?>> provides() {
        return List.of(DialogHandler.class);
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
