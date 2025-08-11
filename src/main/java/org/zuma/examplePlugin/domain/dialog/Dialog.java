package org.zuma.examplePlugin.domain.dialog;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.domain.dialog.base.DialogStep;
import org.zuma.examplePlugin.domain.mob.base.AbstractMob;
import org.zuma.examplePlugin.domain.player.RunePlayer;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Dialog {
    public static int HOT_BAR_LOCK_SLOT_DURING_DIALOG = 8;

    private final String id;
    private final List<DialogStep> scenario;
    private int nextStepIndex, initialHeldSlot;
    private final UUID dialogHolderUUID;
    private UUID participantUUID;
    private boolean aborted = false;

    @Nullable
    private Runnable onDialogFinish;

    @Nullable
    private DialogStep currentStep;

    public Dialog(String id, UUID dialogHolderUUID, List<DialogStep> scenario) {
        this.id = id;
        this.dialogHolderUUID = dialogHolderUUID;
        this.scenario = scenario;
        this.nextStepIndex = 0;
        this.currentStep = null;

        this.scenario.forEach(step -> step.setParent(this));
    }

    public static void selectHotbarSlotToDialogSlot(RunePlayer player) {
        player.asBukkitPlayer().getInventory().setHeldItemSlot(Dialog.HOT_BAR_LOCK_SLOT_DURING_DIALOG);
    }

    protected void startDialog(RunePlayer player) {
        nextStepIndex = 0;
        participantUUID = player.getUUID();
        initialHeldSlot = player.asBukkitPlayer().getInventory().getHeldItemSlot();
        player.asBukkitPlayer().getInventory().setHeldItemSlot(Dialog.HOT_BAR_LOCK_SLOT_DURING_DIALOG);
        advanceDialogStep();
    }

    public void cleanUp() {
        aborted = true;
        scenario.forEach(DialogStep::cleanUp);
    }

    public void advanceDialogStep() {
        if (currentStep != null) {
            currentStep.cleanUp();
        }

        RunePlayer participant = getPlayerParticipant();

        if (participant == null || nextStepIndex >= scenario.size()) {
            if (onDialogFinish != null) {
                onDialogFinish.run();
            }
            if (participant != null) {
                participant.asBukkitPlayer().getInventory().setHeldItemSlot(initialHeldSlot);
            }
            return;
        }

        DialogStep nextStep = scenario.get(nextStepIndex);
        currentStep = nextStep;
        nextStepIndex++;

        Dialog.selectHotbarSlotToDialogSlot(participant);
        nextStep.showDialogStep(participant);
    }

    @Nullable
    public RunePlayer getPlayerParticipant() {
        Player bukkitPlayer = Bukkit.getPlayer(participantUUID);

        if (bukkitPlayer == null) {
            return null;
        }

        return RuneQuestPlugin.getPlayerManager().getRunePlayer(participantUUID);
    }

    @Nullable
    public AbstractMob getDialogHolder() {
        return RuneQuestPlugin.getMobRegistry().getByUUID(dialogHolderUUID);
    }

    public int getNextStepIndex() {
        return nextStepIndex;
    }

    public List<DialogStep> getScenario() {
        return scenario;
    }

    @Nullable
    public DialogStep getCurrentStep() {
        return currentStep;
    }

    public UUID getDialogHolderUUID() {
        return this.dialogHolderUUID;
    }

    public void setOnDialogFinish(@Nullable Runnable onDialogFinish) {
        this.onDialogFinish = onDialogFinish;
    }

    public String getId() {
        return id;
    }

    public boolean isAborted() {
        return aborted;
    }
}
