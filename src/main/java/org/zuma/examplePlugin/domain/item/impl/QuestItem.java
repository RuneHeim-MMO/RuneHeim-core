package org.zuma.examplePlugin.domain.item.impl;

import net.kyori.adventure.text.Component;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.zuma.examplePlugin.core.constants.Color;
import org.zuma.examplePlugin.core.constants.Icon;
import org.zuma.examplePlugin.domain.item.base.AbstractItem;
import org.zuma.examplePlugin.domain.item.meta.AbstractItemMeta;
import org.zuma.examplePlugin.domain.item.state.StateManager;
import org.zuma.examplePlugin.domain.player.RunePlayer;

public class QuestItem extends AbstractItem {
    private String questName;

    public QuestItem(AbstractItemMeta meta) {
        super(meta);
        super.setTypeLore("Quest Item", Icon.BOOK.get());
    }

    public void setQuestName(String name) {
        this.questName = name;
    }

    @Override
    public void onPlayerItemDrop(PlayerDropItemEvent event, RunePlayer player, StateManager itemStateManager) {
        event.setCancelled(true);
        player.asBukkitPlayer().sendMessage(Component.text("You cannot drop this item. It is associated with " + questName + " quest.").color(Color.ERROR.asColor()));
    }


}
