package org.zuma.examplePlugin.domain.shop.lore;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.zuma.examplePlugin.core.constants.Color;
import org.zuma.examplePlugin.core.utils.CurrencyUtils;
import org.zuma.examplePlugin.core.utils.TextUtils;
import org.zuma.examplePlugin.domain.item.helpers.lore.LoreCreator;
import org.zuma.examplePlugin.domain.shop.ShopItem;

import java.util.ArrayList;
import java.util.List;

public class ShopItemLore implements LoreCreator {
    private List<Component> lore = new ArrayList<>();

    public ShopItemLore(ShopItem item) {
        boolean isInStock = item.getCurrentStock() > 0;

        lore.add(Component.empty());

        if (!isInStock) {
            lore.add(Component.text("Out of stock", Color.ERROR.asColor()));
        }

        if (item.isBuyable() && isInStock) {
            lore.add(Component.text("Buy 1x for ", Color.SHOP_DESCRIPTION.asColor()).append(CurrencyUtils.getCurrencyComponent(item.getBuyPrice())).append(Component.text(" coins.", Color.SHOP_DESCRIPTION.asColor())));
        }

        if (item.isSellable()) {
            lore.add(Component.text("Sell 1x for ", Color.SHOP_DESCRIPTION.asColor()).append(CurrencyUtils.getCurrencyComponent(item.getSellPrice())).append(Component.text(" coins.", Color.SHOP_DESCRIPTION.asColor())));
        }


        lore = lore.stream().map(c -> c.decoration(TextDecoration.ITALIC, false)).toList();
    }

    @Override
    public List<Component> getLore() {
        return lore;
    }
}
