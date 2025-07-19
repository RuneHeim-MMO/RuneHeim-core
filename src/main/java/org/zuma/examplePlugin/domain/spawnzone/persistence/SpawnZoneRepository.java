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

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(0, SpawnZoneType.AREA, 3, 50,
                        new Location(Bukkit.getWorld("world"), 5055, 102, 4946)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.BOAR_BROWN, 10, 50),
                        new SpawnZoneMobRecord(MobType.BOAR_BROWN_ALPHA, 5, 30),
                        new SpawnZoneMobRecord(MobType.BOAR_GRAY, 10, 50),
                        new SpawnZoneMobRecord(MobType.BOAR_GRAY_ALPHA, 5, 30),
                        new SpawnZoneMobRecord(MobType.BOAR_LIGHT_BROWN, 10, 50),
                        new SpawnZoneMobRecord(MobType.BOAR_LIGHT_BROWN_ALPHA, 5, 30)
                ), 30, false), 1))

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