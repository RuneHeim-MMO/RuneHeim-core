package org.zuma.examplePlugin.item.impl;

import org.bukkit.Material;
import org.bukkit.event.player.PlayerInteractEvent;
import org.zuma.examplePlugin.item.base.GearItem;
import org.zuma.examplePlugin.item.constants.ItemId;
import org.zuma.examplePlugin.item.constants.ItemRarity;
import org.zuma.examplePlugin.modifier.Modifier;
import org.zuma.examplePlugin.modifier.constants.ModifierType;
import org.zuma.examplePlugin.player.RunePlayer;

public class RuneItem extends GearItem {

    public RuneItem() {
        super(ItemId.HOLY_LOG.asInt(), Material.DIAMOND_BOOTS, ItemRarity.EPIC, "Holy Log");
        super.addModifier(new Modifier(ModifierType.STRENGTH, 123));
        System.out.println("Spawned item");

    }

    @Override
    public void onPlayerInteract(PlayerInteractEvent event, RunePlayer player) {
        System.out.println("Interacted from Scripted Item id:" + super.id + " by RuneQuestPlayer " + player.getUUID());
    }
}
