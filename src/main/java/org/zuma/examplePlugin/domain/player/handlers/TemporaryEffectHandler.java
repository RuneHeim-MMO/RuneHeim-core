package org.zuma.examplePlugin.domain.player.handlers;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.constants.SoundType;
import org.zuma.examplePlugin.domain.hud.HudService;
import org.zuma.examplePlugin.domain.item.base.AbstractItemType;
import org.zuma.examplePlugin.domain.item.base.EquipableItemType;
import org.zuma.examplePlugin.domain.player.RunePlayer;
import org.zuma.examplePlugin.domain.player.RunePlayerManager;
import org.zuma.examplePlugin.domain.skill.constants.SkillType;

import java.util.Arrays;

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

                    RuneQuestPlugin.getGearModifierHandler().recalculatePlayerModifiers(runePlayer, runePlayer.asBukkitPlayer().getInventory().getItemInMainHand());
                    checkInvalidArmor(runePlayer);

                    if (effect.getExpirationMessage() != null) {
                        HudService.sendInfoMessage(runePlayer, Component.text(effect.getExpirationMessage()));
                        SoundType.POTION_EFFECT_EXPIRY.play(runePlayer, false);
                    }
                    return true;

                });
            });
        }, 20L, 20L * 2); // Check every 2 seconds

    }

    // Check if the player still meets the requirements for their equipped armor
    private void checkInvalidArmor(RunePlayer runePlayer) {
        Player player = runePlayer.asBukkitPlayer();
        ItemStack[] armor = player.getInventory().getArmorContents();

        for (int i = 0; i < armor.length; i++) {
            ItemStack stack = armor[i];
            if (stack == null || stack.getType().isAir()) continue;

            AbstractItemType type = RuneQuestPlugin.getItemRegistry().getType(stack);
            if (!(type instanceof EquipableItemType equipable)) continue;

            if (!runePlayer.meetsRequirements(equipable)) {
                ItemStack clone = stack.clone();
                armor[i] = null;

                runePlayer.giveItemOrDrop(clone);
                HudService.sendInfoMessage(
                        runePlayer,
                        Component.text("You no longer meet the requirements to wear your " + equipable.displayName + ".")
                );
            }
        }

        player.getInventory().setArmorContents(armor);
    }


}
