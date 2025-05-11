package org.zuma.examplePlugin.item.items;

import org.bukkit.Material;
import org.bukkit.event.player.PlayerInteractEvent;
import org.zuma.examplePlugin.constants.ItemRarity;
import org.zuma.examplePlugin.item.ScriptedItem;
import org.zuma.examplePlugin.player.RuneQuestPlayer;

public class RuneItem extends ScriptedItem {

    public RuneItem() {
        super(0, Material.ACACIA_LOG, ItemRarity.EPIC, "Holy Log");
        System.out.println("Spawned item");

    }

    @Override
    public void onPlayerInteract(PlayerInteractEvent event, RuneQuestPlayer player) {
        System.out.println("Interacted from Scripted Item id:" + super.id + " by RuneQuestPlayer " + player.getUUID());
    }
}
