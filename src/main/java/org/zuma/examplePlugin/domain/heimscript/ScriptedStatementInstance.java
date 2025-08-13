package org.zuma.examplePlugin.domain.heimscript;


import java.util.Map;

public class ScriptedStatementInstance {
    private final ScriptedStatement action;
    private final Map<String, String> args;

    public ScriptedStatementInstance(ScriptedStatement action, Map<String, String> args) {
        this.action = action;
        this.args = args;
    }


    public String toRawStatement() {
        final String[] result = {this.action.getTrigger()};

        this.args.forEach((key, value) -> {
            result[0] += "_{" + key + "_" + value + "}";
        });

        return result[0];

    }
}
