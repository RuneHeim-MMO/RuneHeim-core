package org.zuma.examplePlugin.domain.effect.effects;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import org.bukkit.Location;
import org.zuma.examplePlugin.core.constants.Color;
import org.zuma.examplePlugin.core.constants.SoundType;
import org.zuma.examplePlugin.domain.effect.AnimatedEffect;
import org.zuma.examplePlugin.domain.player.RunePlayer;

import java.time.Duration;

public class BombStorageUpdatedEffect implements AnimatedEffect {
    private final RunePlayer player;

    public BombStorageUpdatedEffect(RunePlayer player) {
        this.player = player;
    }

    @Override
    public void playAt(Location location, double time) {
        if (time < 0) return;
        if (player.asBukkitPlayer() == null || !player.asBukkitPlayer().isOnline()) return;

        if (time == 1) {
            // Play initial sound
            SoundType.PLAYER_LEVEL_UP.play(player, true);

            // Show level-up title
            player.asBukkitPlayer().showTitle(Title.title(
                    Component.text("Thank you for your support!", Color.SERVER_BRAND.asColor()),
                    Component.text("Your bombs have been updated", Color.SUCCESS.asColor()),
                    Title.Times.times(
                            Duration.ofSeconds(0),
                            Duration.ofSeconds(4, 0),
                            Duration.ofSeconds(2)
                    )
            ));
        }

    }

    @Override
    public int getDurationTicks() {
        return 100;
    }
}
