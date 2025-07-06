package org.zuma.examplePlugin.domain.mob.impl;

import net.kyori.adventure.text.Component;
import org.bukkit.entity.EntityType;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.zuma.examplePlugin.domain.mob.base.AbstractInventoryOpener;
import org.zuma.examplePlugin.domain.mob.behavior.MobBehavior;
import org.zuma.examplePlugin.domain.mob.constants.EquipmentSet;
import org.zuma.examplePlugin.domain.mob.meta.FriendlyImmortalMobMeta;
import org.zuma.examplePlugin.domain.player.RunePlayer;

public class GenericShopkeeper extends AbstractInventoryOpener {
    public GenericShopkeeper(String displayName, EquipmentSet equipmentSet, MobBehavior behavior, String skin, int inventoryId, int maxWalkDistance) {
        super(new FriendlyImmortalMobMeta(Component.text(displayName), EntityType.SHEEP, true), inventoryId, skin, maxWalkDistance);
        super.setMobEquipment(equipmentSet.asMobEquipmentMeta());
        super.setBehavior(behavior);
    }

    @Override
    public void onInventoryClose(PlayerInteractAtEntityEvent event, RunePlayer player) {
        if (getBehavior() != null) {
            getBehavior().handleInteractionFail(event);
        }
    }
}
