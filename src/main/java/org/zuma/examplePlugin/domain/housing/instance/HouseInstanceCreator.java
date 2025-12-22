package org.zuma.examplePlugin.domain.housing.instance;

import org.bukkit.Location;
import org.bukkit.block.data.type.Door;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.constants.Icon;
import org.zuma.examplePlugin.core.logger.constants.LogLevel;
import org.zuma.examplePlugin.domain.block.constants.BlockType;
import org.zuma.examplePlugin.domain.block.impl.CommandBlock;
import org.zuma.examplePlugin.domain.block.impl.door.TeleportDoor;
import org.zuma.examplePlugin.domain.block.meta.AbstractInteractableBlockMeta;
import org.zuma.examplePlugin.domain.block.meta.HologramSide;
import org.zuma.examplePlugin.domain.block.meta.v1.QuestLockedMeta;
import org.zuma.examplePlugin.domain.housing.records.HouseFurnitureEntry;
import org.zuma.examplePlugin.domain.housing.records.HouseSlot;
import org.zuma.examplePlugin.domain.housing.HousingManager;
import org.zuma.examplePlugin.domain.housing.HousingRegion;
import org.zuma.examplePlugin.domain.housing.template.HouseTemplate;
import org.zuma.examplePlugin.domain.housing.template.TemplateMeta;
import org.zuma.examplePlugin.domain.housing.world.WorldManager;
import org.zuma.examplePlugin.domain.item.base.AbstractItemType;
import org.zuma.examplePlugin.domain.item.impl.FurnitureItem;
import org.zuma.examplePlugin.domain.player.RunePlayer;
import org.zuma.examplePlugin.domain.schematic.InteractiveElement;
import org.zuma.examplePlugin.domain.schematic.SchematicSpawner;
import org.zuma.examplePlugin.domain.territory.Territory;

import java.util.*;

public class HouseInstanceCreator {
    private int totalInstances = 0;

    private final HousingManager manager;
    private final WorldManager worldManager;
    private final SchematicSpawner schematicSpawner;

    public HouseInstanceCreator(HousingManager manager, WorldManager worldManager, SchematicSpawner schematicSpawner) {
        this.manager = manager;
        this.worldManager = worldManager;
        this.schematicSpawner = schematicSpawner;
    }


    // Update interactive elements (e.g actions block, holograms) positions according to schematic
    private void handleElementReassignation(List<InteractiveElement> elements, UUID ownerUUID, HouseInstance instance) {
        Set<Location> processedDoors = new HashSet<>();

        for (InteractiveElement el : elements) {

            switch (el.type()) {
                case EXIT -> {
                    Location location = el.location().clone().add(0, -1, 0).toBlockLocation();
                    Location exitLocation = instance.getTemplate().getRegion().getExitLocation();

                    // Determine hinge based on adjacency
                    Door.Hinge hinge = Door.Hinge.RIGHT;

                    for (Location other : processedDoors) {
                        // Check if adjacent (simple cardinal adjacency)
                        if (other.getWorld().equals(location.getWorld())
                                && other.getBlockX() == location.getBlockX() && Math.abs(other.getBlockZ() - location.getBlockZ()) == 1
                                || other.getBlockZ() == location.getBlockZ() && Math.abs(other.getBlockX() - location.getBlockX()) == 1) {
                            // Second door next to first → flip hinge
                            hinge = (hinge == Door.Hinge.RIGHT ? Door.Hinge.LEFT : Door.Hinge.RIGHT);
                            break;
                        }
                    }

                    TeleportDoor exitDoor = new TeleportDoor(
                            new AbstractInteractableBlockMeta(
                                    100001 + instance.getGridX(),
                                    BlockType.TeleportDoor,
                                    el.face(),
                                    location,
                                    HologramSide.TOP
                            ),
                            hinge,
                            "house_exit_" + ownerUUID + "_" + hinge,
                            "Exit",
                            exitLocation.getWorld().toString(),
                            (int) exitLocation.x(),
                            (int) exitLocation.y(),
                            (int) exitLocation.z(),
                            (int) exitLocation.getYaw(),
                            new QuestLockedMeta(null, null, null, null)
                    );

                    exitDoor.setOnUse(instance::exitHouse);
                    exitDoor.initializeBlock();
                    instance.getInteractableLocations().add(location);
                    RuneQuestPlugin.getBlockManager().registerBlock(exitDoor);

                    // Mark this location as processed
                    processedDoors.add(location);
                }
                case CONTROL_PANEL -> {
                    Location location = el.location().clone().toBlockLocation();
                    HologramSide side = HologramSide.FRONT;

                    switch (el.face()) {
                        case EAST -> side = HologramSide.RIGHT;
                        case WEST -> side = HologramSide.LEFT;
                        case NORTH -> side = HologramSide.FRONT;
                        case SOUTH -> side = HologramSide.BACK;
                    }

                    CommandBlock block = new CommandBlock(new AbstractInteractableBlockMeta(200001 + instance.getGridX(), BlockType.CommandBlock, el.face(), location, side));
                    block.initializeBlock(List.of(Icon.CONTROL_PANEL.get() + " Control Panel"), List.of("house"));
                    instance.getInteractableLocations().add(location);

                    RuneQuestPlugin.getBlockManager().registerBlock(block);
                }
                default -> {
                    continue;
                }
            }


        }
    }

    // InitialMetaSpawn is true when creating a test instance to extract metadata from the template
    public HouseInstance createInstance(UUID ownerUUID, UUID instanceUUID, String ownerName, HouseTemplate template, boolean initialMetaSpawn, HashMap<Integer, HouseFurnitureEntry> furnitureEntries) {
        RunePlayer owner = null;

        if (!initialMetaSpawn) {
            owner = RuneQuestPlugin.getPlayerManager().getRunePlayer(ownerUUID);
            if (owner == null) {
                throw new IllegalArgumentException("Owner with UUID " + ownerUUID + " not found!");
            }
        }

        TemplateMeta meta = manager.getTemplateMeta(template);

        if (!initialMetaSpawn && meta == null) {
            throw new IllegalArgumentException("Template " + template.name() + " is not valid or not loaded.");
        }

        int gridX = totalInstances % 1000;
        int gridZ = totalInstances / 1000;

        Location pasteLocation = new Location(worldManager.getHousingWorld(), gridX * HousingManager.GRID_SIZE, 64, gridZ * HousingManager.GRID_SIZE);
        schematicSpawner.pasteSchematic(pasteLocation, template.getSchematic(), true, true);

        List<InteractiveElement> elements = schematicSpawner.extractInteractiveElements();
        Location spawnPoint = new Location(worldManager.getHousingWorld(), gridX * HousingManager.GRID_SIZE, 64, gridZ * HousingManager.GRID_SIZE);

        if (!initialMetaSpawn) {
            Location offset = meta.relativeSpawnPoint().clone();
            spawnPoint.setY(0); // Reset Y to avoid issues
            spawnPoint.add(offset.x(), offset.y(), offset.z());
            spawnPoint.setYaw(offset.getYaw());
        }

        HouseInstance instance = new HouseInstance(ownerUUID, instanceUUID, ownerName, gridX * HousingManager.GRID_SIZE, gridZ * HousingManager.GRID_SIZE, elements, spawnPoint, template);
        manager.getGridMap().computeIfAbsent(gridX, k -> new HashMap<>()).put(gridZ, instance);
        totalInstances++;

        handleElementReassignation(elements, ownerUUID, instance);
        String territoryName = initialMetaSpawn ? template.getTemplateName() : ownerName + "'s House";

        Territory houseTerritory = new Territory("house_" + ownerUUID.toString(),
                new Location(worldManager.getHousingWorld(), (gridX * HousingManager.GRID_SIZE) + HousingManager.GRID_SIZE / 2, 64, (gridZ * HousingManager.GRID_SIZE) + HousingManager.GRID_SIZE / 2),
                instance.getSpawnPoint(),
                HousingManager.GRID_SIZE / 2,
                territoryName,
                "",
                null, 0, 0,
                false,
                false,
                false,
                256, true);

        houseTerritory.setTemporary(true);
        RuneQuestPlugin.getTerritoryHandler().getTerritoryList().add(houseTerritory);

        if (!initialMetaSpawn) {
            populateHouse(instance, owner, furnitureEntries);

            manager.getPlayerHouseMap().computeIfAbsent(ownerUUID, k -> new HashMap<>());
            HashMap<HousingRegion, HouseInstance> regionMap = manager.getPlayerHouseMap().get(ownerUUID);
            regionMap.put(template.getRegion(), instance);
        }

        manager.getUuidHouseInstanceMap().put(instance.getInstanceUUID(), instance);
        RuneQuestPlugin.getRuneLogger().log(LogLevel.INFO, "[PlayerHousing] Created house instance (" + template.name() + ") at grid (" + gridX + ", " + gridZ + ") spawn point: (X " + spawnPoint.getX() + ", Y " + spawnPoint.getY() + ", Z " + spawnPoint.getZ() + ") for player " + ownerName + " with " + furnitureEntries.size() + " furniture items.");
        return instance;
    }

    public void populateHouse(HouseInstance instance, RunePlayer owner, HashMap<Integer, HouseFurnitureEntry> furnitureEntries) {
        // Logic to populate the house with player data (e.g., furniture, decorations)
        // This is a placeholder for actual implementation
        /*
         * This is invoked if player updates template as well,
         * It needs to consider available slots from TemplateMeta,
         * and existing furniture/decorations placed by the player.
         *
         * If overflowing, it should remove excess items and return them to the player's inventory.
         * */

        TemplateMeta meta = RuneQuestPlugin.getHousingManager().getTemplateMeta(instance.getTemplate());

        if (meta == null) {
            RuneQuestPlugin.getRuneLogger().log(LogLevel.CRITICAL, "Failed to initialize furniture for house instance " + instance.getInstanceUUID() + " because template meta is null for template " + instance.getTemplate().getTemplateName());
            return;
        }

        instance.getInteractableLocations().addAll(meta.slots().values().stream().map(HouseSlot::location).toList());

        furnitureEntries.forEach((id, entry) -> {
            try {
                HouseSlot slot = meta.slots().get(entry.slotIndex());

                if (slot == null) {
                    RuneQuestPlugin.getRuneLogger().log(LogLevel.WARN, "Cannot place furniture item ID " + id + " in house instance " + instance.getInstanceUUID() + " because slot ID " + entry.slotIndex() + " does not exist in template meta.");
                    return;
                }

                 System.out.println("Before location adjustment: " + slot.location());

                Location location = HousingManager.getLocationInHouse(slot.location(), instance);
                AbstractItemType type = RuneQuestPlugin.getItemRegistry().getType(entry.itemId());

                  System.out.println("After location adjustment: " + location);


                if (!(type instanceof FurnitureItem furnitureItem)) {
                    RuneQuestPlugin.getRuneLogger().log(LogLevel.WARN, "Cannot place furniture item ID " + id + " in house instance " + instance.getInstanceUUID() + " because item ID " + entry.itemId() + " is not a furniture item.");
                    return;
                }

                if (!furnitureItem.getFurnitureMeta().applicableSlot().equals(slot.type())) {
                    RuneQuestPlugin.getRuneLogger().log(LogLevel.WARN, "Cannot place furniture item ID " + id + " in house instance " + instance.getInstanceUUID() + " because furniture item slot type " + furnitureItem.getFurnitureMeta().applicableSlot() + " does not match house slot type " + slot.type() + ".");
                    return;
                }

                furnitureItem.placeInHouse(location, entry.face());
            } catch (Exception e) {
                RuneQuestPlugin.getRuneLogger().log(LogLevel.WARN, "Failed to place furniture item ID " + id + " in " + instance.getOwnerName() + " house: " + e.getMessage());
            }
        });

    }
}
