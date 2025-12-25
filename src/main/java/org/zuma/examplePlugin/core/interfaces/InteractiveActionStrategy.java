package org.zuma.examplePlugin.core.interfaces;

import org.zuma.examplePlugin.domain.player.RunePlayer;
import org.zuma.examplePlugin.domain.player.actions.ActionParams;

public interface InteractiveActionStrategy {
    InteractiveActionStrategy startAction(RunePlayer player, ActionParams params);

    void cancelTask();
}
