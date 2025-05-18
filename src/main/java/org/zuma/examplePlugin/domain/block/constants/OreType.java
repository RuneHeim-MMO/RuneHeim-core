package org.zuma.examplePlugin.domain.block.constants;

import org.bukkit.Material;
import org.zuma.examplePlugin.domain.item.ItemDrop;
import org.zuma.examplePlugin.domain.item.constants.ItemId;

public enum OreType {
    IRON_ORE(0, "Iron ore", org.bukkit.Material.IRON_ORE, 1, 3000, 5, new ItemDrop(ItemId.IRON_ORE, 1));

    private final int id;
    private final String label;
    private final Material material;
    private final int minLevel;
    private final int baseMiningDuration;
    private final int respawnDurationSecs;
    private final ItemDrop drop;

    OreType(int id, String label, Material material, int minLevel, int baseMiningDurationMs, int respawnDurationSecs, ItemDrop drop) {
        this.id = id;
        this.label = label;
        this.material = material;
        this.minLevel = minLevel;
        this.baseMiningDuration = baseMiningDurationMs;
        this.respawnDurationSecs = respawnDurationSecs;
        this.drop = drop;
    }

    public Material getMaterial() {
        return material;
    }

    public int getId() {
        return id;
    }

    public int getBaseMiningDuration() {
        return baseMiningDuration;
    }

    public String getLabel() {
        return label;
    }

    public int getRespawnDurationSeconds() {
        return respawnDurationSecs;
    }

    public int getMinLevel() {
        return minLevel;
    }

    public ItemDrop getDrop() {
        return drop;
    }
}
