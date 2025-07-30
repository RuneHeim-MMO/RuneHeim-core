package org.zuma.examplePlugin.core.logger;

import org.bukkit.Bukkit;
import org.zuma.examplePlugin.core.logger.constants.LogLevel;

import java.io.PrintWriter;
import java.io.StringWriter;

public class Logger {
    private final static String PREFIX = "[RuneHeim Core]";

    public Logger() {
    }

    public void log(LogLevel level, String message, Throwable throwable) {
        Bukkit.getLogger().log(level.getLoggingLevel(), PREFIX + " [" + level.getString() + "]: " + message, throwable);
    }

    public void log(LogLevel level, String message) {
        Bukkit.getLogger().log(level.getLoggingLevel(), PREFIX + " [" + level.getString() + "]: " + message);
    }

    public void logWithStack(LogLevel level, String message) {
        String stackTrace = getStackTrace(message);
        log(level, message + "\n" + stackTrace);
    }

    public static String getStackTrace(String message) {
        Exception e = new Exception(message); // Not thrown, just used for stack trace

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);

        return sw.toString();
    }
}
