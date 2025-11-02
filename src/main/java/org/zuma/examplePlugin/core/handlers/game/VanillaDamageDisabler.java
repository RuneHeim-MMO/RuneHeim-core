package org.zuma.examplePlugin.core.handlers.game;

import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.EnumSet;

public class VanillaDamageDisabler implements Listener {
    private final EnumSet<EntityDamageEvent.DamageCause> IGNORED_CAUSES =
            EnumSet.of(
                    EntityDamageEvent.DamageCause.FALL,
                    EntityDamageEvent.DamageCause.SUFFOCATION,
                    EntityDamageEvent.DamageCause.LAVA,
                    EntityDamageEvent.DamageCause.FIRE,
                    EntityDamageEvent.DamageCause.FIRE_TICK,
                    EntityDamageEvent.DamageCause.CAMPFIRE,
                    EntityDamageEvent.DamageCause.DROWNING,
                    EntityDamageEvent.DamageCause.FLY_INTO_WALL,
                    EntityDamageEvent.DamageCause.WITHER
                    );

    @EventHandler
    public void onFireworkDamage(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Firework && event.getEntity() instanceof Player) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = false)
    public void onIgnoredDamage(EntityDamageEvent event) {
        if (IGNORED_CAUSES.contains(event.getCause())) {
            event.setCancelled(true);
        }

    }


}
