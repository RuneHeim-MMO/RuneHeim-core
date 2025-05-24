package org.zuma.examplePlugin.domain.item.base;

import com.google.gson.Gson;
import net.kyori.adventure.text.Component;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.zuma.examplePlugin.constants.Colors;
import org.zuma.examplePlugin.domain.item.interfaces.WithMeta;
import org.zuma.examplePlugin.domain.item.meta.AbstractItemMeta;
import org.zuma.examplePlugin.domain.item.meta.QuestItemMeta;
import org.zuma.examplePlugin.domain.item.state.StateManager;
import org.zuma.examplePlugin.domain.player.RunePlayer;

public class QuestItem extends AbstractItem implements WithMeta {
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
        QuestItemMeta deserialized = new Gson().fromJson(this.jsonMeta, QuestItemMeta.class);
        questName = deserialized.questName();
    }


}
