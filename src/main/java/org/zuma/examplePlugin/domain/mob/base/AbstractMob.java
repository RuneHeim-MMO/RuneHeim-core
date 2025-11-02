package org.zuma.examplePlugin.domain.mob.base;

import com.ticxo.modelengine.api.ModelEngineAPI;
import com.ticxo.modelengine.api.model.ActiveModel;
import com.ticxo.modelengine.api.model.ModeledEntity;
import io.papermc.paper.event.entity.EntityKnockbackEvent;
import me.libraryaddict.disguise.DisguiseAPI;
import me.libraryaddict.disguise.disguisetypes.Disguise;
import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import me.libraryaddict.disguise.disguisetypes.MobDisguise;
import me.libraryaddict.disguise.disguisetypes.PlayerDisguise;
import me.libraryaddict.disguise.disguisetypes.watchers.LivingWatcher;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.*;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
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
import org.zuma.examplePlugin.domain.mob.mechanics.base.MobMechanic;
import org.zuma.examplePlugin.domain.mob.meta.AbstractMobMeta;
import org.zuma.examplePlugin.domain.mob.meta.MobEquipmentMeta;
import org.zuma.examplePlugin.domain.player.RunePlayer;

import javax.annotation.Nullable;
import java.util.*;
import java.util.function.Function;

public abstract class AbstractMob {
    private UUID uuid;
    private Component displayName;
    private EntityType baseMob;
    private boolean ai, silent, isBaby, isInvulnerable, clearDefaultHandsEquipment;

    private int maxWalkDistance, yaw, secondsAlive;
    private HashMap<Attribute, Double> customAttributes;
    private List<Integer> dropTableIds;
    private Function<Entity, Void> onSpawnRunnable;
    private String healthBarSafeName = "";

    private @Nullable Entity entity;
    private @Nullable Location spawnLocation;
    private @Nullable MobEquipmentMeta mobEquipment;
    private @Nullable Disguise disguise;
    private @Nullable String model;
    private @Nullable MobBehavior behavior;

    private final HashMap<Class<? extends MobMechanic>, MobMechanic> mobMechanics;

    public AbstractMob(AbstractMobMeta meta) {
        this.ai = meta.ai();
        this.displayName = meta.displayName();
        this.baseMob = meta.baseEntity();
        this.silent = meta.silent();

        this.disguise = null;
        this.model = null;
        this.behavior = null;
        this.mobEquipment = null;
        this.isBaby = false;
        this.isInvulnerable = false;
        this.clearDefaultHandsEquipment = true;
        this.maxWalkDistance = -1; // -1 means no limits, can walk anywhere.
        this.customAttributes = new HashMap<>();
        this.dropTableIds = new ArrayList<>();
        this.healthBarSafeName = this instanceof CombatantMob ? "" : getDisplayName();
        this.onSpawnRunnable = null;
        this.yaw = 0;
        this.secondsAlive = 0;
        this.mobMechanics = new HashMap<>();
    }

    public void setYaw(int yaw) {
        this.yaw = yaw;
    }

    public int getYaw() {
        return this.yaw;
    }

    public void setSpawnRunnable(Function<Entity, Void> runnable) {
        this.onSpawnRunnable = runnable;
    }

    public Entity spawn(UUID uuid, Location location) throws IllegalStateException {
        if (this.entity != null) {
            throw new IllegalStateException("Cannot spawn second mob in the same instance!");
        }

        this.uuid = uuid;
        this.spawnLocation = location.clone();

        if (yaw != 0) {
            this.spawnLocation.setYaw(yaw);
        }

        if (this.disguise != null) {
            Disguise cloned = disguise.clone();

            if (disguise instanceof MobDisguise) {
                LivingWatcher watcher = (LivingWatcher) cloned.getWatcher();
                watcher.setCustomName(healthBarSafeName);
                watcher.setCustomNameVisible(true);
            }

            DisguiseAPI.disguiseNextEntity(disguise);
        }

        Entity entity = location.getWorld().spawnEntity(spawnLocation, baseMob);
        this.secondsAlive = 0;
        this.entity = entity;

        this.setEntityPersistenceKey(entity);

        if (entity instanceof LivingEntity livingEntity) {
            this.setLivingEntityMeta(livingEntity);
        }

        if (onSpawnRunnable != null) {
            onSpawnRunnable.apply(entity);
        }

        mobMechanics.values().forEach(MobMechanic::onSpawn);

        return entity;
    }

    public HashMap<Attribute, Double> getCustomAttributes() {
        return this.customAttributes;
    }

    public void setAI(boolean ai) {
        this.ai = ai;
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

    public AbstractMob setBaby(boolean baby) {
        this.isBaby = baby;
        return this;
    }

    public AbstractMob setMaxWalkDistance(int distance) {
        this.maxWalkDistance = distance;
        return this;
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

    public void sendMessageToPlayer(Player player, String message) {
        player.sendMessage(Component.text(this.getDisplayName(), NamedTextColor.WHITE)
                .append(Component.text(" » ", TextColor.color(150, 150, 150)))
                .append(Component.text(message, NamedTextColor.WHITE)));
    }

    public void sendMessageToNearbyPlayers(String message, int radius) {
        if (this.entity == null) return;

        Component chatMessage = Component.text(getDisplayName(), NamedTextColor.WHITE)
                .append(Component.text(" » ", TextColor.color(150, 150, 150)))
                .append(Component.text(message, NamedTextColor.WHITE));

        for (Player player : entity.getLocation().getNearbyPlayers(radius)) {
            player.sendMessage(chatMessage);
        }

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
        equipment.setItemInOffHand(null);
        if (clearDefaultHandsEquipment) {
            equipment.setItemInMainHand(null);
        }

        if (!(entity instanceof ArmorStand armorStand)) {
            // Set all drop chances to zero
            equipment.setHelmetDropChance(0f);
            equipment.setChestplateDropChance(0f);
            equipment.setLeggingsDropChance(0f);
            equipment.setBootsDropChance(0f);
            equipment.setItemInMainHandDropChance(0f);
            equipment.setItemInOffHandDropChance(0f);
        }

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

    public void setLivingEntitySpeedAttr(double speed) {
        if (!(this.getEntity() instanceof LivingEntity livingEntity)) {
            return;
        }
        AttributeInstance entityAttr = livingEntity.getAttribute(Attribute.MOVEMENT_SPEED);

        if (entityAttr == null) {
            return;
        }

        entityAttr.setBaseValue(speed);
    }

    public double getLivingEntitySpeedAttr() {
        if (!(this.getEntity() instanceof LivingEntity livingEntity)) {
            return 0;
        }
        AttributeInstance entityAttr = livingEntity.getAttribute(Attribute.MOVEMENT_SPEED);

        if (entityAttr == null) {
            return 0;
        }

        return entityAttr.getBaseValue();
    }

    public List<ItemStack> rollDropTables(RunePlayer activator) {
        List<ItemStack> drops = new ArrayList<>();

        this.dropTableIds.forEach(id -> {
            DropTable table = RuneQuestPlugin.getDropTableHandler().getByID(id);

            if (table == null) {
                RuneQuestPlugin.getRuneLogger().log(LogLevel.WARN, "Cannot roll drops for " + this.displayName + "! The table ID (" + id + ") is invalid!");
                return;
            }

            @Nullable
            ItemRecord drop = table.roll(activator);

            if (drop == null) {
                return;
            }
            AbstractItemType type = RuneQuestPlugin.getItemRegistry().getType(drop.getItemId());

            if (type == null) {
                RuneQuestPlugin.getRuneLogger().log(LogLevel.WARN, "Cannot spawn drop for " + this.displayName + "! The item id (" + drop.getItemId() + ") in table (" + id + ") is invalid!");
                return;
            }

            ItemStack stack = type.createItemStackOfType(drop.getAmount());

            if (!type.isTradeable) {
                type.setUntradeableOwnerUUID(stack, activator.getUUID());
            }

            drops.add(stack);
        });

        return drops;
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

    @Nullable
    public Location getSpawnLocation() {
        return spawnLocation;
    }

    public void freezeMob() {
        // Prevent mob from walking/jumping when player clicks it.
        if (this.getEntity() instanceof LivingEntity living) {
            living.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, Integer.MAX_VALUE, 254, false, false, false)); // 255 is reserved for mobs that NEVER walk.
        }
    }

    public void unfreezeMob() {
        if (this.getEntity() instanceof LivingEntity living) {
            var effect = living.getPotionEffect(PotionEffectType.SLOWNESS);
            if (effect != null && effect.getAmplifier() == 254) {
                living.removePotionEffect(PotionEffectType.SLOWNESS);
            }
        }
    }

    public boolean isClearDefaultHandsEquipment() {
        return clearDefaultHandsEquipment;
    }

    public void setClearDefaultHandsEquipment(boolean clearDefaultHandsEquipment) {
        this.clearDefaultHandsEquipment = clearDefaultHandsEquipment;
    }

    public HashMap<Class<? extends MobMechanic>, MobMechanic> getMobMechanics() {
        return this.mobMechanics;
    }

    public AbstractMob addMobMechanic(MobMechanic mechanic) {
        this.mobMechanics.put(mechanic.getClass(), mechanic);
        mechanic.setParent(this);
        return this;
    }

    public void playAnimation(String animationName, float speed, float blendIn, int loopCount) {
        Entity entity = getEntity();
        if (entity == null) return;

        ModeledEntity me = ModelEngineAPI.getModeledEntity(entity);

        if (me == null) return;

        me.getModels().values().forEach(am -> {
            am.getAnimationHandler().playAnimation(animationName, speed, blendIn, loopCount, true);
        });

    }

    public void handleInteraction(RunePlayer player) {

    }

    public void onPlayerInteractWithEntity(PlayerInteractAtEntityEvent event, RunePlayer player) {

    }

    public void onEntityAttackEntity(EntityDamageByEntityEvent event, Entity attacker) {

    }

    public void onPlayerAttackEntity(EntityDamageByEntityEvent event, RunePlayer player) {

    }

    public void onEntityAttackPlayer(EntityDamageByEntityEvent event, RunePlayer player) {

    }

    public void onEntityShootProjectile(ProjectileLaunchEvent event) {

    }


    public void onEntityDamaged(EntityDamageEvent event) {

    }

    public void onEntityDeath(EntityDeathEvent event) {

    }

    public void onEntityKnockback(EntityKnockbackEvent event) {

    }

    public void onEntityTargetPlayer(EntityTargetEvent event, RunePlayer player) {

    }

    public void tick() {
        this.secondsAlive++;
        this.mobMechanics.values().forEach(MobMechanic::tick);
    }

    public int getSecondsAlive() {
        return this.secondsAlive;
    }

}
