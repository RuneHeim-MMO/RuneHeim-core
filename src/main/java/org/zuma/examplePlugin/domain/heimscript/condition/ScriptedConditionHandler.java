package org.zuma.examplePlugin.domain.heimscript.condition;

import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.logger.constants.LogLevel;
import org.zuma.examplePlugin.core.utils.PlayerUtils;
import org.zuma.examplePlugin.domain.dialog.Dialog;
import org.zuma.examplePlugin.domain.item.ItemRecord;
import org.zuma.examplePlugin.domain.mob.base.AbstractMob;
import org.zuma.examplePlugin.domain.player.RunePlayer;
import org.zuma.examplePlugin.domain.heimscript.ScriptParser;
import org.zuma.examplePlugin.domain.skill.constants.SkillType;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;

public class ScriptedConditionHandler {

    /*    IS_USERNAME("is_username", "username"),
    SKILL_GREATER_THAN("skill_gt", "skill", "level"),
    SKILL_LESS_THAN("skill_lt", "skill", "level"),
    COMBAT_GREATER_THAN("combat_gt", "level"),
    COMBAT_LESS_THAN("combat_lt", "level"),
    COINS_GREATER_THAN("coins_gt", "amount"),
    COINS_LESS_THAN("coins_lt", "amount");*/

    public static boolean executeCondition(String input, RunePlayer player, @Nullable Runnable callback, Dialog parentDialog) {
        try {
            ScriptedCondition action = ScriptParser.matchInputToStatement(input, ScriptedCondition.class);
            HashMap<String, String> args = ScriptParser.parseArgs(input, action);
            AbstractMob holder = getHolder(input, parentDialog, action);
            boolean result = false;

            switch (action) {
                case IS_USERNAME -> {
                    result = player.asBukkitPlayer().getName().equals(args.get("username"));
                }
                case SKILL_GREATER_THAN -> {
                    SkillType skillType = SkillType.valueOf(args.get("skill").toUpperCase());
                    result = player.getSkill(skillType).getLevelWithBonuses() > Integer.parseInt(args.get("level"));
                }
                case SKILL_GREATER_THAN_EQUALS -> {
                    SkillType skillType = SkillType.valueOf(args.get("skill").toUpperCase());
                    result = player.getSkill(skillType).getLevelWithBonuses() >= Integer.parseInt(args.get("level"));
                }
                case SKILL_LESS_THAN -> {
                    SkillType skillType = SkillType.valueOf(args.get("skill").toUpperCase());
                    result = player.getSkill(skillType).getLevelWithBonuses() < Integer.parseInt(args.get("level"));
                }
                case COMBAT_GREATER_THAN -> {
                    result = player.getCombatLvl() > Integer.parseInt(args.get("level"));
                }
                case COMBAT_GREATER_THAN_EQUALS -> {
                    result = player.getCombatLvl() >= Integer.parseInt(args.get("level"));
                }
                case COMBAT_LESS_THAN -> {
                    result = player.getCombatLvl() < Integer.parseInt(args.get("level"));
                }
                case COINS_GREATER_THAN -> {
                    result = player.getPouchCoins() > Integer.parseInt(args.get("amount"));
                }
                case COINS_GREATER_THAN_EQUALS -> {
                    result = player.getPouchCoins() >= Integer.parseInt(args.get("amount"));
                }
                case COINS_LESS_THAN -> {
                    result = player.getPouchCoins() < Integer.parseInt(args.get("amount"));
                }
                case INVENTORY_CONTAINS -> {
                    result = PlayerUtils.hasItems(player, List.of(new ItemRecord(Integer.parseInt(args.get("item_id")), Integer.parseInt(args.get("amount")))));
                }
                case REMOVE_INVENTORY_ITEM_SUCCESSFUL -> {
                    result = PlayerUtils.removeItems(player, List.of(new ItemRecord(Integer.parseInt(args.get("item_id")), Integer.parseInt(args.get("amount")))));
                }
                default ->
                        RuneQuestPlugin.getRuneLogger().log(LogLevel.CRITICAL, "Unknown condition " + action + " in " + input + "!");
            }

            return loggerWrapper(input, player, result);


        } catch (Exception e) {
            RuneQuestPlugin.getRuneLogger().log(LogLevel.CRITICAL, "[SCRIPT] Could not execute the condition " + input, e);
        }

        return false;
    }

    private static @org.jetbrains.annotations.Nullable AbstractMob getHolder(String input, @org.jetbrains.annotations.Nullable Dialog parentDialog, ScriptedCondition condition) {
        List<ScriptedCondition> typesRequireDialogHolder = List.of();
        AbstractMob holder = null;

        if (typesRequireDialogHolder.contains(condition)) {
            if (parentDialog == null) {
                throw new IllegalStateException("[SCRIPT] Cannot run " + input + " condition is executed outside of dialog context!");
            }

            holder = parentDialog.getDialogHolder();

            if (holder == null) {
                throw new IllegalStateException("[SCRIPT] Cannot run " + input + " condition dialog holder is null!");
            }
        }
        return holder;
    }

    private static boolean loggerWrapper(String input, RunePlayer player, boolean conditionResult) {
        RuneQuestPlugin.getRuneLogger().log(LogLevel.INFO,
                "\u001B[36m[SCRIPT] Condition " +
                        "\u001B[93m" + input +
                        "\u001B[36m executed on target " +
                        "\u001B[93m" + player.asBukkitPlayer().getName() +
                        "\u001B[36m condition result " +
                        "\u001B[93m" + conditionResult +
                        "\u001B[0m.");

        return conditionResult;
    }

}
