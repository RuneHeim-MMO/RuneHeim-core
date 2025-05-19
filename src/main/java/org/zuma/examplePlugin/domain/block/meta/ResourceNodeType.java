package org.zuma.examplePlugin.domain.block.meta;

import org.bukkit.Material;
import org.zuma.examplePlugin.constants.SoundType;
import org.zuma.examplePlugin.domain.item.ItemDrop;
import org.zuma.examplePlugin.domain.item.SkillRequirement;
import org.zuma.examplePlugin.domain.modifier.constants.ModifierType;

import javax.annotation.Nullable;
import java.util.List;

public class ResourceNodeType {
    private final int id;
    private final String label, wrongToolMessage, actionVerb;
    private final Material material;
    private final List<SkillRequirement> requirements;
    private final int baseGatheringDurationMs, respawnDurationSecs;
    private final ItemDrop drop;
    private final SoundType stepSound, gatherSuccessSound;

    @Nullable
    private final ModifierType toolModifier;

    public ResourceNodeType(int id, String label, Material material, List<SkillRequirement> requirements, @Nullable ModifierType toolRequiresModifier,
                            int baseGatheringDurationMs, int respawnDurationSecs, ItemDrop drop, String wrongToolMsg, String actionVerb, SoundType stepSound, SoundType gatherSuccessSound) {
        this.id = id;
        this.label = label;
        this.material = material;
        this.requirements = requirements;
        this.toolModifier = toolRequiresModifier;
        this.baseGatheringDurationMs = baseGatheringDurationMs;
        this.respawnDurationSecs = respawnDurationSecs;
        this.drop = drop;
        this.wrongToolMessage = wrongToolMsg;
        this.actionVerb = actionVerb;
        this.stepSound = stepSound;
        this.gatherSuccessSound = gatherSuccessSound;
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

    public ItemDrop getDrop() {
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
}
