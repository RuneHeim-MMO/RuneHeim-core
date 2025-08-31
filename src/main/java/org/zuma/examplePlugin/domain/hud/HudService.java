package org.zuma.examplePlugin.domain.hud;

import eu.decentsoftware.holograms.api.DHAPI;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.constants.Color;
import org.zuma.examplePlugin.core.constants.Icon;
import org.zuma.examplePlugin.core.constants.SoundType;
import org.zuma.examplePlugin.core.constants.XpSource;
import org.zuma.examplePlugin.core.interfaces.CombatEntity;
import org.zuma.examplePlugin.core.utils.*;
import org.zuma.examplePlugin.domain.combat.constants.AttackType;
import org.zuma.examplePlugin.domain.combat.Hit;
import org.zuma.examplePlugin.domain.combat.constants.HitType;
import org.zuma.examplePlugin.domain.mob.base.AbstractMob;
import org.zuma.examplePlugin.domain.mob.impl.FusionMaster;
import org.zuma.examplePlugin.domain.effect.EffectHandler;
import org.zuma.examplePlugin.domain.effect.effects.FusionThunderEffect;
import org.zuma.examplePlugin.domain.player.RunePlayer;
import org.zuma.examplePlugin.domain.skill.constants.SkillType;

import java.util.List;
import java.util.Random;
import java.util.UUID;

public class HudService {
    private static final Random random = new Random();

    public static void showFusionSuccessEffect(RunePlayer player, int gemsInserted) {
        double radius = 5.0;

        // Animate all inventory openers who open fusion master inventory.
        for (Entity entity : player.asBukkitPlayer().getWorld().getNearbyEntities(player.asBukkitPlayer().getLocation(), radius, radius, radius)) {
            if (!(entity instanceof LivingEntity)) continue;
            AbstractMob mob = RuneQuestPlugin.getMobRegistry().getMob(entity);

            if (!(mob instanceof FusionMaster)) {
                continue;
            }

            EffectHandler.playAt(entity.getLocation(), new FusionThunderEffect(entity, player.asBukkitPlayer(), gemsInserted));
        }


    }

    public static void showHealBubble(RunePlayer player, int healedHp) {
        if (healedHp == 0) return;

        String icon = "§f" + Icon.HEART_BIG.get() + Color.SUCCESS.asSpigotHex();
        spawnVerticalHologramBubble(player, Color.SUCCESS.asSpigotHex() + "+ " + healedHp + " " + icon + " HP");
    }

    public static void showCoinsChangeBubble(RunePlayer player, long change) {
        long absChange = Math.abs(change);
        String icon = "§f" + CurrencyUtils.getCurrencyIcon(absChange).get() + Color.SUCCESS.asSpigotHex();
        String prefix = change > 0 ? Color.SUCCESS.asSpigotHex() + "+" : Color.ERROR.asSpigotHex() + "-";

        spawnVerticalHologramBubble(player, prefix + CurrencyUtils.formatCurrencyString(absChange) + " " + icon);
    }

    public static void showXpGainMessage(RunePlayer player, SkillType type, double xp, XpSource source) {
        String xpStr = "§f" + type.getSymbol() + Color.SUCCESS.asSpigotHex() + " " + xp + " XP";
        boolean isPartySource = source == org.zuma.examplePlugin.core.constants.XpSource.PARTY;

        spawnVerticalHologramBubble(player, Color.SUCCESS.asSpigotHex() + "+ " + xpStr + " " + (isPartySource ? "[Party]" : ""));
    }

    public static void showDamageBubble(CombatEntity entity, Hit hit, AttackType type) {
        boolean isZeroHit = hit.hitType() == HitType.MISS;
        boolean isHitByPlayer = !(entity.getEntity() instanceof Player);
        boolean isCriticalHit = hit.hitType() == HitType.CRIT;

        String color = hit.damage() == 0 ? Color.ZERO_HIT.asSpigotHex() : isCriticalHit ? Color.CRITICAL_HIT.asSpigotHex() : Color.DAMAGE_HIT.asSpigotHex();
        String symbol = type == AttackType.MELEE ? Icon.SWORD.get() : type == AttackType.RANGED ? Icon.ARROW.get() : Icon.LEGENDARY.get(); // Todo: Replace magic icon!
        String msg = "§f" + symbol + (isHitByPlayer ? "§l" : "") + " " + color + (isHitByPlayer ? "§l" : "") + (isCriticalHit ? "CRIT! " : "") + (isZeroHit ? "" : "-") + hit.damage() + (hit.isSpecialAttack() ? " §f" + Icon.CLOVER.get() : "");

        spawnVerticalHologramBubble(entity.getEntity().getLocation(), msg);

    }

    public static void sendFailMessage(RunePlayer player, Component text) {
        player.asBukkitPlayer().sendMessage(Icon.ERROR.asComponent().append(text.color(Color.ERROR.asColor())));
        SoundType.FAIL_ACTION.play(player.asBukkitPlayer(), false);
    }

    public static void sendInfoMessage(RunePlayer player, Component text) {
        player.asBukkitPlayer().sendMessage(Icon.INFO.asComponent().append(text.color(Color.INFO.asColor())));
    }

    public static void showFailTitle(RunePlayer player, String text) {
        player.asBukkitPlayer().showTitle(Title.title(
                Component.empty(),
                Component.text(text).color(Color.ERROR.asColor()),
                TextUtils.getGenericDuration()
        ));
        SoundType.FAIL_ACTION.play(player.asBukkitPlayer(), false);
    }

    private static void updateVanillaHealth(RunePlayer player) {
        int maxHpLevel = player.getSkills().get(SkillType.HITPOINTS).getLevelWithBonuses();
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
        player.asBukkitPlayer().setExp((float) PlayerUtils.getLastSkillPercent(player) / 100);
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
