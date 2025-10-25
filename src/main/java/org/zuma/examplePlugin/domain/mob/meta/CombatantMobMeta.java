package org.zuma.examplePlugin.domain.mob.meta;

import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import org.bukkit.entity.EntityType;
import org.zuma.examplePlugin.domain.mob.constants.EquipmentSet;
import org.zuma.examplePlugin.domain.modifier.Modifier;
import org.zuma.examplePlugin.domain.skill.SkillLevelRecord;

import javax.annotation.Nullable;
import java.util.List;

public record CombatantMobMeta(String displayName, EquipmentSet equipmentSet, List<SkillLevelRecord> skills,
                               @Nullable List<Modifier> modifiers, @Nullable String skin, @Nullable String model,
                               @Nullable DisguiseType disguiseType, List<Integer> dropTableIds,
                               boolean safeSpottable, int maxHealth, EntityType baseEntity) {
}
