package org.zuma.examplePlugin.domain.item;

import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.logger.constants.LogLevel;
import org.zuma.examplePlugin.core.repositories.CustomItemMetaRepository;
import org.zuma.examplePlugin.domain.item.base.CustomItem;
import org.zuma.examplePlugin.interfaces.WithAdditionalMeta;

public class ItemLoader {
    private final CustomItemMetaRepository customItemMetaRepository;
    private final ItemFactory factory;
    private final ItemRegistry registry;

    public ItemLoader(ItemRegistry registry, ItemFactory factory, CustomItemMetaRepository customItemMetaRepository) {
        this.registry = registry;
        this.factory = factory;
        this.customItemMetaRepository = customItemMetaRepository;
    }

    public void loadItemsFromDatabase() {
        customItemMetaRepository.getAll().forEach(meta -> {
            CustomItem item = factory.createItem(meta);

            if (item instanceof WithAdditionalMeta itemWithAdditionalMeta) {
                try {
                    itemWithAdditionalMeta.deserializeAndApplyMeta(item.jsonMeta);
                } catch (Exception e) {
                    RuneQuestPlugin.getRuneLogger().log(LogLevel.CRITICAL, "Could not apply jsonMeta to the " + item.displayName + "! " + e.getMessage());
                    return;
                }
            }

            this.registry.registerItem(item);
        });

    }


}
