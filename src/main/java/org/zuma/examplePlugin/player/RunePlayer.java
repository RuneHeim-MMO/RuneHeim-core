package org.zuma.examplePlugin.player;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.constants.Colors;
import org.zuma.examplePlugin.constants.SoundType;
import org.zuma.examplePlugin.constants.XpSource;
import org.zuma.examplePlugin.logger.constants.LogLevel;
import org.zuma.examplePlugin.modifier.Modifier;
import org.zuma.examplePlugin.player.events.RunePlayerGainedXPEvent;
import org.zuma.examplePlugin.skill.Skill;
import org.zuma.examplePlugin.skill.constants.SkillType;
import org.zuma.examplePlugin.utils.TextUtils;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

public class RunePlayer {
    private final UUID uuid;
    private final List<Modifier> gearModifiers = new ArrayList<>();
    private final EnumMap<SkillType, Skill> skills = new EnumMap<>(SkillType.class);
    private boolean isBusy;

    public RunePlayer(UUID uuid) {
        this.uuid = uuid;
        this.isBusy = false;

        // Each player starts with default XP in all skill types.
        for (SkillType type : SkillType.values()) {
            skills.put(type, new Skill(type, type.getDefaultXp()));
        }
    }

    public UUID getUUID() {
        return uuid;
    }

    public Player getPlayer() {
        return Bukkit.getPlayer(uuid);
    }

    public void setGearModifiers(List<Modifier> newModifiers) {
        gearModifiers.clear();
        gearModifiers.addAll(newModifiers);

        System.out.println("Recalculated modifiers " + newModifiers);
    }

    public List<Modifier> getActiveModifiers() {
        return List.copyOf(gearModifiers);
    }

    protected void setSkill(SkillType type, Skill skill) {
        skills.put(type, skill);
    }

    @NotNull
    public Skill getSkill(SkillType type) {
        return skills.get(type);
    }

    @NotNull
    public Collection<Skill> getSkills() {
        return skills.values();
    }


    public void addSkillXp(SkillType type, int xp, XpSource source) {
        Skill skill = skills.get(type);

        RunePlayerGainedXPEvent event = new RunePlayerGainedXPEvent(this, type, xp, source);
        Bukkit.getServer().getPluginManager().callEvent(event);

        if (event.isCancelled()) {
            return;
        }

        if (skill == null) {
            RuneQuestPlugin.getRuneLogger().log(LogLevel.WARN, "Tried adding XP for skill " + type.getLabel() + " which does not exist in players instance.");
            return;
        }

        skill.addXp(xp);

    }

    public void useBusyState(int durationMs, String action, String successAction, SoundType stepSound, Consumer<Void> consumer) {
        UUID uuid = this.getUUID();
        int steps = 10;
        long stepDuration = Math.max(1L, (durationMs / 50L) / steps);
        AtomicInteger currentSteps = new AtomicInteger();

        if (isBusy) {
            showFailTitle("You are already busy with something else!");
            return;
        }

        isBusy = true;

        new BukkitRunnable() {
            @Override
            public void run() {
                Player onlinePlayer = Bukkit.getPlayer(uuid);


                if (onlinePlayer == null || !onlinePlayer.isOnline()) {
                    isBusy = false;
                    this.cancel();
                    return;
                }

                if (!isBusy) {
                    isBusy = false;
                    showFailTitle("Task cancelled.");
                    this.cancel();
                    return;
                }


                if (currentSteps.incrementAndGet() >= steps) {
                    isBusy = false;
                    this.cancel();
                    consumer.accept(null);

                    SoundType.SUCCESS_ACTION.play(onlinePlayer);
                    onlinePlayer.showTitle(Title.title(
                            Component.text(""),
                            Component.text(successAction).color(Colors.SUCCESS.asColor()),
                            TextUtils.getGenericDuration()
                    ));
                }

                // Progress bar
                int secondsLeft = (int) Math.ceil(((durationMs / (double) steps) * (steps - currentSteps.get())) / 1000.0);

                stepSound.play(onlinePlayer);
                onlinePlayer.showTitle(Title.title(
                        TextUtils.buildLoadingBar(steps, currentSteps.get()),
                        Component.text("[" + secondsLeft + "s] ").color(Colors.SUCCESS.asColor())
                                .append(Component.text(action).color(Colors.SUBTITLE.asColor())),
                        TextUtils.getGenericDuration()
                ));


            }
        }.runTaskTimer(RuneQuestPlugin.getInstance(), 0L, stepDuration);
    }

    public void showFailTitle(String text) {
        this.getPlayer().showTitle(Title.title(
                Component.text(""),
                Component.text(text).color(Colors.ERROR.asColor()),
                TextUtils.getGenericDuration()
        ));
        SoundType.FAIL_ACTION.play(this.getPlayer());
    }

    public boolean isBusy() {
        return isBusy;
    }

    public void cancelTask() {
        isBusy = false;
    }
}
