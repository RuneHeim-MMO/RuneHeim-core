package org.zuma.examplePlugin.domain.payments.handlers;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.Nullable;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.constants.Color;
import org.zuma.examplePlugin.core.logger.constants.LogLevel;
import org.zuma.examplePlugin.core.permissions.PlayerRole;
import org.zuma.examplePlugin.core.permissions.roles.UpgradedRole;
import org.zuma.examplePlugin.domain.effect.effects.RankUpgradeEffect;
import org.zuma.examplePlugin.domain.effect.effects.SupporterLevelUpEffect;
import org.zuma.examplePlugin.domain.payments.SupporterLevel;
import org.zuma.examplePlugin.domain.player.RunePlayer;
import org.zuma.examplePlugin.events.payment.RankReloadEvent;
import org.zuma.examplePlugin.events.payment.SupportLevelUpgradeEvent;

import java.math.BigDecimal;
import java.util.UUID;

public class DonationHandler {
    public static void updatePlayerRole(UUID uuid, UpgradedRole role) {
        @Nullable UpgradedRole currentRole = RuneQuestPlugin.getPlayerManager().getPlayerRolesFromDatabaseSync(uuid).getValue();

        if (currentRole != null && role.getWeight() > currentRole.getWeight()) {
            RuneQuestPlugin.getRuneLogger().log(LogLevel.WARN, "[DONATE] New rank " + role + " has lower weight than current rank " + currentRole + " for player " + uuid);
            RuneQuestPlugin.getRuneLogger().log(LogLevel.WARN, "[DONATE] Aborting rank update to prevent downgrade.");
            return;
        }

        RuneQuestPlugin.getPlayerManager().writeNewUpgradedRoleToDatabaseSync(uuid, role);
        RankReloadEvent rankReloadEvent = new RankReloadEvent(new RankReloadEvent.Payload(uuid, role));
        Bukkit.getServer().getPluginManager().callEvent(rankReloadEvent);
        RuneQuestPlugin.getRuneLogger().log(LogLevel.INFO, "[DONATE] Updated player " + uuid + " to new role " + role);
    }

    public static void handleRankReloadEvent(UUID uuid, UpgradedRole newRole) {
        RunePlayer matchingPlayer = RuneQuestPlugin.getPlayerManager().getRunePlayer(uuid);
        if (matchingPlayer == null) return; // Not on server

        matchingPlayer.setRole(new PlayerRole(matchingPlayer.getRole().getBaseRole(), newRole, matchingPlayer.getRole().getSupporterLevel()), true);
        matchingPlayer.getScoreboardManager().reloadNametags();
        matchingPlayer.getScoreboardUsernameRenderer().updateTabUsername();
        EffectQueueManager.startEffect(matchingPlayer, new RankUpgradeEffect(matchingPlayer));
    }

    public static void handleSupporterLevelReloadEvent(UUID uuid) {
        RunePlayer matchingPlayer = RuneQuestPlugin.getPlayerManager().getRunePlayer(uuid);
        if (matchingPlayer == null) return; // Not on server

        Bukkit.getScheduler().runTaskAsynchronously(RuneQuestPlugin.getInstance(), () -> {
            BigDecimal totalPayments = RuneQuestPlugin.getPaymentHandler().getTotalPayments(uuid);
            SupporterLevel newLevel = SupporterLevel.getLevelByAmount(totalPayments.doubleValue());
            if (newLevel.equals(matchingPlayer.getRole().getSupporterLevel())) {
                return;
            }

            Bukkit.getScheduler().runTask(RuneQuestPlugin.getInstance(), () -> {
                matchingPlayer.setSupporterLevel(newLevel);
                matchingPlayer.getScoreboardManager().reloadNametags();
                matchingPlayer.getScoreboardUsernameRenderer().updateTabUsername();
                Bukkit.getPluginManager().callEvent(new SupportLevelUpgradeEvent(new SupportLevelUpgradeEvent.Payload(matchingPlayer.asBukkitPlayer().getName(), newLevel)));
                EffectQueueManager.startEffect(matchingPlayer, new SupporterLevelUpEffect(matchingPlayer));
            });
        });
    }

    public static void handleSupporterUpgradeAnnounce(String username, SupporterLevel newLevel) {
        Component message = Component.text(username, Color.ACCENT.asColor()).append(Component.text(" has just achieved ", Color.SERVER_BRAND.asColor())
                .append(Component.text("§f" + newLevel.levelIcon.get() + " " + newLevel.colorCode + "Supporter Level " + newLevel.getRomanNumeral()))
                .append(Component.text("! ", Color.SERVER_BRAND.asColor()))
                .append(Component.text("Thank you for your support!", Color.SERVER_BRAND.asColor())));

        Bukkit.getOnlinePlayers().forEach(p -> p.sendMessage(message));
    }

}
