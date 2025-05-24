package org.zuma.examplePlugin.domain.item.state.states;

import org.zuma.examplePlugin.domain.item.state.StateType;
import org.zuma.examplePlugin.domain.item.state.base.AbstractState;

public class ConsumableState extends AbstractState {
    private int usageLeft = 3;

    public ConsumableState(int usages) {
        super(StateType.CONSUMABLE);
        usageLeft = usages;
    }

    public int getUsageLeft() {
        return usageLeft;
    }

    public void setUsageLeft(int usageLeft) {
        this.usageLeft = usageLeft;
    }
}
