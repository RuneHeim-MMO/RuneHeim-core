package org.zuma.examplePlugin.domain.spawnzone.persistence;

import com.google.gson.Gson;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.zuma.examplePlugin.core.meta.VersionedJson;
import org.zuma.examplePlugin.domain.mob.constants.MobType;
import org.zuma.examplePlugin.domain.spawnzone.SpawnZoneMobRecord;
import org.zuma.examplePlugin.domain.spawnzone.SpawnZoneRecord;
import org.zuma.examplePlugin.domain.spawnzone.SpawnZoneType;
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

                // Drakkar and surroundings
                new SpawnZoneRecord(new AbstractSpawnZoneMeta(6, SpawnZoneType.AREA, 30, 20,
                        new Location(Bukkit.getWorld("world"), 191, 73, -72)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.RAT, 10, 100)
                ), 200, false), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(7, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), 199, 68, -192)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.GOBLIN_1_BASE_1, 5, 100),
                        new SpawnZoneMobRecord(MobType.GOBLIN_2_BASE_1, 5, 100),
                        new SpawnZoneMobRecord(MobType.GOBLIN_3_BASE_1, 5, 100),
                        new SpawnZoneMobRecord(MobType.GOBLIN_4_BASE_1, 5, 100),
                        new SpawnZoneMobRecord(MobType.GOBLIN_5_BASE_1, 5, 100)
                ), 50, false), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(8, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), 63, 68, -81)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.GOBLIN_1_BASE_1, 2, 100),
                        new SpawnZoneMobRecord(MobType.GOBLIN_2_BASE_1, 2, 100),
                        new SpawnZoneMobRecord(MobType.GOBLIN_3_BASE_1, 2, 100),
                        new SpawnZoneMobRecord(MobType.GOBLIN_4_BASE_1, 2, 100),
                        new SpawnZoneMobRecord(MobType.GOBLIN_5_BASE_1, 2, 100)
                ), 50, false), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(9, SpawnZoneType.POINT, 30, 1,
                        new Location(Bukkit.getWorld("world"), 173, 73, -50)), toVersionedJson(new PointSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.GENERAL_STORE, 1, 100)
                ), false), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(10, SpawnZoneType.POINT, 30, 1,
                        new Location(Bukkit.getWorld("world"), 209, 73, -98)), toVersionedJson(new PointSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.DRAKKAR_BLACKSMITH, 1, 100)
                ), false), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(11, SpawnZoneType.POINT, 30, 1,
                        new Location(Bukkit.getWorld("world"), 257.5, 73, -58.5)), toVersionedJson(new PointSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.BANK_TELLER_1, 1, 100)
                ), false), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(12, SpawnZoneType.POINT, 30, 1,
                        new Location(Bukkit.getWorld("world"), 257.5, 73, -63.5)), toVersionedJson(new PointSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.BANK_TELLER_2, 1, 100)
                ), false), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(13, SpawnZoneType.POINT, 30, 1,
                        new Location(Bukkit.getWorld("world"), 257.5, 73, -68.5)), toVersionedJson(new PointSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.BANK_TELLER_3, 1, 100)
                ), false), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(14, SpawnZoneType.POINT, 30, 1,
                        new Location(Bukkit.getWorld("world"), 247, 74, -43.5)), toVersionedJson(new PointSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.INVESTMENT_BANKER, 1, 100)
                ), false), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(15, SpawnZoneType.POINT, 30, 1,
                        new Location(Bukkit.getWorld("world"), 153.5, 73, -50)), toVersionedJson(new PointSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.JEWLER, 1, 100)
                ), false), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(16, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), 324, 68, -205)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.BOAR_BROWN, 1, 100),
                        new SpawnZoneMobRecord(MobType.BOAR_GRAY, 1, 100),
                        new SpawnZoneMobRecord(MobType.GOBLIN_4_BASE_1, 5, 100),
                        new SpawnZoneMobRecord(MobType.GOBLIN_5_BASE_1, 5, 100)
                ), 50, false), 1)),


                new SpawnZoneRecord(new AbstractSpawnZoneMeta(17, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), 374, 68, -127)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.BOAR_GRAY, 2, 100),
                        new SpawnZoneMobRecord(MobType.BOAR_LIGHT_BROWN, 2, 100)
                ), 30, false), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(18, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), 235, 70, 50)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.GOBLIN_1_BASE_1, 7, 100),
                        new SpawnZoneMobRecord(MobType.GOBLIN_2_BASE_1, 7, 100),
                        new SpawnZoneMobRecord(MobType.GOBLIN_3_BASE_1, 7, 100),
                        new SpawnZoneMobRecord(MobType.GOBLIN_4_BASE_1, 10, 100),
                        new SpawnZoneMobRecord(MobType.GOBLIN_5_BASE_1, 10, 100),
                        new SpawnZoneMobRecord(MobType.GOBLIN_1_BASE_4, 3, 100),
                        new SpawnZoneMobRecord(MobType.GOBLIN_2_BASE_4, 3, 100),
                        new SpawnZoneMobRecord(MobType.GOBLIN_3_BASE_4, 3, 100)
                ), 150, false), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(19, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), 18, 71, 19)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.GOBLIN_1_BASE_1, 7, 100),
                        new SpawnZoneMobRecord(MobType.GOBLIN_2_BASE_1, 7, 100),
                        new SpawnZoneMobRecord(MobType.GOBLIN_3_BASE_1, 7, 100),
                        new SpawnZoneMobRecord(MobType.GOBLIN_4_BASE_1, 10, 100),
                        new SpawnZoneMobRecord(MobType.GOBLIN_5_BASE_1, 10, 100),
                        new SpawnZoneMobRecord(MobType.GOBLIN_1_BASE_4, 3, 100),
                        new SpawnZoneMobRecord(MobType.GOBLIN_2_BASE_4, 3, 100),
                        new SpawnZoneMobRecord(MobType.GOBLIN_3_BASE_4, 3, 100),
                        new SpawnZoneMobRecord(MobType.BOAR_LIGHT_BROWN, 2, 100)
                ), 150, false), 1)),


                new SpawnZoneRecord(new AbstractSpawnZoneMeta(20, SpawnZoneType.POINT, 30, 1,
                        new Location(Bukkit.getWorld("world"), 19, 70, -203)), toVersionedJson(new PointSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.BOAR_BROWN_ALPHA, 1, 100)
                ), false), 1)),


                new SpawnZoneRecord(new AbstractSpawnZoneMeta(21, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), 105, 73, -245)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.GOBLIN_1_BASE_1, 5, 100),
                        new SpawnZoneMobRecord(MobType.GOBLIN_2_BASE_1, 5, 100),
                        new SpawnZoneMobRecord(MobType.GOBLIN_3_BASE_1, 5, 100),
                        new SpawnZoneMobRecord(MobType.GOBLIN_4_BASE_1, 3, 100),
                        new SpawnZoneMobRecord(MobType.GOBLIN_5_BASE_1, 2, 100),
                        new SpawnZoneMobRecord(MobType.GOBLIN_4_BASE_4, 3, 100),
                        new SpawnZoneMobRecord(MobType.GOBLIN_5_BASE_4, 3, 100),
                        new SpawnZoneMobRecord(MobType.BOAR_LIGHT_BROWN, 1, 100)
                ), 150, false), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(22, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), 480, 75, -58)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.HOGLIN_BASE_5, 5, 100)
                ), 80, false), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(23, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), 523, 71, 15)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.CHICKEN_BASE_2, 10, 100)
                ), 40, false), 1)),


                new SpawnZoneRecord(new AbstractSpawnZoneMeta(24, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), 520, 74, -35)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.CHICKEN_BASE_2, 10, 100)
                ), 40, false), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(25, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), 448, 75, -242)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.GOBLIN_1_BASE_4, 5, 100),
                        new SpawnZoneMobRecord(MobType.GOBLIN_2_BASE_4, 5, 100),
                        new SpawnZoneMobRecord(MobType.GOBLIN_3_BASE_4, 5, 100),
                        new SpawnZoneMobRecord(MobType.GOBLIN_4_BASE_4, 5, 100),
                        new SpawnZoneMobRecord(MobType.GOBLIN_5_BASE_4, 5, 100)
                ), 150, false), 1)),


                new SpawnZoneRecord(new AbstractSpawnZoneMeta(26, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), 448, 75, -47)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.GOBLIN_1_BASE_4, 5, 100),
                        new SpawnZoneMobRecord(MobType.GOBLIN_2_BASE_4, 5, 100),
                        new SpawnZoneMobRecord(MobType.GOBLIN_3_BASE_4, 5, 100),
                        new SpawnZoneMobRecord(MobType.GOBLIN_4_BASE_4, 5, 100),
                        new SpawnZoneMobRecord(MobType.GOBLIN_5_BASE_4, 5, 100)
                ), 150, false), 1)),


                new SpawnZoneRecord(new AbstractSpawnZoneMeta(27, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), 184, 65, 188)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.WOLF_BLACK_BASE_9, 2, 100),
                        new SpawnZoneMobRecord(MobType.WOLF_BLONDE_BASE_9, 2, 100)
                ), 50, false), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(28, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), -104, 87, 103)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.WOLF_BLACK_ALPHA_BASE_20, 3, 100),
                        new SpawnZoneMobRecord(MobType.WOLF_GRAY_LIGHT_ALPHA_BASE_20, 2, 100)
                ), 80, false), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(29, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), -77, 74, -210)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.GOBLIN_1_BASE_4, 5, 100),
                        new SpawnZoneMobRecord(MobType.GOBLIN_2_BASE_4, 5, 100),
                        new SpawnZoneMobRecord(MobType.GOBLIN_3_BASE_4, 5, 100),
                        new SpawnZoneMobRecord(MobType.GOBLIN_4_BASE_4, 5, 100),
                        new SpawnZoneMobRecord(MobType.GOBLIN_5_BASE_4, 5, 100)
                ), 150, false), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(30, SpawnZoneType.AREA, 30, 50,
                        new Location(Bukkit.getWorld("world"), 257, 69, -291)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.CHICKEN_BASE_2, 20, 100)
                ), 10, false), 1))





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