package org.zuma.examplePlugin.domain.runescript.action;

import org.zuma.examplePlugin.domain.runescript.ScriptedStatement;
import org.zuma.examplePlugin.domain.runescript.StatementType;

import java.util.ArrayList;
import java.util.List;

public enum ScriptedAction implements ScriptedStatement {
    OPEN_INVENTORY("open_inventory", "id"), // open_inventory_{id_4}
    GIVE_ITEM("give_item", "id", "amount"),
    ROLL_DROP_TABLE("roll_drop_table", "id"),
    SPAWN_MOB("spawn_mob", "type", "world", "x", "y", "z"),
    GIVE_DISTRIBUTED_XP("give_distributed_xp", "skill", "xp"),
    GIVE_PERSONAL_XP("give_personal_xp", "skill", "xp"),
    TELEPORT_PLAYER("teleport_player", "world", "x", "y", "z"),
    DIALOG_HOLDER_SOUND("dialog_holder_sound", "sound"),
    DIALOG_HOLDER_FORWARD_INTERACTION("dialog_holder_forward_interaction");

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


}
