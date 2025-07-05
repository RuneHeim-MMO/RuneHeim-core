package org.zuma.examplePlugin.domain.fairy;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.domain.item.base.AbstractItemType;
import org.zuma.examplePlugin.domain.item.impl.fairy.Fairy;
import org.zuma.examplePlugin.domain.item.impl.fairy.FairyFruit;
import org.zuma.examplePlugin.domain.player.RunePlayer;

public class FairyListener implements Listener {
    private final FairyHandler handler;

    public FairyListener(FairyHandler handler) {
        this.handler = handler;
    }

    @EventHandler
    public void onClickOnFairyWithFruit(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player player)) return;

        Inventory clickedInventory = event.getClickedInventory();
        if (clickedInventory == null || !clickedInventory.equals(player.getInventory())) {
            return;
        }

        RunePlayer runePlayer = RuneQuestPlugin.getPlayerManager().getRunePlayer(player);

        if (runePlayer == null) {
            return;
        }

        ItemStack clickedOn = event.getCurrentItem();
        ItemStack cursor = event.getCursor();

        AbstractItemType clickedAbstract = RuneQuestPlugin.getItemRegistry().getItem(clickedOn);
        AbstractItemType cursorAbstract = RuneQuestPlugin.getItemRegistry().getItem(cursor);

        if (clickedAbstract instanceof Fairy fairy && cursorAbstract instanceof FairyFruit fruit) {
            event.setCancelled(true);
            handler.handleFruitClickOnFairy(event, runePlayer, fairy, fruit, clickedOn, cursor);

        }


    }
}
