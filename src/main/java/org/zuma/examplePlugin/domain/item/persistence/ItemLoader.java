package org.zuma.examplePlugin.domain.item.persistence;

import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.interfaces.Serializable;
import org.zuma.examplePlugin.core.logger.constants.LogLevel;
import org.zuma.examplePlugin.domain.item.ItemFactory;
import org.zuma.examplePlugin.domain.item.ItemRegistry;
import org.zuma.examplePlugin.domain.item.base.AbstractItem;
import org.zuma.examplePlugin.domain.item.meta.AbstractItemMeta;

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
        abstractItemMetaRepository.getAll().forEach(this::registerItem);
        RuneQuestPlugin.getRuneLogger().log(LogLevel.INFO, "Initialized " + this.registry.getAllItems().size() + " custom items.");
    }

    private void registerItem(AbstractItemMeta meta) {
        AbstractItem item = factory.createItem(meta);

        if (item instanceof Serializable itemWithAdditionalMeta) {
            try {
                itemWithAdditionalMeta.deserializeAndApplyMeta();
            } catch (Exception e) {
                RuneQuestPlugin.getRuneLogger().log(LogLevel.CRITICAL, "Could not apply jsonMeta to the " + item.displayName + "! " + e.getMessage());
                return;
            }
        }

        this.registry.registerItem(item);
    }


}
