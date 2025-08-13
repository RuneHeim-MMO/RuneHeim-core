package org.zuma.examplePlugin.domain.heimscript.action;

import org.zuma.examplePlugin.domain.droptable.DropTableId;
import org.zuma.examplePlugin.domain.gui.constants.InventoryId;
import org.zuma.examplePlugin.domain.heimscript.condition.ScriptedCondition;
import org.zuma.examplePlugin.domain.item.constants.ItemId;
import org.zuma.examplePlugin.domain.mob.behavior.MobBehavior;
import org.zuma.examplePlugin.domain.heimscript.ScriptedStatement;
import org.zuma.examplePlugin.domain.heimscript.ScriptedStatementInstance;
import org.zuma.examplePlugin.domain.heimscript.StatementType;
import org.zuma.examplePlugin.domain.skill.constants.SkillType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public enum ScriptedAction implements ScriptedStatement {
    OPEN_INVENTORY("open_inventory", "id"), // open_inventory_{id_4}
    GIVE_ITEM("give_item", "id", "amount"),
    ROLL_DROP_TABLE("roll_drop_table", "id"),
    SPAWN_MOB("spawn_mob", "type", "world", "x", "y", "z"),
    GIVE_DISTRIBUTED_XP("give_distributed_xp", "skill", "xp"),
    GIVE_PERSONAL_XP("give_personal_xp", "skill", "xp"),
    TELEPORT_PLAYER("teleport_player", "world", "x", "y", "z"),
    TAKE_POUCH_COINS("take_pouch_coins", "amount"),
    DIALOG_HOLDER_SOUND("dialog_holder_sound", "sound"),
    DIALOG_HOLDER_FORWARD_INTERACTION("dialog_holder_forward_interaction"),
    TAKE_INVENTORY_ITEM("take_inventory_item", "item_id", "amount");

    private final String trigger;
    private final List<String> argsTemplates;

    ScriptedAction(String trigger, String... argsTemplates) {
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


    public static ScriptedStatementInstance takeInventoryItem(ItemId id, int amount) {
        return new ScriptedStatementInstance(ScriptedAction.TAKE_INVENTORY_ITEM, Map.of(
                "item_id", String.valueOf(id.asInt()),
                "amount", String.valueOf(amount)
        ));
    }

    public static ScriptedStatementInstance openInventory(InventoryId id) {
        return new ScriptedStatementInstance(ScriptedAction.OPEN_INVENTORY, Map.of(
                "id", String.valueOf(id.asInt())
        ));
    }

    public static ScriptedStatementInstance giveItem(ItemId id, int amount) {
        return new ScriptedStatementInstance(ScriptedAction.GIVE_ITEM, Map.of(
                "id", String.valueOf(id.asInt()),
                "amount", String.valueOf(amount)
        ));
    }

    public static ScriptedStatementInstance rollDropTable(DropTableId id) {
        return new ScriptedStatementInstance(ScriptedAction.ROLL_DROP_TABLE, Map.of(
                "id", String.valueOf(id.asInt())
        ));
    }

    public static ScriptedStatementInstance spawnMob(String type, String world, double x, double y, double z) {
        return new ScriptedStatementInstance(ScriptedAction.SPAWN_MOB, Map.of(
                "type", type,
                "world", world,
                "x", String.valueOf(x),
                "y", String.valueOf(y),
                "z", String.valueOf(z)
        ));
    }

    public static ScriptedStatementInstance giveDistributedXp(SkillType skillType, int xp) {
        return new ScriptedStatementInstance(ScriptedAction.GIVE_DISTRIBUTED_XP, Map.of(
                "skill", skillType.toString(),
                "xp", String.valueOf(xp)
        ));
    }

    public static ScriptedStatementInstance givePersonalXp(SkillType skillType, int xp) {
        return new ScriptedStatementInstance(ScriptedAction.GIVE_PERSONAL_XP, Map.of(
                "skill", skillType.toString(),
                "xp", String.valueOf(xp)
        ));
    }

    public static ScriptedStatementInstance teleportPlayer(String world, double x, double y, double z) {
        return new ScriptedStatementInstance(ScriptedAction.TELEPORT_PLAYER, Map.of(
                "world", world,
                "x", String.valueOf(x),
                "y", String.valueOf(y),
                "z", String.valueOf(z)
        ));
    }

    public static ScriptedStatementInstance takePouchCoins(long amount) {
        return new ScriptedStatementInstance(ScriptedAction.TAKE_POUCH_COINS, Map.of(
                "amount", String.valueOf(amount)
        ));
    }

    public static ScriptedStatementInstance dialogHolderSound(MobBehavior.MobSoundType sound) {
        return new ScriptedStatementInstance(ScriptedAction.DIALOG_HOLDER_SOUND, Map.of(
                "sound", String.valueOf(sound.toString())
        ));
    }

    public static ScriptedStatementInstance dialogHolderForwardInteraction() {
        return new ScriptedStatementInstance(ScriptedAction.DIALOG_HOLDER_FORWARD_INTERACTION, Map.of());
    }


}
