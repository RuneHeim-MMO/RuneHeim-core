package org.zuma.examplePlugin.core.handlers;

import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class FireworkDamageDisabler implements Listener {

    @EventHandler
    public void onFireworkDamage(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Firework && event.getEntity() instanceof Player) {
            event.setCancelled(true);
        }
    }

}
