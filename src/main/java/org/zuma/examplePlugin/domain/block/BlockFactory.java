package org.zuma.examplePlugin.domain.block;

import org.zuma.examplePlugin.domain.block.base.AbstractInteractableBlock;
import org.zuma.examplePlugin.domain.block.base.GenericInteractableBlock;
import org.zuma.examplePlugin.domain.block.base.InventoryOpenerBlock;
import org.zuma.examplePlugin.domain.block.base.ResourceNode;
import org.zuma.examplePlugin.domain.block.meta.AbstractInteractableBlockMeta;

public class BlockFactory {
    public AbstractInteractableBlock createBlock(AbstractInteractableBlockMeta meta) {
        return switch (meta.type()) {
            case ResourceNode -> new ResourceNode(meta);
            case InventoryOpener -> new InventoryOpenerBlock(meta);
            default -> new GenericInteractableBlock(meta);
        };
    }
}

