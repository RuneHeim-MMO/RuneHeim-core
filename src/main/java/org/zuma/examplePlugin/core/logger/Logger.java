package org.zuma.examplePlugin.core.logger;

import org.bukkit.Bukkit;
import org.zuma.examplePlugin.core.logger.constants.LogLevel;

public class Logger {

    public Logger() {
    }

    public void log(LogLevel level, String message) {
        Bukkit.getLogger().log(level.getLoggingLevel(), "[" + level.getString() + "]: " + message);
    }
}
