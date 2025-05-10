package org.zuma.examplePlugin.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.zuma.examplePlugin.constants.Colors;

public class TextUtils {

    public static Component centerText(String text, int width) {
        // Calculate how much padding is needed on the left to center the text
        int textLength = text.length();
        int paddingLength = (width - textLength) / 2;

        // Create a string with the necessary spaces (padding) and append the original text
        String paddedText = " ".repeat(Math.max(0, paddingLength)) + text;

        // Return the text as a Component with center alignment (you can apply colors here too)
        return Component.text(paddedText);
    }

    public static String repeatString(String text, int times) {
        return String.valueOf(text).repeat(Math.max(0, times));
    }

    public static Component getBrandKickScreen(Component component) {
        return Component.text("RuneQuest").color(Colors.SERVER_BRAND.asColor()).decorate(TextDecoration.BOLD)
                .append(Component.text("\n\n"))
                .append(component
                        .decoration(TextDecoration.STRIKETHROUGH, TextDecoration.State.FALSE)
                        .decoration(TextDecoration.BOLD, TextDecoration.State.FALSE))
                .append(Component.text("\n\n"))
                .append(Component.text("@ www.runequest.com @").color(Colors.SERVER_BRAND.asColor()));
    }

    public static Component getErrorKickScreen(String error) {
        return TextUtils.getBrandKickScreen(Component.text(error).color(Colors.ERROR.asColor()));
    }

    public static Component resetTextDecoration(Component component) {
        return component.style(style -> style
                .decoration(TextDecoration.BOLD, TextDecoration.State.NOT_SET)
                .decoration(TextDecoration.ITALIC, TextDecoration.State.NOT_SET)
                .decoration(TextDecoration.UNDERLINED, TextDecoration.State.NOT_SET)
                .decoration(TextDecoration.STRIKETHROUGH, TextDecoration.State.NOT_SET)
                .color(null) // Resets color, or you can set to a default like TextColor.color(WHITE)
        );
    }
}