package org.zuma.examplePlugin.core.interfaces;

import org.zuma.examplePlugin.domain.item.state.StateType;
import org.zuma.examplePlugin.domain.item.state.base.AbstractState;

public interface Stateful {

    // Types initial state gets assigned in factories.
    StateType getStateType();

    // Then, if state manager needs to initialize a new state instance, it uses this method and state received from above to clone it.
    AbstractState cloneDefaultStateTemplate();
}
