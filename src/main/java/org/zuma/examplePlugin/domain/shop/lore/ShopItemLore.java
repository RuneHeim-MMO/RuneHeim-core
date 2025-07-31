package org.zuma.examplePlugin.domain.shop.lore;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.constants.Color;
import org.zuma.examplePlugin.core.utils.CurrencyUtils;
import org.zuma.examplePlugin.core.utils.TextUtils;
import org.zuma.examplePlugin.domain.item.base.AbstractItemType;
import org.zuma.examplePlugin.domain.item.helpers.lore.LoreCreator;
import org.zuma.examplePlugin.domain.shop.ShopItem;

import java.util.ArrayList;
import java.util.List;

public class ShopItemLore implements LoreCreator {
    private List<Component> lore = new ArrayList<>();

    public ShopItemLore(ShopItem item) {
        boolean isInStock = item.getCurrentStock() > 0;


        lore.add(Component.empty());

        if (!isInStock && item.isBuyable()) {
            lore.add(Component.text("Out of stock", Color.ERROR.asColor()));
        }

        if (item.isBuyable() && isInStock) {
            lore.add(Component.text("Buy 1x for ", Color.SHOP_DESCRIPTION.asColor()).append(item.getCurrencyAmountComponent(item.getBuyPrice())).append(net.kyori.adventure.text.Component.text(" (Click)", Color.SHOP_DESCRIPTION.asColor())));
            lore.add(Component.text("Buy 5x for ", Color.SHOP_DESCRIPTION.asColor()).append(item.getCurrencyAmountComponent(item.getBuyPrice() * 5)).append(Component.text(" (Shift + Click)", Color.SHOP_DESCRIPTION.asColor())));

            if (item.isSellable()) {
                lore.add(Component.empty());
            }
        }

        if (item.isSellable()) {
            lore.add(Component.text("Sell 1x for ", Color.SHOP_DESCRIPTION.asColor()).append(item.getCurrencyAmountComponent(item.getSellPrice())).append(Component.text(" (Click in own inventory)", Color.SHOP_DESCRIPTION.asColor())));
        }


        lore = lore.stream().map(c -> c.decoration(TextDecoration.ITALIC, false)).toList();
    }

    @Override
    public List<Component> getLore() {
        return lore;
    }
}
