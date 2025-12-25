package org.zuma.examplePlugin.domain.item.state;

import com.google.gson.Gson;
import org.zuma.examplePlugin.core.exception.UnsupportedJsonVersionException;
import org.zuma.examplePlugin.core.meta.VersionedJson;
import org.zuma.examplePlugin.domain.item.state.base.AbstractState;
import org.zuma.examplePlugin.domain.item.state.meta.AbstractStateMeta;
import org.zuma.examplePlugin.domain.item.state.meta.v1.*;
import org.zuma.examplePlugin.domain.item.state.states.*;

import javax.annotation.Nullable;
import java.util.Map;

public class StateFactory {
    private final Gson gson = new Gson();

    @Nullable
    public AbstractState createState(Map.Entry<Long, AbstractStateMeta> input) {
        AbstractStateMeta abstractStateMeta = input.getValue();
        AbstractState newState = null;

        switch (abstractStateMeta.type()) {
            case MULTI_CONSUMABLE -> newState = createMultiConsumableState(abstractStateMeta, abstractStateMeta.json());
            case GEAR_GEM_SOCKETS -> newState = createGearGemSocketsState(abstractStateMeta, abstractStateMeta.json());
            case FAIRY_STATE -> newState = createFairyState(abstractStateMeta, abstractStateMeta.json());
            case MOUNT_STATE -> newState = createMountState(abstractStateMeta, abstractStateMeta.json());
            case STORAGE_ITEM_UUID_STATE ->
                    newState = createStorageItemState(abstractStateMeta, abstractStateMeta.json());
            default -> {
            }
        }

        newState.setCreatedAt(input.getKey());
        return newState;
    }

    private StorageItemState createStorageItemState(AbstractStateMeta meta, @Nullable VersionedJson json) {
        if (json == null) {
            throw new UnsupportedOperationException("Cannot create " + meta.type() + " state required json is null!");
        }

        switch (json.version()) {
            case 1 -> {
                StorageItemStateMeta deserialized = gson.fromJson(json.meta(), StorageItemStateMeta.class);
                return new StorageItemState(deserialized);
            }
            default -> throw new UnsupportedJsonVersionException(meta.type().name(), json.version());
        }

    }

    private MountState createMountState(AbstractStateMeta meta, @Nullable VersionedJson json) {
        if (json == null) {
            throw new UnsupportedOperationException("Cannot create " + meta.type() + " state required json is null!");
        }

        switch (json.version()) {
            case 1 -> {
                MountStateMeta deserialized = gson.fromJson(json.meta(), MountStateMeta.class);
                return new MountState(deserialized);
            }
            default -> throw new UnsupportedJsonVersionException(meta.type().name(), json.version());
        }

    }


    private FairyState createFairyState(AbstractStateMeta meta, @Nullable VersionedJson json) {
        if (json == null) {
            throw new UnsupportedOperationException("Cannot create " + meta.type() + " state required json is null!");
        }

        switch (json.version()) {
            case 1 -> {
                FairyStateMeta deserialized = gson.fromJson(json.meta(), FairyStateMeta.class);
                return new FairyState(deserialized);
            }
            default -> throw new UnsupportedJsonVersionException(meta.type().name(), json.version());
        }

    }


    private MultiConsumableState createMultiConsumableState(AbstractStateMeta meta, @Nullable VersionedJson json) {
        if (json == null) {
            throw new UnsupportedOperationException("Cannot create " + meta.type() + " state required json is null!");
        }

        switch (json.version()) {
            case 1 -> {
                MultiConsumableStateMeta deserialized = gson.fromJson(json.meta(), MultiConsumableStateMeta.class);
                return new MultiConsumableState(deserialized);
            }
            default -> throw new UnsupportedJsonVersionException(meta.type().name(), json.version());
        }

    }

    private GearGemSocketsState createGearGemSocketsState(AbstractStateMeta meta, @Nullable VersionedJson json) {
        if (json == null) {
            throw new UnsupportedOperationException("Cannot create " + meta.type() + " state required json is null!");
        }

        switch (json.version()) {
            case 1 -> {
                GearGemSocketsStateMeta deserialized = gson.fromJson(json.meta(), GearGemSocketsStateMeta.class);
                return new GearGemSocketsState(deserialized);
            }
            default -> throw new UnsupportedJsonVersionException(meta.type().name(), json.version());
        }

    }

}
