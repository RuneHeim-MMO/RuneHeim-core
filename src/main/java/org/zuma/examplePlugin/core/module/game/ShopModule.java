package org.zuma.examplePlugin.core.module.game;


import org.bukkit.plugin.Plugin;
import org.zuma.examplePlugin.core.module.PluginModule;
import org.zuma.examplePlugin.core.module.ModuleContext;
import org.zuma.examplePlugin.domain.item.ItemRegistry;
import org.zuma.examplePlugin.domain.shop.ShopHandler;
import org.zuma.examplePlugin.domain.shop.ShopRegistry;
import org.zuma.examplePlugin.domain.shop.persistence.ShopRepository;

import java.util.List;

public class ShopModule implements PluginModule {

    @Override
    public List<Class<?>> dependencies() {
        return List.of(ItemRegistry.class);
    }

    @Override
    public void start(Plugin plugin, ModuleContext context) {
        ShopRepository shopRepository = new ShopRepository();
        ShopRegistry shopRegistry = new ShopRegistry();
        ShopHandler shopHandler = new ShopHandler(shopRegistry, shopRepository);
        shopHandler.initializeShops();

        context.bind(ShopRegistry.class, shopRegistry);
    }

    @Override
    public void stop(Plugin plugin, ModuleContext context) {
    }

    @Override
    public String name() {
        return this.getClass().getSimpleName();
    }
}
