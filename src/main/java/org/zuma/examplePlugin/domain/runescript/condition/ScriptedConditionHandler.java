package org.zuma.examplePlugin.domain.runescript.condition;

import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.logger.constants.LogLevel;
import org.zuma.examplePlugin.domain.dialog.Dialog;
import org.zuma.examplePlugin.domain.mob.base.AbstractMob;
import org.zuma.examplePlugin.domain.player.RunePlayer;
import org.zuma.examplePlugin.domain.runescript.ScriptParser;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;

public class ScriptedConditionHandler {


    public static boolean executeCondition(String input, RunePlayer player, @Nullable Runnable callback, Dialog parentDialog) {
        try {
            ScriptedCondition action = ScriptParser.matchInputToStatement(input, ScriptedCondition.class);
            HashMap<String, String> args = ScriptParser.parseArgs(input, action);
            AbstractMob holder = getHolder(input, parentDialog, action);

            switch (action) {
                case IS_USERNAME -> {
                    boolean result = player.asBukkitPlayer().getName().equals(args.get("username"));
                    return loggerWrapper(input, player, result);
                }
                default ->
                        RuneQuestPlugin.getRuneLogger().log(LogLevel.CRITICAL, "Unknown condition " + action + " in " + input + "!");
            }


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
