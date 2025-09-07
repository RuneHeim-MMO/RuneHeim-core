package org.zuma.examplePlugin.domain.item.meta.v1;

import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Horse;
import org.jetbrains.annotations.Nullable;
import org.zuma.examplePlugin.domain.skill.SkillRequirement;

import java.util.List;

public record MountMeta(@Nullable String model, @Nullable EntityType baseEntityType, @Nullable Horse.Style style,
                        @Nullable Horse.Color color, int maxLevel, double baseSpeed, double baseJump,
                        double speedLevelBonus, double jumpLevelBonus,
                        List<SkillRequirement> requirements) {
}
