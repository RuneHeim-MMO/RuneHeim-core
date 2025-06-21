package org.zuma.examplePlugin.domain.block.impl;

import org.zuma.examplePlugin.domain.block.base.AbstractInteractableBlock;
import org.zuma.examplePlugin.domain.block.meta.AbstractInteractableBlockMeta;

public class GenericInteractableBlock extends AbstractInteractableBlock {
    public GenericInteractableBlock(AbstractInteractableBlockMeta meta) {
        super(meta, null, null);
    }
}
