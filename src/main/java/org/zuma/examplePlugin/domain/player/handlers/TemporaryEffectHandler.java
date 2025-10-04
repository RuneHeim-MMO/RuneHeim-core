package org.zuma.examplePlugin.domain.player.handlers;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.constants.SoundType;
import org.zuma.examplePlugin.domain.hud.HudService;
import org.zuma.examplePlugin.domain.player.RunePlayerManager;

public class TemporaryEffectHandler {
    private final RunePlayerManager manager;

    public TemporaryEffectHandler(RunePlayerManager manager) {
        this.manager = manager;
    }

    public void startLoop() {
        Bukkit.getScheduler().runTaskTimer(RuneQuestPlugin.getInstance(), () -> {
            manager.getOnlinePlayers().forEach(runePlayer -> {
                runePlayer.getActiveEffects().removeIf(effect -> {
                    effect.setExpiresInSeconds(effect.getExpiresInSeconds() - 2);

                    if (!effect.isExpired()) return false;

                    if (effect.getExpirationMessage() != null) {
                        HudService.sendInfoMessage(runePlayer, Component.text(effect.getExpirationMessage()));
                        SoundType.POTION_EFFECT_EXPIRY.play(runePlayer, false);
                    }

                    RuneQuestPlugin.getGearModifierHandler().recalculatePlayerModifiers(runePlayer, runePlayer.asBukkitPlayer().getInventory().getItemInMainHand());
                    return true;

                });
            });
        }, 20L, 20L * 2); // Check every 2 seconds

    }

}
