package org.zuma.examplePlugin.core.logger.constants;

import java.util.logging.Level;

public enum LogLevel {
    MODULE("Module", Level.INFO),
    INFO("Info", Level.INFO),
    WARN("Warning", Level.WARNING),
    CRITICAL("Critical", Level.SEVERE);

    private final String strRepresentation;
    private final Level level;

    LogLevel(String strRepresentation, Level level) {
        this.strRepresentation = strRepresentation;
        this.level = level;
    }

    public String getString() {
        return strRepresentation;
    }

    public Level getLoggingLevel() {
        return level;
    }
}
