package org.zuma.examplePlugin.domain.item.base;

import com.google.gson.Gson;
import net.kyori.adventure.text.Component;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.zuma.examplePlugin.constants.Colors;
import org.zuma.examplePlugin.core.utils.PlayerUtils;
import org.zuma.examplePlugin.domain.item.interfaces.Stateful;
import org.zuma.examplePlugin.domain.item.interfaces.WithMeta;
import org.zuma.examplePlugin.domain.item.meta.AbstractItemMeta;
import org.zuma.examplePlugin.domain.item.meta.ConsumableItemMeta;
import org.zuma.examplePlugin.domain.item.state.StateManager;
import org.zuma.examplePlugin.domain.item.state.StateType;
import org.zuma.examplePlugin.domain.item.state.base.AbstractState;
import org.zuma.examplePlugin.domain.item.state.states.ConsumableState;
import org.zuma.examplePlugin.domain.player.RunePlayer;

public class ConsumableItem extends AbstractItem implements Stateful, WithMeta {
    private int usages;
    private int maxUsages;

    public ConsumableItem(AbstractItemMeta meta) {
        super(meta);
        this.setRarityPrefixSlot("CONSUMABLE");
        this.setStateType(StateType.CONSUMABLE);
    }


    @Override
    public AbstractState getInitialState() {
        return new ConsumableState(maxUsages, usages);
    }

    @Override
    public void deserializeAndApplyMeta() {
        ConsumableItemMeta meta = new Gson().fromJson(super.jsonMeta, ConsumableItemMeta.class);
        usages = meta.usages();
        maxUsages = meta.maxUsages();
    }


    @Override
    public void onPlayerInteract(PlayerInteractEvent event, RunePlayer player, StateManager itemStateManager) {
        ItemStack itemStack = PlayerUtils.getItemStackInHand(player.getPlayer());
        ConsumableState state = (ConsumableState) itemStateManager.getItemState(itemStack);

        if (state == null) {
            return;
        }

        if (state.getUsageLeft() == 0) {
            player.getPlayer().sendMessage("Oops! It's consumed already");
            return;
        }

        state.setUsageLeft(state.getUsageLeft() - 1);
        player.getPlayer().sendMessage("Usages left: " + state.getUsageLeft());
        this.setDisplayName(itemStack, null, Component.text(" ✦ (" + state.getMaxUsages() + "/" + state.getUsageLeft() + ")", Colors.ITEM_TYPE.asColor()));

    }


}
