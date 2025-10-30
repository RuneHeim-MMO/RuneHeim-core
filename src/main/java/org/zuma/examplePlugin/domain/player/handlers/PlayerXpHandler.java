package org.zuma.examplePlugin.domain.player.handlers;

import org.bukkit.Bukkit;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.constants.XpSource;
import org.zuma.examplePlugin.core.logger.constants.LogLevel;
import org.zuma.examplePlugin.core.utils.XpUtils;
import org.zuma.examplePlugin.domain.hud.HudService;
import org.zuma.examplePlugin.domain.effect.EffectHandler;
import org.zuma.examplePlugin.domain.effect.effects.LevelUpEffect;
import org.zuma.examplePlugin.domain.modifier.constants.ModifierType;
import org.zuma.examplePlugin.domain.player.RunePlayer;
import org.zuma.examplePlugin.domain.skill.Skill;
import org.zuma.examplePlugin.domain.skill.constants.SkillType;
import org.zuma.examplePlugin.events.player.RunePlayerGainedXPEvent;
import org.zuma.examplePlugin.events.player.RunePlayerLevelUpEvent;

import javax.annotation.Nullable;
import java.util.Arrays;

public class PlayerXpHandler {
    private final RunePlayer player;

    private final java.util.Set<SkillType> combatSkills = java.util.EnumSet.of(
            SkillType.ATTACK, SkillType.STRENGTH, SkillType.DEFENCE, SkillType.RANGED, SkillType.HITPOINTS);

    private final java.util.Set<SkillType> skillingSkills = java.util.EnumSet.of(
            SkillType.WOODCUTTING, SkillType.FISHING, SkillType.COOKING, SkillType.MINING,
            SkillType.SMITHING, SkillType.FIREMAKING, SkillType.CRAFTING, SkillType.FLETCHING, SkillType.PRAYER);


    @Nullable
    private SkillType lastXPGainedSkillType;

    public PlayerXpHandler(RunePlayer player) {
        this.player = player;
        this.lastXPGainedSkillType = null;
    }

    public void addXp(SkillType type, double xp, XpSource source) {
        Skill skill = player.getSkills().get(type);

        // Apply modifiers only if not quest XP
        if (source != XpSource.QUEST) {
            double combined = 1.0;

            if (player.getModifiers().containsKey(ModifierType.XP_BOOST)) {
                double multiplier = player.getModifiers().get(ModifierType.XP_BOOST).amount();
                combined *= 1.0 + multiplier;
            }

            if (player.getModifiers().containsKey(ModifierType.COMBAT_XP_BOOST) && combatSkills.contains(type)) {
                double multiplier = player.getModifiers().get(ModifierType.COMBAT_XP_BOOST).amount();
                combined *= 1.0 + multiplier;
            }

            if (player.getModifiers().containsKey(ModifierType.SKILLING_XP_BOOST) && skillingSkills.contains(type)) {
                double multiplier = player.getModifiers().get(ModifierType.SKILLING_XP_BOOST).amount();
                combined *= 1.0 + multiplier;
            }

            xp *= combined;
        }

        RunePlayerGainedXPEvent event = new RunePlayerGainedXPEvent(player, type, xp, source);
        Bukkit.getServer().getPluginManager().callEvent(event);

        if (event.isCancelled()) {
            return;
        }

        if (skill == null) {
            RuneQuestPlugin.getRuneLogger().log(LogLevel.WARN, "Tried adding XP for skill " + type.getLabel() + " which does not exist in players instance.");
            return;
        }

        if (source == XpSource.PERSONAL) {
            lastXPGainedSkillType = type;
        }

        double nextXp = skill.getXp() + xp;
        boolean leveledUp = false;
        int availableRecipesBeforeLevelUp = 0;

        if (XpUtils.isMaxLevel(skill.getBaseLevel())) {
            return;
        }

        if (nextXp >= XpUtils.getXpForLevel(skill.getBaseLevel() + 1)) {
            availableRecipesBeforeLevelUp = RuneQuestPlugin.getRecipeHandler().getAvailableRecipesNumber(player);
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
            int availableRecipesAfterLevelUp = RuneQuestPlugin.getRecipeHandler().getAvailableRecipesNumber(player);

            EffectHandler.playAt(() -> player.asBukkitPlayer().getLocation(), new LevelUpEffect(player.asBukkitPlayer(), skill));
            player.setCombatLvl(XpUtils.getCombatLevelFromSkills(player.getSkills()));
            player.getActionBar().showLevelUp(skill, availableRecipesAfterLevelUp - availableRecipesBeforeLevelUp);
        }

        HudService.updateVanillaGUI(player);

    }

    public void distributeXp(SkillType type, double xp) {
        double roundedXP = Math.round(xp * 100.0) / 100.0;

        if (player.getParty() != null) {
            player.getParty().splitXPBetweenMembers(this.player, type, roundedXP);
        } else {
            addXp(type, roundedXP, XpSource.PERSONAL);
        }
    }

    @Nullable
    public SkillType getLastXPGainedSkillType() {
        return this.lastXPGainedSkillType;
    }


}
