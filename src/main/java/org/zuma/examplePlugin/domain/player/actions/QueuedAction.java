package org.zuma.examplePlugin.domain.player.actions;

import org.zuma.examplePlugin.core.interfaces.InteractiveActionStrategy;

public record QueuedAction(InteractiveActionStrategy strategy, ActionParams params) {
}
