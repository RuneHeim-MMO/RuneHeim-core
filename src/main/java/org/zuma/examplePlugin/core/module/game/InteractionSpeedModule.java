package org.zuma.examplePlugin.core.module.game;


import org.bukkit.plugin.Plugin;
import org.zuma.examplePlugin.core.database.HikariPool;
import org.zuma.examplePlugin.core.interfaces.PluginModule;
import org.zuma.examplePlugin.core.module.ModuleContext;
import org.zuma.examplePlugin.domain.combat.melee.VisualMeleeSpeedHandler;
import org.zuma.examplePlugin.domain.combat.melee.WeaponSpeedHandler;
import org.zuma.examplePlugin.domain.cooldown.InteractionSpeedHandler;

import java.util.List;

public class InteractionSpeedModule implements PluginModule {

    @Override
    public List<Class<?>> provides() {
        return List.of(VisualMeleeSpeedHandler.class);
    }

    @Override
    public void start(Plugin plugin, ModuleContext context) {
        InteractionSpeedHandler interactionSpeedHandler = new InteractionSpeedHandler();
        WeaponSpeedHandler weaponSpeedHandler = new WeaponSpeedHandler();

        VisualMeleeSpeedHandler visualMeleeSpeedHandler = new VisualMeleeSpeedHandler();
        visualMeleeSpeedHandler.startEffectsLoop();

        plugin.getServer().getPluginManager().registerEvents(interactionSpeedHandler, plugin);
        plugin.getServer().getPluginManager().registerEvents(weaponSpeedHandler, plugin);

        context.bind(VisualMeleeSpeedHandler.class, visualMeleeSpeedHandler);
    }

    @Override
    public void stop(Plugin plugin, ModuleContext context) {
        context.get(VisualMeleeSpeedHandler.class).onDisable();
    }

    @Override
    public String name() {
        return this.getClass().getSimpleName();
    }
}
