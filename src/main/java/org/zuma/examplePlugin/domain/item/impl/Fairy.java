package org.zuma.examplePlugin.domain.item.impl;

import org.zuma.examplePlugin.core.constants.Icon;
import org.zuma.examplePlugin.core.interfaces.Stateful;
import org.zuma.examplePlugin.domain.item.base.AbstractItemType;
import org.zuma.examplePlugin.domain.item.meta.AbstractItemMeta;
import org.zuma.examplePlugin.domain.item.state.StateType;
import org.zuma.examplePlugin.domain.item.state.base.AbstractState;
import org.zuma.examplePlugin.domain.item.state.meta.v1.FairyStateMeta;
import org.zuma.examplePlugin.domain.item.state.states.FairyState;


public class Fairy extends AbstractItemType implements Stateful {
    private FairyState state;

    public Fairy(AbstractItemMeta meta) {
        super(meta);
        super.setTypeLore("Fairy", Icon.POTION.get());
        this.setStateType(StateType.FAIRY_STATE);
    }

    public void setFairyState(FairyState state) {
        this.state = state;
    }

    @Override
    public AbstractState getInitialState() {
        return new FairyState(new FairyStateMeta(state.getUpgradeIds(), state.getStamina(), state.getExperience(), state.canConsumeFruit(), state.getGeneration()));
    }

}
