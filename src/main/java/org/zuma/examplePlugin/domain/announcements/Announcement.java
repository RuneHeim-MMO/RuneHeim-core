package org.zuma.examplePlugin.domain.announcements;

import net.kyori.adventure.text.Component;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.domain.hud.HudService;

public class Announcement {
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
        RuneQuestPlugin.getPlayerManager().getOnlinePlayers().forEach(p -> {
            HudService.sendAnnouncementMessage(p, Component.text(message.replaceAll("&", "§")));
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
