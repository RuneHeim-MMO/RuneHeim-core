package org.zuma.examplePlugin.domain.player.handlers;

import org.bukkit.Bukkit;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.constants.XpSource;
import org.zuma.examplePlugin.core.logger.constants.LogLevel;
import org.zuma.examplePlugin.core.utils.XpUtils;
import org.zuma.examplePlugin.domain.hud.HudService;
import org.zuma.examplePlugin.domain.player.RunePlayer;
import org.zuma.examplePlugin.domain.skill.Skill;
import org.zuma.examplePlugin.domain.skill.constants.SkillType;
import org.zuma.examplePlugin.events.player.RunePlayerGainedXPEvent;
import org.zuma.examplePlugin.events.player.RunePlayerLevelUpEvent;

public class PlayerXpHandler {
    private final RunePlayer player;

    public PlayerXpHandler(RunePlayer player) {
        this.player = player;
    }

    public void addXp(SkillType type, double xp, XpSource source) {
        Skill skill = player.getSkills().get(type);

        RunePlayerGainedXPEvent event = new RunePlayerGainedXPEvent(player, type, xp, source);
        Bukkit.getServer().getPluginManager().callEvent(event);

        if (event.isCancelled()) {
            return;
        }

        if (skill == null) {
            RuneQuestPlugin.getRuneLogger().log(LogLevel.WARN, "Tried adding XP for skill " + type.getLabel() + " which does not exist in players instance.");
            return;
        }

        double nextXp = skill.getXp() + xp;
        boolean leveledUp = false;

        if (XpUtils.isMaxLevel(skill.getBaseLevel())) {
            return;
        }

        if (nextXp >= XpUtils.getXpForLevel(skill.getBaseLevel() + 1)) {
            leveledUp = true;

            RunePlayerLevelUpEvent levelUpEvent = new RunePlayerLevelUpEvent(player, type, XpUtils.getCappedLevel(skill.getBaseLevel() + 1), source);
            Bukkit.getServer().getPluginManager().callEvent(levelUpEvent);

            if (levelUpEvent.isCancelled()) {
                return;
            }
        }

        HudService.showXpGainMessage(player, type, xp, source);
        skill.addXp(xp);

        if (leveledUp) {
            HudService.showPlayerLevelUpTitle(player, skill);
            player.setCombatLvl(XpUtils.getCombatLevelFromSkills(player.getSkills()));
            HudService.updateVanillaGUI(player);
            player.getActionBar().showLevelUp();
        }

    }

    public void distributeXp(SkillType type, double xp) {
        if (player.getParty() != null) {
            player.getParty().splitXPBetweenMembers(type, xp);
        } else {
            addXp(type, xp, XpSource.PERSONAL);
        }
    }


}
