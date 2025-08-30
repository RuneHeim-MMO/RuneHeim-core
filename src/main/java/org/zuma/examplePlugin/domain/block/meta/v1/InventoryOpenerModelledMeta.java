package org.zuma.examplePlugin.domain.block.meta.v1;

import org.bukkit.Material;
import org.zuma.examplePlugin.domain.map.MarkerType;
import org.zuma.examplePlugin.domain.skill.SkillRequirement;

import javax.annotation.Nullable;
import java.util.List;

public record InventoryOpenerModelledMeta(int inventoryId, Material blockType,
                                          @Nullable List<SkillRequirement> requirements,
                                          String model, int height, int width, double scale,
                                          MarkerType marker, String markerLabel,
                                          String label, String icon) {
}
