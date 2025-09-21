package org.zuma.examplePlugin.domain.spawnzone.base;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.persistence.PersistentDataType;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.logger.constants.LogLevel;
import org.zuma.examplePlugin.core.utils.MathUtils;
import org.zuma.examplePlugin.core.utils.MobUtils;
import org.zuma.examplePlugin.domain.spawnzone.SpawnZoneHandler;
import org.zuma.examplePlugin.domain.spawnzone.SpawnZoneMobRecord;
import org.zuma.examplePlugin.domain.spawnzone.SpawnZoneType;
import org.zuma.examplePlugin.domain.spawnzone.meta.AbstractSpawnZoneMeta;
import org.zuma.examplePlugin.events.spawnzone.SpawnZoneWaveEvent;

import javax.annotation.Nullable;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public abstract class AbstractSpawnZone {
    private static final int BLOCK_DISTANCE_FROM_CENTER_KILL_THRESHOLD = 128; // If too far away, kill entity.
    private static final Random RANDOM = new Random();

    private List<SpawnZoneMobRecord> mobRecords;
    private final SpawnZoneType type;
    private final int id, waveRateSeconds, deadThresholdForWave;
    private final Location centerPoint;
    private Long lastWaveAttemptOccurred;

    private final Set<UUID> spawnedAndLoadedEntities;
    private int maximumMobs, lastWaveSpawnedAmount;


    public AbstractSpawnZone(SpawnZoneType type, AbstractSpawnZoneMeta meta) {
        this.mobRecords = new ArrayList<>();
        this.type = type;
        this.id = meta.id();
        this.centerPoint = meta.location();
        this.waveRateSeconds = meta.waveRateSeconds();
        this.deadThresholdForWave = meta.deadThresholdForWave();
        this.spawnedAndLoadedEntities = ConcurrentHashMap.newKeySet();
        this.lastWaveAttemptOccurred = 0L;
        this.maximumMobs = 0;
        this.lastWaveSpawnedAmount = 0;

    }

    // IS MUTATED BY HANDLER!
    public Set<UUID> getSpawnedAndLoadedEntities() {
        return spawnedAndLoadedEntities;
    }

    public abstract Location getNextMobLocation();

    public void setMobRecords(List<SpawnZoneMobRecord> mobRecords) {
        this.mobRecords = mobRecords;
        mobRecords.forEach(record -> maximumMobs += record.amount());
    }

    public List<SpawnZoneMobRecord> getMobRecords() {
        return this.mobRecords;
    }

    public int getId() {
        return this.id;
    }

    public double getDeadEntitiesPercentage() {
        if (lastWaveSpawnedAmount == 0) return 0;

        int alive = spawnedAndLoadedEntities.size();
        int dead = lastWaveSpawnedAmount - alive;

        return ((double) dead / lastWaveSpawnedAmount) * 100.0;
    }


    public void clearAliveEntities() {
        Iterator<UUID> iterator = spawnedAndLoadedEntities.iterator();

        while (iterator.hasNext()) {
            UUID uuid = iterator.next();
            Entity entity = Bukkit.getEntity(uuid);

            iterator.remove();

            if (entity == null) {
                continue;
            }

            if (entity.isValid() || !entity.isDead()) {
                entity.remove();
            }


        }
    }

    public void spawnWave() {
        lastWaveAttemptOccurred = System.currentTimeMillis();
        lastWaveSpawnedAmount = spawnedAndLoadedEntities.size();

        Bukkit.getServer().getPluginManager().callEvent(new SpawnZoneWaveEvent(this));

        mobRecords.forEach(record -> {
            if (RANDOM.nextInt(100) >= record.spawnChancePercentage()) {
                return;
            }

            for (int i = 0; i < record.amount(); i++) {

                if (spawnedAndLoadedEntities.size() >= maximumMobs) {
                    return;
                }

                Location mobLocation = null;
                int maxRetries = 3;
                int attempts = 0;

                while (attempts < maxRetries) {
                    Location candidate = getNextMobLocation();
                    attempts++;

                    // Do not search for safe location for point spawns.
                    if (type == SpawnZoneType.POINT) {
                        mobLocation = candidate;
                        break;
                    }

                    if (candidate == null) {
                        RuneQuestPlugin.getRuneLogger().log(LogLevel.WARN, "Spawn location candidate was null for mob in Drop Zone ID: " + id + " type: " + this.type);
                        continue;
                    }

                    if (MobUtils.isSafeMobLocation(candidate)) {
                        mobLocation = candidate;
                        break; // valid location found, exit loop
                    }
                }


                if (mobLocation == null) {
                    // Skip if failed getting location
                    continue;
                }

                Entity spawnedEntity = RuneQuestPlugin.getMobRegistry().spawnMob(UUID.randomUUID(), MobUtils.getGroundLocation(mobLocation), record.mobId().getId());

                if (spawnedEntity == null) {
                    RuneQuestPlugin.getRuneLogger().log(LogLevel.WARN, "Unable to spawn entity. Entity not found. Drop Zone ID: " + id + " Entity ID: " + record.mobId() + " (numeric: " + record.mobId().getId() + ")");
                    continue;
                }

                this.lastWaveSpawnedAmount++;
                spawnedEntity.getPersistentDataContainer().set(SpawnZoneHandler.ZONE_KEY, PersistentDataType.INTEGER, this.id);
                spawnedAndLoadedEntities.add(spawnedEntity.getUniqueId());
            }
        });

    }

    private void waveSpawnTick() {
        if (System.currentTimeMillis() - lastWaveAttemptOccurred <= waveRateSeconds * 1000L) {
            return;
        }

        double deadPercentage = getDeadEntitiesPercentage();

        if (!spawnedAndLoadedEntities.isEmpty() && deadPercentage < deadThresholdForWave) {
            return;
        }

        spawnWave();
    }

    // Kill if entity is too far away
    private void mobDistanceTick() {
        Iterator<UUID> iterator = spawnedAndLoadedEntities.iterator();
        while (iterator.hasNext()) {
            UUID uuid = iterator.next();
            Entity entity = Bukkit.getEntity(uuid);
            if (entity == null) continue;

            if (!entity.isValid() || entity.isDead()) {
                iterator.remove();
                continue;
            }

            Location entityLocation = entity.getLocation();
            if (MathUtils.getLocationsDistanceXZ(centerPoint, entityLocation) > BLOCK_DISTANCE_FROM_CENTER_KILL_THRESHOLD) {
                entity.remove();
                iterator.remove();
            }
        }

    }

    public void runTickIfDue() {
        if (!this.getCenterPoint().getWorld().isChunkLoaded(this.getCenterPoint().getBlockX() >> 4, this.getCenterPoint().getBlockZ() >> 4)) {
            return;
        }

        waveSpawnTick();
        mobDistanceTick();
    }

    @Nullable
    public Integer getWaveRateSeconds() {
        return waveRateSeconds;
    }

    @Nullable
    public Integer getDeadThresholdForWave() {
        return deadThresholdForWave;
    }

    public Location getCenterPoint() {
        return centerPoint;
    }

    public SpawnZoneType getType() {
        return type;
    }

    public int getAliveMobCount() {
        return spawnedAndLoadedEntities.size();
    }

    public int getMaxMobs() {
        return maximumMobs;
    }

    public int getLastWaveSpawnedAmount() {
        return lastWaveSpawnedAmount;
    }


}
