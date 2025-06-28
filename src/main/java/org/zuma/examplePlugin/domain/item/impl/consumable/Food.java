package org.zuma.examplePlugin.domain.item.impl.consumable;

import org.bukkit.Bukkit;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.zuma.examplePlugin.core.constants.Icon;
import org.zuma.examplePlugin.core.utils.PlayerUtils;
import org.zuma.examplePlugin.domain.item.base.AbstractItemType;
import org.zuma.examplePlugin.domain.item.meta.AbstractItemMeta;
import org.zuma.examplePlugin.domain.item.state.StateManager;
import org.zuma.examplePlugin.domain.player.RunePlayer;
import org.zuma.examplePlugin.events.item.PlayerConsumableConsumeEvent;

public class Food extends AbstractItemType {
    private int healAmount = 0;

    public Food(AbstractItemMeta meta) {
        super(meta);
        super.setTypeLore("Food", Icon.FRUIT.get());
    }

    public void setHealAmount(int amount) {
        this.healAmount = amount;
    }

    public int getHealAmount() {
        return healAmount;
    }

    public void onPlayerConsumeItem(PlayerItemConsumeEvent event, RunePlayer player, StateManager itemStateManager) {
        ItemStack itemStack = PlayerUtils.getItemStackInHand(player.asBukkitPlayer());

        PlayerConsumableConsumeEvent newEvent = new PlayerConsumableConsumeEvent(player, this, itemStack, itemStateManager);
        Bukkit.getServer().getPluginManager().callEvent(newEvent);

        if (newEvent.isCancelled()) {
            event.setCancelled(true);
            return;
        }


        player.asBukkitPlayer().sendMessage("You ate " + super.displayName + "! Health +" + healAmount);
        PlayerUtils.subtractOneFromMainHand(player);
    }


}
