package org.zuma.examplePlugin.domain.mob.base;

import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Ageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.persistence.PersistentDataType;
import org.zuma.examplePlugin.RuneQuestPlugin;
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

    @Nullable
    private MobEquipmentMeta mobEquipment = null;
    private boolean isBaby = false;

    public AbstractMob(AbstractMobMeta meta) {
        this.id = meta.id();
        this.typeId = meta.typeId();
        this.ai = meta.ai();
        this.displayName = meta.displayName();
        this.baseMob = meta.baseEntity();
    }

    public void spawn(Location location) {
        Entity entity = location.getWorld().spawnEntity(location, baseMob);

        this.setEntityPersistenceKey(entity);

        if (entity instanceof LivingEntity livingEntity) {
            this.setLivingEntityMeta(livingEntity);
        }

    }

    public void setMobEquipment(@Nullable MobEquipmentMeta equipment) {
        this.mobEquipment = equipment;
    }

    public void setBaby(boolean baby) {
        this.isBaby = baby;
    }

    private void setLivingEntityMeta(LivingEntity entity) {
        entity.customName(displayName);
        entity.setCustomNameVisible(true);
        entity.setAI(ai);

        if (entity instanceof Ageable ageable) {
            if (isBaby) {
                ageable.setBaby();
            } else {
                ageable.setAdult();
            }
        }


        if (mobEquipment != null && entity.getEquipment() != null) {
            EntityEquipment equipment = entity.getEquipment();

            if (mobEquipment.helmet() != null) {
                equipment.setHelmet(mobEquipment.helmet());
                equipment.setHelmetDropChance(0f);
            }

            if (mobEquipment.chestplate() != null) {
                equipment.setChestplate(mobEquipment.chestplate());
                equipment.setChestplateDropChance(0f);
            }

            if (mobEquipment.leggings() != null) {
                equipment.setLeggings(mobEquipment.leggings());
                equipment.setLeggingsDropChance(0f);
            }

            if (mobEquipment.chestplate() != null) {
                equipment.setChestplate(mobEquipment.chestplate());
                equipment.setChestplateDropChance(0f);
            }

            if (mobEquipment.hand() != null) {
                equipment.setItemInMainHand(mobEquipment.hand());
                equipment.setItemInMainHandDropChance(0f);
            }


        }
    }

    private void setEntityPersistenceKey(Entity entity) {
        NamespacedKey key = new NamespacedKey(RuneQuestPlugin.getInstance(), MobDataKey.MOB_ID.asString());
        entity.getPersistentDataContainer().set(key, PersistentDataType.INTEGER, id);
    }

    public int getTypeId() {
        return this.typeId;
    }

    public int getId() {
        return this.id;
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
