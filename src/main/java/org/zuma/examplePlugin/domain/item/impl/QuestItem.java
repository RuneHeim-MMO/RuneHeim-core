package org.zuma.examplePlugin.domain.item.impl;

import com.google.gson.Gson;
import net.kyori.adventure.text.Component;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.zuma.examplePlugin.core.constants.Colors;
import org.zuma.examplePlugin.core.interfaces.Serializable;
import org.zuma.examplePlugin.domain.item.base.AbstractItem;
import org.zuma.examplePlugin.domain.item.meta.AbstractItemMeta;
import org.zuma.examplePlugin.domain.item.meta.QuestMeta;
import org.zuma.examplePlugin.domain.item.state.StateManager;
import org.zuma.examplePlugin.domain.player.RunePlayer;

public class QuestItem extends AbstractItem implements Serializable {
    private String questName;

    public QuestItem(AbstractItemMeta meta) {
        super(meta);
        this.appendToLore(Component.text("Quest Item").color(Colors.ITEM_TYPE.asColor()));
    }

    @Override
    public void onPlayerItemDrop(PlayerDropItemEvent event, RunePlayer player, StateManager itemStateManager) {
        event.setCancelled(true);
        player.getPlayer().sendMessage(Component.text("You cannot drop this item. It is associated with " + questName + " quest.").color(Colors.ERROR.asColor()));
    }

    public void deserializeAndApplyMeta() {
        QuestMeta deserialized = new Gson().fromJson(this.jsonMeta, QuestMeta.class);
        questName = deserialized.questName();
    }


}
