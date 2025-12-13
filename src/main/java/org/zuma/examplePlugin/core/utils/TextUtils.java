package org.zuma.examplePlugin.core.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import net.kyori.adventure.title.Title;
import org.jetbrains.annotations.Nullable;
import org.zuma.examplePlugin.core.constants.Color;
import org.zuma.examplePlugin.core.constants.Icon;
import org.zuma.examplePlugin.domain.player.RunePlayer;
import org.zuma.examplePlugin.domain.territory.Territory;

import java.text.DecimalFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TextUtils {
    private final static DecimalFormat df = new DecimalFormat("#,###");

    private static List<Component> buildTerritoryComponents(RunePlayer player, Territory territory) {
        int area = (int) (Math.PI * territory.getRadius() * territory.getRadius());
        boolean isUnlocked = player.getDiscoveredTerritoryIds().contains(territory.getId());

        return List.of(
                Component.text(territory.getDescription(), Color.ITEM_LORE_DESCRIPTION.asColor()),

                Component.text("Area: ", Color.ITEM_LORE_DESCRIPTION.asColor())
                        .append(Component.text(df.format(area) + " blocks.", Color.INFO.asColor())),

                Component.empty(),

                Component.text(isUnlocked ? "Discovered" : "Not Discovered")
                        .color(isUnlocked ? Color.SUCCESS.asColor() : Color.ERROR.asColor()),

                Component.text("Level Range: ", Color.ITEM_LORE_DESCRIPTION.asColor())
                        .append(Component.text(TextUtils.getLegacyColorCodeForLevel(territory.getMinLevel()) + territory.getMinLevel()))
                        .append(Component.text(" - ", Color.ITEM_LORE_DESCRIPTION.asColor()))
                        .append(Component.text(TextUtils.getLegacyColorCodeForLevel(territory.getMaxLevel()) + territory.getMaxLevel()))
        );
    }

    public static List<Component> getLoreTerritoryDescription(RunePlayer player, Territory territory) {
        return buildTerritoryComponents(player, territory);
    }

    public static Component getChatTerritoryHoverComponent(RunePlayer player, Territory territory) {
        List<Component> components = buildTerritoryComponents(player, territory);
        Component result = Component.empty();
        for (Component c : components) {
            result = result.append(c).append(Component.newline());
        }
        return result;
    }

    public static String centerText(String text, int width) {
        String plainText = text.replaceAll("§.", "");
        int textLength = plainText.length();

        if (textLength >= width) {
            return text;
        }

        int totalPadding = width - textLength;
        int paddingLeft = totalPadding / 2 + (totalPadding % 2); // Extra space to the left if odd
        int paddingRight = totalPadding / 2;

        return " ".repeat(paddingLeft) + text + " ".repeat(paddingRight);
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

    public static Component getErrorKickScreen(String error, boolean unexpected) {
        return TextUtils.getBrandKickScreen(Component.text(error + (unexpected ? "\n\nPlease report this issue. Incident ID #" + System.currentTimeMillis() : "")).color(Color.ERROR.asColor()));
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

    public static String getLegacyColorCodeForPercent(double percent) {
        if (percent <= 0) return "§c";      // red
        if (percent < 25) return "§6";      // gold (orange/gold)
        if (percent < 50) return "§e";      // yellow
        if (percent < 70) return "§2";      // dark green
        if (percent < 85) return "§a";      // green
        if (percent <= 100) return "§b";     // aqua (bright greenish/blue)
        return "§f";
    }

    public static Component getLevelGradientComponent(int level) {
        // Gradient stops
        int[] stops = {10, 25, 50, 75, 100};
        TextColor[] colors = {
                TextColor.color(0x55FF55), // green
                TextColor.color(0x00AA00), // dark green
                TextColor.color(0xFFFF55), // yellow
                TextColor.color(0xFFAA00), // gold
                TextColor.color(0xFF5555)  // red
        };

        // Below first stop
        if (level <= stops[0]) {
            return Component.text(String.valueOf(level), colors[0]);
        }

        // Above last stop
        if (level >= stops[stops.length - 1]) {
            return Component.text(String.valueOf(level), colors[colors.length - 1]);
        }

        // Find which two stops the level is between
        int startIndex = 0;
        while (startIndex < stops.length && level > stops[startIndex]) startIndex++;
        int endIndex = startIndex;
        startIndex--;

        int startLevel = stops[startIndex];
        int endLevel = stops[endIndex];
        TextColor startColor = colors[startIndex];
        TextColor endColor = colors[endIndex];

        // Interpolate
        float ratio = (float) (level - startLevel) / (endLevel - startLevel);
        int r = (int) (startColor.red() + (endColor.red() - startColor.red()) * ratio);
        int g = (int) (startColor.green() + (endColor.green() - startColor.green()) * ratio);
        int b = (int) (startColor.blue() + (endColor.blue() - startColor.blue()) * ratio);

        return Component.text(String.valueOf(level), TextColor.color(r, g, b));
    }

    public static String replaceColorCodes(String input) {
        return input.replaceAll("§.", "").replaceAll("&.", "");
    }

    public static String replaceAllIcons(String input) {
        for (Icon icon : Icon.values()) {
            input = input.replace(icon.get(), "");
        }

        return input;
    }

    public static List<Component> splitLore(
            String text,
            int maxWordsPerLine,
            TextColor color
    ) {
        List<Component> lines = new ArrayList<>();

        String[] words = text.split("\\s+");
        StringBuilder currentLine = new StringBuilder();
        int wordCount = 0;

        for (String word : words) {
            if (wordCount == maxWordsPerLine) {
                lines.add(
                        Component.text(currentLine.toString(), color)
                                .decoration(TextDecoration.ITALIC, false)
                );
                currentLine.setLength(0);
                wordCount = 0;
            }

            if (!currentLine.isEmpty()) {
                currentLine.append(" ");
            }

            currentLine.append(word);
            wordCount++;
        }

        if (!currentLine.isEmpty()) {
            lines.add(
                    Component.text(currentLine.toString(), color)
                            .decoration(TextDecoration.ITALIC, false)
            );
        }

        return lines;
    }

}