package org.zuma.examplePlugin.domain.spawnzone.base;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.persistence.PersistentDataType;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.logger.constants.LogLevel;
import org.zuma.examplePlugin.core.utils.MathUtils;
import org.zuma.examplePlugin.domain.spawnzone.SpawnZoneHandler;
import org.zuma.examplePlugin.domain.spawnzone.SpawnZoneMobRecord;
import org.zuma.examplePlugin.domain.spawnzone.SpawnZoneType;
import org.zuma.examplePlugin.domain.spawnzone.meta.AbstractSpawnZoneMeta;

import javax.annotation.Nullable;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public abstract class AbstractSpawnZone {
    private static final int BLOCK_DISTANCE_FROM_CENTER_KILL_THRESHOLD = 128; // If too far away, kill entity.
    private static final Random RANDOM = new Random();

    private List<SpawnZoneMobRecord> mobRecords;
    private final SpawnZoneType type;
    private final int id, waveRateSeconds, deadThresholdForWave;
    private final Location location;
    private Long lastWaveAttemptOccurred;

    private final Set<UUID> spawnedAndLoadedEntities;
    private int maximumMobs, lastWaveSpawnedAmount;


    public AbstractSpawnZone(SpawnZoneType type, AbstractSpawnZoneMeta meta) {
        this.mobRecords = new ArrayList<>();
        this.type = type;
        this.id = meta.id();
        this.location = meta.location();
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
                return;
            }

            if (!entity.isValid() || !entity.isDead()) {
                entity.remove();
            }


        }
    }

    public void spawnWave() {
        lastWaveAttemptOccurred = System.currentTimeMillis();
        lastWaveSpawnedAmount = spawnedAndLoadedEntities.size();
        // clearAliveEntities();


        mobRecords.forEach(record -> {
            if (RANDOM.nextInt(101) >= record.spawnChancePercentage()) {
                System.out.println("fail chance");
                return;
            }

            for (int i = 0; i < record.amount(); i++) {

                if (spawnedAndLoadedEntities.size() >= maximumMobs) {
                    return;
                }

                Location mobLocation = getNextMobLocation();

                if (mobLocation == null) {
                    RuneQuestPlugin.getRuneLogger().log(LogLevel.WARN, "Spawn location was null for mob in Drop Zone ID: " + id + " type: " + this.type);
                    continue;
                }

                Entity spawnedEntity = RuneQuestPlugin.getMobRegistry().spawnMob(UUID.randomUUID(), mobLocation, record.mobId().getId());

                if (spawnedEntity == null) {
                    RuneQuestPlugin.getRuneLogger().log(LogLevel.WARN, "Unable to spawn entity. Entity not found. Drop Zone ID: " + id + " Entity ID: " + record.mobId() + " (numeric: " + record.mobId().getId() + ")");
                    continue;
                }

                this.lastWaveSpawnedAmount++;
                spawnedEntity.getPersistentDataContainer().set(SpawnZoneHandler.ZONE_KEY, PersistentDataType.INTEGER, this.id);
                spawnedAndLoadedEntities.add(spawnedEntity.getUniqueId());
                System.out.println(mobLocation);
            }
        });

    }

    private void waveSpawnTick() {
        if (System.currentTimeMillis() - lastWaveAttemptOccurred <= waveRateSeconds * 1000L) {
            System.out.println("too fast");
            return;
        }

        double deadPercentage = getDeadEntitiesPercentage();

        if (!spawnedAndLoadedEntities.isEmpty() && deadPercentage < deadThresholdForWave) {
            System.out.println("dead " + deadPercentage + " need min threshold " + deadThresholdForWave + " last spawned amnt: " + lastWaveSpawnedAmount + " max mobs " + maximumMobs + " total uuids " + spawnedAndLoadedEntities.size());
            return;
        }

        System.out.println("dead " + deadPercentage + " need min threshold " + deadThresholdForWave + " last spawned amnt: " + lastWaveSpawnedAmount + " max mobs " + maximumMobs + " total uuids " + spawnedAndLoadedEntities.size());
        System.out.println("should spawn");
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
            if (MathUtils.getLocationsDistanceXZ(location, entityLocation) > BLOCK_DISTANCE_FROM_CENTER_KILL_THRESHOLD) {
                entity.remove();
                iterator.remove();
            }
        }

    }

    public void runTickIfDue() {
        if (!this.getLocation().getWorld().isChunkLoaded(this.getLocation().getBlockX() >> 4, this.getLocation().getBlockZ() >> 4)) {
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

    public Location getLocation() {
        return location;
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
