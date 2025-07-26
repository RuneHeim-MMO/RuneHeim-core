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
import org.zuma.examplePlugin.domain.mob.types.*;
import org.zuma.examplePlugin.domain.modifier.Modifier;
import org.zuma.examplePlugin.domain.modifier.constants.ModifierType;
import org.zuma.examplePlugin.domain.skill.SkillLevelRecord;
import org.zuma.examplePlugin.domain.skill.constants.SkillType;

import javax.annotation.Nullable;
import java.util.List;

public class MobTypesLoader {

    public static List<SkillLevelRecord> getBaseSkills(int base) {
        return List.of(
                new SkillLevelRecord(SkillType.ATTACK, base),
                new SkillLevelRecord(SkillType.STRENGTH, (int) (base * 0.5)),
                new SkillLevelRecord(SkillType.DEFENCE, (int) (base * 0.5))
        );
    }

    public static void registerMobs(MobTypeRegistry registry) {

        Common.registerMobs(registry);
        Shopkeepers.registerMobs(registry);
        CityGuards.registerMobs(registry);
        Boars.registerMobs(registry);
        Wolves.registerMobs(registry);
        Goblin.registerMobs(registry);

        System.out.println("Loaded " + registry.getAvailable().size() + " mob types.");


    }

}

