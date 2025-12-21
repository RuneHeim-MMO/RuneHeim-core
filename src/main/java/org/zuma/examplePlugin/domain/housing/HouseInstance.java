package org.zuma.examplePlugin.domain.housing;

import org.bukkit.Location;
import org.zuma.examplePlugin.domain.housing.template.HouseTemplate;
import org.zuma.examplePlugin.domain.schematic.InteractiveElement;

import java.util.List;
import java.util.UUID;

public class HouseInstance {
    private final UUID ownerUUID;
    private final String ownerName;
    private final List<InteractiveElement> interactiveElements;
    private final int gridX, gridZ;
    private final Location spawnPoint;
    private final HouseTemplate template;

    public HouseInstance(UUID ownerUUID, String ownerName, int x, int z, List<InteractiveElement> interactiveElements, Location spawnPoint, HouseTemplate template) {
        this.ownerUUID = ownerUUID;
        this.ownerName = ownerName;
        this.gridX = Math.floorDiv(x, HousingManager.GRID_SIZE);
        this.gridZ = Math.floorDiv(z, HousingManager.GRID_SIZE);
        this.interactiveElements = interactiveElements;
        this.spawnPoint = spawnPoint;
        this.template = template;
    }

    public UUID getOwnerUUID() {
        return ownerUUID;
    }

    public int getGridX() {
        return gridX;
    }

    public int getGridZ() {
        return gridZ;
    }

    public List<InteractiveElement> getInteractiveElements() {
        return interactiveElements;
    }

    public Location getSpawnPoint() {
        return spawnPoint;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public HouseTemplate getTemplate() {
        return template;
    }
}
