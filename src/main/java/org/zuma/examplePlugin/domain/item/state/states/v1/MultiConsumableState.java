package org.zuma.examplePlugin.domain.item.state.states.v1;

import org.zuma.examplePlugin.domain.item.state.StateType;
import org.zuma.examplePlugin.domain.item.state.base.AbstractState;

public class MultiConsumableState extends AbstractState {
    private final int maxUsages;
    private int usageLeft;

    public MultiConsumableState(int maxUsage, int usages) {
        super(StateType.MULTI_CONSUMABLE, 1);
        this.maxUsages = maxUsage;
        usageLeft = usages;
    }

    public int getUsageLeft() {
        return usageLeft;
    }

    public int getMaxUsages() {
        return maxUsages;
    }

    public boolean consume() {
        if (usageLeft <= 0) {
            return false;
        }
        usageLeft--;
        return true;
    }

    public boolean isConsumed() {
        return usageLeft <= 0;
    }
}
