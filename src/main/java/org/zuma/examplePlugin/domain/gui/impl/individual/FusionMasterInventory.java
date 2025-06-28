package org.zuma.examplePlugin.domain.gui.impl.individual;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.zuma.examplePlugin.core.constants.Color;
import org.zuma.examplePlugin.domain.gui.base.IndividualGUI;
import org.zuma.examplePlugin.domain.gui.constants.GUIControl;
import org.zuma.examplePlugin.domain.player.RunePlayer;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FusionMasterInventory extends IndividualGUI {
    private final ItemStack blankSlot = GUIControl.TRANSPARENT.asItemstack();
    private final static int GEAR_SLOT = 4;
    private final static int SCROLL_SLOT = 22;
    private final static Set<Integer> GEM_SLOTS = new HashSet<>(List.of(21, 23, 30, 32));

    private final static int CONFIRM_SLOT = 50;
    private final static int CANCEL_SLOT = 48;
    private final static int INFO_SLOT = 53;

    public FusionMasterInventory(String title, int id) {
        super(title, id, 6);
    }

    @Override
    public void openInventory(RunePlayer player) {
        Inventory inventory = getBaseInventory();

        for (int i = 0; i < inventory.getSize(); i++) {

            if (i == GEAR_SLOT || i == SCROLL_SLOT || GEM_SLOTS.contains(i)) {
                continue;
            }

            inventory.setItem(i, blankSlot);
        }

        ItemStack confirmItem = GUIControl.YES.asItemstack(Component.text("Confirm fusion", Color.SUCCESS.asColor()));
        super.assignMenuValue(confirmItem, 1);

        ItemStack cancelItem = GUIControl.NO.asItemstack(Component.text("Cancel", Color.ERROR.asColor()));
        super.assignMenuValue(cancelItem, 0);

        inventory.setItem(CONFIRM_SLOT, confirmItem);
        inventory.setItem(CANCEL_SLOT, cancelItem);
        inventory.setItem(INFO_SLOT, GUIControl.INFO.asItemstack(Component.text("Info", NamedTextColor.WHITE), List.of(Component.text("Some info here"))));

        player.asBukkitPlayer().openInventory(inventory);
    }

    @Override
    public void onInventoryClick(InventoryClickEvent event, int slot, RunePlayer player) {

        ItemStack currentItem = event.getCurrentItem();

        if (currentItem == null) {
            return;
        }

        Integer menuValue = getMenuValue(currentItem);

        if (menuValue != null && menuValue == 0) {
            // Todo: give player back items
            player.asBukkitPlayer().closeInventory();
        }


    }
}
