package org.zuma.examplePlugin.domain.mob;

import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Entity;
import org.bukkit.persistence.PersistentDataType;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.logger.constants.LogLevel;
import org.zuma.examplePlugin.domain.mob.base.AbstractMob;
import org.zuma.examplePlugin.domain.mob.constants.MobDataKey;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.HashMap;
import java.util.UUID;

public class MobRegistry {
    private final MobTypeRegistry typeRegistry;
    private final HashMap<UUID, AbstractMob> mobs = new HashMap<>();

    public MobRegistry(MobTypeRegistry typeRegistry) {
        this.typeRegistry = typeRegistry;
    }

    @Nullable
    public AbstractMob getByUUID(UUID uuid) {
        return mobs.get(uuid);
    }

    public Collection<AbstractMob> getAll() {
        return this.mobs.values();
    }

    @Nullable
    public AbstractMob getMob(Entity entity) {
        NamespacedKey key = new NamespacedKey(RuneQuestPlugin.getInstance(), MobDataKey.MOB_UUID.asString());
        String uuid = entity.getPersistentDataContainer().get(key, PersistentDataType.STRING);

        return uuid != null ? getByUUID(UUID.fromString(uuid)) : null;
    }

    @Nullable
    public Entity spawnMob(UUID uuid, Location location, String id) {
        AbstractMob existing = this.mobs.get(uuid);

        if (existing != null) {
            RuneQuestPlugin.getRuneLogger().log(LogLevel.WARN, "[DUPLICATE] Mob with id " + uuid + " already exists.");
            return null;
        }

        AbstractMob mob = typeRegistry.create(id);

        try {
            Entity spawnedEntity = mob.spawn(uuid, location);
            this.mobs.put(uuid, mob);

            if (mob.getEntity() != null) MobSessionManager.tagEntity(mob.getEntity());

            return spawnedEntity;
        } catch (Exception e) {
            RuneQuestPlugin.getRuneLogger().log(LogLevel.WARN, "Failed to spawn a " + mob.getDisplayName() + " mob! ", e);
        }

        return null;
    }

    public void removeMob(UUID uuid) {
        mobs.remove(uuid);
    }
}
