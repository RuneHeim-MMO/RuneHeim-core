package org.zuma.examplePlugin.domain.item;

import com.google.gson.Gson;
import org.zuma.examplePlugin.core.exception.UnsupportedJsonVersionException;
import org.zuma.examplePlugin.core.meta.VersionedJson;
import org.zuma.examplePlugin.domain.item.base.AbstractItemType;
import org.zuma.examplePlugin.domain.item.impl.*;
import org.zuma.examplePlugin.domain.item.impl.consumable.Food;
import org.zuma.examplePlugin.domain.item.impl.consumable.Potion;
import org.zuma.examplePlugin.domain.item.impl.equipable.*;
import org.zuma.examplePlugin.domain.item.impl.fairy.Fairy;
import org.zuma.examplePlugin.domain.item.impl.fairy.FairyFood;
import org.zuma.examplePlugin.domain.item.impl.fairy.FairyFruit;
import org.zuma.examplePlugin.domain.item.meta.AbstractItemMeta;
import org.zuma.examplePlugin.domain.item.meta.v1.*;
import org.zuma.examplePlugin.domain.item.state.meta.v1.FairyStateMeta;
import org.zuma.examplePlugin.domain.item.state.meta.v1.GearGemSocketsStateMeta;
import org.zuma.examplePlugin.domain.item.state.meta.v1.MountStateMeta;
import org.zuma.examplePlugin.domain.item.state.meta.v1.MultiConsumableStateMeta;
import org.zuma.examplePlugin.domain.item.state.states.FairyState;
import org.zuma.examplePlugin.domain.item.state.states.GearGemSocketsState;
import org.zuma.examplePlugin.domain.item.state.states.MountState;
import org.zuma.examplePlugin.domain.item.state.states.MultiConsumableState;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class ItemFactory {
    public AbstractItemType createItem(AbstractItemMeta meta, @Nullable VersionedJson json) {
        return switch (meta.type()) {
            case EQUIPMENT -> createGearItem(meta, json);
            case ACCESSORY_AMULET -> createAccessoryAmulet(meta, json);
            case ACCESSORY_RING -> createAccessoryRing(meta, json);
            case EQUIPMENT_WITH_GEM_SOCKETS -> createGearWithSockets(meta, json);
            case TOOL -> createToolItem(meta, json);
            case TOOL_WITH_GEM_SOCKETS -> createToolWithSockets(meta, json);
            case COMPASS -> createCompassItem(meta, json);
            case MELEE_WEAPON -> createMeleeWeaponItem(meta, json);
            case MELEE_WEAPON_WITH_GEM_SOCKETS -> createMeleeWeaponWithSockets(meta, json);
            case RANGED_WEAPON -> createRangedWeaponItem(meta, json);
            case RANGED_WEAPON_WITH_SOCKETS -> createRangedWeaponWithSockets(meta, json);
            case QUEST -> createQuestItem(meta, json);
            case MATERIAL -> createMaterialItem(meta, json);
            case POTION -> createPotionItem(meta, json);
            case FOOD -> createFoodItem(meta, json);
            case GEM -> createGemItem(meta, json);
            case MATERIAL_SCROLL -> createMaterialScroll(meta);
            case FAIRY_FOOD -> createFairyFood(meta, json);
            case FAIRY_FRUIT -> createFairyFruit(meta, json);
            case FAIRY -> createFairy(meta, json);
            case MISC -> createMiscItem(meta);
            case EMPTY_CONTAINER -> createEmptyContainer(meta, json);
            case COINS -> new CoinsItem(meta);
            case ARROW -> createArrowItem(meta, json);
            case CURRENCY -> createCurrencyItem(meta);
            case CHANCE_ITEM -> createChanceItem(meta, json);
            case XP_LAMP -> createXPLampItem(meta, json);
            case REMAINS -> createRemainsItem(meta, json);
            case MOUNT -> createMountItem(meta, json);
            default -> throw new IllegalArgumentException("Unknown item type: " + meta.type());
        };
    }

    private Mount createMountItem(AbstractItemMeta meta, @Nullable VersionedJson json) {
        if (json == null) {
            throw new UnsupportedOperationException("Cannot create " + meta.type() + " " + meta.displayName() + " required json is null!");
        }

        switch (json.version()) {
            case 1 -> {
                MountMeta deserialized = new Gson().fromJson(json.meta(), MountMeta.class);
                Mount item = new Mount(meta, deserialized.model(), deserialized.baseEntityType(), deserialized.color(), deserialized.style());

                item.setDefaultStateTemplate(new MountState(new MountStateMeta(null, 1, 0, 0)));
                item.addSkillRequirements(deserialized.requirements());
                item.addQuestRequirements(deserialized.requiredQuests());
                item.initializeStats(deserialized.maxLevel(), deserialized.baseSpeed(), deserialized.baseJump(), deserialized.speedLevelBonus(), deserialized.jumpLevelBonus());
                return item;
            }
            default -> throw new UnsupportedJsonVersionException(meta.type().name(), json.version());
        }

    }


    private Remains createRemainsItem(AbstractItemMeta meta, @Nullable VersionedJson json) {
        if (json == null) {
            throw new UnsupportedOperationException("Cannot create " + meta.type() + " " + meta.displayName() + " required json is null!");
        }

        switch (json.version()) {
            case 1 -> {
                RemainsItemMeta deserialized = new Gson().fromJson(json.meta(), RemainsItemMeta.class);
                Remains item = new Remains(meta);
                item.setBuryXPReward(deserialized.buryXp());
                item.setBuryingDurationMs(deserialized.buryingDurationMs());

                return item;
            }
            default -> throw new UnsupportedJsonVersionException(meta.type().name(), json.version());
        }

    }

    private AccessoryRing createAccessoryRing(AbstractItemMeta meta, @Nullable VersionedJson json) {
        if (json == null) {
            throw new UnsupportedOperationException("Cannot create " + meta.type() + " " + meta.displayName() + " required json is null!");
        }

        switch (json.version()) {
            case 1 -> {
                EquipableTypeMeta deserialized = new Gson().fromJson(json.meta(), EquipableTypeMeta.class);
                AccessoryRing item = new AccessoryRing(meta);

                item.addModifiers(deserialized.modifiers());
                item.addSkillRequirements(deserialized.requirements());
                item.addQuestRequirements(deserialized.requiredQuests());
                item.addSkillModifiers(deserialized.skillModifiers());
                item.setEquipableData(deserialized.equipmentSlot(), deserialized.equipableModel());
                return item;
            }
            default -> throw new UnsupportedJsonVersionException(meta.type().name(), json.version());
        }

    }

    private AccessoryAmulet createAccessoryAmulet(AbstractItemMeta meta, @Nullable VersionedJson json) {
        if (json == null) {
            throw new UnsupportedOperationException("Cannot create " + meta.type() + " " + meta.displayName() + " required json is null!");
        }

        switch (json.version()) {
            case 1 -> {
                EquipableTypeMeta deserialized = new Gson().fromJson(json.meta(), EquipableTypeMeta.class);
                AccessoryAmulet item = new AccessoryAmulet(meta);

                item.addModifiers(deserialized.modifiers());
                item.addSkillRequirements(deserialized.requirements());
                item.addQuestRequirements(deserialized.requiredQuests());
                item.addSkillModifiers(deserialized.skillModifiers());
                item.setEquipableData(deserialized.equipmentSlot(), deserialized.equipableModel());
                return item;
            }
            default -> throw new UnsupportedJsonVersionException(meta.type().name(), json.version());
        }

    }

    private XPLamp createXPLampItem(AbstractItemMeta meta, @Nullable VersionedJson json) {
        if (json == null) {
            throw new UnsupportedOperationException("Cannot create " + meta.type() + " " + meta.displayName() + " required json is null!");
        }

        switch (json.version()) {
            case 1 -> {
                XPLampMeta deserialized = new Gson().fromJson(json.meta(), XPLampMeta.class);
                XPLamp item = new XPLamp(meta);

                item.setDisabledSkills(deserialized.disabledSkills());
                item.setXpReward(deserialized.xpReward());

                return item;
            }
            default -> throw new UnsupportedJsonVersionException(meta.type().name(), json.version());
        }

    }


    private SocketedTool createToolWithSockets(AbstractItemMeta meta, @Nullable VersionedJson json) {
        if (json == null) {
            throw new UnsupportedOperationException("Cannot create " + meta.type() + " " + meta.displayName() + " required json is null!");
        }

        switch (json.version()) {
            case 1 -> {
                SocketedToolMeta deserialized = new Gson().fromJson(json.meta(), SocketedToolMeta.class);
                SocketedTool item = new SocketedTool(meta);
                List<Integer> gemIds = deserialized.defaultGemIds();

                if (gemIds == null) {
                    gemIds = new ArrayList<>();
                }

                item.addModifiers(deserialized.equipableTypeMeta().modifiers());
                item.addSkillRequirements(deserialized.equipableTypeMeta().requirements());
                item.addQuestRequirements(deserialized.equipableTypeMeta().requiredQuests());
                item.addSkillModifiers(deserialized.equipableTypeMeta().skillModifiers());
                item.setEquipableData(deserialized.equipableTypeMeta().equipmentSlot(), deserialized.equipableTypeMeta().equipableModel());
                item.setDefaultStateTemplate(new GearGemSocketsState(new GearGemSocketsStateMeta(gemIds, deserialized.gemSlots())));
                return item;
            }
            default -> throw new UnsupportedJsonVersionException(meta.type().name(), json.version());
        }

    }

    private SocketedRangedWeapon createRangedWeaponWithSockets(AbstractItemMeta meta, @Nullable VersionedJson json) {
        if (json == null) {
            throw new UnsupportedOperationException("Cannot create " + meta.type() + " " + meta.displayName() + " required json is null!");
        }

        switch (json.version()) {
            case 1 -> {
                SocketedWeaponEquipableMeta deserialized = new Gson().fromJson(json.meta(), SocketedWeaponEquipableMeta.class);
                SocketedRangedWeapon item = new SocketedRangedWeapon(meta, deserialized.equipableTypeMeta().speed());
                List<Integer> gemIds = deserialized.defaultGemIds();

                if (gemIds == null) {
                    gemIds = new ArrayList<>();
                }

                item.addModifiers(deserialized.equipableTypeMeta().modifiers());
                item.addSkillRequirements(deserialized.equipableTypeMeta().requirements());
                item.addQuestRequirements(deserialized.equipableTypeMeta().requiredQuests());
                item.addSkillModifiers(deserialized.equipableTypeMeta().skillModifiers());

                item.setDefaultStateTemplate(new GearGemSocketsState(new GearGemSocketsStateMeta(gemIds, deserialized.gemSlots())));
                return item;
            }
            default -> throw new UnsupportedJsonVersionException(meta.type().name(), json.version());
        }

    }

    private ChanceItem createChanceItem(AbstractItemMeta meta, @Nullable VersionedJson json) {
        if (json == null) {
            throw new UnsupportedOperationException("Cannot create " + meta.type() + " " + meta.displayName() + " required json is null!");
        }

        switch (json.version()) {
            case 1 -> {
                ChanceItemMeta deserialized = new Gson().fromJson(json.meta(), ChanceItemMeta.class);
                ChanceItem item = new ChanceItem(meta);

                item.setDropTableIds(deserialized.dropTableIds());
                item.setSounds(deserialized.openSound(), deserialized.openFinishSound());

                return item;
            }
            default -> throw new UnsupportedJsonVersionException(meta.type().name(), json.version());
        }

    }

    private Currency createCurrencyItem(AbstractItemMeta meta) {
        return new Currency(meta);
    }

    private Arrow createArrowItem(AbstractItemMeta meta, @Nullable VersionedJson json) {
        if (json == null) {
            throw new UnsupportedOperationException("Cannot create " + meta.type() + " " + meta.displayName() + " required json is null!");
        }

        switch (json.version()) {
            case 1 -> {
                EquipableTypeMeta deserialized = new Gson().fromJson(json.meta(), EquipableTypeMeta.class);
                Arrow item = new Arrow(meta);

                item.addModifiers(deserialized.modifiers());
                item.addSkillRequirements(deserialized.requirements());
                item.addQuestRequirements(deserialized.requiredQuests());
                item.addSkillModifiers(deserialized.skillModifiers());
                return item;
            }
            default -> throw new UnsupportedJsonVersionException(meta.type().name(), json.version());
        }

    }

    private EmptyContainer createEmptyContainer(AbstractItemMeta meta, @Nullable VersionedJson json) {
        if (json == null) {
            throw new UnsupportedOperationException("Cannot create " + meta.type() + " " + meta.displayName() + " required json is null!");
        }

        switch (json.version()) {
            case 1 -> {
                EmptyContainerMeta deserialized = new Gson().fromJson(json.meta(), EmptyContainerMeta.class);
                EmptyContainer item = new EmptyContainer(meta, deserialized.config());

                return item;
            }
            default -> throw new UnsupportedJsonVersionException(meta.type().name(), json.version());
        }

    }


    private Fairy createFairy(AbstractItemMeta meta, @Nullable VersionedJson json) {
        if (json == null) {
            throw new UnsupportedOperationException("Cannot create " + meta.type() + " " + meta.displayName() + " required json is null!");
        }

        switch (json.version()) {
            case 1 -> {
                FairyMeta deserialized = new Gson().fromJson(json.meta(), FairyMeta.class);
                Fairy item = new Fairy(meta, deserialized.model());

                item.setDefaultStateTemplate(new FairyState(new FairyStateMeta(new ArrayList<>(), 100, 0, false, deserialized.generation())));
                return item;
            }
            default -> throw new UnsupportedJsonVersionException(meta.type().name(), json.version());
        }

    }


    private FairyFruit createFairyFruit(AbstractItemMeta meta, @Nullable VersionedJson json) {
        if (json == null) {
            throw new UnsupportedOperationException("Cannot create " + meta.type() + " " + meta.displayName() + " required json is null!");
        }

        switch (json.version()) {
            case 1 -> {
                FairyFruitMeta deserialized = new Gson().fromJson(json.meta(), FairyFruitMeta.class);
                FairyFruit item = new FairyFruit(meta);

                item.addModifiers(deserialized.modifiers());
                item.addSkillModifiers(deserialized.skillModifiers());

                return item;
            }
            default -> throw new UnsupportedJsonVersionException(meta.type().name(), json.version());
        }

    }

    private FairyFood createFairyFood(AbstractItemMeta meta, @Nullable VersionedJson json) {
        if (json == null) {
            throw new UnsupportedOperationException("Cannot create " + meta.type() + " " + meta.displayName() + " required json is null!");
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
            throw new UnsupportedOperationException("Cannot create " + meta.type() + " " + meta.displayName() + " required json is null!");
        }

        switch (json.version()) {
            case 1 -> {
                GemMeta deserialized = new Gson().fromJson(json.meta(), GemMeta.class);
                Gem item = new Gem(meta);

                item.addModifiers(deserialized.modifiers());
                item.addSkillModifiers(deserialized.skillModifiers());
                return item;
            }
            default -> throw new UnsupportedJsonVersionException(meta.type().name(), json.version());
        }

    }

    private Gear createGearItem(AbstractItemMeta meta, @Nullable VersionedJson json) {
        if (json == null) {
            throw new UnsupportedOperationException("Cannot create " + meta.type() + " " + meta.displayName() + " required json is null!");
        }

        switch (json.version()) {
            case 1 -> {
                EquipableTypeMeta deserialized = new Gson().fromJson(json.meta(), EquipableTypeMeta.class);
                Gear item = new Gear(meta);

                item.addModifiers(deserialized.modifiers());
                item.addSkillRequirements(deserialized.requirements());
                item.addQuestRequirements(deserialized.requiredQuests());
                item.addSkillModifiers(deserialized.skillModifiers());
                item.setEquipableData(deserialized.equipmentSlot(), deserialized.equipableModel());

                return item;
            }
            default -> throw new UnsupportedJsonVersionException(meta.type().name(), json.version());
        }

    }


    private CompassItem createCompassItem(AbstractItemMeta meta, @Nullable VersionedJson json) {
        if (json == null) {
            throw new UnsupportedOperationException("Cannot create " + meta.type() + " " + meta.displayName() + " required json is null!");
        }

        switch (json.version()) {
            case 1 -> {
                EquipableTypeMeta deserialized = new Gson().fromJson(json.meta(), EquipableTypeMeta.class);
                CompassItem item = new CompassItem(meta);

                item.addModifiers(deserialized.modifiers());
                item.addSkillRequirements(deserialized.requirements());
                item.addQuestRequirements(deserialized.requiredQuests());
                item.addSkillModifiers(deserialized.skillModifiers());
                item.setEquipableData(deserialized.equipmentSlot(), deserialized.equipableModel());

                return item;
            }
            default -> throw new UnsupportedJsonVersionException(meta.type().name(), json.version());
        }

    }

    private Tool createToolItem(AbstractItemMeta meta, @Nullable VersionedJson json) {
        if (json == null) {
            throw new UnsupportedOperationException("Cannot create " + meta.type() + " " + meta.displayName() + " required json is null!");
        }

        switch (json.version()) {
            case 1 -> {
                EquipableTypeMeta deserialized = new Gson().fromJson(json.meta(), EquipableTypeMeta.class);
                Tool item = new Tool(meta);

                item.addModifiers(deserialized.modifiers());
                item.addSkillRequirements(deserialized.requirements());
                item.addQuestRequirements(deserialized.requiredQuests());
                item.addSkillModifiers(deserialized.skillModifiers());
                item.setEquipableData(deserialized.equipmentSlot(), deserialized.equipableModel());

                return item;
            }
            default -> throw new UnsupportedJsonVersionException(meta.type().name(), json.version());
        }

    }

    private MeleeWeapon createMeleeWeaponItem(AbstractItemMeta meta, @Nullable VersionedJson json) {
        if (json == null) {
            throw new UnsupportedOperationException("Cannot create " + meta.type() + " " + meta.displayName() + " required json is null!");
        }

        switch (json.version()) {
            case 1 -> {
                EquipableWeaponTypeMeta deserialized = new Gson().fromJson(json.meta(), EquipableWeaponTypeMeta.class);
                MeleeWeapon item = new MeleeWeapon(meta, deserialized.speed());

                item.addModifiers(deserialized.modifiers());
                item.addSkillRequirements(deserialized.requirements());
                item.addQuestRequirements(deserialized.requiredQuests());
                item.addSkillModifiers(deserialized.skillModifiers());

                return item;
            }
            default -> throw new UnsupportedJsonVersionException(meta.type().name(), json.version());
        }

    }

    private RangedWeapon createRangedWeaponItem(AbstractItemMeta meta, @Nullable VersionedJson json) {
        if (json == null) {
            throw new UnsupportedOperationException("Cannot create " + meta.type() + " " + meta.displayName() + " required json is null!");
        }

        switch (json.version()) {
            case 1 -> {
                EquipableWeaponTypeMeta deserialized = new Gson().fromJson(json.meta(), EquipableWeaponTypeMeta.class);
                RangedWeapon item = new RangedWeapon(meta, deserialized.speed());

                item.addModifiers(deserialized.modifiers());
                item.addSkillRequirements(deserialized.requirements());
                item.addQuestRequirements(deserialized.requiredQuests());
                item.addSkillModifiers(deserialized.skillModifiers());

                return item;
            }
            default -> throw new UnsupportedJsonVersionException(meta.type().name(), json.version());
        }

    }

    private QuestItem createQuestItem(AbstractItemMeta meta, @Nullable VersionedJson json) {
        if (json == null) {
            throw new UnsupportedOperationException("Cannot create " + meta.type() + " " + meta.displayName() + " required json is null!");
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

    private Material createMaterialItem(AbstractItemMeta meta, VersionedJson json) {
        if (json == null) {
            return new Material(meta);
        }

        switch (json.version()) {
            case 1 -> {
                MaterialMeta deserialized = new Gson().fromJson(json.meta(), MaterialMeta.class);
                Material item = new Material(meta);

                item.setBasePrice(deserialized.basePrice());

                return item;
            }
            default -> throw new UnsupportedJsonVersionException(meta.type().name(), json.version());
        }
    }

    private Potion createPotionItem(AbstractItemMeta meta, @Nullable VersionedJson json) {
        if (json == null) {
            throw new UnsupportedOperationException("Cannot create " + meta.type() + " " + meta.displayName() + " required json is null!");
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
            throw new UnsupportedOperationException("Cannot create " + meta.type() + " " + meta.displayName() + " required json is null!");
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

    private SocketedMeleeWeapon createMeleeWeaponWithSockets(AbstractItemMeta meta, @Nullable VersionedJson json) {
        if (json == null) {
            throw new UnsupportedOperationException("Cannot create " + meta.type() + " " + meta.displayName() + " required json is null!");
        }

        switch (json.version()) {
            case 1 -> {
                SocketedWeaponEquipableMeta deserialized = new Gson().fromJson(json.meta(), SocketedWeaponEquipableMeta.class);
                SocketedMeleeWeapon item = new SocketedMeleeWeapon(meta, deserialized.equipableTypeMeta().speed());
                List<Integer> gemIds = deserialized.defaultGemIds();

                if (gemIds == null) {
                    gemIds = new ArrayList<>();
                }

                item.addModifiers(deserialized.equipableTypeMeta().modifiers());
                item.addSkillRequirements(deserialized.equipableTypeMeta().requirements());
                item.addQuestRequirements(deserialized.equipableTypeMeta().requiredQuests());
                item.addSkillModifiers(deserialized.equipableTypeMeta().skillModifiers());
                item.setDefaultStateTemplate(new GearGemSocketsState(new GearGemSocketsStateMeta(gemIds, deserialized.gemSlots())));

                return item;
            }
            default -> throw new UnsupportedJsonVersionException(meta.type().name(), json.version());
        }

    }

    private SocketedGear createGearWithSockets(AbstractItemMeta meta, @Nullable VersionedJson json) {
        if (json == null) {
            throw new UnsupportedOperationException("Cannot create " + meta.type() + " " + meta.displayName() + " required json is null!");
        }

        switch (json.version()) {
            case 1 -> {
                SocketedEquipableMeta deserialized = new Gson().fromJson(json.meta(), SocketedEquipableMeta.class);
                SocketedGear item = new SocketedGear(meta);
                List<Integer> gemIds = deserialized.defaultGemIds();

                if (gemIds == null) {
                    gemIds = new ArrayList<>();
                }

                item.addModifiers(deserialized.equipableTypeMeta().modifiers());
                item.addSkillModifiers(deserialized.equipableTypeMeta().skillModifiers());
                item.addSkillRequirements(deserialized.equipableTypeMeta().requirements());
                item.addQuestRequirements(deserialized.equipableTypeMeta().requiredQuests());
                item.setEquipableData(deserialized.equipableTypeMeta().equipmentSlot(), deserialized.equipableTypeMeta().equipableModel());
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
