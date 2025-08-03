package org.zuma.examplePlugin.domain.mob.impl;

import net.kyori.adventure.text.Component;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.zuma.examplePlugin.domain.mob.base.AbstractInventoryOpener;
import org.zuma.examplePlugin.domain.mob.behavior.MobBehavior;
import org.zuma.examplePlugin.domain.mob.constants.EquipmentSet;
import org.zuma.examplePlugin.domain.mob.meta.FriendlyImmortalMobMeta;
import org.zuma.examplePlugin.domain.player.RunePlayer;

import javax.annotation.Nullable;
import java.util.HashMap;

public class InventoryOpenerMob extends AbstractInventoryOpener {
    public InventoryOpenerMob(String displayName, @Nullable EquipmentSet equipmentSet, MobBehavior behavior, @Nullable String skin, @Nullable String model, int inventoryId, int maxWalkDistance) {
        super(new FriendlyImmortalMobMeta(Component.text(displayName), EntityType.VILLAGER, true), inventoryId, skin, model, maxWalkDistance);

        if (equipmentSet != null) super.setMobEquipment(equipmentSet.asMobEquipmentMeta());
        super.setBehavior(behavior);

        HashMap<Attribute, Double> attributes = new HashMap<>();
        attributes.put(Attribute.GENERIC_MOVEMENT_SPEED, 0.23);
        super.setAttributes(attributes);
    }

    @Override
    public void onInventoryClose(PlayerInteractAtEntityEvent event, RunePlayer player) {
        Entity clicked = event.getRightClicked();

        if (clicked instanceof LivingEntity living) {
            var effect = living.getPotionEffect(PotionEffectType.SLOWNESS);
            if (effect != null && effect.getAmplifier() == 254) {
                living.removePotionEffect(PotionEffectType.SLOWNESS);
            }
        }

    }

    @Override
    public void onPlayerInteractWithEntity(PlayerInteractAtEntityEvent event, RunePlayer player) {
        Entity clicked = event.getRightClicked();

        // Prevent mob from walking/jumping when player clicks it.
        if (clicked instanceof LivingEntity living) {
            living.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, Integer.MAX_VALUE, 254, false, false, false)); // 255 is reserved for mobs that NEVER walk.
        }

        super.onPlayerInteractWithEntity(event, player);
    }
}
