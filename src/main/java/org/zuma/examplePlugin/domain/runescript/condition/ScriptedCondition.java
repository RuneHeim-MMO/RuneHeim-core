package org.zuma.examplePlugin.domain.runescript.condition;

import org.zuma.examplePlugin.domain.runescript.ScriptedStatement;
import org.zuma.examplePlugin.domain.runescript.StatementType;

import java.util.ArrayList;
import java.util.List;

public enum ScriptedCondition implements ScriptedStatement {
    IS_USERNAME("is_username", "username");

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


}
