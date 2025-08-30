package org.zuma.examplePlugin.domain.item.impl.consumable;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.constants.Color;
import org.zuma.examplePlugin.core.constants.Icon;
import org.zuma.examplePlugin.core.interfaces.Stateful;
import org.zuma.examplePlugin.core.utils.PlayerUtils;
import org.zuma.examplePlugin.domain.item.ItemRecord;
import org.zuma.examplePlugin.domain.item.base.AbstractItemType;
import org.zuma.examplePlugin.domain.item.constants.ItemId;
import org.zuma.examplePlugin.domain.item.meta.AbstractItemMeta;
import org.zuma.examplePlugin.domain.item.state.StateManager;
import org.zuma.examplePlugin.domain.item.state.StateType;
import org.zuma.examplePlugin.domain.item.state.base.AbstractState;
import org.zuma.examplePlugin.domain.item.state.states.MultiConsumableState;
import org.zuma.examplePlugin.domain.player.RunePlayer;
import org.zuma.examplePlugin.events.item.PlayerConsumableConsumeEvent;

import javax.annotation.Nullable;

public class Potion extends AbstractItemType implements Stateful {
    private MultiConsumableState defaultStateTemplate;

    public Potion(AbstractItemMeta meta) {
        super(meta);
        super.setTypeLore("Potion", Icon.POTION.get());
        this.setStateType(StateType.MULTI_CONSUMABLE);
    }

    public AbstractState cloneDefaultStateTemplate() {
        return defaultStateTemplate.clone();
    }

    public void setDefaultStateTemplate(MultiConsumableState state) {
        this.defaultStateTemplate = state;
    }

    @Nullable
    public MultiConsumableState getState(ItemStack item) {
        AbstractState state = RuneQuestPlugin.getItemStateManager().getItemState(item);

        if (state != null && state.getStateType() == StateType.MULTI_CONSUMABLE) {
            return (MultiConsumableState) state;
        }

        return null;
    }

    @Override
    public void onPlayerConsumeItem(PlayerItemConsumeEvent event, RunePlayer player, StateManager itemStateManager) {
        ItemStack itemStack = PlayerUtils.getItemStackInHand(player.asBukkitPlayer());
        MultiConsumableState state = (MultiConsumableState) itemStateManager.getItemState(itemStack);

        if (state == null) {
            return;
        }

        if (state.isConsumed()) {
            player.asBukkitPlayer().sendMessage(Component.text("It's consumed already!", Color.ERROR.asColor()));
            return;
        }

        PlayerConsumableConsumeEvent newEvent = new PlayerConsumableConsumeEvent(player, this, itemStack, itemStateManager);
        Bukkit.getServer().getPluginManager().callEvent(newEvent);

        if (newEvent.isCancelled()) {
            event.setCancelled(true);
            return;
        }

        if (state.consume()) {
            this.getMetaGenerator().updatePresentationLayer(itemStack);

            if (state.isConsumed()) {
                PlayerUtils.subtractOneFromMainHand(player);
                player.giveItemOrDrop(new ItemRecord(ItemId.EMPTY_VIAL, 1));
                itemStateManager.destroyStateIfExists(itemStack);
            }
        }
    }


}
