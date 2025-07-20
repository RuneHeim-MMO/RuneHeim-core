package org.zuma.examplePlugin.domain.combat.melee;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.zuma.examplePlugin.core.utils.ItemUtils;
import org.zuma.examplePlugin.core.utils.PlayerUtils;
import org.zuma.examplePlugin.domain.cooldown.CooldownHandler;
import org.zuma.examplePlugin.domain.item.constants.WeaponSpeed;

import javax.annotation.Nullable;

public class WeaponSpeedHandler implements Listener {
    private final CooldownHandler weaponSpeedHandler;

    public WeaponSpeedHandler() {
        this.weaponSpeedHandler = new CooldownHandler();
    }


    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerDamageEntity(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player damager)) {
            return;
        }

        if (weaponSpeedHandler.isCooledDown(damager)) {
            event.setCancelled(true);
            return;
        }

        @Nullable WeaponSpeed speed = ItemUtils.getWeaponSpeed(PlayerUtils.getItemStackInHand(damager));
        weaponSpeedHandler.startCooldown(damager, speed == null ? WeaponSpeed.NORMAL.getMeleeDelay() : speed.getMeleeDelay());

    }

}
