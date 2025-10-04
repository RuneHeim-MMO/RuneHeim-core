package org.zuma.examplePlugin.domain.item;

import org.zuma.examplePlugin.domain.modifier.Modifier;
import org.zuma.examplePlugin.domain.modifier.SkillModifier;

import java.util.List;

// effectKey is a unique identifier for the effect, used for stacking and referencing
public record StatsEffect(String effectKey, int durationSeconds, List<Modifier> modifiers, List<SkillModifier> skillModifiers) {
}
