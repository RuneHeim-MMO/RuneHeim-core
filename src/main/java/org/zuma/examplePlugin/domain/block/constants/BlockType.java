package org.zuma.examplePlugin.domain.block.constants;

import org.bukkit.Material;

public enum BlockType {
    ResourceNode(0, Material.IRON_ORE),
    InventoryOpener(1, Material.BARRIER),
    InventoryOpenerModelled(2, Material.BARRIER),
    Altar(3, Material.LECTERN),
    Door(4, Material.IRON_BARS),
    ItemTrigger(5, Material.DROPPER),
    TeleportDoor(6, Material.IRON_DOOR),
    InvisibleMarker(7, Material.AIR),
    CommandBlock(8, Material.LODESTONE),
    LightableBlock(9, Material.REDSTONE_LAMP),
    CombatDummyFurniture(10, Material.ARMOR_STAND),
    CropResourceNode(11, Material.WHEAT),
    ButtonTrigger(12, Material.STONE_BUTTON);

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
