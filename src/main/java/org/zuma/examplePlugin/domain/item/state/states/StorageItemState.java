package org.zuma.examplePlugin.domain.item.state.states;

import com.google.gson.Gson;
import org.zuma.examplePlugin.core.meta.VersionedJson;
import org.zuma.examplePlugin.domain.item.state.StateType;
import org.zuma.examplePlugin.domain.item.state.base.AbstractState;
import org.zuma.examplePlugin.domain.item.state.meta.AbstractStateMeta;
import org.zuma.examplePlugin.domain.item.state.meta.v1.StorageItemStateMeta;

import java.util.UUID;

public class StorageItemState extends AbstractState {
    private final UUID uuid;

    public StorageItemState(StorageItemStateMeta meta) {
        super(StateType.STORAGE_ITEM_UUID_STATE);
        this.uuid = meta.uuid();
    }

    public UUID getUUID() {
        return uuid;
    }

    @Override
    public AbstractStateMeta serialize(Gson gson) {
        return new AbstractStateMeta(this.getStateType(), new VersionedJson(gson.toJson(new StorageItemStateMeta(this.uuid)), 1));
    }

    @Override
    public AbstractState clone() {
        return new StorageItemState(new StorageItemStateMeta(this.uuid));
    }
}
