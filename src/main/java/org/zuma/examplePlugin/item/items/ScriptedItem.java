package org.zuma.examplePlugin.item.items;

import org.bukkit.event.player.PlayerInteractEvent;
import org.zuma.examplePlugin.item.RuneItem;

public class ScriptedItem extends RuneItem {

    public ScriptedItem() {
        super(2);
        System.out.println("Spawned item");

    }

    @Override
    public void onPlayerInteract(PlayerInteractEvent event) {
        System.out.println("Interacted from Scripted Item id:" + super.id);
    }
}
