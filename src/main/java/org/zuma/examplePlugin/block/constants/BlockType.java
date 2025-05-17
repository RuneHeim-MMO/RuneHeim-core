package org.zuma.examplePlugin.block.constants;

import org.bukkit.Material;

public enum BlockType {
    ORE(0, Material.IRON_ORE);

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
