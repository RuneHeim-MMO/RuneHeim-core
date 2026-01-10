package org.zuma.examplePlugin.domain.block.meta;

import org.bukkit.Material;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.constants.SoundType;
import org.zuma.examplePlugin.core.logger.constants.LogLevel;
import org.zuma.examplePlugin.domain.block.constants.RetexturedStem;
import org.zuma.examplePlugin.domain.droptable.DropTable;
import org.zuma.examplePlugin.domain.item.ItemRecord;
import org.zuma.examplePlugin.domain.map.MarkerType;
import org.zuma.examplePlugin.domain.modifier.constants.ModifierType;
import org.zuma.examplePlugin.domain.player.RunePlayer;
import org.zuma.examplePlugin.domain.skill.SkillRequirement;
import org.zuma.examplePlugin.domain.skill.SkillXpRecord;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ResourceNodeType {
    private final int id, startingDeposit, maxDeposit;
    private final String label, wrongToolMessage, actionVerb;
    private final String labelHex;
    private final Material material;
    private final List<SkillRequirement> requirements;
    private final int baseGatheringDurationMs, respawnDurationSecs;
    private final List<DropTable> dropTables;
    private final int displayItemStackID;
    private final SoundType startSound, stepSound, gatherSuccessSound;
    private final List<SkillXpRecord> xpReward;
    private final MarkerType markerType;
    private final boolean displayResultInHologram;

    private @Nullable RetexturedStem retexturedStem;

    @Nullable
    private final ModifierType toolModifier;

    public ResourceNodeType(int id, MarkerType markerType, String label, @Nullable String labelHex, Material material, boolean displayResultInHologram, @Nullable List<SkillRequirement> requirements, @Nullable ModifierType toolRequiresModifier,
                            int baseGatheringDurationMs, int respawnDurationSecs, List<Integer> dropTableIds, int displayItemStackID, String wrongToolMsg, String actionVerb, SoundType startSound, SoundType stepSound, SoundType gatherSuccessSound, @Nullable List<SkillXpRecord> xpReward, int startingDeposit, int maxDeposit) {
        this.id = id;
        this.markerType = markerType;
        this.label = label;
        this.labelHex = labelHex == null ? "" : labelHex;
        this.material = material;
        this.toolModifier = toolRequiresModifier;
        this.baseGatheringDurationMs = baseGatheringDurationMs;
        this.respawnDurationSecs = respawnDurationSecs;
        this.displayItemStackID = displayItemStackID;
        this.wrongToolMessage = wrongToolMsg;
        this.actionVerb = actionVerb;
        this.startSound = startSound;
        this.stepSound = stepSound;
        this.gatherSuccessSound = gatherSuccessSound;
        this.displayResultInHologram = displayResultInHologram;
        this.startingDeposit = startingDeposit;
        this.maxDeposit = maxDeposit;
        this.retexturedStem = null;

        this.requirements = Objects.requireNonNullElseGet(requirements, ArrayList::new);
        this.xpReward = Objects.requireNonNullElseGet(xpReward, ArrayList::new);

        this.dropTables = dropTableIds.stream().map(dropTableId -> {
            DropTable table = RuneQuestPlugin.getDropTableHandler().getByID(dropTableId);

            if (table == null) {
                RuneQuestPlugin.getRuneLogger().log(LogLevel.WARN, "Failed adding drop table id " + dropTableId + " to " + label + " node! Drop table does not exist.");
            }

            return table;
        }).toList();
    }

    public ResourceNodeType setRetexturedStem(RetexturedStem stem) {
        this.retexturedStem = stem;
        return this;
    }

    public @Nullable RetexturedStem getRetexturedStem() {
        return retexturedStem;
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


    public ItemRecord getDisplayItemStackID() {
        return new ItemRecord(displayItemStackID, 1);
    }

    public List<ItemRecord> rollGatheringDrops(RunePlayer activator) {
        List<ItemRecord> drops = new ArrayList<>();

        this.dropTables.forEach(dropTable -> {
            ItemRecord drop = dropTable.roll(activator);

            if (drop != null) drops.add(drop);

        });

        return drops;
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

    @Nullable
    public SoundType getStepSound() {
        return stepSound;
    }

    @Nullable
    public SoundType getGatherSuccessSound() {
        return gatherSuccessSound;
    }

    public int getId() {
        return id;
    }

    public List<SkillXpRecord> getXpReward() {
        return xpReward;
    }

    @Nullable
    public SoundType getStartSound() {
        return startSound;
    }

    public MarkerType getMarkerType() {
        return markerType;
    }

    public boolean isDisplayResultInHologram() {
        return displayResultInHologram;
    }

    public int getStartingDeposit() {
        return startingDeposit;
    }

    public int getMaxDeposit() {
        return maxDeposit;
    }
    
    public String getLabelHex() {
        return labelHex;
    }
}
