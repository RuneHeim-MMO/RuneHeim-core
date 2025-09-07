package org.zuma.examplePlugin.core.module.game;


import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import org.bukkit.plugin.Plugin;
import org.zuma.examplePlugin.core.module.ModuleContext;
import org.zuma.examplePlugin.core.module.PluginModule;
import org.zuma.examplePlugin.domain.block.BlockManager;
import org.zuma.examplePlugin.domain.block.persistence.InteractableBlockRepository;
import org.zuma.examplePlugin.domain.block.persistence.ResourceNodeTypeRepository;
import org.zuma.examplePlugin.domain.combat.HitHandler;
import org.zuma.examplePlugin.domain.command.ConsoleActionCommand;
import org.zuma.examplePlugin.domain.command.MountCommand;
import org.zuma.examplePlugin.domain.command.PouchCommand;
import org.zuma.examplePlugin.domain.command.SkillsCommand;
import org.zuma.examplePlugin.domain.item.ItemRegistry;
import org.zuma.examplePlugin.domain.recipe.RecipeRegistry;
import org.zuma.examplePlugin.features.admin.commands.AdminCommand;

import java.util.List;

public class CommandModule implements PluginModule {

    @Override
    public List<Class<?>> dependencies() {
        return List.of(HitHandler.class, InteractableBlockRepository.class, ItemRegistry.class, RecipeRegistry.class, BlockManager.class);
    }

    @Override
    @SuppressWarnings("UnstableApiUsage")
    public void start(Plugin plugin, ModuleContext context) {
        plugin.getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS,
                event -> event.registrar().register("admin", new AdminCommand(
                        context.get(HitHandler.class),
                        context.get(InteractableBlockRepository.class),
                        context.get(ItemRegistry.class),
                        context.get(RecipeRegistry.class),
                        context.get(BlockManager.class),
                        new ResourceNodeTypeRepository()))
        );
        plugin.getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS,
                event -> event.registrar().register("skills", new SkillsCommand()));
        plugin.getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS,
                event -> event.registrar().register("pouch", new PouchCommand()));

        plugin.getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS,
                event -> event.registrar().register("actions", new ConsoleActionCommand()));
    }

    @Override
    public void stop(Plugin plugin, ModuleContext context) {
    }

    @Override
    public String name() {
        return this.getClass().getSimpleName();
    }
}
