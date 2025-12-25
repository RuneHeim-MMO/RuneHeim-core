package org.zuma.examplePlugin.domain.announcements;

import org.bukkit.Bukkit;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.logger.constants.LogLevel;
import org.zuma.examplePlugin.domain.announcements.persistence.AnnouncementsRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class AnnouncementsHandler {
    private final AnnouncementsRepository repository;
    private List<Announcement> activeAnnouncements;
    private int lastAnnouncementIndex;
    private final Object lock;

    public AnnouncementsHandler(AnnouncementsRepository repository) {
        this.repository = repository;
        this.activeAnnouncements = new ArrayList<>();
        this.lastAnnouncementIndex = 0;
        this.lock = new Object();
    }

    public void startRefetchInterval(int durationMinutes) {
        Bukkit.getScheduler().runTaskTimerAsynchronously(RuneQuestPlugin.getInstance(), () -> {
            List<Announcement> newAnnouncements = repository.getAll();

            synchronized (lock) {
                Set<String> oldMessages = activeAnnouncements.stream()
                        .map(Announcement::message)
                        .collect(Collectors.toSet());

                Set<String> newMessages = newAnnouncements.stream()
                        .map(Announcement::message)
                        .collect(Collectors.toSet());

                boolean changed = !oldMessages.equals(newMessages);

                if (changed) {
                    RuneQuestPlugin.getRuneLogger().log(LogLevel.INFO, "Announcements list updated. Found " + newAnnouncements.size() + " announcements.");
                    lastAnnouncementIndex = 0;
                }

                activeAnnouncements = newAnnouncements;


            }
        }, 0L, durationMinutes * 60L * 20L);
    }

    public void startAnnouncementBroadcastInterval(int durationMinutes) {
        Bukkit.getScheduler().runTaskTimer(RuneQuestPlugin.getInstance(), () -> {
            synchronized (lock) {
                if (activeAnnouncements.isEmpty()) {
                    return;
                }

                lastAnnouncementIndex++;

                if (lastAnnouncementIndex >= activeAnnouncements.size()) {
                    lastAnnouncementIndex = 0;
                }

                Announcement announcement = activeAnnouncements.get(lastAnnouncementIndex);

                if (announcement != null) {
                    announcement.broadcast();
                }
            }

        }, 0L, durationMinutes * 60L * 20L);
    }

}
