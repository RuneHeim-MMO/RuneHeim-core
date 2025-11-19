package org.zuma.examplePlugin.domain.block.meta.v1;

import org.bukkit.block.data.type.Door;

import javax.annotation.Nullable;
import java.util.List;

public record TeleportDoorMeta(String id, String label, String world, int x, int y, int z, int yaw,
                               Door.Hinge hinge,
                               @Nullable QuestLockedMeta questLockedMeta) {
}
