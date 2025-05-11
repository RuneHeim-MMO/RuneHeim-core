package org.zuma.examplePlugin.item.base;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.zuma.examplePlugin.constants.Colors;
import org.zuma.examplePlugin.constants.ItemRarity;
import org.zuma.examplePlugin.player.RunePlayer;

public abstract class QuestItem extends CustomItem {
    private final String dropMessage;

    public QuestItem(int id, String dropMessage, Material material, ItemRarity rarity, String displayName) {
        this(id, dropMessage, material, rarity, displayName, null);
    }

    public QuestItem(int id, String dropMessage, Material material, ItemRarity rarity, String displayName, Component[] lore) {
        super(id, material, rarity, displayName, lore);
        this.dropMessage = dropMessage;
    }

    @Override
    public void onPlayerItemDrop(PlayerDropItemEvent event, RunePlayer player) {
        event.setCancelled(true);
        player.getPlayer().sendMessage(Component.text(dropMessage).color(Colors.ERROR.asColor()));
    }


}
