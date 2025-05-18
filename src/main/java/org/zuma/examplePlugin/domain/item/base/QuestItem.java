package org.zuma.examplePlugin.domain.item.base;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.zuma.examplePlugin.constants.Colors;
import org.zuma.examplePlugin.domain.item.constants.ItemRarity;
import org.zuma.examplePlugin.domain.item.constants.ItemType;
import org.zuma.examplePlugin.domain.player.RunePlayer;

import java.util.List;

public abstract class QuestItem extends CustomItem {
    private final String dropMessage;

    public QuestItem(int id, String dropMessage, Material material, ItemRarity rarity, String displayName) {
        this(id, dropMessage, material, rarity, displayName, null);
    }

    public QuestItem(int id, String dropMessage, Material material, ItemRarity rarity, String displayName, List<Component> lore) {
        super(ItemType.QUEST, id, material, rarity, displayName, lore);
        this.dropMessage = dropMessage;
        this.setCustomModelData(id);
        this.appendToLore(Component.text("Quest Item").color(Colors.ITEM_TYPE.asColor()));
    }

    @Override
    public void onPlayerItemDrop(PlayerDropItemEvent event, RunePlayer player) {
        event.setCancelled(true);
        player.getPlayer().sendMessage(Component.text(dropMessage).color(Colors.ERROR.asColor()));
    }


}
