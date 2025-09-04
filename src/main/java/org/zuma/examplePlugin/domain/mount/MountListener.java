package org.zuma.examplePlugin.domain.mount;

import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.HorseInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.domain.fairy.FairyHandler;
import org.zuma.examplePlugin.domain.item.base.AbstractItemType;
import org.zuma.examplePlugin.domain.item.impl.fairy.Fairy;
import org.zuma.examplePlugin.domain.item.impl.fairy.FairyFruit;
import org.zuma.examplePlugin.domain.player.RunePlayer;

public class MountListener implements Listener {
    private final MountHandler handler;

    public MountListener(MountHandler handler) {
        this.handler = handler;
    }

    @EventHandler
    public void onHorseRightClick(PlayerInteractEntityEvent event) {
        if (!(event.getRightClicked() instanceof Horse horse)) return;
        Player player = event.getPlayer();

        event.setCancelled(true);

        if (horse.isTamed() && horse.getOwner() == player) {
            horse.addPassenger(player);
        }
    }

    @EventHandler
    public void onPlayerDisconnect(PlayerQuitEvent event) {
        handler.clearMountInstances(event.getPlayer().getUniqueId());
    }

    @EventHandler
    public void onHorseDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Horse horse && horse.getOwner() != null) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onHorseDamageByEntity(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Horse horse && horse.getOwner() != null) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Inventory inv = event.getClickedInventory();
        if (inv instanceof HorseInventory) {
            event.setCancelled(true);
        }
    }

}
