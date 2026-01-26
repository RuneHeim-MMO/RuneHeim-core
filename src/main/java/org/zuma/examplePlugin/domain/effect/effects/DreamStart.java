package org.zuma.examplePlugin.domain.effect.effects;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import org.bukkit.Location;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.zuma.examplePlugin.core.constants.Color;
import org.zuma.examplePlugin.core.constants.SoundType;
import org.zuma.examplePlugin.domain.effect.AnimatedEffect;
import org.zuma.examplePlugin.domain.player.RunePlayer;

import java.time.Duration;

public class DreamStart implements AnimatedEffect {
    private final RunePlayer player;

    public DreamStart(RunePlayer player) {
        this.player = player;
    }

    @Override
    public void playAt(Location location, double time) {
        if (!player.isOnline()) return;

        SoundType.DREAM_START.play(player, false);
        player.asBukkitPlayer().addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 60, 200, false, false, false));
        player.asBukkitPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, 60, 200, false, false, false));

        if (time == 30) {
            player.asBukkitPlayer().showTitle(Title.title(
                    Component.empty(),
                    Component.text("You close your eyes...", Color.DANGER.asColor()),
                    Title.Times.times(Duration.ofSeconds(1), Duration.ofSeconds(3, 5), Duration.ofSeconds(3)
                    )
            ));
        }


    }

    @Override
    public int getDurationTicks() {
        return 60;
    }
}
