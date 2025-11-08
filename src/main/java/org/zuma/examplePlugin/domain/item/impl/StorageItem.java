package org.zuma.examplePlugin.domain.item.impl;

import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.constants.Icon;
import org.zuma.examplePlugin.core.interfaces.Stateful;
import org.zuma.examplePlugin.domain.gui.constants.InventoryId;
import org.zuma.examplePlugin.domain.item.base.AbstractItemType;
import org.zuma.examplePlugin.domain.item.constants.ItemType;
import org.zuma.examplePlugin.domain.item.meta.AbstractItemMeta;
import org.zuma.examplePlugin.domain.item.meta.v1.StorageItemMeta;
import org.zuma.examplePlugin.domain.item.state.StateManager;
import org.zuma.examplePlugin.domain.item.state.StateType;
import org.zuma.examplePlugin.domain.item.state.base.AbstractState;
import org.zuma.examplePlugin.domain.item.state.meta.v1.StorageItemStateMeta;
import org.zuma.examplePlugin.domain.item.state.states.StorageItemState;
import org.zuma.examplePlugin.domain.player.RunePlayer;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

public class StorageItem extends AbstractItemType implements Stateful {
    private final InventoryId inventoryId;

    public StorageItem(AbstractItemMeta meta, StorageItemMeta storageMeta) {
        super(meta);
        super.setTypeLore("Bag", Icon.STORAGE.get());
        this.setStateType(StateType.STORAGE_ITEM_UUID_STATE);
        this.inventoryId = storageMeta.inventoryId();
    }

    @Override
    public @Nullable StateType getStateType() {
        return super.getStateType();
    }

    public AbstractState cloneDefaultStateTemplate() {
        return new StorageItemState(new StorageItemStateMeta(UUID.randomUUID()));
    }

    @Nullable
    public StorageItemState getState(ItemStack item) {
        AbstractState state = RuneQuestPlugin.getItemStateManager().getItemState(item);

        if (state != null && state.getStateType() == StateType.STORAGE_ITEM_UUID_STATE) {
            return (StorageItemState) state;
        }

        return null;
    }

    @Override
    public void onPlayerInteract(PlayerInteractEvent event, RunePlayer player, StateManager itemStateManager) {
        RuneQuestPlugin.getGUIHandler().openInventory(inventoryId.asInt(), player, null);
    }


}
