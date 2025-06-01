package org.zuma.examplePlugin.core.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import net.kyori.adventure.title.Title;
import org.zuma.examplePlugin.core.constants.Colors;

import java.time.Duration;

public class TextUtils {
    private static final char ZWSP = '\u200B'; // zero-width space = 0
    private static final char ZWNJ = '\u200C'; // zero-width non-joiner = 1

    public static String encodeInvisibleId(int id) {
        String binary = Integer.toBinaryString(id);
        StringBuilder encoded = new StringBuilder();
        for (char c : binary.toCharArray()) {
            encoded.append(c == '0' ? ZWSP : ZWNJ);
        }
        return encoded.toString();
    }

    public static Integer decodeInvisibleId(String title) {
        StringBuilder binary = new StringBuilder();
        for (char c : title.toCharArray()) {
            if (c == ZWSP) binary.append('0');
            else if (c == ZWNJ) binary.append('1');
        }
        if (binary.isEmpty()) return null;
        return Integer.parseInt(binary.toString(), 2);
    }

    public static String appendInvisibleId(String title, int id) {
        return title + encodeInvisibleId(id);
    }

    public static Integer extractInvisibleId(String title) {
        return decodeInvisibleId(title);
    }

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

    public static Component buildLoadingBar(int totalSteps, int currentStep) {
        Component bar = Component.text("[ ").color(NamedTextColor.GRAY);

        for (int i = 1; i <= totalSteps; i++) {
            if (i <= currentStep) {
                bar = bar.append(Component.text("|||").color(Colors.SUCCESS.asColor()));
            } else {
                bar = bar.append(Component.text("|||").color(NamedTextColor.GRAY));
            }
        }

        bar = bar.append(Component.text(" ]").color(NamedTextColor.GRAY));
        return bar;
    }

    public static Title.Times getGenericDuration() {
        return Title.Times.times(
                Duration.ZERO,
                Duration.ofSeconds(1, 5),
                Duration.ofMillis(500)
        );
    }

    public static String extractPlainText(Component component) {
        return PlainTextComponentSerializer.plainText().serialize(component);
    }
}