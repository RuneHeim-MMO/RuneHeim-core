package org.zuma.examplePlugin.domain.gui.impl.individual.house;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.constants.Color;
import org.zuma.examplePlugin.core.constants.SoundType;
import org.zuma.examplePlugin.domain.gui.base.PaginatedIndividualGUI;
import org.zuma.examplePlugin.domain.gui.constants.GUIControl;
import org.zuma.examplePlugin.domain.housing.instance.HouseInstance;
import org.zuma.examplePlugin.domain.housing.HousingRegion;
import org.zuma.examplePlugin.domain.hud.HudService;
import org.zuma.examplePlugin.domain.player.RunePlayer;
import org.zuma.examplePlugin.events.housing.HouseSettingsUpdateEvent;

import java.util.*;

public class HouseDiscoverer extends PaginatedIndividualGUI {
    private static final Set<Integer> IGNORED_SLOTS = new HashSet<>();
    private final HousingRegion region;
    private RunePlayer viewer;

    public HouseDiscoverer(String title, int id, HousingRegion region) {
        super(title, id, 6, IGNORED_SLOTS);
        this.region = region;
    }

    private ItemStack getHouseItemStack(HouseInstance house) {
        ItemStack itemStack = new ItemStack(Material.PAPER);
        ItemMeta meta = itemStack.getItemMeta();
        List<Component> lore = new ArrayList<>();

        meta.displayName(Component.text(house.getOwnerName() + "'s House").decoration(TextDecoration.ITALIC, false));
        lore.add(Component.text("Owner: ", Color.ITEM_LORE_DESCRIPTION.asColor()).append(Component.text(house.getOwnerName(), Color.ACCENT.asColor())).decoration(TextDecoration.ITALIC, false));
        lore.add(Component.text("Type: ", Color.ITEM_LORE_DESCRIPTION.asColor()).append(Component.text(house.getTemplate().getTemplateName(), Color.ACCENT.asColor())).decoration(TextDecoration.ITALIC, false));
        lore.add(Component.text("Visitors: ", Color.ITEM_LORE_DESCRIPTION.asColor()).append(Component.text(house.getCurrentVisitors().size(), Color.ACCENT.asColor())).decoration(TextDecoration.ITALIC, false));
        lore.add(Component.empty());
        lore.add(Component.text("Click to teleport to this house.", Color.SUCCESS.asColor()).decoration(TextDecoration.ITALIC, false));

        meta.lore(lore);
        itemStack.setItemMeta(meta);
        assignMenuValue(itemStack, house.getOwnerUUID().toString());

        return itemStack;
    }

    private void updateInventory(RunePlayer player) {
        List<HouseInstance> houses = RuneQuestPlugin.getHousingManager().getAllRegionHouses(region);

        if (RuneQuestPlugin.getHousingManager().playHasHouseInRegion(player.getUUID(), region)) {
            HashMap<Integer, ItemStack> overrides = new HashMap<>();
            ItemStack stack = GUIControl.HOUSE_BUTTON.asItemstack(Component.text("Teleport to Your House"));
            assignMenuValue(stack, "teleport-own-house");

            overrides.put(45, stack);
            setOverrideItems(overrides, true);
        }

        List<ItemStack> content = houses.stream().filter(h -> h.canJoin(player) && !h.getOwnerUUID().equals(player.getUUID())).map(this::getHouseItemStack).toList();
        setContent(content, true);
        getBaseInventory();
    }

    @Override
    public boolean attemptOpenInventory(RunePlayer player, Runnable onFail) {
        this.viewer = player;
        updateInventory(viewer);
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

        String menuValue = getMenuValueStr(currentItem);

        if (menuValue == null) {
            return;
        }

        SoundType.UI_CLICK.play(player, false);

        if (menuValue.equals("teleport-own-house")) {
            HouseInstance instance = RuneQuestPlugin.getHousingManager().getHouseInstanceByOwner(player.getUUID(), region);

            if (instance == null) {
                RuneQuestPlugin.getHousingManager().teleportToOwnHouse(player, region);
            }

            if (instance != null) {
                try {
                    instance.teleportToHouse(player);
                } catch (Exception e) {
                    HudService.sendFailMessage(player, Component.text(e.getMessage()));
                }
            }

            return;
        }

        HouseInstance instance = RuneQuestPlugin.getHousingManager().getHouseInstanceByOwner(UUID.fromString(menuValue), region);

        if (instance != null) {
            try {
                instance.teleportToHouse(player);
            } catch (Exception e) {
                HudService.sendFailMessage(player, Component.text(e.getMessage()));
            }
        }
    }

    @Override
    public int getPaginationControlsOffset() {
        return 0;
    }

    @EventHandler
    public void onHouseUpdate(HouseSettingsUpdateEvent event) {
        if (!event.getInstance().getTemplate().getRegion().equals(this.region)) {
            return;
        }

        updateInventory(viewer);
    }

}
