package org.zuma.examplePlugin.domain.tour;

import com.melluh.servertours.api.event.RoutePlaybackEndEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

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

    @EventHandler(priority = EventPriority.LOWEST)
    public void onInventoryClickDuringTour(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        if (tourHandler.isViewingTour(player.getUniqueId())) {
            event.setCancelled(true);
        }
    }
}
