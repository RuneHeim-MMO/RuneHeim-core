package org.zuma.examplePlugin.domain.hud;

import eu.decentsoftware.holograms.api.DHAPI;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.constants.Color;
import org.zuma.examplePlugin.core.constants.Icon;
import org.zuma.examplePlugin.core.constants.SoundType;
import org.zuma.examplePlugin.core.constants.XpSource;
import org.zuma.examplePlugin.core.interfaces.CombatEntity;
import org.zuma.examplePlugin.core.utils.FireworkUtils;
import org.zuma.examplePlugin.core.utils.TextUtils;
import org.zuma.examplePlugin.core.utils.XpUtils;
import org.zuma.examplePlugin.domain.combat.AttackType;
import org.zuma.examplePlugin.domain.combat.Hit;
import org.zuma.examplePlugin.domain.player.RunePlayer;
import org.zuma.examplePlugin.domain.skill.Skill;
import org.zuma.examplePlugin.domain.skill.constants.SkillType;

import java.time.Duration;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class HudService {
    private static final Random random = new Random();

    public static void showXpGainMessage(RunePlayer player, SkillType type, double xp, XpSource source) {
        String xpStr = "§f" + type.getSymbol() + Color.SUCCESS.asSpigotHex() + " " + xp + " XP";
        boolean isPartySource = source == XpSource.PARTY;

        spawnVerticalHologramBubble(player, Color.SUCCESS.asSpigotHex() + "+ " + xpStr + " " + (isPartySource ? "[Party]" : ""));
        player.asBukkitPlayer().sendMessage(type.getLabel() + " +" + xp + "xp! Type: " + source.name());
    }

    public static void showDamageBubble(CombatEntity entity, Hit hit, AttackType type, boolean isCriticalHit) {
        boolean isZeroHit = hit.damage() == 0;
        boolean isHitByPlayer = !(entity.getEntity() instanceof Player);

        String color = hit.damage() == 0 ? Color.ZERO_HIT.asSpigotHex() : isCriticalHit ? Color.CRITICAL_HIT.asSpigotHex() : Color.DAMAGE_HIT.asSpigotHex();
        String symbol = type == AttackType.MELEE ? Icon.SWORD.get() : type == AttackType.RANGED ? Icon.ARROW.get() : Icon.LEGENDARY.get(); // Todo: Replace magic icon!
        String msg = "§f" + (isHitByPlayer ? "§l" : "") + symbol + " " + color + (isHitByPlayer ? "§l" : "") + (isCriticalHit ? "CRIT! " : "") + (isZeroHit ? "" : "-") + hit.damage();

        spawnVerticalHologramBubble(entity.getEntity().getLocation(), msg);

    }

    public static void sendFailMessage(RunePlayer player, Component text) {
        player.asBukkitPlayer().sendMessage(text.color(Color.ERROR.asColor()));
        SoundType.FAIL_ACTION.play(player.asBukkitPlayer());
    }

    public static void showFailTitle(RunePlayer player, String text) {
        player.asBukkitPlayer().showTitle(Title.title(
                Component.text(""),
                Component.text(text).color(Color.ERROR.asColor()),
                TextUtils.getGenericDuration()
        ));
        SoundType.FAIL_ACTION.play(player.asBukkitPlayer());
    }

    public static void showPlayerLevelUpTitle(RunePlayer player, Skill skill) {
        boolean isMaxedSkill = XpUtils.isMaxLevel(skill.getLevel());
        int count = isMaxedSkill ? 50 : 5;
        int delayBetween = 5;
        SkillType type = skill.getType();

        SoundType.PLAYER_LEVEL_UP.play(player);


        if (!isMaxedSkill) {
            player.asBukkitPlayer().showTitle(Title.title(
                    Component.text("Congratulations!", Color.SERVER_BRAND.asColor()),
                    Component.text("Your ", Color.SUCCESS.asColor())
                            .append(Component.text(type.getSymbol(), NamedTextColor.WHITE))
                            .append(Component.text(" " + type.getLabel() + " level is now " + skill.getLevel() + "!", Color.SUCCESS.asColor())),
                    Title.Times.times(
                            Duration.ofSeconds(1),
                            Duration.ofSeconds(8, 5),
                            Duration.ofSeconds(3)
                    )
            ));
        }

        if (isMaxedSkill) {
            Bukkit.getScheduler().runTaskLater(RuneQuestPlugin.getInstance(), () -> {
                SoundType.PLAYER_MAX_SKILL.play(player);
            }, 40);

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

            player.asBukkitPlayer().showTitle(Title.title(
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

        for (int i = 0; i < count; i++) {
            int delay = i * delayBetween;
            Bukkit.getScheduler().runTaskLater(RuneQuestPlugin.getInstance(), () -> {
                Location loc = player.asBukkitPlayer().getLocation().add(0, 1, 0);
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
            }, delay);
        }


    }

    private static void updateVanillaHealth(RunePlayer player) {
        int maxHpLevel = player.getSkills().get(SkillType.HITPOINTS).getLevel();
        if (maxHpLevel <= 0) maxHpLevel = 1; // prevent division by zero

        // Clamp current health between 0 and max HP level
        double currentHp = Math.max(0, Math.min(player.getHealth(), maxHpLevel));

        // Scale current HP proportionally to Minecraft's 20 HP scale
        double scaledHealth = 20.0 * (currentHp / maxHpLevel);

        // Clamp scaledHealth between 0.5 and 20
        scaledHealth = Math.max(0.5, Math.min(20.0, scaledHealth));

        player.asBukkitPlayer().setHealth(scaledHealth);
    }

    private static void updateXpBarLevel(RunePlayer player) {
        player.asBukkitPlayer().setLevel(player.getCombatLvl());
    }

    public static void updateVanillaGUI(RunePlayer player) {
        updateXpBarLevel(player);
        updateVanillaHealth(player);
    }

    private static void spawnVerticalHologramBubble(Location location, String content) {
        spawnVerticalHologramBubble(location.clone().add(0, 1, 0), location.getDirection().clone().normalize(), content);
    }

    private static void spawnVerticalHologramBubble(RunePlayer player, String content) {
        spawnVerticalHologramBubble(player.asBukkitPlayer().getLocation().clone().add(0, 1, 0), player.asBukkitPlayer().getLocation().getDirection().clone().normalize(), content);
    }

    private static void spawnVerticalHologramBubble(Location start, Vector direction, String content) {
        UUID holoId = UUID.randomUUID();
        DHAPI.createHologram(holoId.toString(), start, List.of(content));

        new BukkitRunnable() {
            double t = 0;
            final double duration = 20;
            final double maxVerticalHeight = 0.3;
            final double baseHorizontalOffset = 0.2;
            final double horizontalRandomness = 0.1;

            @Override
            public void run() {
                if (t > duration) {
                    DHAPI.removeHologram(holoId.toString());
                    this.cancel();
                    return;
                }

                double progress = t / duration;

                // Vertical parabola
                double verticalOffset = (-4 * Math.pow(progress - 0.5, 2) + 1) * maxVerticalHeight;

                // Calculate forward position
                Location loc = start.clone().add(direction.clone().multiply(baseHorizontalOffset));

                // Add random horizontal side offset perpendicular to direction vector
                Vector perp = new Vector(-direction.getZ(), 0, direction.getX()).normalize(); // perpendicular vector on XZ plane

                // Random offset between -horizontalRandomness and +horizontalRandomness
                double randomOffset = (random.nextDouble() * 2 - 1) * horizontalRandomness;

                loc.add(perp.multiply(randomOffset));
                loc.add(0, verticalOffset, 0);

                DHAPI.moveHologram(holoId.toString(), loc);

                t++;
            }
        }.runTaskTimer(RuneQuestPlugin.getInstance(), 0L, 2L);
    }


}
