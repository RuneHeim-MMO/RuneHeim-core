package org.zuma.examplePlugin.domain.gui.impl.individual;

import net.kyori.adventure.text.Component;
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
import org.zuma.examplePlugin.core.handlers.game.IllegalItemWatchdog;
import org.zuma.examplePlugin.core.logger.constants.LogLevel;
import org.zuma.examplePlugin.core.utils.CurrencyUtils;
import org.zuma.examplePlugin.core.utils.InventoryUtils;
import org.zuma.examplePlugin.domain.gui.base.IndividualGUI;
import org.zuma.examplePlugin.domain.gui.constants.GUIControl;
import org.zuma.examplePlugin.domain.hud.HudService;
import org.zuma.examplePlugin.domain.item.base.AbstractItemType;
import org.zuma.examplePlugin.domain.mob.base.AbstractMob;
import org.zuma.examplePlugin.domain.mob.behavior.MobBehavior;
import org.zuma.examplePlugin.domain.player.RunePlayer;
import org.zuma.examplePlugin.domain.shop.*;
import org.zuma.examplePlugin.domain.shop.lore.ShopItemLore;

import javax.annotation.Nullable;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

public class ShopInventory extends IndividualGUI implements ShopSubscriber {
    private static final Set<Integer> IGNORED_SLOTS = new HashSet<>(Arrays.asList(0, 1, 9, 10));
    private static final int SHOP_ITEMS_UNTIL_SLOT = 36;
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
                shopInventory.setItem(slot, null);
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
        AbstractItemType type = RuneQuestPlugin.getItemRegistry().getType(item.getItemId());

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
    public boolean attemptOpenInventory(RunePlayer player, Runnable onFail) {
        shop.subscribe(this);
        initialPlayerCoins = player.getPouchCoins();
        updateShopItems();
        player.asBukkitPlayer().openInventory(shopInventory);
        return true;
    }

    private int tryAddOrStackToShopSlots(ItemStack cursorStack) {
        if (cursorStack == null || cursorStack.getAmount() <= 0) return 0;

        int remaining = cursorStack.getAmount();
        int original = remaining;
        int maxStack = cursorStack.getMaxStackSize();
        boolean didAny = false;

        // 1) Stack onto existing
        for (int slot = SELLING_SLOTS_START; slot <= SELLING_SLOT_END; slot++) {
            ItemStack shopStack = shopInventory.getItem(slot);
            if (shopStack != null && shopStack.isSimilar(cursorStack)) {
                int space = maxStack - shopStack.getAmount();
                if (space > 0) {
                    int toMove = Math.min(space, remaining);
                    shopStack.setAmount(shopStack.getAmount() + toMove);
                    remaining -= toMove;
                    didAny = true;
                    if (remaining == 0) break;
                }
            }
        }

        // If we stacked anything, return how many went in
        if (didAny) {
            int moved = original - remaining;
            cursorStack.setAmount(remaining);
            return moved;
        }

        // 2) No stacking → try an empty slot
        for (int slot = SELLING_SLOTS_START; slot <= SELLING_SLOT_END; slot++) {
            if (shopInventory.getItem(slot) == null) {
                // move the *whole* stack
                ItemStack clone = cursorStack.clone();
                shopInventory.setItem(slot, clone);
                cursorStack.setAmount(0);
                return original;
            }
        }

        // 3) Nothing moved
        return 0;
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
            ItemStack confirmIcon = GUIControl.YES.asItemstack(
                    Component.text("Sell items", Color.SUCCESS.asColor()), generateInvoice(toBeSold));
            assignMenuValue(confirmIcon, CONFIRM_MENU_VALUE);

            shopInventory.setItem(SELLING_CONFIRM_SLOT, confirmIcon);
        } else {
            shopInventory.setItem(SELLING_CONFIRM_SLOT, null);
        }
    }

    private List<Component> generateInvoice(List<ItemStack> toBeSold) {
        AtomicLong salePrice = new AtomicLong();
        Map<Integer, Long> customCurrencies = new HashMap<>();

        List<Component> invoice = new ArrayList<>(toBeSold.stream().map(itemStack -> {
            ShopItem item = shop.getShopItem(itemStack);

            if (shop.isBuyAll() && item == null) {
                AbstractItemType type = RuneQuestPlugin.getItemRegistry().getType(itemStack);

                if (type == null) {
                    return Component.text("Unknown Item");
                }

                PriceRecord algorithmicSellPrice = ItemPriceCalculator.getPrice(type);

                if (algorithmicSellPrice == null) {
                    return Component.text("Unknown Item");
                }

                // create a temporary ShopItem for pricing purposes
                item = new ShopItem(
                        type.id,
                        0,
                        0,
                        null,
                        120,
                        algorithmicSellPrice.buyPrice() == null ? 0L : algorithmicSellPrice.buyPrice(),
                        algorithmicSellPrice.sellPrice(),
                        null,
                        null,
                        true,
                        algorithmicSellPrice.buyPrice() != null,
                        true
                );
            }

            if (item == null || item.getSellPrice() == null) {
                return Component.text("Unknown Item");
            }

            long itemPrice = item.getSellPrice() * itemStack.getAmount();
            @Nullable AbstractItemType currency = item.getSellCurrency();

            if (currency == null) {
                salePrice.addAndGet(itemPrice);
            } else {
                if (!customCurrencies.containsKey(currency.id)) {
                    customCurrencies.put(currency.id, itemPrice);
                } else {
                    customCurrencies.put(currency.id, customCurrencies.get(currency.id) + itemPrice);
                }
            }

            return Component.text(CurrencyUtils.formatCurrencyString(itemStack.getAmount()) + " x ", CurrencyUtils.getCurrencyColor(itemPrice).asColor())
                    .append(itemStack.displayName())
                    .append(Component.text(" for ", Color.SHOP_DESCRIPTION.asColor()))
                    .append(CurrencyUtils.getCustomCurrencyComponent(currency, itemPrice));

        }).toList());


        invoice.add(Component.empty());
        invoice.add(Component.text("Total: ", Color.SHOP_DESCRIPTION.asColor())
                .append(CurrencyUtils.getCoinsCurrencyComponent(salePrice.get()))
                .append(Component.text(" coins.", Color.SHOP_DESCRIPTION.asColor()))
                .decoration(TextDecoration.ITALIC, false));

        customCurrencies.forEach((itemId, amount) -> {
            AbstractItemType currencyType = RuneQuestPlugin.getItemRegistry().getType(itemId);

            if (currencyType == null) {
                return;
            }

            invoice.add(Component.text("Total: ", Color.SHOP_DESCRIPTION.asColor())
                    .append(Component.text(CurrencyUtils.formatCurrencyString(amount) + " x ", CurrencyUtils.getCurrencyColor(amount).asColor()))
                    .append(Component.text(currencyType.displayName, Color.SHOP_DESCRIPTION.asColor())
                            .decoration(TextDecoration.ITALIC, false)));
        });

        return invoice;
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
                int moved = tryAddOrStackToShopSlots(clickedStack);

                if (moved > 0) {
                    // play sound etc
                    SoundType.SHOP_MOVE_ITEM.play(player, false);
                    updateConfirmButton();

                    // write back to player inventory:
                    if (clickedStack.getAmount() <= 0) {
                        playerInventory.setItem(playerInvSlot, null);
                    } else {
                        // necessary if your Inventory implementation copies on getItem()
                        playerInventory.setItem(playerInvSlot, clickedStack);
                    }
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

            if (!GUIControl.isGuiItem(currentItem) && slot >= SELLING_SLOTS_START && slot <= SELLING_SLOT_END + 1) {
                ItemStack cloned = currentItem.clone();
                shopInventory.setItem(slot, null);
                player.giveItemOrDrop(cloned);
                updateConfirmButton();
            }

            return;
        }

        if (menuValue == CONFIRM_MENU_VALUE) {
            for (int s = SELLING_SLOTS_START; s <= SELLING_SLOT_END; s++) {
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
            int amount = 1;

            if (event.isShiftClick()) {
                amount = 5;
            }

            shop.buyItem(shop.getStock().get(menuValue), amount, player);
        } catch (Exception e) {
            HudService.sendFailMessage(player, Component.text(e.getMessage()));
        }


    }

    @Override
    public void onInventoryClose(@Nullable InventoryCloseEvent event, @Nullable RunePlayer player) {
        shop.unsubscribe(this);

        if (player == null) {
            return;
        }

        InventoryUtils.returnPlayerItemFromInventory(shopInventory, player);

        long change = player.getPouchCoins() - initialPlayerCoins;

        if (change != 0)
            HudService.showCoinsChangeBubble(player, change);


        AbstractMob mobHolder = getMobHolder();
        if (mobHolder != null && mobHolder.getEntity() != null) {

            if (change != 0) {
                mobHolder.playSound(MobBehavior.MobSoundType.POSITIVE);
            } else {
                mobHolder.playSound(MobBehavior.MobSoundType.NEGATIVE);
            }

        }
    }

    @Override
    public void onShopUpdate() {
        updateShopItems();
    }
}
