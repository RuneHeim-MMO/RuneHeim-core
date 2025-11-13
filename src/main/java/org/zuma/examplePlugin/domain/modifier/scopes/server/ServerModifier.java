package org.zuma.examplePlugin.domain.modifier.scopes.server;

public record ServerModifier(ServerModifierType type, long activeUntilMillis) {
}
