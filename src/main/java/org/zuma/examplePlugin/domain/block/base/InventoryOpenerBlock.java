package org.zuma.examplePlugin.domain.block.base;

import eu.decentsoftware.holograms.api.DHAPI;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.constants.Colors;
import org.zuma.examplePlugin.core.interfaces.SkillRestrictable;
import org.zuma.examplePlugin.domain.block.meta.AbstractInteractableBlockMeta;
import org.zuma.examplePlugin.domain.hud.HudService;
import org.zuma.examplePlugin.domain.item.base.AbstractItem;
import org.zuma.examplePlugin.domain.player.RunePlayer;
import org.zuma.examplePlugin.domain.skill.SkillRequirement;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class InventoryOpenerBlock extends AbstractInteractableBlock implements SkillRestrictable {
    private int inventoryId;
    private List<SkillRequirement> requirements;
    private String label;

    public InventoryOpenerBlock(AbstractInteractableBlockMeta meta) {
        super(meta, null, null);
    }


    private void initializeHologram() {
        List<String> hologramContent = new ArrayList<>(List.of(
                Colors.INTERACTABLE_ENTITY.asSpigotHex() + label
        ));

        if (requirements != null) {
            requirements.forEach(req -> hologramContent.add(Colors.ACCENT.asSpigotHex() +
                    "Req. " + req.type().getSymbol() + " " + req.minimumLevel() + " Level"));
        }

        setHologram(DHAPI.createHologram(UUID.randomUUID().toString(), getHologramLocation(), hologramContent));
    }

    @Override
    public void onPlayerInteractEvent(RunePlayer player, Action action, PlayerInteractEvent event, AbstractItem itemInHand) {
        event.setCancelled(true);

        if (requirements != null && !player.meetsRequirements(this)) {
            HudService.sendFailMessage(player, Component.text("You do not meet requirements to use this!"));
            return;
        }

        RuneQuestPlugin.getGUIHandler().openInventory(inventoryId, player);
    }


    public void initializeBlock(int inventoryId, List<SkillRequirement> requirements, String label, Material material) {
        this.inventoryId = inventoryId;
        this.requirements = requirements;
        this.label = label;

        initializeHologram();
        super.spawn(material);
    }

    @Override
    public List<SkillRequirement> getRequirements() {
        return requirements;
    }
}
