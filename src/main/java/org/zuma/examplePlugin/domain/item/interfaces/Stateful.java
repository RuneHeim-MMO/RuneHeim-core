package org.zuma.examplePlugin.domain.item.interfaces;

import org.zuma.examplePlugin.domain.item.state.AbstractState;
import org.zuma.examplePlugin.domain.item.state.StateType;

public interface Stateful {
    public StateType getStateType();

    public AbstractState getInitialState();
}
