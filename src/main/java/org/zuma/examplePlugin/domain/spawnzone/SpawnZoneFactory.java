package org.zuma.examplePlugin.domain.spawnzone;

import com.google.gson.Gson;
import org.bukkit.Location;
import org.zuma.examplePlugin.core.exception.UnsupportedJsonVersionException;
import org.zuma.examplePlugin.core.meta.VersionedJson;
import org.zuma.examplePlugin.domain.item.base.AbstractItemType;
import org.zuma.examplePlugin.domain.item.impl.CoinsItem;
import org.zuma.examplePlugin.domain.item.impl.EmptyContainer;
import org.zuma.examplePlugin.domain.item.meta.AbstractItemMeta;
import org.zuma.examplePlugin.domain.item.meta.v1.EmptyContainerMeta;
import org.zuma.examplePlugin.domain.spawnzone.base.AbstractSpawnZone;
import org.zuma.examplePlugin.domain.spawnzone.impl.AreaSpawnZone;
import org.zuma.examplePlugin.domain.spawnzone.impl.ChunkSpawnZone;
import org.zuma.examplePlugin.domain.spawnzone.impl.PointSpawnZone;
import org.zuma.examplePlugin.domain.spawnzone.meta.AbstractSpawnZoneMeta;
import org.zuma.examplePlugin.domain.spawnzone.meta.v1.AreaSpawnZoneMeta;
import org.zuma.examplePlugin.domain.spawnzone.meta.v1.ChunkSpawnZoneMeta;
import org.zuma.examplePlugin.domain.spawnzone.meta.v1.PointSpawnZoneMeta;

import javax.annotation.Nullable;
import java.util.List;

public class SpawnZoneFactory {
    public AbstractSpawnZone createZone(AbstractSpawnZoneMeta meta, @Nullable VersionedJson json) {
        return switch (meta.type()) {
            case POINT -> createPointZone(meta, json);
            case CHUNK -> createChunkZone(meta, json);
            case AREA -> createAreaZone(meta, json);
            default -> throw new IllegalArgumentException("Unknown zone type: " + meta.type());
        };
    }

    private PointSpawnZone createPointZone(AbstractSpawnZoneMeta meta, @Nullable VersionedJson json) {
        if (json == null) {
            throw new UnsupportedOperationException("Cannot create point spawn zone id " + meta.id() + " required json is null!");
        }

        switch (json.version()) {
            case 1 -> {
                PointSpawnZoneMeta deserialized = new Gson().fromJson(json.meta(), PointSpawnZoneMeta.class);
                PointSpawnZone zone = new PointSpawnZone(meta, deserialized.spawnAtHighestBlock(), deserialized.marker(), deserialized.markerLabel());
                zone.setMobRecords(deserialized.mobRecords());

                return zone;
            }
            default -> throw new UnsupportedJsonVersionException(meta.type().name(), json.version());
        }
    }

    private AreaSpawnZone createAreaZone(AbstractSpawnZoneMeta meta, @Nullable VersionedJson json) {
        if (json == null) {
            throw new UnsupportedOperationException("Cannot create area spawn zone id " + meta.id() + " required json is null!");
        }

        switch (json.version()) {
            case 1 -> {
                AreaSpawnZoneMeta deserialized = new Gson().fromJson(json.meta(), AreaSpawnZoneMeta.class);
                AreaSpawnZone zone = new AreaSpawnZone(meta, deserialized.radius(), deserialized.spawnAtHighestBlock());
                zone.setMobRecords(deserialized.mobRecords());

                return zone;
            }
            default -> throw new UnsupportedJsonVersionException(meta.type().name(), json.version());
        }
    }

    private ChunkSpawnZone createChunkZone(AbstractSpawnZoneMeta meta, @Nullable VersionedJson json) {
        if (json == null) {
            throw new UnsupportedOperationException("Cannot create chunk spawn zone id " + meta.id() + " required json is null!");
        }

        switch (json.version()) {
            case 1 -> {
                ChunkSpawnZoneMeta deserialized = new Gson().fromJson(json.meta(), ChunkSpawnZoneMeta.class);
                ChunkSpawnZone zone = new ChunkSpawnZone(meta, deserialized.spawnAtHighestBlock());
                zone.setMobRecords(deserialized.mobRecords());

                return zone;
            }
            default -> throw new UnsupportedJsonVersionException(meta.type().name(), json.version());
        }
    }


}
