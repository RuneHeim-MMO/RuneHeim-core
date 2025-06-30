package org.zuma.examplePlugin.domain.item.state.base;

import com.google.gson.Gson;
import org.zuma.examplePlugin.domain.item.state.StateType;
import org.zuma.examplePlugin.domain.item.state.meta.AbstractStateMeta;

public abstract class AbstractState {
    private final StateType type;

    public AbstractState(StateType type) {
        this.type = type;
    }

    public StateType getStateType() {
        return type;
    }

    public abstract AbstractStateMeta serialize(Gson gson);
}
