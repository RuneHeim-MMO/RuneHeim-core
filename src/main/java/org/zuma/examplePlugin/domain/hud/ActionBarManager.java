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
import org.zuma.examplePlugin.domain.player.RunePlayer;
import org.zuma.examplePlugin.domain.skill.Skill;
import org.zuma.examplePlugin.domain.skill.constants.SkillType;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ActionBarManager {
    private static final int FRAME_DURATION = 2;
    private final RunePlayer player;
    private final List<Component> elements;
    private BukkitTask activeTask;
    private boolean tick;
    private FairyStatus fairyStatus;

    int bar = 6;

    public ActionBarManager(RunePlayer player) {
        this.player = player;
        this.elements = new ArrayList<>();
        this.activeTask = null;
        this.tick = false;
        this.fairyStatus = FairyStatus.NONE;
        startDefaultLoop();
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
        NamedTextColor fairyIconColor = NamedTextColor.WHITE;
        boolean playerHasFairy = !fairyStatus.equals(FairyStatus.NONE);
        long coins = player.getPouchCoins();

        switch (fairyStatus) {
            case EXHAUSTED -> fairyIconColor = NamedTextColor.DARK_GRAY;
            case READY_FOR_UPGRADE -> fairyIconColor = tick ? NamedTextColor.GREEN : NamedTextColor.WHITE;

        }

        Component fairyIcon = Component.text(" " + Icon.FAIRY.get() + " ", fairyIconColor);


        if (bar >= 10) {
            bar = 0;
        }

        Component specialAttackBar = new LoadingBarBuilder()
                .totalSteps(10)
                .currentStep(bar)
                .barLength(8)
                .segment(Icon.PROGRESS_TALL_SEGMENT.get())
                .start(Icon.PROGRESS_GEM_TALL_START.get())
                .stop(Icon.PROGRESS_TALL_END.get())
                .emptyColor(Color.SPECIAL_BAR_EMPTY.asColor())
                .fillColor(Color.SPECIAL_BAR_FILL.asColor())
                .build();

        Component manaBar = new LoadingBarBuilder()
                .totalSteps(10)
                .currentStep(bar)
                .barLength(8)
                .segment(Icon.PROGRESS_TALL_SEGMENT.get())
                .start(Icon.PROGRESS_TALL_START.get())
                .stop(Icon.PROGRESS_SKULL_TALL_END.get())
                .emptyColor(Color.MANA_BAR_EMPTY.asColor())
                .fillColor(Color.MANA_BAR_FILL.asColor())
                .inverted(true)
                .build();

        return Component.text(Icon.HEART_BIG.get(), NamedTextColor.WHITE)
                .append(Component.text(" " + player.getHealth() + "/" + player.getSkill(SkillType.HITPOINTS).getLevelWithBonuses() + " ".repeat(2), Color.ERROR.asColor()))
                .append(specialAttackBar)
                .append(playerHasFairy ? fairyIcon : Component.empty())
                .append(manaBar)
                .append(Component.text(" ".repeat(2)))
                .append(CurrencyUtils.getCurrencyComponent(coins))
                .append(Component.text("", CurrencyUtils.getCurrencyColor(coins).asColor()));

    }

    public void showLevelUp(Skill skill, int newRecipesAmount) {
        queueFrame(Component.text(skill.getType().getSymbol() + " ", NamedTextColor.WHITE)
                .append(Component.text(skill.getType().getLabel() + " level up!", Color.SUCCESS.asColor())), 40);

        player.asBukkitPlayer().sendMessage("Level up, new recipes amount: " + newRecipesAmount);

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
