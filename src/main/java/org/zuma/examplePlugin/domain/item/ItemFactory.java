package org.zuma.examplePlugin.domain.item;

import com.google.gson.Gson;
import org.zuma.examplePlugin.core.exception.UnsupportedJsonVersionException;
import org.zuma.examplePlugin.core.meta.VersionedJson;
import org.zuma.examplePlugin.domain.item.base.AbstractType;
import org.zuma.examplePlugin.domain.item.impl.*;
import org.zuma.examplePlugin.domain.item.impl.consumable.FoodType;
import org.zuma.examplePlugin.domain.item.impl.consumable.PotionType;
import org.zuma.examplePlugin.domain.item.meta.AbstractItemMeta;
import org.zuma.examplePlugin.domain.item.meta.v1.*;
import org.zuma.examplePlugin.domain.item.state.states.GearGemSocketsState;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class ItemFactory {
    public AbstractType createItem(AbstractItemMeta meta, @Nullable VersionedJson json) {
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
            case MISC -> createMiscItem(meta);
            default -> throw new IllegalArgumentException("Unknown item type: " + meta.type());
        };
    }

    private MaterialScrollType createMaterialScroll(AbstractItemMeta meta) {
        return new MaterialScrollType(meta);
    }


    private GemType createGemItem(AbstractItemMeta meta, @Nullable VersionedJson json) {
        if (json == null) {
            throw new UnsupportedOperationException("Cannot create gem item " + meta.displayName() + " required json is null!");
        }

        switch (json.version()) {
            case 1 -> {
                GemMeta deserialized = new Gson().fromJson(json.meta(), GemMeta.class);
                GemType item = new GemType(meta);

                item.addModifiers(deserialized.modifiers());
                return item;
            }
            default -> throw new UnsupportedJsonVersionException(meta.type().name(), json.version());
        }

    }

    private GearType createGearItem(AbstractItemMeta meta, @Nullable VersionedJson json) {
        if (json == null) {
            throw new UnsupportedOperationException("Cannot create equipable item " + meta.displayName() + " required json is null!");
        }

        switch (json.version()) {
            case 1 -> {
                EquipableTypeMeta deserialized = new Gson().fromJson(json.meta(), EquipableTypeMeta.class);
                GearType item = new GearType(meta);

                item.addModifiers(deserialized.modifiers());
                item.addRequirements(deserialized.requirements());
                return item;
            }
            default -> throw new UnsupportedJsonVersionException(meta.type().name(), json.version());
        }

    }

    private ToolType createToolItem(AbstractItemMeta meta, @Nullable VersionedJson json) {
        if (json == null) {
            throw new UnsupportedOperationException("Cannot create tool item " + meta.displayName() + " required json is null!");
        }

        switch (json.version()) {
            case 1 -> {
                EquipableTypeMeta deserialized = new Gson().fromJson(json.meta(), EquipableTypeMeta.class);
                ToolType item = new ToolType(meta);

                item.addModifiers(deserialized.modifiers());
                item.addRequirements(deserialized.requirements());
                return item;
            }
            default -> throw new UnsupportedJsonVersionException(meta.type().name(), json.version());
        }

    }

    private WeaponType createWeaponItem(AbstractItemMeta meta, @Nullable VersionedJson json) {
        if (json == null) {
            throw new UnsupportedOperationException("Cannot create tool item " + meta.displayName() + " required json is null!");
        }

        switch (json.version()) {
            case 1 -> {
                EquipableTypeMeta deserialized = new Gson().fromJson(json.meta(), EquipableTypeMeta.class);
                WeaponType item = new WeaponType(meta);

                item.addModifiers(deserialized.modifiers());
                item.addRequirements(deserialized.requirements());
                return item;
            }
            default -> throw new UnsupportedJsonVersionException(meta.type().name(), json.version());
        }

    }

    private QuestType createQuestItem(AbstractItemMeta meta, @Nullable VersionedJson json) {
        if (json == null) {
            throw new UnsupportedOperationException("Cannot create quest item " + meta.displayName() + " required json is null!");
        }

        switch (json.version()) {
            case 1 -> {
                QuestItemMeta deserialized = new Gson().fromJson(json.meta(), QuestItemMeta.class);
                QuestType item = new QuestType(meta);

                item.setQuestName(deserialized.questName());
                return item;
            }
            default -> throw new UnsupportedJsonVersionException(meta.type().name(), json.version());
        }
    }

    private MaterialType createMaterialItem(AbstractItemMeta meta) {
        return new MaterialType(meta);
    }

    private PotionType createPotionItem(AbstractItemMeta meta, @Nullable VersionedJson json) {
        if (json == null) {
            throw new UnsupportedOperationException("Cannot create potion item " + meta.displayName() + " required json is null!");
        }

        switch (json.version()) {
            case 1 -> {
                PotionMeta deserialized = new Gson().fromJson(json.meta(), PotionMeta.class);
                PotionType item = new PotionType(meta);

                item.setUsages(deserialized.usages(), deserialized.maxUsages());
                item.setCustomModelData(deserialized.potionModel());
                return item;
            }
            default -> throw new UnsupportedJsonVersionException(meta.type().name(), json.version());
        }

    }

    private FoodType createFoodItem(AbstractItemMeta meta, @Nullable VersionedJson json) {
        if (json == null) {
            throw new UnsupportedOperationException("Cannot create food item " + meta.displayName() + " required json is null!");
        }

        switch (json.version()) {
            case 1 -> {
                FoodTypeMeta deserialized = new Gson().fromJson(json.meta(), FoodTypeMeta.class);
                FoodType item = new FoodType(meta);

                item.setHealAmount(deserialized.healAmount());
                return item;
            }
            default -> throw new UnsupportedJsonVersionException(meta.type().name(), json.version());
        }
    }

    private SocketedWeaponType createWeaponWithSockets(AbstractItemMeta meta, @Nullable VersionedJson json) {
        if (json == null) {
            throw new UnsupportedOperationException("Cannot create gear with sockets " + meta.displayName() + " required json is null!");
        }

        switch (json.version()) {
            case 1 -> {
                SocketedEquipableMeta deserialized = new Gson().fromJson(json.meta(), SocketedEquipableMeta.class);
                SocketedWeaponType item = new SocketedWeaponType(meta);
                List<Integer> gemIds = deserialized.defaultGemIds();

                if (gemIds == null) {
                    gemIds = new ArrayList<>();
                }

                item.addModifiers(deserialized.equipableTypeMeta().modifiers());
                item.addRequirements(deserialized.equipableTypeMeta().requirements());
                item.setGemState(new GearGemSocketsState(gemIds, deserialized.gemSlots()));
                return item;
            }
            default -> throw new UnsupportedJsonVersionException(meta.type().name(), json.version());
        }

    }

    private SocketedWeaponType createGearWithSockets(AbstractItemMeta meta, @Nullable VersionedJson json) {
        if (json == null) {
            throw new UnsupportedOperationException("Cannot create gear with sockets " + meta.displayName() + " required json is null!");
        }

        switch (json.version()) {
            case 1 -> {
                SocketedEquipableMeta deserialized = new Gson().fromJson(json.meta(), SocketedEquipableMeta.class);
                SocketedWeaponType item = new SocketedWeaponType(meta);
                List<Integer> gemIds = deserialized.defaultGemIds();

                if (gemIds == null) {
                    gemIds = new ArrayList<>();
                }

                item.addModifiers(deserialized.equipableTypeMeta().modifiers());
                item.addRequirements(deserialized.equipableTypeMeta().requirements());
                item.setGemState(new GearGemSocketsState(gemIds, deserialized.gemSlots()));
                return item;
            }
            default -> throw new UnsupportedJsonVersionException(meta.type().name(), json.version());
        }

    }


    private MiscType createMiscItem(AbstractItemMeta meta) {
        return new MiscType(meta);
    }
}
