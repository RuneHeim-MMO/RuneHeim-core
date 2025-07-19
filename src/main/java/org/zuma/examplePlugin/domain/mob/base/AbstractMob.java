package org.zuma.examplePlugin.domain.mob.base;

import com.ticxo.modelengine.api.ModelEngineAPI;
import com.ticxo.modelengine.api.model.ActiveModel;
import com.ticxo.modelengine.api.model.ModeledEntity;
import me.libraryaddict.disguise.DisguiseAPI;
import me.libraryaddict.disguise.disguisetypes.Disguise;
import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import me.libraryaddict.disguise.disguisetypes.MobDisguise;
import me.libraryaddict.disguise.disguisetypes.PlayerDisguise;
import me.libraryaddict.disguise.disguisetypes.watchers.LivingWatcher;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.*;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.EulerAngle;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.logger.constants.LogLevel;
import org.zuma.examplePlugin.core.utils.TextUtils;
import org.zuma.examplePlugin.domain.droptable.DropTable;
import org.zuma.examplePlugin.domain.item.ItemRecord;
import org.zuma.examplePlugin.domain.item.base.AbstractItemType;
import org.zuma.examplePlugin.domain.mob.behavior.MobBehavior;
import org.zuma.examplePlugin.domain.mob.constants.EquipmentSlot;
import org.zuma.examplePlugin.domain.mob.constants.MobDataKey;
import org.zuma.examplePlugin.domain.mob.meta.AbstractMobMeta;
import org.zuma.examplePlugin.domain.mob.meta.MobEquipmentMeta;
import org.zuma.examplePlugin.domain.player.RunePlayer;

import javax.annotation.Nullable;
import java.util.*;

public abstract class AbstractMob {
    private UUID uuid;
    private final boolean ai;
    private Component displayName;
    private EntityType baseMob;
    private boolean silent;

    @Nullable
    private Disguise disguise;

    @Nullable
    private String model;

    @Nullable
    private MobBehavior behavior;

    @Nullable
    private MobEquipmentMeta mobEquipment;
    private boolean isBaby, isInvulnerable;
    private int maxWalkDistance;

    @Nullable
    private Entity entity;

    private HashMap<Attribute, Double> customAttributes;
    private List<Integer> dropTableIds;

    private String healthBarSafeName = "";

    public AbstractMob(AbstractMobMeta meta) {
        this.ai = meta.ai();
        this.displayName = meta.displayName();
        this.baseMob = meta.baseEntity();
        this.silent = meta.silent();


        disguise = null;
        model = null;
        behavior = null;
        mobEquipment = null;
        isBaby = false;
        isInvulnerable = false;
        maxWalkDistance = -1; // -1 means no limits, can walk anywhere.
        customAttributes = new HashMap<>();
        dropTableIds = new ArrayList<>();

        healthBarSafeName = this instanceof CombatantMob ? "" : getDisplayName();
    }

    public Entity spawn(UUID uuid, Location location) {
        Entity entity = location.getWorld().spawnEntity(location, baseMob);
        this.entity = entity;
        this.uuid = uuid;

        this.setEntityPersistenceKey(entity);

        if (entity instanceof LivingEntity livingEntity) {
            this.setLivingEntityMeta(livingEntity);
        }

        return entity;
    }

    public void setBaseEntity(EntityType type) {
        this.baseMob = type;
    }

    public void setDropTableIds(List<Integer> dropTableIds) {
        this.dropTableIds = dropTableIds;
    }

    public void setAttributes(HashMap<Attribute, Double> customAttributes) {
        this.customAttributes = customAttributes;
    }

    public void setMobEquipment(@Nullable MobEquipmentMeta equipment) {
        this.mobEquipment = equipment;
    }

    public void setDisplayName(Component name) {
        this.displayName = name;
    }

    public void setBaby(boolean baby) {
        this.isBaby = baby;
    }

    public void setMaxWalkDistance(int distance) {
        this.maxWalkDistance = distance;
    }

    public int getMaxWalkDistance() {
        return this.maxWalkDistance;
    }

    public boolean canWalk() {
        return maxWalkDistance != 0;
    }

    public String getDisplayName() {
        return TextUtils.extractPlainText(displayName);
    }

    public void setSilent(boolean silent) {
        this.silent = silent;
    }

    @Nullable
    public Entity getEntity() {
        return this.entity;
    }

    public void setDisguise(DisguiseType type) {
        MobDisguise disguise = new MobDisguise(type);
        disguise.setReplaceSounds(true);
        this.disguise = disguise;
    }

    public void setPlayerDisguise(String username) {
        PlayerDisguise disguise = new PlayerDisguise(username);
        disguise.setDisplayedInTab(false);
        disguise.setReplaceSounds(true);
        disguise.setName(healthBarSafeName);
        this.disguise = disguise;
    }

    public void onDisable() {
        if (this.entity != null) {
            DisguiseAPI.undisguiseToAll(this.entity);
        }
    }

    public UUID getUUID() {
        return this.uuid;
    }

    @Nullable
    public MobBehavior getBehavior() {
        return behavior;
    }

    public void setBehavior(@Nullable MobBehavior behavior) {
        this.behavior = behavior;
    }

    public void clearMobEquipment(LivingEntity entity) {
        if (entity == null || entity.getEquipment() == null) return;

        EntityEquipment equipment = entity.getEquipment();

        // Clear all armor and items by setting to AIR
        equipment.setHelmet(null);      // or new ItemStack(Material.AIR)
        equipment.setChestplate(null);
        equipment.setLeggings(null);
        equipment.setBoots(null);
        equipment.setItemInMainHand(null);
        equipment.setItemInOffHand(null);

        // Set all drop chances to zero
        equipment.setHelmetDropChance(0f);
        equipment.setChestplateDropChance(0f);
        equipment.setLeggingsDropChance(0f);
        equipment.setBootsDropChance(0f);
        equipment.setItemInMainHandDropChance(0f);
        equipment.setItemInOffHandDropChance(0f);

        // Special case for ArmorStands
        if (entity instanceof ArmorStand armorStand) {
            armorStand.setArms(false);
            armorStand.setRightArmPose(new EulerAngle(0, 0, 0));
            armorStand.setLeftArmPose(new EulerAngle(0, 0, 0));
        }
    }

    protected void setLivingEntityMeta(LivingEntity entity) {
        entity.customName(Component.text(healthBarSafeName));
        entity.setCustomNameVisible(true);
        entity.setAI(ai);
        entity.setSilent(silent);
        entity.setInvulnerable(isInvulnerable);

        if (this.model != null) {
            ModeledEntity modeledEntity = ModelEngineAPI.createModeledEntity(entity);
            ActiveModel activeModel = ModelEngineAPI.createActiveModel(model);

            modeledEntity.addModel(activeModel, true);
            entity.setInvisible(true);
        }

        if (this.disguise != null) {
            Disguise cloned = disguise.clone();

            if (disguise instanceof MobDisguise) {
                LivingWatcher watcher = (LivingWatcher) cloned.getWatcher();
                watcher.setCustomName(healthBarSafeName);
                watcher.setCustomNameVisible(true);
            }

            DisguiseAPI.disguiseToAll(entity, cloned);
        }


        customAttributes.forEach((attr, val) -> {
            AttributeInstance entityAttr = entity.getAttribute(attr);

            if (entityAttr != null) {
                entityAttr.setBaseValue(val);
            }
        });

        if (entity instanceof Ageable ageable) {
            if (isBaby) {
                ageable.setBaby();
            } else {
                ageable.setAdult();
            }
        }


        if (!canWalk()) {
            entity.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, Integer.MAX_VALUE, 255, false, false, false));
        }

        clearMobEquipment(entity);

        if (mobEquipment != null && entity.getEquipment() != null) {
            EntityEquipment equipment = entity.getEquipment();

            equipment.setHelmet(mobEquipment.rollSlot(EquipmentSlot.HEAD));
            equipment.setChestplate(mobEquipment.rollSlot(EquipmentSlot.CHEST));
            equipment.setLeggings(mobEquipment.rollSlot(EquipmentSlot.LEGS));
            equipment.setBoots(mobEquipment.rollSlot(EquipmentSlot.FEET));
            equipment.setItemInMainHand(mobEquipment.rollSlot(EquipmentSlot.HAND));
            equipment.setItemInOffHand(mobEquipment.rollSlot(EquipmentSlot.OFFHAND));

        }


        if (!(entity instanceof ArmorStand armorStand)) {
            EntityEquipment equipment = entity.getEquipment();

            if (equipment == null) {
                return;
            }

            equipment.setHelmetDropChance(0f);
            equipment.setChestplateDropChance(0f);
            equipment.setLeggingsDropChance(0f);
            equipment.setChestplateDropChance(0f);
            equipment.setItemInMainHandDropChance(0f);
            equipment.setItemInOffHandDropChance(0f);
        } else {
            armorStand.setArms(true);
            armorStand.setRightArmPose(new EulerAngle(Math.toRadians(250), 0, 0));
            armorStand.setLeftArmPose(new EulerAngle(Math.toRadians(250), 0, 0));
        }


    }

    private void setEntityPersistenceKey(Entity entity) {
        NamespacedKey key = new NamespacedKey(RuneQuestPlugin.getInstance(), MobDataKey.MOB_UUID.asString());
        entity.getPersistentDataContainer().set(key, PersistentDataType.STRING, this.uuid.toString());
    }


    public void reapplyMetaToEntity(ChunkLoadEvent event) {
        if (entity instanceof LivingEntity livingEntity) {
            this.setLivingEntityMeta(livingEntity);

            if (this.disguise != null) {
                DisguiseAPI.disguiseToAll(entity, disguise.clone());
            }
        }
    }

    public void playSound(MobBehavior.MobSoundType type) {
        if (behavior == null || entity == null || entity.isDead()) {
            return;
        }

        behavior.playSound(entity, type);
    }

    public void rollDrops(Location dropLocation) {
        this.dropTableIds.forEach(id -> {
            DropTable table = RuneQuestPlugin.getDropTableHandler().getByID(id);

            if (table == null) {
                RuneQuestPlugin.getRuneLogger().log(LogLevel.WARN, "Cannot roll drops for " + this.displayName + "! The table ID (" + id + ") is invalid!");
                return;
            }

            @Nullable
            ItemRecord drop = table.roll();

            if (drop == null) {
                return;
            }
            AbstractItemType type = RuneQuestPlugin.getItemRegistry().getItem(drop.getItemId());

            if (type == null) {
                RuneQuestPlugin.getRuneLogger().log(LogLevel.WARN, "Cannot spawn drop for " + this.displayName + "! The item id (" + drop.getItemId() + ") in table (" + id + ") is invalid!");
                return;
            }

            dropLocation.getWorld().dropItem(dropLocation, type.createItemStackOfType(drop.getAmount()));
        });
    }

    public void onPlayerInteractWithEntity(PlayerInteractAtEntityEvent event, RunePlayer player) {

    }

    public void onPlayerAttackEntity(EntityDamageByEntityEvent event, RunePlayer player) {

    }

    public void onEntityAttackPlayer(EntityDamageByEntityEvent event, RunePlayer player) {

    }

    public void onEntityDamaged(EntityDamageEvent event) {

    }


    @Nullable
    public Disguise getDisguise() {
        if (disguise == null) return null;
        return disguise.clone();
    }

    public void setModel(@Nullable String model) {
        this.model = model;
    }

    public boolean isInvulnerable() {
        return isInvulnerable;
    }

    public void setInvulnerable(boolean invulnerable) {
        isInvulnerable = invulnerable;
    }
}
