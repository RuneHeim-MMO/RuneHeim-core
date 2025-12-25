package org.zuma.examplePlugin.domain.quest.impl;

import org.bukkit.event.EventHandler;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.domain.quest.PlayerQuestState;
import org.zuma.examplePlugin.domain.quest.Quest;
import org.zuma.examplePlugin.domain.quest.base.AbstractQuestStep;
import org.zuma.examplePlugin.domain.quest.constants.StepType;
import org.zuma.examplePlugin.events.block.TeleportDoorEnteredEvent;

import javax.annotation.Nullable;
import java.util.UUID;


public class EnterTeleportDoorStep extends AbstractQuestStep {
    private final String doorId;

    public EnterTeleportDoorStep(RuneQuestPlugin plugin, Quest parentQuest, String description, String stableId, String doorId, @Nullable UUID owner) {
        super(plugin, parentQuest, StepType.ENTER_TELEPORT_DOOR, description, stableId, owner);
        this.doorId = doorId;
    }

    public float getProgressPercentage() {
        return 0f;
    }

    @EventHandler
    public void onPlayerEnterDoor(TeleportDoorEnteredEvent event) {
        if (!event.getPlayer().getUUID().equals(getOwner())) return;
        if (!event.getDoor().getDoorId().equals(this.doorId)) return;

        this.handleStepCompletion();
    }

    @Override
    public AbstractQuestStep cloneForPlayer(UUID ownerUUID) {
        EnterTeleportDoorStep step = new EnterTeleportDoorStep(super.getPlugin(), super.getParentQuest(), super.getDescription(), super.getStableId(), doorId, ownerUUID);
        step.setId(this.getId());
        return step;
    }

    @Override
    public AbstractQuestStep assignValuesFromState(PlayerQuestState state) {
        // Not assigning completed here, because its 'in progress' state is non-existent. as soon as completed is set to true, the next quest step replaces it.
        return this;
    }

    @Override
    public PlayerQuestState assignValuesToState(PlayerQuestState state) {
        return state;
    }
}
