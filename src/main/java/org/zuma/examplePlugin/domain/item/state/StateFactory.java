package org.zuma.examplePlugin.domain.item.state;

import com.google.gson.Gson;
import org.zuma.examplePlugin.domain.item.state.base.AbstractState;
import org.zuma.examplePlugin.domain.item.state.states.ConsumableState;

public class StateFactory {

    public AbstractState createState(StateType type, String json) {
        Gson gson = new Gson();

        return switch (type) {
            case CONSUMABLE -> gson.fromJson(json, ConsumableState.class);
        };
    }
}
