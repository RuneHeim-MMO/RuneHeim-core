package org.zuma.examplePlugin.core.logger;

import org.bukkit.Bukkit;
import org.zuma.examplePlugin.core.logger.constants.LogLevel;

import java.io.PrintWriter;
import java.io.StringWriter;

public class Logger {
    private final static String PREFIX = "[RuneHeim]";

    public Logger() {
    }

    public static String getStackTrace(String message) {
        Exception e = new Exception(message); // Not thrown, just used for stack trace

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);

        return sw.toString();
    }

    public void log(LogLevel level, String message) {
        log(level, message, null);
    }

    public void log(LogLevel level, String message, Throwable throwable) {
        String incidentPrefix = (level != LogLevel.INFO && level != LogLevel.MODULE) ? "[Incident #" + System.currentTimeMillis() + "] " : "";

        String formattedMessage = incidentPrefix + PREFIX + " [" + level.getString() + "]: " + message;

        if (level == LogLevel.INFO) {
            formattedMessage = "\u001B[96m" + formattedMessage + "\u001B[0m";
        }

        if (level == LogLevel.MODULE) {
            formattedMessage = "\u001B[92m" + formattedMessage + "\u001B[0m";
        }

        if (throwable != null) {
            Bukkit.getLogger().log(level.getLoggingLevel(), formattedMessage, throwable);
        } else {
            Bukkit.getLogger().log(level.getLoggingLevel(), formattedMessage);
        }
    }

    public void logWithStack(LogLevel level, String message) {
        String stackTrace = getStackTrace(message);
        log(level, message + "\n" + stackTrace);
    }
}
