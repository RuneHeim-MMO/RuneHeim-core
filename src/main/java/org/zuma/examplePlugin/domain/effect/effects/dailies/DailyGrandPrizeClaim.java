package org.zuma.examplePlugin.domain.effect.effects.dailies;

import org.bukkit.Location;
import org.bukkit.entity.Firework;
import org.bukkit.inventory.meta.FireworkMeta;
import org.zuma.examplePlugin.core.utils.FireworkUtils;
import org.zuma.examplePlugin.domain.effect.AnimatedEffect;
import org.zuma.examplePlugin.domain.player.RunePlayer;

public class DailyGrandPrizeClaim implements AnimatedEffect {
    private final RunePlayer player;

    public DailyGrandPrizeClaim(RunePlayer player) {
        this.player = player;
    }

    @Override
    public void playAt(Location location, double time) {
        if (!player.isOnline()) {
            return;
        }

        if (time % 25 == 0) {
            Location loc = player.asBukkitPlayer().getLocation().add(0, 1, 0);
            Firework firework = loc.getWorld().spawn(loc, Firework.class);

            FireworkMeta meta = firework.getFireworkMeta();
            meta.setPower(1);

            meta.addEffect(FireworkUtils.getRandomEffect());
            firework.setFireworkMeta(meta);
        }


    }

    @Override
    public int getDurationTicks() {
        return 100;
    }

}
