package org.zuma.examplePlugin.domain.block;

import com.google.gson.Gson;
import eu.decentsoftware.holograms.api.DHAPI;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.zuma.examplePlugin.constants.Colors;
import org.zuma.examplePlugin.domain.block.base.InteractableBlock;
import org.zuma.examplePlugin.domain.block.base.InteractableBlockMeta;
import org.zuma.examplePlugin.domain.block.base.OreBlock;
import org.zuma.examplePlugin.domain.block.base.OreBlockMeta;
import org.zuma.examplePlugin.domain.block.constants.BlockType;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class BlockManager {
    private final HashMap<Location, InteractableBlock> blocks = new HashMap<>();

    public void spawnOreBlock(OreBlockMeta type, Location location, Location hologramLocation) {
        if (blocks.get(location) != null) {
            return;
        }

        List<String> hologramContent = List.of(
                Colors.INTERACTABLE_ENTITY.asSpigotHex() + type.getLabel(),
                Colors.ACCENT.asSpigotHex() + "Req. ⛏ " + type.getMinLevel() + " Level"
        );

        OreBlock block = new OreBlock(new InteractableBlockMeta(BlockType.ORE, location, new Gson().toJson(type)));
        block.deserializeAndApplyMeta(block.getJsonMeta());

        block.setHologram(DHAPI.createHologram(UUID.randomUUID().toString(), hologramLocation, hologramContent));
        block.spawn(type.getMaterial());

        blocks.put(location, block);
    }

    public InteractableBlock getInteractableBlock(Block block) {
        return blocks.get(block.getLocation());
    }

}
