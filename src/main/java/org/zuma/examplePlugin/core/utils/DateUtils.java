package org.zuma.examplePlugin.core.utils;

import java.time.Duration;
import java.time.Instant;

public class DateUtils {
    public static String getTimeDifference(long futureTimestampMillis) {
        Instant now = Instant.now();
        Instant future = Instant.ofEpochMilli(futureTimestampMillis);

        if (future.isBefore(now)) {
            return "Time is in the past";
        }

        Duration duration = Duration.between(now, future);
        long days = duration.toDays();
        duration = duration.minusDays(days);
        long hours = duration.toHours();
        duration = duration.minusHours(hours);
        long minutes = duration.toMinutes();

        StringBuilder sb = new StringBuilder();
        if (days > 0) sb.append(days).append(days == 1 ? " day" : " days");
        if (hours > 0) {
            if (sb.length() > 0) sb.append(" ");
            sb.append(hours).append(hours == 1 ? " hour" : " hours");
        }
        if (minutes > 0) {
            if (sb.length() > 0) sb.append(" ");
            sb.append(minutes).append(minutes == 1 ? " minute" : " minutes");
        }

        if (sb.length() == 0) {
            sb.append("less than a minute");
        }

        return sb.toString();
    }
}
