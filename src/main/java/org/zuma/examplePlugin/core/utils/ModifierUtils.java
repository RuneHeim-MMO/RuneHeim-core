package org.zuma.examplePlugin.core.utils;

import org.zuma.examplePlugin.domain.modifier.Modifier;
import org.zuma.examplePlugin.domain.modifier.SkillModifier;
import org.zuma.examplePlugin.domain.modifier.constants.ModifierType;
import org.zuma.examplePlugin.domain.skill.constants.SkillType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ModifierUtils {

    public static List<Modifier> combineModifiersByType(List<Modifier> modifiers) {
        Map<ModifierType, Double> combined = new HashMap<>();

        for (Modifier modifier : modifiers) {
            combined.merge(modifier.type(), modifier.amount(), Double::sum);
        }

        return combined.entrySet().stream()
                .map(entry -> new Modifier(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }

    public static List<SkillModifier> combineSkillModifiersByType(List<SkillModifier> modifiers) {
        class Acc {
            int amount = 0;
            double percent = 0;
        }

        Map<SkillType, Acc> combined = new HashMap<>();

        for (SkillModifier modifier : modifiers) {
            Acc acc = combined.computeIfAbsent(modifier.type(), k -> new Acc());
            acc.amount += modifier.amount();
            if (modifier.percent() != null) {
                acc.percent += modifier.percent();
            }
        }

        return combined.entrySet().stream()
                .map(e -> new SkillModifier(
                        e.getKey(),
                        e.getValue().amount,
                        e.getValue().percent == 0 ? null : e.getValue().percent
                ))
                .collect(Collectors.toList());
    }

}
