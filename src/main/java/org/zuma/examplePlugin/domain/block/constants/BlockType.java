package org.zuma.examplePlugin.domain.block.constants;

import org.bukkit.Material;

public enum BlockType {
    ResourceNode(0, Material.IRON_ORE),
    InventoryOpener(1, Material.BARRIER),
    InventoryOpenerModelled(2, Material.BARRIER),
    Altar(3, Material.LECTERN),
    Door(4, Material.IRON_BARS);

    private final int id;
    private final Material material;

    BlockType(int id, Material material) {
        this.id = id;
        this.material = material;
    }

    public Material getMaterial() {
        return material;
    }

    public int getId() {
        return id;
    }

    }
