package org.zuma.examplePlugin.core.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.zuma.examplePlugin.domain.player.RunePlayer;
import org.zuma.examplePlugin.domain.skill.SkillRequirement;
import org.zuma.examplePlugin.domain.skill.constants.SkillType;

import java.util.ArrayList;
import java.util.List;

public class LoreUtils {

    /**
     * Builds a fancy lore list of skill requirements for an item.
     *
     * @param requirements List of skill requirements
     * @param player       The player to check if they meet each requirement (optional, can be null)
     * @return List of Components representing the lore lines
     */
    public static List<Component> buildSkillRequirementsLore(List<SkillRequirement> requirements, RunePlayer player) {
        List<Component> lore = new ArrayList<>();

        // Header line - visually striking
        lore.add(Component.text("⛏ Required Skills", NamedTextColor.GOLD, TextDecoration.BOLD));

        for (SkillRequirement req : requirements) {
            SkillType skill = req.getType();
            int neededLevel = req.getMinimumLevel();

            // Check if player meets requirement (if player is null, just show in neutral color)
            boolean meets = player != null && player.getSkill(skill).getLevel() >= neededLevel;

            Component skillLine = Component.text("  ")
                    .append(Component.text("➤ ", NamedTextColor.GRAY))
                    .append(Component.text(skill.getLabel(), NamedTextColor.AQUA))
                    .append(Component.text(": ", NamedTextColor.WHITE))
                    .append(Component.text(neededLevel, meets ? NamedTextColor.GREEN : NamedTextColor.RED));

            lore.add(skillLine);
        }

        return lore;
    }
}
