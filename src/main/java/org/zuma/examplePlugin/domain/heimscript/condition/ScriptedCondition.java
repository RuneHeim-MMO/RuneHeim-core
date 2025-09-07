package org.zuma.examplePlugin.domain.heimscript.condition;

import org.zuma.examplePlugin.domain.heimscript.ScriptedStatement;
import org.zuma.examplePlugin.domain.heimscript.ScriptedStatementInstance;
import org.zuma.examplePlugin.domain.heimscript.StatementType;
import org.zuma.examplePlugin.domain.item.constants.ItemId;
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

    INVENTORY_CONTAINS("inventory_contains", "item_id", "amount"),
    REMOVE_INVENTORY_ITEM_SUCCESSFUL("remove_inventory_item_successful", "item_id", "amount"),
    REMOVE_POUCH_COINS_SUCCESSFUL("remove_pouch_coins_successful",  "amount");

    public final String trigger;
    public final List<String> argsTemplates;

    ScriptedCondition(String trigger, String... argsTemplates) {
        this.trigger = trigger;
        this.argsTemplates = new ArrayList<>(List.of(argsTemplates));
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

    public static ScriptedStatementInstance removePouchCoinsSuccessful(int amount) {
        return new ScriptedStatementInstance(ScriptedCondition.REMOVE_POUCH_COINS_SUCCESSFUL, Map.of(
                "amount", String.valueOf(amount)
        ));
    }

    public static ScriptedStatementInstance removeInventoryItemSuccessful(ItemId id, int amount) {
        return new ScriptedStatementInstance(ScriptedCondition.REMOVE_INVENTORY_ITEM_SUCCESSFUL, Map.of(
                "item_id", String.valueOf(id.asInt()),
                "amount", String.valueOf(amount)
        ));
    }

    public static ScriptedStatementInstance inventoryContains(ItemId id, int amount) {
        return new ScriptedStatementInstance(ScriptedCondition.INVENTORY_CONTAINS, Map.of(
                "item_id", String.valueOf(id.asInt()),
                "amount", String.valueOf(amount)
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
}
