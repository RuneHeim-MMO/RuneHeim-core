package org.zuma.examplePlugin.domain.tour;

import com.melluh.servertours.api.event.RoutePlaybackEndEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class TourListener implements Listener {
    private final TourHandler tourHandler;

    public TourListener(TourHandler tourHandler) {
        this.tourHandler = tourHandler;
    }

    @EventHandler
    public void onTourFinish(RoutePlaybackEndEvent event) {
        Player player = event.getBukkitPlayer();

        tourHandler.handleTourEnd(player == null || !player.isOnline() ? null : player.getUniqueId());
    }
}
