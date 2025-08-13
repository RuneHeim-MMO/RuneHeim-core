package org.zuma.examplePlugin.domain.runescript;

import java.util.List;

public interface ScriptedStatement {
    public StatementType getType();

    public List<String> getArgsTemplates();

    public String getTrigger();
}
