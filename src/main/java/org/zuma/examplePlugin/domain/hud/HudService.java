package org.zuma.examplePlugin.domain.hud;

import eu.decentsoftware.holograms.api.DHAPI;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
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
import org.zuma.examplePlugin.domain.item.ItemRecord;
import org.zuma.examplePlugin.domain.item.base.AbstractItemType;
import org.zuma.examplePlugin.domain.mob.base.AbstractMob;
import org.zuma.examplePlugin.domain.mob.impl.FusionMaster;
import org.zuma.examplePlugin.domain.effect.EffectHandler;
import org.zuma.examplePlugin.domain.effect.effects.FusionThunderEffect;
import org.zuma.examplePlugin.domain.modifier.scopes.global.GlobalModifier;
import org.zuma.examplePlugin.domain.player.RunePlayer;
import org.zuma.examplePlugin.domain.quest.Quest;
import org.zuma.examplePlugin.domain.quest.base.AbstractQuestStep;
import org.zuma.examplePlugin.domain.skill.constants.SkillType;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

public class HudService {
    private static final Random random = new Random();


    public static void globalModifiersActivated(RunePlayer player, List<GlobalModifier> activeModifiers) {
        if (activeModifiers.isEmpty()) return;
        Component body = Component.text(String.join("\n", activeModifiers.stream().map(m -> "§f" + Icon.UPGRADE.get() + " §e" + m.label() + " is active for " + DateUtils.getTimeDifference(m.activeUntilMillis())).toList()) + "!");
        player.asBukkitPlayer().sendMessage(body);
    }

    public static void globalModifiersExpired(RunePlayer player, List<GlobalModifier> activeModifiers) {
        if (activeModifiers.isEmpty()) return;
        Component body = Component.text(String.join("\n", activeModifiers.stream().map(m -> "§f" + Icon.DOWNGRADE.get() + " §7" + m.label() + " has expired!").toList()));
        player.asBukkitPlayer().sendMessage(body);
    }

    public static void sendQuestRewards(RunePlayer player, Quest quest) {
        Map<SkillType, Double> xpRewards = quest.getReward().getXpRewards();
        List<ItemRecord> itemRewards = quest.getReward().getItemRewards();
        Integer coinsReward = quest.getReward().getCoins();

        if (xpRewards != null) {
            xpRewards.forEach((type, xp) -> {
                sendInfoMessage(player, Component.text("+ " + xp + " " + type.getLabel() + " experience."));
            });
        }

        if (itemRewards != null) {
            itemRewards.forEach((itemRecord) -> {
                AbstractItemType itemType = RuneQuestPlugin.getItemRegistry().getType(itemRecord.getItemId());

                if (itemType == null) return;

                sendInfoMessage(player, Component.text("+ " + itemRecord.getAmount() + "x " + itemType.displayName + "."));
            });
        }

        if (coinsReward != null) {
            sendInfoMessage(player, Component.text("+ " + coinsReward + " coins."));
        }
    }

    public static void sendQuestStartMessage(RunePlayer player, Quest quest) {
        HudService.sendInfoMessage(player, Component.text(quest.getName() + " has been started!"));
    }

    public static void sendQuestStepTaskMessage(RunePlayer player, AbstractQuestStep step) {
        if (step.getDescription() == null) return; // Silent checkpoint
        HudService.sendInfoMessage(player, Component.text(step.getParentQuest().getName() + " >> ").append(Component.text(step.getDescription(), NamedTextColor.WHITE)));
        SoundType.PLAYER_QUEST_PROGRESS.play(player, false);
    }

    public static void sendQuestCompletedMessage(RunePlayer player, Quest quest) {
        HudService.sendInfoMessage(player, Component.text(quest.getName() + " has been completed!"));
    }

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

    public static void showCombinedCombatXPGainMessage(RunePlayer player, double xp, XpSource source, double multiplier) {
        String formattedXP = String.format("%.1f", xp);
        String xpStr = "§f" + Icon.CROSSED_SWORDS.get() + Color.SUCCESS.asSpigotHex() + " " + formattedXP + " XP";
        String multiplierStr = multiplier > 1.0 ? " §d(x" + String.format("%.2f", multiplier) + ")" : "";
        boolean isPartySource = source == org.zuma.examplePlugin.core.constants.XpSource.PARTY;

        spawnVerticalHologramBubble(player, (isPartySource ? "§f" + Icon.PARTY.get() + " " : "") + Color.SUCCESS.asSpigotHex() + "+ " + xpStr + multiplierStr);
    }

    public static void showXpGainMessage(RunePlayer player, SkillType type, double xp, XpSource source, double multiplier) {
        String formattedXP = String.format("%.1f", xp);
        String xpStr = "§f" + type.getSymbol() + Color.SUCCESS.asSpigotHex() + " " + formattedXP + " XP";
        String multiplierStr = multiplier > 1.0 ? " §d(x" + String.format("%.2f", multiplier) + ")" : "";
        boolean isPartySource = source == org.zuma.examplePlugin.core.constants.XpSource.PARTY;

        spawnVerticalHologramBubble(player, (isPartySource ? "§f" + Icon.PARTY.get() + " " : "") + Color.SUCCESS.asSpigotHex() + "+ " + xpStr + multiplierStr);
    }

    public static void showDamageBubble(CombatEntity entity, Hit hit) {
        boolean isZeroHit = hit.getHitType() == HitType.MISS;
        boolean isHitByPlayer = !(entity.getEntity() instanceof Player);
        boolean isCriticalHit = hit.getHitType() == HitType.CRIT;

        String color = hit.getDamage() == 0 ? Color.ZERO_HIT.asSpigotHex() : isCriticalHit ? Color.CRITICAL_HIT.asSpigotHex() : Color.DAMAGE_HIT.asSpigotHex();
        String symbol = hit.getAttackType() == AttackType.MELEE ? Icon.SWORD.get() : hit.getAttackType() == AttackType.RANGED ? Icon.ARROW.get() : Icon.LEGENDARY.get(); // Todo: Replace magic icon!
        String msg = "§f" + symbol + (isHitByPlayer ? "§l" : "") + " " + color + (isHitByPlayer ? "§l" : "") + (isCriticalHit ? "CRIT! " : "") + (isZeroHit ? "" : "-") + hit.getDamage() + (hit.isSpecialAttack() ? " §f" + Icon.SPECIAL_ATTACK.get() : "");

        spawnVerticalHologramBubble(entity.getEntity().getLocation(), msg);

    }

    public static void sendFailMessage(RunePlayer player, Component text) {
        player.asBukkitPlayer().sendMessage(Icon.ERROR.asComponent().append(text.color(Color.ERROR.asColor())));
        SoundType.FAIL_ACTION.play(player.asBukkitPlayer(), false);
    }

    public static void sendSuccessMessage(RunePlayer player, Component text) {
        player.asBukkitPlayer().sendMessage(Icon.SUCCESS.asComponent().color(Color.SUCCESS.asColor()).append(text.color(Color.SUCCESS.asColor())));
    }

    public static void sendInfoMessage(RunePlayer player, Component text) {
        player.asBukkitPlayer().sendMessage(Icon.INFO.asComponent().append(text.color(Color.INFO.asColor())));
    }

    public static void sendAnnouncementMessage(RunePlayer player, Component text) {
        player.asBukkitPlayer().sendMessage(Icon.ANNOUNCEMENT.asComponent().color(Color.SERVER_BRAND.asColor()).append(text.color(Color.SERVER_BRAND.asColor())));
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
