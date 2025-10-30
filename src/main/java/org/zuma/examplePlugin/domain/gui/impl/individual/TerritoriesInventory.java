package org.zuma.examplePlugin.domain.gui.impl.individual;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.constants.Color;
import org.zuma.examplePlugin.core.constants.Icon;
import org.zuma.examplePlugin.core.handlers.game.IllegalItemWatchdog;
import org.zuma.examplePlugin.core.utils.TextUtils;
import org.zuma.examplePlugin.domain.gui.base.PaginatedIndividualGUI;
import org.zuma.examplePlugin.domain.hud.HudService;
import org.zuma.examplePlugin.domain.player.RunePlayer;
import org.zuma.examplePlugin.domain.territory.Territory;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;


public class TerritoriesInventory extends PaginatedIndividualGUI {
    private final static Map<String, Territory> allTerritories = RuneQuestPlugin.getTerritoryHandler()
            .getTerritoryList()
            .stream()
            .collect(Collectors.toMap(
                    Territory::getId,
                    p -> p,
                    (existing, replacement) -> existing,
                    LinkedHashMap::new
            ));

    public TerritoriesInventory(String title, int id) {
        super(title, id, 6, Set.of(11, 12, 13, 14, 15, 20, 21, 22, 23, 24, 29, 30, 31, 32, 33));
    }

    private ItemStack getTerritoryDisplayItem(RunePlayer player, Territory territory) {
        boolean isUnlocked = player.getDiscoveredTerritoryIds().contains(territory.getId());

        ItemStack icon = new ItemStack(isUnlocked ? Material.FILLED_MAP : Material.GLASS_PANE);
        ItemMeta meta = icon.getItemMeta();

        List<Component> lore = new ArrayList<>(TextUtils.getLoreTerritoryDescription(player, territory));
        lore.add(Component.text("Click to set your compass location", Color.ACCENT.asColor()));

        meta.displayName(Icon.MAP.asComponent().append(
                Component.text(territory.getName())
                        .append(Component.text(" [").color(Color.ITEM_LORE_DESCRIPTION.asColor()).decoration(TextDecoration.ITALIC, false))
                        .append(Component.text(String.format("X: %.0f, Z: %.0f",
                                        territory.getCenterPoint().getX(),
                                        territory.getCenterPoint().getZ()))
                                .color(Color.INFO.asColor())
                                .decoration(TextDecoration.ITALIC, false))
                        .append(Component.text("]").color(Color.ITEM_LORE_DESCRIPTION.asColor()).decoration(TextDecoration.ITALIC, false)))
        );

        meta.lore(lore.stream().map(c -> c.decoration(TextDecoration.ITALIC, false)).toList());
        icon.setItemMeta(meta);
        IllegalItemWatchdog.tagIllegalItem(icon);

        return icon;
    }


    @Override
    public boolean attemptOpenInventory(RunePlayer player, Runnable onFail) {
        AtomicInteger index = new AtomicInteger(0);

        setContent(allTerritories.values().stream().map(territory -> {
                    ItemStack displayItem = getTerritoryDisplayItem(player, territory);
                    assignMenuValue(displayItem, index.intValue());
                    index.getAndIncrement();
                    return displayItem;
                }
        ).toList(), true);

        player.asBukkitPlayer().openInventory(getBaseInventory());
        return true;
    }

    @Override
    public void handleInventoryClick(InventoryClickEvent event, int slot, RunePlayer player) {
        event.setCancelled(true);

        ItemStack currentItem = event.getCurrentItem();

        if (currentItem == null) {
            return;
        }

        Integer menuValue = getMenuValue(currentItem);

        if (menuValue == null) {
            return;
        }

        List<Territory> territoriesList = new ArrayList<>(allTerritories.values());
        Territory territoryAtIndex = territoriesList.get(menuValue);

        if (territoryAtIndex == null) {
            return;
        }

        HudService.sendInfoMessage(player, Component.text("Set compass location to " + territoryAtIndex.getName() + "."));
        player.asBukkitPlayer().setCompassTarget(territoryAtIndex.getCenterPoint());

    }

    @Override
    public int getPaginationControlsOffset() {
        return 0;
    }

}
