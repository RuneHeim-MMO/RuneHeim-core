package org.zuma.examplePlugin.core.utils;

import org.zuma.examplePlugin.domain.modifier.Modifier;
import org.zuma.examplePlugin.domain.modifier.constants.ModifierType;

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
}
