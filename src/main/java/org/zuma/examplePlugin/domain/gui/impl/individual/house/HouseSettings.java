package org.zuma.examplePlugin.domain.gui.impl.individual.house;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.constants.Color;
import org.zuma.examplePlugin.core.constants.SoundType;
import org.zuma.examplePlugin.domain.gui.base.PaginatedIndividualGUI;
import org.zuma.examplePlugin.domain.gui.constants.GUIControl;
import org.zuma.examplePlugin.domain.head.HeadGenerator;
import org.zuma.examplePlugin.domain.housing.instance.HouseInstance;
import org.zuma.examplePlugin.domain.hud.HudService;
import org.zuma.examplePlugin.domain.player.RunePlayer;
import org.zuma.examplePlugin.events.housing.HouseSettingsUpdateEvent;

import java.util.*;

public class HouseSettings extends PaginatedIndividualGUI {
    private enum ViewMode {
        VISITORS, INVITATIONS
    }

    private static final Set<Integer> IGNORED_SLOTS = new HashSet<>();
    private UUID instanceUUID;
    private ViewMode mode;

    public HouseSettings(String title, int id) {
        super(title, id, 5, IGNORED_SLOTS);
        this.instanceUUID = null;
        this.mode = ViewMode.VISITORS;
    }

    private ItemStack getVisitorDisplayItem(RunePlayer runePlayer, HouseInstance houseInstance) {
        Player player = runePlayer.asBukkitPlayer();
        ItemStack icon = HeadGenerator.getPlayerHead(player);
        ItemMeta meta = icon.getItemMeta();

        List<Component> lore = new ArrayList<>();
        Component name = Component.text(player.getName(), NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false);
        meta.displayName(name);

        if (houseInstance.getOwnerUUID().equals(runePlayer.getUUID())) {
            lore.add(Component.text("Status: Owner", Color.SERVER_BRAND.asColor()));
        } else if (houseInstance.getInvitedPlayers().contains(runePlayer.getUUID())) {
            lore.add(Component.text("Status: Invited", Color.WARNING.asColor()));
            lore.add(Component.empty());
            lore.add(Component.text("Click to revoke invitation", Color.ERROR.asColor()));
        } else {
            lore.add(Component.text("Status: Currently visiting", Color.SUCCESS.asColor()));
            lore.add(Component.empty());
            lore.add(Component.text("Click to kick", Color.ERROR.asColor()));
        }

        meta.lore(lore.stream().map(c -> c.decoration(TextDecoration.ITALIC, false)).toList());
        icon.setItemMeta(meta);
        assignMenuValue(icon, "player_" + runePlayer.getUUID());

        return icon;
    }

    private void toggleMode() {
        this.mode = this.mode == ViewMode.VISITORS ? ViewMode.INVITATIONS : ViewMode.VISITORS;
        super.resetPage();
    }

    private void updateInventory() {
        HouseInstance houseInstance = RuneQuestPlugin.getHousingManager().getInstanceByUUID(instanceUUID);

        if (houseInstance == null) {
            return;
        }

        List<ItemStack> content = mode == ViewMode.VISITORS
                ? houseInstance.getCurrentVisitors().stream()
                .map(uuid -> RuneQuestPlugin.getPlayerManager().getRunePlayer(uuid))
                .filter(Objects::nonNull)
                .map(rp -> getVisitorDisplayItem(rp, houseInstance))
                .toList()
                : houseInstance.getInvitedPlayers().stream()
                .map(uuid -> RuneQuestPlugin.getPlayerManager().getRunePlayer(uuid))
                .filter(Objects::nonNull)
                .map(rp -> getVisitorDisplayItem(rp, houseInstance))
                .toList();

        HashMap<Integer, ItemStack> overrides = new HashMap<>();

        if (houseInstance.isOpenToPublic()) {
            ItemStack publicStatus = GUIControl.HOUSE_MODE_PUBLIC.asItemstack(Component.text("House is Open to Public", NamedTextColor.GREEN), List.of(Component.text("Players can join without an invitation.", Color.ITEM_LORE_DESCRIPTION.asColor())));
            overrides.put(36, publicStatus);
            assignMenuValue(publicStatus, "toggle-public-status");
        } else {
            ItemStack privateStatus = GUIControl.HOUSE_MODE_PRIVATE.asItemstack(Component.text("House is Private", NamedTextColor.RED), List.of(Component.text("Only invited players can join.", Color.ITEM_LORE_DESCRIPTION.asColor())));
            overrides.put(36, privateStatus);
            assignMenuValue(privateStatus, "toggle-public-status");
        }

        if (mode == ViewMode.VISITORS) {
            ItemStack kickAllButton = GUIControl.NO.asItemstack(Component.text("Kick All Visitors", Color.ERROR.asColor()), List.of(Component.text("Click to kick all visitors from the house.", Color.ITEM_LORE_DESCRIPTION.asColor())));
            assignMenuValue(kickAllButton, "kick-all");
            overrides.put(37, kickAllButton);
        }

        if (mode == ViewMode.VISITORS) {
            ItemStack toggleButton = GUIControl.VISIBILITY.asItemstack(Component.text("Switch to Invitations View", NamedTextColor.YELLOW), List.of(Component.text("Click to view and manage invited players.", Color.ITEM_LORE_DESCRIPTION.asColor())));
            assignMenuValue(toggleButton, "toggle-mode");
            overrides.put(38, toggleButton);
        } else {
            ItemStack toggleButton = GUIControl.VISIBILITY.asItemstack(Component.text("Switch to Visitors View", NamedTextColor.YELLOW), List.of(Component.text("Click to view and manage current visitors.", Color.ITEM_LORE_DESCRIPTION.asColor())));
            assignMenuValue(toggleButton, "toggle-mode");
            overrides.put(38, toggleButton);
        }

        setOverrideItems(overrides, true);
        setContent(content, true);
        getBaseInventory();
    }

    @Override
    public boolean attemptOpenInventory(RunePlayer player, Runnable onFail) {
        HouseInstance houseInstance = RuneQuestPlugin.getHousingManager().getVisitingHouse(player);

        if (houseInstance == null || !houseInstance.getOwnerUUID().equals(player.getUUID())) {
            HudService.sendFailMessage(player, Component.text("You must be inside your own house to use this."));
            return false;
        }

        this.instanceUUID = houseInstance.getInstanceUUID();
        updateInventory();
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
        HouseInstance houseInstance = RuneQuestPlugin.getHousingManager().getInstanceByUUID(instanceUUID);

        if (houseInstance == null) {
            return;
        }

        SoundType.UI_CLICK.play(player, false);

        if (menuValue.equals("kick-all")) {
            try {
                player.asBukkitPlayer().performCommand("house kickall");
                updateInventory();
            } catch (Exception e) {
                HudService.sendFailMessage(player, Component.text(e.getMessage()));
            }
            return;
        }

        if (menuValue.equals("toggle-mode")) {
            toggleMode();
            updateInventory();
            return;
        }

        if (menuValue.equals("toggle-public-status")) {
            houseInstance.setOpenToPublic(!houseInstance.isOpenToPublic());
            updateInventory();
            return;
        }

        if (menuValue.startsWith("player_")) {
            UUID targetUUID = UUID.fromString(menuValue.substring(7));

            try {
                if (mode == ViewMode.VISITORS) {
                    houseInstance.kickVisitor(targetUUID, true);
                } else {
                    houseInstance.uninvitePlayer(targetUUID);
                }
                updateInventory();
            } catch (Exception e) {
                HudService.sendFailMessage(player, Component.text(e.getMessage()));
            }
        }

    }

    @Override
    public int getPaginationControlsOffset() {
        return 3;
    }

    @EventHandler
    public void onHouseUpdate(HouseSettingsUpdateEvent event) {
        if (!event.getInstance().getInstanceUUID().equals(this.instanceUUID)) {
            return;
        }

        updateInventory();
    }

}
