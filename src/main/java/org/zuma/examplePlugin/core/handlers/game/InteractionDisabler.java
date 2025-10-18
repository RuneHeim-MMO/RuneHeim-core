package org.zuma.examplePlugin.core.handlers.game;

import net.kyori.adventure.text.Component;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Sheep;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;
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
import org.zuma.examplePlugin.domain.player.RunePlayer;

import java.util.Locale;

public class InteractionDisabler implements Listener {
    private static final String[] WHITELISTED_COMMANDS = {
            "/mount", "/pouch", "/skills",

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

            // STAFF_MESSAGING
            "/broadcast",

            // STAFF_ITEM_SPAWN
            "/gamemode",
            "/clear",
            "/invsee",

            // STAFF_MODERATION
            "/joinfullserver",
            "/kick",
            "/socialspy",
            "/time",
            "/list",
            "/ban",

            // PLAYER_DEFAULTS
            "/msg",
            "/chat",
            "/r"
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
        RunePlayer player = RuneQuestPlugin.getPlayerManager().getRunePlayer(event.getPlayer());

        if (player != null && player.getRole().hasPermission(org.zuma.examplePlugin.core.permissions.Permission.WORLD_MANIPULATION) && event.getPlayer().getGameMode() == GameMode.CREATIVE) {
            return;
        }

        event.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onPutIntoOffhandSlot(InventoryClickEvent event) {
        if (event.getSlotType() == InventoryType.SlotType.QUICKBAR && event.getSlot() == 40) {
            event.setCancelled(true);
            return;
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

        // Cancel health regen for non-player entities
        if (!(event.getEntity() instanceof Player)) {
            event.setCancelled(true);
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
