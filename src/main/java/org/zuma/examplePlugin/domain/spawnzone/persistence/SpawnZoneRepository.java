package org.zuma.examplePlugin.domain.spawnzone.persistence;

import com.google.gson.Gson;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.zuma.examplePlugin.core.meta.VersionedJson;
import org.zuma.examplePlugin.domain.mob.constants.MobType;
import org.zuma.examplePlugin.domain.spawnzone.SpawnZoneMobRecord;
import org.zuma.examplePlugin.domain.spawnzone.SpawnZoneRecord;
import org.zuma.examplePlugin.domain.spawnzone.SpawnZoneType;
import org.zuma.examplePlugin.domain.spawnzone.impl.PointSpawnZone;
import org.zuma.examplePlugin.domain.spawnzone.meta.AbstractSpawnZoneMeta;
import org.zuma.examplePlugin.domain.spawnzone.meta.v1.AreaSpawnZoneMeta;
import org.zuma.examplePlugin.domain.spawnzone.meta.v1.PointSpawnZoneMeta;

import java.util.List;

public class SpawnZoneRepository {
    private final Gson gson = new Gson();

    private VersionedJson toVersionedJson(Object obj, int version) {
        return new VersionedJson(gson.toJson(obj), version);
    }

    public List<SpawnZoneRecord> getAll() {
        return List.of(

                // Drakkar city guards start
                new SpawnZoneRecord(new AbstractSpawnZoneMeta(0, SpawnZoneType.POINT, 20, 1,
                        new Location(Bukkit.getWorld("world"), 188.5, 67, -161.5)), toVersionedJson(new PointSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.CITY_GUARD_STATIC, 1, 100)
                ), false), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(1, SpawnZoneType.POINT, 200, 1,
                        new Location(Bukkit.getWorld("world"), 196.5, 67, -161.5)), toVersionedJson(new PointSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.CITY_GUARD_STATIC, 1, 100)
                ), false), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(2, SpawnZoneType.POINT, 20, 1,
                        new Location(Bukkit.getWorld("world"), 116.5, 67, -86.5)), toVersionedJson(new PointSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.CITY_GUARD_STATIC, 1, 100)
                ), false), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(3, SpawnZoneType.POINT, 20, 1,
                        new Location(Bukkit.getWorld("world"), 116.5, 67, -79.5)), toVersionedJson(new PointSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.CITY_GUARD_STATIC, 1, 100)
                ), false), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(4, SpawnZoneType.AREA, 20, 1,
                        new Location(Bukkit.getWorld("world"), 191, 73, -72)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.CITY_GUARD_WALKING, 10, 100)
                ), 100, false), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(5, SpawnZoneType.AREA, 20, 1,
                        new Location(Bukkit.getWorld("world"), 192, 72, -109)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.CITY_GUARD_WALKING, 10, 100)
                ), 100, false), 1)),


                // Drakkar city guards end

        new SpawnZoneRecord(new AbstractSpawnZoneMeta(6, SpawnZoneType.AREA, 30, 20,
                new Location(Bukkit.getWorld("world"), 191, 73, -72)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                new SpawnZoneMobRecord(MobType.RAT, 50, 100)
        ), 200, false), 1))

        );
    }
}


/*


public record AbstractSpawnZoneMeta(int id, SpawnZoneType type, @Nullable Integer waveRateSeconds,
                                    @Nullable Integer deadThresholdForWave,
                                    Location location) {
}
public record PointSpawnZoneMeta(List<SpawnZoneMobRecord> mobRecords, boolean spawnAtHighestBlock) {
}


 */