package org.zuma.examplePlugin.core.handlers.game;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.domain.effect.EffectHandler;
import org.zuma.examplePlugin.domain.effect.effects.FirstJoinEffect;
import org.zuma.examplePlugin.domain.player.RunePlayer;
import org.zuma.examplePlugin.domain.tour.Tour;
import org.zuma.examplePlugin.events.player.RunePlayerLoadedEvent;

public class FirstJoinTutorialTeleporter implements Listener {

    @EventHandler
    public void onPlayerFirstJoin(RunePlayerLoadedEvent event) {
        if (!event.isFirstTimeJoin() || event.isCancelled()) {
            return;
        }


        RunePlayer player = event.getPlayer();
        player.asBukkitPlayer().teleport(new Location(Bukkit.getWorld("world"), 2435.6, 155, 2465.8, 25, 7));

        RuneQuestPlugin.getTourHandler().startTour(player, Tour.WELCOME_TOUR.getRouteName(), () -> {
            if (player != null && player.asBukkitPlayer() != null && player.asBukkitPlayer().isOnline()) {
                EffectHandler.playAt(player.asBukkitPlayer().getLocation(), new FirstJoinEffect(player));
            }
        });

    }
}
