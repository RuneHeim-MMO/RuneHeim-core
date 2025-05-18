package org.zuma.examplePlugin.domain.block.base;

import org.bukkit.Material;
import org.zuma.examplePlugin.domain.item.ItemDrop;

public class OreBlockMeta {
    private final String label;
    private final Material material;
    private final int minLevel;
    private final int baseMiningDuration;
    private final int respawnDurationSecs;
    private final ItemDrop drop;

    public OreBlockMeta(String label, Material material, int minLevel, int baseMiningDurationMs, int respawnDurationSecs, ItemDrop drop) {
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
