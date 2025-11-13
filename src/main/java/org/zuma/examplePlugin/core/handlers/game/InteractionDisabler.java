package org.zuma.examplePlugin.core.handlers.game;

import net.kyori.adventure.text.Component;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.Cancellable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.*;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.constants.Color;
import org.zuma.examplePlugin.core.logger.constants.LogLevel;
import org.zuma.examplePlugin.core.utils.PlayerUtils;
import org.zuma.examplePlugin.domain.player.RunePlayer;
import org.zuma.examplePlugin.domain.player.RunePlayerManager;

import java.util.Locale;

public class InteractionDisabler implements Listener {
    private static final String[] WHITELISTED_COMMANDS = {
            // STAFF_MOVEMENT
            "/fly",
            "/back",
            "/vanish",
            "/speed",
            "/tp",
            "/tpall",
            "/tphere",
            "/tppos",
            "/warp",

            // STAFF_ITEM_SPAWN
            "/gamemode",
            "/clear",
            "/invsee",

            // STAFF_MODERATION
            "/ban",
            "/kick",
            "/mute",
            "/tempmute",
            "/ipban",
            "/ipmute",
            "/tempban",
            "/warn",
            "/tempmute",
            "/history",
            "/checkban",
            "/checkmute",
            "/checkwarn",
            "/warnlist",


            // PLAYER_DEFAULTS
            "/msg",
            "/chat",
            "/r",
            "/mount",
            "/pouch",
            "/skills",
            "/stuck",
            "/party",
            "/p",
            "/megaphone",
            "/bombs",

            // Social Media & Links
            "/store",
            "/shop",
            "/donate",
            "/discord",
            "/website",
            "/site",
            "/rules",
            "/help",
            "/twitter",
            "/x",
            "/youtube",
            "/support",
            "/map",
    };

    public void disableTourViewPermission() {
        if (RuneQuestPlugin.getInstance().getServer().getPluginManager().getPermission("servertours.commands.play") == null) {
            RuneQuestPlugin.getInstance().getServer().getPluginManager().addPermission(new Permission(
                    "servertours.commands.play",
                    "Blocked to prevent players from using /tour play",
                    PermissionDefault.FALSE
            ));
        }
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onPlayerInteractOffhand(PlayerInteractEvent event) {
        if (event.getHand() == EquipmentSlot.OFF_HAND) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onSwapHandItems(PlayerSwapHandItemsEvent event) {
        event.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onPlayerJoin(PlayerJoinEvent event) {
        event.getPlayer().getInventory().setItemInOffHand(null);
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onPlayerInteract(PlayerInteractEvent event) {
        boolean isAllowed = PlayerUtils.canPlayerManipulateWorld(event.getPlayer());

        if (isAllowed) return;

        event.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onPutIntoOffhandSlot(InventoryClickEvent event) {
        if (event.getSlotType() == InventoryType.SlotType.QUICKBAR && event.getSlot() == 40) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onInventoryCloseWithOffhandItem(InventoryCloseEvent event) {
        if (!(event.getPlayer() instanceof Player player)) return;

        ItemStack offhand = player.getInventory().getItemInOffHand();

        if (!offhand.getType().isAir()) {
            ItemStack cloned = offhand.clone();
            player.getInventory().setItemInOffHand(null);
            player.getWorld().dropItem(player.getLocation(), cloned);
        }
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onSheepShear(PlayerShearEntityEvent event) {
        if (event.getEntity() instanceof Sheep) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onFarmlandTrample(EntityChangeBlockEvent event) {
        if (event.getBlock().getType() == Material.FARMLAND && event.getTo() == Material.DIRT) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onEntityRegainHealth(EntityRegainHealthEvent event) {
        if (!(event.getEntity() instanceof LivingEntity)) return;
        event.setCancelled(true);
    }

    // Handle right-click interactions for armor stands
    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
        handleArmorStandInteraction(event.getRightClicked(), event.getPlayer(), event);
    }

    // Handle armor stand specific equipment changes
    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onPlayerInteractAtEntity(PlayerInteractAtEntityEvent event) {
        handleArmorStandInteraction(event.getRightClicked(), event.getPlayer(), event);
    }

    private void handleArmorStandInteraction(Entity entity, Player player, Cancellable event) {
        if (entity instanceof ArmorStand) {
            boolean isAllowed = PlayerUtils.canPlayerManipulateWorld(player);

            if (!isAllowed) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onItemFrameDamage(EntityDamageByEntityEvent event) {
        Entity entity = event.getEntity();

        // Check if the damaged entity is an item frame
        if (entity instanceof ItemFrame) {
            // If damaged by a player
            if (event.getDamager() instanceof Player player) {
                boolean isAllowed = PlayerUtils.canPlayerManipulateWorld(player);

                // If NOT allowed, cancel the damage
                if (!isAllowed) {
                    event.setCancelled(true);
                }
            } else {
                // Cancel damage from non-players (mobs, arrows, etc.)
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent event) {
        RunePlayer player = RuneQuestPlugin.getPlayerManager().getRunePlayer(event.getPlayer());

        if (player == null) {
            event.setCancelled(true);
            return;
        }

        if (player.getRole().hasPermission(org.zuma.examplePlugin.core.permissions.Permission.BLOCKED_CMD_BYPASS)) {
            return;
        }

        String msg = event.getMessage();
        String cmd = msg.split("\\s+")[0].toLowerCase(Locale.ROOT);

        boolean found = false;
        for (String enabled : WHITELISTED_COMMANDS) {
            if (cmd.equalsIgnoreCase(enabled)) {
                found = true;
                break;
            }

        }

        if (!found) {
            event.setCancelled(true);
            player.asBukkitPlayer().sendMessage(Component.text("You do not have access to that command.", Color.ERROR.asColor()));
            RuneQuestPlugin.getRuneLogger().log(LogLevel.WARN, "Blocked command attempt: " + event.getPlayer().getName() + " tried to use command: " + cmd);
        }

    }

}
