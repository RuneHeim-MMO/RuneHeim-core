package org.zuma.examplePlugin.domain.block.impl;

import eu.decentsoftware.holograms.api.DHAPI;
import org.bukkit.Material;
import org.zuma.examplePlugin.core.utils.TextUtils;
import org.zuma.examplePlugin.domain.block.base.AbstractInteractableBlock;
import org.zuma.examplePlugin.domain.block.meta.AbstractInteractableBlockMeta;
import org.zuma.examplePlugin.domain.map.MarkerType;

import java.util.*;

public class InvisibleMarker extends AbstractInteractableBlock {
    private final List<String> hologramContent;

    public InvisibleMarker(AbstractInteractableBlockMeta meta, MarkerType markerType, String markerLabel) {
        super(meta, markerType, markerLabel);
        this.hologramContent = new ArrayList<>();
    }

    private void initializeHologram() {
        setHologram(DHAPI.createHologram(UUID.randomUUID().toString(), getHologramLocation(), hologramContent));
    }

    public void initializeBlock(List<String> hologramContent) {
        this.hologramContent.addAll(hologramContent);
        initializeHologram();
        super.spawn(Material.AIR);
    }

    @Override
    public String getDescription() {
        if (hologramContent.size() < 2) {
            return "";
        }
        // Replace icons with nothing, remove first line (title)
        return TextUtils.replaceAllIcons(String.join("<br/>", hologramContent.subList(1, hologramContent.size())));
    }
}
