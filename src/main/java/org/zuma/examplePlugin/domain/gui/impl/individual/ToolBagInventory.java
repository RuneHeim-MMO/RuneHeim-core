package org.zuma.examplePlugin.domain.gui.impl.individual;

import net.kyori.adventure.text.Component;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.domain.gui.base.StorageInventory;
import org.zuma.examplePlugin.domain.gui.constants.GUIControl;
import org.zuma.examplePlugin.domain.hud.HudService;
import org.zuma.examplePlugin.domain.item.base.AbstractItemType;
import org.zuma.examplePlugin.domain.player.RunePlayer;
import org.zuma.examplePlugin.domain.storage.RepositoryQueryLockController;
import org.zuma.examplePlugin.domain.storage.constants.StorageCategory;
import org.zuma.examplePlugin.domain.storage.constants.StorageType;

import java.util.Set;

public class ToolBagInventory extends StorageInventory {
    private final StorageCategory storageCategory;

    public ToolBagInventory(String title, int id, StorageCategory storageCategory, RepositoryQueryLockController repositoryQueryLockController) {
        super(title, id, 6, 1, StorageType.ITEM_STORAGE, repositoryQueryLockController, Set.of(0, 1, 2, 6, 7, 8, 9, 10, 11, 12, 16, 17, 18, 19, 20, 21, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53));
        setHideGUIControls(true);
        this.storageCategory = storageCategory;
    }

    @Override
    public int getPaginationControlsOffset() {
        return 0;
    }

    @Override
    public void onInventoryClick(InventoryClickEvent event, int slot, RunePlayer player) {
        InventoryView view = event.getView();
        Inventory topInv = view.getTopInventory();
        Inventory bottomInv = view.getBottomInventory();
        Inventory clickedInv = event.getClickedInventory();
        ItemStack current = event.getCurrentItem();
        InventoryAction action = event.getAction();
        ItemStack cursor = event.getCursor();

        if (clickedInv == null) return;

        if (GUIControl.isGuiItem(current)) {
            event.setCancelled(true);
            return;
        }

        // Allow normal inventory reorganization
        if (clickedInv.equals(bottomInv) && action != InventoryAction.MOVE_TO_OTHER_INVENTORY) {
            return;
        }

        // Handle shift-click from player → top
        if (clickedInv.equals(bottomInv) && action == InventoryAction.MOVE_TO_OTHER_INVENTORY) {
            AbstractItemType type = RuneQuestPlugin.getItemRegistry().getType(current);
            if (type == null || !(storageCategory.isItemTypeInCategory(type.type))) {
                handleCancellation(player, event);
                return;
            }
        }

        // Handle manual placing via cursor into top inventory
        if (clickedInv.equals(topInv) && !cursor.getType().isAir()) {
            AbstractItemType type = RuneQuestPlugin.getItemRegistry().getType(cursor);
            if (type == null || !(storageCategory.isItemTypeInCategory(type.type))) {
                handleCancellation(player, event);
            }
        }
    }

    private void handleCancellation(RunePlayer player, InventoryClickEvent event) {
        HudService.sendFailMessage(player, Component.text("Only " + storageCategory.getDisplayName() + " can be stored in this container."));
        event.setCancelled(true);
    }
}
