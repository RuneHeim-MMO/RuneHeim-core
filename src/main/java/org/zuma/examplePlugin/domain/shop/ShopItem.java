package org.zuma.examplePlugin.domain.shop;

import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.logger.constants.LogLevel;
import org.zuma.examplePlugin.domain.item.base.AbstractItemType;

import javax.annotation.Nullable;

public class ShopItem {
    // Indicates if shop is buy-all and the item is temporary (created & removed dynamically), disappears after stock is bought.
    private final boolean temporaryItem;
    private int itemId;
    private int defaultStock;
    private int currentStock;
    @Nullable
    private Long lastReplenishedMillis;
    @Nullable
    private Integer stockReplenishmentSeconds;
    @Nullable
    private Long buyPrice;
    @Nullable
    private Long sellPrice;
    private boolean sellable;
    private boolean buyable;
    @Nullable
    private AbstractItemType buyCustomCurrency, sellCustomCurrency;

    // Constructor
    public ShopItem(int itemId,
                    int defaultStock,
                    int currentStock,
                    @Nullable Long lastReplenishedMillis,
                    @Nullable Integer stockReplenishmentSeconds,
                    @Nullable Long buyPrice,
                    @Nullable Long sellPrice,
                    @Nullable Integer buyCustomCurrencyId,
                    @Nullable Integer sellCustomCurrencyId,
                    boolean sellable,
                    boolean buyable,
                    boolean isTemporaryItem) {
        this.itemId = itemId;
        this.defaultStock = defaultStock;
        this.currentStock = currentStock;
        this.lastReplenishedMillis = lastReplenishedMillis;
        this.stockReplenishmentSeconds = stockReplenishmentSeconds;
        this.buyPrice = buyPrice;
        this.sellPrice = sellPrice;
        this.sellable = sellable;
        this.buyable = buyable;
        this.temporaryItem = isTemporaryItem;

        if (buyCustomCurrencyId != null) {
            AbstractItemType currencyType = RuneQuestPlugin.getItemRegistry().getType(buyCustomCurrencyId);

            if (currencyType == null) {
                RuneQuestPlugin.getRuneLogger().log(LogLevel.WARN, "Couldn't set custom currency for a shop item! ItemId: " + itemId + " CurrencyId: " + buyCustomCurrencyId);
                this.sellPrice = 0L;
                this.buyPrice = null;
            } else {
                this.buyCustomCurrency = currencyType;
            }
        }

        if (sellCustomCurrencyId != null) {
            AbstractItemType currencyType = RuneQuestPlugin.getItemRegistry().getType(sellCustomCurrencyId);

            if (currencyType == null) {
                RuneQuestPlugin.getRuneLogger().log(LogLevel.WARN, "Couldn't set custom currency for a shop item! ItemId: " + itemId + " CurrencyId: " + buyCustomCurrencyId);
                this.sellPrice = 0L;
                this.buyPrice = null;
            } else {
                this.sellCustomCurrency = currencyType;
            }
        }
    }

    // Getters and setters
    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getDefaultStock() {
        return defaultStock;
    }

    public void setDefaultStock(int defaultStock) {
        this.defaultStock = defaultStock;
    }

    public int getCurrentStock() {
        return currentStock;
    }

    public void setCurrentStock(int currentStock) {
        this.currentStock = currentStock;
    }

    @Nullable
    public Long getLastReplenishedMillis() {
        return lastReplenishedMillis;
    }

    public void setLastReplenishedMillis(@Nullable Long lastReplenishedMillis) {
        this.lastReplenishedMillis = lastReplenishedMillis;
    }

    @Nullable
    public Integer getStockReplenishmentSeconds() {
        return stockReplenishmentSeconds;
    }

    public void setStockReplenishmentSeconds(@Nullable Integer stockReplenishmentSeconds) {
        this.stockReplenishmentSeconds = stockReplenishmentSeconds;
    }

    @Nullable
    public Long getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(@Nullable Long buyPrice) {
        this.buyPrice = buyPrice;
    }

    @Nullable
    public AbstractItemType getBuyCurrency() {
        return this.buyCustomCurrency;
    }

    public AbstractItemType getSellCurrency() {
        return this.sellCustomCurrency;
    }

    @Nullable
    public Long getBaseSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(@Nullable Long sellPrice) {
        this.sellPrice = sellPrice;
    }

    public boolean isSellable() {
        return sellable;
    }

    public void setSellable(boolean sellable) {
        this.sellable = sellable;
    }

    public boolean isBuyable() {
        return buyable;
    }

    public void setBuyable(boolean buyable) {
        this.buyable = buyable;
    }

    public boolean isTemporaryItem() {
        return temporaryItem;
    }

    public static double getSellPriceMultiplier(int itemId, int stock, int amount) {
        if (Shop.STATIC_SELL_PRICE_ITEMS.contains(itemId)) {
            return 1.0;
        }
        return Math.max(Shop.SELL_PRICE_FLOOR_MULTIPLIER, 1.0 - ((double) (stock + amount) / Shop.MAX_STOCK_PER_ITEM) * (1 - Shop.SELL_PRICE_FLOOR_MULTIPLIER));
    }
}
