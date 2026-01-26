package org.zuma.examplePlugin.domain.heimscript.condition;

import org.zuma.examplePlugin.domain.heimscript.ScriptedStatement;
import org.zuma.examplePlugin.domain.heimscript.ScriptedStatementInstance;
import org.zuma.examplePlugin.domain.heimscript.StatementType;
import org.zuma.examplePlugin.domain.item.ItemRecord;
import org.zuma.examplePlugin.domain.quest.constants.QuestId;
import org.zuma.examplePlugin.domain.skill.constants.SkillType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public enum ScriptedCondition implements ScriptedStatement {
    IS_USERNAME("is_username", "username"),
    SKILL_GREATER_THAN("skill_gt", "skill", "level"),
    SKILL_GREATER_THAN_EQUALS("skill_gte", "skill", "level"),
    SKILL_LESS_THAN("skill_lt", "skill", "level"),

    COMBAT_GREATER_THAN("combat_gt", "level"),
    COMBAT_GREATER_THAN_EQUALS("combat_gte", "level"),
    COMBAT_LESS_THAN("combat_lt", "level"),

    COINS_GREATER_THAN("coins_gt", "amount"),
    COINS_GREATER_THAN_EQUALS("coins_gte", "amount"),
    COINS_LESS_THAN("coins_lt", "amount"),

    MODIFIER_GREATER_THAN_EQUALS("modifier_gte", "id_modifier", "amount"),

    INVENTORY_CONTAINS("inventory_contains_one_of", "item_id", "amount"),
    INVENTORY_CONTAINS_ANY_OF("inventory_contains_any_of", "item_ids", "amounts"),
    REMOVE_INVENTORY_ITEM_SUCCESSFUL("remove_inventory_item_successful", "item_id", "amount"),
    REMOVE_POUCH_COINS_SUCCESSFUL("remove_pouch_coins_successful", "amount"),

    QUEST_START_IF_REQUIREMENTS_MET("start_quest_if_requirements_met", "id"),
    PLAYER_MEETS_QUEST_REQUIREMENTS("player_meets_quest_requirements", "id"),
    QUEST_STARTED("quest_started", "id"),
    QUEST_FINISHED("quest_finished", "id"),
    QUEST_STEP_ACTIVE("quest_step_active", "stable_id"),
    NEVER_TRUE("never_true"),
    RANDOM_CHANCE("random_chance", "percent"),
    CAN_CLAIM_DAILY_REWARD("can_claim_daily_reward"),
    PLAYER_OWNS_HOUSE_IN("player_owns_house_in", "region");

    public final String trigger;
    public final List<String> argsTemplates;

    ScriptedCondition(String trigger, String... argsTemplates) {
        this.trigger = trigger;
        this.argsTemplates = new ArrayList<>(List.of(argsTemplates));
    }

    public static ScriptedStatementInstance questStartSuccessful(QuestId id) {
        return new ScriptedStatementInstance(ScriptedCondition.QUEST_START_IF_REQUIREMENTS_MET, Map.of(
                "id", id.getId()
        ));
    }

    public static ScriptedStatementInstance questFinished(QuestId id) {
        return new ScriptedStatementInstance(ScriptedCondition.QUEST_FINISHED, Map.of(
                "id", id.getId()
        ));
    }

    public static ScriptedStatementInstance questStarted(QuestId id) {
        return new ScriptedStatementInstance(ScriptedCondition.QUEST_STARTED, Map.of(
                "id", id.getId()
        ));
    }

    public static ScriptedStatementInstance questQuestStepActive(String stable_id) {
        return new ScriptedStatementInstance(ScriptedCondition.QUEST_STEP_ACTIVE, Map.of(
                "stable_id", stable_id
        ));
    }

    public static ScriptedStatementInstance removePouchCoinsSuccessful(int amount) {
        return new ScriptedStatementInstance(ScriptedCondition.REMOVE_POUCH_COINS_SUCCESSFUL, Map.of(
                "amount", String.valueOf(amount)
        ));
    }

    public static ScriptedStatementInstance removeInventoryItemSuccessful(ItemRecord record) {
        return new ScriptedStatementInstance(ScriptedCondition.REMOVE_INVENTORY_ITEM_SUCCESSFUL, Map.of(
                "item_id", String.valueOf(record.getItemId()),
                "amount", String.valueOf(record.getAmount())
        ));
    }

    public static ScriptedStatementInstance inventoryContainsAnyOf(List<ItemRecord> records) {
        List<String> ids = new ArrayList<>();
        List<String> amounts = new ArrayList<>();

        records.forEach(r -> {
            ids.add(String.valueOf(r.getItemId()));
            amounts.add(String.valueOf(r.getAmount()));
        });

        return new ScriptedStatementInstance(ScriptedCondition.INVENTORY_CONTAINS_ANY_OF, Map.of(
                "item_ids", String.join(",", ids),
                "amounts", String.join(",", amounts)
        ));
    }

    public static ScriptedStatementInstance inventoryContains(ItemRecord record) {
        return new ScriptedStatementInstance(ScriptedCondition.INVENTORY_CONTAINS, Map.of(
                "item_id", String.valueOf(record.getItemId()),
                "amount", String.valueOf(record.getAmount())
        ));
    }

    public static ScriptedStatementInstance isUsername(String username) {
        return new ScriptedStatementInstance(ScriptedCondition.IS_USERNAME, Map.of(
                "username", username
        ));
    }

    public static ScriptedStatementInstance skillGreaterThan(SkillType skillType, int level) {
        return new ScriptedStatementInstance(ScriptedCondition.SKILL_GREATER_THAN, Map.of(
                "skill", skillType.toString(),
                "level", String.valueOf(level)
        ));
    }

    public static ScriptedStatementInstance skillGreaterThanEquals(SkillType skillType, int level) {
        return new ScriptedStatementInstance(ScriptedCondition.SKILL_GREATER_THAN_EQUALS, Map.of(
                "skill", skillType.toString(),
                "level", String.valueOf(level)
        ));
    }

    public static ScriptedStatementInstance skillLessThan(SkillType skillType, int level) {
        return new ScriptedStatementInstance(ScriptedCondition.SKILL_LESS_THAN, Map.of(
                "skill", skillType.toString(),
                "level", String.valueOf(level)
        ));
    }

    public static ScriptedStatementInstance combatGreaterThan(int level) {
        return new ScriptedStatementInstance(ScriptedCondition.COMBAT_GREATER_THAN, Map.of(
                "level", String.valueOf(level)
        ));
    }

    public static ScriptedStatementInstance combatGreaterThanEquals(int level) {
        return new ScriptedStatementInstance(ScriptedCondition.COMBAT_GREATER_THAN_EQUALS, Map.of(
                "level", String.valueOf(level)
        ));
    }

    public static ScriptedStatementInstance combatLessThan(int level) {
        return new ScriptedStatementInstance(ScriptedCondition.COMBAT_LESS_THAN, Map.of(
                "level", String.valueOf(level)
        ));
    }

    public static ScriptedStatementInstance coinsGreaterThan(long amount) {
        return new ScriptedStatementInstance(ScriptedCondition.COINS_GREATER_THAN, Map.of(
                "amount", String.valueOf(amount)
        ));
    }

    public static ScriptedStatementInstance coinsGreaterThanEquals(long amount) {
        return new ScriptedStatementInstance(ScriptedCondition.COINS_GREATER_THAN_EQUALS, Map.of(
                "amount", String.valueOf(amount)
        ));
    }

    public static ScriptedStatementInstance coinsLessThan(long amount) {
        return new ScriptedStatementInstance(ScriptedCondition.COINS_LESS_THAN, Map.of(
                "amount", String.valueOf(amount)
        ));
    }

    @Override
    public StatementType getType() {
        return StatementType.ACTION;
    }

    public String getTrigger() {
        return this.trigger;
    }

    public List<String> getArgsTemplates() {
        return this.argsTemplates;
    }
}
