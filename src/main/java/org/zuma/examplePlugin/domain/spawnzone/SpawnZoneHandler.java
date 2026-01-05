package org.zuma.examplePlugin.domain.spawnzone;

import com.destroystokyo.paper.event.entity.EntityRemoveFromWorldEvent;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.event.world.ChunkUnloadEvent;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitTask;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.logger.constants.LogLevel;
import org.zuma.examplePlugin.core.utils.MathUtils;
import org.zuma.examplePlugin.domain.mob.constants.MobDataKey;
import org.zuma.examplePlugin.domain.spawnzone.base.AbstractSpawnZone;
import org.zuma.examplePlugin.domain.spawnzone.persistence.SpawnZoneRepository;

import javax.annotation.Nullable;
import java.util.*;

public class SpawnZoneHandler {
    public static final NamespacedKey ZONE_KEY =
            new NamespacedKey(RuneQuestPlugin.getInstance(), MobDataKey.SPAWN_ZONE_ID.toString());

    private final static long TICK_RATE = 3 * 20L; //  Every 3 sec
    private final Map<Integer, AbstractSpawnZone> spawnZones;
    private final Map<Long, List<AbstractSpawnZone>> spawnZonesByChunk = new HashMap<>();
    private final SpawnZoneRepository repository;
    private final SpawnZoneFactory factory;
    private int tickPartition = 0;
    private BukkitTask loop;

    public SpawnZoneHandler(SpawnZoneRepository repository) {
        this.spawnZones = new HashMap<>();
        this.repository = repository;
        this.factory = new SpawnZoneFactory();
    }

    public Collection<AbstractSpawnZone> getZones() {
        return this.spawnZones.values();
    }

    @Nullable
    public AbstractSpawnZone getClosest(Location loc) {
        if (spawnZones.isEmpty() || loc == null) {
            return null;
        }

        AbstractSpawnZone closest = null;
        double closestDistance = Double.MAX_VALUE;

        for (AbstractSpawnZone zone : spawnZones.values()) {
            Location zoneLoc = zone.getCenterPoint();
            if (zoneLoc == null) continue;

            double distance = MathUtils.getLocationsDistanceXZ(loc, zoneLoc);
            if (distance < closestDistance) {
                closestDistance = distance;
                closest = zone;
            }
        }

        return closest;
    }

    public void onChunkLoad(ChunkLoadEvent event) {
        Long key = event.getChunk().getChunkKey();

        List<AbstractSpawnZone> zonesInChunk = spawnZonesByChunk.get(key);
        if (zonesInChunk == null) {
            return;
        }

        for (AbstractSpawnZone zone : zonesInChunk) {
            if(zone.getId() == 267){
                System.out.println("Handling chunk load for zone id 267");
            }
            zone.handleChunkLoad();
        }

    }

    public void onChunkUnload(ChunkUnloadEvent event) {
        Chunk chunk = event.getChunk();
        Long key = event.getChunk().getChunkKey();

        List<AbstractSpawnZone> zonesInChunk = spawnZonesByChunk.get(key);
        if (zonesInChunk == null) {
            return;
        }

        for (AbstractSpawnZone zone : zonesInChunk) {
            if(zone.getId() == 267){
                System.out.println("Handling chunk unload for zone id 267");
            }
            zone.handleChunkUnload();
        }

        // Process entities after zones have cleared their alive lists
        for (Entity entity : chunk.getEntities()) {
            Integer zoneId = entity.getPersistentDataContainer().get(ZONE_KEY, PersistentDataType.INTEGER);

            if (RuneQuestPlugin.getMobRegistry().getMob(entity) == null) {
                // Not our mob, maybe item frame or smth
                continue;
            }

            if (zoneId == null) {
                // Unknown mob, remove - all mobs are controlled by spawn zones.
                entity.remove();
                continue;
            }

            AbstractSpawnZone matchingZone = spawnZones.get(zoneId);

            if (matchingZone == null) {
                // Unknown spawn zone, remove mob.
                entity.remove();
                continue;
            }

        }
    }

    public void onEntityDeath(EntityDeathEvent event) {
        LivingEntity entity = event.getEntity();
        UUID uuid = entity.getUniqueId();

        Integer zoneId = entity.getPersistentDataContainer().get(ZONE_KEY, PersistentDataType.INTEGER);
        if (zoneId == null) {
            return;
        }
        AbstractSpawnZone zone = spawnZones.get(zoneId);

        if (zone == null) {
            return;
        }

        zone.getSpawnedAndLoadedEntities().remove(uuid);

    }

    public void onEntityRemove(EntityRemoveFromWorldEvent event) {
        Entity entity = event.getEntity();

        UUID uuid = entity.getUniqueId();
        Integer zoneId = entity.getPersistentDataContainer().get(ZONE_KEY, PersistentDataType.INTEGER);
        if (zoneId == null) {
            return;
        }

        AbstractSpawnZone zone = spawnZones.get(zoneId);
        if (zone == null) {
            return;
        }

        zone.getSpawnedAndLoadedEntities().remove(uuid);
    }

    public void initializeFromDb() {
        this.repository.getAll().forEach(record -> {
            AbstractSpawnZone zone = factory.createZone(record.meta(), record.json());
            registerSpawnZone(zone);
        });

        RuneQuestPlugin.getRuneLogger().log(LogLevel.INFO, "Loaded " + spawnZones.size() + " spawn zones.");
    }

    public void registerSpawnZone(AbstractSpawnZone zone) {
        if (spawnZones.containsKey(zone.getId())) {
            throw new IllegalStateException("Spawn zone with id " + zone.getId() + " already exists!");
        }

        spawnZones.put(zone.getId(), zone);
        spawnZonesByChunk.computeIfAbsent(zone.getCenterPoint().getChunk().getChunkKey(), k -> new ArrayList<>()).add(zone);
    }

    public void unregisterSpawnZone(int id) {
        AbstractSpawnZone zone = spawnZones.get(id);
        if (zone != null) {
            zone.clearAliveEntities();
            spawnZones.remove(id);

            Long chunkKey = zone.getCenterPoint().getChunk().getChunkKey();
            List<AbstractSpawnZone> list = spawnZonesByChunk.get(chunkKey);

            if (list != null) {
                list.remove(zone);
                if (list.isEmpty()) spawnZonesByChunk.remove(chunkKey);
            }
        }
    }


    public void startLoop() {
        if (loop != null) {
            loop.cancel();
        }
        // Run 3x faster
        loop = Bukkit.getScheduler().runTaskTimer(
                RuneQuestPlugin.getInstance(), this::tick, TICK_RATE / 3, TICK_RATE / 3
        );
    }

    public void onDisable() {
        if (loop != null) {
            loop.cancel();
        }
        RuneQuestPlugin.getRuneLogger().log(LogLevel.INFO, "Spawn zone loop stopped.");
    }

    private void tick() {
        if (loop.isCancelled()) {
            return;
        }

        Collection<AbstractSpawnZone> zones = spawnZones.values();
        int totalZones = zones.size();
        if (totalZones == 0) return;

        int partitionSize = (int) Math.ceil(totalZones / 3.0);
        int start = tickPartition * partitionSize;
        int end = Math.min(start + partitionSize, totalZones);

        // Iterate using index on the collection directly
        int currentIndex = 0;
        for (AbstractSpawnZone zone : zones) {
            if (currentIndex >= start && currentIndex < end) {
                zone.runTickIfDue();
            }
            currentIndex++;
            // Early exit if we've processed our partition
            if (currentIndex >= end) break;
        }

        tickPartition = (tickPartition + 1) % 3;
    }

    public SpawnZoneFactory getFactory() {
        return factory;
    }
}
