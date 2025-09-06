package org.zuma.examplePlugin.domain.item.meta.v1;

import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import org.bukkit.entity.Horse;
import org.jetbrains.annotations.Nullable;
import org.zuma.examplePlugin.domain.skill.SkillRequirement;

import java.util.List;

public record MountMeta(@Nullable String model, @Nullable Horse.Style style,
                        @Nullable Horse.Color color, double baseSpeed, int maxLevel,
                        List<SkillRequirement> requirements) {
}
