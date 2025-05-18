package org.zuma.examplePlugin.domain.item;

import org.zuma.examplePlugin.domain.item.base.*;

public class ItemFactory {
    public CustomItem createItem(CustomItemMeta meta) {
        return switch (meta.type()) {
            case GEAR -> new EquipableItem(meta);
            case QUEST -> new QuestItem(meta);
            case MATERIAL -> new MaterialItem(meta);
            default -> new MiscItem(meta);
        };
    }
}
