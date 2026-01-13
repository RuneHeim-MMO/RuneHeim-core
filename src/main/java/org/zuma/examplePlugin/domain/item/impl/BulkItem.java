package org.zuma.examplePlugin.domain.item.impl;

import net.kyori.adventure.text.Component;
import org.bukkit.event.player.PlayerInteractEvent;
import org.zuma.examplePlugin.core.constants.Color;
import org.zuma.examplePlugin.core.constants.Icon;
import org.zuma.examplePlugin.core.constants.SoundType;
import org.zuma.examplePlugin.core.utils.PlayerUtils;
import org.zuma.examplePlugin.domain.hud.HudService;
import org.zuma.examplePlugin.domain.item.ItemRecord;
import org.zuma.examplePlugin.domain.item.base.AbstractItemType;
import org.zuma.examplePlugin.domain.item.constants.CustomModelId;
import org.zuma.examplePlugin.domain.item.meta.AbstractItemMeta;
import org.zuma.examplePlugin.domain.item.state.StateManager;
import org.zuma.examplePlugin.domain.player.RunePlayer;

import java.util.ArrayList;
import java.util.List;

public class BulkItem extends AbstractItemType {
    private List<ItemRecord> items = new ArrayList<>();

    public BulkItem(AbstractItemMeta meta) {
        super(meta);
        super.setTypeLore("Bulk Item", Icon.CHEST.get());
    }

    public void setItems(List<ItemRecord> items) {
        this.items = items;
    }

    public void setCustomModelData(CustomModelId modelId) {
        super.setCustomModelData(modelId);
    }


    @Override
    public void onPlayerInteract(PlayerInteractEvent event, RunePlayer player, StateManager itemStateManager) {
        boolean success = PlayerUtils.subtractOneFromMainHand(player);

        if (!success) {
            HudService.sendFailMessage(player, Component.text("Failed to consume item from your hand!", Color.ERROR.asColor()));
            return;
        }

        SoundType.BULK_ITEM_OPEN.play(player, true);
        items.forEach(player::giveItemOrDrop);
    }

    public List<ItemRecord> getItems() {
        return items;
    }
}
