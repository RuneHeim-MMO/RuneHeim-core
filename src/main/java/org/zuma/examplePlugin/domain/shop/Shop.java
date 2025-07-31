package org.zuma.examplePlugin.domain.shop;

import org.bukkit.inventory.ItemStack;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.constants.SoundType;
import org.zuma.examplePlugin.core.utils.PlayerUtils;
import org.zuma.examplePlugin.core.utils.Throttler;
import org.zuma.examplePlugin.domain.item.ItemRecord;
import org.zuma.examplePlugin.domain.item.base.AbstractItemType;
import org.zuma.examplePlugin.domain.player.RunePlayer;
import org.zuma.examplePlugin.domain.shop.persistence.ShopItemConfig;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class Shop {
    private final int id;
    private final LinkedHashMap<Integer, ShopItem> stock = new LinkedHashMap<>(); // Preserves order
    private final List<ShopSubscriber> updateSubscribers = new ArrayList<>();
    private final Throttler updateThrottler = new Throttler(500);

    public Shop(int id, List<ShopItemConfig> stockConfiguration) {
        this.id = id;

        stockConfiguration.forEach(config -> {
            stock.put(config.itemId(), new ShopItem(
                    config.itemId(),
                    config.defaultStock(),
                    config.defaultStock(),
                    null,
                    config.stockReplenishmentSeconds(),
                    config.buyPrice(),
                    config.sellPrice(),
                    config.buyCustomCurrencyId(),
                    config.sellCustomCurrencyId(),
                    config.sellPrice() != null,
                    config.buyPrice() != null));
        });

    }

    public int getId() {
        return id;
    }

    public HashMap<Integer, ShopItem> getStock() {
        return this.stock;
    }

    public void subscribe(ShopSubscriber sub) {
        if (!updateSubscribers.contains(sub)) {
            updateSubscribers.add(sub);
        }
    }

    public void unsubscribe(ShopSubscriber sub) {
        updateSubscribers.remove(sub);
    }

    public void notifyStockSubscribers() {
        updateThrottler.throttle(() -> updateSubscribers.forEach(ShopSubscriber::onShopUpdate));
    }

    public ShopItem getShopItem(ItemStack item) {
        AbstractItemType type = RuneQuestPlugin.getItemRegistry().getType(item);

        if (type == null) {
            return null;
        }

        ShopItem shopItem = stock.get(type.id);

        if (shopItem == null || shopItem.getSellPrice() == null) {
            return null;
        }

        return shopItem;
    }

    public boolean isItemStackSellable(@Nullable ItemStack item) throws IllegalStateException {
        AbstractItemType type = RuneQuestPlugin.getItemRegistry().getType(item);

        if (type == null || item == null) {
            throw new IllegalStateException("Invalid item");
        }

        if (!stock.containsKey(type.id)) {
            throw new IllegalStateException("You cannot sell this item to this shop!");
        }

        ShopItem shopItem = stock.get(type.id);

        if (!shopItem.isSellable()) {
            throw new IllegalStateException("You cannot sell this item to this shop!");
        }

        if (shopItem.getCurrentStock() + item.getAmount() >= 10000) {
            throw new IllegalStateException("This shop is full and can't accept more of this item.");
        }

        return true;
    }

    public void sellItem(ItemStack item, RunePlayer player) throws IllegalStateException {
        if (!isItemStackSellable(item)) {
            return;
        }

        AbstractItemType type = RuneQuestPlugin.getItemRegistry().getType(item);

        if (type == null) {
            throw new IllegalStateException("Unknown item");
        }

        ShopItem shopItem = stock.get(type.id);

        if (shopItem == null || shopItem.getSellPrice() == null) {
            throw new IllegalStateException("This item cannot be sold to this shop!");
        }

        long totalOrderPrice = item.getAmount() * shopItem.getSellPrice();
        shopItem.setCurrentStock(shopItem.getCurrentStock() + item.getAmount());

        if (shopItem.getSellCurrency() != null) {
            player.giveItemOrDrop(new ItemRecord(shopItem.getSellCurrency().id, Math.toIntExact(totalOrderPrice)));
            SoundType.SHOP_BUY_WITH_CUSTOM_CURRENCY.play(player, true);
        } else {
            player.setPouchCoins(player.getPouchCoins() + totalOrderPrice);
        }

        notifyStockSubscribers();

    }


    public void buyItem(ShopItem item, int amount, RunePlayer player) throws IllegalStateException {
        if (!stock.containsValue(item)) {
            throw new IllegalStateException("This item does not exist in this shop!");
        }

        if (!item.isBuyable() || item.getBuyPrice() == null) {
            throw new IllegalStateException("You cannot buy this item!");
        }

        if (item.getCurrentStock() < amount) {
            throw new IllegalStateException("Cannot buy more items than in stock!");
        }

        AbstractItemType type = RuneQuestPlugin.getItemRegistry().getType(item.getItemId());

        if (type == null) {
            throw new IllegalStateException("Unknown item id " + item.getItemId());
        }

        long totalOrderPrice = item.getBuyPrice() * amount;
        item.setCurrentStock(item.getCurrentStock() - amount);

        if (item.getBuyCurrency() != null) {
            boolean currencyDeductSuccess = PlayerUtils.removeItems(player, List.of(new ItemRecord(item.getBuyCurrency().id, Math.toIntExact(totalOrderPrice))));

            if (currencyDeductSuccess) {
                SoundType.SHOP_BUY_WITH_CUSTOM_CURRENCY.play(player, true);
            } else {
                throw new IllegalStateException("You cannot afford this! You need at least " + totalOrderPrice + "x " + item.getBuyCurrency().displayName + "!");
            }
        } else {
            if (player.getPouchCoins() < totalOrderPrice) {
                throw new IllegalStateException("You cannot afford this! You need at least " + totalOrderPrice + " coins.");
            }

            player.setPouchCoins(player.getPouchCoins() - totalOrderPrice);
        }

        player.giveItemOrDrop(type.createItemStackOfType(amount));
        notifyStockSubscribers();
    }
}
