package org.zuma.examplePlugin.domain.player.handlers;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.analytics.AnalyticsKey;
import org.zuma.examplePlugin.core.constants.Color;
import org.zuma.examplePlugin.core.constants.XpSource;
import org.zuma.examplePlugin.core.logger.constants.LogLevel;
import org.zuma.examplePlugin.core.utils.AnalyticsUtils;
import org.zuma.examplePlugin.core.utils.XpUtils;
import org.zuma.examplePlugin.domain.hud.HudService;
import org.zuma.examplePlugin.domain.effect.EffectHandler;
import org.zuma.examplePlugin.domain.effect.effects.LevelUpEffect;
import org.zuma.examplePlugin.domain.modifier.constants.ModifierType;
import org.zuma.examplePlugin.domain.modifier.scopes.server.ServerModifierType;
import org.zuma.examplePlugin.domain.player.RunePlayer;
import org.zuma.examplePlugin.domain.skill.Skill;
import org.zuma.examplePlugin.domain.skill.constants.SkillType;
import org.zuma.examplePlugin.events.player.RunePlayerGainedXPEvent;
import org.zuma.examplePlugin.events.player.RunePlayerLevelUpEvent;

import javax.annotation.Nullable;
import java.util.EnumMap;
import java.util.Map;

public class PlayerXpHandler {
    private final RunePlayer player;

    private final java.util.Set<SkillType> combatSkills = java.util.EnumSet.of(
            SkillType.ATTACK, SkillType.STRENGTH, SkillType.DEFENCE, SkillType.RANGED, SkillType.HITPOINTS);

    private final java.util.Set<SkillType> skillingSkills = java.util.EnumSet.of(
            SkillType.WOODCUTTING, SkillType.FISHING, SkillType.COOKING, SkillType.MINING,
            SkillType.SMITHING, SkillType.FIREMAKING, SkillType.CRAFTING, SkillType.FLETCHING, SkillType.PRAYER);

    private final java.util.Set<SkillType> skillsToBeCombined = java.util.EnumSet.of(
            SkillType.ATTACK, SkillType.STRENGTH, SkillType.DEFENCE);

    public record DeferredXP(double xp, double multiplier) {
    }

    @Nullable
    private SkillType lastXPGainedSkillType;
    private final EnumMap<SkillType, DeferredXP> deferredXPHolograms = new EnumMap<>(SkillType.class);
    private BukkitTask debouncedXPBubbleTask;


    public PlayerXpHandler(RunePlayer player) {
        this.player = player;
        this.lastXPGainedSkillType = null;
    }

    private void scheduleXPBubble(SkillType type, double xp, XpSource source, double multiplier) {
        final double EPSILON = 1e-6;

        // Merge the XP + multiplier into the map
        deferredXPHolograms.merge(
                type,
                new DeferredXP(xp, multiplier),
                (oldVal, newVal) -> new DeferredXP(
                        oldVal.xp() + newVal.xp(),
                        Math.min(3.0, oldVal.multiplier() * newVal.multiplier())
                )
        );

        // Reset the debounce timer
        if (debouncedXPBubbleTask != null) {
            debouncedXPBubbleTask.cancel();
        }

        debouncedXPBubbleTask = Bukkit.getScheduler().runTaskLater(RuneQuestPlugin.getInstance(), () -> {
            if (player.asBukkitPlayer() == null) return;

            boolean shouldCombine = true;
            Double firstValue = null;

            // Check if all skillsToBeCombined have equal XP
            for (SkillType skill : skillsToBeCombined) {
                double value = deferredXPHolograms.getOrDefault(skill, new DeferredXP(0.0, 1.0)).xp();
                if (firstValue == null) {
                    firstValue = value;
                } else if (Math.abs(firstValue - value) > EPSILON) {
                    shouldCombine = false;
                    break;
                }
            }

            if (!shouldCombine) {
                // Show each hologram independently
                for (Map.Entry<SkillType, DeferredXP> entry : deferredXPHolograms.entrySet()) {
                    final SkillType skillType = entry.getKey();
                    final DeferredXP pack = entry.getValue();

                    Bukkit.getScheduler().runTaskLater(RuneQuestPlugin.getInstance(), () -> {
                        if (player.asBukkitPlayer() == null) return;
                        HudService.showXpGainMessage(player, skillType, pack.xp(), source, pack.multiplier());
                    }, skillType.ordinal() * 5L);
                }

            } else {
                // Show non-combined skills normally
                deferredXPHolograms.forEach((skillType, pack) -> {
                    if (skillsToBeCombined.contains(skillType)) return;

                    Bukkit.getScheduler().runTaskLater(RuneQuestPlugin.getInstance(), () -> {
                        if (player.asBukkitPlayer() == null) return;
                        HudService.showXpGainMessage(player, skillType, pack.xp(), source, pack.multiplier());
                    }, skillType.ordinal() * 5L);
                });

                // Combine combat-type skills
                double firstXP = firstValue != null ? firstValue : 0.0;

                if (firstXP != 0.0) {
                    // Assuming all combined skills use the same multiplier; grab one.
                    double combinedMultiplier = deferredXPHolograms
                            .get(skillsToBeCombined.iterator().next())
                            .multiplier();

                    HudService.showCombinedCombatXPGainMessage(player, firstXP, source, combinedMultiplier);
                }
            }

            deferredXPHolograms.clear();

        }, 40L);
    }


    @Nullable
    private ServerModifierType getServerModifierForSkill(SkillType type) {
        @Nullable ServerModifierType bombModifier = null;

        if (skillingSkills.contains(type)) {
            bombModifier = ServerModifierType.DOUBLE_SKILL_XP;
        } else if (combatSkills.contains(type)) {
            bombModifier = ServerModifierType.DOUBLE_COMBAT_XP;
        }

        return bombModifier;
    }

    public void addXp(SkillType type, double xp, XpSource source) {
        Skill skill = player.getSkills().get(type);

        if (skill == null) {
            RuneQuestPlugin.getRuneLogger().log(LogLevel.WARN, "Tried adding XP for skill " + type.getLabel() + " which does not exist in players instance.");
            return;
        }

        if (skill.getType().getMaxLevelCap() != null && (skill.getBaseLevel() >= skill.getType().getMaxLevelCap())) {
            return;
        }

        double baseMultiplier = 1.0;      // server-wide stuff
        double bonusAdditive = 0.0;       // player boosts that stack additively

        // Server bomb
        @Nullable ServerModifierType bombModifier = getServerModifierForSkill(type);
        if (bombModifier != null && RuneQuestPlugin.getServerModifierHandler().isModifierActive(bombModifier)) {
            baseMultiplier *= 2.0;
        }

        // Player modifiers (additive, not multiplicative)
        if (source != XpSource.QUEST) {
            if (player.getModifiers().containsKey(ModifierType.XP_BONUS_ADDITIVE)) {
                double mult = player.getModifiers().get(ModifierType.XP_BONUS_ADDITIVE).amount();
                bonusAdditive += mult;
            }

            if (player.getModifiers().containsKey(ModifierType.COMBAT_XP_BONUS_ADDITIVE) && combatSkills.contains(type)) {
                double mult = player.getModifiers().get(ModifierType.COMBAT_XP_BONUS_ADDITIVE).amount();
                bonusAdditive += mult;
            }

            if (player.getModifiers().containsKey(ModifierType.SKILLING_XP_BONUS_ADDITIVE) && skillingSkills.contains(type)) {
                double mult = player.getModifiers().get(ModifierType.SKILLING_XP_BONUS_ADDITIVE).amount();
                bonusAdditive += mult;
            }
        }

        // Total multiplier
        double totalMultiplier = baseMultiplier * (1.0 + bonusAdditive);

        // Cap at 3x
        if (totalMultiplier > 3.0) totalMultiplier = 3.0;
        xp *= totalMultiplier;

        RunePlayerGainedXPEvent event = new RunePlayerGainedXPEvent(player, type, xp, source);
        AnalyticsUtils.incrementServerAndPlayer(player, AnalyticsKey.XP_GAINED, xp);
        Bukkit.getServer().getPluginManager().callEvent(event);

        if (event.isCancelled()) {
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


        scheduleXPBubble(type, xp, source, totalMultiplier);
        skill.addXp(xp);

        if (leveledUp) {
            int availableRecipesAfterLevelUp = RuneQuestPlugin.getRecipeHandler().getAvailableRecipesNumber(player);
            int level = skill.getBaseLevel();

            if (skill.getType().getMaxLevelCap() != null && level >= skill.getType().getMaxLevelCap()) {
                HudService.sendInfoMessage(player, Component.text("You've mastered ")
                        .append(Component.text(skill.getType().getLabel(), Color.SERVER_BRAND.asColor()))
                        .append(Component.text(" up to the current limit of ", Color.INFO.asColor()))
                        .append(Component.text(skill.getType().getMaxLevelCap() + "!", Color.ACCENT.asColor()))
                        .append(Component.text("!")));

                HudService.sendInfoMessage(player, Component.text(
                        "More challenges are on the way. Future content is in development - stay tuned!", Color.SERVER_BRAND.asColor()));
            }


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
