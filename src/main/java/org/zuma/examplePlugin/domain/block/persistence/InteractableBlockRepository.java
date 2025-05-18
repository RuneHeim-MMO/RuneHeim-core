package org.zuma.examplePlugin.domain.block.persistence;

import org.zuma.examplePlugin.domain.block.base.OreBlockMeta;
import org.zuma.examplePlugin.domain.item.ItemDrop;
import org.zuma.examplePlugin.domain.item.constants.ItemId;

public class InteractableBlockRepository {

    public OreBlockMeta getById(int id) {
        return new OreBlockMeta("Iron ore", org.bukkit.Material.IRON_ORE, 1, 3000, 5, new ItemDrop(ItemId.IRON_ORE, 1));
    }

}
