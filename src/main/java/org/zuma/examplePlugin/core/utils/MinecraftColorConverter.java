package org.zuma.examplePlugin.core.utils;

import de.bluecolored.bluemap.api.math.Color;

import java.util.HashMap;
import java.util.Map;

public class MinecraftColorConverter {

    private static final Map<Character, Color> COLOR_MAP = new HashMap<>();

    static {
        COLOR_MAP.put('0', new Color(0, 0, 0));         // Black
        COLOR_MAP.put('1', new Color(0, 0, 170));       // Dark Blue
        COLOR_MAP.put('2', new Color(0, 170, 0));       // Dark Green
        COLOR_MAP.put('3', new Color(0, 170, 170));     // Dark Aqua
        COLOR_MAP.put('4', new Color(170, 0, 0));       // Dark Red
        COLOR_MAP.put('5', new Color(170, 0, 170));     // Dark Purple
        COLOR_MAP.put('6', new Color(255, 170, 0));     // Gold
        COLOR_MAP.put('7', new Color(170, 170, 170));   // Gray
        COLOR_MAP.put('8', new Color(85, 85, 85));      // Dark Gray
        COLOR_MAP.put('9', new Color(85, 85, 255));     // Blue
        COLOR_MAP.put('a', new Color(85, 255, 85));     // Green
        COLOR_MAP.put('b', new Color(85, 255, 255));    // Aqua
        COLOR_MAP.put('c', new Color(255, 85, 85));     // Red
        COLOR_MAP.put('d', new Color(255, 85, 255));    // Light Purple
        COLOR_MAP.put('e', new Color(255, 255, 85));    // Yellow
        COLOR_MAP.put('f', new Color(255, 255, 255));   // White
    }

    public static Color fromMinecraftColorCode(String code, float alpha) {
        if (code == null || code.length() != 2 || code.charAt(0) != '§') {
            throw new IllegalArgumentException("Invalid Minecraft color code format");
        }
        if (alpha < 0 || alpha > 1) {
            throw new IllegalArgumentException("Alpha must be between 0 and 1");
        }
        char colorChar = Character.toLowerCase(code.charAt(1));
        Color baseColor = COLOR_MAP.get(colorChar);
        if (baseColor == null) {
            throw new IllegalArgumentException("Unknown Minecraft color code: " + code);
        }
        // Return new Color with the specified alpha
        return new Color(baseColor.getRed(), baseColor.getGreen(), baseColor.getBlue(), alpha);
    }

}