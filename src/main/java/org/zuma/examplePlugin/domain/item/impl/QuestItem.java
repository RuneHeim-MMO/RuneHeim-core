package org.zuma.examplePlugin.domain.item.impl;

import net.kyori.adventure.text.Component;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.zuma.examplePlugin.core.constants.Color;
import org.zuma.examplePlugin.core.constants.Icon;
import org.zuma.examplePlugin.domain.hud.HudService;
import org.zuma.examplePlugin.domain.item.base.AbstractItemType;
import org.zuma.examplePlugin.domain.item.meta.AbstractItemMeta;
import org.zuma.examplePlugin.domain.item.state.StateManager;
import org.zuma.examplePlugin.domain.player.RunePlayer;

public class QuestItem extends AbstractItemType {
    private String questName = "Not set";

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
        HudService.sendFailMessage(player, Component.text("You cannot drop this item. It is associated with " + questName + " quest."));
    }


}
