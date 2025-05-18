package org.zuma.examplePlugin.interfaces;

import org.jetbrains.annotations.NotNull;
import org.zuma.examplePlugin.domain.item.SkillRequirement;

import java.util.List;

public interface WithRequirements {
    @NotNull
    List<SkillRequirement> getRequirements();
}
