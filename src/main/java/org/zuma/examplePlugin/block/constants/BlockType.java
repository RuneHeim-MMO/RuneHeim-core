package org.zuma.examplePlugin.block.constants;

import org.bukkit.Material;

public enum BlockType {
    IRON_ORE(0, org.bukkit.Material.IRON_ORE);

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
