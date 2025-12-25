package org.zuma.examplePlugin.domain.dialog.base;

import eu.decentsoftware.holograms.api.DHAPI;
import eu.decentsoftware.holograms.api.holograms.Hologram;
import eu.decentsoftware.holograms.api.holograms.HologramLine;
import eu.decentsoftware.holograms.api.holograms.HologramPage;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.constants.Icon;
import org.zuma.examplePlugin.core.constants.SoundType;
import org.zuma.examplePlugin.core.logger.constants.LogLevel;
import org.zuma.examplePlugin.domain.dialog.Dialog;
import org.zuma.examplePlugin.domain.dialog.DialogText;
import org.zuma.examplePlugin.domain.dialog.impl.InteractiveDialogStep;
import org.zuma.examplePlugin.domain.mob.base.AbstractMob;
import org.zuma.examplePlugin.domain.player.RunePlayer;

import java.util.List;
import java.util.UUID;
import java.util.function.Function;

public abstract class DialogStep {
    private static final Logger log = LoggerFactory.getLogger(DialogStep.class);
    private final String uniqueIdentifier;
    private final List<DialogText> content;
    private final UUID hologramUUID = UUID.randomUUID();
    protected Dialog parent;
    private boolean finishedRendering = false;

    public DialogStep(List<DialogText> content, String uniqueIdentifier) {
        this.content = content;
        this.uniqueIdentifier = uniqueIdentifier;
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
        boolean isChatMode = parent.isChatMode();

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


        if (isChatMode) {
            processChatLines(player);
            return;
        }


        Location dialogLoc = getDialogLocationBetween(holderEntity, player.asBukkitPlayer(), 1, 0.25);
        Hologram hologram = DHAPI.createHologram(hologramUUID.toString(), dialogLoc,
                content.stream().map(i -> "").toList());

        hologram.setDefaultVisibleState(false);
        hologram.setShowPlayer(player.asBukkitPlayer());

        processHologramLines(player, hologram);

    }

    private void processChatLines(RunePlayer player) {
        if (this.content.isEmpty() || this.content.getFirst() != null && this.content.getFirst().line().isEmpty()) {
            onDialogFinishRendering(player);
            finishedRendering = true;
            return;
        }

        String opponentName;
        boolean isInteractive = this instanceof InteractiveDialogStep;
        AbstractMob holder = parent.getDialogHolder();
        if (holder != null) {
            opponentName = holder.getDisplayName();
        } else {
            opponentName = "";
        }

        Component prefix = isInteractive ? Component.text("    ") : Component.text("   " + opponentName).append(Component.text(" » ", TextColor.color(150, 150, 150)));

        // Use a dynamic approach similar to hologram processing
        new BukkitRunnable() {
            int messageIndex = 0;
            boolean hasStarted = false;
            final boolean[] messagesSent = new boolean[content.size()];
            final long startTime = System.currentTimeMillis();

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

                // Check if player is sneaking for instant display
                boolean skipTypewriter = player.asBukkitPlayer().isSneaking();

                if (skipTypewriter && !hasStarted) {
                    // Show all messages instantly on first sneak detection
                    if (isInteractive) {
                        player.asBukkitPlayer().sendMessage(Icon.DIALOG_BORDER.asComponent().append(Component.empty()));
                    }

                    for (DialogText text : content) {
                        String contentLine = replaceTemplates(text.line(), player);
                        Component message = Icon.DIALOG_BORDER.asComponent().append(prefix.append(Component.text(contentLine)));
                        player.asBukkitPlayer().sendMessage(message);
                    }

                    if (isInteractive) {
                        player.asBukkitPlayer().sendMessage(Icon.DIALOG_BORDER.asComponent().append(Component.empty()));
                    }

                    onDialogFinishRendering(player);
                    finishedRendering = true;
                    this.cancel();
                    return;
                }

                if (skipTypewriter && hasStarted) {
                    // Show remaining messages instantly if sneaking mid-animation
                    for (int i = messageIndex; i < content.size(); i++) {
                        if (!messagesSent[i]) {
                            DialogText text = content.get(i);
                            String contentLine = replaceTemplates(text.line(), player);
                            Component message = Icon.DIALOG_BORDER.asComponent().append(prefix.append(Component.text(contentLine)));

                            if (isInteractive && !messagesSent[0]) {
                                player.asBukkitPlayer().sendMessage(Icon.DIALOG_BORDER.asComponent().append(Component.empty()));
                            }

                            player.asBukkitPlayer().sendMessage(message);
                            messagesSent[i] = true;
                        }
                    }

                    if (isInteractive && messageIndex < content.size()) {
                        player.asBukkitPlayer().sendMessage(Icon.DIALOG_BORDER.asComponent().append(Component.empty()));
                    }

                    onDialogFinishRendering(player);
                    finishedRendering = true;
                    this.cancel();
                    return;
                }

                // Normal typewriter logic
                hasStarted = true;
                long currentTime = System.currentTimeMillis();
                long elapsed = currentTime - startTime;

                int combinedDelay = 0;
                for (int i = 0; i < content.size(); i++) {
                    DialogText text = content.get(i);
                    String contentLine = replaceTemplates(text.line(), player);

                    long messageDelay = combinedDelay * 50L; // Convert ticks to milliseconds (20 ticks = 1 second)

                    if (elapsed >= messageDelay && !messagesSent[i]) {
                        Component message = Icon.DIALOG_BORDER.asComponent().append(prefix.append(Component.text(contentLine)));

                        if (isInteractive && (text.typewriter() || i == 0)) {
                            player.asBukkitPlayer().sendMessage(Icon.DIALOG_BORDER.asComponent().append(Component.empty()));
                        }

                        player.asBukkitPlayer().sendMessage(message);
                        messagesSent[i] = true;
                        messageIndex = i + 1;

                        if (isInteractive && i == content.size() - 1) {
                            player.asBukkitPlayer().sendMessage(Icon.DIALOG_BORDER.asComponent().append(Component.empty()));
                        }
                    }

                    if (text.typewriter()) {
                        long extra = 2L * contentLine.length();
                        combinedDelay += (int) extra;
                    } else {
                        combinedDelay += 1;
                    }
                }

                // Check if all messages have been sent
                if (messageIndex >= content.size()) {
                    // Wait for typewriter sounds to finish
                    long totalDuration = content.stream()
                            .mapToLong(s -> s.typewriter() ? s.line().length() * 2L : 0L)
                            .sum() * 50L + 500L; // Convert to milliseconds and add buffer

                    if (elapsed >= totalDuration) {
                        onDialogFinishRendering(player);
                        finishedRendering = true;
                        this.cancel();
                    }
                }
            }
        }.runTaskTimer(RuneQuestPlugin.getInstance(), 0L, 1L);

        // Typewriter sound effect
        final int[] letters = {content.stream()
                .map(s -> s.typewriter() ? s.line().length() : 0)
                .mapToInt(Integer::intValue)
                .sum()};

        new BukkitRunnable() {
            @Override
            public void run() {
                if (parent.isAborted() || finishedRendering) {
                    this.cancel();
                    return;
                }

                if (player == null || player.asBukkitPlayer() == null || !player.asBukkitPlayer().isOnline()) {
                    parent.cleanUp();
                    this.cancel();
                    return;
                }

                // Stop sound if player is sneaking
                if (player.asBukkitPlayer().isSneaking()) {
                    this.cancel();
                    return;
                }

                if (letters[0] <= 0) {
                    this.cancel();
                    return;
                }

                SoundType.DIALOG_TYPEWRITER_SOUND.play(player, false);
                letters[0]--;
            }
        }.runTaskTimer(RuneQuestPlugin.getInstance(), 0L, 2L);
    }


    private void processHologramLines(RunePlayer player, Hologram hologram) {
        if (this.content.isEmpty() || this.content.getFirst() != null && this.content.getFirst().line().isEmpty()) {
            onDialogFinishRendering(player);
            finishedRendering = true;
            return;
        }

        new BukkitRunnable() {
            final StringBuilder displayed = new StringBuilder();
            int lineIndex = 0;
            int charIndex = 0;
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

    protected void replaceDialogLine(int lineIndex, Function<String, String> newLineFunction) {
        Hologram hologram = DHAPI.getHologram(hologramUUID.toString());

        if (hologram != null) {
            HologramPage page = DHAPI.getHologramPage(hologram, 0);
            HologramLine line = page != null ? DHAPI.getHologramLine(page, lineIndex) : null;
            String previousLine = line != null ? line.getContent() : "";

            DHAPI.setHologramLine(hologram, lineIndex, newLineFunction.apply(previousLine));
        }

    }

    public abstract void onDialogFinishRendering(RunePlayer player);

    public void handleHotbarSlotChange(PlayerItemHeldEvent event, RunePlayer player) {
        if (finishedRendering) onHotbarSlotChange(event, player, event.getNewSlot());
    }

    public void onHotbarSlotChange(PlayerItemHeldEvent event, RunePlayer player, int newSlot) {

    }

    public String getUniqueIdentifier() {
        return uniqueIdentifier;
    }
}
