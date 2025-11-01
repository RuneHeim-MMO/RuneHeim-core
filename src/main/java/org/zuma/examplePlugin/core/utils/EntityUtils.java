package org.zuma.examplePlugin.core.utils;

import org.bukkit.entity.Entity;

public class EntityUtils {

    public static void removeEntityWithPassengers(Entity entity) {
        for (Entity passenger : entity.getPassengers()) {
            passenger.remove(); // remove passenger
        }
        entity.eject();   // eject remaining passengers (just in case)
        entity.remove();
    }
}
