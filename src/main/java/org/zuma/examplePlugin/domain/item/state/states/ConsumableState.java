package org.zuma.examplePlugin.domain.item.state.states;

import org.zuma.examplePlugin.domain.item.state.StateType;
import org.zuma.examplePlugin.domain.item.state.base.AbstractState;

public class ConsumableState extends AbstractState {
    private int maxUsages;
    private int usageLeft;

    public ConsumableState(int maxUsage, int usages) {
        super(StateType.CONSUMABLE);
        this.maxUsages = maxUsage;
        usageLeft = usages;
    }

    public int getUsageLeft() {
        return usageLeft;
    }

    public void setUsageLeft(int usageLeft) {
        this.usageLeft = usageLeft;
    }

    public int getMaxUsages() {
        return maxUsages;
    }

    public void setMaxUsages(int maxUsage) {
        this.maxUsages = maxUsage;
    }
}
