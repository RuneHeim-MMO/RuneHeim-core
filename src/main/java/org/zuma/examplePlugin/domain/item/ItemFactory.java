package org.zuma.examplePlugin.domain.item;

import org.zuma.examplePlugin.domain.item.base.*;
import org.zuma.examplePlugin.domain.item.meta.AbstractItemMeta;

public class ItemFactory {
    public AbstractItem createItem(AbstractItemMeta meta) {
        return switch (meta.type()) {
            case GEAR -> new EquipableItem(meta);
            case QUEST -> new QuestItem(meta);
            case MATERIAL -> new MaterialItem(meta);
            default -> new MiscItem(meta);
        };
    }
}
