package org.zuma.examplePlugin.domain.block.persistence;

import org.bukkit.Location;
import org.zuma.examplePlugin.core.meta.VersionedJson;
import org.zuma.examplePlugin.domain.block.constants.BlockType;
import org.zuma.examplePlugin.domain.block.meta.AbstractInteractableBlockMeta;

import javax.annotation.Nullable;

public record BlockEntity(AbstractInteractableBlockMeta meta, VersionedJson json) {

    public int getId() {
        return this.meta.id();
    }

    public BlockType getType() {
        return meta.type();
    }

    public Location getLocation() {
        return meta.location();
    }

    @Nullable
    public Location getHologramLocation() {
        return meta.hologramLocation();
    }

    public VersionedJson getJson() {
        return this.json;
    }


}
