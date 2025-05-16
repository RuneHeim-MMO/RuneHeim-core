package org.zuma.examplePlugin.item;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;
import org.zuma.examplePlugin.constants.Colors;
import org.zuma.examplePlugin.interfaces.WithRequirements;
import org.zuma.examplePlugin.item.base.CustomItem;
import org.zuma.examplePlugin.item.base.GearItem;
import org.zuma.examplePlugin.item.events.PlayerArmorEquipEvent;
import org.zuma.examplePlugin.player.RunePlayer;
import org.zuma.examplePlugin.player.RunePlayerManager;
import org.zuma.examplePlugin.utils.PlayerUtils;

import java.util.function.BiConsumer;

public class ItemListener implements Listener {
    private final RunePlayerManager playerHandler;
    private final ItemHandler itemHandler;
    private final ItemRegistry itemRegistry;

    public ItemListener(ItemHandler handler, RunePlayerManager playerHandler) {
        this.itemHandler = handler;
        this.playerHandler = playerHandler;

        itemRegistry = handler.getRegistry();
    }

    private void withItemAndPlayer(Player bukkitPlayer, ItemStack itemStack, BiConsumer<CustomItem, RunePlayer> consumer) {
        if (itemStack == null || itemStack.getType().isAir()) return;

        CustomItem item = itemRegistry.getItem(itemStack);
        if (item == null) return;

        RunePlayer player = playerHandler.getRunePlayer(bukkitPlayer);
        if (player == null) return;

        consumer.accept(item, player);
    }

    private void requirementsFailCancelEvent(Cancellable event, CustomItem item, RunePlayer player) {
        player.getPlayer().sendMessage(Component.text("You do not meet requirements to use " + item.displayName + ".").color(Colors.ERROR.asColor()));
        event.setCancelled(true);
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        withItemAndPlayer(event.getPlayer(), PlayerUtils.getItemStackInHand(event.getPlayer()), (item, player) -> {
            if (!PlayerUtils.playerMeetsRequirements(player, item)) {
                requirementsFailCancelEvent(event, item, player);
                return;
            }

            if (item instanceof GearItem) {
                if (event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_AIR) {
                    PlayerArmorEquipEvent newEvent = new PlayerArmorEquipEvent(player, item);
                    Bukkit.getServer().getPluginManager().callEvent(newEvent);

                    if (newEvent.isCancelled()) {
                        event.setCancelled(true);
                        return;
                    }
                }
            }

            itemHandler.onPlayerInteract(event, item, player);
        });
    }

    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
        withItemAndPlayer(event.getPlayer(), PlayerUtils.getItemStackInHand(event.getPlayer()), (item, player) -> {
            if (!PlayerUtils.playerMeetsRequirements(player, item)) {
                requirementsFailCancelEvent(event, item, player);
                return;
            }

            itemHandler.onPlayerInteractEntity(event, item, player);
        });

    }

    @EventHandler
    public void onPlayerItemDrop(PlayerDropItemEvent event) {
        withItemAndPlayer(event.getPlayer(), event.getItemDrop().getItemStack(), (item, player) -> {
            itemHandler.onPlayerItemDrop(event, item, player);
        });
    }

    @EventHandler
    public void onPlayerTakeItemInHand(PlayerItemHeldEvent event) {
        withItemAndPlayer(event.getPlayer(), event.getPlayer().getInventory().getItem(event.getNewSlot()), (item, player) -> {
            itemHandler.onPlayerTakeItemInHand(event, item, player);
        });
    }

    @EventHandler
    public void onPlayerDamageEntity(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player damager)) {
            return;
        }

        withItemAndPlayer(damager, PlayerUtils.getItemStackInHand(damager), (item, player) -> {
            if (!PlayerUtils.playerMeetsRequirements(player, item)) {
                requirementsFailCancelEvent(event, item, player);
                return;
            }

            itemHandler.onPlayerDamageEntity(event, player, item);
        });
    }

    @EventHandler
    public void onPlayerWearItem(PlayerArmorEquipEvent event) {
        itemHandler.onPlayerWearItem(event, event.getPlayer());
    }


    @EventHandler
    public void onBaseBukkitWearArmorClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player whoClicked)) {
            return;
        }

        withItemAndPlayer(whoClicked, event.getCursor(), (item, player) -> {
            if (!(item instanceof WithRequirements)) {
                return;
            }

            int rawSlot = event.getRawSlot();

            if (event.getClickedInventory() != whoClicked.getInventory()) {
                return;
            }

            // Armor slots
            if (!(rawSlot >= 5 && rawSlot <= 8)) {
                return;
            }

            if (!PlayerUtils.playerMeetsRequirements(player, item)) {
                requirementsFailCancelEvent(event, item, player);
                return;
            }

            PlayerArmorEquipEvent newEvent = new PlayerArmorEquipEvent(player, item);
            Bukkit.getServer().getPluginManager().callEvent(newEvent);

            if (newEvent.isCancelled()) {
                event.setCancelled(true);
                return;
            }
        });
    }

    @EventHandler
    public void onBaseBukkitWearShiftArmorClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player whoClicked)) {
            return;
        }

        withItemAndPlayer(whoClicked, event.getCurrentItem(), (item, player) -> {
            if (!(item instanceof WithRequirements)) {
                return;
            }

            if (player == null) {
                return;
            }

            if (event.getClickedInventory() != whoClicked.getInventory()) {
                return;
            }

            if (!event.getClick().isShiftClick()) {
                return;
            }

            if (!PlayerUtils.playerMeetsRequirements(player, item)) {
                requirementsFailCancelEvent(event, item, player);
                return;
            }

            PlayerArmorEquipEvent newEvent = new PlayerArmorEquipEvent(player, item);
            Bukkit.getServer().getPluginManager().callEvent(newEvent);

            if (newEvent.isCancelled()) {
                event.setCancelled(true);
                return;
            }
        });
    }
}
