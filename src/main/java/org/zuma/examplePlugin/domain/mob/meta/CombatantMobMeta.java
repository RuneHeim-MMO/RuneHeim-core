package org.zuma.examplePlugin.domain.mob.meta;

import org.bukkit.entity.EntityType;
import org.zuma.examplePlugin.domain.mob.constants.EquipmentSet;
import org.zuma.examplePlugin.domain.modifier.Modifier;
import org.zuma.examplePlugin.domain.skill.SkillLevelRecord;

import javax.annotation.Nullable;
import java.util.List;

public record CombatantMobMeta(String displayName, EquipmentSet equipmentSet, List<SkillLevelRecord> skills,
                               List<Modifier> modifiers, @Nullable String skin,
                               int maxHealth, EntityType baseEntity) {
}
