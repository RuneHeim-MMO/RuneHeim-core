package org.zuma.examplePlugin.domain.item.persistence;

import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.logger.constants.LogLevel;
import org.zuma.examplePlugin.domain.item.ItemFactory;
import org.zuma.examplePlugin.domain.item.ItemRegistry;
import org.zuma.examplePlugin.domain.item.base.AbstractItem;
import org.zuma.examplePlugin.domain.item.interfaces.WithMeta;

public class ItemLoader {
    private final AbstractItemMetaRepository abstractItemMetaRepository;
    private final ItemFactory factory;
    private final ItemRegistry registry;

    public ItemLoader(ItemRegistry registry, ItemFactory factory, AbstractItemMetaRepository abstractItemMetaRepository) {
        this.registry = registry;
        this.factory = factory;
        this.abstractItemMetaRepository = abstractItemMetaRepository;
    }

    public void loadItemsFromDatabase() {
        abstractItemMetaRepository.getAll().forEach(meta -> {
            AbstractItem item = factory.createItem(meta);

            if (item instanceof WithMeta itemWithAdditionalMeta) {
                try {
                    itemWithAdditionalMeta.deserializeAndApplyMeta();
                } catch (Exception e) {
                    RuneQuestPlugin.getRuneLogger().log(LogLevel.CRITICAL, "Could not apply jsonMeta to the " + item.displayName + "! " + e.getMessage());
                    return;
                }
            }

            this.registry.registerItem(item);
        });

    }


}
