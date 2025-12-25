package org.zuma.examplePlugin.domain.mob.impl;

import net.kyori.adventure.text.Component;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.EntityType;
import org.zuma.examplePlugin.domain.mob.base.AbstractInventoryOpener;
import org.zuma.examplePlugin.domain.mob.behavior.MobBehavior;
import org.zuma.examplePlugin.domain.mob.constants.EquipmentSet;
import org.zuma.examplePlugin.domain.mob.meta.FriendlyImmortalMobMeta;
import org.zuma.examplePlugin.domain.player.RunePlayer;

import javax.annotation.Nullable;
import java.util.HashMap;

public class InventoryOpenerMob extends AbstractInventoryOpener {

    public InventoryOpenerMob(String displayName, @Nullable EquipmentSet equipmentSet, MobBehavior behavior, @Nullable String skin, @Nullable String model, int inventoryId, int maxWalkDistance) {
        super(new FriendlyImmortalMobMeta(Component.text(displayName), EntityType.SHEEP, true), inventoryId, skin, model, maxWalkDistance);

        if (equipmentSet != null) super.setMobEquipment(equipmentSet.asMobEquipmentMeta());
        super.setBehavior(behavior);

        HashMap<Attribute, Double> attributes = new HashMap<>();
        attributes.put(Attribute.MOVEMENT_SPEED, 0.23);
        super.setAttributes(attributes);
    }

    @Override
    public void onInventoryClose(RunePlayer player) {
        super.playSound(MobBehavior.MobSoundType.IDLE);
    }
}
