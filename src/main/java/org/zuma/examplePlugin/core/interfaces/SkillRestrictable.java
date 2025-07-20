package org.zuma.examplePlugin.core.interfaces;

import org.zuma.examplePlugin.domain.skill.SkillRequirement;
import org.zuma.examplePlugin.domain.skill.constants.SkillType;

import java.util.HashMap;
import java.util.List;

public interface SkillRestrictable {
    List<SkillRequirement> getRequirements();
}
