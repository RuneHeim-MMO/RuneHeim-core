package org.zuma.examplePlugin.domain.particle.effects;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.constants.Color;
import org.zuma.examplePlugin.core.constants.SoundType;
import org.zuma.examplePlugin.core.utils.FireworkUtils;
import org.zuma.examplePlugin.core.utils.XpUtils;
import org.zuma.examplePlugin.domain.particle.AnimatedEffect;
import org.zuma.examplePlugin.domain.skill.Skill;
import org.zuma.examplePlugin.domain.skill.constants.SkillType;

import java.time.Duration;

public class LevelUpEffect implements AnimatedEffect {
    private final Player player;
    private final boolean isMaxedSkill;
    private final SkillType type;
    private final int count;
    private final int delayBetween; // ticks
    private int nextFireworkIndex = 0;

    public LevelUpEffect(Player player, Skill skill) {
        this.player = player;
        this.isMaxedSkill = XpUtils.isMaxLevel(skill.getBaseLevel());
        this.type = skill.getType();
        this.count = isMaxedSkill ? 50 : 5;
        this.delayBetween = 5;

        // Play initial sound
        SoundType.PLAYER_LEVEL_UP.play(player, true);

        if (!isMaxedSkill) {
            // Show level-up title
            player.showTitle(Title.title(
                    Component.text("Congratulations!", Color.SERVER_BRAND.asColor()),
                    Component.text("Your ", Color.SUCCESS.asColor())
                            .append(Component.text(type.getSymbol(), NamedTextColor.WHITE))
                            .append(Component.text(" " + type.getLabel() + " level is now " + skill.getBaseLevel() + "!", Color.SUCCESS.asColor())),
                    Title.Times.times(
                            Duration.ofSeconds(1),
                            Duration.ofSeconds(8, 5),
                            Duration.ofSeconds(3)
                    )
            ));
        } else {
            // Maxed skill sound after delay
            Bukkit.getScheduler().runTaskLater(RuneQuestPlugin.getInstance(), () -> {
                SoundType.PLAYER_MAX_SKILL.play(player, true);
            }, 40);

            // Maxed skill title
            Component congrats = Component.text("C").color(NamedTextColor.RED)
                    .append(Component.text("O").color(NamedTextColor.GOLD))
                    .append(Component.text("N").color(NamedTextColor.YELLOW))
                    .append(Component.text("G").color(NamedTextColor.GREEN))
                    .append(Component.text("R").color(NamedTextColor.AQUA))
                    .append(Component.text("A").color(NamedTextColor.DARK_AQUA))
                    .append(Component.text("T").color(NamedTextColor.BLUE))
                    .append(Component.text("U").color(NamedTextColor.LIGHT_PURPLE))
                    .append(Component.text("L").color(NamedTextColor.DARK_PURPLE))
                    .append(Component.text("A").color(NamedTextColor.DARK_RED))
                    .append(Component.text("T").color(NamedTextColor.DARK_GREEN))
                    .append(Component.text("I").color(NamedTextColor.DARK_GRAY))
                    .append(Component.text("O").color(NamedTextColor.WHITE))
                    .append(Component.text("N").color(NamedTextColor.GRAY))
                    .append(Component.text("S").color(NamedTextColor.GREEN))
                    .append(Component.text("!").color(NamedTextColor.GOLD));

            player.showTitle(Title.title(
                    congrats,
                    Component.text("No way! You have MAXED your ", Color.SUCCESS.asColor())
                            .append(Component.text(type.getSymbol(), NamedTextColor.WHITE))
                            .append(Component.text(" " + type.getLabel() + " level!", Color.SUCCESS.asColor())),
                    Title.Times.times(
                            Duration.ofSeconds(1),
                            Duration.ofSeconds(10),
                            Duration.ofSeconds(3)
                    )
            ));
        }
    }

    @Override
    public void playAt(Location location, double time) {
        // Determine which fireworks to spawn based on time
        while (nextFireworkIndex < count && time >= nextFireworkIndex * delayBetween) {
            spawnFirework();
            nextFireworkIndex++;
        }
    }

    private void spawnFirework() {
        if (!player.isOnline()) return;

        Location loc = player.getLocation().add(0, 1, 0);
        Firework firework = loc.getWorld().spawn(loc, Firework.class);

        FireworkMeta meta = firework.getFireworkMeta();
        meta.setPower(isMaxedSkill ? 2 : 0);

        if (isMaxedSkill) {
            meta.addEffect(FireworkUtils.getRandomEffect());
        } else {
            meta.addEffect(FireworkEffect.builder()
                    .withColor(org.bukkit.Color.AQUA)
                    .withFade(org.bukkit.Color.WHITE)
                    .with(FireworkEffect.Type.BURST)
                    .flicker(true)
                    .trail(true)
                    .build());
        }

        firework.setFireworkMeta(meta);
    }

    @Override
    public int getDurationTicks() {
        return count * delayBetween;
    }

}
