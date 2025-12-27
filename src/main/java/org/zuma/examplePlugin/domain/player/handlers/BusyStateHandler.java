package org.zuma.examplePlugin.domain.player.handlers;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import org.zuma.examplePlugin.core.analytics.AnalyticsKey;
import org.zuma.examplePlugin.core.constants.Color;
import org.zuma.examplePlugin.core.constants.SoundType;
import org.zuma.examplePlugin.core.interfaces.InteractiveActionStrategy;
import org.zuma.examplePlugin.core.utils.AnalyticsUtils;
import org.zuma.examplePlugin.core.utils.TextUtils;
import org.zuma.examplePlugin.domain.hud.HudService;
import org.zuma.examplePlugin.domain.player.RunePlayer;
import org.zuma.examplePlugin.domain.player.actions.ActionParams;
import org.zuma.examplePlugin.domain.player.actions.QueuedAction;

import java.util.ArrayList;
import java.util.List;

public class BusyStateHandler {
    private final RunePlayer player;
    private final List<QueuedAction> pendingActions;

    public BusyStateHandler(RunePlayer player) {
        this.player = player;
        this.pendingActions = new ArrayList<>();
    }

    // Busy state management
    public boolean queueOrStartAction(InteractiveActionStrategy strategy, ActionParams params, boolean stackable) {
        if (!stackable && hasPendingActions()) {
            HudService.showFailTitle(this.player, "You are already busy!");
            return false;
        }

        if (pendingActions.size() >= this.player.getRole().getMaxQueueSize()) {
            SoundType.ACTION_QUEUE_FAIL_SOUND.play(player, false);
            return false;
        }

        if (pendingActions.isEmpty()) {
            strategy.startAction(this.player, params);
        }

        pendingActions.add(new QueuedAction(strategy, params));
        this.player.getScoreboardManager().setPlayerCollision(false);

        if (pendingActions.size() > 1) {
            SoundType.ACTION_QUEUE_SUCCESS_SOUND.play(player, false);
        }

        return true;
    }

    public boolean hasPendingActions() {
        return !this.pendingActions.isEmpty();
    }

    public void cancelActionTask(boolean interrupted) {
        if (interrupted && hasPendingActions()) {
            this.player.asBukkitPlayer().showTitle(Title.title(
                    Component.empty(),
                    Component.text("Task interrupted.").color(Color.ERROR.asColor()),
                    TextUtils.getGenericDuration()
            ));
            SoundType.INTERRUPT_ACTION.play(this.player, false);
            this.player.getScoreboardManager().setPlayerCollision(true);
            this.pendingActions.forEach(action -> action.strategy().cancelTask());
            this.pendingActions.clear();
            AnalyticsUtils.incrementServerAndPlayer(player, AnalyticsKey.ACTIONS_INTERRUPTED, 1);
            return;
        }

        if (this.pendingActions.isEmpty()) {
            this.player.getScoreboardManager().setPlayerCollision(true);
            return;
        }

        QueuedAction firstInQueue = pendingActions.getFirst();

        if (firstInQueue != null) {
            firstInQueue.strategy().cancelTask();
            this.pendingActions.removeFirst();
        }

        if (!this.pendingActions.isEmpty()) {
            QueuedAction secondInQueue = pendingActions.getFirst();
            secondInQueue.strategy().startAction(this.player, secondInQueue.params());
        }
    }

    public int getQueuedTaskCount() {
        return this.pendingActions.size();
    }

    public List<QueuedAction> getQueuedTasks() {
        return pendingActions;
    }
}
