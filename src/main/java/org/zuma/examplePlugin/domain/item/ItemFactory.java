package org.zuma.examplePlugin.domain.item;

import com.google.gson.Gson;
import org.zuma.examplePlugin.core.exception.UnsupportedJsonVersionException;
import org.zuma.examplePlugin.core.meta.VersionedJson;
import org.zuma.examplePlugin.domain.item.base.AbstractItemType;
import org.zuma.examplePlugin.domain.item.impl.*;
import org.zuma.examplePlugin.domain.item.impl.consumable.Food;
import org.zuma.examplePlugin.domain.item.impl.consumable.Potion;
import org.zuma.examplePlugin.domain.item.meta.AbstractItemMeta;
import org.zuma.examplePlugin.domain.item.meta.v1.*;
import org.zuma.examplePlugin.domain.item.state.meta.v1.FairyStateMeta;
import org.zuma.examplePlugin.domain.item.state.meta.v1.GearGemSocketsStateMeta;
import org.zuma.examplePlugin.domain.item.state.meta.v1.MultiConsumableStateMeta;
import org.zuma.examplePlugin.domain.item.state.states.FairyState;
import org.zuma.examplePlugin.domain.item.state.states.GearGemSocketsState;
import org.zuma.examplePlugin.domain.item.state.states.MultiConsumableState;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class ItemFactory {
    public AbstractItemType createItem(AbstractItemMeta meta, @Nullable VersionedJson json) {
        return switch (meta.type()) {
            case EQUIPMENT -> createGearItem(meta, json);
            case EQUIPMENT_WITH_GEM_SOCKETS -> createGearWithSockets(meta, json);
            case TOOL -> createToolItem(meta, json);
            case WEAPON -> createWeaponItem(meta, json);
            case WEAPON_WITH_GEM_SOCKETS -> createWeaponWithSockets(meta, json);
            case QUEST -> createQuestItem(meta, json);
            case MATERIAL -> createMaterialItem(meta);
            case POTION -> createPotionItem(meta, json);
            case FOOD -> createFoodItem(meta, json);
            case GEM -> createGemItem(meta, json);
            case MATERIAL_SCROLL -> createMaterialScroll(meta);
            case FAIRY_FOOD -> createFairyFood(meta, json);
            case FAIRY_FRUIT -> createFairyFruit(meta, json);
            case FAIRY -> createFairy(meta, json);
            case MISC -> createMiscItem(meta);
            default -> throw new IllegalArgumentException("Unknown item type: " + meta.type());
        };
    }

    private Fairy createFairy(AbstractItemMeta meta, @Nullable VersionedJson json) {
        if (json == null) {
            throw new UnsupportedOperationException("Cannot create gem item " + meta.displayName() + " required json is null!");
        }

        switch (json.version()) {
            case 1 -> {
                FairyMeta deserialized = new Gson().fromJson(json.meta(), FairyMeta.class);
                Fairy item = new Fairy(meta);

                item.setDefaultStateTemplate(new FairyState(new FairyStateMeta(new ArrayList<>(), 0, 0, false, deserialized.generation())));
                return item;
            }
            default -> throw new UnsupportedJsonVersionException(meta.type().name(), json.version());
        }

    }


    private FairyFruit createFairyFruit(AbstractItemMeta meta, @Nullable VersionedJson json) {
        if (json == null) {
            throw new UnsupportedOperationException("Cannot create gem item " + meta.displayName() + " required json is null!");
        }

        switch (json.version()) {
            case 1 -> {
                FairyFruitMeta deserialized = new Gson().fromJson(json.meta(), FairyFruitMeta.class);
                FairyFruit item = new FairyFruit(meta);

                item.addModifiers(deserialized.modifiers());
                return item;
            }
            default -> throw new UnsupportedJsonVersionException(meta.type().name(), json.version());
        }

    }

    private FairyFood createFairyFood(AbstractItemMeta meta, @Nullable VersionedJson json) {
        if (json == null) {
            throw new UnsupportedOperationException("Cannot create gem item " + meta.displayName() + " required json is null!");
        }

        switch (json.version()) {
            case 1 -> {
                FairyFoodMeta deserialized = new Gson().fromJson(json.meta(), FairyFoodMeta.class);
                FairyFood item = new FairyFood(meta);

                item.setEnergyRestoreAmount(deserialized.energyRestoreAmount());
                return item;
            }
            default -> throw new UnsupportedJsonVersionException(meta.type().name(), json.version());
        }

    }

    private MaterialScroll createMaterialScroll(AbstractItemMeta meta) {
        return new MaterialScroll(meta);
    }


    private Gem createGemItem(AbstractItemMeta meta, @Nullable VersionedJson json) {
        if (json == null) {
            throw new UnsupportedOperationException("Cannot create gem item " + meta.displayName() + " required json is null!");
        }

        switch (json.version()) {
            case 1 -> {
                GemMeta deserialized = new Gson().fromJson(json.meta(), GemMeta.class);
                Gem item = new Gem(meta);

                item.addModifiers(deserialized.modifiers());
                return item;
            }
            default -> throw new UnsupportedJsonVersionException(meta.type().name(), json.version());
        }

    }

    private Gear createGearItem(AbstractItemMeta meta, @Nullable VersionedJson json) {
        if (json == null) {
            throw new UnsupportedOperationException("Cannot create equipable item " + meta.displayName() + " required json is null!");
        }

        switch (json.version()) {
            case 1 -> {
                EquipableTypeMeta deserialized = new Gson().fromJson(json.meta(), EquipableTypeMeta.class);
                Gear item = new Gear(meta);

                item.addModifiers(deserialized.modifiers());
                item.addRequirements(deserialized.requirements());
                return item;
            }
            default -> throw new UnsupportedJsonVersionException(meta.type().name(), json.version());
        }

    }

    private Tool createToolItem(AbstractItemMeta meta, @Nullable VersionedJson json) {
        if (json == null) {
            throw new UnsupportedOperationException("Cannot create tool item " + meta.displayName() + " required json is null!");
        }

        switch (json.version()) {
            case 1 -> {
                EquipableTypeMeta deserialized = new Gson().fromJson(json.meta(), EquipableTypeMeta.class);
                Tool item = new Tool(meta);

                item.addModifiers(deserialized.modifiers());
                item.addRequirements(deserialized.requirements());
                return item;
            }
            default -> throw new UnsupportedJsonVersionException(meta.type().name(), json.version());
        }

    }

    private Weapon createWeaponItem(AbstractItemMeta meta, @Nullable VersionedJson json) {
        if (json == null) {
            throw new UnsupportedOperationException("Cannot create tool item " + meta.displayName() + " required json is null!");
        }

        switch (json.version()) {
            case 1 -> {
                EquipableTypeMeta deserialized = new Gson().fromJson(json.meta(), EquipableTypeMeta.class);
                Weapon item = new Weapon(meta);

                item.addModifiers(deserialized.modifiers());
                item.addRequirements(deserialized.requirements());
                return item;
            }
            default -> throw new UnsupportedJsonVersionException(meta.type().name(), json.version());
        }

    }

    private QuestItem createQuestItem(AbstractItemMeta meta, @Nullable VersionedJson json) {
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

    private Material createMaterialItem(AbstractItemMeta meta) {
        return new Material(meta);
    }

    private Potion createPotionItem(AbstractItemMeta meta, @Nullable VersionedJson json) {
        if (json == null) {
            throw new UnsupportedOperationException("Cannot create potion item " + meta.displayName() + " required json is null!");
        }

        switch (json.version()) {
            case 1 -> {
                PotionMeta deserialized = new Gson().fromJson(json.meta(), PotionMeta.class);
                Potion item = new Potion(meta);

                item.setDefaultStateTemplate(new MultiConsumableState(new MultiConsumableStateMeta(deserialized.usages(), deserialized.maxUsages())));
                item.setCustomModelData(deserialized.potionModel());
                return item;
            }
            default -> throw new UnsupportedJsonVersionException(meta.type().name(), json.version());
        }

    }

    private Food createFoodItem(AbstractItemMeta meta, @Nullable VersionedJson json) {
        if (json == null) {
            throw new UnsupportedOperationException("Cannot create food item " + meta.displayName() + " required json is null!");
        }

        switch (json.version()) {
            case 1 -> {
                FoodTypeMeta deserialized = new Gson().fromJson(json.meta(), FoodTypeMeta.class);
                Food item = new Food(meta);

                item.setHealAmount(deserialized.healAmount());
                return item;
            }
            default -> throw new UnsupportedJsonVersionException(meta.type().name(), json.version());
        }
    }

    private SocketedWeapon createWeaponWithSockets(AbstractItemMeta meta, @Nullable VersionedJson json) {
        if (json == null) {
            throw new UnsupportedOperationException("Cannot create gear with sockets " + meta.displayName() + " required json is null!");
        }

        switch (json.version()) {
            case 1 -> {
                SocketedEquipableMeta deserialized = new Gson().fromJson(json.meta(), SocketedEquipableMeta.class);
                SocketedWeapon item = new SocketedWeapon(meta);
                List<Integer> gemIds = deserialized.defaultGemIds();

                if (gemIds == null) {
                    gemIds = new ArrayList<>();
                }

                item.addModifiers(deserialized.equipableTypeMeta().modifiers());
                item.addRequirements(deserialized.equipableTypeMeta().requirements());
                item.setDefaultStateTemplate(new GearGemSocketsState(new GearGemSocketsStateMeta(gemIds, deserialized.gemSlots())));
                return item;
            }
            default -> throw new UnsupportedJsonVersionException(meta.type().name(), json.version());
        }

    }

    private SocketedWeapon createGearWithSockets(AbstractItemMeta meta, @Nullable VersionedJson json) {
        if (json == null) {
            throw new UnsupportedOperationException("Cannot create gear with sockets " + meta.displayName() + " required json is null!");
        }

        switch (json.version()) {
            case 1 -> {
                SocketedEquipableMeta deserialized = new Gson().fromJson(json.meta(), SocketedEquipableMeta.class);
                SocketedWeapon item = new SocketedWeapon(meta);
                List<Integer> gemIds = deserialized.defaultGemIds();

                if (gemIds == null) {
                    gemIds = new ArrayList<>();
                }

                item.addModifiers(deserialized.equipableTypeMeta().modifiers());
                item.addRequirements(deserialized.equipableTypeMeta().requirements());
                item.setDefaultStateTemplate(new GearGemSocketsState(new GearGemSocketsStateMeta(gemIds, deserialized.gemSlots())));
                return item;
            }
            default -> throw new UnsupportedJsonVersionException(meta.type().name(), json.version());
        }

    }


    private MiscItem createMiscItem(AbstractItemMeta meta) {
        return new MiscItem(meta);
    }
}
