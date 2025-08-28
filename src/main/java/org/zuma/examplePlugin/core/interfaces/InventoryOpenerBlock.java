package org.zuma.examplePlugin.core.interfaces;

import org.bukkit.Material;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.zuma.examplePlugin.core.interfaces.SkillRestrictable;
import org.zuma.examplePlugin.domain.block.meta.AbstractInteractableBlockMeta;
import org.zuma.examplePlugin.domain.item.base.AbstractItemType;
import org.zuma.examplePlugin.domain.map.MarkerType;
import org.zuma.examplePlugin.domain.player.RunePlayer;
import org.zuma.examplePlugin.domain.skill.SkillRequirement;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

public interface InventoryOpenerBlock extends SkillRestrictable {

    void initializeBlock(
            int inventoryId,
            List<SkillRequirement> requirements,
            String label,
            String icon,
            Material material,
            @Nullable MarkerType markerType,
            @Nullable String markerLabel
    );

    void onPlayerInteractEvent(
            RunePlayer player,
            Action action,
            PlayerInteractEvent event,
            AbstractItemType itemInHand
    );

    void forceCloseOpenedInventories();

    void removeInventoryUUID(UUID instanceUUID);

    List<SkillRequirement> getRequirements();
}
