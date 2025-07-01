package org.zuma.examplePlugin.domain.item.impl;

import org.bukkit.inventory.ItemStack;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.constants.Icon;
import org.zuma.examplePlugin.core.interfaces.Stateful;
import org.zuma.examplePlugin.domain.item.base.AbstractItemType;
import org.zuma.examplePlugin.domain.item.base.EquipableItemType;
import org.zuma.examplePlugin.domain.item.constants.EquipableType;
import org.zuma.examplePlugin.domain.item.meta.AbstractItemMeta;
import org.zuma.examplePlugin.domain.item.state.StateType;
import org.zuma.examplePlugin.domain.item.state.base.AbstractState;
import org.zuma.examplePlugin.domain.item.state.states.FairyState;

import javax.annotation.Nullable;


public class Fairy extends EquipableItemType implements Stateful {
    private FairyState defaultStateTemplate;

    public Fairy(AbstractItemMeta meta) {
        super(meta);
        super.setTypeLore("Fairy", Icon.POTION.get());
        this.setStateType(StateType.FAIRY_STATE);
    }

    @Override
    public EquipableType getEquipableType() {
        return EquipableType.FAIRY;
    }

    public void setDefaultStateTemplate(FairyState state) {
        this.defaultStateTemplate = state;
    }

    public AbstractState cloneDefaultStateTemplate() {
        return defaultStateTemplate.clone();
    }

    @Nullable
    public FairyState getState(ItemStack item) {
        AbstractState state = RuneQuestPlugin.getItemStateManager().getItemState(item);

        if (state != null && state.getStateType() == StateType.FAIRY_STATE) {
            return (FairyState) state;
        }

        return null;
    }

}
