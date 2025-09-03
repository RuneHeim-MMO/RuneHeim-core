package org.zuma.examplePlugin.domain.item.meta.v1;

import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import org.jetbrains.annotations.Nullable;
import org.zuma.examplePlugin.domain.skill.SkillRequirement;

import java.util.List;

public record MountMeta(@Nullable String model, @Nullable DisguiseType disguise, int defaultLevel,
                        List<SkillRequirement> requirements) {
}
