package org.zuma.examplePlugin.domain.item;

import org.zuma.examplePlugin.domain.item.base.AbstractItem;
import org.zuma.examplePlugin.domain.item.impl.EquipableItem;
import org.zuma.examplePlugin.domain.item.impl.MaterialItem;
import org.zuma.examplePlugin.domain.item.impl.MiscItem;
import org.zuma.examplePlugin.domain.item.impl.QuestItem;
import org.zuma.examplePlugin.domain.item.impl.consumable.FoodItem;
import org.zuma.examplePlugin.domain.item.impl.consumable.PotionItem;
import org.zuma.examplePlugin.domain.item.meta.AbstractItemMeta;

public class ItemFactory {
    public AbstractItem createItem(AbstractItemMeta meta) {
        return switch (meta.type()) {
            case GEAR -> new EquipableItem(meta);
            case QUEST -> new QuestItem(meta);
            case MATERIAL -> new MaterialItem(meta);
            case POTION -> new PotionItem(meta);
            case FOOD -> new FoodItem(meta);
            default -> new MiscItem(meta);
        };
    }
}
