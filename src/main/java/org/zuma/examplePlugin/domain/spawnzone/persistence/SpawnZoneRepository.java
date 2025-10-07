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
                        new SpawnZoneMobRecord(MobType.BANK_TELLER_1, 1, 100)
                ), false, MarkerType.BANK_TELLER, "Bank"), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(12, SpawnZoneType.POINT, 30, 1,
                        new Location(Bukkit.getWorld("world"), 257.5, 73, -63.5)), toVersionedJson(new PointSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.BANK_TELLER_2, 1, 100)
                ), false, MarkerType.BANK_TELLER, "Bank"), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(13, SpawnZoneType.POINT, 30, 1,
                        new Location(Bukkit.getWorld("world"), 257.5, 73, -68.5)), toVersionedJson(new PointSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.BANK_TELLER_3, 1, 100)
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
                        new SpawnZoneMobRecord(MobType.CHICKEN_BASE_2, 20, 100)
                ), 10, false, true), 1)),

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
                        // todo
                ), 80, false, true), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(57, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), 1088, 90, -825)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        // todo
                ), 80, false, true), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(58, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), 1035, 120, -928)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        // todo
                ), 80, false, true), 1)),


                new SpawnZoneRecord(new AbstractSpawnZoneMeta(59, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), 1035, 120, -928)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        // todo
                ), 80, false, true), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(60, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), 934, 120, -937)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        // todo
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
                        new SpawnZoneMobRecord(MobType.OCELOT_BASE_48, 2, 100)
                ), 100, false, true), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(77, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), 346, 120, 400)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.OCELOT_BASE_48, 1, 100)
                ), 50, false, true), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(78, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), 604, 120, 409)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.OCELOT_BASE_44, 1, 100),
                        new SpawnZoneMobRecord(MobType.OCELOT_BASE_45, 1, 100),
                        new SpawnZoneMobRecord(MobType.OCELOT_BASE_48, 2, 100)
                ), 50, false, true), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(79, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), 562, 120, 560)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.OCELOT_BASE_44, 1, 100),
                        new SpawnZoneMobRecord(MobType.OCELOT_BASE_45, 1, 100),
                        new SpawnZoneMobRecord(MobType.OCELOT_BASE_48, 2, 100)
                ), 80, false, true), 1)),


                new SpawnZoneRecord(new AbstractSpawnZoneMeta(80, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), 340, 220, 637)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.OCELOT_BASE_44, 1, 100),
                        new SpawnZoneMobRecord(MobType.OCELOT_BASE_45, 1, 100),
                        new SpawnZoneMobRecord(MobType.OCELOT_BASE_48, 2, 100)
                ), 120, false, true), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(81, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), 454, 150, 620)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.OCELOT_BASE_44, 1, 100)
                ), 60, false, true), 1)),


                new SpawnZoneRecord(new AbstractSpawnZoneMeta(82, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), 140, 220, 709)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.OCELOT_BASE_44, 1, 100),
                        new SpawnZoneMobRecord(MobType.OCELOT_BASE_45, 1, 100),
                        new SpawnZoneMobRecord(MobType.OCELOT_BASE_48, 2, 100)
                ), 120, false, true), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(83, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), -13, 180, 561)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.OCELOT_BASE_48, 2, 100)
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
                        new SpawnZoneMobRecord(MobType.FOX_BASE_22, 3, 100)
                ), 120, false, true), 1)),


                new SpawnZoneRecord(new AbstractSpawnZoneMeta(87, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), 1190, 120, 636)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.TUNDRA_WOLF_BASE_45, 3, 100),
                        new SpawnZoneMobRecord(MobType.TUNDRA_WOLF_BASE_48, 4, 100),
                        new SpawnZoneMobRecord(MobType.TUNDRA_WOLF_BASE_47, 3, 100),
                        new SpawnZoneMobRecord(MobType.DEER_BASE_34, 5, 100),
                        new SpawnZoneMobRecord(MobType.DEER_FEMALE_BASE_29, 4, 100),
                        new SpawnZoneMobRecord(MobType.FOX_BASE_22, 3, 100)
                ), 120, false, true), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(88, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), 1017, 150, 712)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.TUNDRA_WOLF_BASE_45, 3, 100),
                        new SpawnZoneMobRecord(MobType.TUNDRA_WOLF_BASE_48, 6, 100),
                        new SpawnZoneMobRecord(MobType.TUNDRA_WOLF_BASE_47, 3, 100),
                        new SpawnZoneMobRecord(MobType.DEER_BASE_34, 5, 100),
                        new SpawnZoneMobRecord(MobType.DEER_FEMALE_BASE_29, 4, 100),
                        new SpawnZoneMobRecord(MobType.FOX_BASE_22, 3, 100)
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
                        new SpawnZoneMobRecord(MobType.DEER_BABY_BASE_15, 3, 100)
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
                        new SpawnZoneMobRecord(MobType.ARMADILLO_BASE_55, 4, 100)
                ), 150, false, true), 1)),


                new SpawnZoneRecord(new AbstractSpawnZoneMeta(93, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), -1169, 180, -624)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.ARMADILLO_BASE_55, 4, 100)
                ), 70, false, true), 1)),


                new SpawnZoneRecord(new AbstractSpawnZoneMeta(94, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), -1075, 180, -792)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        // todo
                ), 70, false, true), 1)),


                new SpawnZoneRecord(new AbstractSpawnZoneMeta(95, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), -960, 120, -490)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.ARMADILLO_BASE_55, 4, 100)
                ), 50, false, true), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(96, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), -893, 120, -689)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.ARMADILLO_BASE_55, 4, 100)
                ), 70, false, true), 1)),


                new SpawnZoneRecord(new AbstractSpawnZoneMeta(97, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), -893, 120, -781)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.ARMADILLO_BASE_55, 4, 100)
                ), 70, false, true), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(98, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), -1160, 120, -867)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.ARMADILLO_BASE_55, 4, 100)
                ), 50, false, true), 1)),


                new SpawnZoneRecord(new AbstractSpawnZoneMeta(99, SpawnZoneType.AREA, 30, 15,
                        new Location(Bukkit.getWorld("world"), -914, 120, -573)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.ARMADILLO_BASE_55, 4, 100)
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
                        new Location(Bukkit.getWorld("world"), -415, 80, 180)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
                        new SpawnZoneMobRecord(MobType.ARMADILLO_NON_AGGRO_BASE_15, 20, 100)
                ), 80, false, false), 1)),

                new SpawnZoneRecord(new AbstractSpawnZoneMeta(106, SpawnZoneType.AREA, 15, 15,
                        new Location(Bukkit.getWorld("world"), -328, 80, 290)), toVersionedJson(new AreaSpawnZoneMeta(List.of(
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
            ), 70, false, false), 1))

                // last 194


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