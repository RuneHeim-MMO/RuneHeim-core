package org.zuma.examplePlugin.domain.modifier;

import org.zuma.examplePlugin.domain.modifier.constants.ModifierType;

public record Modifier(ModifierType type, double amount) {
}
