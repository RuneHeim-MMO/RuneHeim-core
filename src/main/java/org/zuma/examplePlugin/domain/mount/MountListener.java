package org.zuma.examplePlugin.domain.mount;

import net.kyori.adventure.text.Component;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDismountEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.constants.SoundType;
import org.zuma.examplePlugin.domain.hud.HudService;
import org.zuma.examplePlugin.domain.player.RunePlayer;

public class MountListener implements Listener {
    private final MountHandler handler;

    public MountListener(MountHandler handler) {
        this.handler = handler;
    }

    @EventHandler
    public void onDismount(EntityDismountEvent event) {
        Entity entity = event.getEntity(); // the one dismounting
        Entity mount = event.getDismounted(); // the one being dismounted

        // If a player dismounts from a horse, remove all passengers from the horse/camel
        if (entity instanceof Player && mount instanceof AbstractHorse horse) {
            if (horse.getOwner().equals(entity)) {
                horse.getPassengers().forEach(horse::removePassenger);
            }

            // Set slow speed for a mount after dismount so it doesn't run away
            AttributeInstance speedAttr = horse.getAttribute(Attribute.MOVEMENT_SPEED);
            if (speedAttr != null) {

                if (horse instanceof Camel) {
                    // Camels have weird speed values so set to a lower value
                    speedAttr.setBaseValue(0.1);
                } else {
                    speedAttr.setBaseValue(0.2);
                }

            }
        }

    }


    @EventHandler
    public void onHorseRightClick(PlayerInteractEntityEvent event) {
        if (!(event.getRightClicked() instanceof AbstractHorse horse)) return;
        Player player = event.getPlayer();
        RunePlayer runePlayer = RuneQuestPlugin.getPlayerManager().getRunePlayer(player);

        if (runePlayer == null) return;

        event.setCancelled(true);

        if (!horse.isTamed() || horse.getOwner() == null) {
            return;
        }

        if (horse instanceof Camel) {
            // Remove any non-owner passengers (secondary check)
            if (!horse.getPassengers().isEmpty() && horse.getPassengers().getFirst().getUniqueId() != horse.getOwner().getUniqueId()) {
                horse.removePassenger(horse.getPassengers().getFirst());
            }

            // If the camel is already being ridden by its owner, add a second passenger
            if (!horse.getPassengers().isEmpty() && horse.getPassengers().getFirst().getUniqueId() == horse.getOwner().getUniqueId()) {
                if (horse.getPassengers().size() < 2) {
                    horse.addPassenger(player);
                    SoundType.MOUNT_SADDLE.play(player, true);
                }
                return;

            }

            // If the camel is not being ridden, only allow the owner to ride it
            if (horse.getOwner() == player) {
                horse.addPassenger(player);
                SoundType.MOUNT_SADDLE.play(player, true);

                // Restore speed attribute
                RuneQuestPlugin.getMountHandler().restoreMountAttributes(runePlayer, horse);
            } else {
                HudService.sendFailMessage(runePlayer, Component.text("This mount belongs to someone else."));
            }

            return;
        }


        // Handle all other rideables
        if (horse.getOwner() == player) {
            horse.addPassenger(player);
            SoundType.MOUNT_SADDLE.play(player, true);

            // Restore speed attribute
            RuneQuestPlugin.getMountHandler().restoreMountAttributes(runePlayer, horse);
        } else {
            HudService.sendFailMessage(runePlayer, Component.text("This mount belongs to someone else."));
        }
    }

    @EventHandler
    public void onPlayerDisconnect(PlayerQuitEvent event) {
        handler.clearMountInstances(event.getPlayer().getUniqueId());
    }

    @EventHandler
    public void onHorseDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof AbstractHorse horse && horse.getOwner() != null) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onHorseDamageByEntity(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof AbstractHorse horse && horse.getOwner() != null) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Inventory inv = event.getClickedInventory();
        if (inv == null) return;

        InventoryHolder holder = inv.getHolder();
        if (holder instanceof Horse || holder instanceof Llama) {
            event.setCancelled(true);
        }

        // Camels don't use HorseInventory, so check entity directly
        if (holder instanceof Camel) {
            event.setCancelled(true);
        }
    }

}
