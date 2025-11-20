package org.zuma.examplePlugin.domain.block.base;

import net.kyori.adventure.text.Component;
import org.bukkit.*;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.zuma.examplePlugin.core.constants.Color;
import org.zuma.examplePlugin.domain.block.meta.AbstractInteractableBlockMeta;
import org.zuma.examplePlugin.domain.block.meta.v1.QuestLockedMeta;
import org.zuma.examplePlugin.domain.item.base.AbstractItemType;
import org.zuma.examplePlugin.domain.player.RunePlayer;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public abstract class QuestLockedInteractable extends AbstractInteractableBlock {
    private final @Nullable String lockedMessage;
    private final List<String> requiredCompletedQuestIds, requiredStartedQuestIds, requiredQuestStepActive;

    public QuestLockedInteractable(AbstractInteractableBlockMeta meta, QuestLockedMeta questLockedMeta) {
        super(meta, null, null);

        if (questLockedMeta != null) {
            this.lockedMessage = questLockedMeta.lockedMessage();
            this.requiredCompletedQuestIds = questLockedMeta.requiredCompletedQuestIds() != null ? questLockedMeta.requiredCompletedQuestIds() : new ArrayList<>();
            this.requiredStartedQuestIds = questLockedMeta.requiredStartedQuestIds() != null ? questLockedMeta.requiredStartedQuestIds() : new ArrayList<>();
            this.requiredQuestStepActive = questLockedMeta.requiredQuestStepActive() != null ? questLockedMeta.requiredQuestStepActive() : new ArrayList<>();
        } else {
            this.lockedMessage = null;
            this.requiredCompletedQuestIds = new ArrayList<>();
            this.requiredStartedQuestIds = new ArrayList<>();
            this.requiredQuestStepActive = new ArrayList<>();
        }
    }

    @Override
    public void onPlayerInteractEvent(RunePlayer player, Action action, PlayerInteractEvent event, AbstractItemType itemInHand) {
        event.setCancelled(true);


        boolean allowed = new HashSet<>(player.getCompletedQuestIds()).containsAll(requiredCompletedQuestIds) // Completed quests check
                && player.getActiveQuestSteps().keySet().containsAll(requiredStartedQuestIds) // Started quests check
                && (requiredQuestStepActive.isEmpty() || player.getActiveQuestSteps().values().stream().anyMatch(step -> requiredQuestStepActive.contains(step.getStableId()))); // Active quest steps check

        if (!allowed) {
            player.asBukkitPlayer().playSound(getLocation(), Sound.BLOCK_FENCE_GATE_CLOSE, 1f, 1f);

            if (lockedMessage != null) {
                player.asBukkitPlayer().sendMessage(Component.text(lockedMessage, Color.ERROR.asColor()));
            }
            return;
        }

        onInteractSuccess(player, action, event, itemInHand);
    }

    public abstract void onInteractSuccess(RunePlayer player, Action action, PlayerInteractEvent event, AbstractItemType itemInHand);
}
