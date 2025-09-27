package org.zuma.examplePlugin.domain.territory;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.zuma.examplePlugin.domain.player.RunePlayer;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class Territory {
    private final Location centerPoint, spawnPoint;
    private final int radius, minLevel, maxLevel;
    private final String name, description, firstTimeDiscoveryLore, id;
    private final boolean pvpZone, renderOnMap;
    private final List<String> requiredQuestsIds;
    @Nullable
    private String entryDenyMessage;
    private boolean dropItemsOnDeath;

    public Territory(String id, Location centerPoint, @Nullable Location spawnPoint, int radius, String name, String description, @Nullable String firstTimeDiscoveryLore, int minLevel, int maxLevel, boolean pvpZone, boolean renderOnMap) {
        this.centerPoint = centerPoint;
        this.radius = radius;
        this.minLevel = minLevel;
        this.maxLevel = maxLevel;
        this.name = name;
        this.id = id;
        this.description = description;
        this.spawnPoint = spawnPoint;
        this.firstTimeDiscoveryLore = firstTimeDiscoveryLore;
        this.pvpZone = pvpZone;
        this.renderOnMap = renderOnMap;
        this.requiredQuestsIds = new ArrayList<>();
        this.entryDenyMessage = null;
        this.dropItemsOnDeath = false;
    }

    public Location getCenterPoint() {
        return centerPoint;
    }

    public int getRadius() {
        return radius;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public int getMinLevel() {
        return minLevel;
    }

    public int getMaxLevel() {
        return maxLevel;
    }

    @Nullable
    public Location getSpawnPoint() {
        return spawnPoint;
    }

    @Nullable
    public String getFirstTimeDiscoveryLore() {
        return firstTimeDiscoveryLore;
    }

    public boolean isPvpZone() {
        return pvpZone;
    }

    public boolean isRenderOnMap() {
        return renderOnMap;
    }

    public boolean playerMeetsRequirements(RunePlayer player) {
        if (player.asBukkitPlayer().getGameMode() == GameMode.CREATIVE && player.asBukkitPlayer().isOp()) {
            return true;
        }

        for (String questId : requiredQuestsIds) {
            if (!player.getCompletedQuestIds().contains(questId)) {
                return false;
            }
        }

        return true;
    }

    public List<String> getRequiredQuestsIds() {
        return requiredQuestsIds;
    }

    public Territory addRequiredQuestId(String questId) {
        this.requiredQuestsIds.add(questId);
        return this;
    }

    public Territory setEntryDenyMessage(@Nullable String message) {
        this.entryDenyMessage = message;
        return this;
    }

    @Nullable
    public String getEntryDenyMessage() {
        return entryDenyMessage;
    }

    public boolean isDropItemsOnDeath() {
        return dropItemsOnDeath;
    }

    public Territory setDropItemsOnDeath(boolean dropItemsOnDeath) {
        this.dropItemsOnDeath = dropItemsOnDeath;
        return this;
    }
}
