package org.zuma.examplePlugin.domain.hud;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.constants.Color;
import org.zuma.examplePlugin.core.constants.Icon;
import org.zuma.examplePlugin.core.utils.CurrencyUtils;
import org.zuma.examplePlugin.domain.fairy.FairyStatus;
import org.zuma.examplePlugin.domain.mount.MountStatus;
import org.zuma.examplePlugin.domain.player.RunePlayer;
import org.zuma.examplePlugin.domain.skill.Skill;
import org.zuma.examplePlugin.domain.skill.constants.SkillType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ActionBarManager {
    private static final Component SPACE_DIVIDER = Component.text(" ".repeat(4));
    private static final int FRAME_DURATION = 2;
    private final RunePlayer player;
    private final List<Component> elements;
    private BukkitTask activeTask;
    private boolean tick;
    private FairyStatus fairyStatus;
    private MountStatus mountStatus;

    int bar = 6;

    public ActionBarManager(RunePlayer player) {
        this.player = player;
        this.elements = new ArrayList<>();
        this.activeTask = null;
        this.tick = false;
        this.fairyStatus = FairyStatus.NONE;
        this.mountStatus = MountStatus.NONE;
        startDefaultLoop();
    }

    public void setMountStatus(MountStatus status) {
        this.mountStatus = status;
    }

    public MountStatus getMountStatus() {
        return this.mountStatus;
    }

    public void setFairyStatus(FairyStatus status) {
        this.fairyStatus = status;
    }

    public FairyStatus getFairyStatus() {
        return this.fairyStatus;
    }

    private void queueFrame(Component component, int frames) {
        this.elements.addAll(Collections.nCopies(frames, component));

        if (activeTask != null && activeTask.isCancelled()) {
            activeTask.cancel();
        }
    }

    private Component getDefaultElement() {
        if (player.isDialogOptionChoosing()) {
            return Component.text("Use the number keys (above the letters) to choose an option.", Color.ACCENT.asColor());
        }

        boolean playerHasFairy = !fairyStatus.equals(FairyStatus.NONE);
        boolean playerHasMount = !mountStatus.equals(MountStatus.NONE);
        NamedTextColor fairyIconColor = NamedTextColor.WHITE;
        Component mountIcon = Component.empty();

        long coins = player.getPouchCoins();

        switch (fairyStatus) {
            case EXHAUSTED -> fairyIconColor = NamedTextColor.DARK_GRAY;
            case READY_FOR_UPGRADE -> fairyIconColor = tick ? NamedTextColor.GREEN : NamedTextColor.WHITE;
        }

        switch (mountStatus) {
            case ACTIVE -> mountIcon = Component.text(Icon.MOUNT_BADGE_ACTIVE.get(), NamedTextColor.WHITE);
            case WEARING -> mountIcon = Component.text(Icon.MOUNT_BADGE_WEARING.get(), NamedTextColor.WHITE);

        }

        Component fairyIcon = Component.text(Icon.FAIRY_BADGE.get(), fairyIconColor);

        Component specialAttackBar = new LoadingBarBuilder()
                .totalSteps(100)
                .currentStep(player.getSpecialEnergyPercent())
                .barLength(10)
                .segment(Icon.PROGRESS_TALL_SEGMENT.get())
                .start(Icon.PROGRESS_TALL_START.get())
                .stop(Icon.PROGRESS_TALL_END.get())
                .emptyColor(Color.SPECIAL_BAR_EMPTY.asColor())
                .fillColor(Color.SPECIAL_BAR_FILL.asColor())
                .build();

        return Component.text(
                        Icon.HEART_BIG.get(), NamedTextColor.WHITE)
                .append(Component.text(" " + player.getHealth() + "/" + player.getSkill(SkillType.HITPOINTS).getLevelWithBonuses(), Color.ERROR.asColor()))

                .append(SPACE_DIVIDER)
                .append(Component.text(Icon.PRAYER.get(), NamedTextColor.WHITE))
                .append(Component.text(" " + player.getPrayerEnergy() + "/" + player.getSkill(SkillType.PRAYER).getLevelWithBonuses(), Color.PRAYER_ENERGY.asColor()))

                .append(SPACE_DIVIDER)
                .append(playerHasMount ? mountIcon : Component.empty())
                .append(playerHasFairy ? fairyIcon : Component.empty())
                .append(Component.text((player.canPerformSpecialAttack() ? Icon.SPECIAL_ATTACK_AVAILBLE_BADGE : Icon.SPECIAL_ATTACK_UNAVAILABLE_BADGE).get(), NamedTextColor.WHITE))
                .append(specialAttackBar)

                .append(SPACE_DIVIDER)
                .append(CurrencyUtils.getCoinsCurrencyComponent(coins))
                .append(Component.text("", CurrencyUtils.getCurrencyColor(coins).asColor()))

                ;

    }

    public void showLevelUp(Skill skill, int newRecipesAmount) {
        queueFrame(Component.text(skill.getType().getSymbol() + " ", NamedTextColor.WHITE)
                .append(Component.text(skill.getType().getLabel() + " leveled up to level " + skill.getBaseLevel() + "!", Color.SUCCESS.asColor())), 40);

        if (newRecipesAmount > 0) {
            String text = "Unlocked " + newRecipesAmount + " new crafting recipes!";

            queueTypewriter(text, 1, Color.SUCCESS);
            queueFrame(Component.text(text, Color.SUCCESS.asColor()), 60);
        }

        startAnimationLoop();
    }

    private void queueTypewriter(String text, int frameCountPerChar, Color color) {
        for (int i = 1; i <= text.length(); i++) {
            Component frame = Component.text(text.substring(0, i), color.asColor());
            queueFrame(frame, frameCountPerChar);
        }
    }

    private void startAnimationLoop() {
        if (activeTask != null) {
            activeTask.cancel();
        }

        activeTask = new BukkitRunnable() {

            @Override
            public void run() {
                Player p = player.asBukkitPlayer();

                if (p == null || !p.isOnline()) {
                    this.cancel();
                    activeTask = null;
                    return;
                }

                if (elements.isEmpty()) {
                    this.cancel();
                    activeTask = null;
                    startDefaultLoop();
                } else {
                    p.sendActionBar(elements.getFirst());
                    elements.removeFirst();
                }

                tick = !tick;

            }
        }.runTaskTimer(RuneQuestPlugin.getInstance(), 0L, FRAME_DURATION);

    }

    private void startDefaultLoop() {
        if (activeTask != null) {
            activeTask.cancel();
        }

        activeTask = new BukkitRunnable() {

            @Override
            public void run() {
                Player p = player.asBukkitPlayer();

                if (p == null || !p.isOnline()) {
                    this.cancel();
                    activeTask = null;
                    return;
                }

                p.sendActionBar(getDefaultElement());
                tick = !tick;

            }
        }.runTaskTimer(RuneQuestPlugin.getInstance(), 0L, 10L);

    }

}
