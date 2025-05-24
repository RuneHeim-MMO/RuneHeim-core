package org.zuma.examplePlugin.domain.item.interfaces;

import org.zuma.examplePlugin.domain.item.state.StateType;
import org.zuma.examplePlugin.domain.item.state.base.AbstractState;

public interface Stateful {
    public StateType getStateType();

    public AbstractState getInitialState();
}
