package org.zuma.examplePlugin.core.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import net.kyori.adventure.title.Title;
import org.jetbrains.annotations.Nullable;
import org.zuma.examplePlugin.core.constants.Color;

import java.time.Duration;

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
        return Component.text("RuneQuest").color(Color.SERVER_BRAND.asColor()).decorate(TextDecoration.BOLD)
                .append(Component.text("\n\n"))
                .append(component
                        .decoration(TextDecoration.STRIKETHROUGH, TextDecoration.State.FALSE)
                        .decoration(TextDecoration.BOLD, TextDecoration.State.FALSE))
                .append(Component.text("\n\n"))
                .append(Component.text("@ www.runequest.com @").color(Color.SERVER_BRAND.asColor()));
    }

    public static Component getErrorKickScreen(String error) {
        return TextUtils.getBrandKickScreen(Component.text(error).color(Color.ERROR.asColor()));
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

    public static String componentToColoredString(Component component) {
        StringBuilder sb = new StringBuilder();
        appendComponent(sb, component);
        return sb.toString();
    }

    private static void appendComponent(StringBuilder sb, Component component) {
        // Get color code for this component
        String colorCode = getColorCode(component.color());
        if (colorCode != null) {
            sb.append(colorCode);
        }

        // Append decoration codes if present
        if (component.hasDecoration(TextDecoration.BOLD)) {
            sb.append("§l");
        }
        if (component.hasDecoration(TextDecoration.ITALIC)) {
            sb.append("§o");
        }
        if (component.hasDecoration(TextDecoration.UNDERLINED)) {
            sb.append("§n");
        }
        if (component.hasDecoration(TextDecoration.STRIKETHROUGH)) {
            sb.append("§m");
        }
        if (component.hasDecoration(TextDecoration.OBFUSCATED)) {
            sb.append("§k");
        }

        // Append the actual text content of this component
        String content = component instanceof net.kyori.adventure.text.TextComponent
                ? ((net.kyori.adventure.text.TextComponent) component).content()
                : "";
        sb.append(content);

        // Recursively process children
        for (Component child : component.children()) {
            appendComponent(sb, child);
        }

        // Reset format after this component to avoid bleed to following text
        sb.append("§r");
    }

    private static String getColorCode(@Nullable TextColor color) {
        if (color == null) return null;

        // Example mapping of TextColor to Minecraft § codes (basic colors only)
        int rgb = color.value();
        switch (rgb) {
            case 0x000000:
                return "§0"; // black
            case 0x0000AA:
                return "§1"; // dark blue
            case 0x00AA00:
                return "§2"; // dark green
            case 0x00AAAA:
                return "§3"; // dark aqua
            case 0xAA0000:
                return "§4"; // dark red
            case 0xAA00AA:
                return "§5"; // dark purple
            case 0xFFAA00:
                return "§6"; // gold
            case 0xAAAAAA:
                return "§7"; // gray
            case 0x555555:
                return "§8"; // dark gray
            case 0x5555FF:
                return "§9"; // blue
            case 0x55FF55:
                return "§a"; // green
            case 0x55FFFF:
                return "§b"; // aqua
            case 0xFF5555:
                return "§c"; // red
            case 0xFF55FF:
                return "§d"; // light purple
            case 0xFFFF55:
                return "§e"; // yellow
            case 0xFFFFFF:
                return "§f"; // white
            default:
                // For custom colors, Adventure supports RGB with §x§R§R§G§G§B§B format
                return toMinecraftHexColorCode(rgb);
        }
    }

    // Convert int RGB (0xRRGGBB) to Minecraft hex color code §x§R§R§G§G§B§B
    private static String toMinecraftHexColorCode(int rgb) {
        char[] hexChars = String.format("%06X", rgb).toCharArray();
        return "§x§" + hexChars[0] + "§" + hexChars[1] +
                "§" + hexChars[2] + "§" + hexChars[3] +
                "§" + hexChars[4] + "§" + hexChars[5];
    }

    public static String getLegacyColorCodeForLevel(int level) {
        if (level < 10) return "§a"; // green
        if (level < 25) return "§2"; // dark green
        if (level < 50) return "§e"; // yellow
        if (level < 75) return "§6"; // gold
        return "§c";                 // red
    }
}