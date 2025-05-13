package org.zuma.examplePlugin.player;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.zuma.examplePlugin.constants.SkillType;
import org.zuma.examplePlugin.modifier.Modifier;
import org.zuma.examplePlugin.skill.Skill;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.UUID;

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

    public void setSkill(SkillType type, Skill skill) {
        skills.put(type, skill);
    }

    @NotNull
    public Skill getSkill(SkillType type) {
        return skills.get(type);
    }

}
