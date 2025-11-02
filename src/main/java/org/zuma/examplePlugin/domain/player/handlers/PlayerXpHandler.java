package org.zuma.examplePlugin.domain.player.handlers;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;
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


    @Nullable
    private SkillType lastXPGainedSkillType;
    private final EnumMap<SkillType, Double> deferredXPHolograms = new EnumMap<>(SkillType.class);
    private BukkitTask debouncedXPBubbleTask;


    public PlayerXpHandler(RunePlayer player) {
        this.player = player;
        this.lastXPGainedSkillType = null;
    }

    private void scheduleXPBubble(SkillType type, double xp, XpSource source) {
        final double EPSILON = 1e-6;

        if (deferredXPHolograms.containsKey(type)) {
            deferredXPHolograms.put(type, deferredXPHolograms.get(type) + xp);
        } else {
            deferredXPHolograms.put(type, xp);
        }

        if (debouncedXPBubbleTask != null) {
            debouncedXPBubbleTask.cancel(); // Cancel the previous task to reschedule
        }

        debouncedXPBubbleTask = Bukkit.getScheduler().runTaskLater(RuneQuestPlugin.getInstance(), () -> {
            if (player.asBukkitPlayer() == null) return;

            boolean shouldCombine = true;
            Double firstValue = null;

            for (SkillType skill : skillsToBeCombined) {
                double value = deferredXPHolograms.getOrDefault(skill, 0.0);
                if (firstValue == null) {
                    firstValue = value;
                } else if (Math.abs(firstValue - value) > EPSILON) {
                    shouldCombine = false;
                    break;
                }
            }

            if (!shouldCombine) {
                for (Map.Entry<SkillType, Double> entry : deferredXPHolograms.entrySet()) {
                    final SkillType skillType = entry.getKey();
                    final double amount = entry.getValue(); // capture value safely
                    Bukkit.getScheduler().runTaskLater(RuneQuestPlugin.getInstance(), () -> {
                        if (player.asBukkitPlayer() == null) return;
                        HudService.showXpGainMessage(player, skillType, amount, source);
                    }, skillType.ordinal() * 5L);
                }
            } else {
                deferredXPHolograms.forEach((skillType, amount) -> {
                    if (skillsToBeCombined.contains(skillType)) return;
                    Bukkit.getScheduler().runTaskLater(RuneQuestPlugin.getInstance(), () -> {
                        if (player.asBukkitPlayer() == null) return;

                        HudService.showXpGainMessage(player, skillType, amount, source);
                    }, skillType.ordinal() * 5L);
                });

                double firstXP = firstValue != null ? firstValue : 0.0;

                if (firstXP != 0.0) {
                    HudService.showCombinedCombatXPGainMessage(player, firstXP, source);
                }
            }

            deferredXPHolograms.clear();

        }, 40L);


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


        scheduleXPBubble(type, xp, source);
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
