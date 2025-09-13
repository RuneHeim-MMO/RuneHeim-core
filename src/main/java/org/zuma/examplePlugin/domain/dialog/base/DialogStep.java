package org.zuma.examplePlugin.domain.dialog.base;

import eu.decentsoftware.holograms.api.DHAPI;
import eu.decentsoftware.holograms.api.holograms.Hologram;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.constants.SoundType;
import org.zuma.examplePlugin.core.logger.constants.LogLevel;
import org.zuma.examplePlugin.domain.dialog.Dialog;
import org.zuma.examplePlugin.domain.dialog.DialogText;
import org.zuma.examplePlugin.domain.mob.base.AbstractMob;
import org.zuma.examplePlugin.domain.player.RunePlayer;

import java.util.List;
import java.util.UUID;

public abstract class DialogStep {
    protected Dialog parent;
    private final List<DialogText> content;
    private final UUID hologramUUID = UUID.randomUUID();
    private boolean finishedRendering = false;

    public DialogStep(List<DialogText> content) {
        this.content = content;
    }

    private String replaceTemplates(String input, RunePlayer viewer) {
        return input
                .replaceAll("\\{PLAYER_NAME}", viewer.asBukkitPlayer().getName())
                .replaceAll("\\{PLAYER_LEVEL}", String.valueOf(viewer.getCombatLvl()));
    }

    private Location getDialogLocationBetween(Entity mob, Player player, double offsetDistance, double yOffset) {
        Location mobEyeLoc = (mob instanceof LivingEntity living)
                ? living.getEyeLocation()
                : mob.getLocation().add(0, mob.getHeight() / 2.0, 0);

        Location playerEyeLoc = player.getEyeLocation();

        // Direction from mob to player
        Vector direction = playerEyeLoc.toVector().subtract(mobEyeLoc.toVector()).normalize();

        // Offset from mob toward player
        Location dialogLocation = mobEyeLoc.clone().add(direction.multiply(offsetDistance));

        // Apply vertical adjustment
        dialogLocation.add(0, yOffset, 0);

        return dialogLocation;
    }

    public void setParent(Dialog parent) {
        this.parent = parent;
    }

    public void showDialogStep(RunePlayer player) {
        AbstractMob holder = parent.getDialogHolder();

        if (holder == null) {
            RuneQuestPlugin.getRuneLogger().log(LogLevel.WARN, "Invalid dialog holder UUID " + parent.getDialogHolderUUID() + "! In dialog id " + parent.getId() + ". Aborting dialog.");
            RuneQuestPlugin.getDialogHandler().abortDialog(player);
            return;
        }

        Entity holderEntity = holder.getEntity();

        if (holderEntity == null || holderEntity.isDead() || !holderEntity.isValid()) {
            RuneQuestPlugin.getRuneLogger().log(LogLevel.WARN, "Invalid dialog holder is dead/invalid UUID: " + parent.getDialogHolderUUID() + "! In dialog id " + parent.getId() + ". Aborting dialog.");
            RuneQuestPlugin.getDialogHandler().abortDialog(player);
            return;
        }

        Location dialogLoc = getDialogLocationBetween(holderEntity, player.asBukkitPlayer(), 1, 0.25);
        Hologram hologram = DHAPI.createHologram(hologramUUID.toString(), dialogLoc,
                content.stream().map(i -> "").toList());

        hologram.setDefaultVisibleState(false);
        hologram.setShowPlayer(player.asBukkitPlayer());

        processLines(player, hologram);

    }

    private void processLines(RunePlayer player, Hologram hologram) {
        if (this.content.isEmpty() || this.content.getFirst() != null && this.content.getFirst().line().isEmpty()) {
            onDialogFinishRendering(player);
            finishedRendering = true;
            return;
        }

        new BukkitRunnable() {
            int lineIndex = 0;
            int charIndex = 0;
            StringBuilder displayed = new StringBuilder();
            String currentLine = "";
            boolean isTypewriter = false;

            @Override
            public void run() {


                if (parent.isAborted()) {
                    this.cancel();
                    return;
                }

                if (player == null || player.asBukkitPlayer() == null || !player.asBukkitPlayer().isOnline()) {
                    parent.cleanUp();
                    this.cancel();
                    return;
                }

                AbstractMob holder = parent.getDialogHolder();
                if (holder != null) {
                    Entity holderEntity = holder.getEntity();

                    if (holderEntity != null && !holderEntity.isDead() && holderEntity.isValid()) {
                        Location dialogLoc = getDialogLocationBetween(holderEntity, player.asBukkitPlayer(), 1, 0.25);
                        DHAPI.moveHologram(hologram, dialogLoc);
                    }
                }


                if (lineIndex >= content.size()) {
                    onDialogFinishRendering(player);
                    finishedRendering = true;
                    this.cancel();
                    return;
                }

                if (charIndex == 0) {
                    DialogText dialogText = content.get(lineIndex);
                    currentLine = replaceTemplates(dialogText.line(), player);
                    isTypewriter = dialogText.typewriter();
                }

                if (!isTypewriter || player.asBukkitPlayer().isSneaking()) {
                    // Show instantly (skip typing if sneaking or no typewriter){
                    DHAPI.setHologramLine(hologram, lineIndex, currentLine);
                    lineIndex++;
                    charIndex = 0;
                    displayed.setLength(0);
                } else {
                    if (charIndex < currentLine.length()) {
                        // Append up to 2 characters
                        int charsToShow = Math.min(2, currentLine.length() - charIndex);
                        displayed.append(currentLine, charIndex, charIndex + charsToShow);
                        DHAPI.setHologramLine(hologram, lineIndex, displayed.toString());
                        SoundType.DIALOG_TYPEWRITER_SOUND.play(player, false);
                        charIndex += charsToShow;
                    } else {
                        lineIndex++;
                        charIndex = 0;
                        displayed.setLength(0);
                    }
                }
            }
        }.runTaskTimer(RuneQuestPlugin.getInstance(), 0L, 2L);
    }


    public void cleanUp() {
        DHAPI.removeHologram(hologramUUID.toString());
    }

    public abstract void onDialogFinishRendering(RunePlayer player);

    public void handleHotbarSlotChange(PlayerItemHeldEvent event, RunePlayer player) {
        if (finishedRendering) onHotbarSlotChange(event, player, event.getNewSlot());
    }

    public void onHotbarSlotChange(PlayerItemHeldEvent event, RunePlayer player, int newSlot) {

    }
}
