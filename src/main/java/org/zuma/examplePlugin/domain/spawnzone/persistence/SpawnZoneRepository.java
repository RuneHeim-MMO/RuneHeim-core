package org.zuma.examplePlugin.domain.spawnzone.persistence;

import com.google.gson.Gson;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.zuma.examplePlugin.core.meta.VersionedJson;
import org.zuma.examplePlugin.domain.map.MarkerType;
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
                ), false, null, null), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(1, SpawnZoneType.POINT, 200, 1,
                        new Location(Bukkit.getWorld("world"), 196.5, 67, -161.5)), toVersionedJson(new PointSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.CITY_GUARD_STATIC, 1, 100)
                ), false, null, null), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(2, SpawnZoneType.POINT, 20, 1,
                        new Location(Bukkit.getWorld("world"), 116.5, 67, -86.5)), toVersionedJson(new PointSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.CITY_GUARD_STATIC, 1, 100)
                ), false, null, null), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(3, SpawnZoneType.POINT, 20, 1,
                        new Location(Bukkit.getWorld("world"), 116.5, 67, -79.5)), toVersionedJson(new PointSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.CITY_GUARD_STATIC, 1, 100)
                ), false, null, null), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(4, SpawnZoneType.AREA, 20, 1,
                        new Location(Bukkit.getWorld("world"), 191, 73, -72)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.CITY_GUARD_WALKING, 10, 100)
                ), 100, false, true), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(5, SpawnZoneType.AREA, 20, 1,
                        new Location(Bukkit.getWorld("world"), 192, 72, -109)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.CITY_GUARD_WALKING, 10, 100)
                ), 100, false, true), 1)),


                // Drakkar city guards end

                // Drakkar and surroundings

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(7, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), 199, 68, -230)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.GOBLIN_1_BASE_1, 5, 100),
                        new SpawnZoneMobRecord(MobType.GOBLIN_2_BASE_1, 5, 100),
                        new SpawnZoneMobRecord(MobType.GOBLIN_3_BASE_1, 5, 100),
                        new SpawnZoneMobRecord(MobType.GOBLIN_4_BASE_1, 5, 100),
                        new SpawnZoneMobRecord(MobType.GOBLIN_5_BASE_1, 5, 100)
                ), 50, false, true), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(8, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), 63, 68, -81)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.GOBLIN_1_BASE_1, 2, 100),
                        new SpawnZoneMobRecord(MobType.GOBLIN_2_BASE_1, 2, 100),
                        new SpawnZoneMobRecord(MobType.GOBLIN_3_BASE_1, 2, 100),
                        new SpawnZoneMobRecord(MobType.GOBLIN_4_BASE_1, 2, 100),
                        new SpawnZoneMobRecord(MobType.GOBLIN_5_BASE_1, 2, 100)
                ), 50, false, true), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(9, SpawnZoneType.POINT, 30, 1,
                        new Location(Bukkit.getWorld("world"), 173, 73, -50)), toVersionedJson(new PointSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.DRAKKAR_GENERAL_STORE, 1, 100)
                ), false, MarkerType.GENERAL_STORE, "General Store"), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(10, SpawnZoneType.POINT, 30, 1,
                        new Location(Bukkit.getWorld("world"), 209, 73, -98)), toVersionedJson(new PointSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.DRAKKAR_BLACKSMITH, 1, 100)
                ), false, MarkerType.BLACKSMITH, "Blacksmith"), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(11, SpawnZoneType.POINT, 30, 1,
                        new Location(Bukkit.getWorld("world"), 257.5, 73, -58.5)), toVersionedJson(new PointSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.DRAKKAR_BANK_TELLER_1, 1, 100)
                ), false, MarkerType.BANK_TELLER, "Bank"), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(12, SpawnZoneType.POINT, 30, 1,
                        new Location(Bukkit.getWorld("world"), 257.5, 73, -63.5)), toVersionedJson(new PointSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.DRAKKAR_BANK_TELLER_2, 1, 100)
                ), false, MarkerType.BANK_TELLER, "Bank"), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(13, SpawnZoneType.POINT, 30, 1,
                        new Location(Bukkit.getWorld("world"), 257.5, 73, -68.5)), toVersionedJson(new PointSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.DRAKKAR_BANK_TELLER_3, 1, 100)
                ), false, MarkerType.BANK_TELLER, "Bank"), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(14, SpawnZoneType.POINT, 30, 1,
                        new Location(Bukkit.getWorld("world"), 247, 74, -43.5)), toVersionedJson(new PointSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.INVESTMENT_BANKER, 1, 100)
                ), false, MarkerType.BANK_TELLER, "Investment Banker"), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(15, SpawnZoneType.POINT, 30, 1,
                        new Location(Bukkit.getWorld("world"), 153.5, 73, -50)), toVersionedJson(new PointSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.DRAKKAR_JEWLER, 1, 100)
                ), false, MarkerType.JEWELER, "Jeweler"), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(16, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), 324, 68, -205)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.BOAR_BROWN_BASE_3, 1, 100),
                        new SpawnZoneMobRecord(MobType.BOAR_GRAY_BASE_3, 1, 100),
                        new SpawnZoneMobRecord(MobType.GOBLIN_4_BASE_1, 5, 100),
                        new SpawnZoneMobRecord(MobType.GOBLIN_5_BASE_1, 5, 100)
                ), 50, false, true), 1)),


                new SpawnZoneRecord(new AbstractSpawnZoneMeta(17, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), 374, 68, -127)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.BOAR_GRAY_BASE_3, 2, 100),
                        new SpawnZoneMobRecord(MobType.BOAR_LIGHT_BROWN_BASE_3, 2, 100)
                ), 30, false, true), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(18, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), 235, 70, 50)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.GOBLIN_1_BASE_1, 7, 100),
                        new SpawnZoneMobRecord(MobType.GOBLIN_2_BASE_1, 7, 100),
                        new SpawnZoneMobRecord(MobType.GOBLIN_3_BASE_1, 7, 100),
                        new SpawnZoneMobRecord(MobType.GOBLIN_4_BASE_1, 10, 100),
                        new SpawnZoneMobRecord(MobType.GOBLIN_5_BASE_1, 10, 100),
                        new SpawnZoneMobRecord(MobType.GOBLIN_1_BASE_4, 3, 100),
                        new SpawnZoneMobRecord(MobType.GOBLIN_2_BASE_4, 3, 100),
                        new SpawnZoneMobRecord(MobType.GOBLIN_3_BASE_4, 3, 100),
                        new SpawnZoneMobRecord(MobType.FOX_BASE_8, 3, 100)
                ), 150, false, true), 1)),

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
                        new SpawnZoneMobRecord(MobType.BOAR_LIGHT_BROWN_BASE_3, 2, 100),
                        new SpawnZoneMobRecord(MobType.FOX_BASE_8, 3, 100)
                ), 150, false, true), 1)),


                new SpawnZoneRecord(new AbstractSpawnZoneMeta(20, SpawnZoneType.POINT, 30, 1,
                        new Location(Bukkit.getWorld("world"), 19, 70, -203)), toVersionedJson(new PointSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.BOAR_BROWN_ALPHA_BASE10, 1, 100)
                ), false, null, null), 1)),


                new SpawnZoneRecord(new AbstractSpawnZoneMeta(21, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), 105, 73, -245)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.GOBLIN_1_BASE_1, 5, 100),
                        new SpawnZoneMobRecord(MobType.GOBLIN_2_BASE_1, 5, 100),
                        new SpawnZoneMobRecord(MobType.GOBLIN_3_BASE_1, 5, 100),
                        new SpawnZoneMobRecord(MobType.GOBLIN_4_BASE_1, 3, 100),
                        new SpawnZoneMobRecord(MobType.GOBLIN_5_BASE_1, 2, 100),
                        new SpawnZoneMobRecord(MobType.GOBLIN_4_BASE_4, 3, 100),
                        new SpawnZoneMobRecord(MobType.GOBLIN_5_BASE_4, 3, 100),
                        new SpawnZoneMobRecord(MobType.BOAR_LIGHT_BROWN_BASE_3, 1, 100),
                        new SpawnZoneMobRecord(MobType.FOX_BASE_8, 3, 100)
                ), 150, false, true), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(22, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), 480, 75, -58)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.HOGLIN_BASE_5, 5, 100),
                        new SpawnZoneMobRecord(MobType.FOX_BASE_10, 3, 100)
                ), 80, false, false), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(23, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), 523, 71, 15)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.CHICKEN_BASE_2, 10, 100)
                ), 40, false, false), 1)),


                new SpawnZoneRecord(new AbstractSpawnZoneMeta(24, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), 520, 74, -35)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.CHICKEN_BASE_2, 10, 100)
                ), 40, false, false), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(25, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), 408, 75, -242)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.GOBLIN_1_BASE_4, 5, 100),
                        new SpawnZoneMobRecord(MobType.GOBLIN_2_BASE_4, 5, 100),
                        new SpawnZoneMobRecord(MobType.GOBLIN_3_BASE_4, 5, 100),
                        new SpawnZoneMobRecord(MobType.GOBLIN_4_BASE_4, 5, 100),
                        new SpawnZoneMobRecord(MobType.GOBLIN_5_BASE_4, 5, 100),
                        new SpawnZoneMobRecord(MobType.FOX_BASE_8, 3, 100)
                ), 150, false, true), 1)),


                new SpawnZoneRecord(new AbstractSpawnZoneMeta(26, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), 448, 75, -47)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.GOBLIN_1_BASE_4, 5, 100),
                        new SpawnZoneMobRecord(MobType.GOBLIN_2_BASE_4, 5, 100),
                        new SpawnZoneMobRecord(MobType.GOBLIN_3_BASE_4, 5, 100),
                        new SpawnZoneMobRecord(MobType.GOBLIN_4_BASE_4, 5, 100),
                        new SpawnZoneMobRecord(MobType.GOBLIN_5_BASE_4, 5, 100),
                        new SpawnZoneMobRecord(MobType.FOX_BASE_10, 3, 100)
                ), 150, false, true), 1)),


                new SpawnZoneRecord(new AbstractSpawnZoneMeta(27, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), 184, 65, 188)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.WOLF_BLACK_BASE_20, 2, 100),
                        new SpawnZoneMobRecord(MobType.WOLF_BLONDE_BASE_22, 2, 100)
                ), 50, false, true), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(28, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), -104, 87, 103)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.WOLF_BLACK_ALPHA_BASE_36, 3, 100),
                        new SpawnZoneMobRecord(MobType.WOLF_GRAY_LIGHT_ALPHA_BASE_34, 2, 100),
                        new SpawnZoneMobRecord(MobType.DEER_BABY_BASE_15, 3, 100)
                ), 80, false, true), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(29, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), -77, 74, -210)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.GOBLIN_1_BASE_4, 5, 100),
                        new SpawnZoneMobRecord(MobType.GOBLIN_2_BASE_4, 5, 100),
                        new SpawnZoneMobRecord(MobType.GOBLIN_3_BASE_4, 5, 100),
                        new SpawnZoneMobRecord(MobType.GOBLIN_4_BASE_4, 5, 100),
                        new SpawnZoneMobRecord(MobType.GOBLIN_5_BASE_4, 5, 100),
                        new SpawnZoneMobRecord(MobType.FOX_BASE_8, 3, 100)
                ), 150, false, true), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(30, SpawnZoneType.AREA, 30, 50,
                        new Location(Bukkit.getWorld("world"), 257, 69, -291)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.CHICKEN_BASE_2, 10, 100)
                ), 10, false, false), 1)),

                // Between eldarwyn and brineport
                new SpawnZoneRecord(new AbstractSpawnZoneMeta(31, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), -140, 87, 338)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.WOLF_BLACK_ALPHA_BASE_36, 2, 100),
                        new SpawnZoneMobRecord(MobType.WOLF_GRAY_LIGHT_ALPHA_BASE_34, 4, 100),
                        new SpawnZoneMobRecord(MobType.DEER_FEMALE_BASE_29, 2, 100),
                        new SpawnZoneMobRecord(MobType.DEER_BABY_BASE_15, 3, 100)
                ), 150, false, true), 1)),


                new SpawnZoneRecord(new AbstractSpawnZoneMeta(32, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), -260, 120, 122)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.WOLF_BLACK_BASE_20, 2, 100),
                        new SpawnZoneMobRecord(MobType.WOLF_GRAY_LIGHT_BASE_26, 4, 100),
                        new SpawnZoneMobRecord(MobType.DEER_FEMALE_BASE_29, 2, 100),
                        new SpawnZoneMobRecord(MobType.DEER_BABY_BASE_15, 3, 100),
                        new SpawnZoneMobRecord(MobType.FOX_BASE_22, 3, 100)
                ), 150, false, true), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(33, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), -364, 120, -172)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.WOLF_BLACK_ALPHA_BASE_36, 2, 100),
                        new SpawnZoneMobRecord(MobType.WOLF_GRAY_LIGHT_ALPHA_BASE_34, 4, 100)
                ), 150, false, true), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(34, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), -559, 120, -45)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.WOLF_BLONDE_ALPHA_BASE_30, 10, 100),
                        new SpawnZoneMobRecord(MobType.WOLF_GRAY_DARK_ALPHA_BASE_32, 10, 100)
                ), 150, false, true), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(35, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), -273, 120, -419)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.WOLF_GRAY_LIGHT_ALPHA_BASE_34, 2, 100),
                        new SpawnZoneMobRecord(MobType.WOLF_BLACK_BASE_20, 4, 100)
                ), 150, false, true), 1)),

                // Boars Den
                new SpawnZoneRecord(new AbstractSpawnZoneMeta(36, SpawnZoneType.AREA, 15, 15,
                        new Location(Bukkit.getWorld("world"), -594, 100, -560)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.BOAR_BROWN_ALPHA_BASE10, 4, 75),
                        new SpawnZoneMobRecord(MobType.BOAR_GRAY_ALPHA_BASE_12, 4, 75),
                        new SpawnZoneMobRecord(MobType.BOAR_LIGHT_BROWN_ALPHA_BASE_14, 4, 50)
                ), 50, false, false), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(37, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), -508, 140, -465)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.WOLF_GRAY_LIGHT_ALPHA_BASE_34, 2, 100),
                        new SpawnZoneMobRecord(MobType.WOLF_BLACK_BASE_20, 4, 100)
                ), 150, false, true), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(38, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), -721, 100, -525)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.WOLF_GRAY_LIGHT_ALPHA_BASE_34, 2, 100),
                        new SpawnZoneMobRecord(MobType.WOLF_BLACK_BASE_20, 4, 100)
                ), 150, false, true), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(39, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), -692, 150, -700)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.WOLF_GRAY_LIGHT_ALPHA_BASE_34, 2, 100),
                        new SpawnZoneMobRecord(MobType.WOLF_BLACK_BASE_20, 4, 100)
                ), 150, false, true), 1)),


                new SpawnZoneRecord(new AbstractSpawnZoneMeta(40, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), -1116, 100, -345)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.WOLF_GRAY_LIGHT_ALPHA_BASE_34, 2, 100),
                        new SpawnZoneMobRecord(MobType.WOLF_BLACK_BASE_20, 4, 100),
                        new SpawnZoneMobRecord(MobType.FOX_BASE_22, 3, 100)
                ), 100, false, true), 1)),


                new SpawnZoneRecord(new AbstractSpawnZoneMeta(41, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), -746, 150, -161)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.WOLF_GRAY_LIGHT_ALPHA_BASE_34, 1, 100),
                        new SpawnZoneMobRecord(MobType.WOLF_BLACK_BASE_20, 2, 100)
                ), 90, false, true), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(42, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), -405, 150, -733)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.WOLF_BLONDE_ALPHA_BASE_30, 1, 100),
                        new SpawnZoneMobRecord(MobType.WOLF_GRAY_DARK_BASE_24, 2, 100),
                        new SpawnZoneMobRecord(MobType.DEER_BASE_34, 3, 100),
                        new SpawnZoneMobRecord(MobType.DEER_FEMALE_BASE_29, 2, 100),
                        new SpawnZoneMobRecord(MobType.DEER_BABY_BASE_15, 1, 100)
                ), 150, false, true), 1)),


                new SpawnZoneRecord(new AbstractSpawnZoneMeta(43, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), -187, 150, -600)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.WOLF_BLONDE_ALPHA_BASE_30, 1, 100),
                        new SpawnZoneMobRecord(MobType.WOLF_GRAY_DARK_BASE_24, 2, 100),
                        new SpawnZoneMobRecord(MobType.DEER_BASE_34, 3, 100),
                        new SpawnZoneMobRecord(MobType.DEER_FEMALE_BASE_29, 2, 100),
                        new SpawnZoneMobRecord(MobType.DEER_BABY_BASE_15, 1, 100),
                        new SpawnZoneMobRecord(MobType.EAGLE_BLACK_BASE_50, 3, 100),
                        new SpawnZoneMobRecord(MobType.EAGLE_BROWNHEAD_BASE_55, 2, 100),
                        new SpawnZoneMobRecord(MobType.EAGLE_BROWN_BASE_48, 4, 100)
                ), 80, false, true), 1)),


                new SpawnZoneRecord(new AbstractSpawnZoneMeta(44, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), 205, 150, -622)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.WOLF_BLONDE_ALPHA_BASE_30, 1, 100),
                        new SpawnZoneMobRecord(MobType.WOLF_GRAY_DARK_BASE_24, 2, 100),
                        new SpawnZoneMobRecord(MobType.DEER_BASE_34, 10, 100),
                        new SpawnZoneMobRecord(MobType.DEER_FEMALE_BASE_29, 5, 100),
                        new SpawnZoneMobRecord(MobType.DEER_BABY_BASE_15, 3, 100)
                ), 150, false, true), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(45, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), -21, 100, -400)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.WOLF_BLONDE_ALPHA_BASE_30, 1, 100),
                        new SpawnZoneMobRecord(MobType.WOLF_GRAY_DARK_BASE_24, 2, 100),
                        new SpawnZoneMobRecord(MobType.DEER_BASE_34, 3, 100),
                        new SpawnZoneMobRecord(MobType.DEER_FEMALE_BASE_29, 5, 100),
                        new SpawnZoneMobRecord(MobType.DEER_BABY_BASE_15, 1, 100),
                        new SpawnZoneMobRecord(MobType.EAGLE_BLACK_BASE_50, 3, 100)
                ), 50, false, true), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(46, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), -136, 130, -437)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.WOLF_BLONDE_ALPHA_BASE_30, 1, 100),
                        new SpawnZoneMobRecord(MobType.WOLF_GRAY_DARK_BASE_24, 2, 100),
                        new SpawnZoneMobRecord(MobType.DEER_BASE_34, 3, 100),
                        new SpawnZoneMobRecord(MobType.DEER_FEMALE_BASE_29, 2, 100),
                        new SpawnZoneMobRecord(MobType.DEER_BABY_BASE_15, 1, 100),
                        new SpawnZoneMobRecord(MobType.EAGLE_BROWNHEAD_BASE_55, 2, 100),
                        new SpawnZoneMobRecord(MobType.EAGLE_BROWN_BASE_48, 4, 100)
                ), 50, false, true), 1)),


                new SpawnZoneRecord(new AbstractSpawnZoneMeta(47, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), 400, 150, -554)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.WOLF_BLONDE_ALPHA_BASE_30, 1, 100),
                        new SpawnZoneMobRecord(MobType.WOLF_GRAY_DARK_BASE_24, 2, 100),
                        new SpawnZoneMobRecord(MobType.DEER_BASE_34, 10, 100),
                        new SpawnZoneMobRecord(MobType.DEER_FEMALE_BASE_29, 4, 100),
                        new SpawnZoneMobRecord(MobType.DEER_BABY_BASE_15, 3, 100)
                ), 130, false, true), 1)),


                new SpawnZoneRecord(new AbstractSpawnZoneMeta(48, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), 79, 130, -429)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.WOLF_BLONDE_ALPHA_BASE_30, 1, 100),
                        new SpawnZoneMobRecord(MobType.WOLF_GRAY_DARK_BASE_24, 2, 100),
                        new SpawnZoneMobRecord(MobType.DEER_BASE_34, 3, 100),
                        new SpawnZoneMobRecord(MobType.DEER_FEMALE_BASE_29, 2, 100),
                        new SpawnZoneMobRecord(MobType.DEER_BABY_BASE_15, 1, 100),
                        new SpawnZoneMobRecord(MobType.EAGLE_BLACK_BASE_50, 3, 100),
                        new SpawnZoneMobRecord(MobType.EAGLE_BROWNHEAD_BASE_55, 2, 100)
                ), 50, false, true), 1)),


                new SpawnZoneRecord(new AbstractSpawnZoneMeta(49, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), 265, 130, -425)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.WOLF_BLONDE_ALPHA_BASE_30, 1, 100),
                        new SpawnZoneMobRecord(MobType.WOLF_GRAY_DARK_BASE_24, 2, 100),
                        new SpawnZoneMobRecord(MobType.DEER_BASE_34, 6, 100),
                        new SpawnZoneMobRecord(MobType.DEER_FEMALE_BASE_29, 6, 100),
                        new SpawnZoneMobRecord(MobType.DEER_BABY_BASE_15, 3, 100)
                ), 120, false, true), 1)),


                new SpawnZoneRecord(new AbstractSpawnZoneMeta(50, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), 669, 130, -484)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.WOLF_BLONDE_ALPHA_BASE_30, 1, 100),
                        new SpawnZoneMobRecord(MobType.WOLF_GRAY_DARK_BASE_24, 2, 100),
                        new SpawnZoneMobRecord(MobType.DEER_BASE_34, 10, 100),
                        new SpawnZoneMobRecord(MobType.DEER_FEMALE_BASE_29, 4, 100),
                        new SpawnZoneMobRecord(MobType.DEER_BABY_BASE_15, 3, 100)
                ), 150, false, true), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(51, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), 766, 130, -814)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.WOLF_BLACK_ALPHA_BASE_36, 2, 100),
                        new SpawnZoneMobRecord(MobType.WOLF_GRAY_LIGHT_ALPHA_BASE_34, 4, 100),
                        new SpawnZoneMobRecord(MobType.DEER_FEMALE_BASE_29, 2, 100),
                        new SpawnZoneMobRecord(MobType.DEER_BABY_BASE_15, 3, 100),
                        new SpawnZoneMobRecord(MobType.FOX_BASE_22, 3, 100)
                ), 70, false, true), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(52, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), 335, 130, -784)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.DEER_BASE_34, 6, 100),
                        new SpawnZoneMobRecord(MobType.DEER_FEMALE_BASE_29, 5, 100),
                        new SpawnZoneMobRecord(MobType.DEER_BABY_BASE_15, 3, 100)
                ), 65, false, true), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(53, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), 693, 110, -215)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.WOLF_BLONDE_ALPHA_BASE_30, 1, 100),
                        new SpawnZoneMobRecord(MobType.WOLF_GRAY_DARK_BASE_24, 2, 100),
                        new SpawnZoneMobRecord(MobType.DEER_BASE_34, 10, 100),
                        new SpawnZoneMobRecord(MobType.DEER_FEMALE_BASE_29, 4, 100),
                        new SpawnZoneMobRecord(MobType.DEER_BABY_BASE_15, 3, 100)
                ), 100, false, true), 1)),


                new SpawnZoneRecord(new AbstractSpawnZoneMeta(54, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), 890, 100, -300)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.WOLF_GRAY_LIGHT_ALPHA_BASE_34, 1, 100),
                        new SpawnZoneMobRecord(MobType.WOLF_BLACK_ALPHA_BASE_36, 1, 100),
                        new SpawnZoneMobRecord(MobType.LLAMA_BASE_56, 6, 100)
                ), 100, false, true), 1)),


                new SpawnZoneRecord(new AbstractSpawnZoneMeta(55, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), 1000, 100, -487)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.WOLF_GRAY_LIGHT_ALPHA_BASE_34, 1, 100),
                        new SpawnZoneMobRecord(MobType.WOLF_BLACK_ALPHA_BASE_36, 1, 100),
                        new SpawnZoneMobRecord(MobType.LLAMA_BASE_56, 6, 100)
                ), 100, false, true), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(56, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), 1148, 90, -787)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.DEER_FEMALE_BASE_29, 2, 100),
                        new SpawnZoneMobRecord(MobType.DEER_BABY_BASE_15, 3, 100),
                        new SpawnZoneMobRecord(MobType.LLAMA_BASE_56, 6, 100)
                ), 80, false, true), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(57, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), 1088, 90, -825)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.DEER_FEMALE_BASE_29, 2, 100),
                        new SpawnZoneMobRecord(MobType.DEER_BABY_BASE_15, 3, 100),
                        new SpawnZoneMobRecord(MobType.LLAMA_BASE_56, 6, 100)
                ), 80, false, true), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(58, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), 1035, 120, -928)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.DEER_FEMALE_BASE_29, 2, 100),
                        new SpawnZoneMobRecord(MobType.DEER_BABY_BASE_15, 3, 100),
                        new SpawnZoneMobRecord(MobType.LLAMA_BASE_56, 6, 100),
                        new SpawnZoneMobRecord(MobType.FOX_BASE_22, 1, 100)
                ), 80, false, true), 1)),


                new SpawnZoneRecord(new AbstractSpawnZoneMeta(59, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), 1047, 65, -882)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.ARMADILLO_BASE_55, 10, 100)
                ), 30, false, true), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(60, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), 934, 120, -937)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.DEER_FEMALE_BASE_29, 2, 100),
                        new SpawnZoneMobRecord(MobType.DEER_BABY_BASE_15, 3, 100),
                        new SpawnZoneMobRecord(MobType.LLAMA_BASE_56, 6, 100)
                ), 50, false, true), 1)),


                new SpawnZoneRecord(new AbstractSpawnZoneMeta(61, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), 663, 80, -803)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.WOLF_BLACK_ALPHA_BASE_36, 2, 100),
                        new SpawnZoneMobRecord(MobType.DEER_BASE_34, 3, 100)
                ), 40, false, true), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(62, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), 831, 80, -709)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        // todo
                ), 40, false, true), 1)),


                new SpawnZoneRecord(new AbstractSpawnZoneMeta(63, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), 824, 120, -634)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        // todo
                ), 40, false, true), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(64, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), 919, 120, -630)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        // todo
                ), 50, false, true), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(65, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), 1175, 80, -493)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.WOLF_GRAY_LIGHT_ALPHA_BASE_34, 1, 100),
                        new SpawnZoneMobRecord(MobType.WOLF_BLACK_ALPHA_BASE_36, 1, 100),
                        new SpawnZoneMobRecord(MobType.LLAMA_BASE_56, 6, 100)
                ), 80, false, true), 1)),


                new SpawnZoneRecord(new AbstractSpawnZoneMeta(66, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), 845, 120, -126)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.WOLF_BLONDE_ALPHA_BASE_30, 1, 100),
                        new SpawnZoneMobRecord(MobType.WOLF_GRAY_DARK_BASE_24, 2, 100),
                        new SpawnZoneMobRecord(MobType.DEER_BASE_34, 7, 100),
                        new SpawnZoneMobRecord(MobType.DEER_FEMALE_BASE_29, 2, 100),
                        new SpawnZoneMobRecord(MobType.DEER_BABY_BASE_15, 1, 100)
                ), 80, false, true), 1)),


                new SpawnZoneRecord(new AbstractSpawnZoneMeta(67, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), 945, 100, -135)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.WOLF_BLONDE_ALPHA_BASE_30, 1, 100),
                        new SpawnZoneMobRecord(MobType.WOLF_GRAY_DARK_BASE_24, 2, 100),
                        new SpawnZoneMobRecord(MobType.DEER_BASE_34, 3, 100),
                        new SpawnZoneMobRecord(MobType.DEER_FEMALE_BASE_29, 4, 100),
                        new SpawnZoneMobRecord(MobType.DEER_BABY_BASE_15, 2, 100)
                ), 80, false, true), 1)),


                new SpawnZoneRecord(new AbstractSpawnZoneMeta(68, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), 989, 100, -13)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.WOLF_BLONDE_ALPHA_BASE_30, 1, 100),
                        new SpawnZoneMobRecord(MobType.WOLF_GRAY_DARK_BASE_24, 2, 100),
                        new SpawnZoneMobRecord(MobType.DEER_BASE_34, 4, 100),
                        new SpawnZoneMobRecord(MobType.DEER_FEMALE_BASE_29, 3, 100),
                        new SpawnZoneMobRecord(MobType.DEER_BABY_BASE_15, 3, 100)
                ), 80, false, true), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(69, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), 1033, 100, 315)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.WOLF_BLONDE_ALPHA_BASE_30, 1, 100),
                        new SpawnZoneMobRecord(MobType.WOLF_GRAY_DARK_BASE_24, 2, 100),
                        new SpawnZoneMobRecord(MobType.DEER_BASE_34, 3, 100),
                        new SpawnZoneMobRecord(MobType.DEER_FEMALE_BASE_29, 2, 100),
                        new SpawnZoneMobRecord(MobType.DEER_BABY_BASE_15, 1, 100)
                ), 80, false, true), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(70, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), 478, 120, 241)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.DEER_FEMALE_BASE_29, 8, 100),
                        new SpawnZoneMobRecord(MobType.DEER_BABY_BASE_15, 3, 100)
                ), 150, false, true), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(71, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), 85, 130, 250)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.DEER_FEMALE_BASE_29, 2, 100),
                        new SpawnZoneMobRecord(MobType.DEER_BABY_BASE_15, 3, 100)
                ), 80, false, true), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(72, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), 275, 130, 250)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.DEER_FEMALE_BASE_29, 2, 100),
                        new SpawnZoneMobRecord(MobType.DEER_BABY_BASE_15, 3, 100)
                ), 80, false, true), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(73, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), 1100, 100, 130)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.WOLF_BLONDE_ALPHA_BASE_30, 1, 100),
                        new SpawnZoneMobRecord(MobType.WOLF_GRAY_DARK_BASE_24, 2, 100),
                        new SpawnZoneMobRecord(MobType.DEER_BASE_34, 5, 100),
                        new SpawnZoneMobRecord(MobType.DEER_FEMALE_BASE_29, 3, 100),
                        new SpawnZoneMobRecord(MobType.DEER_BABY_BASE_15, 2, 100)
                ), 80, false, true), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(74, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), 724, 100, 376)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.WOLF_BLONDE_ALPHA_BASE_30, 1, 100),
                        new SpawnZoneMobRecord(MobType.WOLF_GRAY_DARK_BASE_24, 2, 100),
                        new SpawnZoneMobRecord(MobType.DEER_BASE_34, 4, 100),
                        new SpawnZoneMobRecord(MobType.DEER_FEMALE_BASE_29, 3, 100),
                        new SpawnZoneMobRecord(MobType.DEER_BABY_BASE_15, 2, 100)
                ), 80, false, true), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(75, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), 730, 100, 505)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.WOLF_BLONDE_ALPHA_BASE_30, 1, 100),
                        new SpawnZoneMobRecord(MobType.WOLF_GRAY_DARK_BASE_24, 2, 100),
                        new SpawnZoneMobRecord(MobType.DEER_BASE_34, 8, 100),
                        new SpawnZoneMobRecord(MobType.DEER_FEMALE_BASE_29, 3, 100),
                        new SpawnZoneMobRecord(MobType.DEER_BABY_BASE_15, 3, 100)
                ), 100, false, true), 1)),


                new SpawnZoneRecord(new AbstractSpawnZoneMeta(76, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), 128, 180, 420)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.OCELOT_BASE_44, 1, 100),
                        new SpawnZoneMobRecord(MobType.OCELOT_BASE_45, 1, 100),
                        new SpawnZoneMobRecord(MobType.OCELOT_BASE_48, 2, 100),
                        new SpawnZoneMobRecord(MobType.LEOPARD_STANDARD_BASE_55, 1, 25),
                        new SpawnZoneMobRecord(MobType.LEOPARD_STANDARD_BABY_BASE_35, 1, 10)
                ), 100, false, true), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(77, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), 346, 120, 400)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.OCELOT_BASE_48, 1, 100),
                        new SpawnZoneMobRecord(MobType.LEOPARD_STANDARD_BASE_55, 1, 25),
                        new SpawnZoneMobRecord(MobType.LEOPARD_STANDARD_BABY_BASE_35, 1, 10)
                ), 50, false, true), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(78, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), 604, 120, 409)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.OCELOT_BASE_44, 1, 100),
                        new SpawnZoneMobRecord(MobType.OCELOT_BASE_45, 1, 100),
                        new SpawnZoneMobRecord(MobType.OCELOT_BASE_48, 2, 100),
                        new SpawnZoneMobRecord(MobType.LEOPARD_STANDARD_BASE_55, 1, 25),
                        new SpawnZoneMobRecord(MobType.LEOPARD_STANDARD_BABY_BASE_35, 1, 10)
                ), 50, false, true), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(79, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), 562, 120, 560)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.OCELOT_BASE_44, 1, 100),
                        new SpawnZoneMobRecord(MobType.OCELOT_BASE_45, 1, 100),
                        new SpawnZoneMobRecord(MobType.OCELOT_BASE_48, 2, 100),
                        new SpawnZoneMobRecord(MobType.LEOPARD_STANDARD_BASE_55, 1, 25),
                        new SpawnZoneMobRecord(MobType.LEOPARD_STANDARD_BABY_BASE_35, 1, 10)
                ), 80, false, true), 1)),


                new SpawnZoneRecord(new AbstractSpawnZoneMeta(80, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), 340, 220, 637)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.OCELOT_BASE_44, 1, 100),
                        new SpawnZoneMobRecord(MobType.OCELOT_BASE_45, 1, 100),
                        new SpawnZoneMobRecord(MobType.OCELOT_BASE_48, 2, 100),
                        new SpawnZoneMobRecord(MobType.LEOPARD_STANDARD_BASE_55, 1, 25),
                        new SpawnZoneMobRecord(MobType.LEOPARD_STANDARD_BABY_BASE_35, 1, 10)
                ), 120, false, true), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(81, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), 454, 150, 620)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.OCELOT_BASE_44, 1, 100),
                        new SpawnZoneMobRecord(MobType.LEOPARD_STANDARD_BASE_55, 1, 25),
                        new SpawnZoneMobRecord(MobType.LEOPARD_STANDARD_BABY_BASE_35, 1, 10)
                ), 60, false, true), 1)),


                new SpawnZoneRecord(new AbstractSpawnZoneMeta(82, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), 140, 220, 709)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.OCELOT_BASE_44, 1, 100),
                        new SpawnZoneMobRecord(MobType.OCELOT_BASE_45, 1, 100),
                        new SpawnZoneMobRecord(MobType.OCELOT_BASE_48, 2, 100),
                        new SpawnZoneMobRecord(MobType.LEOPARD_STANDARD_BASE_55, 1, 25),
                        new SpawnZoneMobRecord(MobType.LEOPARD_STANDARD_BABY_BASE_35, 1, 10)
                ), 120, false, true), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(83, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), -13, 180, 561)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.OCELOT_BASE_48, 2, 100),
                        new SpawnZoneMobRecord(MobType.LEOPARD_STANDARD_BASE_55, 1, 25),
                        new SpawnZoneMobRecord(MobType.LEOPARD_STANDARD_BABY_BASE_35, 1, 10)
                ), 120, false, true), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(84, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), -241, 180, 536)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.WOLF_BLACK_ALPHA_BASE_36, 2, 100),
                        new SpawnZoneMobRecord(MobType.WOLF_GRAY_LIGHT_ALPHA_BASE_34, 4, 100),
                        new SpawnZoneMobRecord(MobType.DEER_FEMALE_BASE_29, 2, 100),
                        new SpawnZoneMobRecord(MobType.DEER_BABY_BASE_15, 3, 100),
                        new SpawnZoneMobRecord(MobType.MOLE_BASE_70, 3, 100)
                ), 120, false, true), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(85, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), -310, 150, 670)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.WOLF_BLACK_ALPHA_BASE_36, 2, 100),
                        new SpawnZoneMobRecord(MobType.WOLF_GRAY_LIGHT_ALPHA_BASE_34, 4, 100),
                        new SpawnZoneMobRecord(MobType.DEER_BASE_34, 2, 100),
                        new SpawnZoneMobRecord(MobType.MOLE_BASE_70, 3, 100)
                ), 80, false, true), 1)),


                new SpawnZoneRecord(new AbstractSpawnZoneMeta(86, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), 1175, 120, 496)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.TUNDRA_WOLF_BASE_45, 3, 100),
                        new SpawnZoneMobRecord(MobType.TUNDRA_WOLF_BASE_48, 2, 100),
                        new SpawnZoneMobRecord(MobType.TUNDRA_WOLF_BASE_47, 3, 100),
                        new SpawnZoneMobRecord(MobType.DEER_BASE_34, 5, 100),
                        new SpawnZoneMobRecord(MobType.DEER_FEMALE_BASE_29, 4, 100),
                        new SpawnZoneMobRecord(MobType.FOX_BASE_22, 3, 100),
                        new SpawnZoneMobRecord(MobType.LEOPARD_SNOW_BASE_60, 1, 25),
                        new SpawnZoneMobRecord(MobType.LEOPARD_SNOW_BABY_BASE_40, 1, 10)
                ), 120, false, true), 1)),


                new SpawnZoneRecord(new AbstractSpawnZoneMeta(87, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), 1190, 120, 636)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.TUNDRA_WOLF_BASE_45, 3, 100),
                        new SpawnZoneMobRecord(MobType.TUNDRA_WOLF_BASE_48, 4, 100),
                        new SpawnZoneMobRecord(MobType.TUNDRA_WOLF_BASE_47, 3, 100),
                        new SpawnZoneMobRecord(MobType.DEER_BASE_34, 5, 100),
                        new SpawnZoneMobRecord(MobType.DEER_FEMALE_BASE_29, 4, 100),
                        new SpawnZoneMobRecord(MobType.FOX_BASE_22, 3, 100),
                        new SpawnZoneMobRecord(MobType.LEOPARD_SNOW_BASE_60, 1, 25),
                        new SpawnZoneMobRecord(MobType.LEOPARD_SNOW_BABY_BASE_40, 1, 10)
                ), 120, false, true), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(88, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), 1017, 150, 712)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.TUNDRA_WOLF_BASE_45, 3, 100),
                        new SpawnZoneMobRecord(MobType.TUNDRA_WOLF_BASE_48, 6, 100),
                        new SpawnZoneMobRecord(MobType.TUNDRA_WOLF_BASE_47, 3, 100),
                        new SpawnZoneMobRecord(MobType.DEER_BASE_34, 5, 100),
                        new SpawnZoneMobRecord(MobType.DEER_FEMALE_BASE_29, 4, 100),
                        new SpawnZoneMobRecord(MobType.FOX_BASE_22, 3, 100),
                        new SpawnZoneMobRecord(MobType.LEOPARD_SNOW_BASE_60, 1, 25),
                        new SpawnZoneMobRecord(MobType.LEOPARD_SNOW_BABY_BASE_40, 1, 10)
                ), 150, false, true), 1)),


                new SpawnZoneRecord(new AbstractSpawnZoneMeta(89, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), 707, 120, -14)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.WOLF_BLONDE_ALPHA_BASE_30, 1, 100),
                        new SpawnZoneMobRecord(MobType.WOLF_GRAY_DARK_BASE_24, 2, 100),
                        new SpawnZoneMobRecord(MobType.DEER_BASE_34, 5, 100),
                        new SpawnZoneMobRecord(MobType.DEER_FEMALE_BASE_29, 2, 100),
                        new SpawnZoneMobRecord(MobType.DEER_BABY_BASE_15, 3, 100)
                ), 80, false, true), 1)),


                new SpawnZoneRecord(new AbstractSpawnZoneMeta(90, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), 906, 120, 391)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.WOLF_BLONDE_ALPHA_BASE_30, 1, 100),
                        new SpawnZoneMobRecord(MobType.WOLF_GRAY_DARK_BASE_24, 2, 100),
                        new SpawnZoneMobRecord(MobType.DEER_BASE_34, 5, 100),
                        new SpawnZoneMobRecord(MobType.DEER_FEMALE_BASE_29, 2, 100),
                        new SpawnZoneMobRecord(MobType.DEER_BABY_BASE_15, 3, 100),
                        new SpawnZoneMobRecord(MobType.LEOPARD_SNOW_BABY_BASE_40, 1, 50)
                ), 80, false, true), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(91, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), -980, 120, -224)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.WOLF_BLONDE_ALPHA_BASE_30, 1, 100),
                        new SpawnZoneMobRecord(MobType.WOLF_GRAY_DARK_BASE_24, 2, 100),
                        new SpawnZoneMobRecord(MobType.DEER_BASE_34, 5, 100),
                        new SpawnZoneMobRecord(MobType.DEER_FEMALE_BASE_29, 2, 100),
                        new SpawnZoneMobRecord(MobType.DEER_BABY_BASE_15, 3, 100)
                ), 70, false, true), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(92, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), -1156, 180, -484)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.ARMADILLO_BASE_55, 4, 100),
                        new SpawnZoneMobRecord(MobType.SCORPION_POISONOUS_DESERT_BASE_35, 1, 100),
                        new SpawnZoneMobRecord(MobType.SCORPION_BASE_30, 3, 100)
                ), 150, false, true), 1)),


                new SpawnZoneRecord(new AbstractSpawnZoneMeta(93, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), -1169, 180, -624)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.ARMADILLO_BASE_55, 4, 100),
                        new SpawnZoneMobRecord(MobType.SCORPION_POISONOUS_DESERT_BASE_35, 1, 100),
                        new SpawnZoneMobRecord(MobType.SCORPION_BASE_30, 3, 100)
                ), 70, false, true), 1)),


                new SpawnZoneRecord(new AbstractSpawnZoneMeta(94, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), -1075, 180, -792)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.SCORPION_BASE_30, 25, 100),
                        new SpawnZoneMobRecord(MobType.SCORPION_POISONOUS_DESERT_BASE_35, 5, 100)
                ), 70, false, true), 1)),


                new SpawnZoneRecord(new AbstractSpawnZoneMeta(95, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), -960, 120, -490)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.ARMADILLO_BASE_55, 4, 100),
                        new SpawnZoneMobRecord(MobType.SCORPION_POISONOUS_DESERT_BASE_35, 1, 100),
                        new SpawnZoneMobRecord(MobType.SCORPION_BASE_30, 3, 100)
                ), 50, false, true), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(96, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), -893, 120, -689)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.ARMADILLO_BASE_55, 4, 100),
                        new SpawnZoneMobRecord(MobType.SCORPION_POISONOUS_DESERT_BASE_35, 1, 100),
                        new SpawnZoneMobRecord(MobType.SCORPION_BASE_30, 3, 100)
                ), 70, false, true), 1)),


                new SpawnZoneRecord(new AbstractSpawnZoneMeta(97, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), -893, 120, -781)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.ARMADILLO_BASE_55, 4, 100),
                        new SpawnZoneMobRecord(MobType.SCORPION_POISONOUS_DESERT_BASE_35, 1, 100),
                        new SpawnZoneMobRecord(MobType.SCORPION_BASE_30, 3, 100)
                ), 70, false, true), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(98, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), -1160, 120, -867)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.ARMADILLO_BASE_55, 4, 100),
                        new SpawnZoneMobRecord(MobType.SCORPION_POISONOUS_DESERT_BASE_35, 1, 100),
                        new SpawnZoneMobRecord(MobType.SCORPION_BASE_30, 3, 100)
                ), 50, false, true), 1)),


                new SpawnZoneRecord(new AbstractSpawnZoneMeta(99, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), -914, 120, -573)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.ARMADILLO_BASE_55, 4, 100),
                        new SpawnZoneMobRecord(MobType.SCORPION_POISONOUS_DESERT_BASE_35, 4, 100)
                ), 50, false, true), 1)),


                new SpawnZoneRecord(new AbstractSpawnZoneMeta(100, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), 1263, 80, -164)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.SNIFFER_BASE_65, 15, 100),
                        new SpawnZoneMobRecord(MobType.STRIDER_BASE_60, 15, 100)
                ), 150, false, true), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(101, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), -1030, 200, 165)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.FROG_BASE_25, 12, 100),
                        new SpawnZoneMobRecord(MobType.FROG_BASE_26, 12, 100)
                ), 150, false, true), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(102, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), -774, 200, 550)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        // todo
                ), 100, false, true), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(103, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), -879, 200, 566)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.FROG_BASE_25, 12, 100),
                        new SpawnZoneMobRecord(MobType.FROG_BASE_26, 14, 100)
                ), 150, false, true), 1)),


                new SpawnZoneRecord(new AbstractSpawnZoneMeta(104, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), -837, 100, 323)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.FROG_BASE_25, 6, 100),
                        new SpawnZoneMobRecord(MobType.FROG_BASE_26, 6, 100)
                ), 100, false, true), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(105, SpawnZoneType.AREA, 15, 15,
                        new Location(Bukkit.getWorld("world"), -415, 65, 180)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.ARMADILLO_NON_AGGRO_BASE_15, 20, 100)
                ), 80, false, false), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(106, SpawnZoneType.AREA, 15, 15,
                        new Location(Bukkit.getWorld("world"), -328, 67, 290)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.ARMADILLO_NON_AGGRO_BASE_15, 20, 100)
                ), 80, false, false), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(107, SpawnZoneType.POINT, 20, 1,
                        new Location(Bukkit.getWorld("world"), -440, 65, 206.5)), toVersionedJson(new PointSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.CITY_GUARD_STATIC, 1, 100)
                ), false, null, null), 1)),


                new SpawnZoneRecord(new AbstractSpawnZoneMeta(108, SpawnZoneType.POINT, 20, 1,
                        new Location(Bukkit.getWorld("world"), -423.5, 65, 227)), toVersionedJson(new PointSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.CITY_GUARD_STATIC, 1, 100)
                ), false, null, null), 1)),

                // Drakkar gym
                new SpawnZoneRecord(new AbstractSpawnZoneMeta(109, SpawnZoneType.POINT, 30, 20,
                        new Location(Bukkit.getWorld("world"), 198.5, 72, -49.5)), toVersionedJson(new PointSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.COMBAT_DUMMY_FACE_SOUTH, 1, 100)
                ), false, null, null), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(110, SpawnZoneType.POINT, 30, 20,
                        new Location(Bukkit.getWorld("world"), 200.5, 72, -49.5)), toVersionedJson(new PointSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.COMBAT_DUMMY_FACE_SOUTH, 1, 100)
                ), false, null, null), 1)),

                // Drakkar auctioneer
                new SpawnZoneRecord(new AbstractSpawnZoneMeta(111, SpawnZoneType.POINT, 30, 20,
                        new Location(Bukkit.getWorld("world"), 205.5, 73, -59.5)), toVersionedJson(new PointSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.AUCTIONEER, 1, 100)
                ), false, MarkerType.AUCTION, "Auction"), 1)),

                // Brown farm house trader
                new SpawnZoneRecord(new AbstractSpawnZoneMeta(112, SpawnZoneType.POINT, 30, 20,
                        new Location(Bukkit.getWorld("world"), 253, 68, -277)), toVersionedJson(new PointSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.BROWN_FARMHOUSE_MOUNT_SHOP, 1, 100)
                ), false, MarkerType.MOUNT_SHOP, "Mount Shop"), 1)),

                // Brown farm house trader
                new SpawnZoneRecord(new AbstractSpawnZoneMeta(113, SpawnZoneType.POINT, 30, 20,
                        new Location(Bukkit.getWorld("world"), 180.5, 72, -109.5)), toVersionedJson(new PointSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.ABRAHAM_FAIRY_TEACHER, 1, 100)
                ), false, null, null), 1)),

                // Sheep shearer quest npc
                new SpawnZoneRecord(new AbstractSpawnZoneMeta(114, SpawnZoneType.POINT, 30, 20,
                        new Location(Bukkit.getWorld("world"), 45.5, 68, -162.5)), toVersionedJson(new PointSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.SHEPHERD, 1, 100)
                ), false, null, null), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(115, SpawnZoneType.AREA, 30, 20,
                        new Location(Bukkit.getWorld("world"), 66, 68, -165)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.SHEEP, 20, 100)
                ), 20, false, false), 1)),


                // Tutorial island
                new SpawnZoneRecord(new AbstractSpawnZoneMeta(117, SpawnZoneType.POINT, 10, 20,
                        new Location(Bukkit.getWorld("world"), 2432.5, 155, 2479.5)), toVersionedJson(new PointSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.PEASANT_MINER, 1, 100)
                ), false, null, null), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(116, SpawnZoneType.POINT, 10, 20,
                        new Location(Bukkit.getWorld("world"), 2440, 145, 2527)), toVersionedJson(new PointSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.TUTOR_BLACKSMITH, 1, 100)
                ), false, null, null), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(118, SpawnZoneType.POINT, 10, 20,
                        new Location(Bukkit.getWorld("world"), 2517.5, 128, 2412.5)), toVersionedJson(new PointSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.TUTOR_LUMBERJACK, 1, 100)
                ), false, null, null), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(119, SpawnZoneType.POINT, 10, 20,
                        new Location(Bukkit.getWorld("world"), 2520.5, 110, 2451.5)), toVersionedJson(new PointSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.COMBAT_DUMMY_FACE_SOUTH, 1, 100)
                ), false, null, null), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(120, SpawnZoneType.AREA, 10, 50,
                        new Location(Bukkit.getWorld("world"), 2574, 111, 2423)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.GOBLIN_1_BASE_1, 3, 100),
                        new SpawnZoneMobRecord(MobType.GOBLIN_2_BASE_1, 3, 100),
                        new SpawnZoneMobRecord(MobType.GOBLIN_3_BASE_1, 3, 100),
                        new SpawnZoneMobRecord(MobType.GOBLIN_4_BASE_1, 3, 100),
                        new SpawnZoneMobRecord(MobType.GOBLIN_5_BASE_1, 3, 100)
                ), 30, false, true), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(121, SpawnZoneType.POINT, 10, 20,
                        new Location(Bukkit.getWorld("world"), 2522, 110, 2454)), toVersionedJson(new PointSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.TUTOR_SURVIVAL, 1, 100)
                ), false, null, null), 1)),

                // Drakkar armor merchant
                new SpawnZoneRecord(new AbstractSpawnZoneMeta(123, SpawnZoneType.POINT, 30, 20,
                        new Location(Bukkit.getWorld("world"), 153, 73, -94.5)), toVersionedJson(new PointSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.DRAKKAR_ARMOR_SHOP, 1, 100)
                ), false, MarkerType.ARMOR_SHOP, "Armor Shop"), 1)),

                // Drakkar basic food stall
                new SpawnZoneRecord(new AbstractSpawnZoneMeta(124, SpawnZoneType.POINT, 30, 20,
                        new Location(Bukkit.getWorld("world"), 208.5, 73, -79.5)), toVersionedJson(new PointSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.BASIC_FOOD_STALL_SHOP, 1, 100)
                ), false, MarkerType.FOOD_SHOP, "Food Shop"), 1)),

                // Drakkar guard archer merchant
                new SpawnZoneRecord(new AbstractSpawnZoneMeta(125, SpawnZoneType.POINT, 30, 20,
                        new Location(Bukkit.getWorld("world"), 127.5, 72, -58)), toVersionedJson(new PointSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.DRAKKAR_GUARD_ARCHER_SHOP, 1, 100)
                ), false, MarkerType.ARCHERY_SHOP, "Archery Shop"), 1)),

                // Murkwatch
                new SpawnZoneRecord(new AbstractSpawnZoneMeta(126, SpawnZoneType.POINT, 30, 1,
                        new Location(Bukkit.getWorld("world"), -1035, 68, 379)), toVersionedJson(new PointSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.BANK_TELLER_1, 1, 100)
                ), false, MarkerType.BANK_TELLER, "Bank"), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(134, SpawnZoneType.POINT, 30, 20,
                        new Location(Bukkit.getWorld("world"), -977.5, 68, 411.5)), toVersionedJson(new PointSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.AUCTIONEER, 1, 100)
                ), false, MarkerType.AUCTION, "Auction"), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(135, SpawnZoneType.POINT, 30, 1,
                        new Location(Bukkit.getWorld("world"), -1008, 68.5, 417.5)), toVersionedJson(new PointSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.MURKWATCH_GENERAL_STORE, 1, 100)
                ), false, MarkerType.GENERAL_STORE, "General Store"), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(136, SpawnZoneType.POINT, 30, 1,
                        new Location(Bukkit.getWorld("world"), -1041, 68, 423.5)), toVersionedJson(new PointSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.MURKWATCH_BLACKSMITH, 1, 100)
                ), false, MarkerType.BLACKSMITH, "Blacksmith"), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(137, SpawnZoneType.POINT, 30, 20,
                        new Location(Bukkit.getWorld("world"), -968, 76, 436.5)), toVersionedJson(new PointSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.MURKWATCH_ARCHER_SHOP, 1, 100)
                ), false, MarkerType.ARCHERY_SHOP, "Archery Shop"), 1)),

                // Aetheris bank
                new SpawnZoneRecord(new AbstractSpawnZoneMeta(127, SpawnZoneType.POINT, 30, 1,
                        new Location(Bukkit.getWorld("world"), -130, 81, 758.5)), toVersionedJson(new PointSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.BANK_TELLER_2, 1, 100)
                ), false, MarkerType.BANK_TELLER, "Bank"), 1)),

                // Sporefell bank
                new SpawnZoneRecord(new AbstractSpawnZoneMeta(128, SpawnZoneType.POINT, 30, 1,
                        new Location(Bukkit.getWorld("world"), 1254, 71, -166.5)), toVersionedJson(new PointSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.BANK_TELLER_4, 1, 100)
                ), false, MarkerType.BANK_TELLER, "Bank"), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(129, SpawnZoneType.POINT, 30, 1,
                        new Location(Bukkit.getWorld("world"), 1251, 71, -166.5)), toVersionedJson(new PointSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.BANK_TELLER_5, 1, 100)
                ), false, MarkerType.BANK_TELLER, "Bank"), 1)),

                // Islenor bank
                new SpawnZoneRecord(new AbstractSpawnZoneMeta(130, SpawnZoneType.POINT, 30, 1,
                        new Location(Bukkit.getWorld("world"), 501, 66, -686.3)), toVersionedJson(new PointSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.BANK_TELLER_6, 1, 100)
                ), false, MarkerType.BANK_TELLER, "Bank"), 1)),

                // Valdrin bank
                new SpawnZoneRecord(new AbstractSpawnZoneMeta(131, SpawnZoneType.POINT, 30, 1,
                        new Location(Bukkit.getWorld("world"), 10.5, 118, -528.3)), toVersionedJson(new PointSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.BANK_TELLER_7, 1, 100)
                ), false, MarkerType.BANK_TELLER, "Bank"), 1)),

                // Saharijin bank
                new SpawnZoneRecord(new AbstractSpawnZoneMeta(132, SpawnZoneType.POINT, 30, 1,
                        new Location(Bukkit.getWorld("world"), -1234.5, 78, -847.5)), toVersionedJson(new PointSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.BANK_TELLER_8, 1, 100)
                ), false, MarkerType.BANK_TELLER, "Bank"), 1)),

                // Sakurmeer bank
                new SpawnZoneRecord(new AbstractSpawnZoneMeta(133, SpawnZoneType.POINT, 30, 1,
                        new Location(Bukkit.getWorld("world"), 886.5, 133, 191.5)), toVersionedJson(new PointSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.BANK_TELLER_9, 1, 100)
                ), false, MarkerType.BANK_TELLER, "Bank"), 1)),

                // Brineport
                new SpawnZoneRecord(new AbstractSpawnZoneMeta(139, SpawnZoneType.POINT, 30, 20,
                        new Location(Bukkit.getWorld("world"), -424.5, 65.5, 274.5)), toVersionedJson(new PointSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.BRINEPORT_FISHING_SHOP, 1, 100)
                ), false, MarkerType.FISHING_SHOP, "Fishing Shop"), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(140, SpawnZoneType.POINT, 30, 20,
                        new Location(Bukkit.getWorld("world"), -487.5, 65, 198.5)), toVersionedJson(new PointSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.BRINEPORT_GENERAL_STORE, 1, 100)
                ), false, MarkerType.GENERAL_STORE, "General Store"), 1)),


                // Fenshade
                new SpawnZoneRecord(new AbstractSpawnZoneMeta(141, SpawnZoneType.POINT, 30, 20,
                        new Location(Bukkit.getWorld("world"), -779.5, 70, 554.5)), toVersionedJson(new PointSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.FENSHADE_MINING_SHOP, 1, 100)
                ), false, MarkerType.MINING_SHOP, "Mining Shop"), 1)),


                // Ashwale
                new SpawnZoneRecord(new AbstractSpawnZoneMeta(142, SpawnZoneType.POINT, 30, 20,
                        new Location(Bukkit.getWorld("world"), -70.5, 67.5, -60)), toVersionedJson(new PointSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.ASHWALE_MINING_SHOP, 1, 100)
                ), false, MarkerType.MINING_SHOP, "Mining Shop"), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(143, SpawnZoneType.POINT, 30, 20,
                        new Location(Bukkit.getWorld("world"), -52, 67.5, -76.6)), toVersionedJson(new PointSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.ASHWALE_AXE_SHOP, 1, 100)
                ), false, MarkerType.AXE_SHOP, "Axe Shop"), 1)),


                new SpawnZoneRecord(new AbstractSpawnZoneMeta(144, SpawnZoneType.POINT, 30, 20,
                        new Location(Bukkit.getWorld("world"), 1269.5, 73, -219.5)), toVersionedJson(new PointSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.SPOREFELL_FISHING_SHOP, 1, 100)
                ), false, MarkerType.FISHING_SHOP, "Fishing Shop"), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(145, SpawnZoneType.POINT, 30, 20,
                        new Location(Bukkit.getWorld("world"), -1248.5, 68, -798.5)), toVersionedJson(new PointSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.SAHARIJIN_FISHING_SHOP, 1, 100)
                ), false, MarkerType.FISHING_SHOP, "Fishing Shop"), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(146, SpawnZoneType.POINT, 30, 20,
                        new Location(Bukkit.getWorld("world"), -870.5, 89, -376)), toVersionedJson(new PointSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.FUSION_MASTER, 1, 100)
                ), false, MarkerType.FUSION_MASTER, "Fusion Master"), 1)),


                // Eldarwyn general store
                new SpawnZoneRecord(new AbstractSpawnZoneMeta(147, SpawnZoneType.POINT, 30, 20,
                        new Location(Bukkit.getWorld("world"), -866, 79, -375)), toVersionedJson(new PointSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.ELDARWYN_GENERAL_STORE, 1, 100)
                ), false, MarkerType.GENERAL_STORE, "General Store"), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(148, SpawnZoneType.POINT, 30, 20,
                        new Location(Bukkit.getWorld("world"), -814.5, 79.5, -331.5)), toVersionedJson(new PointSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.ELDARWYN_ARMOR_SHOP, 1, 100)
                ), false, MarkerType.ARMOR_SHOP, "Armor Shop"), 1)),

                // Valdrin weapon mearchant
                new SpawnZoneRecord(new AbstractSpawnZoneMeta(149, SpawnZoneType.POINT, 30, 20,
                        new Location(Bukkit.getWorld("world"), -5, 117.5, -543.5)), toVersionedJson(new PointSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.VALDRIN_WEAPON_SHOP, 1, 100)
                ), false, MarkerType.WEAPON_SHOP, "Weapon Shop"), 1)),

                // Infernak blacksmith
                new SpawnZoneRecord(new AbstractSpawnZoneMeta(150, SpawnZoneType.POINT, 30, 20,
                        new Location(Bukkit.getWorld("world"), 1025, 80, -669)), toVersionedJson(new PointSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.INFERNAK_BLACKSMITH, 1, 100)
                ), false, MarkerType.BLACKSMITH, "Blacksmith"), 1)),


                // Sakurmeer general store
                new SpawnZoneRecord(new AbstractSpawnZoneMeta(151, SpawnZoneType.POINT, 30, 20,
                        new Location(Bukkit.getWorld("world"), 890, 135, 240)), toVersionedJson(new PointSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.SAKURMEER_GENERAL_STORE, 1, 100)
                ), false, MarkerType.GENERAL_STORE, "General Store"), 1)),

                // Aetheris
                new SpawnZoneRecord(new AbstractSpawnZoneMeta(152, SpawnZoneType.POINT, 30, 20,
                        new Location(Bukkit.getWorld("world"), -104.5, 80, 792.5)), toVersionedJson(new PointSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.AETHERIS_GENERAL_STORE, 1, 100)
                ), false, MarkerType.GENERAL_STORE, "General Store"), 1)),

                // Islenor
                new SpawnZoneRecord(new AbstractSpawnZoneMeta(153, SpawnZoneType.POINT, 30, 20,
                        new Location(Bukkit.getWorld("world"), 500, 66, -752.5)), toVersionedJson(new PointSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.ISLENOR_GENERAL_STORE, 1, 100)
                ), false, MarkerType.GENERAL_STORE, "General Store"), 1)),


                // Drakkar coachman
                new SpawnZoneRecord(new AbstractSpawnZoneMeta(154, SpawnZoneType.POINT, 30, 20,
                        new Location(Bukkit.getWorld("world"), 181, 67, -179.3)), toVersionedJson(new PointSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.DRAKKAR_COACHMAN, 1, 100)
                ), false, MarkerType.QUICK_TRAVEL, "Coachman"), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(155, SpawnZoneType.POINT, 30, 20,
                        new Location(Bukkit.getWorld("world"), 179.5, 67, -180.5)), toVersionedJson(new PointSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.STATIC_HORSE, 1, 100)
                ), false, null, null), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(156, SpawnZoneType.POINT, 30, 20,
                        new Location(Bukkit.getWorld("world"), 1058, 72, -329)), toVersionedJson(new PointSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.THORNBARK_GENERAL_STORE, 1, 100)
                ), false, MarkerType.GENERAL_STORE, "General Store"), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(157, SpawnZoneType.POINT, 30, 20,
                        new Location(Bukkit.getWorld("world"), 858, 121, 223)), toVersionedJson(new PointSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.FUSION_MASTER, 1, 100)
                ), false, MarkerType.FUSION_MASTER, "Fusion Master"), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(158, SpawnZoneType.POINT, 30, 20,
                        new Location(Bukkit.getWorld("world"), 872, 141, 130)), toVersionedJson(new PointSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.SAKURMEER_FOOD_SHOP, 1, 100)
                ), false, MarkerType.FOOD_SHOP, "Food Shop"), 1)),

                // Sakurmeer auctioner
                new SpawnZoneRecord(new AbstractSpawnZoneMeta(159, SpawnZoneType.POINT, 30, 20,
                        new Location(Bukkit.getWorld("world"), 956.5, 137.5, 207.5)), toVersionedJson(new PointSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.AUCTIONEER, 1, 100)
                ), false, MarkerType.AUCTION, "Auction"), 1)),

                // Saharijin auctioneer
                new SpawnZoneRecord(new AbstractSpawnZoneMeta(160, SpawnZoneType.POINT, 30, 20,
                        new Location(Bukkit.getWorld("world"), -1223.5, 68, -786.5)), toVersionedJson(new PointSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.AUCTIONEER, 1, 100)
                ), false, MarkerType.AUCTION, "Auction"), 1)),

                // Saharijin arena overseer
                new SpawnZoneRecord(new AbstractSpawnZoneMeta(161, SpawnZoneType.POINT, 30, 20,
                        new Location(Bukkit.getWorld("world"), -1188.5, 69, -705.5)), toVersionedJson(new PointSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.SAHARIJIN_ARENA_NPC, 1, 100)
                ), false, null, null), 1)),


                // Rogues haven quick travel
                new SpawnZoneRecord(new AbstractSpawnZoneMeta(162, SpawnZoneType.POINT, 30, 20,
                        new Location(Bukkit.getWorld("world"), -140, 63, -993)), toVersionedJson(new PointSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.ROGUES_HAVEN_SAILOR, 1, 100)
                ), false, MarkerType.QUICK_TRAVEL, "Sailor"), 1)),


                // Sailor buffer ship
                new SpawnZoneRecord(new AbstractSpawnZoneMeta(163, SpawnZoneType.POINT, 30, 20,
                        new Location(Bukkit.getWorld("world"), 1415.5, 68.5, -1527)), toVersionedJson(new PointSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.SAILOR_BUFFER, 1, 100)
                ), false, null, null), 1)),


                // Islenor quick travel
                new SpawnZoneRecord(new AbstractSpawnZoneMeta(164, SpawnZoneType.POINT, 30, 20,
                        new Location(Bukkit.getWorld("world"), 527, 67, -820)), toVersionedJson(new PointSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.ISLENOR_SAILOR, 1, 100)
                ), false, MarkerType.QUICK_TRAVEL, "Sailor"), 1)),


                // Murkwatch quick travel
                new SpawnZoneRecord(new AbstractSpawnZoneMeta(165, SpawnZoneType.POINT, 30, 20,
                        new Location(Bukkit.getWorld("world"), -1159, 67, 465)), toVersionedJson(new PointSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.MURKWATCH_SAILOR, 1, 100)
                ), false, MarkerType.QUICK_TRAVEL, "Sailor"), 1)),

                // Jungle ocelots on top island

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(166, SpawnZoneType.AREA, 180, 50,
                        new Location(Bukkit.getWorld("world"), 114, 117, 472)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.OCELOT_BASE_44, 1, 100),
                        new SpawnZoneMobRecord(MobType.OCELOT_BASE_45, 2, 100),
                        new SpawnZoneMobRecord(MobType.OCELOT_BASE_48, 2, 100)
                ), 40, false, true), 1)),

                // Swamp island swamp frogs
                new SpawnZoneRecord(new AbstractSpawnZoneMeta(167, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), -820, 64, 455)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.FROG_BASE_25_NO_AGGRO, 12, 100),
                        new SpawnZoneMobRecord(MobType.FROG_BASE_26_NO_AGGRO, 12, 100)
                ), 50, false, false), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(168, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), -868, 64, 321)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.FROG_BASE_25_NO_AGGRO, 12, 100),
                        new SpawnZoneMobRecord(MobType.FROG_BASE_26_NO_AGGRO, 12, 100)
                ), 50, false, false), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(170, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), -1017, 64.5, 427)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.FROG_BASE_25_NO_AGGRO, 30, 100),
                        new SpawnZoneMobRecord(MobType.FROG_BASE_26_NO_AGGRO, 30, 100)
                ), 80, false, false), 1)),

                // Valdrin eagles
                new SpawnZoneRecord(new AbstractSpawnZoneMeta(171, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), -86, 200, -547)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.EAGLE_BLACK_BASE_50, 3, 100),
                        new SpawnZoneMobRecord(MobType.EAGLE_BROWNHEAD_BASE_55, 2, 100),
                        new SpawnZoneMobRecord(MobType.EAGLE_BROWN_BASE_48, 4, 100)
                ), 50, false, true), 1)),


                // Sporefell armadillo beach spawns
                new SpawnZoneRecord(new AbstractSpawnZoneMeta(172, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), 1116, 65, -174)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.ARMADILLO_BASE_55, 3, 100)
                ), 30, false, false), 1)),


                new SpawnZoneRecord(new AbstractSpawnZoneMeta(173, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), 1122, 65, -220)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.ARMADILLO_BASE_55, 8, 100)
                ), 30, false, false), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(174, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), 1189, 66, -234)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.ARMADILLO_BASE_55, 3, 100)
                ), 30, false, false), 1)),


                new SpawnZoneRecord(new AbstractSpawnZoneMeta(175, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), 1216, 65, -266)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.ARMADILLO_BASE_55, 3, 100)
                ), 30, false, false), 1)),


                new SpawnZoneRecord(new AbstractSpawnZoneMeta(176, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), 1290, 73, -284)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.ARMADILLO_BASE_55, 8, 100)
                ), 50, false, false), 1)),


                new SpawnZoneRecord(new AbstractSpawnZoneMeta(177, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), 1361, 65, -167)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.ARMADILLO_BASE_55, 8, 100)
                ), 30, false, false), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(178, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), 1172, 67, -68)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.ARMADILLO_BASE_55, 4, 100)
                ), 30, false, false), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(179, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), 1115, 69, -111)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.ARMADILLO_BASE_55, 8, 100)
                ), 30, false, false), 1)),


                // Murkwatch bandits
                new SpawnZoneRecord(new AbstractSpawnZoneMeta(180, SpawnZoneType.AREA, 120, 15,
                        new Location(Bukkit.getWorld("world"), -871, 70, 420)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.BANDIT_BASE_20_1, 1, 100),
                        new SpawnZoneMobRecord(MobType.BANDIT_BASE_20_2, 1, 100),
                        new SpawnZoneMobRecord(MobType.BANDIT_BASE_20_3, 1, 100),
                        new SpawnZoneMobRecord(MobType.BANDIT_BASE_20_4, 1, 100)
                ), 10, false, false), 1)),


                // Glaciem bandits
                new SpawnZoneRecord(new AbstractSpawnZoneMeta(181, SpawnZoneType.AREA, 120, 15,
                        new Location(Bukkit.getWorld("world"), 824, 74, 414)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.BANDIT_BASE_40_1, 1, 100),
                        new SpawnZoneMobRecord(MobType.BANDIT_BASE_40_3, 1, 100)
                ), 10, false, false), 1)),


                // Aetheris bandits
                new SpawnZoneRecord(new AbstractSpawnZoneMeta(182, SpawnZoneType.AREA, 600, 50,
                        new Location(Bukkit.getWorld("world"), 44, 73, 778)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.BANDIT_BASE_80_1, 2, 10),
                        new SpawnZoneMobRecord(MobType.BANDIT_BASE_80_2, 2, 10)
                ), 10, false, false), 1)),


                // Rogues haven bandits
                new SpawnZoneRecord(new AbstractSpawnZoneMeta(183, SpawnZoneType.AREA, 300, 50,
                        new Location(Bukkit.getWorld("world"), -76, 72, -1080)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.BANDIT_BASE_80_1, 3, 10),
                        new SpawnZoneMobRecord(MobType.BANDIT_BASE_60_2, 3, 10)
                ), 10, false, false), 1)),


                new SpawnZoneRecord(new AbstractSpawnZoneMeta(184, SpawnZoneType.AREA, 300, 50,
                        new Location(Bukkit.getWorld("world"), -217, 80, -1090)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.BANDIT_BASE_60_1, 3, 10),
                        new SpawnZoneMobRecord(MobType.BANDIT_BASE_60_2, 3, 10)
                ), 10, false, false), 1)),


                new SpawnZoneRecord(new AbstractSpawnZoneMeta(185, SpawnZoneType.AREA, 600, 50,
                        new Location(Bukkit.getWorld("world"), -76, 72, -1080)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.BANDIT_BASE_60_1, 3, 10),
                        new SpawnZoneMobRecord(MobType.BANDIT_BASE_60_2, 3, 10)
                ), 10, false, false), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(186, SpawnZoneType.AREA, 600, 50,
                        new Location(Bukkit.getWorld("world"), -176, 86, -993.5)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.BANDIT_BASE_60_1, 3, 10),
                        new SpawnZoneMobRecord(MobType.BANDIT_BASE_60_2, 3, 10)
                ), 10, false, false), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(187, SpawnZoneType.AREA, 500, 50,
                        new Location(Bukkit.getWorld("world"), 100, 72, -1056)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.BANDIT_BASE_80_1, 1, 80),
                        new SpawnZoneMobRecord(MobType.BANDIT_BASE_80_2, 2, 80),
                        new SpawnZoneMobRecord(MobType.BANDIT_BASE_80_3, 1, 80),
                        new SpawnZoneMobRecord(MobType.BANDIT_BASE_80_4, 1, 80)
                ), 10, false, false), 1)),

                // Branleaft bandits
                new SpawnZoneRecord(new AbstractSpawnZoneMeta(188, SpawnZoneType.AREA, 300, 50,
                        new Location(Bukkit.getWorld("world"), -650, 75, -415)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.BANDIT_BASE_40_1, 1, 80),
                        new SpawnZoneMobRecord(MobType.BANDIT_BASE_40_2, 2, 80),
                        new SpawnZoneMobRecord(MobType.BANDIT_BASE_40_3, 1, 80),
                        new SpawnZoneMobRecord(MobType.BANDIT_BASE_40_4, 1, 80)
                ), 10, false, false), 1)),

                // Islenor bandits
                new SpawnZoneRecord(new AbstractSpawnZoneMeta(189, SpawnZoneType.AREA, 300, 50,
                        new Location(Bukkit.getWorld("world"), 296, 82, -531)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.BANDIT_BASE_40_1, 1, 80),
                        new SpawnZoneMobRecord(MobType.BANDIT_BASE_40_2, 2, 80),
                        new SpawnZoneMobRecord(MobType.BANDIT_BASE_40_3, 1, 80),
                        new SpawnZoneMobRecord(MobType.BANDIT_BASE_40_4, 1, 80)
                ), 10, false, false), 1)),


                // Drakkar bunnies
                new SpawnZoneRecord(new AbstractSpawnZoneMeta(190, SpawnZoneType.AREA, 30, 50,
                        new Location(Bukkit.getWorld("world"), 82, 90, 244)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.BUNNY_BLACK_BASE_12, 5, 100),
                        new SpawnZoneMobRecord(MobType.BUNNY_GRAY_BASE_15, 5, 100),
                        new SpawnZoneMobRecord(MobType.BUNNY_BROWN_BASE_14, 5, 100),
                        new SpawnZoneMobRecord(MobType.BUNNY_WHITE_BASE_15, 5, 100)
                ), 40, false, false), 1)),


                new SpawnZoneRecord(new AbstractSpawnZoneMeta(191, SpawnZoneType.AREA, 30, 50,
                        new Location(Bukkit.getWorld("world"), 260, 85, 253)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.BUNNY_BLACK_BASE_12, 10, 100),
                        new SpawnZoneMobRecord(MobType.BUNNY_GRAY_BASE_15, 10, 100),
                        new SpawnZoneMobRecord(MobType.BUNNY_BROWN_BASE_14, 10, 100),
                        new SpawnZoneMobRecord(MobType.BUNNY_WHITE_BASE_15, 10, 100)
                ), 70, false, false), 1)),


                new SpawnZoneRecord(new AbstractSpawnZoneMeta(192, SpawnZoneType.AREA, 30, 50,
                        new Location(Bukkit.getWorld("world"), 379, 100, 280)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.BUNNY_BLACK_BASE_12, 5, 100),
                        new SpawnZoneMobRecord(MobType.BUNNY_GRAY_BASE_15, 5, 100),
                        new SpawnZoneMobRecord(MobType.BUNNY_BROWN_BASE_14, 5, 100),
                        new SpawnZoneMobRecord(MobType.BUNNY_WHITE_BASE_15, 5, 100)
                ), 40, false, false), 1)),

                // Sakurmeer bunnies
                new SpawnZoneRecord(new AbstractSpawnZoneMeta(193, SpawnZoneType.AREA, 30, 50,
                        new Location(Bukkit.getWorld("world"), 744, 100, -72)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.BUNNY_BLACK_BASE_12, 5, 100),
                        new SpawnZoneMobRecord(MobType.BUNNY_GRAY_BASE_15, 5, 100),
                        new SpawnZoneMobRecord(MobType.BUNNY_BROWN_BASE_14, 5, 100),
                        new SpawnZoneMobRecord(MobType.BUNNY_WHITE_BASE_15, 5, 100)
                ), 40, false, false), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(194, SpawnZoneType.AREA, 30, 50,
                        new Location(Bukkit.getWorld("world"), 566, 110, -235)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.BUNNY_BLACK_BASE_12, 10, 100),
                        new SpawnZoneMobRecord(MobType.BUNNY_GRAY_BASE_15, 10, 100),
                        new SpawnZoneMobRecord(MobType.BUNNY_BROWN_BASE_14, 10, 100),
                        new SpawnZoneMobRecord(MobType.BUNNY_WHITE_BASE_15, 10, 100)
                ), 70, false, false), 1)),

                // mountain eagles
                new SpawnZoneRecord(new AbstractSpawnZoneMeta(195, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), -266, 150, -617)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.EAGLE_BLACK_BASE_50, 3, 50),
                        new SpawnZoneMobRecord(MobType.EAGLE_BROWNHEAD_BASE_55, 2, 50),
                        new SpawnZoneMobRecord(MobType.EAGLE_BROWN_BASE_48, 4, 50)
                ), 80, false, false), 1)),


                new SpawnZoneRecord(new AbstractSpawnZoneMeta(196, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), 262, 180, -596)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.EAGLE_BLACK_BASE_50, 3, 50),
                        new SpawnZoneMobRecord(MobType.EAGLE_BROWNHEAD_BASE_55, 2, 50),
                        new SpawnZoneMobRecord(MobType.EAGLE_BROWN_BASE_48, 4, 50)
                ), 60, false, false), 1)),


                new SpawnZoneRecord(new AbstractSpawnZoneMeta(197, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), 163, 180, -478)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.EAGLE_BLACK_BASE_50, 3, 50),
                        new SpawnZoneMobRecord(MobType.EAGLE_BROWNHEAD_BASE_55, 2, 50),
                        new SpawnZoneMobRecord(MobType.EAGLE_BROWN_BASE_48, 4, 50)
                ), 60, false, false), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(199, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), 378, 150, -808)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.EAGLE_BLACK_BASE_50, 3, 50),
                        new SpawnZoneMobRecord(MobType.EAGLE_BROWNHEAD_BASE_55, 2, 50),
                        new SpawnZoneMobRecord(MobType.EAGLE_BROWN_BASE_48, 4, 50)
                ), 80, false, false), 1)),


                new SpawnZoneRecord(new AbstractSpawnZoneMeta(200, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), 786, 150, -795)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.EAGLE_BLACK_BASE_50, 3, 50),
                        new SpawnZoneMobRecord(MobType.EAGLE_BROWNHEAD_BASE_55, 2, 50),
                        new SpawnZoneMobRecord(MobType.EAGLE_BROWN_BASE_48, 4, 50)
                ), 60, false, false), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(201, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), 1035, 150, -616)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.EAGLE_BLACK_BASE_50, 3, 50),
                        new SpawnZoneMobRecord(MobType.EAGLE_BROWNHEAD_BASE_55, 2, 50),
                        new SpawnZoneMobRecord(MobType.EAGLE_BROWN_BASE_48, 4, 50)
                ), 80, false, false), 1)),


                new SpawnZoneRecord(new AbstractSpawnZoneMeta(202, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), 816, 128, -179)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.EAGLE_BLACK_BASE_50, 3, 50),
                        new SpawnZoneMobRecord(MobType.EAGLE_BROWNHEAD_BASE_55, 2, 50),
                        new SpawnZoneMobRecord(MobType.EAGLE_BROWN_BASE_48, 4, 50)
                ), 50, false, false), 1)),


                new SpawnZoneRecord(new AbstractSpawnZoneMeta(203, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), -218, 200, 507)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.EAGLE_BLACK_BASE_50, 3, 50),
                        new SpawnZoneMobRecord(MobType.EAGLE_BROWNHEAD_BASE_55, 2, 50),
                        new SpawnZoneMobRecord(MobType.EAGLE_BROWN_BASE_48, 4, 50)
                ), 50, false, false), 1)),


                new SpawnZoneRecord(new AbstractSpawnZoneMeta(204, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), -58, 200, 643)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.EAGLE_BLACK_BASE_50, 3, 50),
                        new SpawnZoneMobRecord(MobType.EAGLE_BROWNHEAD_BASE_55, 2, 50),
                        new SpawnZoneMobRecord(MobType.EAGLE_BROWN_BASE_48, 4, 50)
                ), 50, false, false), 1)),


                new SpawnZoneRecord(new AbstractSpawnZoneMeta(205, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), 211, 210, 739)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.EAGLE_BLACK_BASE_50, 3, 50),
                        new SpawnZoneMobRecord(MobType.EAGLE_BROWNHEAD_BASE_55, 2, 50),
                        new SpawnZoneMobRecord(MobType.EAGLE_BROWN_BASE_48, 4, 50)
                ), 50, false, false), 1)),


                new SpawnZoneRecord(new AbstractSpawnZoneMeta(206, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), 355, 230, 639)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.EAGLE_BLACK_BASE_50, 3, 50),
                        new SpawnZoneMobRecord(MobType.EAGLE_BROWNHEAD_BASE_55, 2, 50),
                        new SpawnZoneMobRecord(MobType.EAGLE_BROWN_BASE_48, 4, 50)
                ), 50, false, false), 1)),


                //Infernak warhounds
                new SpawnZoneRecord(new AbstractSpawnZoneMeta(207, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), 1000, 90, -630)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.INFERNAK_WARHOUND_BASE_80, 30, 100)
                ), 120, false, true), 1)),

                // Ashwale cows
                new SpawnZoneRecord(new AbstractSpawnZoneMeta(208, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), -92, 69, -132)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.COW_BASE_7, 24, 100),
                        new SpawnZoneMobRecord(MobType.CALF_BASE_5, 6, 100)
                ), 20, false, true), 1)),


                // Drakkar tanner
                new SpawnZoneRecord(new AbstractSpawnZoneMeta(209, SpawnZoneType.POINT, 20, 1,
                        new Location(Bukkit.getWorld("world"), 210, 68, -127)), toVersionedJson(new PointSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.DRAKKAR_TANNER, 1, 100)
                ), false, MarkerType.TANNER, "Tanner"), 1)),

                // Saharijin camel bazaar
                new SpawnZoneRecord(new AbstractSpawnZoneMeta(210, SpawnZoneType.POINT, 30, 20,
                        new Location(Bukkit.getWorld("world"), -1002, 73, -556)), toVersionedJson(new PointSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.STATIC_CAMEL, 1, 100)
                ), false, null, null), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(211, SpawnZoneType.POINT, 30, 20,
                        new Location(Bukkit.getWorld("world"), -1009, 73, -556)), toVersionedJson(new PointSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.STATIC_CAMEL, 1, 100)
                ), false, null, null), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(212, SpawnZoneType.POINT, 30, 20,
                        new Location(Bukkit.getWorld("world"), -1016, 73, -556)), toVersionedJson(new PointSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.STATIC_CAMEL, 1, 100)
                ), false, null, null), 1)),


                new SpawnZoneRecord(new AbstractSpawnZoneMeta(213, SpawnZoneType.POINT, 30, 20,
                        new Location(Bukkit.getWorld("world"), -1002, 73, -567)), toVersionedJson(new PointSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.STATIC_CAMEL, 1, 100)
                ), false, null, null), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(214, SpawnZoneType.POINT, 30, 20,
                        new Location(Bukkit.getWorld("world"), -1009, 73, -567)), toVersionedJson(new PointSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.STATIC_CAMEL, 1, 100)
                ), false, null, null), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(215, SpawnZoneType.POINT, 30, 20,
                        new Location(Bukkit.getWorld("world"), -1016, 73, -567)), toVersionedJson(new PointSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.STATIC_CAMEL, 1, 100)
                ), false, null, null), 1)),


                // The sick camels quest npcs
                new SpawnZoneRecord(new AbstractSpawnZoneMeta(216, SpawnZoneType.POINT, 30, 20,
                        new Location(Bukkit.getWorld("world"), -1008, 73, -561)), toVersionedJson(new PointSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.RAFIQ_CAMEL_TRADER, 1, 100)
                ), false, MarkerType.MOUNT_SHOP, "Camel Merchant"), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(217, SpawnZoneType.POINT, 30, 20,
                        new Location(Bukkit.getWorld("world"), 490, 80, 497)), toVersionedJson(new PointSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.SHAMIRA_SHAMAN, 1, 100)
                ), false, MarkerType.HERBLORE_SHOP, "Herblore Shop"), 1)),


                // Saharijin guards
                new SpawnZoneRecord(new AbstractSpawnZoneMeta(218, SpawnZoneType.AREA, 20, 1,
                        new Location(Bukkit.getWorld("world"), -1226, 70, -807)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.CITY_GUARD_WALKING, 4, 100),
                        new SpawnZoneMobRecord(MobType.CITY_CITIZEN_SAHARIJIN, 3, 100)
                ), 40, false, true), 1)),


                new SpawnZoneRecord(new AbstractSpawnZoneMeta(219, SpawnZoneType.AREA, 20, 1,
                        new Location(Bukkit.getWorld("world"), -1223, 70, -760)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.CITY_GUARD_WALKING, 4, 100),
                        new SpawnZoneMobRecord(MobType.CITY_CITIZEN_SAHARIJIN, 3, 100)
                ), 40, false, true), 1)),

                // Eldarwyn guards
                new SpawnZoneRecord(new AbstractSpawnZoneMeta(220, SpawnZoneType.AREA, 20, 1,
                        new Location(Bukkit.getWorld("world"), -845, 80, -346)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.CITY_GUARD_WALKING, 10, 100),
                        new SpawnZoneMobRecord(MobType.CITY_CITIZEN_MAN, 8, 100),
                        new SpawnZoneMobRecord(MobType.CITY_CITIZEN_WOMAN, 3, 100)
                ), 50, false, true), 1)),

                // Branleft guards
                new SpawnZoneRecord(new AbstractSpawnZoneMeta(221, SpawnZoneType.AREA, 20, 1,
                        new Location(Bukkit.getWorld("world"), -638, 68, -294)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.CITY_GUARD_WALKING, 10, 100),
                        new SpawnZoneMobRecord(MobType.CITY_CITIZEN_MAN, 3, 100),
                        new SpawnZoneMobRecord(MobType.CITY_CITIZEN_WOMAN, 3, 100)
                ), 50, false, true), 1)),

                // Murkwatch guards // TODO: citizens
                new SpawnZoneRecord(new AbstractSpawnZoneMeta(222, SpawnZoneType.POINT, 20, 1,
                        new Location(Bukkit.getWorld("world"), -1031, 69, 450)), toVersionedJson(new PointSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.CITY_GUARD_STATIC, 1, 100)
                ), false, null, null), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(223, SpawnZoneType.POINT, 20, 1,
                        new Location(Bukkit.getWorld("world"), -1007, 68, 435.5)), toVersionedJson(new PointSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.CITY_GUARD_STATIC, 1, 100)
                ), false, null, null), 1)),


                new SpawnZoneRecord(new AbstractSpawnZoneMeta(224, SpawnZoneType.POINT, 20, 1,
                        new Location(Bukkit.getWorld("world"), -962, 68, 438)), toVersionedJson(new PointSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.CITY_GUARD_STATIC, 1, 100)
                ), false, null, null), 1)),


                new SpawnZoneRecord(new AbstractSpawnZoneMeta(225, SpawnZoneType.POINT, 20, 1,
                        new Location(Bukkit.getWorld("world"), -1022, 68, 384)), toVersionedJson(new PointSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.CITY_GUARD_STATIC, 1, 100)
                ), false, null, null), 1)),


                new SpawnZoneRecord(new AbstractSpawnZoneMeta(226, SpawnZoneType.POINT, 20, 1,
                        new Location(Bukkit.getWorld("world"), -1041, 68, 384)), toVersionedJson(new PointSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.CITY_GUARD_STATIC, 1, 100)
                ), false, null, null), 1)),


                new SpawnZoneRecord(new AbstractSpawnZoneMeta(227, SpawnZoneType.POINT, 20, 1,
                        new Location(Bukkit.getWorld("world"), -1081, 68, 482)), toVersionedJson(new PointSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.CITY_GUARD_STATIC, 1, 100)
                ), false, null, null), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(228, SpawnZoneType.POINT, 20, 1,
                        new Location(Bukkit.getWorld("world"), -1085, 68, 423)), toVersionedJson(new PointSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.CITY_GUARD_STATIC, 1, 100)
                ), false, null, null), 1)),


                // Aetheris guards
                new SpawnZoneRecord(new AbstractSpawnZoneMeta(229, SpawnZoneType.AREA, 20, 1,
                        new Location(Bukkit.getWorld("world"), -157, 80, 791)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.CITY_GUARD_WALKING, 3, 100),
                        new SpawnZoneMobRecord(MobType.CITY_CITIZEN_MAN, 5, 100),
                        new SpawnZoneMobRecord(MobType.CITY_CITIZEN_WOMAN, 1, 100)
                ), 30, false, true), 1)),


                new SpawnZoneRecord(new AbstractSpawnZoneMeta(230, SpawnZoneType.AREA, 20, 1,
                        new Location(Bukkit.getWorld("world"), -100, 80, 774)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.CITY_GUARD_WALKING, 5, 100),
                        new SpawnZoneMobRecord(MobType.CITY_CITIZEN_MAN, 3, 100),
                        new SpawnZoneMobRecord(MobType.CITY_CITIZEN_WOMAN, 3, 100)
                ), 40, false, true), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(231, SpawnZoneType.POINT, 20, 1,
                        new Location(Bukkit.getWorld("world"), -19, 83, 777)), toVersionedJson(new PointSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.CITY_GUARD_STATIC, 1, 100)
                ), false, null, null), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(232, SpawnZoneType.POINT, 20, 1,
                        new Location(Bukkit.getWorld("world"), -19, 83, 788)), toVersionedJson(new PointSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.CITY_GUARD_STATIC, 1, 100)
                ), false, null, null), 1)),

                // Glaciem guards
                new SpawnZoneRecord(new AbstractSpawnZoneMeta(233, SpawnZoneType.AREA, 20, 1,
                        new Location(Bukkit.getWorld("world"), 953, 70, 503)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.CITY_GUARD_WALKING, 3, 100),
                        new SpawnZoneMobRecord(MobType.CITY_CITIZEN_MAN, 4, 100),
                        new SpawnZoneMobRecord(MobType.CITY_CITIZEN_WOMAN, 1, 100)
                ), 30, false, true), 1)),

                // Sakurmeer guards
                new SpawnZoneRecord(new AbstractSpawnZoneMeta(234, SpawnZoneType.POINT, 20, 1,
                        new Location(Bukkit.getWorld("world"), 762, 115, 192.5)), toVersionedJson(new PointSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.CITY_GUARD_STATIC, 1, 100)
                ), false, null, null), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(235, SpawnZoneType.POINT, 20, 1,
                        new Location(Bukkit.getWorld("world"), 762, 115, 188.5)), toVersionedJson(new PointSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.CITY_GUARD_STATIC, 1, 100)
                ), false, null, null), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(236, SpawnZoneType.AREA, 20, 1,
                        new Location(Bukkit.getWorld("world"), 844, 120, 157)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.CITY_GUARD_WALKING, 2, 100),
                        new SpawnZoneMobRecord(MobType.CITY_CITIZEN_MAN, 1, 100),
                        new SpawnZoneMobRecord(MobType.CITY_CITIZEN_WOMAN, 2, 100)
                ), 20, false, true), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(237, SpawnZoneType.AREA, 20, 1,
                        new Location(Bukkit.getWorld("world"), 854, 122, 233)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.CITY_GUARD_WALKING, 2, 100),
                        new SpawnZoneMobRecord(MobType.CITY_CITIZEN_WOMAN, 2, 100)
                ), 20, false, true), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(238, SpawnZoneType.AREA, 20, 1,
                        new Location(Bukkit.getWorld("world"), 931, 134, 191)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.CITY_GUARD_WALKING, 5, 100),
                        new SpawnZoneMobRecord(MobType.CITY_CITIZEN_MAN, 3, 100),
                        new SpawnZoneMobRecord(MobType.CITY_CITIZEN_WOMAN, 2, 100)
                ), 30, false, true), 1)),


                // Islenor guards
                new SpawnZoneRecord(new AbstractSpawnZoneMeta(239, SpawnZoneType.AREA, 20, 1,
                        new Location(Bukkit.getWorld("world"), 496, 67, -715)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.CITY_GUARD_WALKING, 5, 100),
                        new SpawnZoneMobRecord(MobType.CITY_CITIZEN_MAN, 3, 100),
                        new SpawnZoneMobRecord(MobType.CITY_CITIZEN_WOMAN, 2, 100)
                ), 40, false, true), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(240, SpawnZoneType.POINT, 20, 1,
                        new Location(Bukkit.getWorld("world"), 471, 66, -669)), toVersionedJson(new PointSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.CITY_GUARD_STATIC, 1, 100)
                ), false, null, null), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(241, SpawnZoneType.POINT, 20, 1,
                        new Location(Bukkit.getWorld("world"), 467.5, 66, -671.5)), toVersionedJson(new PointSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.CITY_GUARD_STATIC, 1, 100)
                ), false, null, null), 1)),

                // Valdrin guards
                new SpawnZoneRecord(new AbstractSpawnZoneMeta(242, SpawnZoneType.POINT, 20, 1,
                        new Location(Bukkit.getWorld("world"), -36, 123, -533)), toVersionedJson(new PointSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.CITY_GUARD_STATIC, 1, 100)
                ), false, null, null), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(243, SpawnZoneType.AREA, 20, 1,
                        new Location(Bukkit.getWorld("world"), -12, 116, -528)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.CITY_GUARD_WALKING, 3, 100),
                        new SpawnZoneMobRecord(MobType.CITY_CITIZEN_MAN, 3, 100),
                        new SpawnZoneMobRecord(MobType.CITY_CITIZEN_WOMAN, 2, 100)
                ), 20, false, true), 1)),

                // Zarali guards
                new SpawnZoneRecord(new AbstractSpawnZoneMeta(244, SpawnZoneType.AREA, 20, 1,
                        new Location(Bukkit.getWorld("world"), 476, 69, 469)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.CITY_GUARD_WALKING, 3, 100),
                        new SpawnZoneMobRecord(MobType.CITY_CITIZEN_JUNGLE, 5, 100)
                ), 20, false, true), 1)),

                // Ashwale guards
                new SpawnZoneRecord(new AbstractSpawnZoneMeta(245, SpawnZoneType.AREA, 20, 1,
                        new Location(Bukkit.getWorld("world"), -66, 67, -88)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.CITY_GUARD_WALKING, 3, 100),
                        new SpawnZoneMobRecord(MobType.CITY_CITIZEN_MAN, 1, 100),
                        new SpawnZoneMobRecord(MobType.CITY_CITIZEN_WOMAN, 1, 100)
                ), 20, false, true), 1)),

                // Oakshade guards
                new SpawnZoneRecord(new AbstractSpawnZoneMeta(246, SpawnZoneType.AREA, 20, 1,
                        new Location(Bukkit.getWorld("world"), 530, 70, 2)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.CITY_GUARD_WALKING, 4, 100),
                        new SpawnZoneMobRecord(MobType.CITY_CITIZEN_MAN, 3, 100),
                        new SpawnZoneMobRecord(MobType.CITY_CITIZEN_WOMAN, 2, 100)
                ), 40, false, true), 1)),


                // Drakkar citizens
                new SpawnZoneRecord(new AbstractSpawnZoneMeta(247, SpawnZoneType.AREA, 20, 1,
                        new Location(Bukkit.getWorld("world"), 195, 73, -84)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.CITY_CITIZEN_MAN, 10, 100),
                        new SpawnZoneMobRecord(MobType.CITY_CITIZEN_WOMAN, 8, 100)
                ), 80, false, true), 1)),

                // Saharijin citizens
                new SpawnZoneRecord(new AbstractSpawnZoneMeta(248, SpawnZoneType.AREA, 20, 1,
                        new Location(Bukkit.getWorld("world"), -999, 67, -591)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.CITY_CITIZEN_SAHARIJIN, 3, 100)
                ), 40, false, true), 1)),

                // Thronbark guards
                new SpawnZoneRecord(new AbstractSpawnZoneMeta(249, SpawnZoneType.AREA, 20, 1,
                        new Location(Bukkit.getWorld("world"), 1092, 68, -343)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.CITY_GUARD_WALKING, 4, 100),
                        new SpawnZoneMobRecord(MobType.CITY_CITIZEN_MAN, 3, 100),
                        new SpawnZoneMobRecord(MobType.CITY_CITIZEN_WOMAN, 3, 100)
                ), 40, false, true), 1)),

                // Infernak citizens
                new SpawnZoneRecord(new AbstractSpawnZoneMeta(250, SpawnZoneType.AREA, 20, 1,
                        new Location(Bukkit.getWorld("world"), 1030, 80, -666)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.CITY_CITIZEN_INFERNAK, 3, 100)
                ), 40, false, true), 1)),

                // Brineport citizens
                new SpawnZoneRecord(new AbstractSpawnZoneMeta(251, SpawnZoneType.POINT, 20, 1,
                        new Location(Bukkit.getWorld("world"), -489, 65.5, 180)), toVersionedJson(new PointSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.CITY_CITIZEN_MAN, 1, 100),
                        new SpawnZoneMobRecord(MobType.CITY_CITIZEN_WOMAN, 1, 100)
                ), false, null, null), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(252, SpawnZoneType.POINT, 20, 1,
                        new Location(Bukkit.getWorld("world"), -478, 65, 215)), toVersionedJson(new PointSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.CITY_CITIZEN_MAN, 1, 100),
                        new SpawnZoneMobRecord(MobType.CITY_CITIZEN_WOMAN, 1, 100)
                ), false, null, null), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(253, SpawnZoneType.POINT, 20, 1,
                        new Location(Bukkit.getWorld("world"), -452, 65.5, 274)), toVersionedJson(new PointSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.CITY_CITIZEN_MAN, 1, 100),
                        new SpawnZoneMobRecord(MobType.CITY_CITIZEN_WOMAN, 1, 100)
                ), false, null, null), 1)),

                // Murkwatch citizens
                new SpawnZoneRecord(new AbstractSpawnZoneMeta(254, SpawnZoneType.POINT, 20, 1,
                        new Location(Bukkit.getWorld("world"), -1011, 68, 447)), toVersionedJson(new PointSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.CITY_CITIZEN_MAN, 2, 100),
                        new SpawnZoneMobRecord(MobType.CITY_CITIZEN_WOMAN, 1, 100)
                ), false, null, null), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(255, SpawnZoneType.POINT, 20, 1,
                        new Location(Bukkit.getWorld("world"), -969, 68, 409)), toVersionedJson(new PointSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.CITY_CITIZEN_MAN, 1, 100),
                        new SpawnZoneMobRecord(MobType.CITY_CITIZEN_WOMAN, 1, 100)
                ), false, null, null), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(256, SpawnZoneType.POINT, 20, 1,
                        new Location(Bukkit.getWorld("world"), -1027, 68, 384)), toVersionedJson(new PointSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.CITY_CITIZEN_MAN, 1, 100),
                        new SpawnZoneMobRecord(MobType.CITY_CITIZEN_WOMAN, 1, 100)
                ), false, null, null), 1)),

                // Nearby Murkwatch village citizens
                new SpawnZoneRecord(new AbstractSpawnZoneMeta(257, SpawnZoneType.AREA, 20, 1,
                        new Location(Bukkit.getWorld("world"), -739, 71, 388)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.CITY_CITIZEN_MAN, 1, 100),
                        new SpawnZoneMobRecord(MobType.CITY_CITIZEN_WOMAN, 2, 100)
                ), 30, false, true), 1)),


                // Lava dungeon entrance
                new SpawnZoneRecord(new AbstractSpawnZoneMeta(258, SpawnZoneType.AREA, 50, 30,
                        new Location(Bukkit.getWorld("world"), 566, 45, -266)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.LAVA_DUNGEON_TURRET_BASE_20, 3, 100)
                ), 10, false, true), 1)),


                new SpawnZoneRecord(new AbstractSpawnZoneMeta(259, SpawnZoneType.AREA, 50, 30,
                        new Location(Bukkit.getWorld("world"), 568, 45, -217)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.LAVA_DUNGEON_TURRET_BASE_20, 3, 100)
                ), 10, false, true), 1)),


                new SpawnZoneRecord(new AbstractSpawnZoneMeta(260, SpawnZoneType.AREA, 50, 30,
                        new Location(Bukkit.getWorld("world"), 588, 45, -239)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.LAVA_DUNGEON_TURRET_BASE_20, 3, 100)
                ), 10, false, true), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(261, SpawnZoneType.AREA, 50, 30,
                        new Location(Bukkit.getWorld("world"), 605, 45, -216)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.LAVA_DUNGEON_TURRET_BASE_20, 3, 100)
                ), 10, false, true), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(262, SpawnZoneType.AREA, 50, 30,
                        new Location(Bukkit.getWorld("world"), 613, 45, -251)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.LAVA_DUNGEON_TURRET_BASE_20, 3, 100)
                ), 10, false, true), 1)),

                // guards
                new SpawnZoneRecord(new AbstractSpawnZoneMeta(263, SpawnZoneType.POINT, 60, 100,
                        new Location(Bukkit.getWorld("world"), 568, 45, -217)), toVersionedJson(new PointSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.LAVA_DUNGEON_GUARD_1_BASE_35, 1, 100)
                ), false, null, null), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(264, SpawnZoneType.POINT, 60, 100,
                        new Location(Bukkit.getWorld("world"), 587, 45, -238)), toVersionedJson(new PointSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.LAVA_DUNGEON_GUARD_2_BASE_40, 1, 100)
                ), false, null, null), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(265, SpawnZoneType.POINT, 60, 100,
                        new Location(Bukkit.getWorld("world"), 612, 45, -251)), toVersionedJson(new PointSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.LAVA_DUNGEON_GUARD_3_BASE_45, 1, 100)
                ), false, null, null), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(266, SpawnZoneType.POINT, 60, 100,
                        new Location(Bukkit.getWorld("world"), 605, 45, -216)), toVersionedJson(new PointSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.LAVA_DUNGEON_GUARD_4_BASE_50, 1, 100)
                ), false, null, null), 1)),

                // Lava dungeon boss
                new SpawnZoneRecord(new AbstractSpawnZoneMeta(267, SpawnZoneType.POINT, 60, 100,
                        new Location(Bukkit.getWorld("world"), 741, 15, -227)), toVersionedJson(new PointSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.LAVA_DUNGEON_BOSS_BASE_55, 1, 100)
                ), false, null, null), 1)),

                // Rats
                new SpawnZoneRecord(new AbstractSpawnZoneMeta(268, SpawnZoneType.AREA, 60, 30,
                        new Location(Bukkit.getWorld("world"), 191, 73, -79)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.RAT_GRAY_BASE_1, 5, 100),
                        new SpawnZoneMobRecord(MobType.RAT_BROWN_BASE_1, 5, 100),
                        new SpawnZoneMobRecord(MobType.RAT_ALBINO_BASE_1, 1, 100)
                ), 100, false, true), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(269, SpawnZoneType.AREA, 60, 30,
                        new Location(Bukkit.getWorld("world"), 954, 70, 500)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.RAT_GRAY_BASE_1, 3, 100),
                        new SpawnZoneMobRecord(MobType.RAT_BROWN_BASE_1, 3, 100),
                        new SpawnZoneMobRecord(MobType.RAT_ALBINO_BASE_1, 1, 100)
                ), 60, false, true), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(270, SpawnZoneType.AREA, 60, 30,
                        new Location(Bukkit.getWorld("world"), 863, 135, 189)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.RAT_GRAY_BASE_1, 5, 100),
                        new SpawnZoneMobRecord(MobType.RAT_BROWN_BASE_1, 5, 100),
                        new SpawnZoneMobRecord(MobType.RAT_ALBINO_BASE_1, 1, 100)
                ), 100, false, true), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(271, SpawnZoneType.AREA, 60, 30,
                        new Location(Bukkit.getWorld("world"), 477, 74, 470)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.RAT_GRAY_BASE_1, 2, 100),
                        new SpawnZoneMobRecord(MobType.RAT_BROWN_BASE_1, 2, 100),
                        new SpawnZoneMobRecord(MobType.RAT_ALBINO_BASE_1, 1, 100)
                ), 60, false, true), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(272, SpawnZoneType.AREA, 60, 30,
                        new Location(Bukkit.getWorld("world"), -217, 78, 794)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.RAT_GRAY_BASE_1, 2, 100),
                        new SpawnZoneMobRecord(MobType.RAT_BROWN_BASE_1, 2, 100),
                        new SpawnZoneMobRecord(MobType.RAT_ALBINO_BASE_1, 1, 100)
                ), 30, false, true), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(273, SpawnZoneType.AREA, 60, 30,
                        new Location(Bukkit.getWorld("world"), -1005, 68, 432)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.RAT_GRAY_BASE_1, 5, 100),
                        new SpawnZoneMobRecord(MobType.RAT_BROWN_BASE_1, 5, 100),
                        new SpawnZoneMobRecord(MobType.RAT_ALBINO_BASE_1, 1, 100)
                ), 100, false, true), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(274, SpawnZoneType.AREA, 60, 30,
                        new Location(Bukkit.getWorld("world"), -846, 79, -346)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.RAT_GRAY_BASE_1, 5, 100),
                        new SpawnZoneMobRecord(MobType.RAT_BROWN_BASE_1, 5, 100),
                        new SpawnZoneMobRecord(MobType.RAT_ALBINO_BASE_1, 1, 100)
                ), 100, false, true), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(275, SpawnZoneType.AREA, 60, 30,
                        new Location(Bukkit.getWorld("world"), -654, 68, -301)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.RAT_GRAY_BASE_1, 2, 100),
                        new SpawnZoneMobRecord(MobType.RAT_BROWN_BASE_1, 2, 100),
                        new SpawnZoneMobRecord(MobType.RAT_ALBINO_BASE_1, 1, 100)
                ), 50, false, true), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(276, SpawnZoneType.AREA, 60, 30,
                        new Location(Bukkit.getWorld("world"), 1092, 66, -343)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.RAT_GRAY_BASE_1, 2, 100),
                        new SpawnZoneMobRecord(MobType.RAT_BROWN_BASE_1, 2, 100),
                        new SpawnZoneMobRecord(MobType.RAT_ALBINO_BASE_1, 1, 100)
                ), 50, false, true), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(277, SpawnZoneType.AREA, 60, 30,
                        new Location(Bukkit.getWorld("world"), 498, 66, -715)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.RAT_GRAY_BASE_1, 2, 100),
                        new SpawnZoneMobRecord(MobType.RAT_BROWN_BASE_1, 2, 100),
                        new SpawnZoneMobRecord(MobType.RAT_ALBINO_BASE_1, 1, 100)
                ), 50, false, true), 1)),


                new SpawnZoneRecord(new AbstractSpawnZoneMeta(278, SpawnZoneType.AREA, 60, 30,
                        new Location(Bukkit.getWorld("world"), -9, 116, -533)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.RAT_GRAY_BASE_1, 2, 100),
                        new SpawnZoneMobRecord(MobType.RAT_BROWN_BASE_1, 2, 100),
                        new SpawnZoneMobRecord(MobType.RAT_ALBINO_BASE_1, 1, 100)
                ), 50, false, true), 1)),


                new SpawnZoneRecord(new AbstractSpawnZoneMeta(279, SpawnZoneType.AREA, 60, 30,
                        new Location(Bukkit.getWorld("world"), -78, 71, -1084)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.RAT_GRAY_BASE_1, 2, 100),
                        new SpawnZoneMobRecord(MobType.RAT_BROWN_BASE_1, 2, 100),
                        new SpawnZoneMobRecord(MobType.RAT_ALBINO_BASE_1, 1, 100)
                ), 50, false, true), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(280, SpawnZoneType.AREA, 60, 30,
                        new Location(Bukkit.getWorld("world"), -219, 81, -1085)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.RAT_ALBINO_BASE_1, 3, 100)
                ), 5, false, true), 1)),


                // Penguins
                new SpawnZoneRecord(new AbstractSpawnZoneMeta(281, SpawnZoneType.AREA, 30, 30,
                        new Location(Bukkit.getWorld("world"), 1106, 75, 653)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.PENGUIN_BASE_40, 20, 100),
                        new SpawnZoneMobRecord(MobType.PENGUIN_BABY_BASE_30, 5, 100)
                ), 30, false, false), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(282, SpawnZoneType.AREA, 30, 30,
                        new Location(Bukkit.getWorld("world"), 1133, 85, 571)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.PENGUIN_BASE_40, 20, 100),
                        new SpawnZoneMobRecord(MobType.PENGUIN_BABY_BASE_30, 5, 100)
                ), 30, false, false), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(283, SpawnZoneType.AREA, 30, 30,
                        new Location(Bukkit.getWorld("world"), 1224, 63, 393)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.PENGUIN_BASE_40, 10, 100),
                        new SpawnZoneMobRecord(MobType.PENGUIN_BABY_BASE_30, 3, 100)
                ), 50, false, false), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(284, SpawnZoneType.AREA, 30, 30,
                        new Location(Bukkit.getWorld("world"), 1317, 63, 494)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.PENGUIN_BASE_40, 10, 100),
                        new SpawnZoneMobRecord(MobType.PENGUIN_BABY_BASE_30, 3, 100)
                ), 50, false, false), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(285, SpawnZoneType.AREA, 30, 30,
                        new Location(Bukkit.getWorld("world"), 1338, 63, 569)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.PENGUIN_BASE_40, 10, 100),
                        new SpawnZoneMobRecord(MobType.PENGUIN_BABY_BASE_30, 3, 100)
                ), 50, false, false), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(286, SpawnZoneType.AREA, 30, 30,
                        new Location(Bukkit.getWorld("world"), 1307, 63, 672)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.PENGUIN_BASE_40, 10, 100),
                        new SpawnZoneMobRecord(MobType.PENGUIN_BABY_BASE_30, 3, 100)
                ), 50, false, false), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(287, SpawnZoneType.AREA, 30, 30,
                        new Location(Bukkit.getWorld("world"), 1284, 63, 751)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.PENGUIN_BASE_40, 10, 100),
                        new SpawnZoneMobRecord(MobType.PENGUIN_BABY_BASE_30, 3, 100)
                ), 50, false, false), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(288, SpawnZoneType.AREA, 30, 30,
                        new Location(Bukkit.getWorld("world"), 1176, 63, 804)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.PENGUIN_BASE_40, 10, 100),
                        new SpawnZoneMobRecord(MobType.PENGUIN_BABY_BASE_30, 3, 100)
                ), 50, false, false), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(289, SpawnZoneType.AREA, 30, 30,
                        new Location(Bukkit.getWorld("world"), 1108, 63, 844)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.PENGUIN_BASE_40, 10, 100),
                        new SpawnZoneMobRecord(MobType.PENGUIN_BABY_BASE_30, 3, 100)
                ), 50, false, false), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(290, SpawnZoneType.AREA, 30, 30,
                        new Location(Bukkit.getWorld("world"), 1010, 63, 871)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.PENGUIN_BASE_40, 10, 100),
                        new SpawnZoneMobRecord(MobType.PENGUIN_BABY_BASE_30, 3, 100)
                ), 50, false, false), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(291, SpawnZoneType.AREA, 30, 30,
                        new Location(Bukkit.getWorld("world"), 1224, 63, 393)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.PENGUIN_BASE_40, 10, 100),
                        new SpawnZoneMobRecord(MobType.PENGUIN_BABY_BASE_30, 3, 100)
                ), 50, false, false), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(292, SpawnZoneType.AREA, 30, 30,
                        new Location(Bukkit.getWorld("world"), 882, 63, 847)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.PENGUIN_BASE_40, 10, 100),
                        new SpawnZoneMobRecord(MobType.PENGUIN_BABY_BASE_30, 3, 100)
                ), 50, false, false), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(293, SpawnZoneType.AREA, 30, 30,
                        new Location(Bukkit.getWorld("world"), 833, 63, 778)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.PENGUIN_BASE_40, 10, 100),
                        new SpawnZoneMobRecord(MobType.PENGUIN_BABY_BASE_30, 3, 100)
                ), 50, false, false), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(294, SpawnZoneType.AREA, 30, 30,
                        new Location(Bukkit.getWorld("world"), 820, 63, 680)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.PENGUIN_BASE_40, 10, 100),
                        new SpawnZoneMobRecord(MobType.PENGUIN_BABY_BASE_30, 3, 100)
                ), 50, false, false), 1)),

                // Bugs
                new SpawnZoneRecord(new AbstractSpawnZoneMeta(295, SpawnZoneType.AREA, 30, 30,
                        new Location(Bukkit.getWorld("world"), -1116, 65, -707)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.CENTIPEDE_BASE_20, 5, 100)
                ), 20, false, false), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(296, SpawnZoneType.AREA, 160, 50,
                        new Location(Bukkit.getWorld("world"), -1019, 63, -716)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.SCORPION_BASE_30, 1, 30),
                        new SpawnZoneMobRecord(MobType.SCORPION_BASE_30, 1, 30),
                        new SpawnZoneMobRecord(MobType.SCORPION_BASE_30, 1, 30)
                ), 20, false, false), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(297, SpawnZoneType.AREA, 30, 30,
                        new Location(Bukkit.getWorld("world"), -927, 90, -696)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.CENTIPEDE_BASE_20, 3, 100)
                ), 20, false, false), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(298, SpawnZoneType.AREA, 30, 30,
                        new Location(Bukkit.getWorld("world"), -1076, 78, -726)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.CENTIPEDE_BASE_20, 3, 100)
                ), 20, false, false), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(299, SpawnZoneType.AREA, 30, 30,
                        new Location(Bukkit.getWorld("world"), -1145, 65, -857)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.CENTIPEDE_BASE_20, 2, 100)
                ), 10, false, false), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(300, SpawnZoneType.AREA, 30, 30,
                        new Location(Bukkit.getWorld("world"), -1191, 85, -593)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.CENTIPEDE_BASE_20, 3, 100)
                ), 20, false, false), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(301, SpawnZoneType.AREA, 30, 30,
                        new Location(Bukkit.getWorld("world"), -982, 55, -765)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.CENTIPEDE_BASE_20, 3, 100)
                ), 10, false, false), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(302, SpawnZoneType.AREA, 30, 30,
                        new Location(Bukkit.getWorld("world"), -472, 82, -501)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.CRICKET_GREEN_BASE_7, 3, 100),
                        new SpawnZoneMobRecord(MobType.CRICKET_BROWN_BASE_5, 3, 100)
                ), 30, false, false), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(303, SpawnZoneType.AREA, 30, 30,
                        new Location(Bukkit.getWorld("world"), 156, 84, -467)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.CRICKET_GREEN_BASE_7, 3, 100),
                        new SpawnZoneMobRecord(MobType.CRICKET_BROWN_BASE_5, 3, 100),
                        new SpawnZoneMobRecord(MobType.MANTIS_BASE_25, 2, 20)
                ), 30, false, false), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(304, SpawnZoneType.AREA, 30, 30,
                        new Location(Bukkit.getWorld("world"), 121, 63, -439)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.COCKROACH_BASE_8, 2, 100),
                        new SpawnZoneMobRecord(MobType.ANT_BLACK_BASE_5, 8, 100),
                        new SpawnZoneMobRecord(MobType.MANTIS_BASE_25, 2, 100)
                ), 10, false, false), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(305, SpawnZoneType.AREA, 30, 30,
                        new Location(Bukkit.getWorld("world"), 96, 59, -427)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.CRICKET_GREEN_BASE_7, 2, 100),
                        new SpawnZoneMobRecord(MobType.MANTIS_BASE_25, 1, 100)
                ), 5, false, false), 1)),


                new SpawnZoneRecord(new AbstractSpawnZoneMeta(306, SpawnZoneType.AREA, 30, 30,
                        new Location(Bukkit.getWorld("world"), 72, 42, -425)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.COCKROACH_BASE_8, 2, 100),
                        new SpawnZoneMobRecord(MobType.MANTIS_BASE_25, 1, 100)
                ), 5, false, false), 1)),


                new SpawnZoneRecord(new AbstractSpawnZoneMeta(307, SpawnZoneType.AREA, 120, 30,
                        new Location(Bukkit.getWorld("world"), 88, 29, -449)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.ANT_BLACK_BASE_5, 8, 100),
                        new SpawnZoneMobRecord(MobType.SCORPION_BASE_30, 1, 50)
                ), 5, false, false), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(308, SpawnZoneType.AREA, 120, 30,
                        new Location(Bukkit.getWorld("world"), 105, 34, -435)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.COCKROACH_BASE_8, 3, 100),
                        new SpawnZoneMobRecord(MobType.SCORPION_BASE_30, 1, 100)
                ), 5, false, false), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(309, SpawnZoneType.POINT, 60, 100,
                        new Location(Bukkit.getWorld("world"), 195.5, 73, -52.5)), toVersionedJson(new PointSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.DRAKKAR_MAYOR, 1, 100)
                ), false, null, null), 1)),

                // Drakkar bank vault rats
                new SpawnZoneRecord(new AbstractSpawnZoneMeta(310, SpawnZoneType.AREA, 10, 30,
                        new Location(Bukkit.getWorld("world"), 276, 50, -77)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.RAT_WELCOME_TO_DRAKKAR_BASE_1, 20, 100)
                ), 10, false, false), 1)),


                new SpawnZoneRecord(new AbstractSpawnZoneMeta(311, SpawnZoneType.AREA, 30, 30,
                        new Location(Bukkit.getWorld("world"), 672, 76, 243)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.PANDA_BASE_35, 10, 100)
                ), 50, false, true), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(312, SpawnZoneType.AREA, 30, 30,
                        new Location(Bukkit.getWorld("world"), 735, 98, 111)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.PANDA_BASE_35, 10, 100)
                ), 50, false, true), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(313, SpawnZoneType.AREA, 30, 30,
                        new Location(Bukkit.getWorld("world"), 842, 120, 80)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.PANDA_BASE_35, 10, 100)
                ), 30, false, true), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(314, SpawnZoneType.AREA, 30, 30,
                        new Location(Bukkit.getWorld("world"), 912, 107, 45)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.PANDA_BASE_35, 10, 100)
                ), 30, false, true), 1)),


                // Jungle bugs
                new SpawnZoneRecord(new AbstractSpawnZoneMeta(315, SpawnZoneType.AREA, 30, 30,
                        new Location(Bukkit.getWorld("world"), 471, 110, 544)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.CRICKET_GREEN_BASE_7, 30, 100),
                        new SpawnZoneMobRecord(MobType.ANT_BLACK_BASE_5, 30, 100),
                        new SpawnZoneMobRecord(MobType.MANTIS_BASE_25, 20, 100),
                        new SpawnZoneMobRecord(MobType.SCORPION_BASE_30, 10, 100)
                ), 100, false, true), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(316, SpawnZoneType.AREA, 30, 30,
                        new Location(Bukkit.getWorld("world"), 331, 110, 461)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.CRICKET_GREEN_BASE_7, 30, 100),
                        new SpawnZoneMobRecord(MobType.ANT_BLACK_BASE_5, 30, 100),
                        new SpawnZoneMobRecord(MobType.MANTIS_BASE_25, 20, 100),
                        new SpawnZoneMobRecord(MobType.SCORPION_BASE_30, 10, 100)
                ), 100, false, true), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(317, SpawnZoneType.AREA, 30, 30,
                        new Location(Bukkit.getWorld("world"), 143, 110, 503)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.CRICKET_GREEN_BASE_7, 30, 100),
                        new SpawnZoneMobRecord(MobType.ANT_BLACK_BASE_5, 30, 100),
                        new SpawnZoneMobRecord(MobType.MANTIS_BASE_25, 20, 100),
                        new SpawnZoneMobRecord(MobType.SCORPION_BASE_30, 10, 100)
                ), 100, false, true), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(318, SpawnZoneType.AREA, 30, 30,
                        new Location(Bukkit.getWorld("world"), 516, 110, 408)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.CRICKET_GREEN_BASE_7, 8, 100),
                        new SpawnZoneMobRecord(MobType.ANT_BLACK_BASE_5, 8, 100),
                        new SpawnZoneMobRecord(MobType.MANTIS_BASE_25, 4, 100),
                        new SpawnZoneMobRecord(MobType.SCORPION_BASE_30, 3, 100)
                ), 50, false, true), 1)),


                new SpawnZoneRecord(new AbstractSpawnZoneMeta(319, SpawnZoneType.AREA, 30, 30,
                        new Location(Bukkit.getWorld("world"), 577, 110, 456)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.CRICKET_GREEN_BASE_7, 8, 100),
                        new SpawnZoneMobRecord(MobType.ANT_BLACK_BASE_5, 8, 100),
                        new SpawnZoneMobRecord(MobType.MANTIS_BASE_25, 4, 100),
                        new SpawnZoneMobRecord(MobType.SCORPION_BASE_30, 3, 100)
                ), 50, false, true), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(320, SpawnZoneType.AREA, 30, 30,
                        new Location(Bukkit.getWorld("world"), 409, 110, 700)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.CRICKET_GREEN_BASE_7, 8, 100),
                        new SpawnZoneMobRecord(MobType.ANT_BLACK_BASE_5, 8, 100),
                        new SpawnZoneMobRecord(MobType.MANTIS_BASE_25, 4, 100),
                        new SpawnZoneMobRecord(MobType.SCORPION_BASE_30, 3, 100)
                ), 50, false, true), 1)),


                // Near jungle bandits camp
                new SpawnZoneRecord(new AbstractSpawnZoneMeta(321, SpawnZoneType.AREA, 120, 15,
                        new Location(Bukkit.getWorld("world"), -66, 80, 457)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.BANDIT_BASE_20_1, 2, 100),
                        new SpawnZoneMobRecord(MobType.BANDIT_BASE_20_2, 2, 100),
                        new SpawnZoneMobRecord(MobType.BANDIT_BASE_20_3, 2, 100),
                        new SpawnZoneMobRecord(MobType.BANDIT_BASE_20_4, 2, 100)
                ), 30, false, false), 1)),

                // Heartroot bandits (for now)
                new SpawnZoneRecord(new AbstractSpawnZoneMeta(322, SpawnZoneType.AREA, 120, 15,
                        new Location(Bukkit.getWorld("world"), 247, 104, 508)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.BANDIT_BASE_40_1, 1, 100),
                        new SpawnZoneMobRecord(MobType.BANDIT_BASE_40_2, 1, 100),
                        new SpawnZoneMobRecord(MobType.BANDIT_BASE_40_3, 1, 100),
                        new SpawnZoneMobRecord(MobType.BANDIT_BASE_40_4, 1, 100)
                ), 15, false, false), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(323, SpawnZoneType.POINT, 30, 20,
                        new Location(Bukkit.getWorld("world"), -746, 71, 372)), toVersionedJson(new PointSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.SWAMP_ISLAND_HERBLORE_SHOP, 1, 100)
                ), false, MarkerType.HERBLORE_SHOP, "Herblore Shop"), 1)),

                // Drakkar daily rewards
                new SpawnZoneRecord(new AbstractSpawnZoneMeta(324, SpawnZoneType.POINT, 30, 100,
                        new Location(Bukkit.getWorld("world"), 230.5, 73, -76)), toVersionedJson(new PointSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.COLLECTOR_OF_DAYS, 1, 100)
                ), false, MarkerType.DAILY_REWARD, "Collector of Days"), 1)),


                // Saharijin daily rewards
                new SpawnZoneRecord(new AbstractSpawnZoneMeta(325, SpawnZoneType.POINT, 30, 100,
                        new Location(Bukkit.getWorld("world"), -1236, 67, -743)), toVersionedJson(new PointSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.COLLECTOR_OF_DAYS, 1, 100)
                ), false, MarkerType.DAILY_REWARD, "Collector of Days"), 1)),

                // Murkwatch daily rewards
                new SpawnZoneRecord(new AbstractSpawnZoneMeta(326, SpawnZoneType.POINT, 30, 100,
                        new Location(Bukkit.getWorld("world"), -1039, 68, 435)), toVersionedJson(new PointSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.COLLECTOR_OF_DAYS, 1, 100)
                ), false, MarkerType.DAILY_REWARD, "Collector of Days"), 1)),

                // Sakurmeer daily rewards
                new SpawnZoneRecord(new AbstractSpawnZoneMeta(327, SpawnZoneType.POINT, 30, 100,
                        new Location(Bukkit.getWorld("world"), 831.5, 120, 129)), toVersionedJson(new PointSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.COLLECTOR_OF_DAYS, 1, 100)
                ), false, MarkerType.DAILY_REWARD, "Collector of Days"), 1)),

                // Drakkar smeltery shop
                new SpawnZoneRecord(new AbstractSpawnZoneMeta(328, SpawnZoneType.POINT, 10, 100,
                        new Location(Bukkit.getWorld("world"), 3239, 4, 3216)), toVersionedJson(new PointSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.DRAKKAR_SMELTERY_WORKER, 1, 100)
                ), false, null, null), 1))


                // last 328


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