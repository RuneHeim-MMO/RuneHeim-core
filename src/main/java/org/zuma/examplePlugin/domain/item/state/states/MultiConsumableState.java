package org.zuma.examplePlugin.domain.item.state.states;

import com.google.gson.Gson;
import org.zuma.examplePlugin.core.meta.VersionedJson;
import org.zuma.examplePlugin.domain.item.state.StateType;
import org.zuma.examplePlugin.domain.item.state.base.AbstractState;
import org.zuma.examplePlugin.domain.item.state.meta.AbstractStateMeta;
import org.zuma.examplePlugin.domain.item.state.meta.v1.GearGemSocketsStateMeta;
import org.zuma.examplePlugin.domain.item.state.meta.v1.MultiConsumableStateMeta;

import java.util.ArrayList;

public class MultiConsumableState extends AbstractState {
    private final int maxUsages;
    private int usageLeft;

    public MultiConsumableState(MultiConsumableStateMeta meta) {
        super(StateType.MULTI_CONSUMABLE);
        this.maxUsages = meta.maxUsages();
        usageLeft = meta.usagesLeft();
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

    @Override
    public AbstractStateMeta serialize(Gson gson) {
        return new AbstractStateMeta(this.getStateType(), new VersionedJson(gson.toJson(new MultiConsumableStateMeta(this.maxUsages, this.usageLeft)), 1));
    }

    @Override
    public AbstractState clone() {
        return new MultiConsumableState(new MultiConsumableStateMeta(this.maxUsages, this.usageLeft));
    }
}
