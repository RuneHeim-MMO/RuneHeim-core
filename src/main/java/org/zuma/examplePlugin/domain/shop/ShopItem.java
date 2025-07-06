package org.zuma.examplePlugin.domain.shop;

import javax.annotation.Nullable;

public class ShopItem {
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

    // Constructor
    public ShopItem(int itemId,
                    int defaultStock,
                    int currentStock,
                    @Nullable Long lastReplenishedMillis,
                    @Nullable Integer stockReplenishmentSeconds,
                    @Nullable Long buyPrice,
                    @Nullable Long sellPrice,
                    boolean sellable,
                    boolean buyable) {
        this.itemId = itemId;
        this.defaultStock = defaultStock;
        this.currentStock = currentStock;
        this.lastReplenishedMillis = lastReplenishedMillis;
        this.stockReplenishmentSeconds = stockReplenishmentSeconds;
        this.buyPrice = buyPrice;
        this.sellPrice = sellPrice;
        this.sellable = sellable;
        this.buyable = buyable;
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
    public Long getSellPrice() {
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
}
