package org.zuma.examplePlugin.domain.effect.effects;

import net.kyori.adventure.text.Component;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.Item;
import org.bukkit.util.Vector;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.constants.SoundType;
import org.zuma.examplePlugin.domain.effect.AnimatedEffect;
import org.zuma.examplePlugin.domain.hud.HudService;
import org.zuma.examplePlugin.domain.item.constants.ItemRarity;
import org.zuma.examplePlugin.domain.player.RunePlayer;

public class RareMobDropEffect implements AnimatedEffect {
    private final int durationTicks = 100;
    private final Item droppedItem;
    private final RunePlayer killer;
    private final ItemRarity rarity;
    private final String mobName;

    private final int riseTicks = 40;
    private final int hoverTicks = 20;
    private final double peakHeight = 2.5;

    private boolean started = false;
    private boolean finished = false;
    private boolean gravityEnabled = false;

    private Location base;

    // For smooth velocity updates between calls
    private double lastTime = 0.0;
    private double lastYOffset = 0.0;

    public RareMobDropEffect(Item droppedItem, RunePlayer killer, ItemRarity rarity, String mobName) {
        this.droppedItem = droppedItem;
        this.killer = killer;
        this.rarity = rarity;
        this.mobName = mobName;
    }

    public static void spawnHexDust(Location loc, String hex, float size) {
        World w = loc.getWorld();
        if (w == null) return;

        if (hex.startsWith("#")) hex = hex.substring(1);
        int rgb = Integer.parseInt(hex, 16);

        int r = (rgb >> 16) & 0xFF;
        int g = (rgb >> 8) & 0xFF;
        int b = rgb & 0xFF;

        Particle.DustOptions dust = new Particle.DustOptions(Color.fromRGB(r, g, b), size);

        w.spawnParticle(Particle.DUST, loc, 5, 0.15, 0.15, 0.15, 0.0, dust);
    }

    private static double easeOutCubic(double t) {
        double p = 1.0 - t;
        return 1.0 - (p * p * p);
    }

    @Override
    public void playAt(Location location, double time) {
        if (finished) return;
        if (location == null || droppedItem == null || droppedItem.isDead() || !droppedItem.isValid()) {
            finished = true;
            return;
        }

        double elapsed = Math.max(0.0, Math.min(durationTicks, time));

        if (!started) {
            started = true;
            base = location.clone();

            droppedItem.teleport(base);
            droppedItem.setGravity(false);
            droppedItem.setPickupDelay(durationTicks + 20);
            droppedItem.setGlowing(true);
            SoundType.RARE_MOB_DROP_SPAWN.play(base);

            droppedItem.setVelocity(new Vector(0, 0, 0));

            lastTime = elapsed;
            lastYOffset = 0.0;

            RuneQuestPlugin.getPlayerManager().getOnlinePlayers().forEach(player -> {
                HudService.sendAnnouncementMessage(player, Component.text(killer.asBukkitPlayer().getName() + " got a rare drop from " + mobName + ": ", org.zuma.examplePlugin.core.constants.Color.WARNING.asColor())
                        .append(Component.text(droppedItem.getItemStack().getAmount() + "x ", org.zuma.examplePlugin.core.constants.Color.ACCENT.asColor()))
                        .append(droppedItem.getItemStack().displayName())
                        .append(Component.text("!", org.zuma.examplePlugin.core.constants.Color.ACCENT.asColor())));
            });
        }

        // End
        if (elapsed >= durationTicks) {
            cleanup();
            SoundType.RARE_MOB_DROP_FALL.play(base);
            finished = true;
            return;
        }

        double riseEnd = riseTicks;
        double hoverEnd = riseTicks + hoverTicks;

        // If we're already letting gravity handle it, nothing to do
        if (gravityEnabled) return;

        // Compute dt since last call (works regardless of being called every 1 or 2 ticks)
        double dt = elapsed - lastTime;
        if (dt <= 0.0) dt = 1.0;

        if (elapsed <= riseEnd) {
            // Rise: smooth position curve -> velocity derived from delta
            double t = elapsed / (double) riseTicks;          // 0..1
            double y = peakHeight * easeOutCubic(t);          // desired offset

            double dy = y - lastYOffset;
            double vy = dy / dt;

            // Safety clamp so it doesn't launch into orbit if something weird happens
            vy = Math.max(-1.0, Math.min(1.0, vy));

            droppedItem.setVelocity(new Vector(0, vy, 0));

            spawnHexDust(droppedItem.getLocation(), rarity.getHexColor(), 1f);

            lastYOffset = y;
            lastTime = elapsed;
            return;
        }

        if (elapsed <= hoverEnd) {
            // Hover: stop movement, keep it glowing
            droppedItem.setVelocity(new Vector(0, 0, 0));

            // Optional: lightly correct drift (rare, but can happen with collisions)
            Location target = base.clone().add(0, peakHeight, 0);
            if (droppedItem.getLocation().distanceSquared(target) > 0.05) {
                droppedItem.teleport(target);
            }

            spawnHexDust(droppedItem.getLocation(), rarity.getHexColor(), 1f);

            lastTime = elapsed;
            lastYOffset = peakHeight;
            return;
        }

        // Fall: flip gravity on ONCE and let physics do the rest
        gravityEnabled = true;

        droppedItem.setVelocity(new Vector(0, 0, 0));
        droppedItem.setGlowing(false);
        droppedItem.setGravity(true);

        SoundType.RARE_MOB_DROP_FALL.play(base);
    }

    private void cleanup() {
        droppedItem.setGlowing(false);
        droppedItem.setPickupDelay(20);
        droppedItem.setGravity(true);
    }

    @Override
    public int getDurationTicks() {
        return durationTicks;
    }
}
