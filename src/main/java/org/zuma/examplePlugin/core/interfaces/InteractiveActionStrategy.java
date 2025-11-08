package org.zuma.examplePlugin.core.interfaces;

import org.bukkit.scheduler.BukkitTask;
import org.zuma.examplePlugin.core.constants.SoundType;
import org.zuma.examplePlugin.domain.player.RunePlayer;
import org.zuma.examplePlugin.domain.player.actions.ActionParams;

import javax.annotation.Nullable;

public interface InteractiveActionStrategy {
    InteractiveActionStrategy startAction(RunePlayer player, ActionParams params);

    void cancelTask();
}
