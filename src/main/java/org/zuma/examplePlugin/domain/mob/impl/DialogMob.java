package org.zuma.examplePlugin.domain.mob.impl;

import net.kyori.adventure.text.Component;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.EntityType;
import org.zuma.examplePlugin.domain.dialog.DialogBuilder;
import org.zuma.examplePlugin.domain.mob.base.AbstractDialogMob;
import org.zuma.examplePlugin.domain.mob.behavior.MobBehavior;
import org.zuma.examplePlugin.domain.mob.constants.EquipmentSet;
import org.zuma.examplePlugin.domain.mob.meta.FriendlyImmortalMobMeta;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.function.Supplier;

public class DialogMob extends AbstractDialogMob {

    public DialogMob(String displayName, @Nullable EquipmentSet equipmentSet, MobBehavior behavior, @Nullable String skin, @Nullable String model, int maxWalkDistance, Supplier<DialogBuilder> dialog) {
        super(new FriendlyImmortalMobMeta(Component.text(displayName), EntityType.SHEEP, true, false), skin, model, maxWalkDistance, dialog);

        if (equipmentSet != null) super.setMobEquipment(equipmentSet.asMobEquipmentMeta());
        super.setBehavior(behavior);

        HashMap<Attribute, Double> attributes = new HashMap<>();
        attributes.put(Attribute.MOVEMENT_SPEED, 0.23);
        super.setAttributes(attributes);
    }
}
