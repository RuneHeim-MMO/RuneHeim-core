package org.zuma.examplePlugin.domain.heimscript;

import java.util.List;

public interface ScriptedStatement {
    StatementType getType();

    List<String> getArgsTemplates();

    String getTrigger();
}
