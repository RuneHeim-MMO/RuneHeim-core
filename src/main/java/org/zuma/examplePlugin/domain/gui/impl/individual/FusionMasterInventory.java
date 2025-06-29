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
import org.zuma.examplePlugin.core.utils.ItemUtils;
import org.zuma.examplePlugin.domain.gui.base.IndividualGUI;
import org.zuma.examplePlugin.domain.gui.constants.GUIControl;
import org.zuma.examplePlugin.domain.hud.HudService;
import org.zuma.examplePlugin.domain.item.base.AbstractItemType;
import org.zuma.examplePlugin.domain.item.constants.ItemId;
import org.zuma.examplePlugin.domain.item.constants.ItemType;
import org.zuma.examplePlugin.domain.item.state.base.AbstractState;
import org.zuma.examplePlugin.domain.item.state.states.v1.GearGemSocketsState;
import org.zuma.examplePlugin.domain.player.RunePlayer;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FusionMasterInventory extends IndividualGUI {
    private final ItemStack transparentItem = GUIControl.TRANSPARENT.asItemstack();
    private final ItemStack blankItem = GUIControl.BLANK.asItemstack();
    private final ItemStack confirmItem = GUIControl.YES.asItemstack(Component.text("Confirm fusion", Color.SUCCESS.asColor()));
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

    private void fillWithPlaceholders() {
        for (int i = 0; i < inventory.getSize(); i++) {

            if (i == SOCKETABLE_SLOT || i == SCROLL_SLOT || GEM_SLOTS.contains(i)) {
                continue;
            }

            inventory.setItem(i, transparentItem);
        }
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

        AbstractItemType itemType = RuneQuestPlugin.getItemRegistry().getItem(itemStack);
        List<ItemType> socketedTypes = List.of(ItemType.EQUIPMENT_WITH_GEM_SOCKETS, ItemType.WEAPON_WITH_GEM_SOCKETS);

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
    public void openInventory(RunePlayer player) {
        fillWithPlaceholders();
        updateInventory(player);

        super.assignMenuValue(confirmItem, 1);
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
    }

    @Override
    public void onInventoryClick(InventoryClickEvent event, int slot, RunePlayer player) {
        Inventory topInv = event.getView().getTopInventory();
        Inventory bottomInv = event.getView().getBottomInventory();
        ItemStack currentItem = event.getCurrentItem();
        event.setCancelled(true);

        if (currentItem == null) {
            return;
        }

        Integer menuValue = getMenuValue(currentItem);

        if (menuValue != null && menuValue == 0) {
            player.asBukkitPlayer().closeInventory();
            return;
        }

        if (menuValue != null && menuValue == 1) {
            GearGemSocketsState state = getSocketableSlotState();
            int insertedGems = 0;

            if (state == null) {
                RuneQuestPlugin.getRuneLogger().log(LogLevel.WARN, "Socket state is null during fusion! Something went wrong...");
                return;
            }

            for (int gemSlot : ACTIVE_GEM_SLOTS) {
                AbstractItemType gem = RuneQuestPlugin.getItemRegistry().getItem(inventory.getItem(gemSlot));

                if (gem == null) {
                    continue;
                }

                if (gem.type == ItemType.GEM) {
                    try {
                        state.insertGem(gem);
                        inventory.setItem(gemSlot, null);
                        insertedGems += 1;
                    } catch (IllegalStateException e) {
                        HudService.sendFailMessage(player, Component.text(e.getMessage()));
                    }
                }

            }

            ItemStack socketableSlotItem = inventory.getItem(SOCKETABLE_SLOT);
            AbstractItemType equipment = RuneQuestPlugin.getItemRegistry().getItem(socketableSlotItem);

            if (equipment == null || socketableSlotItem == null) {
                return;
            }

            if (insertedGems >= 1) {
                inventory.setItem(SCROLL_SLOT, null);

                RuneQuestPlugin.getItemStateManager().saveUpdatedState(state);
                equipment.getMetaGenerator().updatePresentationLayer(socketableSlotItem);
                Bukkit.getScheduler().runTaskLater(RuneQuestPlugin.getInstance(), () -> updateInventory(player), 2L);
                SoundType.FUSION_SUCCESS_1.play(player, true);
                HudService.showFusionSuccessEffect(player, insertedGems);
                player.asBukkitPlayer().closeInventory();
            }
            return;
        }

        if (event.getClickedInventory() == topInv) {
            if (!GUIControl.isGuiItem(currentItem)) {
                player.giveItemOrDrop(currentItem);
                event.setCurrentItem(null);
                Bukkit.getScheduler().runTaskLater(RuneQuestPlugin.getInstance(), () -> updateInventory(player), 2L);
            }
        }


        if (event.getClickedInventory() == bottomInv) {

            if (!(isValidItemStack(ItemStackType.SCROLL, currentItem) || isValidItemStack(ItemStackType.GEM, currentItem) || isValidItemStack(ItemStackType.SOCKETABLE, currentItem))) {
                HudService.sendFailMessage(player, Component.text("Fusion master accepts only items with sockets, gems or fusion scrolls."));
                return;
            }

            if (isValidItemStack(ItemStackType.SOCKETABLE, currentItem)) {
                if (inventory.getItem(SOCKETABLE_SLOT) != null) {
                    player.giveItemOrDrop(topInv.getItem(SOCKETABLE_SLOT));
                    topInv.setItem(SOCKETABLE_SLOT, null);
                }

                ItemUtils.moveItemToSlot(bottomInv, topInv, event.getSlot(), SOCKETABLE_SLOT, 1);
                Bukkit.getScheduler().runTaskLater(RuneQuestPlugin.getInstance(), () -> updateInventory(player), 2L);
                SoundType.EQUIPMENT_INSERT.play(player, false);
            }

            if (isValidItemStack(ItemStackType.SCROLL, currentItem)) {
                if (inventory.getItem(SCROLL_SLOT) != null) {
                    return;
                }

                ItemUtils.moveItemToSlot(bottomInv, topInv, event.getSlot(), SCROLL_SLOT, 1);
                Bukkit.getScheduler().runTaskLater(RuneQuestPlugin.getInstance(), () -> updateInventory(player), 2L);
                SoundType.PAPER_INSERT.play(player, false);
            }

            if (isValidItemStack(ItemStackType.GEM, currentItem)) {

                if (ACTIVE_GEM_SLOTS.isEmpty()) {
                    HudService.sendFailMessage(player, Component.text("Put in the equipment first!"));
                    return;
                }

                int availableSlot = -1;

                for (int gemSlot : ACTIVE_GEM_SLOTS) {
                    if (inventory.getItem(gemSlot) == null) {
                        availableSlot = gemSlot;
                        break;

                    }
                }

                if (availableSlot == -1) {
                    HudService.sendFailMessage(player, Component.text("You cannot add more gems!"));
                    return;
                }

                ItemUtils.moveItemToSlot(bottomInv, topInv, event.getSlot(), availableSlot, 1);
                Bukkit.getScheduler().runTaskLater(RuneQuestPlugin.getInstance(), () -> updateInventory(player), 2L);
                SoundType.FUSION_GEM_INSERT.play(player, false);
                return;
            }


            return;
        }
    }

    @Override
    public void onInventoryClose(InventoryCloseEvent event, RunePlayer player) {
        for (int slot = 0; slot < inventory.getSize(); slot++) {
            ItemStack item = inventory.getItem(slot);

            if (item != null && !GUIControl.isGuiItem(item)) {
                player.giveItemOrDrop(item);
                inventory.setItem(slot, null);
            }
        }
    }


}
