package org.zuma.examplePlugin.domain.item.impl.consumable;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.zuma.examplePlugin.core.constants.Color;
import org.zuma.examplePlugin.core.constants.Icon;
import org.zuma.examplePlugin.core.utils.PlayerUtils;
import org.zuma.examplePlugin.domain.item.base.AbstractItem;
import org.zuma.examplePlugin.domain.item.meta.AbstractItemMeta;
import org.zuma.examplePlugin.domain.item.state.StateManager;
import org.zuma.examplePlugin.domain.player.RunePlayer;
import org.zuma.examplePlugin.domain.skill.constants.SkillType;
import org.zuma.examplePlugin.events.item.PlayerConsumableConsumeEvent;

public class FoodItem extends AbstractItem {
    private int healAmount = 0;

    public FoodItem(AbstractItemMeta meta) {
        super(meta);
        super.appendToItemTypeLore("Food", Icon.FRUIT.get());
    }

    public void setHealAmount(int amount) {
        this.healAmount = amount;
        super.appendToLore(Component.text("Heals ", Color.SUCCESS.asColor()).append(Component.text(SkillType.HITPOINTS.getSymbol(), NamedTextColor.WHITE))
                .append(Component.text(" " + healAmount + " " + SkillType.HITPOINTS.getLabel(), Color.SUCCESS.asColor())));

    }

    public void onPlayerConsumeItem(PlayerItemConsumeEvent event, RunePlayer player, StateManager itemStateManager) {
        ItemStack itemStack = PlayerUtils.getItemStackInHand(player.getPlayer());

        PlayerConsumableConsumeEvent newEvent = new PlayerConsumableConsumeEvent(player, this, itemStack, itemStateManager);
        Bukkit.getServer().getPluginManager().callEvent(newEvent);

        if (newEvent.isCancelled()) {
            event.setCancelled(true);
            return;
        }


        player.getPlayer().sendMessage("You ate " + super.displayName + "! Health +" + healAmount);
        PlayerUtils.subtractOneFromMainHand(player);
    }


}
