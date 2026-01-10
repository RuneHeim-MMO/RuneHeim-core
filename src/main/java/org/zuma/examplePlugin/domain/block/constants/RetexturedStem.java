package org.zuma.examplePlugin.domain.block.constants;

import org.bukkit.Material;

public enum RetexturedStem {
    DEWLEAF(0, Material.WHEAT, false),
    SUNROOT(1, Material.WHEAT, false),
    MOONSHADE(2, Material.WHEAT, false),
    CRIMSON_THISTLE(3, Material.WHEAT, false),
    FROSTCAP(4, Material.WHEAT, false),
    GLOWFERN(0, Material.POTATOES, false),
    STORMVINE(2, Material.POTATOES, false),
    BLOODORCHID(4, Material.POTATOES, false),
    DRAGONS_BREATH(0, Material.CARROTS, false),
    ETHEREAL_BLOOM(2, Material.CARROTS, false);


    private final int age;
    private final Material stemMaterial;
    private final boolean needsFarmland;

    RetexturedStem(int age, Material stemMaterial, boolean needsFarmland) {
        this.age = age;
        this.stemMaterial = stemMaterial;
        this.needsFarmland = needsFarmland;
    }

    public int getAge() {
        return age;
    }

    public Material getStemMaterial() {
        return stemMaterial;
    }

    public boolean isNeedsFarmland() {
        return needsFarmland;
    }
}
