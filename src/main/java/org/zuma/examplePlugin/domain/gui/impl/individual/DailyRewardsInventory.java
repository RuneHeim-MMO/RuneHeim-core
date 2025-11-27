package org.zuma.examplePlugin.domain.gui.impl.individual;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.zuma.examplePlugin.core.constants.SoundType;
import org.zuma.examplePlugin.domain.gui.base.IndividualGUI;
import org.zuma.examplePlugin.domain.player.RunePlayer;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class DailyRewardsInventory extends IndividualGUI {
    private static final List<Integer> AVAILABLE_SLOTS = Arrays.asList(
            6, 7, 8, 15, 16, 17, 24, 25, 26, 33, 34, 35, 42, 43, 44
    );

    public DailyRewardsInventory(String title, int id) {
        super(title, id, 6);
    }

    private void updateInventory(RunePlayer player, Inventory inventory) {
        Iterator<Integer> slotIterator = AVAILABLE_SLOTS.iterator();
      /*  for (Prayer prayer : Prayer.values()) {
            if (!slotIterator.hasNext()) {
                break;
            }

            int slot = slotIterator.next();

            ItemStack button = new ItemStack(Material.AIR);
            inventory.setItem(slot, button);
        }*/
    }

    @Override
    public boolean attemptOpenInventory(RunePlayer player, Runnable onFail) {
        Inventory inventory = getBaseInventory();
        updateInventory(player, inventory);

        player.asBukkitPlayer().openInventory(inventory);
        return true;
    }


    @Override
    public void onInventoryClick(InventoryClickEvent event, int slot, RunePlayer player) {
        event.setCancelled(true);

        ItemStack currentItem = event.getCurrentItem();

        if (currentItem == null) {
            return;
        }

        Integer menuValue = getMenuValue(currentItem);

        if (menuValue == null) return;


    }

}
