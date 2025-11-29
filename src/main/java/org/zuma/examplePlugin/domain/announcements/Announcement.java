package org.zuma.examplePlugin.domain.announcements;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.constants.Color;
import org.zuma.examplePlugin.domain.hud.HudService;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Announcement {
    private final static Pattern LINK_PATTERN = Pattern.compile("<link:(https?://[^>]+)>");

    private final int id;
    private final String message;
    private long lastSentAt;

    public Announcement(int id, String message) {
        this.message = message;
        this.lastSentAt = 0L;
        this.id = id;
    }

    public void broadcast() {
        this.lastSentAt = System.currentTimeMillis();

        String raw = this.message;
        raw = raw.replace("&", "§");

        Matcher matcher = LINK_PATTERN.matcher(raw);
        Component finalComponent = Component.empty();

        int lastEnd = 0;
        while (matcher.find()) {
            // Normal text before the link tag
            if (matcher.start() > lastEnd) {
                finalComponent = finalComponent.append(Component.text(raw.substring(lastEnd, matcher.start())));
            }

            String url = matcher.group(1);

            // Clickable fancy part
            Component clickable = Component.text("[Link]", Color.ACCENT.asColor()).decorate(TextDecoration.BOLD)
                    .clickEvent(ClickEvent.openUrl(url))
                    .hoverEvent(HoverEvent.showText(Component.text("Click to open link")));

            finalComponent = finalComponent.append(clickable);
            lastEnd = matcher.end();
        }

        // Append leftover text after last link
        if (lastEnd < raw.length()) {
            finalComponent = finalComponent.append(Component.text(raw.substring(lastEnd)));
        }

        Component finalComponent1 = finalComponent;
        RuneQuestPlugin.getPlayerManager().getOnlinePlayers().forEach(p -> {
            HudService.sendAnnouncementMessage(p, finalComponent1);
        });
    }

    public String message() {
        return message;
    }

    public long getLastSentAt() {
        return lastSentAt;
    }


    public int getId() {
        return id;
    }
}
