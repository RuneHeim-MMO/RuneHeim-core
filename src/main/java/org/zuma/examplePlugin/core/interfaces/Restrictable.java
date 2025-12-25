package org.zuma.examplePlugin.core.interfaces;

import org.zuma.examplePlugin.domain.skill.SkillRequirement;

import java.util.List;

public interface Restrictable {
    List<SkillRequirement> getSkillRequirements();

    List<String> getRequiredCompletedQuests();
}
