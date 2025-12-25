package org.zuma.examplePlugin.domain.mob.base;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.*;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.analytics.AnalyticsKey;
import org.zuma.examplePlugin.core.interfaces.CombatEntity;
import org.zuma.examplePlugin.core.utils.*;
import org.zuma.examplePlugin.domain.combat.Hit;
import org.zuma.examplePlugin.domain.combat.constants.ProjectileType;
import org.zuma.examplePlugin.domain.item.base.AbstractItemType;
import org.zuma.examplePlugin.domain.item.base.EquipableItemType;
import org.zuma.examplePlugin.domain.mob.constants.MetadataKeys;
import org.zuma.examplePlugin.domain.mob.mechanics.traits.AntiSafeSpotPullMechanic;
import org.zuma.examplePlugin.domain.mob.mechanics.traits.RangedMobMechanic;
import org.zuma.examplePlugin.domain.mob.meta.AbstractMobMeta;
import org.zuma.examplePlugin.domain.mob.meta.CombatantMobMeta;
import org.zuma.examplePlugin.domain.mob.projectiles.RuneProjectile;
import org.zuma.examplePlugin.domain.modifier.Modifier;
import org.zuma.examplePlugin.domain.modifier.constants.ModifierType;
import org.zuma.examplePlugin.domain.player.RunePlayer;
import org.zuma.examplePlugin.domain.skill.Skill;
import org.zuma.examplePlugin.domain.skill.constants.SkillType;
import org.zuma.examplePlugin.domain.xp.PendingXPHandler;
import org.zuma.examplePlugin.events.combat.RuneEntityDeathEvent;

import java.util.*;
import java.util.stream.Stream;

public abstract class CombatantMob extends AbstractMob implements CombatEntity {
    private final EnumMap<ModifierType, Modifier> modifiers = new EnumMap<>(ModifierType.class);
    private final EnumMap<SkillType, Skill> skills = new EnumMap<>(SkillType.class);
    private RuneProjectile projectileType = new RuneProjectile(ProjectileType.RANGED, EntityType.ARROW, 2);
    private final int combatLvl;
    private int health, maxHealth;
    private long lastDamagedAt;
    private boolean isSpecialAttacking;

    private final PendingXPHandler xpHandler;

    public CombatantMob(CombatantMobMeta meta) {
        super(new AbstractMobMeta(true, true, Component.empty(), meta.baseEntity()));
        this.health = meta.maxHealth();
        this.maxHealth = meta.maxHealth();
        this.xpHandler = new PendingXPHandler();
        this.isSpecialAttacking = false;

        if (!meta.safeSpottable()) {
            AntiSafeSpotPullMechanic mechanic = new AntiSafeSpotPullMechanic();
            this.addMobMechanic(mechanic);
            mechanic.setParent(this);
        }

        setModel(meta.model());
        setDropTableIds(meta.dropTableIds());
        meta.skills().forEach(record -> skills.put(record.type(), new Skill(record.type(), record.getXP())));

        if (meta.modifiers() != null) {
            meta.modifiers().forEach(modifier -> modifiers.put(modifier.type(), modifier));
        }

        combatLvl = XpUtils.getCombatLevelFromSkills(skills);
        String levelColorCode = TextUtils.getLegacyColorCodeForLevel(combatLvl);

        String displayName = levelColorCode + "[" + combatLvl + " Lvl] §7" + meta.displayName();
        this.setDisplayName(Component.text(displayName));

        if (meta.skin() != null && meta.disguiseType() == null) {
            setPlayerDisguise(meta.skin());
        }

        if (meta.disguiseType() != null) {
            setDisguise(meta.disguiseType());
        }
    }


    public CombatantMob overrideBaseToSkeleton() {
        this.setBaseEntity(EntityType.SKELETON);
        setClearDefaultHandsEquipment(false); // Keep bow in hand
        return this;
    }

    public CombatantMob overrideBaseToBlaze() {
        this.setBaseEntity(EntityType.BLAZE);
        return this;
    }

    public CombatantMob setProjectileType(RuneProjectile projectileType) {
        this.projectileType = projectileType;
        return this;
    }

    public void setBaseEntity(EntityType type) {
        super.setBaseEntity(type);
    }

    public int getCombatLvl() {
        return this.combatLvl;
    }

    public EnumMap<ModifierType, Modifier> getModifiers() {
        return modifiers;
    }

    public EnumMap<SkillType, Skill> getSkills() {
        return skills;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = Math.min(health, maxHealth);
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    @Override
    public Entity spawn(UUID uuid, Location location) {
        Entity spawnedEntity = super.spawn(uuid, location);

        if (!(spawnedEntity instanceof LivingEntity livingEntity)) {
            return spawnedEntity;
        }

        List<ItemStack> spawnedWithEquipment = MobUtils.getMobEquipment(livingEntity);
        List<Modifier> newModifiers = new ArrayList<>(modifiers.values());

        spawnedWithEquipment.stream()
                .filter(Objects::nonNull)
                .flatMap(itemStack -> {
                    AbstractItemType item = RuneQuestPlugin.getItemRegistry().getType(itemStack);
                    return item != null
                            ? Stream.of(Map.entry(itemStack, item))
                            : Stream.empty();
                })
                .filter(entry -> {
                    AbstractItemType abstractItemType = entry.getValue();
                    return abstractItemType instanceof EquipableItemType;
                })
                .forEach(entry -> {
                    newModifiers.addAll(((EquipableItemType) entry.getValue()).getModifiers());
                });


        List<Modifier> combined = ModifierUtils.combineModifiersByType(newModifiers);
        this.modifiers.clear();

        combined.forEach(m -> this.modifiers.put(m.type(), m));

        return spawnedEntity;
    }

    public void registerHit(Hit hit) {
        if (!(getEntity() instanceof LivingEntity living) || super.isInvulnerable()) {
            return;
        }

        // Notify mob mechanics of player damage
        if (hit.getAttacker().getEntity() instanceof Player playerAttacker) {
            RunePlayer runePlayer = RuneQuestPlugin.getPlayerManager().getRunePlayer(playerAttacker);
            if (runePlayer != null) {
                AnalyticsUtils.incrementServerAndPlayer(runePlayer, AnalyticsKey.TOTAL_DAMAGE_DEALT, hit.getDamage());
                this.getMobMechanics().values().forEach(m -> m.onDamagedByPlayer(runePlayer, hit));
            }
        }


        // Get max Minecraft health for this entity
        AttributeInstance attr = living.getAttribute(Attribute.MAX_HEALTH);
        double maxHealth = attr != null ? attr.getValue() : 20.0; // fallback

        if (maxHealth <= 0) maxHealth = 1.0;

        // Apply the hit to the custom health system
        this.health = Math.max(0, this.health - hit.getDamage());

        // Scale custom health to the entity's real health bar
        double scaledHealth = maxHealth * ((double) this.health / this.maxHealth);

        scaledHealth = Math.max(0, Math.min(maxHealth, scaledHealth));

        // Apply the scaled health to the entity
        this.xpHandler.registerHit(hit);
        this.lastDamagedAt = System.currentTimeMillis();

        // Set killer metadata on last hit
        if (scaledHealth == 0) {
            if (this.xpHandler.getPlayerWithMostPendingXP() != null) {
                living.setMetadata(MetadataKeys.MOST_DAMAGING_PLAYER_UUID.asString(), new FixedMetadataValue(RuneQuestPlugin.getInstance(), this.xpHandler.getPlayerWithMostPendingXP().toString()));
            }

            if (hit.getAttacker().getEntity() instanceof Player playerAttacker) {
                living.setMetadata(MetadataKeys.KILLER_UUID.asString(), new FixedMetadataValue(RuneQuestPlugin.getInstance(), playerAttacker.getUniqueId().toString()));

                RunePlayer runePlayer = RuneQuestPlugin.getPlayerManager().getRunePlayer(playerAttacker);
                if (runePlayer != null) {
                    AnalyticsUtils.incrementServerAndPlayer(runePlayer, AnalyticsKey.MOBS_KILLED, 1);
                    this.getMobMechanics().values().forEach(m -> m.onDeath(runePlayer, hit));

                }
                RuneEntityDeathEvent event = new RuneEntityDeathEvent(hit.getAttacker(), hit.getDefender(), hit, hit.getAttackType());
                Bukkit.getServer().getPluginManager().callEvent(event);

            }
        }

        living.setHealth(scaledHealth);

    }

    @Override
    public void onPlayerInteractWithEntity(PlayerInteractAtEntityEvent event, RunePlayer player) {
        event.setCancelled(true);
    }

    @Override
    public void onEntityDamaged(EntityDamageEvent event) {
        if (event.getCause() != EntityDamageEvent.DamageCause.CUSTOM) {
            event.setDamage(0);
        }

        Entity entity = this.getEntity();

        if (entity == null) {
            return;
        }

        if (health == 0) {

            if (entity instanceof LivingEntity livingEntity) {
                livingEntity.setHealth(0.0);
            } else {
                entity.remove();
            }
        }
    }

    @Override
    public void onEntityAttackPlayer(EntityDamageByEntityEvent event, RunePlayer runePlayer) {
        this.playAnimation("attack", 0f, 0.3f, 1);
    }

    @Override
    public void onEntityDeath(EntityDeathEvent event) {
        this.xpHandler.distributePendingXP();
    }

    @Override
    public void onEntityShootProjectile(ProjectileLaunchEvent event) {
        Projectile projectile = event.getEntity();

        if (this.getMobMechanics().containsKey(RangedMobMechanic.class)) {
            event.setCancelled(true);
            return;
        }

        if (projectileType == null) return;
        Projectile newProjectile = projectileType.replaceProjectile(projectile);
    }


    public long getLastDamagedAt() {
        return lastDamagedAt;
    }

    public List<RunePlayer> getRecentAttackers(Long durationMillis) {
        return this.xpHandler.getRecentAttackers(durationMillis);
    }

    public void shootProjectileAtTarget(LivingEntity target) {
        if (!(getEntity() instanceof LivingEntity shooter)) {
            return;
        }

        this.projectileType.shootProjectileAtTarget(shooter, target);

    }

    @Override
    public boolean isSpecialAttacking() {
        return isSpecialAttacking;
    }

    public void setSpecialAttacking(boolean specialAttacking) {
        isSpecialAttacking = specialAttacking;
    }

}
