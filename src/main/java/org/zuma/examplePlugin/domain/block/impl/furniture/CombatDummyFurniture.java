package org.zuma.examplePlugin.domain.block.impl.furniture;

import com.google.gson.Gson;
import eu.decentsoftware.holograms.api.DHAPI;
import eu.decentsoftware.holograms.api.holograms.Hologram;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Lightable;
import org.bukkit.block.data.Rotatable;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.constants.Icon;
import org.zuma.examplePlugin.core.constants.SoundType;
import org.zuma.examplePlugin.core.meta.VersionedJson;
import org.zuma.examplePlugin.domain.block.base.AbstractInteractableBlock;
import org.zuma.examplePlugin.domain.block.meta.AbstractInteractableBlockMeta;
import org.zuma.examplePlugin.domain.item.base.AbstractItemType;
import org.zuma.examplePlugin.domain.mob.constants.MobType;
import org.zuma.examplePlugin.domain.player.RunePlayer;
import org.zuma.examplePlugin.domain.spawnzone.SpawnZoneMobRecord;
import org.zuma.examplePlugin.domain.spawnzone.SpawnZoneRecord;
import org.zuma.examplePlugin.domain.spawnzone.SpawnZoneType;
import org.zuma.examplePlugin.domain.spawnzone.base.AbstractSpawnZone;
import org.zuma.examplePlugin.domain.spawnzone.impl.PointSpawnZone;
import org.zuma.examplePlugin.domain.spawnzone.meta.AbstractSpawnZoneMeta;
import org.zuma.examplePlugin.domain.spawnzone.meta.v1.PointSpawnZoneMeta;

import java.util.List;
import java.util.UUID;

public class CombatDummyFurniture extends AbstractInteractableBlock {
    private int spawnedZoneId = -1;

    public CombatDummyFurniture(AbstractInteractableBlockMeta meta) {
        super(meta, null, null);
    }

    @Override
    public void spawn(Material material) {
        super.spawn(Material.AIR);
        spawnWithFace(getFace());
    }

    private void spawnWithFace(BlockFace face) {
        if (spawnedZoneId != -1) {
            RuneQuestPlugin.getSpawnZoneHandler().unregisterSpawnZone(spawnedZoneId);
        }

        this.spawnedZoneId = spawnedZoneId == -1 ? (10000 + (int) (Math.random() * 1000000)) : spawnedZoneId;

        MobType mobType;

        switch (face) {
            case NORTH -> mobType = MobType.COMBAT_DUMMY_FACE_NORTH;
            case SOUTH -> mobType = MobType.COMBAT_DUMMY_FACE_SOUTH;
            case EAST -> mobType = MobType.COMBAT_DUMMY_FACE_EAST;
            case WEST -> mobType = MobType.COMBAT_DUMMY_FACE_WEST;
            default -> mobType = MobType.COMBAT_DUMMY_FACE_SOUTH;
        }

        VersionedJson json = new VersionedJson(new Gson().toJson(new PointSpawnZoneMeta(List.of(
                new SpawnZoneMobRecord(mobType, 1, 100)), false, null, null)), 1);

        AbstractSpawnZoneMeta meta = new AbstractSpawnZoneMeta(spawnedZoneId, SpawnZoneType.POINT, 30, 20, getLocation().clone().add(0.5, 0, 0.5));
        AbstractSpawnZone zone = RuneQuestPlugin.getSpawnZoneHandler().getFactory().createZone(meta, json);
        RuneQuestPlugin.getSpawnZoneHandler().registerSpawnZone(zone);
        zone.spawnWave();
    }

    @Override
    public void onDisable() {
        super.onDisable();
        RuneQuestPlugin.getSpawnZoneHandler().unregisterSpawnZone(spawnedZoneId);
    }

    public void rotate(BlockFace face) {
        spawnWithFace(face);
    }

}
