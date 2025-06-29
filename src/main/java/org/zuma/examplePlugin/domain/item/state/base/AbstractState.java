package org.zuma.examplePlugin.domain.item.state.base;

import org.zuma.examplePlugin.domain.item.state.StateType;

public abstract class AbstractState {
    private final StateType type;
    private final int version;

    public AbstractState(StateType type, int version) {
        this.type = type;
        this.version = version;
    }

    public StateType getStateType() {
        return type;
    }

    public int getVersion() {
        return version;
    }
}
