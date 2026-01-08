package org.zuma.examplePlugin.domain.herblore.base;

import org.bukkit.Material;

import java.util.HashMap;
import java.util.Map;

public class FertileBlock {
    private static final Material[] MATERIALS = {Material.GRASS_BLOCK, Material.DIRT, Material.COARSE_DIRT, Material.PODZOL};
    private static final Map<Material, Byte> MATERIAL_TO_BYTE = new HashMap<>();

    static {
        for (byte i = 0; i < MATERIALS.length; i++) {
            MATERIAL_TO_BYTE.put(MATERIALS[i], i);
        }
    }

    private final byte topMostBlock;
    public final int x, y, z;

    public FertileBlock(Material material, int x, int y, int z) {
        this.topMostBlock = FertileBlock.getByteForMaterial(material);
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Material getMaterial() {
        return FertileBlock.getMaterialForByte(this.topMostBlock);
    }

    private static byte getByteForMaterial(Material mat) {
        if (MATERIAL_TO_BYTE.containsKey(mat)) {
            return MATERIAL_TO_BYTE.get(mat);
        }

        throw new IllegalArgumentException("Invalid material: " + mat);
    }

    private static Material getMaterialForByte(byte b) {
        return MATERIALS[b];
    }

    public static boolean isMaterialValid(Material mat) {
        return MATERIAL_TO_BYTE.containsKey(mat);
    }
}
