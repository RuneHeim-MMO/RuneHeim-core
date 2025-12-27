package org.zuma.examplePlugin.domain.player.inventory;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.analytics.AnalyticsKey;
import org.zuma.examplePlugin.core.constants.Color;
import org.zuma.examplePlugin.core.constants.Icon;
import org.zuma.examplePlugin.core.constants.SoundType;
import org.zuma.examplePlugin.core.interfaces.SerializableItemContainer;
import org.zuma.examplePlugin.core.utils.AnalyticsUtils;
import org.zuma.examplePlugin.core.utils.PlayerUtils;
import org.zuma.examplePlugin.domain.gui.IDHolder;
import org.zuma.examplePlugin.domain.gui.ItemStackSlotPair;
import org.zuma.examplePlugin.domain.gui.constants.GUIControl;
import org.zuma.examplePlugin.domain.gui.constants.GUIOverlay;
import org.zuma.examplePlugin.domain.gui.constants.InventoryId;
import org.zuma.examplePlugin.domain.hud.HudService;
import org.zuma.examplePlugin.domain.item.base.AbstractItemType;
import org.zuma.examplePlugin.domain.item.base.EquipableItemType;
import org.zuma.examplePlugin.domain.item.constants.ItemType;
import org.zuma.examplePlugin.domain.item.constants.PersistenceDataKey;
import org.zuma.examplePlugin.domain.modifier.Modifier;
import org.zuma.examplePlugin.domain.modifier.constants.ModifierType;
import org.zuma.examplePlugin.domain.player.RunePlayer;

import java.util.List;
import java.util.Set;

public class QuiverHandler implements SerializableItemContainer {
    private final static Set<Integer> ALLOWED_SLOTS = Set.of(3, 4, 5, 12, 13, 14, 21, 22, 23);
    private final static ItemStack FAKE_ARROW = getFakeArrow();

    private final RunePlayer player;
    private final Inventory quiverInventory;

    public QuiverHandler(RunePlayer player) {
        this.player = player;
        this.quiverInventory = Bukkit.createInventory(new IDHolder(InventoryId.QUIVER_INVENTORY.asInt(), player.getUUID()), 9 * 4, Component.text(GUIOverlay.QUIVER_OVERLAY.asGlyph() + "§f" + Icon.QUIVER.get() + " Quiver"));

        for (int i = 0; i < quiverInventory.getSize(); i++) {
            if (ALLOWED_SLOTS.contains(i)) continue;
            quiverInventory.setItem(i, GUIControl.TRANSPARENT.asItemstack(Component.text(" "), false));
        }
    }

    public void setContent(List<ItemStackSlotPair> items) {
        for (ItemStackSlotPair pair : items) {
            if (ALLOWED_SLOTS.contains(pair.slot())) {
                quiverInventory.setItem(pair.slot(), pair.itemStack());
            }
        }
    }

    public static boolean isFakeArrow(ItemStack itemStack) {
        return itemStack.isSimilar(FAKE_ARROW);
    }

    public static ItemStack getFakeArrow() {
        ItemStack arrow = new ItemStack(Material.ARROW);
        ItemMeta meta = arrow.getItemMeta();
        PersistentDataContainer pdc = meta.getPersistentDataContainer();
        pdc.set(PersistenceDataKey.OVERRIDE_SAVING_AND_ILLEGALITY.key(RuneQuestPlugin.getInstance()), org.bukkit.persistence.PersistentDataType.BYTE, (byte) 1);
        meta.displayName(Component.text("Quiver").decoration(TextDecoration.ITALIC, false));
        arrow.setItemMeta(meta);
        return arrow;
    }

    public void openQuiver() {
        player.asBukkitPlayer().openInventory(quiverInventory);
    }

    public boolean hasArrows() {
        for (var item : quiverInventory.getContents()) {
            if (item != null && isArrow(item)) {
                return true;
            }
        }
        return false;
    }

    public ItemStack getNextArrow() {
        for (var item : quiverInventory.getContents()) {
            if (item != null && isArrow(item)) {
                return item;
            }
        }
        return null;
    }

    public int getArrowCount() {
        int count = 0;
        for (var item : quiverInventory.getContents()) {
            if (item != null && isArrow(item)) {
                count += item.getAmount();
            }
        }
        return count;
    }

    public void deductArrow() {
        Modifier arrowRecoveryModifier = this.player.getModifiers().get(ModifierType.ARROW_RECOVERY_PERCENT);
        boolean isArrowConsumed = true;

        if (arrowRecoveryModifier != null) {
            double randomRoll = PlayerUtils.getRandom().nextDouble() * 100.0;
            if (randomRoll < arrowRecoveryModifier.amount()) {
                isArrowConsumed = false;
            }
        }

        if (!isArrowConsumed) {
            SoundType.ARROW_RECOVERED.play(this.player, false);
            return;
        } else {
            AnalyticsUtils.incrementServerAndPlayer(this.player, AnalyticsKey.ARROWS_CONSUMED, 1);
        }

        for (int i = 0; i < quiverInventory.getSize(); i++) {
            ItemStack item = quiverInventory.getItem(i);
            if (item != null && item.getType() == Material.ARROW) {
                int amount = item.getAmount();

                if (amount > 1) {
                    item.setAmount(amount - 1);
                    quiverInventory.setItem(i, item);
                } else {
                    quiverInventory.setItem(i, null);
                }

                return;
            }
        }
    }

    public boolean isArrow(ItemStack itemStack) {
        AbstractItemType itemType = RuneQuestPlugin.getItemRegistry().getType(itemStack);
        return itemType != null && itemType.type.equals(ItemType.ARROW);
    }

    public void onQuiverClickEvent(InventoryClickEvent event) {
        ItemStack clickedItem = event.getCurrentItem();

        if (clickedItem == null) {
            return;
        }

        if (GUIControl.isGuiItem(clickedItem)) {
            event.setCancelled(true);
            return;
        }

        AbstractItemType itemType = RuneQuestPlugin.getItemRegistry().getType(clickedItem);
        boolean isArrow = itemType != null && itemType.type.equals(ItemType.ARROW);

        if (!isArrow) {
            event.setCancelled(true);
            HudService.sendFailMessage(player, Component.text("You can only store arrows in your quiver!", Color.ERROR.asColor()));
            return;
        }

        if (itemType instanceof EquipableItemType equipable) {
            if (!player.meetsRequirements(equipable)) {
                HudService.sendFailMessage(player, Component.text("You do not meet requirements to use " + itemType.displayName + ".", Color.ERROR.asColor()));
                event.setCancelled(true);
                return;
            }
        }

        Inventory topInv = event.getView().getTopInventory();
        Inventory clickedInv = event.getClickedInventory();
        Inventory bottomInv = event.getView().getBottomInventory();
        InventoryAction action = event.getAction();

        if (clickedInv == null) return;

        // Placing an item INTO quiver (from player inventory or cursor)
        if (clickedInv.equals(topInv) && (action == InventoryAction.PLACE_ALL || action == InventoryAction.PLACE_ONE || action == InventoryAction.SWAP_WITH_CURSOR)) {
            SoundType.ARROW_EQUIP.play(player, false);
        }
        // Shift-clicking from player inventory INTO quiver
        else if (clickedInv.equals(bottomInv) && action == InventoryAction.MOVE_TO_OTHER_INVENTORY) {
            SoundType.ARROW_EQUIP.play(player, false);
        } else if (clickedInv.equals(topInv) && action == InventoryAction.MOVE_TO_OTHER_INVENTORY) {
            SoundType.ARROW_UNEQUIP.play(player, false);
        }

        // Taking an item OUT of quiver
        else if (clickedInv.equals(topInv) && (action == InventoryAction.PICKUP_ALL || action == InventoryAction.PICKUP_ONE || action == InventoryAction.PICKUP_HALF)) {
            SoundType.ARROW_UNEQUIP.play(player, false);
        }
    }

    @Override
    public List<ItemStackSlotPair> getContent() {
        List<ItemStackSlotPair> itemPairs = new java.util.ArrayList<>();

        for (int slot = 0; slot < quiverInventory.getSize(); slot++) {
            if (!ALLOWED_SLOTS.contains(slot)) {
                continue;
            }

            ItemStack item = quiverInventory.getItem(slot);

            if (item == null || GUIControl.isGuiItem(item)) {
                continue;
            }

            itemPairs.add(new ItemStackSlotPair(slot, item));
        }

        return itemPairs;

    }
}
