package org.zuma.examplePlugin.core.interfaces;

import org.zuma.examplePlugin.domain.item.ItemRecord;

import javax.annotation.Nullable;

public interface DespawnableBlock {
    boolean shouldBeDespawned();

    @Nullable
    default ItemRecord dropOnDespawn() {
        return null;
    }
}