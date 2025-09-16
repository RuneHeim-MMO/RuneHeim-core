package org.zuma.examplePlugin.core.handlers.game;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.zuma.examplePlugin.domain.effect.EffectHandler;
import org.zuma.examplePlugin.domain.effect.effects.FirstJoinEffect;
import org.zuma.examplePlugin.domain.player.RunePlayer;
import org.zuma.examplePlugin.events.player.RunePlayerLoadedEvent;

public class FirstJoinTutorialTeleporter implements Listener {

    @EventHandler
    public void onPlayerFirstJoin(RunePlayerLoadedEvent event) {
        if (!event.isFirstTimeJoin()) {
            return;
        }


        RunePlayer player = event.getPlayer();
        player.asBukkitPlayer().teleport(new Location(Bukkit.getWorld("world"), 2435.6, 155, 2465.8, 25, 7));
        EffectHandler.playAt(player.asBukkitPlayer().getLocation(), new FirstJoinEffect(player));
    }
}
