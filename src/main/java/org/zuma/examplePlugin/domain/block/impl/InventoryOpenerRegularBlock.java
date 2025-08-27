package org.zuma.examplePlugin.domain.block.impl;

import eu.decentsoftware.holograms.api.DHAPI;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.constants.Color;
import org.zuma.examplePlugin.core.interfaces.SkillRestrictable;
import org.zuma.examplePlugin.core.utils.PlayerUtils;
import org.zuma.examplePlugin.domain.block.base.AbstractInteractableBlock;
import org.zuma.examplePlugin.domain.block.meta.AbstractInteractableBlockMeta;
import org.zuma.examplePlugin.domain.hud.HudService;
import org.zuma.examplePlugin.domain.item.base.AbstractItemType;
import org.zuma.examplePlugin.domain.map.MarkerType;
import org.zuma.examplePlugin.domain.player.RunePlayer;
import org.zuma.examplePlugin.domain.skill.SkillRequirement;

import javax.annotation.Nullable;
import java.util.*;

public class InventoryOpenerRegularBlock extends AbstractInteractableBlock implements SkillRestrictable, org.zuma.examplePlugin.core.interfaces.InventoryOpenerBlock {
    private int inventoryId;
    private final List<UUID> createdInventoryInstances;
    private List<SkillRequirement> requirements;
    private String label, icon;

    public InventoryOpenerRegularBlock(AbstractInteractableBlockMeta meta) {
        super(meta, null, null);
        createdInventoryInstances = new ArrayList<>();
    }

    private void initializeHologram() {
        String icon = this.icon == null ? "" : "§f" + this.icon + " ";

        List<String> hologramContent = new ArrayList<>(List.of(
                icon + Color.INTERACTABLE_ENTITY.asSpigotHex() + label
        ));

        if (requirements != null) {
            requirements.forEach(req -> hologramContent.add(Color.ACCENT.asSpigotHex() +
                    "Req. " + req.type().getSymbol() + " " + req.minimumLevel() + " Level"));
        }

        setHologram(DHAPI.createHologram(UUID.randomUUID().toString(), getHologramLocation(), hologramContent));
    }

    public void forceCloseOpenedInventories() {
        Set<UUID> copy = new HashSet<>(createdInventoryInstances);
        copy.forEach(uuid -> RuneQuestPlugin.getGUIHandler().forceCloseOpenedInventories(uuid));
        createdInventoryInstances.clear();
    }

    public void removeInventoryUUID(UUID instanceUUID) {
        createdInventoryInstances.remove(instanceUUID);
    }

    @Override
    public void onPlayerInteractEvent(RunePlayer player, Action action, PlayerInteractEvent event, AbstractItemType itemInHand) {
        event.setCancelled(true);

        if (PlayerUtils.hasCustomInventoryOpen(player)) {
            return;
        }

        if (requirements != null && !player.meetsRequirements(this)) {
            HudService.sendFailMessage(player, Component.text("You do not meet requirements to use this!"));
            return;
        }

        UUID initializedInventoryUUID = RuneQuestPlugin.getGUIHandler().openInventory(inventoryId, player, null, null, this);
        createdInventoryInstances.add(initializedInventoryUUID);

    }


    public void initializeBlock(int inventoryId, List<SkillRequirement> requirements, String label, String icon, Material material, @Nullable MarkerType markerType, @Nullable String markerLabel) {
        this.inventoryId = inventoryId;
        this.requirements = requirements;
        this.label = label;
        this.icon = icon;
        super.setMarkerType(markerType);
        super.setMarkerLabel(markerLabel);


        initializeHologram();
        super.spawn(material);
    }

    @Override
    public List<SkillRequirement> getRequirements() {
        return requirements;
    }
}
