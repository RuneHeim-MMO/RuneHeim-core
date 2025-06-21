package org.zuma.examplePlugin.domain.block;

import com.google.gson.Gson;
import org.zuma.examplePlugin.core.exception.UnsupportedJsonVersionException;
import org.zuma.examplePlugin.core.meta.VersionedJson;
import org.zuma.examplePlugin.domain.block.base.AbstractInteractableBlock;
import org.zuma.examplePlugin.domain.block.impl.InventoryOpenerBlock;
import org.zuma.examplePlugin.domain.block.impl.ResourceNode;
import org.zuma.examplePlugin.domain.block.meta.AbstractInteractableBlockMeta;
import org.zuma.examplePlugin.domain.block.meta.v1.InventoryOpenerMeta;
import org.zuma.examplePlugin.domain.block.meta.v1.ResourceNodeMeta;

import javax.annotation.Nullable;

public class BlockFactory {
    public AbstractInteractableBlock createBlock(BlockManager manager, AbstractInteractableBlockMeta meta, VersionedJson json, BlockLoader loader) {
        return switch (meta.type()) {
            case ResourceNode -> createResourceNode(manager, meta, json, loader);
            case InventoryOpener -> createInventoryOpener(meta, json);
            default -> throw new IllegalArgumentException("Unknown interactable block type: " + meta.type());
        };
    }


    private ResourceNode createResourceNode(BlockManager manager, AbstractInteractableBlockMeta meta, @Nullable VersionedJson json, BlockLoader loader) {
        if (json == null) {
            throw new UnsupportedOperationException("Cannot create resource node block " + meta.type() + " required json is null!");
        }

        switch (json.version()) {
            case 1 -> {
                ResourceNodeMeta deserialized = new Gson().fromJson(json.meta(), ResourceNodeMeta.class);
                ResourceNode block = new ResourceNode(manager, meta);
                block.setSchematics(deserialized.schematicName(), deserialized.depletedSchematicName());
                block.initializeBlock(deserialized.resourceMetaId(), loader);
                return block;
            }
            default -> throw new UnsupportedJsonVersionException(meta.type().name(), json.version());
        }
    }

    private InventoryOpenerBlock createInventoryOpener(AbstractInteractableBlockMeta meta, @Nullable VersionedJson json) {
        if (json == null) {
            throw new UnsupportedOperationException("Cannot create resource node block " + meta.type() + " required json is null!");
        }

        switch (json.version()) {
            case 1 -> {
                InventoryOpenerMeta deserialized = new Gson().fromJson(json.meta(), InventoryOpenerMeta.class);
                InventoryOpenerBlock block = new InventoryOpenerBlock(meta);
                block.initializeBlock(deserialized.inventoryId(), deserialized.requirements(), deserialized.label(), deserialized.icon(), deserialized.blockType(), deserialized.marker(), deserialized.markerLabel());
                return block;
            }
            default -> throw new UnsupportedJsonVersionException(meta.type().name(), json.version());
        }
    }
}

