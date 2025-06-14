package org.zuma.examplePlugin.domain.block.meta;

import org.bukkit.Material;
import org.zuma.examplePlugin.core.constants.SoundType;
import org.zuma.examplePlugin.domain.item.ItemRecord;
import org.zuma.examplePlugin.domain.map.MarkerType;
import org.zuma.examplePlugin.domain.modifier.constants.ModifierType;
import org.zuma.examplePlugin.domain.skill.SkillRequirement;
import org.zuma.examplePlugin.domain.skill.SkillXpRecord;

import javax.annotation.Nullable;
import java.util.List;

public class ResourceNodeType {
    private final int id;
    private final String label, wrongToolMessage, actionVerb;
    private final Material material;
    private final List<SkillRequirement> requirements;
    private final int baseGatheringDurationMs, respawnDurationSecs;
    private final ItemRecord drop;
    private final SoundType startSound, stepSound, gatherSuccessSound;
    private final List<SkillXpRecord> xpReward;
    private final MarkerType markerType;

    @Nullable
    private final ModifierType toolModifier;

    public ResourceNodeType(int id, MarkerType markerType, String label, Material material, List<SkillRequirement> requirements, @Nullable ModifierType toolRequiresModifier,
                            int baseGatheringDurationMs, int respawnDurationSecs, ItemRecord drop, String wrongToolMsg, String actionVerb, SoundType startSound, SoundType stepSound, SoundType gatherSuccessSound, List<SkillXpRecord> xpReward) {
        this.id = id;
        this.markerType = markerType;
        this.label = label;
        this.material = material;
        this.requirements = requirements;
        this.toolModifier = toolRequiresModifier;
        this.baseGatheringDurationMs = baseGatheringDurationMs;
        this.respawnDurationSecs = respawnDurationSecs;
        this.drop = drop;
        this.wrongToolMessage = wrongToolMsg;
        this.actionVerb = actionVerb;
        this.startSound = startSound;
        this.stepSound = stepSound;
        this.gatherSuccessSound = gatherSuccessSound;
        this.xpReward = xpReward;
    }

    public Material getMaterial() {
        return material;
    }

    public int getBaseGatheringDurationMillis() {
        return baseGatheringDurationMs;
    }

    public String getLabel() {
        return label;
    }

    public int getRespawnDurationSeconds() {
        return respawnDurationSecs;
    }

    public ItemRecord getDrop() {
        return drop;
    }

    public List<SkillRequirement> getRequirements() {
        return requirements;
    }

    @Nullable
    public ModifierType getRequiredToolModifier() {
        return toolModifier;
    }

    public String getWrongToolMessage() {
        return wrongToolMessage;
    }

    public String getActionVerb() {
        return actionVerb;
    }

    public SoundType getStepSound() {
        return stepSound;
    }

    public SoundType getGatherSuccessSound() {
        return gatherSuccessSound;
    }

    public int getId() {
        return id;
    }

    public List<SkillXpRecord> getXpReward() {
        return xpReward;
    }

    public SoundType getStartSound() {
        return startSound;
    }

    public MarkerType getMarkerType() {
        return markerType;
    }
}
