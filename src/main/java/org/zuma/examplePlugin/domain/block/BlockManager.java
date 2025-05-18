package org.zuma.examplePlugin.domain.block;

import com.google.gson.Gson;
import eu.decentsoftware.holograms.api.DHAPI;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.zuma.examplePlugin.constants.Colors;
import org.zuma.examplePlugin.domain.block.base.InteractableBlock;
import org.zuma.examplePlugin.domain.block.base.InteractableBlockMeta;
import org.zuma.examplePlugin.domain.block.base.ResourceNode;
import org.zuma.examplePlugin.domain.block.base.ResourceNodeMeta;
import org.zuma.examplePlugin.domain.block.constants.BlockType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class BlockManager {
    private final HashMap<Location, InteractableBlock> blocks = new HashMap<>();

    public void spawnResourceNode(ResourceNodeMeta type, Location location, Location hologramLocation) {
        if (blocks.get(location) != null) {
            return;
        }

        List<String> hologramContent = new ArrayList<>(List.of(
                Colors.INTERACTABLE_ENTITY.asSpigotHex() + type.getLabel()
        ));

        type.getRequirements().forEach(req -> hologramContent.add(Colors.ACCENT.asSpigotHex() +
                "Req. " + req.getType().getSymbol() + " " + req.getMinimumLevel() + " Level"));

        ResourceNode block = new ResourceNode(new InteractableBlockMeta(BlockType.ResourceNode, location, new Gson().toJson(type)));
        block.deserializeAndApplyMeta();

        block.setHologram(DHAPI.createHologram(UUID.randomUUID().toString(), hologramLocation, hologramContent));
        block.spawn(type.getMaterial());

        blocks.put(location, block);
    }

    public InteractableBlock getInteractableBlock(Block block) {
        return blocks.get(block.getLocation());
    }

}
