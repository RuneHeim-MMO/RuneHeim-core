package org.zuma.examplePlugin.domain.effect.effects;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.title.Title;
import org.bukkit.Location;
import org.zuma.examplePlugin.core.constants.Color;
import org.zuma.examplePlugin.core.constants.SoundType;
import org.zuma.examplePlugin.domain.effect.AnimatedEffect;
import org.zuma.examplePlugin.domain.player.RunePlayer;
import org.zuma.examplePlugin.domain.quest.Quest;

import java.time.Duration;

public class FirstJoinEffect implements AnimatedEffect {
    private final RunePlayer player;

    public FirstJoinEffect(RunePlayer player) {
        this.player = player;
    }

    @Override
    public void playAt(Location location, double time) {
        if (time == 30 && player.asBukkitPlayer().isOnline()) {

            player.asBukkitPlayer().showTitle(Title.title(
                    Component.text("Welcome to the ", NamedTextColor.WHITE).append(Component.text("Rune", NamedTextColor.RED)).append(Component.text("Heim", NamedTextColor.YELLOW)),
                    Component.text("Start by talking to the NPC", Color.ACCENT.asColor()),
                    Title.Times.times(Duration.ofSeconds(0), Duration.ofSeconds(6), Duration.ofSeconds(1)
                    )
            ));

            SoundType.PLAYER_QUEST_COMPLETE.play(player, false);
        }

        if (time == 159 && player.asBukkitPlayer() != null && player.asBukkitPlayer().isOnline()) {
            player.asBukkitPlayer().showTitle(Title.title(
                    Component.empty(),
                    Component.text("This server is running alpha version - expect bugs.", Color.SERVER_BRAND.asColor()),
                    Title.Times.times(Duration.ofSeconds(0), Duration.ofSeconds(4), Duration.ofSeconds(1)
                    )));
        }
    }

    @Override
    public int getDurationTicks() {
        return 160;
    }

}
