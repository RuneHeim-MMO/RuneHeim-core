package org.zuma.examplePlugin.domain.mob.base;

import me.libraryaddict.disguise.DisguiseAPI;
import me.libraryaddict.disguise.disguisetypes.Disguise;
import me.libraryaddict.disguise.disguisetypes.PlayerDisguise;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.*;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.EulerAngle;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.utils.TextUtils;
import org.zuma.examplePlugin.domain.mob.behavior.MobBehavior;
import org.zuma.examplePlugin.domain.mob.constants.MobDataKey;
import org.zuma.examplePlugin.domain.mob.meta.AbstractMobMeta;
import org.zuma.examplePlugin.domain.mob.meta.MobEquipmentMeta;
import org.zuma.examplePlugin.domain.player.RunePlayer;

import javax.annotation.Nullable;

public abstract class AbstractMob {
    private final int id, typeId;
    private final boolean ai;
    private final Component displayName;
    private final EntityType baseMob;
    private final boolean silent;

    @Nullable
    private Disguise disguise = null;

    @Nullable
    private MobBehavior behavior = null;

    @Nullable
    private MobEquipmentMeta mobEquipment = null;
    private boolean isBaby = false;
    private boolean isWalking = true;

    public AbstractMob(AbstractMobMeta meta) {
        this.id = meta.id();
        this.typeId = meta.typeId();
        this.ai = meta.ai();
        this.displayName = meta.displayName();
        this.baseMob = meta.baseEntity();
        this.silent = meta.silent();
    }

    public void spawn(Location location) {
        Entity entity = location.getWorld().spawnEntity(location, baseMob);

        this.setEntityPersistenceKey(entity);

        if (entity instanceof LivingEntity livingEntity) {
            this.setLivingEntityMeta(livingEntity);

            if (this.disguise != null) {
                DisguiseAPI.disguiseToAll(entity, disguise);
            }
        }

    }

    public void setMobEquipment(@Nullable MobEquipmentMeta equipment) {
        this.mobEquipment = equipment;
    }

    public void setBaby(boolean baby) {
        this.isBaby = baby;
    }

    public void setCanWalk(boolean canWalk) {
        this.isWalking = canWalk;
    }

    public boolean canWalk() {
        return this.isWalking;
    }

    public void setPlayerDisguise(String username) {
        PlayerDisguise disguise = new PlayerDisguise(username);
        disguise.setDisplayedInTab(false);
        disguise.setReplaceSounds(true);
        disguise.setName(TextUtils.extractPlainText(displayName));
        this.disguise = disguise;
    }

    public int getTypeId() {
        return this.typeId;
    }

    public int getId() {
        return this.id;
    }

    @Nullable
    public MobBehavior getBehavior() {
        return behavior;
    }

    public void setBehavior(@Nullable MobBehavior behavior) {
        this.behavior = behavior;
    }


    private void setLivingEntityMeta(LivingEntity entity) {
        entity.customName(displayName);
        entity.setCustomNameVisible(true);
        entity.setAI(ai);
        entity.setSilent(silent);


        if (entity instanceof Ageable ageable) {
            if (isBaby) {
                ageable.setBaby();
            } else {
                ageable.setAdult();
            }
        }


        if (!this.isWalking) {
            entity.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, Integer.MAX_VALUE, 255, false, false, false));
        }

        if (mobEquipment != null && entity.getEquipment() != null) {
            EntityEquipment equipment = entity.getEquipment();

            if (mobEquipment.helmet() != null) {
                equipment.setHelmet(mobEquipment.helmet());
            }

            if (mobEquipment.chestplate() != null) {
                equipment.setChestplate(mobEquipment.chestplate());
            }

            if (mobEquipment.leggings() != null) {
                equipment.setLeggings(mobEquipment.leggings());
            }

            if (mobEquipment.chestplate() != null) {
                equipment.setChestplate(mobEquipment.chestplate());
            }

            if (mobEquipment.hand() != null) {
                equipment.setItemInMainHand(mobEquipment.hand());
            }

            if (!(entity instanceof ArmorStand armorStand)) {
                equipment.setHelmetDropChance(0f);
                equipment.setChestplateDropChance(0f);
                equipment.setLeggingsDropChance(0f);
                equipment.setChestplateDropChance(0f);
                equipment.setItemInMainHandDropChance(0f);
            } else {
                armorStand.setArms(true);
                armorStand.setRightArmPose(new EulerAngle(Math.toRadians(250), 0, 0));
                armorStand.setLeftArmPose(new EulerAngle(Math.toRadians(250), 0, 0));
            }


        }
    }

    private void setEntityPersistenceKey(Entity entity) {
        NamespacedKey key = new NamespacedKey(RuneQuestPlugin.getInstance(), MobDataKey.MOB_ID.asString());
        entity.getPersistentDataContainer().set(key, PersistentDataType.INTEGER, id);
    }

    public void handlePositiveInteraction(PlayerInteractAtEntityEvent event, RunePlayer player) {
        if (behavior != null) {
            behavior.handleInteractionSuccess(event, player);
        }
    }

    public void handleNegativeInteraction(PlayerInteractAtEntityEvent event, RunePlayer player) {
        if (behavior != null) {
            behavior.handleInteractionSuccess(event, player);
        }
    }

    public void onPlayerInteractWithEntity(PlayerInteractAtEntityEvent event, RunePlayer player) {

    }

    public void onPlayerAttackEntity(EntityDamageByEntityEvent event, RunePlayer player) {

    }

    public void onEntityAttackPlayer(EntityDamageByEntityEvent event, RunePlayer player) {

    }

    public void onEntityDamaged(EntityDamageEvent event) {

    }


}
