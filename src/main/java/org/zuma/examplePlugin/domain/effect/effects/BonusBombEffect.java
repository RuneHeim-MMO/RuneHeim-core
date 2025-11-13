package org.zuma.examplePlugin.domain.effect.effects;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.FireworkEffect;
import org.bukkit.inventory.meta.FireworkMeta;
import org.zuma.examplePlugin.core.constants.Color;
import org.zuma.examplePlugin.domain.effect.AnimatedEffect;
import org.zuma.examplePlugin.domain.modifier.scopes.server.ServerModifierType;
import org.zuma.examplePlugin.domain.player.RunePlayer;

import java.time.Duration;

public class BonusBombEffect implements AnimatedEffect {
    private final RunePlayer player;
    private final ServerModifierType bombType;

    public BonusBombEffect(RunePlayer player, ServerModifierType bombType) {
        this.player = player;
        this.bombType = bombType;
    }

    @Override
    public void playAt(Location location, double time) {
        if (time < 0) return;
        if (player.asBukkitPlayer() == null || !player.asBukkitPlayer().isOnline()) return;
        if (location.getWorld() == null) return;

        // initial throw / ignite
        if (time == 0) {
            location.getWorld().playSound(location, Sound.ENTITY_SNOWBALL_THROW, 1.0f, 1.0f);
            location.getWorld().playSound(location, Sound.ENTITY_TNT_PRIMED, 0.8f, 1.0f);
            location.getWorld().spawnParticle(Particle.LARGE_SMOKE, location.clone().add(0, 1, 0), 10, 0.25, 0.25, 0.25, 0.01);
        }

        if (time == 0) {
            player.asBukkitPlayer().showTitle(Title.title(
                    Component.text("3...", Color.SUCCESS.asColor()),
                    Component.text(bombType.getLabel()).color(Color.SERVER_BRAND.asColor()),
                    Title.Times.times(
                            Duration.ZERO,
                            Duration.ofSeconds(8, 5),
                            Duration.ofMillis(200)
                    )
            ));
        }

        if (time == 20) {
            player.asBukkitPlayer().showTitle(Title.title(
                    Component.text("2...", Color.SUCCESS.asColor()),
                    Component.text(bombType.getLabel()).color(Color.SERVER_BRAND.asColor()),
                    Title.Times.times(
                            Duration.ZERO,
                            Duration.ofSeconds(8, 5),
                            Duration.ofMillis(200)
                    )
            ));
        }

        if (time == 40) {
            player.asBukkitPlayer().showTitle(Title.title(
                    Component.text("1...", Color.SUCCESS.asColor()),
                    Component.text(bombType.getLabel()).color(Color.SERVER_BRAND.asColor()),
                    Title.Times.times(
                            Duration.ZERO,
                            Duration.ofSeconds(8, 5),
                            Duration.ofMillis(200)
                    )
            ));
        }

        if (time >= 0 && time < 55 && time % 5 == 0) {
            location.getWorld().spawnParticle(Particle.LARGE_SMOKE, location.clone().add(0, 1, 0), 10, 0.25, 0.25, 0.25, 0.01);
        }

        // explosion moment: loud explosion + visual + green firework + XP orbs
        if (time == 55) {
            location.getWorld().spawnParticle(Particle.EXPLOSION, location, 1);
            location.getWorld().playSound(location, Sound.ENTITY_GENERIC_EXPLODE, 1.2f, 1.0f);

            // spawn a firework, set it to green, and detonate immediately for the visual burst
            Firework fw = (Firework) location.getWorld().spawnEntity(location, EntityType.FIREWORK_ROCKET);
            FireworkMeta meta = fw.getFireworkMeta();
            org.bukkit.Color color;
            org.bukkit.Color fade;

            switch (bombType) {
                case DOUBLE_XP -> {
                    color = org.bukkit.Color.LIME;
                    fade = org.bukkit.Color.GREEN;
                }
                case INCREASED_MOB_DROPS -> {
                    color = org.bukkit.Color.RED;
                    fade = org.bukkit.Color.ORANGE;
                }
                case INCREASED_RESOURCE_DROPS -> {
                    color = org.bukkit.Color.YELLOW;
                    fade = org.bukkit.Color.WHITE;
                }
                case INCREASED_PERFECT_STRIKE -> {
                    color = org.bukkit.Color.AQUA;
                    fade = org.bukkit.Color.BLUE;
                }
                default -> {
                    color = org.bukkit.Color.GREEN;
                    fade = org.bukkit.Color.GREEN;
                }
            }

            meta.addEffect(
                    FireworkEffect.builder()
                            .with(FireworkEffect.Type.BALL_LARGE)
                            .withColor(color)
                            .withFade(fade)
                            .trail(true)
                            .flicker(true)
                            .build()
            );

            meta.setPower(0);
            fw.setFireworkMeta(meta);
            fw.detonate();

        }
    }

    @Override
    public int getDurationTicks() {
        return 60;
    }
}
