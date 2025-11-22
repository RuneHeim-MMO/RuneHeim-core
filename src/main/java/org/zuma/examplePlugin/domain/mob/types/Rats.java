package org.zuma.examplePlugin.domain.mob.types;

import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import org.zuma.examplePlugin.domain.droptable.DropTableId;
import org.zuma.examplePlugin.domain.mob.MobTypeRegistry;
import org.zuma.examplePlugin.domain.mob.behavior.MobBehaviors;
import org.zuma.examplePlugin.domain.mob.constants.MobType;
import org.zuma.examplePlugin.domain.mob.impl.GenericCombatant;
import org.zuma.examplePlugin.domain.skill.SkillLevelRecord;
import org.zuma.examplePlugin.domain.skill.constants.SkillType;

import java.util.List;

import static org.zuma.examplePlugin.domain.mob.MobTypesLoader.getBaseSkills;

public class Rats {

    public static void registerMobs(MobTypeRegistry registry) {
        registry.register(MobType.RAT_BROWN_BASE_1.getId(), () -> new GenericCombatant(
                "Rat", null,
                List.of(new SkillLevelRecord(SkillType.ATTACK, 1)),
                null,
                MobBehaviors.RAT.getBehavior(),
                "nm_rat_brown",
                0.23, false, false, 3,
                List.of(DropTableId.RAT.asInt()),
                true));

        registry.register(MobType.RAT_GRAY_BASE_1.getId(), () -> new GenericCombatant(
                "Rat", null,
                List.of(new SkillLevelRecord(SkillType.ATTACK, 1)),
                null,
                MobBehaviors.RAT.getBehavior(),
                "nm_rat_gray",
                0.23, false, false, 3,
                List.of(DropTableId.RAT.asInt()),
                true));

        registry.register(MobType.RAT_ALBINO_BASE_1.getId(), () -> new GenericCombatant(
                "Rat", null,
                List.of(new SkillLevelRecord(SkillType.ATTACK, 1)),
                null,
                MobBehaviors.RAT.getBehavior(),
                "nm_rat_albino",
                0.23, false, false, 3,
                List.of(DropTableId.RAT.asInt()),
                true));

        registry.register(MobType.RAT_WELCOME_TO_DRAKKAR_BASE_1.getId(), () -> new GenericCombatant(
                "Rat", null,
                List.of(new SkillLevelRecord(SkillType.ATTACK, 1)),
                null,
                MobBehaviors.RAT.getBehavior(),
                "nm_rat_albino",
                0.23, false, false, 3,
                List.of(DropTableId.RAT.asInt()),
                true));

    }
}

