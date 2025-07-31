package org.zuma.examplePlugin.domain.shop;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.constants.Color;
import org.zuma.examplePlugin.core.logger.constants.LogLevel;
import org.zuma.examplePlugin.core.utils.CurrencyUtils;
import org.zuma.examplePlugin.domain.item.base.AbstractItemType;

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

    @Nullable
    private AbstractItemType currency;

    @Nullable
    private String currencyDisplayName;


    // Constructor
    public ShopItem(int itemId,
                    int defaultStock,
                    int currentStock,
                    @Nullable Long lastReplenishedMillis,
                    @Nullable Integer stockReplenishmentSeconds,
                    @Nullable Long buyPrice,
                    @Nullable Long sellPrice,
                    @Nullable Integer currencyId,
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

        if (currencyId != null) {
            AbstractItemType currencyType = RuneQuestPlugin.getItemRegistry().getType(currencyId);

            if (currencyType == null) {
                RuneQuestPlugin.getRuneLogger().log(LogLevel.WARN, "Couldn't set custom currency for a shop item! ItemId: " + itemId + " CurrencyId: " + currencyId);
                this.sellPrice = 0L;
                this.buyPrice = null;
            } else {
                this.currency = RuneQuestPlugin.getItemRegistry().getType(currencyId);
                this.currencyDisplayName = currencyType.displayName;
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

    @Nullable
    public AbstractItemType getCurrency() {
        return this.currency;
    }

    public String getCurrencyDisplayName() {
        if (this.currencyDisplayName == null) {
            return "[UNKNOWN ITEM]";
        }

        return this.currencyDisplayName;
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

    public Component getCurrencyAmountComponent(Long amount) {
        if (getCurrency() == null) {
            // Regular gold coins
            return CurrencyUtils.getCurrencyComponent(amount).append(Component.text(" coins.", Color.SHOP_DESCRIPTION.asColor()));
        }

        // Custom currency

        return Component.text(CurrencyUtils.formatCurrencyString(amount) + " x " + getCurrencyDisplayName(), CurrencyUtils.getCurrencyColor(amount).asColor());

    }
}
