package org.zuma.examplePlugin.domain.item.impl;

import net.kyori.adventure.text.Component;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.constants.Icon;
import org.zuma.examplePlugin.domain.hud.HudService;
import org.zuma.examplePlugin.domain.item.base.AbstractItemType;
import org.zuma.examplePlugin.domain.item.meta.AbstractItemMeta;
import org.zuma.examplePlugin.domain.item.state.StateManager;
import org.zuma.examplePlugin.domain.player.RunePlayer;
import org.zuma.examplePlugin.domain.quest.Quest;

public class QuestItem extends AbstractItemType {
    private String questId = "Not set";
    private boolean droppable = false;

    public QuestItem(AbstractItemMeta meta) {
        super(meta);
        super.setTypeLore("Quest Item", Icon.BOOK.get());
        this.setSellableToShop(false);
    }

    public void setQuestId(String id) {
        this.questId = id;
    }

    public void setAttributes(boolean droppable) {
        this.droppable = droppable;
    }

    @Override
    public void onPlayerItemDrop(PlayerDropItemEvent event, RunePlayer player, StateManager itemStateManager) {
        if (droppable) return;

        event.setCancelled(true);
        Quest matchingQuest = RuneQuestPlugin.getQuestHandler().getRegistry().getQuestById(questId);

        if (matchingQuest != null)
            HudService.sendFailMessage(player, Component.text("You cannot drop this item. It's needed for the " + matchingQuest.getName() + " quest."));
    }


}
