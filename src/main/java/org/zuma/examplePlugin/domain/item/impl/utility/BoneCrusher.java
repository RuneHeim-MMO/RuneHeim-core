package org.zuma.examplePlugin.domain.item.impl.utility;

import org.bukkit.Bukkit;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.interfaces.Stateful;
import org.zuma.examplePlugin.domain.item.base.AbstractItemType;
import org.zuma.examplePlugin.domain.item.impl.Remains;
import org.zuma.examplePlugin.domain.item.impl.UtilityItem;
import org.zuma.examplePlugin.domain.item.meta.AbstractItemMeta;
import org.zuma.examplePlugin.domain.item.meta.v1.BoneCrusherMeta;
import org.zuma.examplePlugin.domain.item.state.StateManager;
import org.zuma.examplePlugin.domain.item.state.StateType;
import org.zuma.examplePlugin.domain.item.state.base.AbstractState;
import org.zuma.examplePlugin.domain.item.state.meta.v1.ChargesStateMeta;
import org.zuma.examplePlugin.domain.item.state.states.ChargesState;
import org.zuma.examplePlugin.domain.player.RunePlayer;

import javax.annotation.Nullable;

public class BoneCrusher extends UtilityItem implements Stateful {
    private final ChargesState defaultStateTemplate = new ChargesState(new ChargesStateMeta(0));
    private final double rewardMultiplier, maxCharges;

    public BoneCrusher(AbstractItemMeta meta, BoneCrusherMeta crusherMeta) {
        super(meta);
        this.setStateType(StateType.CHARGES_STATE);
        this.rewardMultiplier = crusherMeta.buryExperienceMultiplier();
        this.maxCharges = crusherMeta.maxCharges();
    }

    public AbstractState cloneDefaultStateTemplate() {
        return defaultStateTemplate.clone();
    }

    @Nullable
    public ChargesState getState(ItemStack item) {
        AbstractState state = RuneQuestPlugin.getItemStateManager().getItemState(item);

        if (state != null && state.getStateType() == StateType.CHARGES_STATE) {
            return (ChargesState) state;
        }

        return null;
    }

    @Override
    public ItemStack onOtherItemPickupWhileHaving(EntityPickupItemEvent event, ItemStack thisItemStack, RunePlayer player, StateManager itemStateManager) {
        ChargesState state = getState(thisItemStack);

        if (state == null || state.getCharges() == 0) {
            return null;
        }

        ItemStack pickedUp = event.getItem().getItemStack();
        AbstractItemType pickedUpType = RuneQuestPlugin.getItemRegistry().getType(pickedUp);

        if (!(pickedUpType instanceof Remains remains)) {
            return null;
        }

        int canBury = (int) Math.min(state.getCharges(), pickedUp.getAmount());

        if (canBury <= 0) {
            return null;
        }

        Bukkit.getScheduler().runTaskLater(RuneQuestPlugin.getInstance(), () -> {
            remains.handleBury(player, canBury, rewardMultiplier);
        }, 1L);

        state.setCharges(Math.max(0, state.getCharges() - canBury));
        this.getMetaGenerator().updatePresentationLayer(thisItemStack);

        return thisItemStack;
    }

    public double getMaxCharges() {
        return maxCharges;
    }
}
