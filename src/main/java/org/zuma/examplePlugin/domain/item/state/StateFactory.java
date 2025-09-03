package org.zuma.examplePlugin.domain.item.state;

import com.google.gson.Gson;
import org.zuma.examplePlugin.core.exception.UnsupportedJsonVersionException;
import org.zuma.examplePlugin.core.meta.VersionedJson;
import org.zuma.examplePlugin.domain.item.state.base.AbstractState;
import org.zuma.examplePlugin.domain.item.state.meta.AbstractStateMeta;
import org.zuma.examplePlugin.domain.item.state.meta.v1.FairyStateMeta;
import org.zuma.examplePlugin.domain.item.state.meta.v1.GearGemSocketsStateMeta;
import org.zuma.examplePlugin.domain.item.state.meta.v1.MountStateMeta;
import org.zuma.examplePlugin.domain.item.state.meta.v1.MultiConsumableStateMeta;
import org.zuma.examplePlugin.domain.item.state.states.FairyState;
import org.zuma.examplePlugin.domain.item.state.states.GearGemSocketsState;
import org.zuma.examplePlugin.domain.item.state.states.MountState;
import org.zuma.examplePlugin.domain.item.state.states.MultiConsumableState;

import javax.annotation.Nullable;

public class StateFactory {
    private final Gson gson = new Gson();

    @Nullable
    public AbstractState createState(AbstractStateMeta abstractStateMeta) {
        return switch (abstractStateMeta.type()) {
            case MULTI_CONSUMABLE -> createMultiConsumableState(abstractStateMeta, abstractStateMeta.json());
            case GEAR_GEM_SOCKETS -> createGearGemSocketsState(abstractStateMeta, abstractStateMeta.json());
            case FAIRY_STATE -> createFairyState(abstractStateMeta, abstractStateMeta.json());
            case MOUNT_STATE -> createMountState(abstractStateMeta, abstractStateMeta.json());
            default -> null;
        };
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
