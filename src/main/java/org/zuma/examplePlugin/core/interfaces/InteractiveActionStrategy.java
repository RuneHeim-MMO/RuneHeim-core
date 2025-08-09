package org.zuma.examplePlugin.core.interfaces;

import org.zuma.examplePlugin.core.constants.SoundType;
import org.zuma.examplePlugin.domain.player.actions.ActionParams;

import javax.annotation.Nullable;

public interface InteractiveActionStrategy {
    public void startAction(ActionParams params);
}
