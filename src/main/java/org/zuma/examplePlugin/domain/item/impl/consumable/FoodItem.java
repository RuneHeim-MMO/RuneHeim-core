package org.zuma.examplePlugin.domain.item.impl.consumable;

import com.google.gson.Gson;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.zuma.examplePlugin.core.constants.Colors;
import org.zuma.examplePlugin.core.interfaces.Serializable;
import org.zuma.examplePlugin.core.utils.PlayerUtils;
import org.zuma.examplePlugin.domain.item.base.AbstractItem;
import org.zuma.examplePlugin.domain.item.meta.AbstractItemMeta;
import org.zuma.examplePlugin.domain.item.meta.FoodMeta;
import org.zuma.examplePlugin.domain.item.state.StateManager;
import org.zuma.examplePlugin.domain.player.RunePlayer;
import org.zuma.examplePlugin.domain.skill.constants.SkillType;
import org.zuma.examplePlugin.events.item.PlayerConsumableConsumeEvent;

public class FoodItem extends AbstractItem implements Serializable {
    private int healAmount = 0;

    public FoodItem(AbstractItemMeta meta) {
        super(meta);
    }

    @Override
    public void deserializeAndApplyMeta() {
        if (super.getJsonMetaVersion() == 1) {
            FoodMeta deserialized = new Gson().fromJson(this.jsonMeta, FoodMeta.class);
            this.healAmount = deserialized.healAmount();
            super.appendToLore(Component.text(""));
            super.appendToLore(Component.text("Food", Colors.ITEM_TYPE.asColor()));
            super.appendToLore(Component.text("Heals " + SkillType.HITPOINTS.getSymbol() + " " + healAmount + " " + SkillType.HITPOINTS.getLabel(), Colors.SUCCESS.asColor()));
            return;
        }

        throw new Error("[" + this.displayName + "] Unsupported JSON meta version! " + super.getJsonMetaVersion());
    }

    @Override
    public void onPlayerInteract(PlayerInteractEvent event, RunePlayer player, StateManager itemStateManager) {
        ItemStack itemStack = PlayerUtils.getItemStackInHand(player.getPlayer());

        PlayerConsumableConsumeEvent newEvent = new PlayerConsumableConsumeEvent(player, this, itemStack, itemStateManager);
        Bukkit.getServer().getPluginManager().callEvent(newEvent);

        if (newEvent.isCancelled()) {
            event.setCancelled(true);
            return;
        }


        player.getPlayer().sendMessage("You ate " + super.displayName + "! Health +" + healAmount);

    }
}
