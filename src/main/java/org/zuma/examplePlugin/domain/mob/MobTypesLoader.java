package org.zuma.examplePlugin.domain.mob;

import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.logger.constants.LogLevel;
import org.zuma.examplePlugin.domain.mob.types.*;
import org.zuma.examplePlugin.domain.skill.SkillLevelRecord;
import org.zuma.examplePlugin.domain.skill.constants.SkillType;

import java.util.List;

public class MobTypesLoader {

    public static List<SkillLevelRecord> getBaseSkills(int base) {
        return List.of(
                new SkillLevelRecord(SkillType.ATTACK, Math.max(1, base)),
                new SkillLevelRecord(SkillType.RANGED, Math.max(1, base)),
                new SkillLevelRecord(SkillType.HITPOINTS, Math.max(1, base)),
                new SkillLevelRecord(SkillType.STRENGTH, Math.max(1, (int) (base * 0.5))),
                new SkillLevelRecord(SkillType.DEFENCE, Math.max(1, (int) (base * 0.5)))
        );
    }

    public static void registerMobs(MobTypeRegistry registry) {

        Common.registerMobs(registry);
        Shopkeepers.registerMobs(registry);
        CityGuards.registerMobs(registry);
        Boars.registerMobs(registry);
        Wolves.registerMobs(registry);
        Goblin.registerMobs(registry);
        BankTellers.registerMobs(registry);
        Deers.registerMobs(registry);
        Bunnies.registerMobs(registry);
        Eagles.registerMobs(registry);
        Moles.registerMobs(registry);
        Cows.registerMobs(registry);
        InteractiveMobs.registerMobs(registry);

        RuneQuestPlugin.getRuneLogger().log(LogLevel.INFO, "Loaded " + registry.getAvailable().size() + " mob types.");


    }

}

