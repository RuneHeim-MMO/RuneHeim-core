package org.zuma.examplePlugin.domain.mob;

import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import org.zuma.examplePlugin.core.constants.Icon;
import org.zuma.examplePlugin.domain.droptable.DropTableId;
import org.zuma.examplePlugin.domain.gui.constants.InventoryId;
import org.zuma.examplePlugin.domain.mob.behavior.MobBehavior;
import org.zuma.examplePlugin.domain.mob.behavior.MobBehaviors;
import org.zuma.examplePlugin.domain.mob.constants.EquipmentSet;
import org.zuma.examplePlugin.domain.mob.constants.MobType;
import org.zuma.examplePlugin.domain.mob.impl.FusionMaster;
import org.zuma.examplePlugin.domain.mob.impl.GenericCombatant;
import org.zuma.examplePlugin.domain.mob.impl.GenericShopkeeper;
import org.zuma.examplePlugin.domain.mob.impl.ShearableSheep;
import org.zuma.examplePlugin.domain.modifier.Modifier;
import org.zuma.examplePlugin.domain.modifier.constants.ModifierType;
import org.zuma.examplePlugin.domain.skill.SkillLevelRecord;
import org.zuma.examplePlugin.domain.skill.constants.SkillType;

import javax.annotation.Nullable;
import java.util.List;

public class MobTypesLoader {

    public static void registerMobs(MobTypeRegistry registry) {
        registry.register(MobType.FUSION_MASTER.getId(), () -> new FusionMaster(Icon.SCROLL_RED.get() + " Fusion Master", MobBehaviors.FUSION_MASTER.getBehavior(), 0));

        registry.register(MobType.GENERIC_SHOPKEEPER.getId(), () -> new GenericShopkeeper(Icon.CHEST.get() + " General Store", EquipmentSet.SHOPKEEPER_1, MobBehaviors.VILLAGER_MAN_1.getBehavior(), "shopkeeper", InventoryId.GENERAL_STORE.asInt(), 20));

        registry.register(MobType.AGGRESSIVE_BANDIT_1.getId(), () -> new GenericCombatant("Bandit", EquipmentSet.BANDIT_1,
                List.of(new SkillLevelRecord(SkillType.ATTACK, 1), new SkillLevelRecord(SkillType.STRENGTH, 1), new SkillLevelRecord(SkillType.DEFENCE, 1)),
                List.of(new Modifier(ModifierType.ATTACK_SLASH, 5)), MobBehaviors.VILLAGER_MAN_1.getBehavior(),
                0.23, false, true, 50, "bandit", List.of(DropTableId.BONES.asInt())));

        registry.register(MobType.AGGRESSIVE_BANDIT_2.getId(), () -> new GenericCombatant("Bandit", EquipmentSet.BANDIT_2,
                List.of(new SkillLevelRecord(SkillType.ATTACK, 1), new SkillLevelRecord(SkillType.STRENGTH, 1), new SkillLevelRecord(SkillType.DEFENCE, 1)),
                List.of(new Modifier(ModifierType.ATTACK_SLASH, 5)), MobBehaviors.VILLAGER_MAN_1.getBehavior(),
                0.23, false, true, 50, "bandit", List.of(DropTableId.BONES.asInt())));


        registry.register(MobType.GOBLIN_LOW_LVL_1.getId(), () -> new GenericCombatant("Goblin", EquipmentSet.GOBLIN_1,
                List.of(new SkillLevelRecord(SkillType.ATTACK, 5), new SkillLevelRecord(SkillType.STRENGTH, 5), new SkillLevelRecord(SkillType.DEFENCE, 5)),
                null, MobBehaviors.GOBLIN.getBehavior(),
                0.23, false, false, 20, "goblin_1", List.of(DropTableId.BONES.asInt(), DropTableId.COINS_SMALL.asInt())));


        registry.register(MobType.GOBLIN_LOW_LVL_2.getId(), () -> new GenericCombatant("Goblin", EquipmentSet.GOBLIN_2,
                List.of(new SkillLevelRecord(SkillType.ATTACK, 40), new SkillLevelRecord(SkillType.STRENGTH, 40), new SkillLevelRecord(SkillType.DEFENCE, 40)),
                null, MobBehaviors.GOBLIN.getBehavior(),
                0.33, false, false, 8, "goblin_1", List.of(DropTableId.BONES.asInt(), DropTableId.COINS_SMALL.asInt())));

        registry.register(MobType.SHEEP.getId(), () -> new ShearableSheep("Sheep",
                List.of(new SkillLevelRecord(SkillType.ATTACK, 1), new SkillLevelRecord(SkillType.STRENGTH, 1), new SkillLevelRecord(SkillType.DEFENCE, 1)),
                null, MobBehaviors.SHEEP.getBehavior(),
                0.2, false, false, 3));







        registry.register(MobType.BOAR_BROWN.getId(), () -> new GenericCombatant(
                "Brown Boar", null,
                List.of(
                        new SkillLevelRecord(SkillType.ATTACK, 10),
                        new SkillLevelRecord(SkillType.STRENGTH, 5),
                        new SkillLevelRecord(SkillType.DEFENCE, 5)
                ),
                null,
                MobBehaviors.BOAR.getBehavior(),
                "yungwilder_isles_boar_brown",
                0.33, false, false, 8,
                List.of(DropTableId.BONES.asInt(), DropTableId.COINS_SMALL.asInt())
        ));

        registry.register(MobType.BOAR_BROWN_ALPHA.getId(), () -> new GenericCombatant(
                "Alpha Brown Boar", null,
                List.of(
                        new SkillLevelRecord(SkillType.ATTACK, 15),
                        new SkillLevelRecord(SkillType.STRENGTH, 8),
                        new SkillLevelRecord(SkillType.DEFENCE, 7)
                ),
                null,
                MobBehaviors.BOAR.getBehavior(),
                "yungwilder_isles_boar_brown_alpha",
                0.35, false, false, 12,
                List.of(DropTableId.BONES.asInt(), DropTableId.COINS_SMALL.asInt())
        ));

        registry.register(MobType.BOAR_GRAY.getId(), () -> new GenericCombatant(
                "Gray Boar", null,
                List.of(
                        new SkillLevelRecord(SkillType.ATTACK, 12),
                        new SkillLevelRecord(SkillType.STRENGTH, 6),
                        new SkillLevelRecord(SkillType.DEFENCE, 6)
                ),
                null,
                MobBehaviors.BOAR.getBehavior(),
                "yungwilder_isles_boar_gray",
                0.35, false, false, 9,
                List.of(DropTableId.BONES.asInt(), DropTableId.COINS_SMALL.asInt())
        ));

        registry.register(MobType.BOAR_GRAY_ALPHA.getId(), () -> new GenericCombatant(
                "Alpha Gray Boar", null,
                List.of(
                        new SkillLevelRecord(SkillType.ATTACK, 16),
                        new SkillLevelRecord(SkillType.STRENGTH, 9),
                        new SkillLevelRecord(SkillType.DEFENCE, 8)
                ),
                null,
                MobBehaviors.BOAR.getBehavior(),
                "yungwilder_isles_boar_gray_alpha",
                0.34, false, false, 13,
                List.of(DropTableId.BONES.asInt(), DropTableId.COINS_SMALL.asInt())
        ));

        registry.register(MobType.BOAR_LIGHT_BROWN.getId(), () -> new GenericCombatant(
                "Light Brown Boar", null,
                List.of(
                        new SkillLevelRecord(SkillType.ATTACK, 11),
                        new SkillLevelRecord(SkillType.STRENGTH, 5),
                        new SkillLevelRecord(SkillType.DEFENCE, 6)
                ),
                null,
                MobBehaviors.BOAR.getBehavior(),
                "yungwilder_isles_boar_light_brown",
                0.34, false, false, 8,
                List.of(DropTableId.BONES.asInt(), DropTableId.COINS_SMALL.asInt())
        ));

        registry.register(MobType.BOAR_LIGHT_BROWN_ALPHA.getId(), () -> new GenericCombatant(
                "Alpha Light Brown Boar", null,
                List.of(
                        new SkillLevelRecord(SkillType.ATTACK, 14),
                        new SkillLevelRecord(SkillType.STRENGTH, 7),
                        new SkillLevelRecord(SkillType.DEFENCE, 7)
                ),
                null,
                MobBehaviors.BOAR.getBehavior(),
                "yungwilder_isles_boar_light_brown_alpha",
                0.32, false, false, 11,
                List.of(DropTableId.BONES.asInt(), DropTableId.COINS_SMALL.asInt())
        ));



    }

}

