package org.zuma.examplePlugin.domain.block;

import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.logger.constants.LogLevel;
import org.zuma.examplePlugin.core.meta.JsonMeta;
import org.zuma.examplePlugin.domain.block.base.AbstractInteractableBlock;
import org.zuma.examplePlugin.domain.block.meta.AbstractInteractableBlockMeta;
import org.zuma.examplePlugin.domain.block.meta.ResourceNodeType;
import org.zuma.examplePlugin.domain.block.persistence.InteractableBlockRepository;
import org.zuma.examplePlugin.domain.block.persistence.ResourceNodeTypeRepository;

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
        RuneQuestPlugin.getRuneLogger().log(LogLevel.INFO, "Loaded " + resourceNodeTypes.size() + " resource node types.");
    }

    public void loadInteractableBlocks() {
        this.interactableBlockRepository.getAll().forEach(entity -> registerBlock(entity.meta(), entity.json()));
        RuneQuestPlugin.getRuneLogger().log(LogLevel.INFO, "Spawned " + this.manager.getAllBlocks().size() + " interactable blocks.");
    }

    private void registerBlock(AbstractInteractableBlockMeta meta, JsonMeta json) {
        AbstractInteractableBlock block = factory.createBlock(meta, json, this);
        this.manager.registerBlock(block);
    }


    public HashMap<Integer, ResourceNodeType> getResourceNodeTypes() {
        return resourceNodeTypes;
    }
}
