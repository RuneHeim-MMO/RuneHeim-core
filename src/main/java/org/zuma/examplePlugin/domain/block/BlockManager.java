package org.zuma.examplePlugin.domain.block;

import eu.decentsoftware.holograms.api.DHAPI;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.zuma.examplePlugin.constants.Colors;
import org.zuma.examplePlugin.domain.block.base.InteractableBlock;
import org.zuma.examplePlugin.domain.block.base.OreBlock;
import org.zuma.examplePlugin.domain.block.base.OreBlockMeta;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class BlockManager {
    private final HashMap<Location, InteractableBlock> blocks = new HashMap<>();

    public void spawnOreBlock(OreBlockMeta meta, Location location, Location hologramLocation) {
        if (blocks.get(location) != null) {
            return;
        }

        List<String> hologramContent = List.of(
                Colors.INTERACTABLE_ENTITY.asSpigotHex() + meta.getLabel(),
                Colors.ACCENT.asSpigotHex() + "⛏ " + meta.getMinLevel() + " Lvl"
        );

        InteractableBlock block = new OreBlock(meta, location);
        block.setHologram(DHAPI.createHologram(UUID.randomUUID().toString(), hologramLocation, hologramContent));
        block.spawn(meta.getMaterial());

        blocks.put(location, block);
    }

    public InteractableBlock getInteractableBlock(Block block) {
        return blocks.get(block.getLocation());
    }

}
