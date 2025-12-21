package org.zuma.examplePlugin.domain.housing;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.data.type.Door;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.jetbrains.annotations.NotNull;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.logger.constants.LogLevel;
import org.zuma.examplePlugin.domain.block.constants.BlockType;
import org.zuma.examplePlugin.domain.block.impl.door.TeleportDoor;
import org.zuma.examplePlugin.domain.block.meta.AbstractInteractableBlockMeta;
import org.zuma.examplePlugin.domain.block.meta.HologramSide;
import org.zuma.examplePlugin.domain.block.meta.v1.QuestLockedMeta;
import org.zuma.examplePlugin.domain.housing.template.HouseTemplate;
import org.zuma.examplePlugin.domain.housing.template.TemplateMeta;
import org.zuma.examplePlugin.domain.housing.world.WorldManager;
import org.zuma.examplePlugin.domain.schematic.InteractiveElType;
import org.zuma.examplePlugin.domain.schematic.InteractiveElement;
import org.zuma.examplePlugin.domain.schematic.SchematicSpawner;
import org.zuma.examplePlugin.domain.territory.Territory;

import java.util.*;

public class HousingManager implements Listener {
    public final static int GRID_SIZE = 128;
    private final Map<Integer, Map<Integer, HouseInstance>> gridMap = new HashMap<>();
    private final Map<HouseTemplate, TemplateMeta> templateMetaMap = new HashMap<>();

    private int totalInstances = 0;

    private final WorldManager worldManager;
    private final SchematicSpawner schematicSpawner;

    public HousingManager(WorldManager worldManager) {
        this.worldManager = worldManager;
        this.schematicSpawner = new SchematicSpawner();

        Bukkit.getScheduler().runTaskLater(RuneQuestPlugin.getInstance(), () -> {
            createHousingInstance(UUID.randomUUID(), "Test1", HouseTemplate.DRAKKAR_STARTER, false);
            createHousingInstance(UUID.randomUUID(), "Test5", HouseTemplate.DRAKKAR_STARTER, false);

        }, 100L);
    }

    // Load all house template, in order to validate them and extract metadata (interactive elements)
    public void loadTemplatesMeta() {
        for (HouseTemplate template : HouseTemplate.values()) {
            try {
                HouseInstance testInstance = createHousingInstance(UUID.randomUUID(), "Template", template, true);

                List<InteractiveElement> elements = testInstance.getInteractiveElements();
                List<InteractiveElType> requiredElements = List.of(InteractiveElType.EXIT, InteractiveElType.SPAWN);

                for (InteractiveElType requiredType : requiredElements) {
                    boolean hasType = elements.stream().anyMatch(el -> el.type() == requiredType);

                    if (!hasType) {
                        throw new IllegalStateException("Template " + template.name() + " is missing required interactive element of type " + requiredType.name());
                    }
                }

                InteractiveElement spawnPoint = elements.stream()
                        .filter(el -> el.type() == InteractiveElType.SPAWN)
                        .findFirst()
                        .orElseThrow(() -> new IllegalStateException("Template " + template.name() + " is missing spawn point interactive element"));

                Location relativeSpawnPoint = getRelativeSpawnPoint(spawnPoint, testInstance);
                TemplateMeta meta = new TemplateMeta(elements.stream().filter(
                        el -> el.type() == InteractiveElType.SLOT).toList().size(),
                        elements.stream().filter(el -> el.type() == InteractiveElType.DECORATION_SLOT).toList().size(),
                        relativeSpawnPoint);

                templateMetaMap.put(template, meta);
                RuneQuestPlugin.getRuneLogger().log(LogLevel.INFO, "[PlayerHousing] Loaded template " + template.name() + " successfully. Detected: " + (meta.interactiveSlotCount() + meta.decorationSlotCount()) + " slots and spawn point at " + meta.relativeSpawnPoint().toString());

            } catch (Exception e) {
                RuneQuestPlugin.getRuneLogger().log(LogLevel.WARN, "[PlayerHousing] Failed to load template " + template.name() + " metadata. Invalid template. ", e);
            }
        }

        RuneQuestPlugin.getRuneLogger().log(LogLevel.INFO, "[PlayerHousing] Loaded " + templateMetaMap.size() + " house templates.");
    }

    private static @NotNull Location getRelativeSpawnPoint(InteractiveElement spawnPoint, HouseInstance instance) {
        Location relativeSpawnPoint = spawnPoint.location().clone();

        switch (spawnPoint.face()) {
            case SOUTH -> relativeSpawnPoint.setYaw(0);
            case NORTH -> relativeSpawnPoint.setYaw(180);
            case EAST -> relativeSpawnPoint.setYaw(90);
            case WEST -> relativeSpawnPoint.setYaw(-90);
            default -> relativeSpawnPoint.setYaw(0);
        }

        relativeSpawnPoint.subtract(instance.getGridX() * GRID_SIZE, 0, instance.getGridZ() * GRID_SIZE);
        return relativeSpawnPoint;
    }

    // Update interactive elements (e.g actions block, holograms) positions according to schematic
    private void handleElementReassignation(List<InteractiveElement> elements, UUID ownerUUID, HouseInstance instance) {
        Set<Location> processedLocations = new HashSet<>();

        for (InteractiveElement el : elements) {

            if (el.type() != InteractiveElType.EXIT) continue;

            Location location = el.location().clone().add(0, -1, 0).toBlockLocation();
            Location exitLocation = instance.getTemplate().getRegion().getExitLocation();

            // Determine hinge based on adjacency
            Door.Hinge hinge = Door.Hinge.RIGHT;

            for (Location other : processedLocations) {
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
                            1001 + instance.getGridX(),
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

            exitDoor.initializeBlock();
            RuneQuestPlugin.getBlockManager().registerBlock(exitDoor);

            // Mark this location as processed
            processedLocations.add(location);
        }
    }


    // InitialMetaSpawn is true when creating a test instance to extract metadata from the template
    private HouseInstance createHousingInstance(UUID ownerUUID, String ownerName, HouseTemplate template, boolean initialMetaSpawn) {
        if (!initialMetaSpawn && !templateMetaMap.containsKey(template)) {
            throw new IllegalArgumentException("Template " + template.name() + " is not valid or not loaded.");
        }


        int gridX = totalInstances % 1000;
        int gridZ = totalInstances / 1000;
        //int centerPoint = GRID_SIZE / 2;

        Location pasteLocation = new Location(worldManager.getHousingWorld(), gridX * GRID_SIZE, 64, gridZ * GRID_SIZE);
        schematicSpawner.pasteSchematic(pasteLocation, template.getSchematic(), true, true);
        List<InteractiveElement> elements = schematicSpawner.extractInteractiveElements();

        Location spawnPoint = new Location(worldManager.getHousingWorld(), gridX * GRID_SIZE, 64, gridZ * GRID_SIZE);

        if (!initialMetaSpawn) {
            Location offset = templateMetaMap.get(template).relativeSpawnPoint().clone();
            spawnPoint.setY(0); // Reset Y to avoid issues
            spawnPoint.add(offset.x(), offset.y(), offset.z());
        }

        HouseInstance instance = new HouseInstance(ownerUUID, ownerName, gridX * GRID_SIZE, gridZ * GRID_SIZE, elements, spawnPoint, template);
        gridMap.computeIfAbsent(gridX, k -> new HashMap<>()).put(gridZ, instance);
        totalInstances++;

        handleElementReassignation(elements, ownerUUID, instance);
        String territoryName = initialMetaSpawn ? template.getTemplateName() : ownerName + "'s House";

        RuneQuestPlugin.getTerritoryHandler().getTerritoryList().add(new Territory("house_" + ownerUUID.toString(),
                new Location(worldManager.getHousingWorld(), (gridX * GRID_SIZE) + GRID_SIZE / 2, 64, (gridZ * GRID_SIZE) + GRID_SIZE / 2),
                instance.getSpawnPoint(),
                GRID_SIZE / 2,
                territoryName,
                "",
                null, 0, 0,
                false,
                false,
                false,
                256, true));

        RuneQuestPlugin.getRuneLogger().log(LogLevel.INFO, "[PlayerHousing] Created house instance for template " + template.name() + " at grid (" + gridX + ", " + gridZ + ") spawn point: " + spawnPoint);

        return instance;
    }

    public HouseInstance getHousingAt(int x, int z) {
        int gridX = Math.floorDiv(x, GRID_SIZE);
        int gridZ = Math.floorDiv(z, GRID_SIZE);

        Map<Integer, HouseInstance> row = gridMap.get(gridX);

        if (row != null) {
            return row.get(gridZ);
        }

        return null;
    }

    @EventHandler
    public void onPlayerChangeWorld(PlayerChangedWorldEvent event) {
       /* RunePlayer player = RuneQuestPlugin.getPlayerManager().getRunePlayer(event.getPlayer());

        if (player == null) {
            return;
        }

        System.out.println("Player " + event.getPlayer().getName() + " changed world to " + event.getPlayer().getWorld().getName());

        String newWorld = event.getPlayer().getWorld().getName();
        String oldWorld = event.getFrom().getName();

        if (newWorld.equals("world") && oldWorld.equals(WorldManager.WORLD_KEY)) {
            System.out.println("Player " + event.getPlayer().getName() + " is leaving housing world, clearing safe location.");
            player.setSafeLocation(null);
            return;
        }

        if (!newWorld.equals(WorldManager.WORLD_KEY)) {
            System.out.println("Player " + event.getPlayer().getName() + " is not in housing world.");
            return;
        }

        // Set safe location if not already set (fail-safe). It should be set when the player get's teleported to this world.
        if (player.getSafeLocation() == null) {
            System.out.println("Setting safe location for player " + event.getPlayer().getName());
            player.setSafeLocation(event.getPlayer().getLocation());
        }*/
    }
}
