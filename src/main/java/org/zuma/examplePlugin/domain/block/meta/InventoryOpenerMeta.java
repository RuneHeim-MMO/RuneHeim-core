package org.zuma.examplePlugin.domain.block.meta;

import org.bukkit.Material;
import org.zuma.examplePlugin.domain.gui.constants.InventoryId;
import org.zuma.examplePlugin.domain.skill.SkillRequirement;

import javax.annotation.Nullable;
import java.util.List;

public record InventoryOpenerMeta(InventoryId inventoryId, Material blockType,
                                  @Nullable List<SkillRequirement> requirements,
                                  String label) {
}
