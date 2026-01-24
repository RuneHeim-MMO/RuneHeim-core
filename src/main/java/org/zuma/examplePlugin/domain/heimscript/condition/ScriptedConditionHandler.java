package org.zuma.examplePlugin.domain.heimscript.condition;

import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.logger.constants.LogLevel;
import org.zuma.examplePlugin.core.utils.PlayerUtils;
import org.zuma.examplePlugin.domain.dialog.Dialog;
import org.zuma.examplePlugin.domain.heimscript.ScriptParser;
import org.zuma.examplePlugin.domain.housing.HousingRegion;
import org.zuma.examplePlugin.domain.item.ItemRecord;
import org.zuma.examplePlugin.domain.mob.base.AbstractMob;
import org.zuma.examplePlugin.domain.modifier.Modifier;
import org.zuma.examplePlugin.domain.modifier.constants.ModifierType;
import org.zuma.examplePlugin.domain.player.RunePlayer;
import org.zuma.examplePlugin.domain.quest.Quest;
import org.zuma.examplePlugin.domain.quest.base.AbstractQuestStep;
import org.zuma.examplePlugin.domain.quest.constants.QuestStartFailReason;
import org.zuma.examplePlugin.domain.skill.constants.SkillType;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class ScriptedConditionHandler {
    public static boolean executeCondition(String input, RunePlayer player, @Nullable Runnable callback, @Nullable Dialog parentDialog) {
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
                case MODIFIER_GREATER_THAN_EQUALS -> {
                    String modifierId = args.get("id_modifier");
                    double amount = Double.parseDouble(args.get("amount"));
                    ModifierType modifierType = ModifierType.valueOf(modifierId.toUpperCase());
                    Modifier modifier = player.getModifiers().get(modifierType);

                    if (modifier != null && modifier.amount() >= amount) {
                        result = true;
                    }
                }
                case COINS_LESS_THAN -> {
                    result = player.getPouchCoins() < Integer.parseInt(args.get("amount"));
                }
                case INVENTORY_CONTAINS -> {
                    result = PlayerUtils.hasItems(player, List.of(new ItemRecord(Integer.parseInt(args.get("item_id")), Integer.parseInt(args.get("amount")))));
                }
                case PLAYER_OWNS_HOUSE_IN -> {
                    String region = args.get("region");
                    result = RuneQuestPlugin.getHousingManager().playHasHouseInRegion(player.getUUID(), HousingRegion.valueOf(region));
                }
                case INVENTORY_CONTAINS_ANY_OF -> {
                    String[] itemIds = args.get("item_ids").split(",");
                    String[] amounts = args.get("amounts").split(",");
                    List<ItemRecord> records = new ArrayList<>();

                    for (int index = 0; index < itemIds.length; index++) {
                        records.add(new ItemRecord(Integer.parseInt(itemIds[index]), Integer.parseInt(amounts[index])));
                    }

                    for (ItemRecord record : records) {
                        if (result) continue;
                        result = PlayerUtils.hasItems(player, List.of(record));
                    }
                }
                case REMOVE_INVENTORY_ITEM_SUCCESSFUL -> {
                    result = PlayerUtils.removeItems(player, List.of(new ItemRecord(Integer.parseInt(args.get("item_id")), Integer.parseInt(args.get("amount")))));
                }
                case REMOVE_POUCH_COINS_SUCCESSFUL -> {
                    int amount = Integer.parseInt(args.get("amount"));

                    if (player.getPouchCoins() >= amount) {
                        player.setPouchCoins(player.getPouchCoins() - amount, false);
                        result = true;
                    }

                }
                case QUEST_STARTED -> {
                    result = player.getActiveQuestSteps().containsKey(args.get("id"));
                }
                case QUEST_FINISHED -> {
                    result = player.getCompletedQuestIds().contains(args.get("id"));
                }
                case QUEST_STEP_ACTIVE -> {
                    String stable_id = args.get("stable_id");

                    for (AbstractQuestStep qs : player.getActiveQuestSteps().values()) {
                        if (qs instanceof AbstractQuestStep questStep && Objects.equals(questStep.getStableId(), stable_id)) {
                            result = true;
                            break;
                        }
                    }

                }

                case PLAYER_MEETS_QUEST_REQUIREMENTS -> {
                    String questId = args.get("id");
                    Quest q = RuneQuestPlugin.getQuestHandler().getRegistry().getQuestById(questId);

                    if (q != null) {
                        QuestStartFailReason failReason = q.getQuestStartFailReason(player);
                        if (failReason == null) result = true;
                    }


                }
                case QUEST_START_IF_REQUIREMENTS_MET -> {
                    String questId = args.get("id");
                    Quest q = RuneQuestPlugin.getQuestHandler().getRegistry().getQuestById(questId);

                    if (q != null) {
                        QuestStartFailReason failReason = q.getQuestStartFailReason(player);

                        if (failReason == null) {
                            q.startForPlayer(player, false);
                            result = true;
                        } else {
                            RuneQuestPlugin.getRuneLogger().log(LogLevel.INFO, "Couldn't start quest " + questId + " because: " + failReason);
                        }
                    }

                }
                case NEVER_TRUE -> {
                    result = false;
                }
                case RANDOM_CHANCE -> {
                    int n = (int) (Math.random() * 101);
                    result = (n < Integer.parseInt(args.get("percent")));
                }
                case CAN_CLAIM_DAILY_REWARD -> {
                    result = player.getDailyRewards().canClaimToday();
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
                "\u001B[36m[SCRIPT] Check " +
                        "\u001B[93m" + input +
                        "\u001B[36m on: " +
                        "\u001B[93m" + player.asBukkitPlayer().getName() +
                        "\u001B[36m result: " +
                        "\u001B[93m" + conditionResult +
                        "\u001B[0m.");

        return conditionResult;
    }

}
