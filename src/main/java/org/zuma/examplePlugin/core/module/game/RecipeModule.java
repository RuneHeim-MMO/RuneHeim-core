package org.zuma.examplePlugin.core.module.game;


import org.bukkit.plugin.Plugin;
import org.zuma.examplePlugin.core.module.ModuleContext;
import org.zuma.examplePlugin.core.module.PluginModule;
import org.zuma.examplePlugin.domain.item.ItemRegistry;
import org.zuma.examplePlugin.domain.recipe.RecipeFactory;
import org.zuma.examplePlugin.domain.recipe.RecipeHandler;
import org.zuma.examplePlugin.domain.recipe.RecipeListener;
import org.zuma.examplePlugin.domain.recipe.RecipeRegistry;
import org.zuma.examplePlugin.domain.recipe.persistence.RecipeLoader;
import org.zuma.examplePlugin.domain.recipe.persistence.RecipeRepository;

import java.util.List;

public class RecipeModule implements PluginModule {

    @Override
    public List<Class<?>> dependencies() {
        return List.of(ItemRegistry.class);
    }

    @Override
    public void start(Plugin plugin, ModuleContext context) {
        RecipeRegistry recipeRegistry = new RecipeRegistry();
        RecipeLoader recipeLoader = new RecipeLoader(context.get(ItemRegistry.class), recipeRegistry, new RecipeRepository(), new RecipeFactory());
        recipeLoader.loadAllRecipes();
        
        RecipeHandler recipeHandler = new RecipeHandler(recipeRegistry);
        RecipeListener recipeListener = new RecipeListener(recipeHandler);

        plugin.getServer().getPluginManager().registerEvents(recipeListener, plugin);

        context.bind(RecipeRegistry.class, recipeRegistry);
        context.bind(RecipeHandler.class, recipeHandler);
    }

    @Override
    public void stop(Plugin plugin, ModuleContext context) {
    }

    @Override
    public String name() {
        return this.getClass().getSimpleName();
    }
}
