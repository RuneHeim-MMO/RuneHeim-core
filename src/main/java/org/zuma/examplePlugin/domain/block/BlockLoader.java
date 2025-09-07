package org.zuma.examplePlugin.domain.block;

import org.bukkit.Bukkit;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.logger.constants.LogLevel;
import org.zuma.examplePlugin.core.meta.VersionedJson;
import org.zuma.examplePlugin.domain.block.base.AbstractInteractableBlock;
import org.zuma.examplePlugin.domain.block.meta.AbstractInteractableBlockMeta;
import org.zuma.examplePlugin.domain.block.meta.ResourceNodeType;
import org.zuma.examplePlugin.domain.block.persistence.BlockEntity;
import org.zuma.examplePlugin.domain.block.persistence.InteractableBlockRepository;
import org.zuma.examplePlugin.domain.block.persistence.ResourceNodeTypeRepository;
import org.zuma.examplePlugin.events.block.InteractableBlockCreateStartEvent;
import org.zuma.examplePlugin.events.block.InteractableBlockRepositoryUpdateEvent;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BlockLoader {
    private final BlockManager manager;
    private final InteractableBlockRepository interactableBlockRepository;
    private final BlockFactory factory;

    private final HashMap<Integer, ResourceNodeType> resourceNodeTypes = new HashMap<>();
    private final ResourceNodeTypeRepository resourceNodeTypeRepository;
    private List<Long> lastInsertedIds = new ArrayList<>();

    public BlockLoader(BlockManager manager, InteractableBlockRepository interactableBlockRepository, BlockFactory factory, ResourceNodeTypeRepository resourceNodeTypeRepository) {
        this.manager = manager;
        this.factory = factory;
        this.interactableBlockRepository = interactableBlockRepository;
        this.resourceNodeTypeRepository = resourceNodeTypeRepository;
    }

    public void insertNewBlock(BlockEntity entity) {
        InteractableBlockCreateStartEvent event = new InteractableBlockCreateStartEvent(entity);
        Bukkit.getServer().getPluginManager().callEvent(event);

        if (event.isCancelled()) {
            return;
        }

        Bukkit.getScheduler().runTaskAsynchronously(RuneQuestPlugin.getInstance(), () -> {
            List<Long> insertedIds = interactableBlockRepository.insertBlocks(List.of(entity));
            lastInsertedIds = new ArrayList<>(insertedIds);

            insertedIds.forEach(id -> {
                BlockEntity retrievedEntity = interactableBlockRepository.getById(id);

                if (retrievedEntity == null) {
                    RuneQuestPlugin.getRuneLogger().log(LogLevel.WARN, "Couldn't retrieve inserted block entity data! Id: " + id);
                    return;
                }

                Bukkit.getScheduler().runTask(RuneQuestPlugin.getInstance(), () -> {
                    Bukkit.getServer().getPluginManager().callEvent(new InteractableBlockRepositoryUpdateEvent(insertedIds));
                    AbstractInteractableBlock block = registerBlock(manager, entity.meta(), entity.json());
                    RuneQuestPlugin.getRuneLogger().log(LogLevel.INFO, "Spawned " + entity.meta().type() + " at " + entity.getLocation() + ".");
                    RuneQuestPlugin.reloadMarkers();
                });
            });
        });
    }

    public void undoLastInsertion() {
        Bukkit.getScheduler().runTaskAsynchronously(RuneQuestPlugin.getInstance(), () -> {
            interactableBlockRepository.deleteByIds(lastInsertedIds);

            Bukkit.getScheduler().runTask(RuneQuestPlugin.getInstance(), () -> {
                Bukkit.getServer().getPluginManager().callEvent(new InteractableBlockRepositoryUpdateEvent(lastInsertedIds));
                manager.onDisable();
                lastInsertedIds = new ArrayList<>();
                Bukkit.getScheduler().runTask(RuneQuestPlugin.getInstance(), this::loadInteractableBlocks);
                RuneQuestPlugin.reloadMarkers();
            });

        });
    }

    public void fetchAllResourceNodeTypes() {
        resourceNodeTypeRepository.getAll().forEach(type -> resourceNodeTypes.put(type.getId(), type));
        RuneQuestPlugin.getRuneLogger().log(LogLevel.INFO, "Loaded " + resourceNodeTypes.size() + " resource node types.");
    }

    public void loadInteractableBlocks() {
        this.interactableBlockRepository.getAll().forEach(entity -> registerBlock(manager, entity.meta(), entity.json()));
        RuneQuestPlugin.getRuneLogger().log(LogLevel.INFO, "Spawned " + this.manager.getAllBlocks().size() + " interactable blocks.");
    }

    @Nullable
    private AbstractInteractableBlock registerBlock(BlockManager manager, AbstractInteractableBlockMeta meta, VersionedJson json) {
        try {
            AbstractInteractableBlock block = factory.createBlock(manager, meta, json, this);
            this.manager.registerBlock(block);

            return block;
        } catch (Exception e) {
            RuneQuestPlugin.getRuneLogger().log(LogLevel.CRITICAL, "Failed to load interactable block " + meta.type().name() + " : ID: " + meta.id() + "! Error: ", e);
        }

        return null;
    }


    public HashMap<Integer, ResourceNodeType> getResourceNodeTypes() {
        return resourceNodeTypes;
    }
}
