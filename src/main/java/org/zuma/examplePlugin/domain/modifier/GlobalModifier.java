package org.zuma.examplePlugin.domain.modifier;

import org.zuma.examplePlugin.domain.modifier.constants.ModifierType;

public record GlobalModifier(ModifierType type, String label, double amount, long activeUntilMillis) {
}
