package org.zuma.examplePlugin.domain.gui.impl.individual.house;

import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.Rotatable;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.constants.SoundType;
import org.zuma.examplePlugin.core.utils.TextUtils;
import org.zuma.examplePlugin.domain.gui.base.IndividualGUI;
import org.zuma.examplePlugin.domain.gui.constants.GUIControl;
import org.zuma.examplePlugin.domain.gui.constants.GUIOverlay;
import org.zuma.examplePlugin.domain.housing.HousingManager;
import org.zuma.examplePlugin.domain.housing.instance.HouseInstance;
import org.zuma.examplePlugin.domain.housing.records.HouseFurnitureEntry;
import org.zuma.examplePlugin.domain.housing.records.HouseSlot;
import org.zuma.examplePlugin.domain.hud.HudService;
import org.zuma.examplePlugin.domain.item.base.AbstractItemType;
import org.zuma.examplePlugin.domain.player.RunePlayer;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class HouseSlotEditor extends IndividualGUI {
    private HouseInstance instance = null;

    public HouseSlotEditor(String title, int id) {
        super(title, id, 1);

    }


    @Override
    public boolean attemptOpenInventory(RunePlayer player, Runnable onFail) {
        HouseInstance instance = RuneQuestPlugin.getHousingManager().getVisitingHouse(player);

        if (instance == null || !instance.getOwnerUUID().equals(player.getUUID())) {
            onFail.run();
            return false;
        }

        this.instance = instance;

        Integer slotIndex = instance.getEditManager().getEditingSlotIndex();

        if (slotIndex == null) {
            onFail.run();
            return false;
        }

        HouseSlot editingSlot = instance.getEditManager().getSlotAtIndex(slotIndex);

        if (editingSlot == null) {
            onFail.run();
            return false;
        }

        HouseFurnitureEntry furnitureEntry = RuneQuestPlugin.getHousingManager().getInstanceFurniture(instance.getInstanceUUID()).get(slotIndex);

        if (furnitureEntry == null) {
            onFail.run();
            return false;
        }

        AbstractItemType itemType = RuneQuestPlugin.getItemRegistry().getType(furnitureEntry.itemId());

        if (itemType == null) {
            onFail.run();
            return false;
        }

        Location location = HousingManager.getLocationInHouse(editingSlot.absoluteLocation(), instance);
        boolean isRotateable = location.getBlock().getBlockData() instanceof Directional || location.getBlock().getBlockData() instanceof Rotatable;

        setTitle(GUIOverlay.HOUSE_SLOT_EDITOR_OVERLAY.asGlyph() + "§fEditing Slot #" + slotIndex + " (" + TextUtils.hexToMc(itemType.rarity.getHexColor()) + itemType.displayName + "§f)");
        Inventory inventory = getBaseInventory();


        ItemStack removeButton = GUIControl.HOUSE_REMOVE_FURNITURE.asItemstack(Component.text("Remove Furniture"));
        assignMenuValue(removeButton, "remove");
        inventory.setItem(8, removeButton);

        if (isRotateable) {
            ItemStack rotateButton = GUIControl.HOUSE_ROTATE_FURNITURE.asItemstack(Component.text("Rotate Furniture"));
            assignMenuValue(rotateButton, "rotate");
            inventory.setItem(0, rotateButton);
        }


        player.asBukkitPlayer().openInventory(inventory);
        return true;
    }


    @Override
    public void onInventoryClick(InventoryClickEvent event, int slot, RunePlayer player) {
        event.setCancelled(true);

        ItemStack currentItem = event.getCurrentItem();
        Integer editingSlotIndex = instance.getEditManager().getEditingSlotIndex();

        if (currentItem == null || instance == null || editingSlotIndex == null) {
            return;
        }

        String menuValue = getMenuValueStr(currentItem);

        if (menuValue == null) {
            return;
        }

        SoundType.UI_CLICK.play(player, false);

        if (menuValue.equals("rotate")) {
            try {
                instance.getEditManager().rotateFurnitureItem(player.asBukkitPlayer(), editingSlotIndex);
            } catch (Exception e) {
                HudService.sendFailMessage(player, Component.text(e.getMessage()));
            }
            return;
        }

        if (menuValue.equals("remove")) {
            try {
                instance.getEditManager().removeFurnitureItem(player.asBukkitPlayer(), editingSlotIndex);
                player.asBukkitPlayer().closeInventory();
            } catch (Exception e) {
                HudService.sendFailMessage(player, Component.text(e.getMessage()));
            }
            return;
        }
    }

}
