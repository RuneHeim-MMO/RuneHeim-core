package org.zuma.examplePlugin.player;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.constants.XpSource;
import org.zuma.examplePlugin.logger.constants.LogLevel;
import org.zuma.examplePlugin.modifier.Modifier;
import org.zuma.examplePlugin.player.events.RunePlayerGainedXPEvent;
import org.zuma.examplePlugin.skill.Skill;
import org.zuma.examplePlugin.skill.constants.SkillType;

import java.util.*;

public class RunePlayer {
    private final UUID uuid;
    private final List<Modifier> gearModifiers = new ArrayList<>();
    private final EnumMap<SkillType, Skill> skills = new EnumMap<>(SkillType.class);

    public RunePlayer(UUID uuid) {
        this.uuid = uuid;

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

}
