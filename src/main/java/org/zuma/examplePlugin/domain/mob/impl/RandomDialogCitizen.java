package org.zuma.examplePlugin.domain.mob.impl;

import net.kyori.adventure.text.Component;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.EntityType;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.zuma.examplePlugin.domain.dialog.DialogBuilder;
import org.zuma.examplePlugin.domain.mob.base.AbstractDialogMob;
import org.zuma.examplePlugin.domain.mob.behavior.MobBehaviors;
import org.zuma.examplePlugin.domain.mob.constants.EquipmentSet;
import org.zuma.examplePlugin.domain.mob.meta.FriendlyImmortalMobMeta;
import org.zuma.examplePlugin.domain.player.RunePlayer;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;
import java.util.function.Supplier;

public class RandomDialogCitizen extends AbstractDialogMob {
    private final List<Supplier<DialogBuilder>> dialogTemplates;

    public RandomDialogCitizen(String displayName, @Nullable List<EquipmentSet> equipmentSet, @Nullable List<String> skin, @Nullable List<MobBehaviors> behaviors, List<Supplier<DialogBuilder>> dialogs) {
        super(new FriendlyImmortalMobMeta(Component.text(displayName), EntityType.SHEEP, true, true), null, null, 500, dialogs.getFirst());
        this.dialogTemplates = dialogs;

        EquipmentSet randomSet = equipmentSet != null && !equipmentSet.isEmpty() ? equipmentSet.get((int) (Math.random() * equipmentSet.size())) : null;
        MobBehaviors randomBehavior = behaviors != null && !behaviors.isEmpty() ? behaviors.get((int) (Math.random() * behaviors.size())) : null;
        String randomSkin = skin != null && !skin.isEmpty() ? skin.get((int) (Math.random() * skin.size())) : null;

        if (randomSet != null) super.setMobEquipment(randomSet.asMobEquipmentMeta());
        if (randomBehavior != null) super.setBehavior(randomBehavior.getBehavior());
        if (randomSkin != null) setPlayerDisguise(randomSkin);

        HashMap<Attribute, Double> attributes = new HashMap<>();
        attributes.put(Attribute.MOVEMENT_SPEED, 0.23);
        super.setAttributes(attributes);
    }

    @Override
    public void onPlayerInteractWithEntity(PlayerInteractAtEntityEvent event, RunePlayer player) {
        Supplier<DialogBuilder> randomDialog = dialogTemplates.get((int) (Math.random() * dialogTemplates.size()));
        super.setDialogTemplate(randomDialog);
        super.onPlayerInteractWithEntity(event, player);
    }
}
