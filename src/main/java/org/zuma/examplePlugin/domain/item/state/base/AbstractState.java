package org.zuma.examplePlugin.domain.item.state.base;

import com.google.gson.Gson;
import org.zuma.examplePlugin.domain.item.state.StateType;
import org.zuma.examplePlugin.domain.item.state.meta.AbstractStateMeta;

public abstract class AbstractState {
    private final StateType type;
    private long createdAt;

    public AbstractState(StateType type) {
        this.type = type;
        this.createdAt = System.currentTimeMillis();
    }

    public StateType getStateType() {
        return type;
    }

    public abstract AbstractStateMeta serialize(Gson gson);

    public abstract AbstractState clone();

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }
}
