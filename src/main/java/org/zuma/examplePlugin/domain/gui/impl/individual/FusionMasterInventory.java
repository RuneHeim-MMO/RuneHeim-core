package org.zuma.examplePlugin.domain.gui.impl.individual;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.constants.Color;
import org.zuma.examplePlugin.core.constants.SoundType;
import org.zuma.examplePlugin.core.logger.constants.LogLevel;
import org.zuma.examplePlugin.core.utils.CurrencyUtils;
import org.zuma.examplePlugin.core.utils.InventoryUtils;
import org.zuma.examplePlugin.core.utils.ItemUtils;
import org.zuma.examplePlugin.domain.gui.base.IndividualGUI;
import org.zuma.examplePlugin.domain.gui.constants.GUIControl;
import org.zuma.examplePlugin.domain.hud.HudService;
import org.zuma.examplePlugin.domain.item.base.AbstractItemType;
import org.zuma.examplePlugin.domain.item.constants.ItemId;
import org.zuma.examplePlugin.domain.item.constants.ItemType;
import org.zuma.examplePlugin.domain.item.state.base.AbstractState;
import org.zuma.examplePlugin.domain.item.state.states.GearGemSocketsState;
import org.zuma.examplePlugin.domain.player.RunePlayer;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FusionMasterInventory extends IndividualGUI {
    private final int COST_PER_GEM = 2500;
    private final ItemStack blankItem = GUIControl.BLANK.asItemstack();
    private final ItemStack cancelItem = GUIControl.NO.asItemstack(Component.text("Cancel", Color.ERROR.asColor()));

    private final static int SOCKETABLE_SLOT = 4;
    private final static int SCROLL_SLOT = 22;
    private final static List<Integer> GEM_SLOTS = new ArrayList<>(List.of(21, 23, 30, 32));
    private final static Set<Integer> ACTIVE_GEM_SLOTS = new HashSet<>();

    private final static int CONFIRM_SLOT = 50;
    private final static int CANCEL_SLOT = 48;
    private final static int INFO_SLOT = 53;

    private final Inventory inventory;

    private enum ItemStackType {
        SOCKETABLE, GEM, SCROLL
    }

    public FusionMasterInventory(String title, int id) {
        super(title, id, 6);
        inventory = getBaseInventory();
    }

    private void updateAvailableGemSlots(int availableGemSlots, RunePlayer player) {
        ACTIVE_GEM_SLOTS.clear();
        for (int i = 0; i < availableGemSlots; i++) {
            ACTIVE_GEM_SLOTS.add(GEM_SLOTS.get(i));
        }

        for (int slot : GEM_SLOTS) {
            if (GUIControl.isGuiItem(inventory.getItem(slot))) {
                inventory.setItem(slot, null);
            }
            if (!ACTIVE_GEM_SLOTS.contains(slot)) {
                ItemStack slotItem = inventory.getItem(slot);
                if (slotItem != null && !GUIControl.isGuiItem(slotItem)) {
                    player.giveItemOrDrop(slotItem);
                }
                inventory.setItem(slot, blankItem);
            }
        }
    }

    private int getValidGemsCount() {
        int validGemsCount = 0;

        for (int slot : ACTIVE_GEM_SLOTS) {
            if (inventory.getItem(slot) != null && isValidItemStack(ItemStackType.GEM, inventory.getItem(slot))) {
                validGemsCount++;
            }
        }

        return validGemsCount;
    }

    @Nullable
    private GearGemSocketsState getSocketableSlotState() {
        ItemStack gearSlotItem = inventory.getItem(SOCKETABLE_SLOT);

        if (gearSlotItem != null) {
            AbstractState state = RuneQuestPlugin.getItemStateManager().getItemState(gearSlotItem);
            if (state instanceof GearGemSocketsState socketsState) {
                return socketsState;
            }
        }

        return null;
    }

    private void updateInventory(RunePlayer player) {
        GearGemSocketsState socketState = getSocketableSlotState();

        if (socketState == null) {
            updateAvailableGemSlots(0, player);
            return;
        }

        updateAvailableGemSlots(socketState.getAvailableGemSlots(), player);

        if (isReadyToFuse(socketState)) {
            int validGemsCount = getValidGemsCount();

            ItemStack confirmItem = GUIControl.YES.asItemstack(Component.text("Confirm fusion", Color.SUCCESS.asColor()),
                    List.of(Component.text("Fusion cost: ", NamedTextColor.WHITE).append(CurrencyUtils.getCoinsCurrencyComponent((long) validGemsCount * COST_PER_GEM))));
            super.assignMenuValue(confirmItem, 1);

            inventory.setItem(CONFIRM_SLOT, confirmItem);
        } else {
            inventory.setItem(CONFIRM_SLOT, blankItem);
        }

    }

    private boolean isReadyToFuse(GearGemSocketsState socketState) {
        int validGemsCount = 0;

        for (int slot : ACTIVE_GEM_SLOTS) {
            if (inventory.getItem(slot) != null && isValidItemStack(ItemStackType.GEM, inventory.getItem(slot))) {
                validGemsCount++;
            }
        }

        if (validGemsCount == 0) {
            return false;
        }

        boolean hasValidScroll = isValidItemStack(ItemStackType.SCROLL, inventory.getItem(SCROLL_SLOT));
        if (!hasValidScroll) {
            return false;
        }

        boolean hasValidWeapon = isValidItemStack(ItemStackType.SOCKETABLE, inventory.getItem(SOCKETABLE_SLOT));
        if (!hasValidWeapon) {
            return false;
        }

        if (socketState.getAvailableGemSlots() < validGemsCount) {
            return false;
        }

        return true;

    }

    private boolean isValidItemStack(ItemStackType type, @Nullable ItemStack itemStack) {
        if (itemStack == null) {
            return false;
        }

        AbstractItemType itemType = RuneQuestPlugin.getItemRegistry().getType(itemStack);
        List<ItemType> socketedTypes = List.of(ItemType.EQUIPMENT_WITH_GEM_SOCKETS, ItemType.MELEE_WEAPON_WITH_GEM_SOCKETS, ItemType.RANGED_WEAPON_WITH_SOCKETS, ItemType.TOOL_WITH_GEM_SOCKETS);

        if (itemType == null) {
            return false;
        }


        return switch (type) {
            case SCROLL -> itemType.id == ItemId.FUSION_SCROLL.asInt();
            case GEM -> itemType.type.equals(ItemType.GEM);
            case SOCKETABLE -> socketedTypes.contains(itemType.type);
        };

    }

    @Override
    public boolean attemptOpenInventory(RunePlayer player, Runnable onFail) {
        updateInventory(player);

        super.assignMenuValue(cancelItem, 0);

        inventory.setItem(CANCEL_SLOT, cancelItem);
        inventory.setItem(INFO_SLOT, GUIControl.INFO.asItemstack(Component.text("Info", NamedTextColor.WHITE), List.of(
                Component.text("• Gems can be fused into ", NamedTextColor.GRAY)
                        .append(Component.text("weapons, tools, and armor", NamedTextColor.AQUA, TextDecoration.BOLD))
                        .append(Component.text(", unlocking powerful enhancements.", NamedTextColor.GRAY)),
                Component.text("• These enhancements grant bonuses like ", NamedTextColor.GRAY)
                        .append(Component.text("increased accuracy, defense,", NamedTextColor.GOLD, TextDecoration.BOLD))
                        .append(Component.text(" or special effects.", NamedTextColor.GRAY)),
                Component.text("• To perform a fusion, you need at least ", NamedTextColor.GRAY)
                        .append(Component.text("one gem", NamedTextColor.LIGHT_PURPLE, TextDecoration.BOLD))
                        .append(Component.text(" and a ", NamedTextColor.GRAY))
                        .append(Component.text("fusion scroll", NamedTextColor.DARK_PURPLE, TextDecoration.BOLD))
                        .append(Component.text(".", NamedTextColor.GRAY)),
                Component.text("• Once fused, gems are ", NamedTextColor.GRAY)
                        .append(Component.text("bound to the item", NamedTextColor.RED, TextDecoration.BOLD))
                        .append(Component.text(" and ", NamedTextColor.GRAY))
                        .append(Component.text("cannot be removed", NamedTextColor.RED, TextDecoration.BOLD))
                        .append(Component.text(", so choose wisely!", NamedTextColor.GRAY)),
                Component.empty(),
                Component.text("• RuneHeim", Color.SERVER_BRAND.asColor())
        )));


        player.asBukkitPlayer().openInventory(inventory);
        return true;
    }

    @Override
    public void onInventoryClick(InventoryClickEvent event, int slot, RunePlayer player) {
        Inventory topInv = event.getView().getTopInventory();
        Inventory bottomInv = event.getView().getBottomInventory();
        ItemStack currentItemOrig = event.getCurrentItem();

        event.setCancelled(true);

        if (currentItemOrig == null) return;

        ItemStack currentItem = currentItemOrig.clone();
        Integer menuValue = getMenuValue(currentItem);

        if (handleMenuClick(menuValue, player)) return;

        if (event.getClickedInventory() == topInv) {
            handleTopInventoryClick(event, player, currentItem);
        } else if (event.getClickedInventory() == bottomInv) {
            handleBottomInventoryClick(event, player, currentItem, topInv, bottomInv);
        }
    }

    private boolean handleMenuClick(Integer menuValue, RunePlayer player) {
        if (menuValue == null) return false;

        if (menuValue == 0) {
            player.asBukkitPlayer().closeInventory();
            return true;
        }

        if (menuValue == 1) {
            processFusion(player);
            return true;
        }

        return false;
    }

    private void handleTopInventoryClick(InventoryClickEvent event, RunePlayer player, ItemStack currentItem) {
        if (!GUIControl.isGuiItem(currentItem)) {
            event.setCurrentItem(null);
            player.giveItemOrDrop(currentItem);
            scheduleInventoryUpdate(player);
        }
    }

    private void handleBottomInventoryClick(InventoryClickEvent event, RunePlayer player, ItemStack currentItem,
                                            Inventory topInv, Inventory bottomInv) {
        if (!isAcceptedItem(currentItem)) {
            HudService.sendFailMessage(player, Component.text("Fusion master accepts only items with sockets, gems or fusion scrolls."));
            return;
        }

        if (isValidItemStack(ItemStackType.SOCKETABLE, currentItem)) {
            handleSocketableItem(event, player, currentItem, topInv, bottomInv);
        }

        if (isValidItemStack(ItemStackType.SCROLL, currentItem)) {
            handleScrollItem(event, player, topInv, bottomInv);
        }

        if (isValidItemStack(ItemStackType.GEM, currentItem)) {
            handleGemItem(event, player, topInv, bottomInv);
        }
    }

    private boolean isAcceptedItem(ItemStack item) {
        return isValidItemStack(ItemStackType.SCROLL, item)
                || isValidItemStack(ItemStackType.GEM, item)
                || isValidItemStack(ItemStackType.SOCKETABLE, item);
    }

    private void handleSocketableItem(InventoryClickEvent event, RunePlayer player, ItemStack currentItem,
                                      Inventory topInv, Inventory bottomInv) {
        AbstractState state = RuneQuestPlugin.getItemStateManager().getItemState(currentItem);
        if (!(state instanceof GearGemSocketsState socketsState)) return;

        if (socketsState.getAvailableGemSlots() == 0) {
            HudService.sendFailMessage(player, Component.text("This item's gem slots are full!"));
            return;
        }

        ItemStack existing = topInv.getItem(SOCKETABLE_SLOT);
        if (existing != null) {
            topInv.setItem(SOCKETABLE_SLOT, null);
            player.giveItemOrDrop(existing.clone());
        }

        ItemUtils.moveItemToSlot(bottomInv, topInv, event.getSlot(), SOCKETABLE_SLOT, 1);
        scheduleInventoryUpdate(player);
        SoundType.EQUIPMENT_INSERT.play(player, false);
    }

    private void handleScrollItem(InventoryClickEvent event, RunePlayer player, Inventory topInv, Inventory bottomInv) {
        if (topInv.getItem(SCROLL_SLOT) != null) return;

        ItemUtils.moveItemToSlot(bottomInv, topInv, event.getSlot(), SCROLL_SLOT, 1);
        scheduleInventoryUpdate(player);
        SoundType.PAPER_INSERT.play(player, false);
    }

    private void handleGemItem(InventoryClickEvent event, RunePlayer player, Inventory topInv, Inventory bottomInv) {
        if (ACTIVE_GEM_SLOTS.isEmpty()) {
            HudService.sendFailMessage(player, Component.text("Put in the equipment first!"));
            return;
        }

        int availableSlot = ACTIVE_GEM_SLOTS.stream()
                .filter(slot -> inventory.getItem(slot) == null)
                .findFirst()
                .orElse(-1);

        if (availableSlot == -1) {
            HudService.sendFailMessage(player, Component.text("You cannot add more gems!"));
            return;
        }

        ItemUtils.moveItemToSlot(bottomInv, topInv, event.getSlot(), availableSlot, 1);
        scheduleInventoryUpdate(player);
        SoundType.FUSION_GEM_INSERT.play(player, false);
    }

    private void processFusion(RunePlayer player) {
        GearGemSocketsState state = getSocketableSlotState();
        if (state == null) {
            RuneQuestPlugin.getRuneLogger().log(LogLevel.WARN, "Socket state is null during fusion! Something went wrong...");
            return;
        }

        int validGemsCount = getValidGemsCount();
        long totalCost = (long) COST_PER_GEM * validGemsCount;

        if (player.getPouchCoins() < totalCost) {
            HudService.sendFailMessage(player, Component.text("You need at least ")
                    .append(CurrencyUtils.getCoinsCurrencyComponent(totalCost))
                    .append(Component.text(" in order to fuse the gems!")));
            return;
        }

        int insertedGems = 0;

        for (int gemSlot : ACTIVE_GEM_SLOTS) {
            AbstractItemType gem = RuneQuestPlugin.getItemRegistry().getType(inventory.getItem(gemSlot));
            if (gem == null || gem.type != ItemType.GEM) continue;

            try {
                state.insertGem(gem);
                inventory.setItem(gemSlot, null);
                player.setPouchCoins(player.getPouchCoins() - COST_PER_GEM, false);
                insertedGems++;
            } catch (IllegalStateException e) {
                HudService.sendFailMessage(player, Component.text(e.getMessage()));
            }
        }

        ItemStack socketableSlotItem = inventory.getItem(SOCKETABLE_SLOT);
        AbstractItemType equipment = RuneQuestPlugin.getItemRegistry().getType(socketableSlotItem);
        if (equipment == null || socketableSlotItem == null) return;

        if (insertedGems > 0) {
            inventory.setItem(SCROLL_SLOT, null);
            RuneQuestPlugin.getItemStateManager().saveUpdatedState(state, false, true);
            equipment.getMetaGenerator().updatePresentationLayer(socketableSlotItem);
            scheduleInventoryUpdate(player);
            SoundType.FUSION_SUCCESS_1.play(player, true);
            HudService.showCoinsChangeBubble(player, -((long) insertedGems * COST_PER_GEM));
            HudService.showFusionSuccessEffect(player, insertedGems);
            player.asBukkitPlayer().closeInventory();
        }
    }

    private void scheduleInventoryUpdate(RunePlayer player) {
        Bukkit.getScheduler().runTaskLater(RuneQuestPlugin.getInstance(), () -> updateInventory(player), 2L);
    }


    @Override
    public void onInventoryClose(@Nullable InventoryCloseEvent event, @Nullable RunePlayer player) {
        if (player == null) {
            return;
        }

        InventoryUtils.returnPlayerItemFromInventory(inventory, player);
    }


}
