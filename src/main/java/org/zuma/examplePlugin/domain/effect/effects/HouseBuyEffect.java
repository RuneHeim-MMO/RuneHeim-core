package org.zuma.examplePlugin.domain.effect.effects;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import org.bukkit.Location;
import org.zuma.examplePlugin.core.constants.Color;
import org.zuma.examplePlugin.core.constants.SoundType;
import org.zuma.examplePlugin.domain.effect.AnimatedEffect;
import org.zuma.examplePlugin.domain.housing.template.HouseTemplate;
import org.zuma.examplePlugin.domain.player.RunePlayer;
import org.zuma.examplePlugin.domain.quest.Quest;

import java.time.Duration;

public class HouseBuyEffect implements AnimatedEffect {
    private final RunePlayer player;
    private final HouseTemplate houseTemplate;

    public HouseBuyEffect(RunePlayer player, HouseTemplate houseTemplate) {
        this.player = player;
        this.houseTemplate = houseTemplate;

    }

    @Override
    public void playAt(Location location, double time) {
        if (player.asBukkitPlayer() == null || !player.asBukkitPlayer().isOnline()) return;

        if (time == 0.0) {
            player.asBukkitPlayer().showTitle(Title.title(
                    Component.text(houseTemplate.getTemplateName(), Color.ACCENT.asColor()),
                    Component.text("You have purchased this house!", Color.SUCCESS.asColor()),
                    Title.Times.times(Duration.ofSeconds(1), Duration.ofSeconds(4, 5), Duration.ofSeconds(3)
                    )
            ));


            SoundType.PLAYER_BUY_HOUSE.play(player, true);
            SoundType.PLAYER_BUY_HOUSE_2.play(player, true);
        }

    }

    @Override
    public int getDurationTicks() {
        return 10;
    }

}
