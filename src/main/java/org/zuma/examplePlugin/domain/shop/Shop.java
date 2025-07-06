package org.zuma.examplePlugin.domain.shop;

import org.bukkit.inventory.ItemStack;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.utils.Throttler;
import org.zuma.examplePlugin.domain.hud.HudService;
import org.zuma.examplePlugin.domain.item.base.AbstractItemType;
import org.zuma.examplePlugin.domain.player.RunePlayer;
import org.zuma.examplePlugin.domain.shop.persistence.ShopItemConfig;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class Shop {
    private final int id;
    private final HashMap<Integer, ShopItem> stock = new HashMap<>();
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

    public long getItemSalePrice(ItemStack item) {
        AbstractItemType type = RuneQuestPlugin.getItemRegistry().getItem(item);

        if (type == null) {
            return 0;
        }

        ShopItem shopItem = stock.get(type.id);

        if (shopItem == null || shopItem.getSellPrice() == null) {
            return 0;
        }

        return shopItem.getSellPrice() * item.getAmount();
    }

    public boolean isItemStackSellable(@Nullable ItemStack item) throws IllegalStateException {
        AbstractItemType type = RuneQuestPlugin.getItemRegistry().getItem(item);

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

        if (shopItem.getCurrentStock() + item.getAmount() >= 1024) {
            throw new IllegalStateException("This shop is full and can't accept more of this item.");
        }

        return true;
    }

    public void sellItem(ItemStack item, RunePlayer player) throws IllegalStateException {
        if (!isItemStackSellable(item)) {
            return;
        }

        AbstractItemType type = RuneQuestPlugin.getItemRegistry().getItem(item);

        if (type == null) {
            throw new IllegalStateException("Unknown item");
        }

        ShopItem shopItem = stock.get(type.id);

        if (shopItem == null || shopItem.getSellPrice() == null) {
            throw new IllegalStateException("This item cannot be sold to this shop!");
        }

        long totalOrderPrice = item.getAmount() * shopItem.getSellPrice();

        shopItem.setCurrentStock(shopItem.getCurrentStock() + item.getAmount());
        player.setPouchCoins(player.getPouchCoins() + totalOrderPrice);
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

        long totalOrderPrice = item.getBuyPrice() * amount;

        if (player.getPouchCoins() < totalOrderPrice) {
            throw new IllegalStateException("You don't have enough money! You need at least " + totalOrderPrice + " coins.");
        }

        AbstractItemType type = RuneQuestPlugin.getItemRegistry().getItem(item.getItemId());

        if (type == null) {
            throw new IllegalStateException("Unknown item id " + item.getItemId());
        }

        item.setCurrentStock(item.getCurrentStock() - amount);
        player.setPouchCoins(player.getPouchCoins() - totalOrderPrice);
        player.giveItemOrDrop(type.createItemStackOfType(amount));
        notifyStockSubscribers();
    }
}
