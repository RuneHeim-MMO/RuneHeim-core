package org.zuma.examplePlugin.domain.modifier.scopes.global;

import org.zuma.examplePlugin.domain.modifier.constants.ModifierType;

public record GlobalModifier(ModifierType type, String label, double amount, long activeUntilMillis) {
}
