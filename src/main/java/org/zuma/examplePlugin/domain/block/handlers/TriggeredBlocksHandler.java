package org.zuma.examplePlugin.domain.block.handlers;

import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.logger.constants.LogLevel;
import org.zuma.examplePlugin.domain.block.BlockManager;
import org.zuma.examplePlugin.domain.block.base.AbstractInteractableBlock;
import org.zuma.examplePlugin.domain.block.impl.door.DoorBlock;
import org.zuma.examplePlugin.domain.block.impl.trigger.ItemCollectorTrigger;
import org.zuma.examplePlugin.domain.block.impl.trigger.Trigger;

import java.util.ArrayList;
import java.util.List;

public class TriggeredBlocksHandler {
    private final BlockManager manager;

    public TriggeredBlocksHandler(BlockManager manager) {
        this.manager = manager;
    }

    public void setDoorState(String doorId, boolean open) {
        boolean found = false;

        for (AbstractInteractableBlock block : this.manager.getAllBlocks()) {

            if (block instanceof DoorBlock doorBlock) {
                if (doorBlock.getDoorId().equals(doorId)) {
                    doorBlock.setOpen(open);
                    found = true;
                }
            }
        }

        if (!found) {
            RuneQuestPlugin.getRuneLogger().log(LogLevel.WARN, "Could not find door with ID " + doorId + " to set state to " + (open ? "open" : "closed") + ".");
        }

    }

    private void handleDoorStateTrigger(String doorId, boolean conditionsMet) {
        setDoorState(doorId, conditionsMet);
    }

    public void onTriggerTriggered(String groupId, String triggeredEntityId) {
        boolean groupAllTriggered = true;
        List<Trigger> triggers = new ArrayList<>();

        for (AbstractInteractableBlock block : this.manager.getAllBlocks()) {
            if (!(block instanceof Trigger trigger)) {
                continue;
            }

            if (!trigger.getGroupId().equals(groupId)) {
                continue;
            }

            if (!trigger.isTriggered()) {
                groupAllTriggered = false;
            }

            triggers.add(trigger);
        }

        if (groupAllTriggered) {
            triggers.forEach(Trigger::startStateResetCountdown);
        }

        handleDoorStateTrigger(triggeredEntityId, groupAllTriggered);

    }
}
