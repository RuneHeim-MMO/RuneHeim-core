package org.zuma.examplePlugin.domain.item.base;

import com.google.gson.Gson;
import net.kyori.adventure.text.Component;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.zuma.examplePlugin.constants.Colors;
import org.zuma.examplePlugin.domain.item.interfaces.WithAdditionalMeta;
import org.zuma.examplePlugin.domain.player.RunePlayer;

public class QuestItem extends CustomItem implements WithAdditionalMeta {
    private String questName;

    public QuestItem(CustomItemMeta meta) {
        super(meta);
        this.appendToLore(Component.text("Quest Item").color(Colors.ITEM_TYPE.asColor()));
    }

    @Override
    public void onPlayerItemDrop(PlayerDropItemEvent event, RunePlayer player) {
        event.setCancelled(true);
        player.getPlayer().sendMessage(Component.text("You cannot drop this item. It is associated with " + questName + " quest.").color(Colors.ERROR.asColor()));
    }

    public String serializeMeta() {
        return new Gson().toJson(new QuestItemMeta(questName));
    }

    public void deserializeAndApplyMeta(String meta) {
        QuestItemMeta deserialized = new Gson().fromJson(meta, QuestItemMeta.class);
        questName = deserialized.questName();
    }


}
