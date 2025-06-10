package org.zuma.examplePlugin.core.utils;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;

import java.util.Random;

public class FireworkUtils {
    private static final Random random = new Random();
    private static final Color[] COLORS = {
            Color.RED, Color.BLUE, Color.AQUA, Color.GREEN, Color.YELLOW,
            Color.FUCHSIA, Color.LIME, Color.ORANGE, Color.PURPLE, Color.WHITE
    };

    private static final Type[] TYPES = Type.values();

    public static FireworkEffect getRandomEffect() {
        Color color = COLORS[random.nextInt(COLORS.length)];
        Color fade = COLORS[random.nextInt(COLORS.length)];
        Type type = TYPES[random.nextInt(TYPES.length)];

        return FireworkEffect.builder()
                .withColor(color)
                .withFade(fade)
                .with(type)
                .flicker(random.nextBoolean())
                .trail(random.nextBoolean())
                .build();
    }
}
