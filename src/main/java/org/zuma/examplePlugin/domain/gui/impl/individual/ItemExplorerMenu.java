package org.zuma.examplePlugin.domain.gui.impl.individual;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.constants.SoundType;
import org.zuma.examplePlugin.core.handlers.game.IllegalItemWatchdog;
import org.zuma.examplePlugin.core.interfaces.Stateful;
import org.zuma.examplePlugin.core.logger.constants.LogLevel;
import org.zuma.examplePlugin.domain.gui.base.IndividualGUI;
import org.zuma.examplePlugin.domain.item.base.AbstractItemType;
import org.zuma.examplePlugin.domain.player.RunePlayer;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.UUID;

public class ItemExplorerMenu extends IndividualGUI {
    private final static int ITEM_SLOT = 13;

    private Integer itemId;
    private @Nullable UUID createdStateUUID;
    private final Inventory inventory;

    public ItemExplorerMenu(String title, int id) {
        super(title, id, 3);
        inventory = getBaseInventory();
    }


    @Override
    public void onPopulate(Map<String, String> data) {
        if (data.containsKey("itemId")) {
            this.itemId = Integer.parseInt(data.get("itemId"));
            return;
        }

        RuneQuestPlugin.getRuneLogger().log(LogLevel.WARN, "ItemExplorerMenu opened without itemId!");
    }

    @Override
    public boolean attemptOpenInventory(RunePlayer player, Runnable onFail) {
        if (itemId == null) {
            onFail.run();
            return false;
        }

        AbstractItemType type = RuneQuestPlugin.getItemRegistry().getType(itemId);

        if (type == null) {
            onFail.run();
            return false;
        }

        ItemStack stack = type.createItemStackOfType(1);
        IllegalItemWatchdog.tagIllegalItem(stack);
        inventory.setItem(ITEM_SLOT, stack);

        if (type instanceof Stateful) {
            createdStateUUID = RuneQuestPlugin.getItemStateManager().getItemStackStateUuid(stack);
        }

        SoundType.BOOK_OPEN.play(player, false);
        player.asBukkitPlayer().openInventory(inventory);
        return true;
    }

    @Override
    public void onInventoryClick(InventoryClickEvent event, int slot, RunePlayer player) {
        event.setCancelled(true);
    }

    public void onInventoryClose(@Nullable InventoryCloseEvent event, @Nullable RunePlayer player) {
        if (createdStateUUID != null) {
            RuneQuestPlugin.getItemStateManager().removeItemStateFromCache(createdStateUUID);
        }
    }

}
