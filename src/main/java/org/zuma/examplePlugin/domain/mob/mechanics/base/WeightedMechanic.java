package org.zuma.examplePlugin.domain.mob.mechanics.base;

import org.jetbrains.annotations.Nullable;
import org.zuma.examplePlugin.core.constants.SoundType;

public record WeightedMechanic(int weight, int cost, MobMechanic mechanic, @Nullable String executionMessage,
                               @Nullable SoundType executionSound) {
}
