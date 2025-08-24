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
                        new SpawnZoneMobRecord(MobType.BOAR_BROWN_BASE_3, 1, 100),
                        new SpawnZoneMobRecord(MobType.BOAR_GRAY_BASE_3, 1, 100),
                        new SpawnZoneMobRecord(MobType.GOBLIN_4_BASE_1, 5, 100),
                        new SpawnZoneMobRecord(MobType.GOBLIN_5_BASE_1, 5, 100)
                ), 50, false), 1)),


                new SpawnZoneRecord(new AbstractSpawnZoneMeta(17, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), 374, 68, -127)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.BOAR_GRAY_BASE_3, 2, 100),
                        new SpawnZoneMobRecord(MobType.BOAR_LIGHT_BROWN_BASE_3, 2, 100)
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
                        new SpawnZoneMobRecord(MobType.BOAR_LIGHT_BROWN_BASE_3, 2, 100)
                ), 150, false), 1)),


                new SpawnZoneRecord(new AbstractSpawnZoneMeta(20, SpawnZoneType.POINT, 30, 1,
                        new Location(Bukkit.getWorld("world"), 19, 70, -203)), toVersionedJson(new PointSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.BOAR_BROWN_ALPHA_BASE10, 1, 100)
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
                        new SpawnZoneMobRecord(MobType.BOAR_LIGHT_BROWN_BASE_3, 1, 100)
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
                        new Location(Bukkit.getWorld("world"), 408, 75, -242)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
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
                        new SpawnZoneMobRecord(MobType.WOLF_BLACK_BASE_20, 2, 100),
                        new SpawnZoneMobRecord(MobType.WOLF_BLONDE_BASE_22, 2, 100)
                ), 50, false), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(28, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), -104, 87, 103)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.WOLF_BLACK_ALPHA_BASE_36, 3, 100),
                        new SpawnZoneMobRecord(MobType.WOLF_GRAY_LIGHT_ALPHA_BASE_34, 2, 100),
                        new SpawnZoneMobRecord(MobType.DEER_BABY_BASE_15, 3, 100)
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
                ), 10, false), 1)),

                // Between eldarwyn and brineport
                new SpawnZoneRecord(new AbstractSpawnZoneMeta(31, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), -140, 87, 338)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.WOLF_BLACK_ALPHA_BASE_36, 2, 100),
                        new SpawnZoneMobRecord(MobType.WOLF_GRAY_LIGHT_ALPHA_BASE_34, 4, 100),
                        new SpawnZoneMobRecord(MobType.DEER_FEMALE_BASE_29, 2, 100),
                        new SpawnZoneMobRecord(MobType.DEER_BABY_BASE_15, 3, 100)
                ), 150, false), 1)),


                new SpawnZoneRecord(new AbstractSpawnZoneMeta(32, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), -260, 120, 122)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.WOLF_BLACK_BASE_20, 2, 100),
                        new SpawnZoneMobRecord(MobType.WOLF_GRAY_LIGHT_BASE_26, 4, 100),
                        new SpawnZoneMobRecord(MobType.DEER_FEMALE_BASE_29, 2, 100),
                        new SpawnZoneMobRecord(MobType.DEER_BABY_BASE_15, 3, 100)
                ), 150, false), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(33, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), -364, 120, -172)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.WOLF_BLACK_ALPHA_BASE_36, 2, 100),
                        new SpawnZoneMobRecord(MobType.WOLF_GRAY_LIGHT_ALPHA_BASE_34, 4, 100)
                ), 150, false), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(34, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), -559, 120, -45)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.WOLF_BLONDE_ALPHA_BASE_30, 10, 100),
                        new SpawnZoneMobRecord(MobType.WOLF_GRAY_DARK_ALPHA_BASE_32, 10, 100)
                ), 150, false), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(35, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), -273, 120, -419)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.WOLF_GRAY_LIGHT_ALPHA_BASE_34, 2, 100),
                        new SpawnZoneMobRecord(MobType.WOLF_BLACK_BASE_20, 4, 100)
                ), 150, false), 1)),

                // Boars Den
                new SpawnZoneRecord(new AbstractSpawnZoneMeta(36, SpawnZoneType.AREA, 15, 15,
                        new Location(Bukkit.getWorld("world"), -594, 100, -560)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.BOAR_BROWN_ALPHA_BASE10, 4, 75),
                        new SpawnZoneMobRecord(MobType.BOAR_GRAY_ALPHA_BASE_12, 4, 75),
                        new SpawnZoneMobRecord(MobType.BOAR_LIGHT_BROWN_ALPHA_BASE_14, 4, 50)
                ), 50, false), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(37, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), -508, 140, -465)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.WOLF_GRAY_LIGHT_ALPHA_BASE_34, 2, 100),
                        new SpawnZoneMobRecord(MobType.WOLF_BLACK_BASE_20, 4, 100)
                ), 150, false), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(38, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), -721, 100, -525)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.WOLF_GRAY_LIGHT_ALPHA_BASE_34, 2, 100),
                        new SpawnZoneMobRecord(MobType.WOLF_BLACK_BASE_20, 4, 100)
                ), 150, false), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(39, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), -692, 150, -700)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.WOLF_GRAY_LIGHT_ALPHA_BASE_34, 2, 100),
                        new SpawnZoneMobRecord(MobType.WOLF_BLACK_BASE_20, 4, 100)
                ), 150, false), 1)),


                new SpawnZoneRecord(new AbstractSpawnZoneMeta(40, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), -1116, 100, -345)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.WOLF_GRAY_LIGHT_ALPHA_BASE_34, 2, 100),
                        new SpawnZoneMobRecord(MobType.WOLF_BLACK_BASE_20, 4, 100)
                ), 100, false), 1)),


                new SpawnZoneRecord(new AbstractSpawnZoneMeta(41, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), -746, 150, -161)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.WOLF_GRAY_LIGHT_ALPHA_BASE_34, 1, 100),
                        new SpawnZoneMobRecord(MobType.WOLF_BLACK_BASE_20, 2, 100)
                ), 90, false), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(42, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), -405, 150, -733)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.WOLF_BLONDE_ALPHA_BASE_30, 1, 100),
                        new SpawnZoneMobRecord(MobType.WOLF_GRAY_DARK_BASE_24, 2, 100),
                        new SpawnZoneMobRecord(MobType.DEER_BASE_34, 3, 100),
                        new SpawnZoneMobRecord(MobType.DEER_FEMALE_BASE_29, 2, 100),
                        new SpawnZoneMobRecord(MobType.DEER_BABY_BASE_15, 1, 100)
                ), 150, false), 1)),


                new SpawnZoneRecord(new AbstractSpawnZoneMeta(43, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), -187, 150, -600)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.WOLF_BLONDE_ALPHA_BASE_30, 1, 100),
                        new SpawnZoneMobRecord(MobType.WOLF_GRAY_DARK_BASE_24, 2, 100),
                        new SpawnZoneMobRecord(MobType.DEER_BASE_34, 3, 100),
                        new SpawnZoneMobRecord(MobType.DEER_FEMALE_BASE_29, 2, 100),
                        new SpawnZoneMobRecord(MobType.DEER_BABY_BASE_15, 1, 100)
                ), 80, false), 1)),


                new SpawnZoneRecord(new AbstractSpawnZoneMeta(44, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), 205, 150, -622)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.WOLF_BLONDE_ALPHA_BASE_30, 1, 100),
                        new SpawnZoneMobRecord(MobType.WOLF_GRAY_DARK_BASE_24, 2, 100),
                        new SpawnZoneMobRecord(MobType.DEER_BASE_34, 10, 100),
                        new SpawnZoneMobRecord(MobType.DEER_FEMALE_BASE_29, 5, 100),
                        new SpawnZoneMobRecord(MobType.DEER_BABY_BASE_15, 3, 100)
                ), 150, false), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(45, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), -21, 100, -400)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.WOLF_BLONDE_ALPHA_BASE_30, 1, 100),
                        new SpawnZoneMobRecord(MobType.WOLF_GRAY_DARK_BASE_24, 2, 100),
                        new SpawnZoneMobRecord(MobType.DEER_BASE_34, 3, 100),
                        new SpawnZoneMobRecord(MobType.DEER_FEMALE_BASE_29, 5, 100),
                        new SpawnZoneMobRecord(MobType.DEER_BABY_BASE_15, 1, 100)
                ), 50, false), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(46, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), -136, 130, -437)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.WOLF_BLONDE_ALPHA_BASE_30, 1, 100),
                        new SpawnZoneMobRecord(MobType.WOLF_GRAY_DARK_BASE_24, 2, 100),
                        new SpawnZoneMobRecord(MobType.DEER_BASE_34, 3, 100),
                        new SpawnZoneMobRecord(MobType.DEER_FEMALE_BASE_29, 2, 100),
                        new SpawnZoneMobRecord(MobType.DEER_BABY_BASE_15, 1, 100)
                ), 50, false), 1)),


                new SpawnZoneRecord(new AbstractSpawnZoneMeta(47, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), 400, 150, -554)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.WOLF_BLONDE_ALPHA_BASE_30, 1, 100),
                        new SpawnZoneMobRecord(MobType.WOLF_GRAY_DARK_BASE_24, 2, 100),
                        new SpawnZoneMobRecord(MobType.DEER_BASE_34, 10, 100),
                        new SpawnZoneMobRecord(MobType.DEER_FEMALE_BASE_29, 4, 100),
                        new SpawnZoneMobRecord(MobType.DEER_BABY_BASE_15, 3, 100)
                ), 130, false), 1)),


                new SpawnZoneRecord(new AbstractSpawnZoneMeta(48, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), 79, 130, -429)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.WOLF_BLONDE_ALPHA_BASE_30, 1, 100),
                        new SpawnZoneMobRecord(MobType.WOLF_GRAY_DARK_BASE_24, 2, 100),
                        new SpawnZoneMobRecord(MobType.DEER_BASE_34, 3, 100),
                        new SpawnZoneMobRecord(MobType.DEER_FEMALE_BASE_29, 2, 100),
                        new SpawnZoneMobRecord(MobType.DEER_BABY_BASE_15, 1, 100)
                ), 50, false), 1)),


                new SpawnZoneRecord(new AbstractSpawnZoneMeta(49, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), 265, 130, -425)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.WOLF_BLONDE_ALPHA_BASE_30, 1, 100),
                        new SpawnZoneMobRecord(MobType.WOLF_GRAY_DARK_BASE_24, 2, 100),
                        new SpawnZoneMobRecord(MobType.DEER_BASE_34, 6, 100),
                        new SpawnZoneMobRecord(MobType.DEER_FEMALE_BASE_29, 6, 100),
                        new SpawnZoneMobRecord(MobType.DEER_BABY_BASE_15, 3, 100)
                ), 120, false), 1)),


                new SpawnZoneRecord(new AbstractSpawnZoneMeta(50, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), 669, 130, -484)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.WOLF_BLONDE_ALPHA_BASE_30, 1, 100),
                        new SpawnZoneMobRecord(MobType.WOLF_GRAY_DARK_BASE_24, 2, 100),
                        new SpawnZoneMobRecord(MobType.DEER_BASE_34, 10, 100),
                        new SpawnZoneMobRecord(MobType.DEER_FEMALE_BASE_29, 4, 100),
                        new SpawnZoneMobRecord(MobType.DEER_BABY_BASE_15, 3, 100)
                ), 150, false), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(51, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), 766, 130, -814)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        // todo
                ), 70, false), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(52, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), 335, 130, -784)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        // todo
                ), 65, false), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(53, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), 693, 110, -215)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.WOLF_BLONDE_ALPHA_BASE_30, 1, 100),
                        new SpawnZoneMobRecord(MobType.WOLF_GRAY_DARK_BASE_24, 2, 100),
                        new SpawnZoneMobRecord(MobType.DEER_BASE_34, 10, 100),
                        new SpawnZoneMobRecord(MobType.DEER_FEMALE_BASE_29, 4, 100),
                        new SpawnZoneMobRecord(MobType.DEER_BABY_BASE_15, 3, 100)
                ), 100, false), 1)),


                new SpawnZoneRecord(new AbstractSpawnZoneMeta(54, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), 890, 100, -300)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        // todo
                ), 100, false), 1)),


                new SpawnZoneRecord(new AbstractSpawnZoneMeta(55, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), 1000, 100, -487)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        // todo
                ), 100, false), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(56, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), 1148, 90, -787)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        // todo
                ), 80, false), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(57, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), 1088, 90, -825)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        // todo
                ), 80, false), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(58, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), 1035, 120, -928)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        // todo
                ), 80, false), 1)),


                new SpawnZoneRecord(new AbstractSpawnZoneMeta(59, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), 1035, 120, -928)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        // todo
                ), 80, false), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(60, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), 934, 120, -937)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        // todo
                ), 50, false), 1)),


                new SpawnZoneRecord(new AbstractSpawnZoneMeta(61, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), 663, 80, -803)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        // todo
                ), 40, false), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(62, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), 831, 80, -709)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        // todo
                ), 40, false), 1)),


                new SpawnZoneRecord(new AbstractSpawnZoneMeta(63, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), 824, 120, -634)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        // todo
                ), 40, false), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(64, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), 919, 120, -630)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        // todo
                ), 50, false), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(65, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), 1175, 80, -493)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        // todo
                ), 80, false), 1)),


                new SpawnZoneRecord(new AbstractSpawnZoneMeta(66, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), 845, 120, -126)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.WOLF_BLONDE_ALPHA_BASE_30, 1, 100),
                        new SpawnZoneMobRecord(MobType.WOLF_GRAY_DARK_BASE_24, 2, 100),
                        new SpawnZoneMobRecord(MobType.DEER_BASE_34, 7, 100),
                        new SpawnZoneMobRecord(MobType.DEER_FEMALE_BASE_29, 2, 100),
                        new SpawnZoneMobRecord(MobType.DEER_BABY_BASE_15, 1, 100)
                ), 80, false), 1)),


                new SpawnZoneRecord(new AbstractSpawnZoneMeta(67, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), 945, 100, -135)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.WOLF_BLONDE_ALPHA_BASE_30, 1, 100),
                        new SpawnZoneMobRecord(MobType.WOLF_GRAY_DARK_BASE_24, 2, 100),
                        new SpawnZoneMobRecord(MobType.DEER_BASE_34, 3, 100),
                        new SpawnZoneMobRecord(MobType.DEER_FEMALE_BASE_29, 4, 100),
                        new SpawnZoneMobRecord(MobType.DEER_BABY_BASE_15, 2, 100)
                ), 80, false), 1)),


                new SpawnZoneRecord(new AbstractSpawnZoneMeta(68, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), 989, 100, -13)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.WOLF_BLONDE_ALPHA_BASE_30, 1, 100),
                        new SpawnZoneMobRecord(MobType.WOLF_GRAY_DARK_BASE_24, 2, 100),
                        new SpawnZoneMobRecord(MobType.DEER_BASE_34, 4, 100),
                        new SpawnZoneMobRecord(MobType.DEER_FEMALE_BASE_29, 3, 100),
                        new SpawnZoneMobRecord(MobType.DEER_BABY_BASE_15, 3, 100)
                ), 80, false), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(69, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), 1033, 100, 315)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.WOLF_BLONDE_ALPHA_BASE_30, 1, 100),
                        new SpawnZoneMobRecord(MobType.WOLF_GRAY_DARK_BASE_24, 2, 100),
                        new SpawnZoneMobRecord(MobType.DEER_BASE_34, 3, 100),
                        new SpawnZoneMobRecord(MobType.DEER_FEMALE_BASE_29, 2, 100),
                        new SpawnZoneMobRecord(MobType.DEER_BABY_BASE_15, 1, 100)
                ), 80, false), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(70, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), 478, 120, 241)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.DEER_FEMALE_BASE_29, 8, 100),
                        new SpawnZoneMobRecord(MobType.DEER_BABY_BASE_15, 3, 100)
                ), 150, false), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(71, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), 85, 130, 250)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.DEER_FEMALE_BASE_29, 2, 100),
                        new SpawnZoneMobRecord(MobType.DEER_BABY_BASE_15, 3, 100)
                ), 80, false), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(72, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), 275, 130, 250)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.DEER_FEMALE_BASE_29, 2, 100),
                        new SpawnZoneMobRecord(MobType.DEER_BABY_BASE_15, 3, 100)
                ), 80, false), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(73, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), 1100, 100, 130)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.WOLF_BLONDE_ALPHA_BASE_30, 1, 100),
                        new SpawnZoneMobRecord(MobType.WOLF_GRAY_DARK_BASE_24, 2, 100),
                        new SpawnZoneMobRecord(MobType.DEER_BASE_34, 5, 100),
                        new SpawnZoneMobRecord(MobType.DEER_FEMALE_BASE_29, 3, 100),
                        new SpawnZoneMobRecord(MobType.DEER_BABY_BASE_15, 2, 100)
                ), 80, false), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(74, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), 724, 100, 376)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.WOLF_BLONDE_ALPHA_BASE_30, 1, 100),
                        new SpawnZoneMobRecord(MobType.WOLF_GRAY_DARK_BASE_24, 2, 100),
                        new SpawnZoneMobRecord(MobType.DEER_BASE_34, 4, 100),
                        new SpawnZoneMobRecord(MobType.DEER_FEMALE_BASE_29, 3, 100),
                        new SpawnZoneMobRecord(MobType.DEER_BABY_BASE_15, 2, 100)
                ), 80, false), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(75, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), 730, 100, 505)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.WOLF_BLONDE_ALPHA_BASE_30, 1, 100),
                        new SpawnZoneMobRecord(MobType.WOLF_GRAY_DARK_BASE_24, 2, 100),
                        new SpawnZoneMobRecord(MobType.DEER_BASE_34, 8, 100),
                        new SpawnZoneMobRecord(MobType.DEER_FEMALE_BASE_29, 3, 100),
                        new SpawnZoneMobRecord(MobType.DEER_BABY_BASE_15, 3, 100)
                ), 100, false), 1)),


                new SpawnZoneRecord(new AbstractSpawnZoneMeta(76, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), 128, 180, 420)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        // todo
                ), 100, false), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(77, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), 346, 120, 400)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        // todo
                ), 50, false), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(78, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), 604, 120, 409)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        // todo
                ), 50, false), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(79, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), 562, 120, 560)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        // todo
                ), 80, false), 1)),


                new SpawnZoneRecord(new AbstractSpawnZoneMeta(80, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), 340, 220, 637)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        // todo
                ), 120, false), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(81, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), 454, 150, 620)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        // todo
                ), 60, false), 1)),


                new SpawnZoneRecord(new AbstractSpawnZoneMeta(82, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), 140, 220, 709)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        // todo
                ), 120, false), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(83, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), -13, 180, 561)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        // todo
                ), 120, false), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(84, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), -241, 180, 536)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        // todo
                ), 120, false), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(85, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), -310, 150, 670)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        // todo
                ), 80, false), 1)),


                new SpawnZoneRecord(new AbstractSpawnZoneMeta(86, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), 1175, 120, 496)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        // todo
                ), 120, false), 1)),


                new SpawnZoneRecord(new AbstractSpawnZoneMeta(87, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), 1190, 120, 636)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        // todo
                ), 120, false), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(88, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), 1017, 150, 712)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        // todo
                ), 150, false), 1)),


                new SpawnZoneRecord(new AbstractSpawnZoneMeta(89, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), 707, 120, -14)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.WOLF_BLONDE_ALPHA_BASE_30, 1, 100),
                        new SpawnZoneMobRecord(MobType.WOLF_GRAY_DARK_BASE_24, 2, 100),
                        new SpawnZoneMobRecord(MobType.DEER_BASE_34, 5, 100),
                        new SpawnZoneMobRecord(MobType.DEER_FEMALE_BASE_29, 2, 100),
                        new SpawnZoneMobRecord(MobType.DEER_BABY_BASE_15, 3, 100)
                ), 80, false), 1)),


                new SpawnZoneRecord(new AbstractSpawnZoneMeta(90, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), 906, 120, 391)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.WOLF_BLONDE_ALPHA_BASE_30, 1, 100),
                        new SpawnZoneMobRecord(MobType.WOLF_GRAY_DARK_BASE_24, 2, 100),
                        new SpawnZoneMobRecord(MobType.DEER_BASE_34, 5, 100),
                        new SpawnZoneMobRecord(MobType.DEER_FEMALE_BASE_29, 2, 100),
                        new SpawnZoneMobRecord(MobType.DEER_BABY_BASE_15, 3, 100)
                ), 80, false), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(91, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), -980, 120, -224)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        // todo
                ), 70, false), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(92, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), -1156, 180, -484)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.ARMADILLO_BASE_60, 4, 100)
                ), 150, false), 1)),


                new SpawnZoneRecord(new AbstractSpawnZoneMeta(93, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), -1169, 180, -624)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.ARMADILLO_BASE_60, 4, 100)
                ), 70, false), 1)),


                new SpawnZoneRecord(new AbstractSpawnZoneMeta(94, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), -1075, 180, -792)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        // todo
                ), 70, false), 1)),


                new SpawnZoneRecord(new AbstractSpawnZoneMeta(95, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), -960, 120, -490)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.ARMADILLO_BASE_60, 4, 100)
                ), 50, false), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(96, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), -893, 120, -689)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.ARMADILLO_BASE_60, 4, 100)
                ), 70, false), 1)),


                new SpawnZoneRecord(new AbstractSpawnZoneMeta(97, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), -893, 120, -781)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.ARMADILLO_BASE_60, 4, 100)
                ), 70, false), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(98, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), -1160, 120, -867)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.ARMADILLO_BASE_60, 4, 100)
                ), 50, false), 1)),


                new SpawnZoneRecord(new AbstractSpawnZoneMeta(99, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), -914, 120, -573)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.ARMADILLO_BASE_60, 4, 100)
                ), 50, false), 1)),


                new SpawnZoneRecord(new AbstractSpawnZoneMeta(100, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), 1263, 80, -164)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        // todo
                ), 150, false), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(101, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), -1030, 200, 165)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        // todo
                ), 150, false), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(102, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), -774, 200, 550)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        // todo
                ), 100, false), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(103, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), -879, 200, 566)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        // todo
                ), 150, false), 1)),


                new SpawnZoneRecord(new AbstractSpawnZoneMeta(104, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), -837, 100, 323)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        // todo
                ), 100, false), 1)),

            new SpawnZoneRecord(new AbstractSpawnZoneMeta(105, SpawnZoneType.AREA, 15, 15,
                    new Location(Bukkit.getWorld("world"), -415, 80, 180)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                    new SpawnZoneMobRecord(MobType.ARMADILLO_NON_AGGRO_BASE_15, 20, 100)
            ), 80, false), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(106, SpawnZoneType.AREA, 15, 15,
                        new Location(Bukkit.getWorld("world"), -328, 80, 290)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.ARMADILLO_NON_AGGRO_BASE_15, 20, 100)
                ), 80, false), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(107, SpawnZoneType.POINT, 20, 1,
                        new Location(Bukkit.getWorld("world"), -440, 65, 206.5)), toVersionedJson(new PointSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.CITY_GUARD_STATIC, 1, 100)
                ), false), 1)),


                new SpawnZoneRecord(new AbstractSpawnZoneMeta(108, SpawnZoneType.POINT, 20, 1,
                        new Location(Bukkit.getWorld("world"), -423.5, 65, 227)), toVersionedJson(new PointSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.CITY_GUARD_STATIC, 1, 100)
                ), false), 1))


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