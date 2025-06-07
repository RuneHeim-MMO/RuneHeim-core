package org.zuma.examplePlugin.domain.block.meta.v1;

import org.bukkit.Material;
import org.zuma.examplePlugin.domain.skill.SkillRequirement;

import javax.annotation.Nullable;
import java.util.List;

public record InventoryOpenerMeta(int inventoryId, Material blockType,
                                  @Nullable List<SkillRequirement> requirements,
                                  String label) {
}
