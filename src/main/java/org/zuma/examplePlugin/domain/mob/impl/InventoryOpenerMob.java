package org.zuma.examplePlugin.domain.mob.impl;

import net.kyori.adventure.text.Component;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.domain.dialog.Dialog;
import org.zuma.examplePlugin.domain.dialog.DialogBuilder;
import org.zuma.examplePlugin.domain.mob.base.AbstractInventoryOpener;
import org.zuma.examplePlugin.domain.mob.behavior.MobBehavior;
import org.zuma.examplePlugin.domain.mob.constants.EquipmentSet;
import org.zuma.examplePlugin.domain.mob.meta.FriendlyImmortalMobMeta;
import org.zuma.examplePlugin.domain.player.RunePlayer;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.function.Supplier;

public class InventoryOpenerMob extends AbstractInventoryOpener {
    private Supplier<DialogBuilder> dialogTemplate;
    private String preposition, positiveOption, negativeOption, farewell;

    public InventoryOpenerMob(String displayName, @Nullable EquipmentSet equipmentSet, MobBehavior behavior, @Nullable String skin, @Nullable String model, int inventoryId, int maxWalkDistance) {
        super(new FriendlyImmortalMobMeta(Component.text(displayName), EntityType.VILLAGER, true), inventoryId, skin, model, maxWalkDistance);

        if (equipmentSet != null) super.setMobEquipment(equipmentSet.asMobEquipmentMeta());
        super.setBehavior(behavior);

        HashMap<Attribute, Double> attributes = new HashMap<>();
        attributes.put(Attribute.GENERIC_MOVEMENT_SPEED, 0.23);
        super.setAttributes(attributes);


    }

    public InventoryOpenerMob attachDialog(Supplier<DialogBuilder> template, String preposition, String positiveOption, String negativeOption, String farewell) {
        this.dialogTemplate = template;
        this.preposition = preposition;
        this.positiveOption = positiveOption;
        this.negativeOption = negativeOption;
        this.farewell = farewell;
        return this;
    }

    @Override
    public void onInventoryClose(PlayerInteractAtEntityEvent event, RunePlayer player) {
        unfreezeMob();
    }

    @Override
    public void onPlayerInteractWithEntity(PlayerInteractAtEntityEvent event, RunePlayer player) {
        freezeMob();
        super.playSound(MobBehavior.MobSoundType.IDLE);

        if (dialogTemplate != null) {
            DialogBuilder newDialogTemplate = dialogTemplate.get();

            newDialogTemplate.interactiveStep(
                    c -> c
                            .text(this.preposition)
                            .option(this.positiveOption, o -> o.runnable(() -> super.onPlayerInteractWithEntity(event, player)))
                            .option(this.negativeOption, v -> v.runnable(() -> super.playSound(MobBehavior.MobSoundType.NEGATIVE))
                                    .followupSteps(o -> o.passiveStep(s -> s.text(this.farewell))))

            );
            Dialog dialog = newDialogTemplate.build(super.getUUID());
            RuneQuestPlugin.getDialogHandler().startDialog(player, dialog, this::unfreezeMob);
            return;
        }


        super.onPlayerInteractWithEntity(event, player);
    }

    private void freezeMob() {
        // Prevent mob from walking/jumping when player clicks it.
        if (this.getEntity() instanceof LivingEntity living) {
            living.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, Integer.MAX_VALUE, 254, false, false, false)); // 255 is reserved for mobs that NEVER walk.
        }
    }

    private void unfreezeMob() {
        if (this.getEntity() instanceof LivingEntity living) {
            var effect = living.getPotionEffect(PotionEffectType.SLOWNESS);
            if (effect != null && effect.getAmplifier() == 254) {
                living.removePotionEffect(PotionEffectType.SLOWNESS);
            }
        }
    }
}
