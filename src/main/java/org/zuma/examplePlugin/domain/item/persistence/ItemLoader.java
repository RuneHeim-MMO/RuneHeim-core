package org.zuma.examplePlugin.domain.item.persistence;

import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.logger.constants.LogLevel;
import org.zuma.examplePlugin.core.meta.VersionedJson;
import org.zuma.examplePlugin.domain.item.ItemFactory;
import org.zuma.examplePlugin.domain.item.ItemRegistry;
import org.zuma.examplePlugin.domain.item.base.AbstractType;
import org.zuma.examplePlugin.domain.item.meta.AbstractItemMeta;

import javax.annotation.Nullable;

public class ItemLoader {
    private final ItemRepository abstractItemMetaRepository;
    private final ItemFactory factory;
    private final ItemRegistry registry;

    public ItemLoader(ItemRegistry registry, ItemFactory factory, ItemRepository abstractItemMetaRepository) {
        this.registry = registry;
        this.factory = factory;
        this.abstractItemMetaRepository = abstractItemMetaRepository;
    }

    public void loadItemsFromDatabase() {
        abstractItemMetaRepository.getAll().forEach(entity -> registerItem(entity.meta(), entity.json()));
        RuneQuestPlugin.getRuneLogger().log(LogLevel.INFO, "Initialized " + this.registry.getAllItems().size() + " custom items.");
    }

    private void registerItem(AbstractItemMeta meta, @Nullable VersionedJson json) {
        AbstractType item = factory.createItem(meta, json);
        this.registry.registerItem(item);
    }


}
