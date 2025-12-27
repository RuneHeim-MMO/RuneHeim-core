package org.zuma.examplePlugin.domain.effect.effects;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import org.bukkit.Location;
import org.zuma.examplePlugin.core.constants.Color;
import org.zuma.examplePlugin.core.constants.SoundType;
import org.zuma.examplePlugin.core.permissions.roles.UpgradedRole;
import org.zuma.examplePlugin.domain.effect.AnimatedEffect;
import org.zuma.examplePlugin.domain.player.RunePlayer;

import java.time.Duration;

public class RankUpgradeEffect implements AnimatedEffect {
    private final RunePlayer player;

    public RankUpgradeEffect(RunePlayer player) {
        this.player = player;
    }

    @Override
    public void playAt(Location location, double time) {
        if (time < 0) return;
        if (!player.isOnline()) return;


        if (time == 1) {
            // Play initial sound
            SoundType.PLAYER_LEVEL_UP.play(player, true);

            // Show level-up title
            player.asBukkitPlayer().showTitle(Title.title(
                    Component.text("Thank you for your support!", Color.SERVER_BRAND.asColor()),
                    Component.text("Your rank has been upgraded!", Color.SUCCESS.asColor()),
                    Title.Times.times(
                            Duration.ofSeconds(0),
                            Duration.ofSeconds(4, 0),
                            Duration.ofSeconds(2)
                    )
            ));
        }

        if (time == 110) {
            UpgradedRole role = player.getRole().getUpgradedRole();
            if (role == null) return;

            SoundType.SUPPORTER_LEVEL_APPEAR.play(player, true);
            player.asBukkitPlayer().showTitle(Title.title(
                    role.getPrefix(),
                    Component.text("New Rank"),
                    Title.Times.times(
                            Duration.ofSeconds(0),
                            Duration.ofSeconds(6, 0),
                            Duration.ofSeconds(2)
                    )
            ));
        }
    }

    @Override
    public int getDurationTicks() {
        return 240;
    }
}
