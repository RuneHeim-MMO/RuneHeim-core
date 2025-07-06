package org.zuma.examplePlugin.core.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.zuma.examplePlugin.core.constants.Color;
import org.zuma.examplePlugin.core.constants.Icon;

import javax.annotation.Nullable;

public class CurrencyUtils {


    public static String formatCurrencyString(long number) {
        if (number >= 1_000_000) {
            double value = Math.floor(number / 10_000.0) / 100.0; // e.g., 1,234,567 → 1.23M
            return stripTrailingZeros(value) + "M";
        } else if (number >= 1_000) {
            double value = Math.floor(number / 10.0) / 100.0; // e.g., 1,250 → 1.25k
            return stripTrailingZeros(value) + "k";
        } else {
            return String.valueOf(number);
        }
    }

    private static String stripTrailingZeros(double value) {
        if (value % 1 == 0) {
            return String.valueOf((int) value); // No decimals needed
        } else if ((value * 10) % 1 == 0) {
            return String.format("%.1f", value); // One decimal needed
        } else {
            return String.format("%.2f", value); // Two decimals needed
        }
    }

    public static Component getCurrencyComponent(long number) {
        return getCurrencyComponent(number, null);
    }

    public static Component getCurrencyComponent(long number, @Nullable String suffix) {
        String suffixFormatted = suffix == null ? "" : " " + suffix;

        return Component.text(getCurrencyIcon(number).get(), NamedTextColor.WHITE)
                .append(Component.text(" " + formatCurrencyString(number) + suffixFormatted, getCurrencyColor(number).asColor()));
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
