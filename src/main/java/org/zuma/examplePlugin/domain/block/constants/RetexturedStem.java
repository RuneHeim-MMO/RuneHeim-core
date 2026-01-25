package org.zuma.examplePlugin.domain.block.constants;

import org.bukkit.Material;

// //replacenear 3000 wheat[age=0|1|2|3|4],potatoes[age=0|2|4],carrots[age=0|2] air

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
