package org.zuma.examplePlugin.domain.player.actions;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.constants.Color;
import org.zuma.examplePlugin.core.constants.Icon;
import org.zuma.examplePlugin.core.interfaces.InteractiveActionStrategy;
import org.zuma.examplePlugin.core.utils.TextUtils;
import org.zuma.examplePlugin.domain.player.RunePlayer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class ReactionBarAction implements InteractiveActionStrategy, Listener {
    private final Random random = new Random();

    public enum SegmentType {
        RED,
        YELLOW,
        GREEN
    }

    private boolean isCancelled, isSuccess;
    private final Listener thisListener = this;
    private int arrowPosition = -1;
    private final List<Integer> successPositions = new ArrayList<>();
    private UUID uuid = null;

    public ReactionBarAction() {
        this.isCancelled = false;
    }


    public InteractiveActionStrategy startAction(RunePlayer player, ActionParams params) {
        RuneQuestPlugin.getInstance().getServer().getPluginManager().registerEvents(this, RuneQuestPlugin.getInstance());

        this.uuid = player.getUUID();

        int redSegments = 6;
        int yellowSegments = 2;
        int greenSegments = random.nextInt((2 - 1) + 1) + 1;
        int totalSegments = redSegments + yellowSegments + greenSegments;
        int stepDurationTicks = random.nextInt((7 - 4) + 1) + 4; // 4..7 inclusive
        int randomOffset = random.nextInt(totalSegments); // 0..totalSegments-1
        final int[] stepsCompleted = {0};

        // compute counts (no offset here)
        int leftRedCount = redSegments / 2;
        int rightRedCount = redSegments - leftRedCount;
        int leftYellowCount = yellowSegments / 2;
        int rightYellowCount = yellowSegments - leftYellowCount;

        // build layout with GREEN centered, and rotate by randomOffset
        final List<SegmentType> layout = new ArrayList<>(totalSegments);
        for (int i = 0; i < leftRedCount; i++) layout.add(SegmentType.RED);
        for (int i = 0; i < leftYellowCount; i++) layout.add(SegmentType.YELLOW);
        for (int i = 0; i < greenSegments; i++) layout.add(SegmentType.GREEN);
        for (int i = 0; i < rightYellowCount; i++) layout.add(SegmentType.YELLOW);
        for (int i = 0; i < rightRedCount; i++) layout.add(SegmentType.RED);

        // safety: if layout size differs (due to integer division), normalize by filling REDs
        while (layout.size() < totalSegments) layout.add(SegmentType.RED);
        if (layout.size() > totalSegments) {
            while (layout.size() > totalSegments) layout.removeLast();
        }

        Collections.rotate(layout, randomOffset);

        // compute success positions from rotated layout
        successPositions.clear();
        for (int i = 0; i < layout.size(); i++) {
            if (layout.get(i) == SegmentType.GREEN) successPositions.add(i);
        }

        new BukkitRunnable() {
            @Override
            public void run() {
                Player onlinePlayer = Bukkit.getPlayer(uuid);

                if (onlinePlayer == null || !onlinePlayer.isOnline() || player.asBukkitPlayer() == null) {
                    HandlerList.unregisterAll(thisListener);
                    this.cancel();
                    return;
                }
                try {
                    if (isCancelled) {
                        this.cancel();

                        if (params.getOnFailure() != null) {
                            params.getOnFailure().run();
                        }

                        if (params.getOnFinally() != null) {
                            params.getOnFinally().run();
                        }

                        HandlerList.unregisterAll(thisListener);
                        player.cancelActionTask(false);
                        return;
                    }

                    arrowPosition += 1;
                    if (arrowPosition >= totalSegments) {
                        arrowPosition = 0;
                    }
                    stepsCompleted[0] += 1;

                    Component baseBar = Component.empty();

                    for (int i = 0; i < totalSegments; i++) {
                        SegmentType segmentType = layout.get(i);

                        Component segmentComponent = (i == arrowPosition)
                                ? switch (segmentType) {
                            case RED -> Icon.REACTION_BAR_SEL_RED.asComponent();
                            case YELLOW -> Icon.REACTION_BAR_SEL_YELLOW.asComponent();
                            case GREEN -> Icon.REACTION_BAR_SEL_GREEN.asComponent();
                        }
                                : switch (segmentType) {
                            case RED -> Icon.REACTION_BAR_UNSEL_RED.asComponent();
                            case YELLOW -> Icon.REACTION_BAR_UNSEL_YELLOW.asComponent();
                            case GREEN -> Icon.REACTION_BAR_UNSEL_GREEN.asComponent();
                        };

                        baseBar = baseBar.append(segmentComponent);
                    }

                    onlinePlayer.showTitle(Title.title(
                            Component.text(successPositions.contains(arrowPosition) ? ">> CLICK NOW! <<<" : ">>> WAIT... <<<").color(Color.INFO.asColor()),
                            baseBar,
                            TextUtils.getGenericDuration()
                    ));

                    if (params.getStepSound() != null && stepsCompleted[0] % params.getStepSoundEveryNth() == 0) {
                        float randomPitch = -0.3f + random.nextFloat() * 0.6f;
                        params.getStepSound().play(onlinePlayer.getLocation(), null, randomPitch);
                    }


                    if (isSuccess) {
                        player.cancelActionTask(false);
                        this.cancel();
                        HandlerList.unregisterAll(thisListener);

                        if (params.getSuccessSound() != null) params.getSuccessSound().play(onlinePlayer, true);
                        onlinePlayer.showTitle(Title.title(
                                Component.empty(),
                                Component.text(params.getSuccessActionLabel()).color(Color.SUCCESS.asColor()),
                                TextUtils.getGenericDuration()
                        ));

                        params.getOnSuccess().run();

                        if (params.getOnFinally() != null) {
                            params.getOnFinally().run();
                        }
                    }

                } catch (Exception e) {
                    if (player.asBukkitPlayer() != null) {
                        player.cancelActionTask(true);
                    }

                    this.cancel();
                }
            }
        }.runTaskTimer(RuneQuestPlugin.getInstance(), 0L, stepDurationTicks);

        return this;
    }


    @Override
    public void cancelTask() {
        isCancelled = true;
        HandlerList.unregisterAll(this);
    }

    @EventHandler
    public void onPlayerLeftClick(PlayerInteractEvent event) {
        if (!event.getPlayer().getUniqueId().equals(this.uuid)) return;
        RunePlayer runePlayer = RuneQuestPlugin.getPlayerManager().getRunePlayer(this.uuid);
        if (runePlayer == null) return;

        if (event.getAction().toString().contains("LEFT_CLICK")) {
            if (successPositions.contains(arrowPosition)) {
                this.isSuccess = true;
            } else {
                cancelTask();
            }
        }

    }

}
