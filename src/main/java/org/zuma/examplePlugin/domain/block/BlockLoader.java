package org.zuma.examplePlugin.domain.block;

import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.logger.constants.LogLevel;
import org.zuma.examplePlugin.domain.block.base.AbstractInteractableBlock;
import org.zuma.examplePlugin.domain.block.base.ResourceNode;
import org.zuma.examplePlugin.domain.block.meta.ResourceNodeType;
import org.zuma.examplePlugin.domain.block.persistence.InteractableBlockRepository;
import org.zuma.examplePlugin.domain.block.persistence.ResourceNodeTypeRepository;
import org.zuma.examplePlugin.domain.item.interfaces.Serializable;

import java.util.HashMap;

public class BlockLoader {
    private final BlockManager manager;
    private final InteractableBlockRepository interactableBlockRepository;
    private final BlockFactory factory;

    private final HashMap<Integer, ResourceNodeType> resourceNodeTypes = new HashMap<>();
    private final ResourceNodeTypeRepository resourceNodeTypeRepository;

    public BlockLoader(BlockManager manager, InteractableBlockRepository interactableBlockRepository, BlockFactory factory, ResourceNodeTypeRepository resourceNodeTypeRepository) {
        this.manager = manager;
        this.factory = factory;
        this.interactableBlockRepository = interactableBlockRepository;
        this.resourceNodeTypeRepository = resourceNodeTypeRepository;
    }

    public void fetchAllResourceNodeTypes() {
        resourceNodeTypeRepository.getAll().forEach(type -> resourceNodeTypes.put(type.getId(), type));
    }

    public void loadInteractableBlocks() {
        this.interactableBlockRepository.getAll().forEach(meta -> {
            AbstractInteractableBlock block = factory.createBlock(meta);

            if (block instanceof Serializable blockWithAdditionalMeta) {
                try {
                    blockWithAdditionalMeta.deserializeAndApplyMeta();
                } catch (Exception e) {
                    RuneQuestPlugin.getRuneLogger().log(LogLevel.WARN, "Could not apply jsonMeta to the " + block.getType() + " block at " + block.getLocation() + "! " + e.getMessage());
                    return;
                }
            }

            if (block instanceof ResourceNode) {
                ((ResourceNode) block).initializeBlockTypeFromManager(this);
            }

            this.manager.registerBlock(block);
        });
    }


    public HashMap<Integer, ResourceNodeType> getResourceNodeTypes() {
        return resourceNodeTypes;
    }
}
