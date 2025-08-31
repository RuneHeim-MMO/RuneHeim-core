package org.zuma.examplePlugin.domain.player.handlers;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.constants.Color;
import org.zuma.examplePlugin.core.constants.Icon;
import org.zuma.examplePlugin.core.constants.SoundType;
import org.zuma.examplePlugin.core.utils.CurrencyUtils;
import org.zuma.examplePlugin.core.utils.PlayerUtils;
import org.zuma.examplePlugin.domain.combat.Hit;
import org.zuma.examplePlugin.domain.hud.HudService;
import org.zuma.examplePlugin.domain.item.base.AbstractItemType;
import org.zuma.examplePlugin.domain.item.constants.ItemId;
import org.zuma.examplePlugin.domain.player.RunePlayer;
import org.zuma.examplePlugin.domain.skill.constants.SkillType;
import org.zuma.examplePlugin.domain.territory.Territory;
import org.zuma.examplePlugin.events.combat.RuneEntityDeathEvent;
import org.zuma.examplePlugin.events.player.RunePlayerHealthChangedEvent;

import java.time.Duration;
import java.util.Random;

public class DeathHandler {
    private final static Random rand = new Random();
    private final static int cooldownDuration = 10;

    public static void handlePlayerDeath(RunePlayer runePlayer, Hit lastHit) {

        RuneEntityDeathEvent event = new RuneEntityDeathEvent(lastHit.getAttacker(), lastHit.getDefender(), lastHit, lastHit.getAttackType());
        Bukkit.getServer().getPluginManager().callEvent(event);

        Player player = runePlayer.asBukkitPlayer();
        Territory closestTerritory = PlayerUtils.getClosestDiscoveredTerritory(runePlayer);
        Location playerLocation = player.getLocation();
        long pouchCoins = runePlayer.getPouchCoins();


        for (Entity entity : player.getNearbyEntities(50, 50, 50)) {
            if (entity instanceof Mob mob) {
                if (mob.getTarget() != null && mob.getTarget().getUniqueId().equals(player.getUniqueId())) {
                    mob.setTarget(null);
                }
            }
        }

        int droppedCoinsAmount = pouchCoins == 0 ? 0 : Math.toIntExact(pouchCoins * 10 / 100); // 10 %
        droppedCoinsAmount = rand.nextInt(droppedCoinsAmount) + 1;

        if (pouchCoins >= droppedCoinsAmount) {
            AbstractItemType coinType = RuneQuestPlugin.getItemRegistry().getType(ItemId.COINS.asInt());

            if (coinType != null) {
                ItemStack newCoinStack = coinType.createItemStackOfType(droppedCoinsAmount);
                runePlayer.setPouchCoins(pouchCoins - droppedCoinsAmount, false);
                playerLocation.getWorld().dropItemNaturally(playerLocation, newCoinStack);
                HudService.sendInfoMessage(runePlayer, Component.text("You have dropped ").append(CurrencyUtils.getCoinsCurrencyComponent(droppedCoinsAmount)).append(Component.text(" coins.")));
            }

        }

        Location spawnPoint = playerLocation.getWorld().getSpawnLocation();

        if (closestTerritory != null && closestTerritory.getSpawnPoint() != null) {
            spawnPoint = closestTerritory.getSpawnPoint();
        }

        runePlayer.setSpectatorMode(spawnPoint);

        SoundType.DEATH_SCREEN.play(player, false);
        SoundType.DEATH_SCREEN_1.play(player, false);
        player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, (cooldownDuration + 1) * 20, 0, false, false, false));
        player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, (cooldownDuration + 1) * 20, 1, false, false, false));

        for (int i = 0; i < cooldownDuration; i++) {
            int finalI = i;

            Bukkit.getScheduler().runTaskLater(RuneQuestPlugin.getInstance(), () -> {
                if (!player.isOnline()) {
                    return;

                }
                player.showTitle(Title.title(
                        Icon.RANGED_STR.asComponent().append(Component.text("YOU HAVE DIED ").color(Color.ERROR.asColor()).append(Icon.RANGED_STR.asComponent()))
                                .decorate(TextDecoration.BOLD),
                        Component.text("Respawning in " + (cooldownDuration - finalI) + " seconds.").color(Color.ERROR_LIGHT.asColor()),
                        Title.Times.times(Duration.ZERO, Duration.ofSeconds(cooldownDuration), Duration.ofMillis(0))
                ));
            }, i == 0 ? 0L : 20L * i);
        }


        Location finalSpawnPoint = spawnPoint;
        Bukkit.getScheduler().runTaskLater(RuneQuestPlugin.getInstance(), () -> {
            if (!player.isOnline()) {
                return;
            }
            player.showTitle(Title.title(
                    Component.empty(), Component.empty(),
                    Title.Times.times(Duration.ZERO, Duration.ofMillis(100), Duration.ofMillis(0))
            ));

            runePlayer.setSurvivalMode();
            player.teleport(finalSpawnPoint);
            runePlayer.setHealth(runePlayer.getSkill(SkillType.HITPOINTS).getLevelWithBonuses(), RunePlayerHealthChangedEvent.Cause.RESPAWN);

        }, cooldownDuration * 20L);

    }
}
