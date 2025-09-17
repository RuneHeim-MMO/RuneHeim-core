package org.zuma.examplePlugin.domain.quest.impl;

import org.bukkit.event.EventHandler;
import org.bukkit.inventory.ItemStack;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.domain.item.base.AbstractItemType;
import org.zuma.examplePlugin.domain.quest.PlayerQuestState;
import org.zuma.examplePlugin.domain.quest.Quest;
import org.zuma.examplePlugin.domain.quest.constants.StepType;
import org.zuma.examplePlugin.domain.quest.base.AbstractQuestStep;
import org.zuma.examplePlugin.events.dialog.DialogQuestStepToBeCompleted;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class DialogStep extends AbstractQuestStep {
    /*
     * Dialog steps are calculated by id, this stableId allows existing dialogs
     * to keep setting completion of this step even if order of steps changes. Also makes it easier to track.
     */
    private String stableId;

    public DialogStep(RuneQuestPlugin plugin, Quest parentQuest, String description, String stableId, @Nullable UUID owner) {
        super(plugin, parentQuest, StepType.DIALOG, description, owner);
        this.stableId = stableId;
    }

    public String getStableId() {
        return stableId;
    }

    @EventHandler
    public void onDialogStepCompleteTriggered(DialogQuestStepToBeCompleted event) {
        if (!event.getPlayerUUID().equals(getOwner()) || !Objects.equals(event.getStableDialogStepId(), this.stableId))
            return;
        this.handleStepCompletion();
    }


    @Override
    public AbstractQuestStep cloneForPlayer(UUID ownerUUID) {
        DialogStep step = new DialogStep(super.getPlugin(), super.getParentQuest(), super.getDescription(), stableId, ownerUUID);
        step.setId(this.getId());

        return step;
    }

    @Override
    public AbstractQuestStep assignValuesFromState(PlayerQuestState state) {
        this.stableId = state.getStepProgressData().get("stableId").toString();
        // Not assigning completed here, because its 'in progress' state is non-existent. as soon as completed is set to true, the next quest step replaces it.
        return this;
    }

    @Override
    public PlayerQuestState assignValuesToState(PlayerQuestState state) {
        Map<String, Object> toBeMutated = state.getStepProgressData();
        toBeMutated.put("stableId", this.stableId);
        return state;
    }
}
