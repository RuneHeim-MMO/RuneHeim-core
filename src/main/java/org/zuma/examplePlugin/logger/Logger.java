package org.zuma.examplePlugin.logger;

import org.bukkit.Bukkit;

public class Logger {

    public Logger() {
    }

    public void log(LogLevel level, String message) {
        Bukkit.getLogger().log(level.getLoggingLevel(), "[" + level.getString() + "]: " + message);
    }
}
