package org.zuma.examplePlugin.core.interfaces;

import org.zuma.examplePlugin.domain.item.ItemRecord;

import javax.annotation.Nullable;

public interface DespawnableBlock {
    void onDespawn();

    boolean shouldBeDespawned();

    default int despawnSeconds() {
        return 600;
    }

    @Nullable
    default ItemRecord dropOnDespawn() {
        return null;
    }
}