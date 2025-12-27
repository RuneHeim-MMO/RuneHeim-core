package org.zuma.examplePlugin.domain.effect.effects;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import org.bukkit.Location;
import org.zuma.examplePlugin.core.constants.Color;
import org.zuma.examplePlugin.core.constants.SoundType;
import org.zuma.examplePlugin.domain.effect.AnimatedEffect;
import org.zuma.examplePlugin.domain.player.RunePlayer;
import org.zuma.examplePlugin.domain.player.states.TravelMode;

import java.time.Duration;

public class TravelStartEffect implements AnimatedEffect {
    private final RunePlayer player;
    private final TravelMode mode;

    public TravelStartEffect(RunePlayer player, TravelMode mode) {
        this.player = player;
        this.mode = mode;
    }

    @Override
    public void playAt(Location location, double time) {
        if (!player.isOnline()) return;

        if (time == 0) {
            player.asBukkitPlayer().showTitle(Title.title(
                    Component.empty(),
                    Component.text("You step onto the " + mode.getVehicleType() + ".", Color.ACCENT.asColor()),
                    Title.Times.times(Duration.ofSeconds(1), Duration.ofSeconds(3, 5), Duration.ofSeconds(3)
                    )
            ));

            if (mode == TravelMode.CARRIAGE) {
                SoundType.TRAVEL_CARRIAGE_DOOR_CLOSE.play(player, false);
            }
        }

        if (time % 3 == 0) {
            mode.getStepSound().play(player, false);
        }

        if (time == 15 && mode == TravelMode.SHIP) {
            SoundType.TRAVEL_SHIP_BELL.play(player, false);
        }

        if (time == 19 && mode == TravelMode.CARRIAGE) {
            SoundType.TRAVEL_CARRIAGE_DOOR_CLOSE.play(player, false);
        }

    }

    @Override
    public int getDurationTicks() {
        return 20;
    }
}
