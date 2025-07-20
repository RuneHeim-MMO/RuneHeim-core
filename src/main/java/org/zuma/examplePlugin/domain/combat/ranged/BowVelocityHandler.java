package org.zuma.examplePlugin.domain.combat.ranged;

import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.util.Vector;

public class BowVelocityHandler implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerShootBow(EntityShootBowEvent event) {
        if (!(event.getEntity() instanceof Player)) return;
        if (!(event.getProjectile() instanceof Arrow arrow)) return;

        Vector originalVelocity = arrow.getVelocity();

        // Prevent zero-length vectors from being normalized
        if (originalVelocity.lengthSquared() == 0) return;

        Vector direction = originalVelocity.normalize();
        double fullSpeed = 3.0;
        Vector newVelocity = direction.multiply(fullSpeed);

        // Sanity check for finite values (optional but good for logging in debug mode)
        if (!Double.isFinite(newVelocity.getX()) || !Double.isFinite(newVelocity.getY()) || !Double.isFinite(newVelocity.getZ())) {
            return;
        }

        arrow.setVelocity(newVelocity);
    }
}
