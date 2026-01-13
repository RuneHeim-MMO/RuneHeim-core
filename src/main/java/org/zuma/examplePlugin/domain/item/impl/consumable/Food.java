package org.zuma.examplePlugin.domain.item.impl.consumable;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.zuma.examplePlugin.core.analytics.AnalyticsKey;
import org.zuma.examplePlugin.core.constants.Color;
import org.zuma.examplePlugin.core.constants.Icon;
import org.zuma.examplePlugin.core.utils.AnalyticsUtils;
import org.zuma.examplePlugin.core.utils.PlayerUtils;
import org.zuma.examplePlugin.domain.hud.HudService;
import org.zuma.examplePlugin.domain.item.base.AbstractItemType;
import org.zuma.examplePlugin.domain.item.meta.AbstractItemMeta;
import org.zuma.examplePlugin.domain.item.state.StateManager;
import org.zuma.examplePlugin.domain.player.RunePlayer;
import org.zuma.examplePlugin.events.item.PlayerConsumableConsumeEvent;
import org.zuma.examplePlugin.events.player.RunePlayerHealthChangedEvent;

public class Food extends AbstractItemType {
    private int healAmount = 0;

    public Food(AbstractItemMeta meta) {
        super(meta);
        super.setTypeLore("Food", Icon.FRUIT.get());
    }

    public int getHealAmount() {
        return healAmount;
    }

    public void setHealAmount(int amount) {
        this.healAmount = amount;
    }

    public void onPlayerConsumeItem(PlayerItemConsumeEvent event, RunePlayer player, StateManager itemStateManager) {
        ItemStack itemStack = PlayerUtils.getItemStackInHand(player.asBukkitPlayer());

        PlayerConsumableConsumeEvent newEvent = new PlayerConsumableConsumeEvent(player, this, itemStack, itemStateManager);
        Bukkit.getServer().getPluginManager().callEvent(newEvent);

        if (newEvent.isCancelled()) {
            event.setCancelled(true);
            return;
        }


        int healthAfterEating = Math.min(player.getMaxHealth(), player.getHealth() + healAmount);
        int healedAmount = healthAfterEating - player.getHealth();

        if (healthAfterEating <= 0) {
            player.killPlayer(null);
            return;
        }

        boolean success = PlayerUtils.subtractOneFromMainHand(player);

        if (!success) {
            HudService.sendFailMessage(player, Component.text("Failed to consume item from your hand!", Color.ERROR.asColor()));
            return;
        }

        player.setHealth(healthAfterEating, RunePlayerHealthChangedEvent.Cause.HEALING);
        HudService.showHealBubble(player, healedAmount);
        AnalyticsUtils.incrementServerAndPlayer(player, AnalyticsKey.FOOD_CONSUMED, 1);
    }


}
