package org.zuma.examplePlugin.domain.gui.impl.individual;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.constants.Color;
import org.zuma.examplePlugin.domain.gui.base.IndividualGUI;
import org.zuma.examplePlugin.domain.gui.constants.GUIControl;
import org.zuma.examplePlugin.domain.hud.HudService;
import org.zuma.examplePlugin.domain.item.base.AbstractItemType;
import org.zuma.examplePlugin.domain.item.base.SocketedEquipableItemType;
import org.zuma.examplePlugin.domain.item.constants.ItemId;
import org.zuma.examplePlugin.domain.item.constants.ItemType;
import org.zuma.examplePlugin.domain.item.impl.Gem;
import org.zuma.examplePlugin.domain.item.state.base.AbstractState;
import org.zuma.examplePlugin.domain.item.state.states.GearGemSocketsState;
import org.zuma.examplePlugin.domain.player.RunePlayer;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FusionMasterInventory extends IndividualGUI {
    private final ItemStack transparentItem = GUIControl.TRANSPARENT.asItemstack();
    private final ItemStack blankItem = GUIControl.BLANK.asItemstack();
    private final ItemStack confirmItem = GUIControl.YES.asItemstack(Component.text("Confirm fusion", Color.SUCCESS.asColor()));
    private final ItemStack cancelItem = GUIControl.NO.asItemstack(Component.text("Cancel", Color.ERROR.asColor()));

    private final static int GEAR_SLOT = 4;
    private final static int SCROLL_SLOT = 22;
    private final static List<Integer> GEM_SLOTS = new ArrayList<>(List.of(21, 23, 30, 32));
    private final static Set<Integer> ACTIVE_GEM_SLOTS = new HashSet<>();

    private final static int CONFIRM_SLOT = 50;
    private final static int CANCEL_SLOT = 48;
    private final static int INFO_SLOT = 53;

    private final Inventory inventory;
    private ItemStack previousPlacedGear = null;

    public FusionMasterInventory(String title, int id) {
        super(title, id, 6);
        inventory = getBaseInventory();
    }

    private boolean isInventoryItem(ItemStack item) {
        return item.isSimilar(transparentItem) || item.isSimilar(confirmItem) || item.isSimilar(cancelItem) || item.isSimilar(blankItem);
    }

    private void updateInventory(RunePlayer player) {
        ItemStack placedItemstack = inventory.getItem(GEAR_SLOT);
        int availableGemSlots = 0;
        GearGemSocketsState socketState = null;

        for (int i = 0; i < inventory.getSize(); i++) {

            if (i == GEAR_SLOT || i == SCROLL_SLOT || GEM_SLOTS.contains(i)) {
                continue;
            }

            inventory.setItem(i, transparentItem);
        }


        if (previousPlacedGear == null || previousPlacedGear.isSimilar(placedItemstack)) {
            System.out.println("DIFF");
            if (placedItemstack != null) {
                AbstractState state = RuneQuestPlugin.getItemStateManager().getItemState(placedItemstack);
                if (state instanceof GearGemSocketsState socketsState) {
                    availableGemSlots = socketsState.getAvailableGemSlots();
                    socketState = socketsState;
                }
            }

            ACTIVE_GEM_SLOTS.clear();
            for (int i = 0; i < availableGemSlots; i++) {
                ACTIVE_GEM_SLOTS.add(GEM_SLOTS.get(i));
            }

            for (int slot : GEM_SLOTS) {
                inventory.clear(slot);
                if (!ACTIVE_GEM_SLOTS.contains(slot)) {
                    ItemStack slotItem = inventory.getItem(slot);
                    if (slotItem != null && !isInventoryItem(slotItem)) {
                        player.giveItemOrDrop(slotItem);
                    }
                    inventory.setItem(slot, blankItem);
                }
            }

        }


        if (isReadyToFuse(socketState)) {
            inventory.setItem(CONFIRM_SLOT, confirmItem);
        } else {
            inventory.setItem(CONFIRM_SLOT, blankItem);
        }

        previousPlacedGear = placedItemstack;
    }

    private boolean isReadyToFuse(GearGemSocketsState socketState) {
        ItemStack placedItemstack = inventory.getItem(GEAR_SLOT);
        ItemStack scrollItemStack = inventory.getItem(SCROLL_SLOT);

        if (scrollItemStack == null) return false;
        AbstractItemType scroll = RuneQuestPlugin.getItemRegistry().getItem(scrollItemStack);
        if (placedItemstack == null) return false;

        AbstractItemType placedItem = RuneQuestPlugin.getItemRegistry().getItem(placedItemstack);

        if (!(placedItem instanceof SocketedEquipableItemType)) {
            return false;
        }

        if (scroll == null || scroll.id != ItemId.FUSION_SCROLL.asInt()) {
            return false;
        }

        for (int slot : ACTIVE_GEM_SLOTS) {
            ItemStack gemStack = inventory.getItem(slot);
            if (gemStack == null) continue;

            AbstractItemType gem = RuneQuestPlugin.getItemRegistry().getItem(scrollItemStack);
            if (gem == null) return false;
            if (!(gem instanceof Gem)) return false;
        }

        if (socketState == null) return false;


        return true;
    }

    @Override
    public void openInventory(RunePlayer player) {

        updateInventory(player);

        super.assignMenuValue(confirmItem, 1);
        super.assignMenuValue(cancelItem, 0);

        inventory.setItem(CANCEL_SLOT, cancelItem);
        inventory.setItem(INFO_SLOT, GUIControl.INFO.asItemstack(Component.text("Info", NamedTextColor.WHITE), List.of(Component.text("Some info here"))));

        player.asBukkitPlayer().openInventory(inventory);
    }

    @Override
    public void onInventoryClick(InventoryClickEvent event, int slot, RunePlayer player) {
        ItemStack currentItem = event.getCurrentItem();
        event.setCancelled(true);

        if (event.getSlot() == GEAR_SLOT || ACTIVE_GEM_SLOTS.contains(event.getSlot()) || event.getSlot() == SCROLL_SLOT || event.isShiftClick()) {
            Bukkit.getScheduler().runTaskLater(RuneQuestPlugin.getInstance(), () -> updateInventory(player), 2L);
        }

        if (currentItem == null) {
            return;
        }

        Integer menuValue = getMenuValue(currentItem);

        if (menuValue != null && menuValue == 0) {
            // Todo: give player back items
            player.asBukkitPlayer().closeInventory();
            return;
        }

        if (menuValue != null && menuValue == 1) {
            // Todo: fusion
            return;
        }

        AbstractItemType itemType = RuneQuestPlugin.getItemRegistry().getItem(currentItem);

        if (itemType != null) {
            List<ItemType> socketedTypes = List.of(ItemType.EQUIPMENT_WITH_GEM_SOCKETS, ItemType.WEAPON_WITH_GEM_SOCKETS);

            if (socketedTypes.contains(itemType.type) || itemType.type.equals(ItemType.GEM) || itemType.id == ItemId.FUSION_SCROLL.asInt()) {
                return;
            }

            HudService.sendFailMessage(player, Component.text("Fusion master accepts only items with sockets, gems or fusion scrolls."));
            event.setCancelled(true);
        }


    }
}
