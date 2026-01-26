package org.zuma.examplePlugin.core.handlers.game;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.constants.SoundType;
import org.zuma.examplePlugin.core.utils.PlayerUtils;
import org.zuma.examplePlugin.domain.cooldown.CooldownHandler;
import org.zuma.examplePlugin.domain.gui.constants.InventoryId;
import org.zuma.examplePlugin.domain.hud.HudService;
import org.zuma.examplePlugin.domain.player.RunePlayer;
import org.zuma.examplePlugin.events.player.RunePlayerLoadedEvent;

import java.util.HashMap;
import java.util.UUID;

public class BotVerificationHandler implements Listener {
    private static final HashMap<UUID, Integer> actionsSinceLastVerification = new HashMap<>();
    private static final HashMap<UUID, Integer> verificationAmounts = new HashMap<>();
    private static final CooldownHandler alertCooldown = new CooldownHandler();
    private static final int ACTIONS_BOT_CHECK_THRESHOLD = 64;

    public static void logSuccessfulAction(RunePlayer player) {
        if (player.isBotVerificationPending()) {
            RuneQuestPlugin.getGUIHandler().openInventory(InventoryId.BOT_VERIFICATION_MENU.asInt(), player, null, null, null, true, null);
            return;
        }

        int thresholdMultiplier = Math.min(3, verificationAmounts.getOrDefault(player.getUUID(), 1)); // Increase threshold with each successful verification, up to 3x
        incrementActionCount(player);

        if (actionsSinceLastVerification.get(player.getUUID()) >= ACTIONS_BOT_CHECK_THRESHOLD * thresholdMultiplier) {
            player.setBotVerificationPending(true);
            SoundType.BOT_VERIFICATION_ALERT.play(player, false);
            RuneQuestPlugin.getGUIHandler().openInventory(InventoryId.BOT_VERIFICATION_MENU.asInt(), player, null, null, null, true, null);
            actionsSinceLastVerification.put(player.getUUID(), 0);
        }


    }

    public static boolean canStartAction(RunePlayer player) {
        if (!player.isBotVerificationPending()) {
            return true;
        }

        HudService.sendFailMessage(player, Component.text("You must complete the bot verification to continue this action."));
        RuneQuestPlugin.getGUIHandler().openInventory(InventoryId.BOT_VERIFICATION_MENU.asInt(), player, null,null);

        if (!alertCooldown.isCooldownInProgress(player)) {
            alertCooldown.startCooldown(player, 10000L); // 10 seconds cooldown
            PlayerUtils.alertStaff("[Bot Verification] " + player.asBukkitPlayer().getName() + " attempted to perform an action while bot verification is pending.");
        }

        return false;
    }

    private static void incrementActionCount(RunePlayer player) {
        UUID uuid = player.getUUID();
        actionsSinceLastVerification.putIfAbsent(uuid, 0);
        actionsSinceLastVerification.put(uuid, actionsSinceLastVerification.get(uuid) + 1);
    }

    public static void incrementVerificationAmount(RunePlayer player) {
        UUID uuid = player.getUUID();
        verificationAmounts.putIfAbsent(uuid, 0);
        verificationAmounts.put(uuid, verificationAmounts.get(uuid) + 1);
        actionsSinceLastVerification.put(uuid, 0);
    }

    @EventHandler
    public void onPlayerJoin(RunePlayerLoadedEvent event) {
        if (!event.getPlayer().isBotVerificationPending()) {
            return;
        }

        Bukkit.getScheduler().runTaskLater(RuneQuestPlugin.getInstance(), () -> {
            if (event.getPlayer() == null || !event.getPlayer().isOnline()) {
                return;
            }
            RuneQuestPlugin.getGUIHandler().openInventory(InventoryId.BOT_VERIFICATION_MENU.asInt(), event.getPlayer(), null, null, null, true, null);
        }, 10L);

    }

    @EventHandler
    public void onPlayerDisconnect(PlayerQuitEvent event) {
        UUID uuid = event.getPlayer().getUniqueId();
        actionsSinceLastVerification.remove(uuid);
        verificationAmounts.remove(uuid);
    }


}
