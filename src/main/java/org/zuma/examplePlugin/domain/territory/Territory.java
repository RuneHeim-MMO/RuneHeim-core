package org.zuma.examplePlugin.domain.territory;

import org.bukkit.Location;
import org.zuma.examplePlugin.core.constants.MusicType;
import org.zuma.examplePlugin.core.constants.SoundType;
import org.zuma.examplePlugin.core.interfaces.Restrictable;
import org.zuma.examplePlugin.domain.skill.SkillRequirement;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class Territory implements Restrictable {
    private final Location centerPoint, spawnPoint;
    private final int radius, minLevel, maxLevel;
    private final String name, description, firstTimeDiscoveryLore, id;
    private final boolean pvpZone, renderOnMap, silent;
    private final List<String> requiredQuestsIds;
    private boolean dropItemsOnDeath, ignoreCutscene;
    private List<SoundType> ambientSounds;

    @Nullable
    private String entryDenyMessage;

    @Nullable
    private MusicType ambientMusic;

    public Territory(String id, Location centerPoint, @Nullable Location spawnPoint, int radius, String name, String description, @Nullable String firstTimeDiscoveryLore, int minLevel, int maxLevel, boolean pvpZone, boolean renderOnMap, boolean silent) {
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
        this.ambientSounds = new ArrayList<>();
        this.ambientMusic = null;
        this.silent = silent; // If true, no messages will be sent to players when they discover/enter/leave the territory
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

    @Override
    public List<SkillRequirement> getSkillRequirements() {
        return List.of();
    }

    @Override
    public List<String> getRequiredCompletedQuests() {
        return requiredQuestsIds;
    }

    public Territory setAmbientSounds(List<SoundType> ambientSounds) {
        this.ambientSounds = ambientSounds;
        return this;
    }

    @Nullable
    public SoundType getRandomAmbientSound() {
        if (ambientSounds.isEmpty()) return null;
        int index = (int) (Math.random() * ambientSounds.size());
        return ambientSounds.get(index);
    }

    @Nullable
    public MusicType getAmbientMusic() {
        return ambientMusic;
    }

    public Territory setAmbientMusic(@Nullable MusicType ambientMusic) {
        this.ambientMusic = ambientMusic;
        return this;
    }

    public boolean isSilent() {
        return silent;
    }

    public boolean isIgnoreCutscene() {
        return ignoreCutscene;
    }

    public Territory setVisibleInCutscene(boolean ignoreCutscene) {
        this.ignoreCutscene = ignoreCutscene;
        return this;
    }
}
