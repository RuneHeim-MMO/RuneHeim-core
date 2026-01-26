package org.zuma.examplePlugin.domain.mob.impl;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Mob;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.config.ConfigManager;
import org.zuma.examplePlugin.core.config.EnvironmentType;
import org.zuma.examplePlugin.domain.dialog.Dialog;
import org.zuma.examplePlugin.domain.dialog.DialogBuilder;
import org.zuma.examplePlugin.domain.mob.behavior.MobBehavior;
import org.zuma.examplePlugin.domain.mob.constants.EquipmentSet;
import org.zuma.examplePlugin.domain.modifier.Modifier;
import org.zuma.examplePlugin.domain.player.RunePlayer;
import org.zuma.examplePlugin.domain.skill.SkillLevelRecord;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;

public class ToggleableCombatant extends GenericCombatant {
    private boolean isAggressive = false;
    private Supplier<DialogBuilder> dialogTemplate;
    private int activeDialogs, passiveWalkDistance, aggressiveWalkDistance;

    public ToggleableCombatant(String displayName,
                               @Nullable EquipmentSet equipmentSet,
                               List<SkillLevelRecord> skills,
                               List<Modifier> modifiers,
                               @Nullable MobBehavior behavior,
                               String skin,
                               double walkSpeed,
                               int health,
                               List<Integer> dropTableIds, boolean safeSpottable, Supplier<DialogBuilder> dialogTemplate, int passiveWalkDistance, int aggressiveWalkDistance) {
        super(displayName, equipmentSet, skills, modifiers, behavior, walkSpeed, true, false, health, skin, null, null, dropTableIds, safeSpottable);

        // max walk distance
        this.dialogTemplate = dialogTemplate;
        this.activeDialogs = 0;
        this.passiveWalkDistance = passiveWalkDistance;
        this.aggressiveWalkDistance = aggressiveWalkDistance;
        setMaxWalkDistance(passiveWalkDistance);
    }

    public void onEntityTargetPlayer(EntityTargetEvent event, RunePlayer player) {
        if (isAggressive) {
            unfreezeMob();
            super.onEntityTargetPlayer(event, player);
        } else {
            event.setCancelled(true);
        }
    }

    public void setAggressive(boolean aggressive) {
        isAggressive = aggressive;

        setMaxWalkDistance(aggressive ? aggressiveWalkDistance : passiveWalkDistance);
        if (!(this.getEntity() instanceof LivingEntity living)) return;

        for (PotionEffect effect : living.getActivePotionEffects()) {
            living.removePotionEffect(effect.getType());
        }

        PotionEffectType effectType = getHostilityEffect(this.isAggressive, false);
        living.addPotionEffect(new PotionEffect(effectType, Integer.MAX_VALUE, 0, false, false, false));
        unfreezeMob();

        if (aggressive) {
            if (getEntity() != null && this.getBehavior() != null) {
                this.getBehavior().playSound(getEntity(), MobBehavior.MobSoundType.NEGATIVE);
            }

            Bukkit.getScheduler().runTaskLater(RuneQuestPlugin.getInstance(), () -> {
                if (this.getEntity() == null || this.getEntity().isDead()) return;

                this.setAggressive(false);
            }, 20 * 60);
        }

        if (!aggressive && this.getEntity() instanceof Mob mob) {
            mob.setTarget(null);
        }
    }

    public void setDialogTemplate(Supplier<DialogBuilder> dialogTemplate) {
        this.dialogTemplate = dialogTemplate;
    }

    @Override
    public void onPlayerInteractWithEntity(PlayerInteractAtEntityEvent event, RunePlayer player) {
        event.setCancelled(true);

        if (player.isInDialog() || isAggressive) {
            return;
        }

        super.playSound(MobBehavior.MobSoundType.IDLE);

        if (dialogTemplate != null) {
            if (RuneQuestPlugin.getDialogHandler().getActivePlayerDialog(player) != null) {
                return;
            }

            DialogBuilder newDialogTemplate = dialogTemplate.get();

            if (!ConfigManager.environment.equals(EnvironmentType.PRODUCTION)) {
                System.out.println(newDialogTemplate.serialize());
            }

            Dialog dialog = newDialogTemplate.build(this.getUUID());

            freezeMob();
            activeDialogs++;


            RuneQuestPlugin.getDialogHandler().startDialog(player, dialog, () -> {
                // Decrease active dialog count but not below 0
                activeDialogs = Math.max(0, activeDialogs - 1);
                // Only unfreeze if no active dialogs remain
                if (activeDialogs == 0) unfreezeMob();
            });
            return;
        }

        // If no dialog, continue
        handleInteraction(player);

    }

    public void onPlayerAttackEntity(EntityDamageByEntityEvent event, RunePlayer player) {
        this.setAggressive(true);

        if (getEntity() != null && this.getBehavior() != null) {
            this.getBehavior().playSound(getEntity(), MobBehavior.MobSoundType.NEGATIVE);
        }
    }

    @Override
    public Entity spawn(UUID uuid, Location location) {
        Entity spawned = super.spawn(uuid, location);
        setAggressive(false);
        return spawned;
    }
}
