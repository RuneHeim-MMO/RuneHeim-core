package org.zuma.examplePlugin.domain.combat.ranged;

import org.bukkit.entity.Arrow;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.util.Vector;

public class BowVelocityHandler implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerShootBow(EntityShootBowEvent event) {
        if (!(event.getProjectile() instanceof Arrow)) return;

        Arrow arrow = (Arrow) event.getProjectile();

        // Get the shooter's facing direction (normalized)
        Vector direction = arrow.getVelocity().normalize();

        // Set the velocity to full speed (Paper/MC default max speed ~3.0)
        double fullSpeed = 3.0;

        // Override velocity to always full speed in the shoot direction
        arrow.setVelocity(direction.multiply(fullSpeed));

    }
}
