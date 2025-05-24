package org.zuma.examplePlugin.domain.item.base;

import com.google.gson.Gson;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
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

    public ConsumableItem(AbstractItemMeta meta) {
        super(meta);
        this.setRarityPrefixSlot("CONSUMABLE");
        this.setStateType(StateType.CONSUMABLE);
    }


    @Override
    public AbstractState getInitialState() {
        return new ConsumableState(usages);
    }

    @Override
    public void deserializeAndApplyMeta() {
        ConsumableItemMeta meta = new Gson().fromJson(super.jsonMeta, ConsumableItemMeta.class);
        usages = meta.usages();
    }

    @Override
    public void onPlayerInteract(PlayerInteractEvent event, RunePlayer player, StateManager itemStateManager) {
        ConsumableState state = (ConsumableState) itemStateManager.getItemState(PlayerUtils.getItemStackInHand(player.getPlayer()));

        if (state == null) {
            System.out.println("State is null for item " + PlayerUtils.getItemStackInHand(player.getPlayer()));
            return;
        }

        if (state.getUsageLeft() == 0) {
            player.getPlayer().sendMessage("Oops! It's consumed already");
            return;
        }

        state.setUsageLeft(state.getUsageLeft() - 1);
        player.getPlayer().sendMessage("Usages left: " + state.getUsageLeft());
    }

    @Override
    public void onPlayerTakeItemInHand(PlayerItemHeldEvent event, RunePlayer player, StateManager itemStateManager) {
        ConsumableState state = (ConsumableState) itemStateManager.getItemState(PlayerUtils.getItemStackInHand(player.getPlayer()));

        if (state == null) {
            return;
        }


        player.getPlayer().sendMessage("Usages left: " + state.getUsageLeft());
    }


}
