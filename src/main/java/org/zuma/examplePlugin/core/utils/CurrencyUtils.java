package org.zuma.examplePlugin.core.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.zuma.examplePlugin.core.constants.Color;
import org.zuma.examplePlugin.core.constants.Icon;

public class CurrencyUtils {


    public static String formatCurrencyString(long number) {
        if (number >= 1_000_000) {
            return String.format("%.1fM", number / 1_000_000.0).replaceAll("\\.0M", "M");
        } else if (number >= 1_000) {
            return String.format("%.1fk", number / 1_000.0).replaceAll("\\.0k", "k");
        } else {
            return String.valueOf(number);
        }
    }

    public static Component getCurrencyComponent(long number) {
        return Component.text(getCurrencyIcon(number).get(), NamedTextColor.WHITE)
                .append(Component.text(" " + formatCurrencyString(number), getCurrencyColor(number).asColor()));
    }

    public static Icon getCurrencyIcon(long number) {
        if (number >= 10_000) {
            return Icon.COIN_PILE;
        } else if (number >= 1_000) {
            return Icon.COIN_HANDFUL;
        } else if (number >= 100) {
            return Icon.COIN_TWO;
        } else {
            return Icon.COIN_ONE;
        }
    }

    public static Color getCurrencyColor(long number) {
        if (number >= 100_000) {
            return Color.CURRENCY_HIGH;
        } else if (number >= 10_000) {
            return Color.CURRENCY_MID;
        } else {
            return Color.CURRENCY_LOW;
        }
    }
}
