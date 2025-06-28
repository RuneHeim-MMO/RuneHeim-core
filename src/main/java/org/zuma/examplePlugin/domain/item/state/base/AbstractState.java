package org.zuma.examplePlugin.domain.item.state.base;

import org.zuma.examplePlugin.domain.item.state.StateType;

public abstract class AbstractState {
    private final StateType type;

    public AbstractState(StateType type) {
        this.type = type;
    }

    public StateType getStateType() {
        return type;
    }

}
