package org.zuma.examplePlugin.domain.hud;

import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
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

    public void setDefault() {
        this.bar.setTitle("RuneHeim");
        this.setProgress(1.0f);
    }

    public void setText(String text) {
        this.bar.setTitle(text);
    }

    public void setProgress(float progress) {
        if (progress == 0f) {
            progress = 0.01f;
        }

        if (!bar.getTitle().equals("RuneHeim")) {
            if (progress < 0.7f) {
                this.bar.setColor(BarColor.RED);
            } else if (progress < 0.9f) {
                this.bar.setColor(BarColor.YELLOW);
            } else {
                this.bar.setColor(BarColor.GREEN);
            }
        }


        this.bar.setProgress(progress);
    }

    public void removeBar() {
        this.bar.removeAll();
        this.bar.setVisible(false);
    }
}
