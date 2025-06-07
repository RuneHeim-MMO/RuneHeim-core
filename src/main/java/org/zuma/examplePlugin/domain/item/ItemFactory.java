package org.zuma.examplePlugin.domain.item;

import com.google.gson.Gson;
import org.zuma.examplePlugin.core.exception.UnsupportedJsonVersionException;
import org.zuma.examplePlugin.core.meta.JsonMeta;
import org.zuma.examplePlugin.domain.item.base.AbstractItem;
import org.zuma.examplePlugin.domain.item.impl.EquipableItem;
import org.zuma.examplePlugin.domain.item.impl.MaterialItem;
import org.zuma.examplePlugin.domain.item.impl.MiscItem;
import org.zuma.examplePlugin.domain.item.impl.QuestItem;
import org.zuma.examplePlugin.domain.item.impl.consumable.FoodItem;
import org.zuma.examplePlugin.domain.item.impl.consumable.PotionItem;
import org.zuma.examplePlugin.domain.item.meta.AbstractItemMeta;
import org.zuma.examplePlugin.domain.item.meta.v1.EquipableMeta;
import org.zuma.examplePlugin.domain.item.meta.v1.FoodMeta;
import org.zuma.examplePlugin.domain.item.meta.v1.PotionMeta;
import org.zuma.examplePlugin.domain.item.meta.v1.QuestItemMeta;

import javax.annotation.Nullable;

public class ItemFactory {
    public AbstractItem createItem(AbstractItemMeta meta, @Nullable JsonMeta json) {
        return switch (meta.type()) {
            case GEAR -> createEquipableItem(meta, json);
            case QUEST -> createQuestItem(meta, json);
            case MATERIAL -> createMaterialItem(meta);
            case POTION -> createPotionItem(meta, json);
            case FOOD -> createFoodItem(meta, json);
            case MISC -> createMiscItem(meta);
            default -> throw new IllegalArgumentException("Unknown item type: " + meta.type());
        };
    }

    private EquipableItem createEquipableItem(AbstractItemMeta meta, @Nullable JsonMeta json) {
        if (json == null) {
            throw new UnsupportedOperationException("Cannot create equipable item " + meta.displayName() + " required json is null!");
        }

        switch (json.version()) {
            case 1 -> {
                EquipableMeta deserialized = new Gson().fromJson(json.meta(), EquipableMeta.class);
                EquipableItem item = new EquipableItem(meta);

                item.addModifiers(deserialized.modifiers());
                item.addRequirements(deserialized.requirements());
                return item;
            }
            default -> throw new UnsupportedJsonVersionException(meta.type().name(), json.version());
        }

    }

    private QuestItem createQuestItem(AbstractItemMeta meta, @Nullable JsonMeta json) {
        if (json == null) {
            throw new UnsupportedOperationException("Cannot create quest item " + meta.displayName() + " required json is null!");
        }

        switch (json.version()) {
            case 1 -> {
                QuestItemMeta deserialized = new Gson().fromJson(json.meta(), QuestItemMeta.class);
                QuestItem item = new QuestItem(meta);

                item.setQuestName(deserialized.questName());
                return item;
            }
            default -> throw new UnsupportedJsonVersionException(meta.type().name(), json.version());
        }
    }

    private MaterialItem createMaterialItem(AbstractItemMeta meta) {
        return new MaterialItem(meta);
    }

    private PotionItem createPotionItem(AbstractItemMeta meta, @Nullable JsonMeta json) {
        if (json == null) {
            throw new UnsupportedOperationException("Cannot create potion item " + meta.displayName() + " required json is null!");
        }

        switch (json.version()) {
            case 1 -> {
                PotionMeta deserialized = new Gson().fromJson(json.meta(), PotionMeta.class);
                PotionItem item = new PotionItem(meta);

                item.setUsages(deserialized.usages(), deserialized.maxUsages());
                item.setCustomModelData(deserialized.potionModel());
                return item;
            }
            default -> throw new UnsupportedJsonVersionException(meta.type().name(), json.version());
        }

    }

    private FoodItem createFoodItem(AbstractItemMeta meta, @Nullable JsonMeta json) {
        if (json == null) {
            throw new UnsupportedOperationException("Cannot create food item " + meta.displayName() + " required json is null!");
        }

        switch (json.version()) {
            case 1 -> {
                FoodMeta deserialized = new Gson().fromJson(json.meta(), FoodMeta.class);
                FoodItem item = new FoodItem(meta);

                item.setHealAmount(deserialized.healAmount());
                return item;
            }
            default -> throw new UnsupportedJsonVersionException(meta.type().name(), json.version());
        }
    }

    private MiscItem createMiscItem(AbstractItemMeta meta) {
        return new MiscItem(meta);
    }
}
