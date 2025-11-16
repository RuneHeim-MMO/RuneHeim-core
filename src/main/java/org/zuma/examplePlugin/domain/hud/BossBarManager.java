package org.zuma.examplePlugin.domain.hud;

import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.zuma.examplePlugin.domain.player.RunePlayer;

public class BossBarManager {
    private final RunePlayer player;
    private final BossBar bar;

    public BossBarManager(RunePlayer player) {
        this.player = player;

        BossBar bar = Bukkit.createBossBar(
                "RuneHeim",
                BarColor.RED,
                BarStyle.SEGMENTED_6
        );

        bar.addPlayer(player.asBukkitPlayer());
        bar.setVisible(true);

        this.bar = bar;
    }

    public void setText(String text) {
        this.bar.setTitle(text);
    }

    public void removeBar() {
        this.bar.removeAll();
        this.bar.setVisible(false);
    }
}
