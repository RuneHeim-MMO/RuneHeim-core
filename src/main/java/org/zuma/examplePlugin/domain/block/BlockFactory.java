package org.zuma.examplePlugin.domain.block;

import com.google.gson.Gson;
import org.bukkit.Material;
import org.zuma.examplePlugin.core.exception.UnsupportedJsonVersionException;
import org.zuma.examplePlugin.core.meta.VersionedJson;
import org.zuma.examplePlugin.domain.block.base.AbstractInteractableBlock;
import org.zuma.examplePlugin.domain.block.impl.*;
import org.zuma.examplePlugin.domain.block.impl.door.DoorBlock;
import org.zuma.examplePlugin.domain.block.impl.furniture.CombatDummyFurniture;
import org.zuma.examplePlugin.domain.block.impl.furniture.LightableBlock;
import org.zuma.examplePlugin.domain.block.impl.trigger.ItemCollectorTrigger;
import org.zuma.examplePlugin.domain.block.impl.door.TeleportDoor;
import org.zuma.examplePlugin.domain.block.meta.AbstractInteractableBlockMeta;
import org.zuma.examplePlugin.domain.block.meta.v1.*;

import javax.annotation.Nullable;

public class BlockFactory {
    public AbstractInteractableBlock createBlock(BlockManager manager, AbstractInteractableBlockMeta meta, VersionedJson json, BlockLoader loader) {
        return switch (meta.type()) {
            case ResourceNode -> createResourceNode(manager, meta, json, loader);
            case InventoryOpener -> createInventoryOpener(meta, json);
            case InventoryOpenerModelled -> createInventoryOpenerEntity(meta, json);
            case Altar -> createAltar(meta, json);
            case Door -> createDoor(meta, json);
            case ItemTrigger -> createItemTrigger(meta, json);
            case TeleportDoor -> createTeleportDoor(meta, json);
            case InvisibleMarker -> createInvisibleMarker(meta, json);
            case CommandBlock -> createCommandBlock(meta, json);
            case LightableBlock -> createLightableBlock(meta, json);
            case CombatDummyFurniture -> createCombatDummyFurniture(meta, json);
            default -> throw new IllegalArgumentException("Unknown interactable block type: " + meta.type());
        };
    }

    private CombatDummyFurniture createCombatDummyFurniture(AbstractInteractableBlockMeta meta, @Nullable VersionedJson json) {
        CombatDummyFurniture block = new CombatDummyFurniture(meta);
        block.spawn(Material.AIR);

        return block;
    }

    private LightableBlock createLightableBlock(AbstractInteractableBlockMeta meta, @Nullable VersionedJson json) {
        if (json == null) {
            throw new UnsupportedOperationException("Cannot create lightable " + meta.type() + " required json is null!");
        }

        switch (json.version()) {
            case 1 -> {

                LightableBlockMeta deserialized = new Gson().fromJson(json.meta(), LightableBlockMeta.class);
                LightableBlock block = new LightableBlock(meta);
                block.spawn(deserialized.material());

                return block;
            }
            default -> throw new UnsupportedJsonVersionException(meta.type().name(), json.version());
        }
    }

    private CommandBlock createCommandBlock(AbstractInteractableBlockMeta meta, @Nullable VersionedJson json) {
        if (json == null) {
            throw new UnsupportedOperationException("Cannot create command block " + meta.type() + " required json is null!");
        }

        switch (json.version()) {
            case 1 -> {

                CommandBlockMeta deserialized = new Gson().fromJson(json.meta(), CommandBlockMeta.class);
                CommandBlock block = new CommandBlock(meta);
                block.initializeBlock(deserialized.hologramContent(), deserialized.commands());

                return block;
            }
            default -> throw new UnsupportedJsonVersionException(meta.type().name(), json.version());
        }
    }


    private InvisibleMarker createInvisibleMarker(AbstractInteractableBlockMeta meta, @Nullable VersionedJson json) {
        if (json == null) {
            throw new UnsupportedOperationException("Cannot create invisible marker block " + meta.type() + " required json is null!");
        }

        switch (json.version()) {
            case 1 -> {

                InvisibleMarkerMeta deserialized = new Gson().fromJson(json.meta(), InvisibleMarkerMeta.class);
                InvisibleMarker block = new InvisibleMarker(meta, deserialized.markerType(), deserialized.markerLabel());
                block.initializeBlock(deserialized.hologramContent());

                return block;
            }
            default -> throw new UnsupportedJsonVersionException(meta.type().name(), json.version());
        }
    }


    private TeleportDoor createTeleportDoor(AbstractInteractableBlockMeta meta, @Nullable VersionedJson json) {
        if (json == null) {
            throw new UnsupportedOperationException("Cannot create teleport door " + meta.type() + " required json is null!");
        }

        switch (json.version()) {
            case 1 -> {

                TeleportDoorMeta deserialized = new Gson().fromJson(json.meta(), TeleportDoorMeta.class);
                TeleportDoor block = new TeleportDoor(meta, deserialized.hinge(), deserialized.id(), deserialized.label(),
                        deserialized.world(), deserialized.x(), deserialized.y(), deserialized.z(), deserialized.yaw(),
                        deserialized.questLockedMeta());

                block.initializeBlock();
                return block;
            }
            default -> throw new UnsupportedJsonVersionException(meta.type().name(), json.version());
        }
    }


    private ItemCollectorTrigger createItemTrigger(AbstractInteractableBlockMeta meta, @Nullable VersionedJson json) {
        if (json == null) {
            throw new UnsupportedOperationException("Cannot create item collector trigger " + meta.type() + " required json is null!");
        }

        switch (json.version()) {
            case 1 -> {

                ItemCollectorTriggerMeta deserialized = new Gson().fromJson(json.meta(), ItemCollectorTriggerMeta.class);
                ItemCollectorTrigger block = new ItemCollectorTrigger(meta, deserialized.groupId(), deserialized.triggeredEntityId(), deserialized.triggeredDurationSeconds(), deserialized.requiredItems());
                block.initializeBlock();
                return block;
            }
            default -> throw new UnsupportedJsonVersionException(meta.type().name(), json.version());
        }
    }


    private DoorBlock createDoor(AbstractInteractableBlockMeta meta, @Nullable VersionedJson json) {
        if (json == null) {
            throw new UnsupportedOperationException("Cannot create door block " + meta.type() + " required json is null!");
        }

        switch (json.version()) {
            case 1 -> {
                DoorBlockMeta deserialized = new Gson().fromJson(json.meta(), DoorBlockMeta.class);
                DoorBlock block = new DoorBlock(meta, deserialized.id(), deserialized.width(), deserialized.height());
                block.initializeBlock();
                return block;
            }
            default -> throw new UnsupportedJsonVersionException(meta.type().name(), json.version());
        }
    }

    private Altar createAltar(AbstractInteractableBlockMeta meta, @Nullable VersionedJson json) {
        Altar block = new Altar(meta);
        block.spawn(block.getType().getMaterial());
        return block;
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

    private InventoryOpenerRegularBlock createInventoryOpener(AbstractInteractableBlockMeta meta, @Nullable VersionedJson json) {
        if (json == null) {
            throw new UnsupportedOperationException("Cannot create resource node block " + meta.type() + " required json is null!");
        }

        switch (json.version()) {
            case 1 -> {
                InventoryOpenerMeta deserialized = new Gson().fromJson(json.meta(), InventoryOpenerMeta.class);
                InventoryOpenerRegularBlock block = new InventoryOpenerRegularBlock(meta);
                block.initializeBlock(deserialized.inventoryId(), deserialized.requirements(), deserialized.label(), deserialized.icon(), deserialized.blockType(), deserialized.marker(), deserialized.markerLabel());
                return block;
            }
            default -> throw new UnsupportedJsonVersionException(meta.type().name(), json.version());
        }
    }

    private InventoryOpenerModelledBlock createInventoryOpenerEntity(AbstractInteractableBlockMeta meta, @Nullable VersionedJson json) {
        if (json == null) {
            throw new UnsupportedOperationException("Cannot create resource node block " + meta.type() + " required json is null!");
        }

        switch (json.version()) {
            case 1 -> {
                InventoryOpenerModelledMeta deserialized = new Gson().fromJson(json.meta(), InventoryOpenerModelledMeta.class);
                InventoryOpenerModelledBlock block = new InventoryOpenerModelledBlock(meta, deserialized.model(), deserialized.height(), deserialized.width(), deserialized.depth(), deserialized.scale());
                block.initializeBlock(deserialized.inventoryId(), deserialized.requirements(), deserialized.label(), deserialized.icon(), deserialized.blockType(), deserialized.marker(), deserialized.markerLabel());
                return block;
            }
            default -> throw new UnsupportedJsonVersionException(meta.type().name(), json.version());
        }
    }
}

