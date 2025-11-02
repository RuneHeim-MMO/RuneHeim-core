package org.zuma.examplePlugin.domain.mob;

import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.logger.constants.LogLevel;
import org.zuma.examplePlugin.domain.mob.types.*;
import org.zuma.examplePlugin.domain.skill.SkillLevelRecord;
import org.zuma.examplePlugin.domain.skill.constants.SkillType;

import java.util.List;

public class MobTypesLoader {

    public static List<SkillLevelRecord> getBaseSkills(int base, double strDefMultiplier) {
        return List.of(
                new SkillLevelRecord(SkillType.ATTACK, Math.max(1, base)),
                new SkillLevelRecord(SkillType.RANGED, Math.max(1, base)),
                new SkillLevelRecord(SkillType.HITPOINTS, Math.max(1, base)),
                new SkillLevelRecord(SkillType.STRENGTH, Math.max(1, (int) (base * strDefMultiplier))),
                new SkillLevelRecord(SkillType.DEFENCE, Math.max(1, (int) (base * strDefMultiplier)))
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
        QuestMobs.registerMobs(registry);
        Decorations.registerMobs(registry);
        Ocelots.registerMobs(registry);
        Frogs.registerMobs(registry);
        Armadillo.registerMobs(registry);
        Foxes.registerMobs(registry);
        Bandits.registerMobs(registry);
        Citizens.registerMobs(registry);
        DungeonMobs.registerMobs(registry);

        RuneQuestPlugin.getRuneLogger().log(LogLevel.INFO, "Loaded " + registry.getAvailable().size() + " mob types.");


    }

}

