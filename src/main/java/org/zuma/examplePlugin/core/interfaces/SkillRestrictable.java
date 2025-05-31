package org.zuma.examplePlugin.core.interfaces;

import org.jetbrains.annotations.NotNull;
import org.zuma.examplePlugin.domain.skill.SkillRequirement;

import java.util.List;

public interface SkillRestrictable {
    @NotNull
    List<SkillRequirement> getRequirements();
}
