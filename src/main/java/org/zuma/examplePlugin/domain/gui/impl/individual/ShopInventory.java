package org.zuma.examplePlugin.domain.gui.impl.individual;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.constants.Color;
import org.zuma.examplePlugin.core.constants.SoundType;
import org.zuma.examplePlugin.core.handlers.IllegalItemWatchdog;
import org.zuma.examplePlugin.core.logger.constants.LogLevel;
import org.zuma.examplePlugin.core.utils.CurrencyUtils;
import org.zuma.examplePlugin.core.utils.InventoryUtils;
import org.zuma.examplePlugin.domain.gui.base.IndividualGUI;
import org.zuma.examplePlugin.domain.gui.constants.GUIControl;
import org.zuma.examplePlugin.domain.hud.HudService;
import org.zuma.examplePlugin.domain.item.base.AbstractItemType;
import org.zuma.examplePlugin.domain.player.RunePlayer;
import org.zuma.examplePlugin.domain.shop.Shop;
import org.zuma.examplePlugin.domain.shop.ShopItem;
import org.zuma.examplePlugin.domain.shop.ShopSubscriber;
import org.zuma.examplePlugin.domain.shop.lore.ShopItemLore;

import javax.annotation.Nullable;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

public class ShopInventory extends IndividualGUI implements ShopSubscriber {
    private static final Set<Integer> IGNORED_SLOTS = new HashSet<>(Arrays.asList(0, 1, 9, 10));
    private static final int SHOP_ITEMS_UNTIL_SLOT = 35;
    private static final int SELLING_SLOTS_START = 45;
    private static final int SELLING_SLOT_END = 52;
    private static final int SELLING_CONFIRM_SLOT = 53;
    private static final int CONFIRM_MENU_VALUE = -10000;

    private final Inventory shopInventory;
    private final Shop shop;
    private long initialPlayerCoins;

    public ShopInventory(String title, int id, Shop shop) {
        super(title, id, 6);
        this.shop = shop;
        this.shopInventory = getBaseInventory();
        this.initialPlayerCoins = 0;
    }

    private void updateShopItems() {
        Iterator<ShopItem> iterator = shop.getStock().values().iterator();

        for (int slot = 0; slot < Math.min(SHOP_ITEMS_UNTIL_SLOT, shopInventory.getSize()); slot++) {
            if (IGNORED_SLOTS.contains(slot)) {
                continue; // skip this slot
            }

            if (!iterator.hasNext()) {
                break; // no more items to place
            }

            ShopItem item = iterator.next();
            ItemStack displayItem = createItemStack(item);

            if (displayItem != null) {
                IllegalItemWatchdog.tagIllegalItem(displayItem);
                assignMenuValue(displayItem, item.getItemId());
                shopInventory.setItem(slot, displayItem);
            } else {
                RuneQuestPlugin.getRuneLogger().log(LogLevel.WARN, "Couldn't create display item for item id " + item.getItemId() + " for shop id " + shop.getId() + "! Item is null!");
            }
        }
    }

    @Nullable
    private ItemStack createItemStack(ShopItem item) {
        AbstractItemType type = RuneQuestPlugin.getItemRegistry().getItem(item.getItemId());

        if (type == null) {
            return null;
        }

        ItemStack stack = type.createItemStackOfType();
        ItemMeta meta = stack.getItemMeta();
        List<Component> lore = meta.lore();
        Component displayName = meta.displayName();

        displayName = Component.text(item.getCurrentStock() + "x ", displayName.style()).append(displayName);
        meta.displayName(displayName);

        if (lore == null) {
            lore = new ArrayList<>();
        }

        lore.addAll(new ShopItemLore(item).getLore());
        meta.lore(lore);

        if (item.getCurrentStock() <= 0) {
            ItemStack outOfStock = new ItemStack(Material.BARRIER);

            outOfStock.setItemMeta(meta);
            return outOfStock;

        }


        stack.setItemMeta(meta);
        stack.setAmount(item.getCurrentStock());


        return stack;
    }

    @Override
    public void openInventory(RunePlayer player) {
        shop.subscribe(this);
        initialPlayerCoins = player.getPouchCoins();
        updateShopItems();
        player.asBukkitPlayer().openInventory(shopInventory);
    }

    private boolean tryAddOrStackToShopSlots(ItemStack itemToAdd) {
        int amountToAdd = itemToAdd.getAmount();

        // First try to stack onto existing stacks of the same item
        for (int slot = SELLING_SLOTS_START; slot <= SELLING_SLOT_END; slot++) {
            ItemStack current = shopInventory.getItem(slot);
            if (current != null && current.isSimilar(itemToAdd)) {
                int maxStack = current.getMaxStackSize();
                int currentAmount = current.getAmount();
                int spaceLeft = maxStack - currentAmount;

                if (spaceLeft > 0) {
                    int addAmount = Math.min(spaceLeft, amountToAdd);
                    current.setAmount(currentAmount + addAmount);
                    amountToAdd -= addAmount;

                    if (amountToAdd <= 0) {
                        // All items added by stacking
                        return true;
                    }
                }
            }
        }

        // Then try to put remaining items in empty slots
        for (int slot = SELLING_SLOTS_START; slot <= SELLING_SLOT_END; slot++) {
            if (shopInventory.getItem(slot) == null) {
                // Put remaining items here
                ItemStack newStack = itemToAdd.clone();
                newStack.setAmount(amountToAdd);
                shopInventory.setItem(slot, newStack);
                return true;
            }
        }

        // No space to stack or put item
        return false;
    }

    private void updateConfirmButton() {
        boolean isVisible = false;
        List<ItemStack> toBeSold = new ArrayList<>();

        for (int slot = SELLING_SLOTS_START; slot <= SELLING_SLOT_END; slot++) {
            ItemStack current = shopInventory.getItem(slot);
            if (current != null) {
                isVisible = true;
                toBeSold.add(current);
            }
        }

        if (isVisible) {
            AtomicLong salePrice = new AtomicLong();
            List<Component> invoice = new ArrayList<>(toBeSold.stream().map(itemStack -> {
                long itemPrice = shop.getItemSalePrice(itemStack);
                salePrice.addAndGet(itemPrice);

                return Component.text(itemStack.getAmount() + "x ", NamedTextColor.WHITE)
                        .append(itemStack.displayName())
                        .append(Component.text(" - ", NamedTextColor.WHITE))
                        .append(CurrencyUtils.getCurrencyComponent(itemPrice));

            }).toList());


            invoice.add(Component.empty());
            invoice.add(Component.text("Total: ", Color.SHOP_DESCRIPTION.asColor())
                    .append(CurrencyUtils.getCurrencyComponent(salePrice.get()))
                    .append(Component.text(" coins.", Color.SHOP_DESCRIPTION.asColor()))
                    .decoration(TextDecoration.ITALIC, false));


            ItemStack confirmIcon = GUIControl.YES.asItemstack(
                    Component.text("Sell items", Color.SUCCESS.asColor()), invoice);
            assignMenuValue(confirmIcon, CONFIRM_MENU_VALUE);

            shopInventory.setItem(SELLING_CONFIRM_SLOT, confirmIcon);
        } else {
            shopInventory.setItem(SELLING_CONFIRM_SLOT, null);
        }
    }

    @Override
    public void onInventoryClick(InventoryClickEvent event, int slot, RunePlayer player) {
        event.setCancelled(true);

        if (event.getClickedInventory() == player.asBukkitPlayer().getInventory()) {
            PlayerInventory playerInventory = player.asBukkitPlayer().getInventory();
            int playerInvSlot = event.getSlot();
            ItemStack clickedStack = playerInventory.getItem(playerInvSlot);

            if (clickedStack == null) {
                return;
            }

            try {
                shop.isItemStackSellable(clickedStack);
                ItemStack cloned = clickedStack.clone();

                if (tryAddOrStackToShopSlots(cloned)) {
                    clickedStack.setAmount(0);
                    playerInventory.setItem(playerInvSlot, null);
                    SoundType.SHOP_MOVE_ITEM.play(player, false);
                    updateConfirmButton();
                }

            } catch (Exception e) {
                HudService.sendFailMessage(player, Component.text(e.getMessage()));
            }
        }

        ItemStack currentItem = event.getCurrentItem();

        if (currentItem == null) {
            return;
        }

        Integer menuValue = getMenuValue(currentItem);

        if (menuValue == null) {
            return;
        }

        if (menuValue == CONFIRM_MENU_VALUE) {
            for (int s = SELLING_SLOTS_START; s < SELLING_SLOT_END; s++) {
                try {
                    if (shopInventory.getItem(s) == null) {
                        continue;
                    }
                    shop.sellItem(shopInventory.getItem(s), player);
                    shopInventory.setItem(s, null);
                } catch (Exception e) {
                    HudService.sendFailMessage(player, Component.text(e.getMessage(), Color.ERROR.asColor()));
                }
            }

            updateConfirmButton();
            return;
        }

        try {
            shop.buyItem(shop.getStock().get(menuValue), 1, player);
        } catch (Exception e) {
            HudService.sendFailMessage(player, Component.text(e.getMessage()));
        }


    }

    @Override
    public void onInventoryClose(InventoryCloseEvent event, RunePlayer player) {
        shop.unsubscribe(this);
        InventoryUtils.returnPlayerItemFromInventory(shopInventory, player);

        long change = player.getPouchCoins() - initialPlayerCoins;

        if (change != 0)
            HudService.showCoinsChangeBubble(player, change);
    }

    @Override
    public void onShopUpdate() {
        updateShopItems();
    }
}
