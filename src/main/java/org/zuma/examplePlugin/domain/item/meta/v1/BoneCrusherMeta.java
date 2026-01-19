package org.zuma.examplePlugin.domain.item.meta.v1;

import org.zuma.examplePlugin.domain.modifier.Modifier;
import org.zuma.examplePlugin.domain.modifier.SkillModifier;

import javax.annotation.Nullable;
import java.util.List;

public record BoneCrusherMeta(double buryExperienceMultiplier, double maxCharges) {
}
