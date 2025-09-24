package org.zuma.examplePlugin.domain.effect.effects;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import org.bukkit.Location;
import org.zuma.examplePlugin.core.constants.Color;
import org.zuma.examplePlugin.core.constants.SoundType;
import org.zuma.examplePlugin.domain.effect.AnimatedEffect;
import org.zuma.examplePlugin.domain.effect.StaticEffect;
import org.zuma.examplePlugin.domain.player.RunePlayer;
import org.zuma.examplePlugin.domain.quest.Quest;

import java.time.Duration;

public class TravelStartEffect implements AnimatedEffect {
    private final RunePlayer player;

    public TravelStartEffect(RunePlayer player) {
        this.player = player;
    }

    @Override
    public void playAt(Location location, double time) {
        if (time == 0) {
            player.asBukkitPlayer().showTitle(Title.title(
                    Component.empty(),
                    Component.text("You step onto the carriage.", Color.ACCENT.asColor()),
                    Title.Times.times(Duration.ofSeconds(1), Duration.ofSeconds(3, 5), Duration.ofSeconds(3)
                    )
            ));
            SoundType.TRAVEL_DOOR_CLOSE.play(player, false);
        }

        if (time % 3 == 0) {
            SoundType.TRAVEL_CARRIAGE_STEP.play(player, false);
        }

        if (time == 19) {
            SoundType.TRAVEL_DOOR_CLOSE.play(player, false);
        }

    }

    @Override
    public int getDurationTicks() {
        return 20;
    }
}
