package org.zuma.examplePlugin.domain.block.impl;

import eu.decentsoftware.holograms.api.DHAPI;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.constants.Color;
import org.zuma.examplePlugin.core.interfaces.InventoryOpenerBlock;
import org.zuma.examplePlugin.core.interfaces.Restrictable;
import org.zuma.examplePlugin.core.utils.PlayerUtils;
import org.zuma.examplePlugin.core.utils.TextUtils;
import org.zuma.examplePlugin.domain.block.base.AbstractInteractableBlock;
import org.zuma.examplePlugin.domain.block.meta.AbstractInteractableBlockMeta;
import org.zuma.examplePlugin.domain.hud.HudService;
import org.zuma.examplePlugin.domain.item.base.AbstractItemType;
import org.zuma.examplePlugin.domain.map.MarkerType;
import org.zuma.examplePlugin.domain.player.RunePlayer;
import org.zuma.examplePlugin.domain.skill.SkillRequirement;

import javax.annotation.Nullable;
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
        // Replace icons with nothing, remove first line (title)
        return TextUtils.replaceAllIcons(String.join("<br/>", hologramContent.subList(1, hologramContent.size())));
    }
}
